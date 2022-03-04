package com.exc.roadlamp.work.strategy;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentStrategyAddBinding;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceSystemBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.adapter.AddStrategyAdapter;
import com.exc.roadlamp.work.adapter.LightstrategyAdapter;
import com.exc.roadlamp.work.lightcontrol.LightStrategyManagerList;
import com.exc.roadlamp.work.model.ActionOptionModel;
import com.exc.roadlamp.work.model.LightStrategyModel;
import com.exc.roadlamp.work.model.LightStrategyTypeModel;
import com.exc.roadlamp.work.model.StrategyActionModel;
import com.exc.roadlamp.work.model.StrategyTypeModel;
import com.google.gson.JsonObject;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "新增策略")
public class StrategyManagerAdd extends MyBaseFragment implements View.OnClickListener{
    private FragmentStrategyAddBinding binding;
    private AddStrategyAdapter itemAdapter;
    private List<String> mStreeOption = new ArrayList<>();
    private List<String> mTypeOption = new ArrayList<>();
    private List<DeviceSystemBean.DataBean> dataBeanList = new ArrayList<>();
    private List<LightStrategyTypeModel.DataBean> typeDataBeanList = new ArrayList<>();
    private int typeSelectOption = 0;
    private List<StrategyActionModel> recordsBeanList = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 30;
    @AutoWired(name = "strategy_type")
    StrategyTypeModel strategyTypeModel;
    StrategyActionModel strategyActionModel;

    private int strategyTypeid;//策略类型id
    private int deviceTypeid;//协议类型id

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(StrategyActionModel model) {
        binding.tvTips.setVisibility(View.GONE);
        strategyActionModel = model;
        strategyActionModel.setName(binding.tvDeviceName.tvContent.getText().toString());
        recordsBeanList.add(model);
        itemAdapter.setmDatas(recordsBeanList);
        binding.tvConfirm.setVisibility(model!=null?View.VISIBLE:View.GONE);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.TextAction("新增动作") {
            @Override
            public void performAction(View view) {
                openNewPage(StrategyActionAdd.class, "strategy_type", strategyTypeModel);
            }
        });
        return titleBar;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.tvAction1.tvContent || v == binding.tvAction1.selectMore){
            showStreeConstellationPickerView(1,binding.tvAction1.tvContent);
        }else if(v == binding.tvAction2.tvContent || v == binding.tvAction2.selectMore){
            showStreeConstellationPickerView(2,binding.tvAction2.tvContent);
        }else if(v == binding.tvTips){
            openNewPage(StrategyActionAdd.class, "strategy_type", strategyTypeModel);
        }else if(v == binding.tvConfirm){
            if(strategyTypeModel.strategy_type == 1){
                addStragegy();
            }else {
                addLightStragegy();
            }

        }
    }

    @Override
    protected void initViews() {

        EventBus.getDefault().register(this);

        //设备列表
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new AddStrategyAdapter(getActivity());
        binding.recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClick(new AddStrategyAdapter.OnItemClick() {
            @Override
            public void click(int index) {

            }
        });

        itemAdapter.setOnDeleateClick(new AddStrategyAdapter.OnDeleateClick() {
            @Override
            public void click(int index) {
//                strategyActionModel.getDlmReqSceneActionVOList().remove(index);
                recordsBeanList.remove(index);
                itemAdapter.setmDatas(recordsBeanList);

                binding.tvTips.setVisibility(strategyActionModel.getDlmReqSceneActionVOList().size()>0?View.GONE:View.VISIBLE);
                binding.tvConfirm.setVisibility(strategyActionModel.getDlmReqSceneActionVOList().size()>0?View.VISIBLE:View.GONE);
            }
        });

        queryStrategyType();
        queryLampDevice();


        binding.tvDeviceName.tvTitle.setText("策略名称");
        binding.tvDeviceName.tvContent.setHint("请输入");
        binding.tvAction1.tvTitle.setText("策略类型");
        binding.tvAction2.tvTitle.setText("协议类型");
        binding.tvAction2.bottomLine.setVisibility(View.GONE);

        if(strategyTypeModel.strategy_type ==1){
            binding.tvDeviceName.tvTitle.setText("场景名称");
            binding.tvAction1.bottomLine.setVisibility(View.GONE);
            binding.tvAction1.getRoot().setVisibility(View.GONE);
            binding.tvAction2.getRoot().setVisibility(View.GONE);
        }

        binding.tvAction1.tvContent.setOnClickListener(this::onClick);
        binding.tvAction2.tvContent.setOnClickListener(this::onClick);
        binding.tvAction1.selectMore.setOnClickListener(this::onClick);
        binding.tvAction2.selectMore.setOnClickListener(this::onClick);
        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.tvTips.setOnClickListener(this::onClick);
    }

    //新增场景策略
    public void addStragegy(){
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        JSONArray list = new JSONArray();
        for (StrategyActionModel model : recordsBeanList) {
            StrategyActionModel.DlmReqSceneActionVOListBean strategyActionModel = model.getDlmReqSceneActionVOList().get(0);
            JSONObject json = new JSONObject();
            json.put("isOpen", strategyActionModel.getIsOpen());
            json.put("executionTime", strategyActionModel.getExecutionTime());
            json.put("startDate",strategyActionModel.getStartDate());
            json.put("endDate",strategyActionModel.getEndDate());
            json.put("cycleTypes", strategyActionModel.getCycleTypes());
            json.put("id",strategyActionModel.getId());

            list.add(json);
        }

        body.put("idSynchro",0);
        body.put("isAtmosphere",0);
        body.put("dlmReqSceneActionVOList",list);
        body.put("name",  binding.tvDeviceName.tvContent.getText().toString());

        String url = HttpApi.API_DLM_LOOP_SCENE_STRATEGY ;
        HttpRequest.JSONPOST(getActivity(),url, body.toString(), new BeanResponseListener<LightStrategyModel>() {
            @Override
            public void onResponse(LightStrategyModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    XToastUtils.info(bean.getMessage());
                    popToBack();
                    EventMessage<DevMapSearchResultListBean> eventMessage = new EventMessage<>();
                    eventMessage.setEventEnum(strategyTypeModel.strategy_type==1?EventEnum.STRATEGY_ADD_SUCCESS:EventEnum.LIGHTSTRATEGY_ADD_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                }
            }
        });
    }

    //新增灯具策略
    public void addLightStragegy(){
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        JSONArray list = new JSONArray();

        List<String> devicetypeids = new ArrayList<>();
        devicetypeids.add(String.valueOf(deviceTypeid));

        for (StrategyActionModel model : recordsBeanList) {
            StrategyActionModel.DlmReqSceneActionVOListBean strategyActionModel = model.getDlmReqSceneActionVOList().get(0);
            JSONObject json = new JSONObject();
            json.put("isOpen", strategyActionModel.getIsOpen());
            json.put("executionTime", strategyActionModel.getExecutionTime());
            json.put("startDate",strategyActionModel.getStartDate());
            json.put("endDate",strategyActionModel.getEndDate());
            json.put("cycleTypes", strategyActionModel.getCycleTypes());
            json.put("id",strategyActionModel.getId());
            json.put("brightness",strategyActionModel.getProgress());
            json.put("deviceTypeIdOfActionList",devicetypeids);

            if(strategyActionModel.getLightModeId() >0){
                json.put("lightModeId",strategyActionModel.getLightModeId());
            }
            list.add(json);
        }

        body.put("idSynchro",0);
        body.put("isAtmosphere",0);
        body.put("slReqStrategyActionVOList",list);
        body.put("name",  binding.tvDeviceName.tvContent.getText().toString());
        body.put("strategyTypeId",strategyTypeid);
        body.put("deviceTypeIdOfStrategyList",devicetypeids);

        String url = HttpApi.API_SL_LAMP_STRATEGY ;
        HttpRequest.JSONPOST(getActivity(),url, body.toString(), new BeanResponseListener<LightStrategyModel>() {
            @Override
            public void onResponse(LightStrategyModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    XToastUtils.info(bean.getMessage());
                    popToBack();

                    EventMessage<DevMapSearchResultListBean> eventMessage = new EventMessage<>();
                    eventMessage.setEventEnum(strategyTypeModel.strategy_type==1?EventEnum.STRATEGY_ADD_SUCCESS:EventEnum.LIGHTSTRATEGY_ADD_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
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

    /**
     * 查询策略类型
     */
    private void queryStrategyType(){
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_SL_LAMP_STRATEGY_TYPE_PULLDOWN, parameter, new BeanResponseListener<LightStrategyTypeModel>() {
            @Override
            public void onResponse(LightStrategyTypeModel deviceSystemBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    typeDataBeanList = deviceSystemBean.getData();
                    for (LightStrategyTypeModel.DataBean dataBean : typeDataBeanList) {
                        mTypeOption.add(dataBean.getName());
                    }
                }
            }
        });
    }
    private void showStreeConstellationPickerView(int index, TextView type) {
        typeSelectOption = index==1?(mTypeOption.size()/2):(mStreeOption.size()/2);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            type.setText(index ==1?mTypeOption.get(options1):mStreeOption.get(options1));
            if(index ==1){
                for (LightStrategyTypeModel.DataBean dataBean : typeDataBeanList) {
                    if(dataBean.getName().contains(mTypeOption.get(options1))){
                        strategyTypeid = dataBean.getId();
                        break;
                    }
                }
            }else {
                for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                    if(dataBean.getName().contains(mStreeOption.get(options1))){
                        deviceTypeid = dataBean.getId();
                        break;
                    }
                }
            }
            typeSelectOption = options1;

            return false;
        })
                .setTitleText(index ==1?"选择策略":"选择协议类型")
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(index==1?mTypeOption:mStreeOption);
        pvOptions.show();
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentStrategyAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
