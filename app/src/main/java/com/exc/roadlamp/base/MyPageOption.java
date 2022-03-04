package com.exc.roadlamp.base;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.model.PageInfo;

import java.io.Serializable;
import java.util.Arrays;

import static com.xuexiang.xpage.core.CoreSwitchBean.convertAnimations;

/**
 * 页面选项
 *
 * @author XUE
 * @since 2019/3/21 10:08
 */
public class MyPageOption {
    /**
     * 页面名
     */
    private String mPageName;
    /**
     * 相关数据
     */
    private Bundle mBundle = null;
    /**
     * 动画类型
     */
    private int[] mAnim = null;
    /**
     * 是否添加到栈中
     */
    private boolean mAddToBackStack = true;
    /**
     * 是否起新的Activity
     */
    private boolean mNewActivity = false;
    /**
     * 新起Activity的容器类名
     */
    private String mContainActivityClassName = MyPageConfig.getContainActivityClassName();
    /**
     * 请求code码
     */
    private int mRequestCode = -1;

    /**
     * 获取页面选项
     *
     * @param pageName 页面名
     * @return
     */
    public static MyPageOption to(String pageName) {
        return new MyPageOption(pageName);
    }

    /**
     * 获取页面选项
     *
     * @param clazz 页面所在类
     * @param <T>
     * @return
     */
    public static <T extends MyXPageFragment> MyPageOption to(Class<T> clazz) {
        return new MyPageOption(clazz);
    }

    public MyPageOption(String pageName) {
        mPageName = pageName;
    }

    public <T extends MyXPageFragment> MyPageOption(Class<T> clazz) {
        setTargetPage(clazz);
    }

    public MyPageOption(String pageName, Bundle bundle) {
        mPageName = pageName;
        mBundle = bundle;
    }

    public MyPageOption(String pageName, Bundle bundle, boolean addToBackStack) {
        mPageName = pageName;
        mBundle = bundle;
        mAddToBackStack = addToBackStack;
    }

    public MyPageOption(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity, int requestCode) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(anim);
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
        mRequestCode = requestCode;
    }

    public MyPageOption(String pageName, Bundle bundle, CoreAnim anim, boolean addToBackStack, boolean newActivity, int requestCode) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(anim);
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
        mRequestCode = requestCode;
    }

    /**
     * 设置跳转目标页
     *
     * @param clazz 必须使用@Page进行修饰的类
     * @param <T>
     * @return
     */
    public <T extends MyXPageFragment> MyPageOption setTargetPage(Class<T> clazz) {
        PageInfo pageInfo = MyPageConfig.getPageInfo(clazz);
        mPageName = pageInfo.getName();
        setAnim(pageInfo.getAnim());
        return this;
    }

    public String getPageName() {
        return mPageName;
    }

    /**
     * 设置跳转fragment页面地名称
     *
     * @param pageName
     * @return
     */
    public MyPageOption setPageName(String pageName) {
        mPageName = pageName;
        return this;
    }

    public int[] getAnim() {
        return mAnim;
    }

    public MyPageOption setAnim(int[] anim) {
        mAnim = anim;
        return this;
    }

    public MyPageOption setAnim(CoreAnim anim) {
        mAnim = convertAnimations(anim);
        return this;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public MyPageOption setAddToBackStack(boolean addToBackStack) {
        mAddToBackStack = addToBackStack;
        return this;
    }

    public boolean isNewActivity() {
        return mNewActivity;
    }

    public MyPageOption setNewActivity(boolean newActivity) {
        mNewActivity = newActivity;
        return this;
    }

    /**
     * 设置XPageFragment的容器Activity类名
     *
     * @param containActivityClazz
     * @return
     */
    public MyPageOption setContainActivityClazz(@NonNull Class<? extends MyXPageActivity> containActivityClazz) {
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public Class<?> getContainActivityClazz() throws ClassNotFoundException {
        return Class.forName(mContainActivityClassName);
    }

    public MyPageOption setNewActivity(boolean newActivity, @NonNull Class<? extends MyXPageActivity> containActivityClazz) {
        mNewActivity = newActivity;
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public String getContainActivityClassName() {
        return mContainActivityClassName;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public boolean isOpenForResult() {
        return mRequestCode != -1;
    }

    /**
     * StartForResult
     *
     * @param requestCode
     * @return
     */
    public MyPageOption setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        //设置了请求码之后，必须加入到堆栈中去
        mAddToBackStack = true;
        return this;
    }

    /**
     * 在XPageFragment中使用
     *
     * @param fragment
     * @return
     */
    public Fragment open(@NonNull MyXPageFragment fragment) {
        return fragment.openPage(this);
    }

    /**
     * 在XPageFragment中使用，有返回值
     *
     * @param fragment
     * @param requestCode
     * @return
     */
    public Fragment openForResult(@NonNull MyXPageFragment fragment, int requestCode) {
        setRequestCode(requestCode);
        return fragment.openPage(this);
    }

    /**
     * 在XPageActivity中使用
     *
     * @param activity
     * @return
     */
    public Fragment open(@NonNull MyXPageActivity activity) {
        return activity.openPage(toSwitch());
    }

    public MyCoreSwitchBean toSwitch() {
        return MyUtils.toSwitch(this);
    }

    //=================参数传递=======================//

    public Bundle getBundle() {
        return mBundle;
    }

    public MyPageOption setBundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public MyPageOption putString(@Nullable String key, @Nullable String value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putString(key, value);
        return this;
    }

    public MyPageOption putBoolean(@Nullable String key, boolean value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putBoolean(key, value);
        return this;
    }

    public MyPageOption putInt(@Nullable String key, int value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putInt(key, value);
        return this;
    }

    public MyPageOption putShort(@Nullable String key, short value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putShort(key, value);
        return this;
    }

    public MyPageOption putLong(@Nullable String key, long value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putLong(key, value);
        return this;
    }

    public MyPageOption putFloat(@Nullable String key, float value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putFloat(key, value);
        return this;
    }

    public MyPageOption putDouble(@Nullable String key, double value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putDouble(key, value);
        return this;
    }

    public MyPageOption putParcelable(@Nullable String key, @Nullable Parcelable value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putParcelable(key, value);
        return this;
    }

    public MyPageOption putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public MyPageOption putSerializable(@Nullable String key, @Nullable Serializable value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putSerializable(key, value);
        return this;
    }

    public MyPageOption putByte(@Nullable String key, byte value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putByte(key, value);
        return this;
    }

    public MyPageOption putAll(Bundle bundle) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putAll(bundle);
        return this;
    }

    @Override
    public String toString() {
        return "PageOption{" +
                "mPageName='" + mPageName + '\'' +
                ", mBundle=" + mBundle +
                ", mAnim=" + Arrays.toString(mAnim) +
                ", mAddToBackStack=" + mAddToBackStack +
                ", mNewActivity=" + mNewActivity +
                ", mRequestCode=" + mRequestCode +
                '}';
    }
}
