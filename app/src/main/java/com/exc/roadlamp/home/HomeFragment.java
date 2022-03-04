/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.exc.roadlamp.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentHomeBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.display.DensityUtils;

import butterknife.BindView;

/**
 * 首页
 */
@Page(anim = CoreAnim.none)
public class HomeFragment extends MyBaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private FragmentHomeBinding binding;

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mActivity = getActivity();

        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.ms_content,new DeviceOnlineFragment());
        fTransaction.add(R.id.mdd_content,new DeviceSingleFragment());
        fTransaction.add(R.id.mo_content,new DeviceOrderFragment());
        fTransaction.add(R.id.mgj_content,new DeviceAlarmFragment());
        fTransaction.add(R.id.mf_content,new DeviceLightFragment());
        fTransaction.add(R.id.mjk_content,new DeviceControlFragment());
//        fTransaction.add(R.id.mel_content,new DeviceSolarFragment());
        fTransaction.commit();

//        binding.btnArea.setOnClickListener(this::onClick);

        StatusBarUtils.setStatusBarLightMode(mActivity);
    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
//        if(v == binding.btnArea){
//            openNewPage(AreaSelectActivity.class);
//        }
    }
}
