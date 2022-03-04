package com.exc.roadlamp.my;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentAboutUsBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "关于我们")
public class AboutUsFragment extends MyBaseFragment {
    private FragmentAboutUsBinding binding;
    @Override
    protected void initViews() {

    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentAboutUsBinding.inflate(inflater,container,false);
        binding.tvVersion.setText("版本号：V" + getAppVersionName(getContext()));
        return binding.getRoot();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;

            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
