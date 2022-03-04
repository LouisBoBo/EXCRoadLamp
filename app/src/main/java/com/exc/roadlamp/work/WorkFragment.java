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

package com.exc.roadlamp.work;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentHomeWorkBinding;
import com.exc.roadlamp.work.alarm.AlarmFrament;
import com.exc.roadlamp.work.control.ControlManagerList;
import com.exc.roadlamp.work.lamppole.LamppoleManagerList;
import com.exc.roadlamp.work.lightcontrol.LightControlManagerList;
import com.exc.roadlamp.work.strategy.StrategyManagerList;
import com.exc.roadlamp.work.workorder.WorkOrderListFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.display.DensityUtils;
import com.xuexiang.xutil.tip.ToastUtils;

/**
 *
 */
@Page(anim = CoreAnim.none)
public class WorkFragment extends MyBaseFragment implements View.OnClickListener {

    private Activity mActivity;

    private FragmentHomeWorkBinding binding;

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_home_work;
//    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mActivity = getActivity();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, MainActivity.sStatusbarHeight + DensityUtils.dip2px(mActivity, 5), 0, 0);
        binding.tvTitle.setLayoutParams(lp);


    }

    @Override
    protected void initListeners() {

        binding.bordy1.setOnClickListener(this);
        binding.bordy2.setOnClickListener(this);
        binding.bordy3.setOnClickListener(this);
        binding.bordy4.setOnClickListener(this);
        binding.bordy5.setOnClickListener(this);

    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {


        binding = FragmentHomeWorkBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bordy1:
                openNewPage(LightControlManagerList.class);
                break;
            case R.id.bordy2:
                openNewPage(ControlManagerList.class);
                break;
            case R.id.bordy3:
                openNewPage(StrategyManagerList.class);
                break;
            case R.id.bordy4:
                openNewPage(LamppoleManagerList.class);
                break;
            case R.id.bordy5:
                openNewPage(AlarmFrament.class);
                break;
        }
    }

//    @OnClick({R.id.bordy3, R.id.bordy4})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bordy3:
//                ToastUtils.toast("告警");
//                break;
//            case R.id.bordy4:
//                openNewPage(WorkOrderListFragment.class);
//                break;
//        }
//    }
}
