package com.exc.roadlamp.device.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentCabinetDeviceAddBinding;
import com.exc.roadlamp.device.bean.DeviceControlTypeBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.DeviceSystemBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Page(name = "新增集控")
public class DeviceCabinetAddFragment extends MyBaseFragment implements View.OnClickListener{
    private FragmentCabinetDeviceAddBinding binding;
    private List<DeviceControlTypeBean.DataBean> dataBeanList = new ArrayList<>();//设备类型数据
    private List<String> mStreeOption = new ArrayList<>();
    private List<PowerCabinetList.DataBean.RecordsBean> mPowerCabinetList = new ArrayList<>();//配电柜数据
    private List<String> mCabinetOpton = new ArrayList<>();
    private int typeSelectOption = 0;
    private int cabinetSelectOption = 0;
    private int system_id;
    private int cabinet_id;

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.tvCancel){
            popToBack();
        }else if(v == binding.tvConfirm){
            if(binding.tvDeviceName.tvContent.getText().length() ==0){
                XToastUtils.info("请输入设备名称");
                return;
            }

            if(binding.tvDeviceNum.tvContent.getText().length() ==0){
                XToastUtils.info("请输入设备编号");
                return;
            }

            if(binding.tvDeviceMac.tvContent.getText().length() ==0){
                XToastUtils.info("请输入mac地址");
                return;
            }else {
                String macstr = binding.tvDeviceMac.tvContent.getText().toString();
                boolean isMAC = stringIsMac(macstr);
                if (!isMAC) {
                    XToastUtils.info("MAC地址不正确请重新输入");
                    return;
                }
            }

            if(binding.tvDeviceType.tvContent.getText().equals("请选择")){
                XToastUtils.info("请选择设备类型");
                return;
            }

            if(binding.tvDeviceCount.tvContent.getText().equals("请选择")){
                XToastUtils.info("请选择所属配电柜");
                return;
            }

            ControlAdd();
        }else if(v == binding.tvDeviceType.tvContent || v == binding.tvDeviceType.selectMore){
            showStreeConstellationPickerView();
        }else if(v == binding.tvDeviceCount.tvContent || v == binding.tvDeviceCount.selectMore){
            showCabinetConstellationPickerView();
        }
    }

    public boolean stringIsMac(String val) {
        String trueMacAddress = "([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}";
        // 这是真正的MAV地址；正则表达式；

        if (val.matches(trueMacAddress)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void initViews() {
        binding.tvDeviceName.tvTitle.setText("设备名称:");
        binding.tvDeviceNum.tvTitle.setText("设备编号:");
        binding.tvDeviceMac.tvTitle.setText("MAC地址:");
        binding.tvDeviceIp.tvTitle.setText("IP地址:");
        binding.tvDevicePort.tvTitle.setText("端口号:");
        binding.tvDeviceType.tvTitle.setText("设备类型:");
        binding.tvDeviceCount.tvTitle.setText("所属配电柜:");

        binding.tvDeviceName.tvContent.setHint("点击输入");
        binding.tvDeviceNum.tvContent.setHint("点击输入");
        binding.tvDeviceMac.tvContent.setHint("格式:1B-2C-3A-4D-5E-6C");
        binding.tvDeviceIp.tvContent.setHint("点击输入");
        binding.tvDevicePort.tvContent.setHint("点击输入");
        binding.tvDeviceType.tvContent.setText("请选择");
        binding.tvDeviceCount.tvContent.setText("请选择");

        binding.tvDeviceCount.bottomLine.setVisibility(View.GONE);

        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.tvCancel.setOnClickListener(this::onClick);
        binding.tvDeviceType.tvContent.setOnClickListener(this::onClick);
        binding.tvDeviceCount.tvContent.setOnClickListener(this::onClick);
        binding.tvDeviceType.selectMore.setOnClickListener(this::onClick);
        binding.tvDeviceCount.selectMore.setOnClickListener(this::onClick);

        queryLampDevice();
        queryAllPowerCabinet();
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentCabinetDeviceAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    //添加集控
    private void ControlAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("cabinetId", cabinet_id);
            body.put("typeId", system_id);
            body.put("name", binding.tvDeviceName.tvContent.getText());
            body.put("num", binding.tvDeviceNum.tvContent.getText());
            body.put("mac", binding.tvDeviceMac.tvContent.getText());
            body.put("ip", binding.tvDeviceIp.tvContent.getText());
            body.put("port", binding.tvDevicePort.tvContent.getText());
            
//            body.put("ip", "192.168.1.2");//默认
//            body.put("port", "9999");//默认
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpRequest.JSONPOST(mActivity, HttpApi.API_DLM_LOCATION_CONTROL_ADD, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.message);
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_JIKONG_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }
            }
        });
    }

    /**
     * 查询配电柜列表
     */
    private void queryAllPowerCabinet() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 0);
        parameter.put("pageSize", 99999999);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    mPowerCabinetList = powerCabinetList.getData().getRecords();
                    for (PowerCabinetList.DataBean.RecordsBean recordsBean : mPowerCabinetList) {
                        mCabinetOpton.add(recordsBean.getName());
                    }
                }
            }
        });
    }

    /**
     * 查询设备类型
     */
    private void queryLampDevice() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi.API_DLM_LOCATION_CONTROL_TYPE_OPTION, parameter, new BeanResponseListener<DeviceControlTypeBean>() {
            @Override
            public void onResponse(DeviceControlTypeBean deviceSystemBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    List<DeviceControlTypeBean.DataBean> dataBeans = deviceSystemBean.getData();
                    for (DeviceControlTypeBean.DataBean dataBean : dataBeans) {
                        if(dataBean.getType().contains("EXC")){
                            dataBeanList.add(dataBean);
                            mStreeOption.add(dataBean.getType());
                        }
                    }
                }
            }
        });


    }

    //选择设备类型
    private void showStreeConstellationPickerView() {
        typeSelectOption = mStreeOption.size()/2;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.tvDeviceType.tvContent.setText(mStreeOption.get(options1));
            system_id = dataBeanList.get(options1).getId();
            typeSelectOption = options1;

            return false;
        })
                .setTitleText("选择协议类型")
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }

    //选择所属灯杆
    private void showCabinetConstellationPickerView() {
        cabinetSelectOption = mCabinetOpton.size()/2;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.tvDeviceCount.tvContent.setText(mCabinetOpton.get(options1));
            cabinet_id = mPowerCabinetList.get(options1).getId();
            cabinetSelectOption = options1;

            return false;
        })
                .setTitleText("选择配电柜")
                .setSelectOptions(cabinetSelectOption)
                .build();
        pvOptions.setPicker(mCabinetOpton);
        pvOptions.show();
    }
}
