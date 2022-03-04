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

package com.exc.roadlamp.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.constraintlayout.solver.state.State;

import com.bumptech.glide.Glide;
import com.exc.roadlamp.Constant;
import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentChangePwdBinding;
import com.exc.roadlamp.databinding.FragmentHomeWorkBinding;
import com.exc.roadlamp.databinding.FragmentMyBinding;
import com.exc.roadlamp.databinding.FragmentUpgradeBinding;
import com.exc.roadlamp.fragment.LoginFragment;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.my.UserModel.UserModel;
import com.exc.roadlamp.utils.PreferencesUtil;
import com.exc.roadlamp.work.alarm.AlarmDetailFragment;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.display.DensityUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Call;

/**
 * @author xuexiang
 * @since 2019-10-30 00:19
 */
@Page(anim = CoreAnim.none)
public class MyFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentMyBinding binding;
    private UserModel.DataBean dataBean;
    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserModel.DataBean usermodel){
        binding.tvName.setText(usermodel.getAccountName());
        binding.tvMember.setText(usermodel.getAreaName() + "  " + usermodel.getRoleName());
    }

    /**
     * 初始化控件
     */
    @SuppressLint("ResourceAsColor")
    @Override
    protected void initViews() {
        mActivity = getActivity();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, MainActivity.sStatusbarHeight + DensityUtils.dip2px(mActivity, 5), 0, 0);
        binding.tvTitle.setLayoutParams(lp);

        EventBus.getDefault().register(this);

        binding.userinfoView.imgTitle.setImageResource(R.mipmap.my_home_icon_me);
        binding.pwdView.imgTitle.setImageResource(R.mipmap.my_home_icon_password);
        binding.settingView.imgTitle.setImageResource(R.mipmap.icon_shouye_setting);
        binding.aboutView.imgTitle.setImageResource(R.mipmap.my_home_icon_about);
        binding.upgradeView.imgTitle.setImageResource(R.mipmap.my_home_icon_about);
        binding.loginoutView.imgTitle.setImageResource(R.mipmap.icon_loginout);

        binding.userinfoView.tvContent.setText("个人信息");
        binding.pwdView.tvContent.setText("修改密码");
        binding.settingView.tvContent.setText("首页配置");
        binding.aboutView.tvContent.setText("关于我们");
        binding.upgradeView.tvContent.setText("系统升级");
        binding.loginoutView.tvContent.setText("退出登录");

        binding.userinfoView.itemView.setOnClickListener(this::onClick);
        binding.pwdView.itemView.setOnClickListener(this::onClick);
        binding.settingView.itemView.setOnClickListener(this::onClick);
        binding.aboutView.itemView.setOnClickListener(this::onClick);
        binding.upgradeView.itemView.setOnClickListener(this::onClick);
        binding.loginoutView.itemView.setOnClickListener(this::onClick);
//        binding.loginoutBtn.setOnClickListener(this::onClick);

        //设置沉浸式状态栏
        StatusBarUtils.translucent(mActivity);
        StatusBarUtils.setStatusBarLightMode(mActivity);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {

        binding = FragmentMyBinding.inflate(inflater, container, false);
        initData();
        return binding.getRoot();
    }

    public void initData(){
        Parameter parameter = new Parameter();
        int user_id = PreferencesUtil.getInt(getActivity(), Constant.USER_ID);
        HttpRequest.GET(getActivity(), HttpApi.API_UA_USER_ID  + String.valueOf(user_id) , parameter, new BeanResponseListener<UserModel>() {
            @Override
            public void onResponse(UserModel model, Exception error) {
                if(model !=null && model.getCode() == 200){
                    dataBean = model.getData();
                    binding.tvName.setText(dataBean.getName());
                    binding.tvMember.setText(dataBean.getAreaName() + "  " + (dataBean.getRoles().size()>0?dataBean.getRoles().get(0).getName():""));

                    if(dataBean.getHeadPicVO().getName() != null) {
                        Glide.with(mActivity).load(HttpApi.SERVICES_ADDRESS + HttpApi.SERVICES_PORT + "/" + dataBean.getHeadPicVO().getName()).into(binding.userPhonto);
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Log.i("sfaf","点击了");

        if(v == binding.userinfoView.itemView){
            openNewPage(UserinfoFragment.class,"user_info",dataBean);
        }else if(v == binding.pwdView.itemView){
            openNewPage(ChangePwdFragment.class);
        }else if(v == binding.settingView.itemView){
            openNewPage(SettingFragment.class);
        }else if(v == binding.aboutView.itemView){
            openNewPage(AboutUsFragment.class);
        }else if(v == binding.upgradeView.itemView){
            openNewPage(UpGradeFragment.class);
        }else if(v == binding.loginoutView.itemView){
            PreferencesUtil.putString(getActivity(), Constant.USER_ID, "");
            PreferencesUtil.putString(getActivity(), Constant.USER_TOKEN, "");

            openNewPage(LoginFragment.class);
            mActivity.finish();
        }
    }
}
