package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampDevDevCommonDetailBinding;
import com.exc.roadlamp.device.bean.LampPoleDevListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;

/**
 * 摄像头详情
 */
public class LampCameraDetailFragment extends MyBaseFragment {
    private FragmentLampDevDevCommonDetailBinding binding;

    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private MapLampCommonDevList.DataBean deviceDetail;
    private ArrayList<LampPoleDevListBean> devTyeList;

    public static final  int deviceType = 4;

    public static LampCameraDetailFragment getInstance(MapLampCommonDevList.DataBean dataBean) {
        LampCameraDetailFragment lampPoleDetailFragment = new LampCameraDetailFragment();
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

    }
    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampDevDevCommonDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}
