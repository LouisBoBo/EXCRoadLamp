package com.exc.roadlamp.base;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.xuexiang.xpage.R;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.model.PageInfo;

import java.util.Arrays;


/**
 * 页面跳转控制参数
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:47
 */
public class MyCoreSwitchBean implements Parcelable {
    public static final String KEY_SWITCH_BEAN = "SwitchBean";
    public static final String KEY_START_ACTIVITY_FOR_RESULT = "startActivityForResult";

    public static final Creator<MyCoreSwitchBean> CREATOR = new Creator<MyCoreSwitchBean>() {
        @Override
        public MyCoreSwitchBean createFromParcel(Parcel in) {
            return new MyCoreSwitchBean(in);
        }

        @Override
        public MyCoreSwitchBean[] newArray(int size) {
            return new MyCoreSwitchBean[size];
        }
    };
    /**
     * 页面名
     */
    private String mPageName;
    /**
     * 相关数据
     */
    private Bundle mBundle;
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
     * XPageFragment的容器Activity类名
     */
    private String mContainActivityClassName = MyPageConfig.getContainActivityClassName();
    /**
     * 请求code码
     */
    private int mRequestCode = -1;

    //fragment跳转
    public MyCoreSwitchBean(String pageName) {
        mPageName = pageName;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle) {
        mPageName = pageName;
        mBundle = bundle;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, CoreAnim coreAnim) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(coreAnim);
    }

    /**
     * 动画转化，根据枚举类返回int数组
     *
     * @param coreAnim 动画枚举
     * @return 转化后的动画数组
     */
    public static int[] convertAnimations(CoreAnim coreAnim) {
        if (coreAnim == CoreAnim.present) {
            return new int[]{R.anim.xpage_push_in_down, R.anim.xpage_push_no_anim, R.anim.xpage_push_no_anim, R.anim.xpage_push_out_down};
        } else if (coreAnim == CoreAnim.fade) {
            return new int[]{R.anim.xpage_alpha_in, R.anim.xpage_alpha_out, R.anim.xpage_alpha_in, R.anim.xpage_alpha_out};
        } else if (coreAnim == CoreAnim.slide) {
            return new int[]{R.anim.xpage_slide_in_right, R.anim.xpage_slide_out_left, R.anim.xpage_slide_in_left, R.anim.xpage_slide_out_right};
        } else if (coreAnim == CoreAnim.zoom) {
            return new int[]{R.anim.xpage_zoom_in, R.anim.xpage_zoom_out, R.anim.xpage_zoom_in, R.anim.xpage_zoom_out};
        }
        return null;
    }

    public <T extends MyXPageFragment> MyCoreSwitchBean(Class<T> clazz, Bundle bundle) {
        PageInfo pageInfo = MyPageConfig.getPageInfo(clazz);
        mPageName = pageInfo.getName();
        mBundle = bundle;
        setAnim(pageInfo.getAnim());
    }

    public <T extends MyXPageFragment> MyCoreSwitchBean(Class<T> clazz) {
        PageInfo pageInfo = MyPageConfig.getPageInfo(clazz);
        mPageName = pageInfo.getName();
        setAnim(pageInfo.getAnim());
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, int[] anim) {
        mPageName = pageName;
        mBundle = bundle;
        mAnim = anim;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(coreAnim);
        mAddToBackStack = addToBackStack;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        mPageName = pageName;
        mBundle = bundle;
        mAnim = anim;
        mAddToBackStack = addToBackStack;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, CoreAnim coreAnim, boolean addToBackStack, boolean newActivity) {
        mPageName = pageName;
        mBundle = bundle;
        setAnim(coreAnim);
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        mPageName = pageName;
        mBundle = bundle;
        mAnim = anim;
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
    }

    public MyCoreSwitchBean(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity, int requestCode) {
        mPageName = pageName;
        mBundle = bundle;
        mAnim = anim;
        mAddToBackStack = addToBackStack;
        mNewActivity = newActivity;
        mRequestCode = requestCode;
    }


    protected MyCoreSwitchBean(Parcel in) {
        mPageName = in.readString();
        mBundle = in.readBundle(getClass().getClassLoader());
        mAnim = new int[]{in.readInt(), in.readInt(), in.readInt(), in.readInt()};
        mAddToBackStack = in.readInt() == 1;
        mNewActivity = in.readInt() == 1;
        mContainActivityClassName = in.readString();
        mRequestCode = in.readInt();
    }

    public String getPageName() {
        return mPageName;
    }

    public MyCoreSwitchBean setPageName(String pageName) {
        mPageName = pageName;
        return this;
    }

    public boolean isNewActivity() {
        return mNewActivity;
    }

    public MyCoreSwitchBean setNewActivity(boolean newActivity) {
        mNewActivity = newActivity;
        return this;
    }

    /**
     * 设置XPageFragment的容器Activity类名
     *
     * @param containActivityClazz
     * @return
     */
    public MyCoreSwitchBean setContainActivityClazz(@NonNull Class<? extends MyXPageActivity> containActivityClazz) {
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public MyCoreSwitchBean setContainActivityClassName(@NonNull String containActivityClassName) {
        mContainActivityClassName = containActivityClassName;
        return this;
    }

    public MyCoreSwitchBean setNewActivity(boolean newActivity, @NonNull Class<? extends MyXPageActivity> containActivityClazz) {
        mNewActivity = newActivity;
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public Class<?> getContainActivityClazz() throws ClassNotFoundException {
        return Class.forName(mContainActivityClassName);
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public MyCoreSwitchBean setAddToBackStack(boolean addToBackStack) {
        mAddToBackStack = addToBackStack;
        return this;
    }

    public int[] getAnim() {
        return mAnim;
    }

    public MyCoreSwitchBean setAnim(CoreAnim anim) {
        mAnim = convertAnimations(anim);
        return this;
    }

    public MyCoreSwitchBean setAnim(int[] anim) {
        mAnim = anim;
        return this;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public MyCoreSwitchBean setBundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public MyCoreSwitchBean setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    @Override
    public String toString() {
        return "CoreSwitchBean{" +
                "mPageName='" + mPageName + '\'' +
                ", mBundle=" + mBundle +
                ", mAnim=" + Arrays.toString(mAnim) +
                ", mAddToBackStack=" + mAddToBackStack +
                ", mNewActivity=" + mNewActivity +
                ", mContainActivityClassName='" + mContainActivityClassName + '\'' +
                ", mRequestCode=" + mRequestCode +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        if (mPageName == null) {
            mPageName = "";
        }
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        if (mAnim == null) {
            int[] a = {-1, -1, -1, -1};
            mAnim = a;
        }
        out.writeString(mPageName);
        mBundle.writeToParcel(out, flags);
        if (mAnim != null && mAnim.length == 4) {
            out.writeInt(mAnim[0]);
            out.writeInt(mAnim[1]);
            out.writeInt(mAnim[2]);
            out.writeInt(mAnim[3]);
        } else {
            out.writeInt(-1);
            out.writeInt(-1);
            out.writeInt(-1);
            out.writeInt(-1);
        }
        out.writeInt(mAddToBackStack ? 1 : 0);
        out.writeInt(mNewActivity ? 1 : 0);
        out.writeString(mContainActivityClassName);
        out.writeInt(mRequestCode);
    }


}
