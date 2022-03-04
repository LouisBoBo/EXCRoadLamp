package com.exc.roadlamp.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.xuexiang.xutil.app.AppUtils.getPackageName;


/**
 * 权限动态获取类
 *
 * @author zwl
 * @date on 2019-08-22
 */
public class PermissionInterceptor {

    private final int REQUEST_CODE = 1000;

    private FragmentActivity mActivity;

    private Fragment mFragment;

    private Context mContext;

    private OnPermissionResult mOnPermissionResult;

    private RxPermissions mRxPermissions;

    private String[] permissions;

    private int grantedNum;

    private boolean isSelectNoAsk;

    private boolean isForce;

    private String permissionsPrompt;//权限提示框的内容

    /**
     * 权限类型
     */
    private PermissionTypeEnum mPermissionTypeEnum;


    public PermissionInterceptor(FragmentActivity activity) {
        if (activity == null) {
            throw new NullPointerException();
        }
        this.mActivity = activity;
        this.mContext = mActivity;
        this.mRxPermissions = new RxPermissions(mActivity);
    }

    public PermissionInterceptor(Fragment fragment) {
        if (fragment == null) {
            throw new NullPointerException();
        }
        this.mFragment = fragment;
        this.mContext = fragment.getContext();
        this.mRxPermissions = new RxPermissions(mFragment);
    }

    public PermissionInterceptor setPermissions(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * 是否强制权限
     *
     * @param isForce true 强制 弹出框只有确认按钮   false 不强制 弹出框有取消按钮
     * @return
     */
    public PermissionInterceptor isForcePermission(boolean isForce) {
        this.isForce = isForce;
        return this;
    }


    /**
     * 设置权限弹出框提示文案
     * @param permissionsPrompt
     * @return
     */
    public PermissionInterceptor setPermissionsPrompt(String permissionsPrompt) {
        this.permissionsPrompt = permissionsPrompt;
        return this;
    }

    public PermissionInterceptor setPermissionType(PermissionTypeEnum  permissionTypeEnum) {
        this.mPermissionTypeEnum = permissionTypeEnum;
        return this;
    }



    /**
     * 检查权限
     *
     * @return
     */
    public PermissionInterceptor checkPermission() {
        if (permissions == null || permissions.length == 0) return this;
        final List<String> rejectPermissions = new ArrayList<>();
        mRxPermissions.requestEach(permissions).subscribe(new Observer<Permission>() {
            @Override
            public void onSubscribe(Disposable d) {
                grantedNum = 0;
                isSelectNoAsk = false;
                rejectPermissions.clear();
            }

            @Override
            public void onNext(Permission permission) {
                // 用户已经同意该权限
                if (permission.granted) {
                    grantedNum++;
                }
                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                else if (permission.shouldShowRequestPermissionRationale) {
                    rejectPermissions.add(permission.name);
                }
                // 用户拒绝了该权限，而且选中『不再询问』
                else {
                    isSelectNoAsk = true;
                    rejectPermissions.add(permission.name);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                if (grantedNum == permissions.length) {
                    isSelectNoAsk = false;
                    if (mOnPermissionResult != null) {
                        mOnPermissionResult.onPermissionAllAllow();
                    }
                } else {
                    showPermissionWarnDialog(isSelectNoAsk, rejectPermissions);
                }
            }
        });
        return this;
    }

    /**
     * 显示权限提示框
     *
     * @param isSelectNoAsk 是否选择了不再询问
     */
    private void showPermissionWarnDialog(final boolean isSelectNoAsk, final List<String> rejectPermissions) {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(mContext);
        materialDialog.title("提示")
                .cancelable(false)
                .content(getPermissionsPrompt())
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isSelectNoAsk) {
                            Intent mItent;
                            switch (mPermissionTypeEnum){
                                case ACCESS_COARSE_LOCATION:
                                    mItent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    String pkg = "com.android.settings";
                                    String cls = "com.android.settings.applications.InstalledAppDetails";
                                    mItent.setComponent(new ComponentName(pkg, cls));
                                    mItent.setData(Uri.parse("package:" + getPackageName()));
                                    if (mActivity != null) {
                                        mActivity.startActivityForResult(mItent, REQUEST_CODE);
                                    } else {
                                        mFragment.startActivityForResult(mItent, REQUEST_CODE);
                                    }
                                    ToastUtils.toast("请点权限，并同意授权定位权限。");
                                    break;
                            }

                        } else {
                            checkPermission();
                        }
                    }
                });
        if (!isForce) {
            materialDialog.negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (mOnPermissionResult != null) {
                                mOnPermissionResult.onPermissionReject((String[]) rejectPermissions.toArray(new String[0]));
                            }
                        }
                    });
        }
        materialDialog.show();
    }

    /**
     * 获取权限弹出框提示
     * @return
     */
    public String getPermissionsPrompt() {
        return TextUtils.isEmpty(permissionsPrompt) ? "获取不到授权，APP将无法正常使用，请允许APP获取权限！" : permissionsPrompt;
    }


    /**
     * Activity或Fragment的onActivityResult方法中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        checkPermission();
    }

    /**
     * 权限操作结果回调
     *
     * @param onPermissionResult
     * @return
     */
    public PermissionInterceptor setPermissionListener(OnPermissionResult onPermissionResult) {
        this.mOnPermissionResult = onPermissionResult;
        return this;
    }

    public interface OnPermissionResult {
        void onPermissionAllAllow();

        void onPermissionReject(String[] permissions);
    }
}
