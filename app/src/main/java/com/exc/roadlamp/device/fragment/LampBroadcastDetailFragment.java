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
 * 广播详情
 */
public class LampBroadcastDetailFragment extends MyBaseFragment {
    private FragmentLampDevDevCommonDetailBinding binding;

    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private MapLampCommonDevList.DataBean deviceDetail;
    private ArrayList<LampPoleDevListBean> devTyeList;
    private ArrayList<MapLampCommonDevList.DataBean.ChildrenListBean> lampDevList = new ArrayList<>();//灯具列表

    public static final  int deviceType = 3;

    public static LampBroadcastDetailFragment getInstance(MapLampCommonDevList.DataBean dataBean) {
        LampBroadcastDetailFragment lampPoleDetailFragment = new LampBroadcastDetailFragment();
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
