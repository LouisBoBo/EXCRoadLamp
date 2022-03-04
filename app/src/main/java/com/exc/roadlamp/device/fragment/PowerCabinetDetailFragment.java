package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentCilckPowerCabinetDetailBinding;
import com.exc.roadlamp.device.adapter.PowerCabinetCenterControlAdapter;
import com.exc.roadlamp.device.bean.PowerCabinetDetail;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

/**
 * 配电柜详情
 */
public class PowerCabinetDetailFragment extends MyBaseFragment {
    private FragmentCilckPowerCabinetDetailBinding binding;

    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private PowerCabinetDetail deviceDetail;
    private PowerCabinetCenterControlAdapter adapter;
    public static final int deviceType = 102;

    public static PowerCabinetDetailFragment getInstance(PowerCabinetDetail powerCabinetDetailData) {
        PowerCabinetDetailFragment powerCabinetDetailFragment = new PowerCabinetDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, powerCabinetDetailData);
        powerCabinetDetailFragment.setArguments(args);
        return powerCabinetDetailFragment;

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
        deviceDetail = (PowerCabinetDetail) getArguments().getSerializable(DATA_KEY);

        initTopView();
        initDevList();

    }

    private void initTopView() {
        binding.tvName.setText(deviceDetail.data.name);
        if (deviceDetail.data.controlOfCabinetVOList == null) {
            binding.tvCenterControlCount.setText("管理集控数：" + 0);
        } else {
            binding.tvCenterControlCount.setText("管理集控数：" + deviceDetail.data.controlOfCabinetVOList.size());
        }


        binding.tvArea.setText("所属区域：" + deviceDetail.data.areaName);
        binding.tvInstallPosition.setText("安装位置：" + deviceDetail.data.location);
        binding.tvNum.setText(deviceDetail.data.num);
        binding.tvProject.setText("所属项目：" + deviceDetail.data.areaName);

        if(deviceDetail.data.lastOnlineTime != null) {
            binding.tvPowerWatchUpdateTime.setText(deviceDetail.data.lastOnlineTime);
        }

    }

    /**
     * 填充设备列表(集控)
     */
    private void initDevList() {

        if (deviceDetail.data.controlOfCabinetVOList == null || deviceDetail.data.controlOfCabinetVOList.size() == 0) {
            binding.recyclerView.setVisibility(View.GONE);
            return;
        }

        WidgetUtils.initRecyclerView(binding.recyclerView, 0);
        adapter = new PowerCabinetCenterControlAdapter();
        binding.recyclerView.setAdapter(adapter);
        PowerCabinetDetail.DataBean.ControlOfCabinetVOListBean titleBean = new PowerCabinetDetail.DataBean.ControlOfCabinetVOListBean();
//        deviceDetail.data.controlOfCabinetVOList.add(0, titleBean);
        adapter.setList(deviceDetail.data.controlOfCabinetVOList);


    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentCilckPowerCabinetDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}
