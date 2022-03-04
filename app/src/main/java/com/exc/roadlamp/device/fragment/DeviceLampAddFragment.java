package com.exc.roadlamp.device.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDeviceLightBinding;
import com.exc.roadlamp.databinding.FragmentLampDeviceAddBinding;
import com.exc.roadlamp.device.adapter.LampChildrenListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.DeviceSystemBean;
import com.exc.roadlamp.device.bean.LampCommonDetail;
import com.exc.roadlamp.device.bean.LampDeviceBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.model.AddBingModel;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

@Page(name = "灯具信息")
public class DeviceLampAddFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentLampDeviceAddBinding binding;
    private LampDeviceListBean.DataBean.RecordsBean recordsBean;
    private List<DeviceSystemBean.DataBean> dataBeanList;
    private List<String> mStreeOption = new ArrayList<>();
    private List<String> mLocationOption = new ArrayList<>();

    private int typeSelectOption = 0;
    private int locationSelectOption = 0;
    private String system_id;
    private String looptype;
    private Integer loopnum;
    private String first_str;

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private  List<String> lightids;

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    bindingLight(lightids);
                    break;
            }

            super.handleMessage(msg);
        }
    };

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
            if(binding.tvDeviceName.tvContent.getText().length() ==0){
                XToastUtils.info("请输入编号");
                return;
            }

            if(binding.tvDeviceName.tvContent.getText().length()  != dataBeanList.get(typeSelectOption).getSystemDeviceType().getNumLength()){
                XToastUtils.info("编号长度须在" + dataBeanList.get(typeSelectOption).getSystemDeviceType().getNumLength() + "字");
                return;
            }

            if(binding.tvDeviceNum.tvContent.getText().length() ==0){
                XToastUtils.info("请选择设备类型");
                return;
            }

            if(binding.tvDeviceCount.tvContent.getText().length() ==0 && device_info.id == 0){
                XToastUtils.info("请选择灯杆");
                return;
            }

            LampDeviceAdd();
        }else if(v == binding.tvDeviceNum.tvContent || v == binding.tvDeviceNum.selectMore){
            showStreeConstellationPickerView();
        }else if(v == binding.tvLightLocation1.tvContent || v == binding.tvLightLocation1.selectMore){
            showLocationConstellationPickerView(1);
        }else if(v == binding.tvLightLocation2.tvContent || v == binding.tvLightLocation2.selectMore){
            showLocationConstellationPickerView(2);
        }else if(v == binding.tvLightLocation3.tvContent || v == binding.tvLightLocation3.selectMore){
            showLocationConstellationPickerView(3);
        }
    }

    @Override
    protected void initViews() {

        if(device_info.isqrcode) {
            XQRCode.startScan(this, REQUEST_CODE, 0);//二维码扫描
        }

        if(device_info.id != 0){
            binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
        }

        //1主路 2 辅路 3 氛围灯
        mLocationOption.add("主路");
        mLocationOption.add("辅路");
        mLocationOption.add("氛围灯");

        binding.tvDeviceName.tvTitle.setText("设备编号:");
        binding.tvDeviceNum.tvTitle.setText("协议类型:");

        binding.tvDeviceName.tvContent.setHint("请输入15字");
        binding.tvDeviceNum.tvContent.setHint("请选择");
        binding.tvDeviceCount.tvContent.setHint("请选择");

        binding.tvLightName1.tvTitle.setText("灯具名称1:");
        binding.tvLightName2.tvTitle.setText("灯具名称2:");
        binding.tvLightName3.tvTitle.setText("灯具名称3:");

        binding.tvLightName1.tvContent.setHint("请输入设备名称");
        binding.tvLightName2.tvContent.setHint("请输入设备名称");
        binding.tvLightName3.tvContent.setHint("请输入设备名称");

        binding.tvLightLocation1.tvTitle.setText("灯具位置1:");
        binding.tvLightLocation2.tvTitle.setText("灯具位置2:");
        binding.tvLightLocation3.tvTitle.setText("灯具位置3:");

        binding.tvLightName1.imgMark.setVisibility(View.GONE);
        binding.tvLightName2.imgMark.setVisibility(View.GONE);
        binding.tvLightName3.imgMark.setVisibility(View.GONE);

        binding.tvLightLocation1.imgMark.setVisibility(View.GONE);
        binding.tvLightLocation2.imgMark.setVisibility(View.GONE);
        binding.tvLightLocation3.imgMark.setVisibility(View.GONE);

        binding.tvLightName1.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
        binding.tvLightLocation1.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);

        binding.tvDeviceCount.tvSelect.setOnClickListener(this::onClick);
        binding.tvDeviceNum.tvContent.setOnClickListener(this::onClick);
        binding.tvDeviceNum.selectMore.setOnClickListener(this::onClick);

        binding.tvLightLocation1.tvContent.setOnClickListener(this::onClick);
        binding.tvLightLocation1.selectMore.setOnClickListener(this::onClick);

        binding.tvLightLocation2.tvContent.setOnClickListener(this::onClick);
        binding.tvLightLocation2.selectMore.setOnClickListener(this::onClick);

        binding.tvLightLocation3.tvContent.setOnClickListener(this::onClick);
        binding.tvLightLocation3.selectMore.setOnClickListener(this::onClick);

        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.tvCancel.setOnClickListener(this::onClick);

        EventBus.getDefault().register(this);

        queryLampDevice();
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampDeviceAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public Integer getpositionidFromName(String name){
        int posititonid = 0;
        if(name.contains("主路")){
            posititonid =1;
        }else if(name.contains("辅路")){
            posititonid =2;
        }else if(name.contains("氛围灯")){
            posititonid =3;
        }

        return posititonid;
    }
    //添加灯具
    private void LampDeviceAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {

            body.put("deviceTypeId", system_id);
            body.put("deviceType", binding.tvDeviceNum.tvContent.getText());
            body.put("num", binding.tvDeviceName.tvContent.getText());
            body.put("lampPostId",recordsBean!=null?recordsBean.getId():device_info.id);
            body.put("lampPostNumber", recordsBean!=null?recordsBean.getChildrenList().size() : device_info.childrenList.size());
            body.put("lampPost", recordsBean!=null?recordsBean.getName():device_info.name);

            JSONArray list = new JSONArray();
            if(binding.tvLightLocation1.getRoot().getVisibility() == View.VISIBLE){
                JSONObject body1 = new JSONObject();
                body1.put("lampPositionId",getpositionidFromName(binding.tvLightLocation1.tvContent.getText().toString()));
                body1.put("loopNum",loopnum);
                body1.put("name",binding.tvLightName1.tvContent.getText().toString());
                list.put(body1);
            }

            if(binding.tvLightLocation2.getRoot().getVisibility() == View.VISIBLE){
                JSONObject body1 = new JSONObject();
                body1.put("lampPositionId",getpositionidFromName(binding.tvLightLocation2.tvContent.getText().toString()));
                body1.put("loopNum",loopnum);
                body1.put("name",binding.tvLightName2.tvContent.getText().toString());
                list.put(body1);
            }

            if(binding.tvLightLocation3.getRoot().getVisibility() == View.VISIBLE){
                JSONObject body1 = new JSONObject();
                body1.put("lampPositionId",getpositionidFromName(binding.tvLightLocation3.tvContent.getText().toString()));
                body1.put("loopNum",loopnum);
                body1.put("name",binding.tvLightName3.tvContent.getText().toString());
                list.put(body1);
            }

            body.put("loopParamVOList",list);

        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest.JSONPOST(mActivity, HttpApi.API_SL_LAMP_DEVICE_APPADD, body, new BeanResponseListener<AddBingModel>() {
            @Override
            public void onResponse(AddBingModel mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.getMessage());
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(device_info.islamplist?EventEnum.ADD_DENGGAN_SUCCESS:EventEnum.ADD_DENGJU_SUCCESS);
                    EventBus.getDefault().post(eventMessage);

                    //如果需要绑定则绑定
                    if(device_info.isbinding){
                        if(first_str.contains("PLC")) {
                            lightids = new ArrayList<>();
                            for (Integer datum : mapLampCommonDevList.getData()) {
                                if (datum > 0) {
                                    lightids.add(String.valueOf(datum));
                                }
                            }

                            myHandler.sendEmptyMessage(123);
                        }else {
                            XToastUtils.info("该灯具不可绑定");
                        }
                    }
                }
            }
        });
    }

    //绑定灯具
    private void bindingLight(List<String> ids){
        mMessageLoader.show();
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("locationControlId", device_info.control_id);

        ArrayList array = new ArrayList();
        array.addAll(ids);
        json.put("systemDeviceIds", array);

        HttpRequest.JSONPOST(getActivity(), HttpApi.API_DLM_SYSTEM_DEVICE_PLC_BINDADD, json.toJSONString(), new BeanResponseListener<LampLightListBean>() {
            @Override
            public void onResponse(LampLightListBean bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.CONTROL_BINDING_SUCCESS);
                    EventBus.getDefault().post(eventMessage);

                    XToastUtils.info(bean.getMessage());
                    popToBack();
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
        parameter.put("filed","device_type");
        HttpRequest.GET(mActivity, HttpApi.API_DLM_SYSTEM_AREA_PARAMETER_BYFILED, parameter, new BeanResponseListener<DeviceSystemBean>() {
            @Override
            public void onResponse(DeviceSystemBean deviceSystemBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    dataBeanList = deviceSystemBean.getData();
                    for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                        mStreeOption.add(dataBean.getName());
                    }
                }
            }
        });
    }

    private void showStreeConstellationPickerView() {
        typeSelectOption = mStreeOption.size()/2;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.tvDeviceNum.tvContent.setText(mStreeOption.get(options1));
            system_id = dataBeanList.get(options1).getValue();
            typeSelectOption = options1;
            binding.tvDeviceName.tvContent.setHint("请输入"+dataBeanList.get(options1).getSystemDeviceType().getNumLength()+"字");

            looptype = dataBeanList.get(options1).getSystemDeviceType().getLoopType();
            if(looptype.contains("单回路")){
                loopnum = 1;
                binding.tvLightName1.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation1.getRoot().setVisibility(View.VISIBLE);

                binding.tvLightName2.getRoot().setVisibility(View.GONE);
                binding.tvLightLocation2.getRoot().setVisibility(View.GONE);

                binding.tvLightName3.getRoot().setVisibility(View.GONE);
                binding.tvLightLocation3.getRoot().setVisibility(View.GONE);
            }else if(looptype.contains("双回路")){
                loopnum = 2;

                binding.tvLightName1.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation1.getRoot().setVisibility(View.VISIBLE);

                binding.tvLightName2.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation2.getRoot().setVisibility(View.VISIBLE);

                binding.tvLightName3.getRoot().setVisibility(View.GONE);
                binding.tvLightLocation3.getRoot().setVisibility(View.GONE);
            }else if(looptype.contains("三回路")){
                loopnum = 3;

                binding.tvLightName1.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation1.getRoot().setVisibility(View.VISIBLE);

                binding.tvLightName2.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation2.getRoot().setVisibility(View.VISIBLE);

                binding.tvLightName3.getRoot().setVisibility(device_info.isqrcode?View.GONE:View.VISIBLE);
                binding.tvLightLocation3.getRoot().setVisibility(View.VISIBLE);
            }

            return false;
        })
                .setTitleText("选择协议类型")
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }

    private void showLocationConstellationPickerView(Integer index){
        locationSelectOption = mLocationOption.size()/2;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {

            switch (index){
                case 1:
                    binding.tvLightLocation1.tvContent.setText(mLocationOption.get(options1));
                    break;
                case 2:
                    binding.tvLightLocation2.tvContent.setText(mLocationOption.get(options1));
                    break;
                case 3:
                    binding.tvLightLocation3.tvContent.setText(mLocationOption.get(options1));
                    break;
            }
            locationSelectOption = options1;

            return false;
        })
                .setTitleText("选择协议类型")
                .setSelectOptions(locationSelectOption)
                .build();
        pvOptions.setPicker(mLocationOption);
        pvOptions.show();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            handleScanResult(data);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            getActivity().finish();
        }
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i("测试用",data.toString());
            //处理扫描结果（在界面上显示）
            handleScanResult(data);
        }
    }

    /**
     * 处理二维码扫描结果
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    Log.i("测试用",result);
                    if(result != ""){
                        String[] arr = result.split("_");
                        first_str = arr[0];
                        if(arr.length >3) {
                            if (arr.length > 1 && arr[1].length() > 0) {
                                device_info.num = arr[1];
                            }
                            if (arr.length > 3 && arr[3].length() > 0) {
                                device_info.model = arr[3];
                            }
                        }else if(arr.length >2){
                            if (arr.length > 1 && arr[1].length() > 0) {
                                device_info.num = arr[1];
                            }
                        }
                    }

                    if(device_info.num != null){//扫二维码填充数据
                        binding.tvDeviceName.tvContent.setText(device_info.num);
                    }

                    if(device_info.model != null){
                        for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                            if(dataBean.getName().contains(device_info.model)){
                                system_id = dataBean.getValue();
                                binding.tvDeviceNum.tvContent.setText(device_info.model);
                                break;
                            }
                        }

                        if (system_id == null) {
                            for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                                if (device_info.model.equals("EXC-TL1-N110E-2") && dataBean.getName().equals("EXC-TL1-N110E-STQ-Ⅱ")) {
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-N110E-3") && dataBean.getName().equals("EXC-TL1-N110E-SMO-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-N210E-2") && dataBean.getName().equals("EXC-TL1-N210E-STQ-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-N210E-3") && dataBean.getName().equals("EXC-TL1-N210E-SMO-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-L110E-2") && dataBean.getName().equals("EXC-TL1-L110E-0")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-L210E-2") && dataBean.getName().equals("EXC-TL1-L210E-0")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-C110E-2") && dataBean.getName().equals("EXC-TL1-C110E-SWQ-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-C210E-2") && dataBean.getName().equals("EXC-TL1-C210E-SWQ-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-C310E-2") && dataBean.getName().equals("EXC-TL1-C21ME-FWQ-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }else if(device_info.model.equals("EXC-TL1-C110E-TFLX") && dataBean.getName().equals("EXC-TL1-C11ME-FWQ-Ⅱ")){
                                    system_id = dataBean.getValue();
                                    binding.tvDeviceNum.tvContent.setText(dataBean.getName());
                                    break;
                                }
                            }
                        }
                    }

                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    XToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
                }
            }
        }

    }
}
