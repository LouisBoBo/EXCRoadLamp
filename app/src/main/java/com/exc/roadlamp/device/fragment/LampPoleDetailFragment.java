package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampPoleDetailBinding;
import com.exc.roadlamp.device.adapter.LampPoleDetailDevNameListAdapter;
import com.exc.roadlamp.device.bean.LampPoleDevListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.test.TestRecyclerViewAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 灯杆详情
 */
public class LampPoleDetailFragment extends MyBaseFragment {
    private FragmentLampPoleDetailBinding binding;

    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private MapLampCommonDevList.DataBean deviceDetail;
    private ArrayList<LampPoleDevListBean> devTyeList;  //设备类型列表
    private LampLightDetailLightListFragment lampLightDetailLightListFragment;//灯具详情
    private LampCameraDetailFragment lampCameraDetailFragment;//其他详情（暂用）
    public static final int deviceType = 0;


    public static LampPoleDetailFragment getInstance(MapLampCommonDevList.DataBean dataBean) {
        LampPoleDetailFragment lampPoleDetailFragment = new LampPoleDetailFragment();
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


//        binding.recyclerViewDevList.setLayoutManager(new GridLayoutManager(mActivity, 3));
//        binding.recyclerViewDevList.setHasFixedSize(true);
//        binding.recyclerViewDevList.setAdapter(new TestRecyclerViewAdapter(mActivity));
        initDevList();

    }

    /**
     * 填充设备列表
     */
    private void initDevList() {


        binding.tvLampPoleName.setText(deviceDetail.name);
        binding.tvLampPoleNum.setText( deviceDetail.lampPostNum);
        binding.tvLampPoleStreet.setText(deviceDetail.streetName);
        binding.tvLampPoleInstallPosition.setText(deviceDetail.names);
        binding.tvLampPoleArea.setText(deviceDetail.areaName);
        binding.tvLampPoleSite.setText(deviceDetail.superName);


        devTyeList = createDevListData();
        binding.recyclerViewDevList.setLayoutManager(getFlexboxLayoutManager());
        LampPoleDetailDevNameListAdapter lampPoleDetailDevNameListAdapter = new LampPoleDetailDevNameListAdapter(devTyeList);
        binding.recyclerViewDevList.setAdapter(lampPoleDetailDevNameListAdapter);
        lampPoleDetailDevNameListAdapter.select(0);
        lampPoleDetailDevNameListAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (lampPoleDetailDevNameListAdapter.select(position)) {
                initDevViewData(item.getDeviceType());

            }
        });
        if(devTyeList.size() >0){
            initDevViewData(devTyeList.get(0).getDeviceType());
        }
    }

    private void initDevViewData(int deviceType) {
        FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        if (lampLightDetailLightListFragment != null) {
            beginTransaction.hide(lampLightDetailLightListFragment);
        }
        if (lampCameraDetailFragment != null) {
            beginTransaction.hide(lampCameraDetailFragment);
        }
        switch (deviceType) {
            case 1://灯具
                if (lampLightDetailLightListFragment == null) {
                    lampLightDetailLightListFragment = LampLightDetailLightListFragment.getInstance(deviceDetail);
                    beginTransaction.add(binding.flDevView.getId(), lampLightDetailLightListFragment);
                } else {
                    beginTransaction.show(lampLightDetailLightListFragment);
                }
                break;
            default://其他先不做
                if (lampCameraDetailFragment == null) {
                    lampCameraDetailFragment = LampCameraDetailFragment.getInstance(deviceDetail);
                    beginTransaction.add(binding.flDevView.getId(), lampCameraDetailFragment);
                } else {
                    beginTransaction.show(lampCameraDetailFragment);
                }
                break;
        }
        beginTransaction.commitAllowingStateLoss();

    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampPoleDetailBinding.inflate(inflater, container, false);
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
