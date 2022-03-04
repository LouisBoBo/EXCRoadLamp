package com.exc.roadlamp.work.lightcontrol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampDeviceAddBinding;
import com.exc.roadlamp.device.bean.DeviceAddBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.fragment.DeviceBindingFragment;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

@Page(name = "编辑灯具")
public class LightEditManager extends MyBaseFragment implements View.OnClickListener{

    private FragmentLampDeviceAddBinding binding;
    private LampDeviceListBean.DataBean.RecordsBean recordsBean;

    @AutoWired(name = "device_info")
    LightInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LampDeviceListBean.DataBean.RecordsBean bean){
        recordsBean = bean;
        binding.tvDeviceCount.tvContent.setText(recordsBean.getName());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvDeviceCount.tvSelect){
            openNewPage(DeviceBindingFragment.class);
        }else if(v == binding.tvCancel){
            popToBack();
        }else if(v == binding.tvConfirm){
            lightUnique();
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        binding.tvDeviceNum.getRoot().setVisibility(View.GONE);
        binding.tvLightName1.getRoot().setVisibility(View.GONE);
        binding.tvLightName2.getRoot().setVisibility(View.GONE);
        binding.tvLightLocation1.getRoot().setVisibility(View.GONE);
        binding.tvLightLocation2.getRoot().setVisibility(View.GONE);

        binding.tvDeviceName.tvTitle.setText("灯具名称");
        binding.tvDeviceCount.tvTitle.setText("绑定灯杆");
        binding.tvDeviceName.tvContent.setText(device_info.name);
        binding.tvDeviceCount.tvContent.setText(device_info.lampPostName);

        binding.tvDeviceCount.tvSelect.setOnClickListener(this::onClick);
        binding.tvCancel.setOnClickListener(this::onClick);
        binding.tvConfirm.setOnClickListener(this::onClick);
    }


    /**
     * 灯控验证
     */
    private void lightUnique() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("num", device_info.num);
            body.put("id", device_info.id);
            body.put("lampPostId", recordsBean.getId());
            body.put("deviceTypeId", device_info.deviceTypeId);

            JSONArray list = new JSONArray();
            JSONObject body1 = new JSONObject();
            body1.put("lampPositionId", device_info.lampPositionId);
            body1.put("LampsYi", device_info.lampPosition);
            body1.put("name",device_info.name);
            list.put(body1);

            body.put("loopParamVOList", list);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpRequest.JSONPOST(mActivity, HttpApi.API_SL_LAMP_DEVICE_UNIQUE, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    lightUpdate();
                }
            }
        });
    }

    /**
     * 灯控修改
     */
    private void lightUpdate() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("num", device_info.num);
            body.put("id", device_info.id);
            body.put("lampPostId", recordsBean.getId());
            body.put("deviceTypeId", device_info.deviceTypeId);

            JSONArray list = new JSONArray();
            JSONObject body1 = new JSONObject();
            body1.put("lampPositionId", device_info.lampPositionId);
            body1.put("LampsYi", device_info.lampPosition);
            body1.put("name",device_info.name);
            list.put(body1);

            body.put("loopParamVOList", list);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpRequest.JSONPOST(mActivity, HttpApi.API_SL_LAMP_DEVICE_UPDATE, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.message);
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.CHANGE_LIGHT_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }
            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampDeviceAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
