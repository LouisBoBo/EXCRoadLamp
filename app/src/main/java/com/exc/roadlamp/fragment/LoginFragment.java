/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.exc.roadlamp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.exc.roadlamp.Constant;
import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.bean.BaseBean;
import com.exc.roadlamp.bean.LoginInfo;
import com.exc.roadlamp.customview.DownloadCircleDialog;
import com.exc.roadlamp.databinding.FragmentLoginBinding;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.http.base.RSAUtils;
import com.exc.roadlamp.utils.DownloadUtils;
import com.exc.roadlamp.utils.FormatUtils;
import com.exc.roadlamp.utils.ObjectSaveUtils;
import com.exc.roadlamp.utils.PreferencesUtil;
import com.exc.roadlamp.utils.SSLAgent;
import com.exc.roadlamp.utils.SSLSocketClient;
import com.exc.roadlamp.utils.SdUtils;
import com.exc.roadlamp.utils.TokenUtils;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.kongzue.baseokhttp.listener.BaseResponseListener;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.app.AppUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhy.http.okhttp.utils.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 登录页面
 */
@Page(anim = CoreAnim.none)
public class LoginFragment extends MyBaseFragment implements View.OnClickListener ,View.OnTouchListener{

    private CountDownButtonHelper mCountDownHelper;
    private Activity mActivity;
    private FragmentLoginBinding binding;
    private DownloadCircleDialog dialogProgress;
    private int touch_count =0;
    private int locationSelectOption = 0;
    private List<String> mLocationOption = new ArrayList<>();

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    dialogProgress = new DownloadCircleDialog(mActivity);
                    showNewVersion();
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setVisibility(View.GONE);
        return titleBar;
    }

    @Override
    protected void initViews() {
        mActivity = getActivity();
        if (!StringUtils.isEmpty(PreferencesUtil.getString(mActivity, Constant.LOCATION_USER_NAME_KEY))) {
            binding.etPhoneNumber.setText((PreferencesUtil.getString(mActivity, Constant.LOCATION_USER_NAME_KEY)));
        }

        if (!StringUtils.isEmpty(PreferencesUtil.getString(mActivity, Constant.LOCATION_PWD_KEY))) {
            binding.etPwd.setText((PreferencesUtil.getString(mActivity, Constant.LOCATION_PWD_KEY)));
        }

        SSLAgent.getInstance().trustAllHttpsCertificates();
        checkAPP();

        binding.basetitle.setOnTouchListener(this::onTouch);

        mLocationOption.add("http://42.194.210.113");//正式
        mLocationOption.add("http://192.168.18.202");//测试
        mLocationOption.add("http://192.168.112.112");//小罗
    }

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding =  FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private void requestLogin(String publicKey) {
        String pwsRSA = RSAUtils.encrypt(publicKey, binding.etPwd.getText().toString());
        JsonMap map = new JsonMap();
        map.set("username", binding.etPhoneNumber.getText().toString());
        map.set("password", pwsRSA);
        HttpRequest.JSONPOST(getContext(), HttpApi.LOGIN, map, new BeanResponseListener<LoginInfo>() {
            @Override
            public void onResponse(LoginInfo loginInfo, Exception error) {
                mMessageLoader.dismiss();

                if (error == null) {
                    //保存登录登录信息到本地
                    new ObjectSaveUtils<LoginInfo>().saveObject(mActivity, Constant.LOGIN_INFO_BEAN_KEY, loginInfo);
                    PreferencesUtil.putString(mActivity, Constant.USER_TOKEN, loginInfo.getData().getToken());
                    PreferencesUtil.putInt(mActivity, Constant.USER_ID, loginInfo.getData().getUserId());
                    //保存登录的账号和密码
                    PreferencesUtil.putString(mActivity, Constant.LOCATION_USER_NAME_KEY, binding.etPhoneNumber.getText().toString());
                    PreferencesUtil.putString(mActivity, Constant.LOCATION_PWD_KEY, binding.etPwd.getText().toString());
                    onLoginSuccess(loginInfo.getData().getToken());
                    XToastUtils.success("登录成功！");
                    return;
                }

            }
        });
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phoneNumber) {
        // TODO: 2020/8/29 这里只是界面演示而已
        XToastUtils.warning("只是演示，验证码请随便输");
        mCountDownHelper.start();
    }

    /**
     * 根据验证码登录
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     */
    private void loginByVerifyCode(String phoneNumber, String verifyCode) {
        // TODO: 2020/8/29 这里只是界面演示而已
//        onLoginSuccess();
    }

    /**
     * 登录成功的处理
     */
    private void onLoginSuccess(String token) {
//        String token = RandomUtils.getRandomNumbersAndLetters(16);
        if (TokenUtils.handleLoginSuccess(token)) {
            popToBack();
            ActivityUtils.startActivity(MainActivity.class);

        }
    }

    @Override
    public void onDestroyView() {
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
                if (binding.etPhoneNumber.getText().toString().length() == 0) {
                    XToastUtils.error("请输入账号");
                    return;
                }
                if (binding.etPwd.getText().toString().length() == 0) {
                    XToastUtils.error("请输入密码");
                    return;
                }
                mMessageLoader.show();

                JsonMap map = new JsonMap();
                HttpRequest.JSONPOST(getContext(), HttpApi.LOGIN_GET_PUBLIC_KEY, map, new BeanResponseListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean baseBean, Exception error) {
                        mMessageLoader.dismiss();
                        requestLogin(baseBean.getMessage());
                    }
                });

                break;

            default:
                break;
        }
    }


    //////////////////////版本更新//////////////////////////

    //检查版本更新
    public void checkAPP() {


        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        Request request = new Request.Builder()
                .url(HttpApi.API_CHECK_APP)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("数据错误",e.getMessage());
            }

            @SneakyThrows
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result="";
                if (response.body()!=null) {
                    result=response.body().string();

                    JSONObject jsonObj = JSONObject.parseObject(result);
                    String code = jsonObj.getString("code");
                    JSONObject obj = jsonObj.getJSONObject("data");
                    String onlineVersionNo = obj.getString("buildVersionNo");//线上的版本号

                    PackageManager pm = mActivity.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(mActivity.getPackageName(), 0);
                    int buildVersonNo  = pi.versionCode;//正在使用的版本号

                    if(Integer.parseInt(onlineVersionNo) > buildVersonNo){
                        myHandler.sendEmptyMessage(123);
                    }
                }else {
                    //返回数据错误
                    return;
                }

            }
        });
    }

    //1.权限申请，通过后开始下载
    private void showNewVersion() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(data -> {
                    L.e("以获得权限" + data.toString());
                    DialogLoader.getInstance().showConfirmDialog(
                            mActivity,
                            getString(R.string.tip_update_permission),
                            getString(R.string.tip_yes),
                            (dialog, which) -> {
                                dialog.dismiss();

                                String down_url = HttpApi.APP_DOWNLOAD_URL;
                                downloadApk((Activity) mActivity, down_url);
                            },
                            getString(R.string.tip_no),
                            (dialog, which) -> {

                                dialog.dismiss();
                            }
                    );
                })
                .onDenied(data -> L.e("未获得权限" + data.toString())).start();
    }

    //2.开始下载apk
    public void downloadApk(final Activity context, String down_url) {
        dialogProgress.show();

        PRDownloader.initialize(context.getApplicationContext());

        DownloadUtils.getInstance(context).download(down_url, SdUtils.getDownloadPath(), "Roadlamp.apk", new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                dialogProgress.setHiden();
                String successDownloadApkPath = SdUtils.getDownloadPath() + "Roadlamp.apk";
                installApkO(mActivity, successDownloadApkPath);
            }

            @Override
            public void onDownloading(Progress progressInfo) {
                double scale = (progressInfo.currentBytes*1.0/progressInfo.totalBytes)*100;
                dialogProgress.setProgress((int)scale);
                if(scale >= 100){
                    dialogProgress.setMsg("下载完成，开始安装！");
                }
            }

            @Override
            public void onDownloadFailed() {
                dialogProgress.dismiss();
                ToastUtils.toast("下载失败！");
            }
        });

    }

    // 3.下载成功，开始安装,兼容8.0安装位置来源的权限
    private void installApkO(Context context, String downloadApkPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
//                L.i("8.0手机已经拥有安装未知来源应用的权限，直接安装！");
                com.exc.roadlamp.utils.AppUtils.installApk(context, downloadApkPath);
            } else {

                DialogLoader.getInstance().showConfirmDialog(
                        mActivity,
                        getString(R.string.tip_update_quanxian),
                        getString(R.string.tip_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            Uri packageUri = Uri.parse("package:" + AppUtils.getAppPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                            startActivityForResult(intent, 10086);
                        },
                        getString(R.string.tip_no),
                        (dialog, which) -> {
                            dialog.dismiss();
                        }
                );
            }
        } else {
            com.exc.roadlamp.utils.AppUtils.installApk(context, downloadApkPath);
        }
    }

    //4.开启了安装未知来源应用权限后，再次进行步骤3的安装。
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            String successDownloadApkPath = SdUtils.getDownloadPath() + "Roadlamp.apk";
            installApkO(mActivity, successDownloadApkPath);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("---------------按下了-------------------");
                touch_count ++;
                if(touch_count >=5){
                    showLocationConstellationPickerView();
                }
                break;
        }
        return true;
    }

    //为项目选择IP
    private void showLocationConstellationPickerView(){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {

            locationSelectOption = options1;

            String SERVICES_ADDRESS = mLocationOption.get(locationSelectOption);
            String SERVICES_ADDRESS_LAMP_POLE = mLocationOption.get(locationSelectOption);
            String SERVICES_ADDRESS_DEVICE_CONTROL = mLocationOption.get(locationSelectOption);
            String SERVICES_ADDRESS_DEVICE_POWER_CABINET = mLocationOption.get(locationSelectOption);

            if(SERVICES_ADDRESS.contains("http://192.168.18.202")){//测试环境
                SERVICES_ADDRESS_LAMP_POLE = "http://192.168.18.201";
                SERVICES_ADDRESS_DEVICE_CONTROL = "http://192.168.18.201";
                SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://192.168.18.201";
            }else if(SERVICES_ADDRESS.contains("http://42.194.210.113")){//正式环境
                SERVICES_ADDRESS_LAMP_POLE = "http://42.194.208.18";
                SERVICES_ADDRESS_DEVICE_CONTROL = "http://106.55.15.212";
                SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://42.194.208.18";
            }

            HttpApi api = new HttpApi();
            api.setIP(SERVICES_ADDRESS,SERVICES_ADDRESS_LAMP_POLE,SERVICES_ADDRESS_DEVICE_CONTROL,SERVICES_ADDRESS_DEVICE_POWER_CABINET);

            touch_count =0;
            return false;
        })
                .setTitleText("选择服务器IP")
                .setSelectOptions(locationSelectOption)
                .build();
        pvOptions.setPicker(mLocationOption);
        pvOptions.show();
    }
}

