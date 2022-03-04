package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampLightDetailBinding;
import com.exc.roadlamp.device.adapter.LampLightDetailListAdapter;
import com.exc.roadlamp.device.bean.DeviceControlResult;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.JsonMap;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;


/**
 * 灯具详情-列表
 */
public class LampLightDetailLightListFragment extends MyBaseFragment {
    private FragmentLampLightDetailBinding binding;
    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private MapLampCommonDevList.DataBean deviceDetail;
    public static final int deviceType = 1;
    private ArrayList<MapLampCommonDevList.DataBean.ChildrenListBean> dataList = new ArrayList<>();
    private LampLightDetailListAdapter listAdapter;

    public static LampLightDetailLightListFragment getInstance(MapLampCommonDevList.DataBean dataBean) {
        LampLightDetailLightListFragment fragment = new LampLightDetailLightListFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, dataBean);
        fragment.setArguments(args);
        return fragment;
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
        initDevList();

    }

    /**
     * 填充灯具列表
     */
    private void initDevList() {
        for (MapLampCommonDevList.DataBean.ChildrenListBean children : deviceDetail.getChildrenList()) {
            if (children.getDeviceType() == deviceType) {
                dataList.add(children);
            }
        }
        WidgetUtils.initRecyclerView(binding.recyclerView, 0);
        listAdapter = new LampLightDetailListAdapter();
        binding.recyclerView.setAdapter(listAdapter);
        listAdapter.setList(dataList);

        listAdapter.setOnItemViewChangeListener((changeDevBean, position, progress, deviceState) -> {
            mMessageLoader.show();
            JsonMap jsonMap = new JsonMap();
            ArrayList<Integer> lampDeviceIdList = new ArrayList<>();
            lampDeviceIdList.add(changeDevBean.getId());
            jsonMap.put("lampDeviceIdList", lampDeviceIdList);
            jsonMap.put("lightness", progress);
            jsonMap.put("type", deviceState);

            HttpRequest.JSONPOST(mActivity, HttpApi1.DEVICE_CONTROL, jsonMap, new BeanResponseListener<DeviceControlResult>() {
                @Override
                public void onResponse(DeviceControlResult controlResult, Exception error) {
                    mMessageLoader.dismiss();
                    if (error == null) {
                        if (controlResult.getCode() != 200) {
                            ToastUtils.toast(controlResult.message);
                            return;
                        }
//                        if(controlResult.getData().getSuccessNum() == 0){
//                            XToastUtils.info("下发失败");
//                        }else
//                            XToastUtils.success("下发成功，" + controlResult.getData().getSuccessNum() + "个设备修改成功！");
                        XToastUtils.info(controlResult.getMessage());
                        if (controlResult.getData().getSuccessNum() > 0) {
                            //手动刷新数据
                            dataList.get(position).getDlmRespDevice().setBrightness(progress);
                            dataList.get(position).getDlmRespDevice().setDeviceState(deviceState);
                            listAdapter.setList(dataList);
                        }
                    }


                }
            });
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampLightDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}
