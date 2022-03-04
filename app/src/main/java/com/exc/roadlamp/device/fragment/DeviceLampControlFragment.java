package com.exc.roadlamp.device.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampListBinding;
import com.exc.roadlamp.device.adapter.LampChildrenListBean;
import com.exc.roadlamp.device.adapter.LampDeviceControlAdapter;
import com.exc.roadlamp.device.adapter.LampDeviceSelectAdapter;
import com.exc.roadlamp.device.adapter.LampPoleControlAdapter;
import com.exc.roadlamp.device.bean.DeviceControlResult;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampCommonDetail;
import com.exc.roadlamp.device.bean.LampDeviceBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampVideoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.google.gson.JsonObject;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import static android.app.Activity.RESULT_OK;
@Page(name = "设备列表")
public class DeviceLampControlFragment extends MyBaseFragment implements View.OnClickListener{
    private FragmentLampListBinding binding;
    private TitleBar.ImageAction brushimageAction;
    private TitleBar titleBar;
    private LampDeviceBean.DataBean dataBean;
    private LampVideoBean lampVideoBean;
//    private LampPoleControlAdapter adapter;
    private LampDeviceControlAdapter adapter;
    private boolean prohibit;//是否禁止操作

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @AutoWired(name = "lamp_pole_info")
    LampDeviceListBean.DataBean.RecordsBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        if (eventMessage.getEventEnum() == EventEnum.ADD_DENGGAN_SUCCESS) {
            queryLampVideo();
        }
    }

    @Override
    protected TitleBar initTitle() {
        titleBar = super.initTitle();

        creatTitleBarAction();

        return titleBar;
    }

    //创建导航条
    public void creatTitleBarAction(){

        brushimageAction = new TitleBar.ImageAction(R.mipmap.icon_qrcodenewest) {
            @Override
            public void performAction(View view) {
                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = LampLightDetailLightListFragment.deviceType;
                infoBean.id = device_info.getId();
                infoBean.name = device_info.getName();
                infoBean.device_size = dataBean.getChildrenList().size();
                infoBean.childrenList = dataBean.getChildrenList();
                infoBean.isqrcode = true;
                infoBean.islamplist = true;
                openNewPage(DeviceLampAddFragment.class,"device_info",infoBean);
            }
        };
        titleBar.addAction(brushimageAction);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        prohibit = true;
        binding.baseview.setVisibility(View.INVISIBLE);

        GridLayoutManager device_manager = new GridLayoutManager(getActivity(), 1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new LampDeviceControlAdapter();
        binding.myrecycleView.setAdapter(adapter);
        adapter.setOnItemClick(new LampDeviceControlAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                LampDeviceBean.DataBean.ChildrenListBean childrenListBean = dataBean.getChildrenList().get(index);
                boolean select = childrenListBean.isSelect();
                dataBean.getChildrenList().get(index).setSelect(!select);
                adapter.setmDatas(dataBean,prohibit);
            }
        });

        adapter.setOnImageClick(new LampDeviceControlAdapter.OnImageClick() {
            @Override
            public void imgclick(int index) {
                LampDeviceBean.DataBean.ChildrenListBean childrenListBean = dataBean.getChildrenList().get(index);
                dataBean.getChildrenList().get(index).setSelect(true);
                adapter.setmDatas(dataBean,prohibit);
            }
        });

        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.tvCancel.setOnClickListener(this::onClick);

        queryLampVideo();
    }

    /**
     * 查询灯杆下设备
     */
    private void queryLamp() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("id",device_info.getId());
        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_GET, parameter, new BeanResponseListener<LampDeviceBean>() {
            @Override
            public void onResponse(LampDeviceBean lampDeviceBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    dataBean = lampDeviceBean.getData().get(0);
                    dataBean.setLampvideobeans(lampVideoBean.getData());
                    if (dataBean.getChildrenList().size()>0) {
                        binding.baseview.setVisibility(View.INVISIBLE);
                        binding.tvConfirm.setVisibility(View.VISIBLE);
                    }else {
                        binding.baseview.setVisibility(View.VISIBLE);
                        binding.tvConfirm.setVisibility(View.INVISIBLE);
                    }

                    //处理不是灯具的数据
                    List<LampDeviceBean.DataBean.ChildrenListBean> datas = new ArrayList<>();
                    for (int i = 0; i < dataBean.getChildrenList().size(); i++) {
                        LampDeviceBean.DataBean.ChildrenListBean childrenListBean = dataBean.getChildrenList().get(i);
                        if(childrenListBean.getDeviceType() ==1){
                            datas.add(childrenListBean);
                        }
                    }

                    if(datas.size() >0){
                        dataBean.setChildrenList(datas);
                    }
                    adapter.setmDatas(dataBean,prohibit);
                }
            }
        });
    }

    /**
     * 查询氛围灯节目
     */
    public void queryLampVideo(){
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("isCustom",0);
        HttpRequest.GET(mActivity, HttpApi.API_SL_LAMP_ATMO_PROGRAM_PULLDOWN, parameter, new BeanResponseListener<LampVideoBean>() {
            @Override
            public void onResponse(LampVideoBean bean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    lampVideoBean = bean;
                }

                queryLamp();
            }
        });
    }

    @SneakyThrows
    @Override
    public void onClick(View v) {
        if(v == binding.tvConfirm){
            if(binding.tvConfirm.getText().toString().contains("编辑")) {
                binding.tvConfirm.setText("下发");
                binding.tvCancel.setVisibility(View.VISIBLE);
                prohibit = false;
                for (LampDeviceBean.DataBean.ChildrenListBean lampChildrenListBean : dataBean.getChildrenList()) {
                    lampChildrenListBean.setShow(true);
                }
                adapter.setmDatas(dataBean,prohibit);
            }else {
                org.json.JSONArray idlist = new JSONArray();
                org.json.JSONArray numlist = new JSONArray();
                org.json.JSONArray swithclist = new JSONArray();

                org.json.JSONArray singleidlist = new JSONArray();
                org.json.JSONArray typelist = new JSONArray();
                org.json.JSONArray lightnesses = new JSONArray();

                int singlelight =0;
                int fenweilight =0;
                for (LampDeviceBean.DataBean.ChildrenListBean lampChildrenListBean : dataBean.getChildrenList()) {
                    org.json.JSONArray list = new JSONArray();
                    if(lampChildrenListBean.isSelect()){

                        if(lampChildrenListBean.getDlmRespDevice().getLampPositionId()==3){//氛围灯
                            fenweilight ++;
                            idlist.put(lampChildrenListBean.getId());
                            swithclist.put(lampChildrenListBean.getDlmRespDevice().getDeviceState()==1?"1":"0");
                            if(lampChildrenListBean.getSelect_colorids() != null) {
                                for (Integer select_colorid : lampChildrenListBean.getSelect_colorids()) {
                                    list.put(select_colorid);
                                }
                                numlist.put(list);
                            }
                        }else {//单灯
                            singlelight ++;
                            singleidlist.put(lampChildrenListBean.getId());
                            typelist.put(lampChildrenListBean.getDlmRespDevice().getDeviceState()==1?"1":"0");
                            lightnesses.put(lampChildrenListBean.getDlmRespDevice().getBrightness());
                        }
                    }
                }

                if(fenweilight >0 || singlelight >0){
                    mMessageLoader.show();
                }else {
                    XToastUtils.success("请选择设备");
                }

                if(fenweilight >0) {
                    JSONObject body = new JSONObject();
                    body.put("deviceIdList", idlist);
                    body.put("playFileNumArrays", numlist);
                    body.put("atmoSwitchs", swithclist);
                    Integer finalSinglelight = singlelight;

                    HttpRequest.JSONPOST(mActivity, HttpApi.API_SL_SYSTEM_DEVICE_SINGLESETATMOSPHEREPARAM, body, new BeanResponseListener<MapLampCommonDevList>() {
                        @Override
                        public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                            mMessageLoader.dismiss();
                            if (null == error && finalSinglelight ==0) {
                                XToastUtils.success("下发成功");
                            }
                        }
                    });
                }

                if(singlelight >0){
                    JSONObject body = new JSONObject();
                    body.put("lampDeviceIdList", singleidlist);
                    body.put("lightnesses", lightnesses);
                    body.put("types", typelist);
                    HttpRequest.JSONPOST(mActivity, HttpApi.DEVICE_CONTROL, body, new BeanResponseListener<MapLampCommonDevList>() {
                        @Override
                        public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                            mMessageLoader.dismiss();
                            if (null == error) {
                                XToastUtils.success("下发成功");
                            }
                        }
                    });
                }
            }
        }else if(v == binding.tvCancel){
            binding.tvConfirm.setText("编辑");
            binding.tvCancel.setVisibility(View.GONE);
            prohibit = true;
            for (LampDeviceBean.DataBean.ChildrenListBean lampChildrenListBean : dataBean.getChildrenList()) {
                lampChildrenListBean.setShow(false);
            }
//            adapter.setmDatas(dataBean,prohibit);
            queryLampVideo();
        }
    }
}
