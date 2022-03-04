package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentCilckLampLightDetailBinding;
import com.exc.roadlamp.device.bean.LampPoleDevListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *  灯具详情（单个）
 */
public class LampLightDetailFragment extends MyBaseFragment {
    private FragmentCilckLampLightDetailBinding binding;

    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private MapLampCommonDevList.DataBean deviceDetail;
    private LampLightDetailLightListFragment lampLightDetailLightListFragment;//灯具详情
    public static LampLightDetailFragment getInstance(MapLampCommonDevList.DataBean dataBean) {
        LampLightDetailFragment lampPoleDetailFragment = new LampLightDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, dataBean);
        lampPoleDetailFragment.setArguments(args);
        return lampPoleDetailFragment;

    }


    @Override
    protected TitleBar initTitle() {
        return null;
    }


    @Override
    protected void initViews() {
        initListData();
    }

    private void initListData() {
        deviceDetail = (MapLampCommonDevList.DataBean) getArguments().getSerializable(DATA_KEY);

//        binding.recycler.setLayoutManager(new GridLayoutManager(mActivity, 3));
//        binding.recycler.setHasFixedSize(true);
//        binding.recycler.setAdapter(new TestRecyclerViewAdapter(mActivity));
        initDevList();

    }

    /**
     * 填充设备列表
     */
    private void initDevList() {

        if(deviceDetail.getChildrenList().size()>1){
            binding.tvLampLightName.setText(deviceDetail.name + "挂载的灯具");
            binding.tvLampPoleName.setVisibility(View.GONE);
            binding.tvLampPoleNum.setText(deviceDetail.lampPostNum);
        }else if(deviceDetail.getChildrenList().size() == 1) {
            binding.tvLampLightName.setText(deviceDetail.getChildrenList().get(0).getName());
            binding.tvLampPoleName.setVisibility(View.VISIBLE);
            binding.tvLampPoleName.setText("所属灯杆：" +deviceDetail.name);
            binding.tvLampPoleNum.setText(deviceDetail.getChildrenList().get(0).getNum());
        }

        binding.tvLampPoleStreet.setText(deviceDetail.streetName);
        binding.tvLampPoleInstallPosition.setText(deviceDetail.names);
        binding.tvLampPoleArea.setText(deviceDetail.areaName);
        binding.tvLampPoleSite.setText(deviceDetail.superName);

        FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        lampLightDetailLightListFragment = LampLightDetailLightListFragment.getInstance(deviceDetail);
        beginTransaction.add(binding.flDevView.getId(), lampLightDetailLightListFragment);
        beginTransaction.commitAllowingStateLoss();
    }




    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentCilckLampLightDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private FlexboxLayoutManager getFlexboxLayoutManager() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mActivity);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }


    private ArrayList<LampPoleDevListBean> createDevListData() {
        ArrayList<LampPoleDevListBean> tempDevTyeList = new ArrayList<>();


        if (null == deviceDetail.childrenList) {
            return tempDevTyeList;
        }


        for (int i = 0; i < deviceDetail.childrenList.size(); i++) {


            LampPoleDevListBean lampPoleDevListBean = new LampPoleDevListBean();


            if (deviceDetail.childrenList.get(i).getDeviceType() == LampLightDetailLightListFragment.deviceType) {
                lampPoleDevListBean.setName("灯具");
                lampPoleDevListBean.setDeviceType(LampLightDetailLightListFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;
            }

            if (deviceDetail.childrenList.get(i).getDeviceType() == LampAPDetailFragment.deviceType) {
                lampPoleDevListBean.setName("AP");
                lampPoleDevListBean.setDeviceType(LampAPDetailFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;
            }

            if (deviceDetail.childrenList.get(i).getDeviceType() == LampBroadcastDetailFragment.deviceType) {
                lampPoleDevListBean.setName("广播");
                lampPoleDevListBean.setDeviceType(LampBroadcastDetailFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;
            }

            if (deviceDetail.childrenList.get(i).getDeviceType() == LampCameraDetailFragment.deviceType) {
                lampPoleDevListBean.setName("摄像头");
                lampPoleDevListBean.setDeviceType(LampCameraDetailFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;

            }

            if (deviceDetail.childrenList.get(i).getDeviceType() == LampDisplayDetailFragment.deviceType) {
                lampPoleDevListBean.setName("显示屏");
                lampPoleDevListBean.setDeviceType(LampDisplayDetailFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;

            }
            if (deviceDetail.childrenList.get(i).getDeviceType() == LampSensorDetailFragment.deviceType) {
                lampPoleDevListBean.setName("传感器");
                lampPoleDevListBean.setDeviceType(LampSensorDetailFragment.deviceType);
                tempDevTyeList.add(lampPoleDevListBean);
                continue;

            }

        }
        ArrayList<LampPoleDevListBean> list = (ArrayList<LampPoleDevListBean>) tempDevTyeList.stream().distinct().collect(Collectors.toList());
        return list;
    }
}
