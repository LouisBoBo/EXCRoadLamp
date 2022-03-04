package com.exc.roadlamp.device.fragment;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentAreaBinding;
import com.exc.roadlamp.databinding.FragmentDeviceMoreBinding;
import com.exc.roadlamp.device.adapter.DeviceSelectAdapter;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.adapter.AreaSelectAdapter;
import com.exc.roadlamp.home.model.AreaModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.model.AlarmNewsModel;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Page(name = "多选灯杆")
public class DeviceSelectMoreFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentDeviceMoreBinding binding;
    private DeviceSelectAdapter adapter;
    private boolean is_sort = true;//是否正序
    private int pageNum = 1;

    /**
     * 灯杆集合
     */
    private List<LampDeviceListBean.DataBean.RecordsBean> dataBeanArrayList = new ArrayList<>();
    /**
     * 灯具集合
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mapLampArrayList = new ArrayList<>();
    /**
     * 在地图上显示的配电柜集合
     */
    private List<PowerCabinetList.DataBean.RecordsBean> mPowerCabinetList = new ArrayList<>();

    /**
     * 集控集合
     */
    private List<CenterControlListData.DataBean.RecordsBean> tempCenterControllist = new ArrayList<>();

    @AutoWired(name = "device_type")
    int device_type;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        String titleStr = "";
        switch (device_type) {
            case LampPoleDetailFragment.deviceType:
                titleStr = "多选灯杆";
                break;
            case LampLightDetailLightListFragment.deviceType:
                titleStr = "多选灯具";
                break;
            case PowerCabinetDetailFragment.deviceType:
                titleStr = "多选配电柜";
                break;
            case CenterControlDetailFragment.deviceType:
                titleStr = "多选集控";
                break;
        }
        titleBar.setTitle(titleStr);
        return titleBar;
    }

    @Override
    protected void initViews() {
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(), 1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new DeviceSelectAdapter();
        binding.myrecycleView.setAdapter(adapter);
        adapter.setOnItemClick(new DeviceSelectAdapter.OnItemClick() {
            @Override
            public void click(int index) {

            }
        });
        adapter.setOnImageClick(new DeviceSelectAdapter.OnImageClick() {

            @Override
            public void imgclick(int index) {
                if (device_type == LampPoleDetailFragment.deviceType ) {

                    boolean select = dataBeanArrayList.get(index).isSelect();
                    dataBeanArrayList.get(index).setSelect(!select);
                    adapter.setmDatas(dataBeanArrayList);

                    int count = 0;
                    for (LampDeviceListBean.DataBean.RecordsBean dataBean : dataBeanArrayList) {
                        if(dataBean.isSelect()){
                            count ++;
                        }
                    }
                    binding.imgSelect.setSelected(count==dataBeanArrayList.size()?true:false);

                } else if (device_type == LampLightDetailLightListFragment.deviceType){
                    boolean select = mapLampArrayList.get(index).is_select();
                    mapLampArrayList.get(index).is_select = !select;
                    adapter.setmMaplampDatas(mapLampArrayList);

                    int count = 0;
                    for (MapLampCommonDevList.DataBean.ChildrenListBean dataBean : mapLampArrayList) {
                        if(dataBean.is_select){
                            count ++;
                        }
                    }
                    binding.imgSelect.setSelected(count==mapLampArrayList.size()?true:false);
                } else if (device_type == PowerCabinetDetailFragment.deviceType) {

                    boolean select = mPowerCabinetList.get(index).is_select();
                    mPowerCabinetList.get(index).is_select = !select;
                    adapter.setmCabinewDatas(mPowerCabinetList);

                    int count = 0;
                    for (PowerCabinetList.DataBean.RecordsBean recordsBean : mPowerCabinetList) {
                        if(recordsBean.is_select){
                            count ++;
                        }
                    }
                    binding.imgSelect.setSelected(count==mPowerCabinetList.size()?true:false);
                } else if (device_type == CenterControlDetailFragment.deviceType) {

                    boolean select = tempCenterControllist.get(index).is_select();
                    tempCenterControllist.get(index).is_select = !select;
                    adapter.setmControlDatas(tempCenterControllist);

                    int count =0;
                    for (CenterControlListData.DataBean.RecordsBean recordsBean : tempCenterControllist) {
                        if(recordsBean.is_select){
                            count ++;
                        }
                    }
                    binding.imgSelect.setSelected(count==tempCenterControllist.size()?true:false);
                }
            }
        });

        binding.imgSelect.setSelected(false);
        binding.imgSelect.setOnClickListener(this::onClick);
        binding.btnGo.setOnClickListener(this::onClick);
        binding.confirm.setOnClickListener(this::onClick);

        initData();

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            //处于搜索时不能刷新
            pageNum = 1;
            initData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //处于搜索时不能加载
            pageNum++;
            initData();
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDeviceMoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void initData() {

        if (device_type == LampPoleDetailFragment.deviceType) {
            queryLampList(0,0,0);
        }else if(device_type == LampLightDetailLightListFragment.deviceType){
            queryLampLightList(device_type);
        } else if (device_type == PowerCabinetDetailFragment.deviceType) {
            queryAllPowerCabinet();
        } else if (device_type == CenterControlDetailFragment.deviceType) {
            queryLampCenterControl();
        }
    }


    /**
     * 查询灯杆
     */
    private void queryLampList(int areaId,int streeId, int siteId) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageSize", 30);
        parameter.put("pageNum", pageNum);

        if(areaId != 0 ){
            parameter.put("areaId", areaId);
        }
        if(streeId != 0 ){
            parameter.put("streetId", streeId);
        }
        if(siteId != 0){
            parameter.put("siteId", siteId);
        }

        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_PAGE, parameter, new BeanResponseListener<LampDeviceListBean>() {
            @Override
            public void onResponse(LampDeviceListBean lampDeviceListBean, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();

                if(error == null) {
                    if(pageNum == 1){
                        dataBeanArrayList.clear();
                    }
                    if(lampDeviceListBean.getData().getRecords() != null){
                        dataBeanArrayList.addAll(lampDeviceListBean.getData().getRecords());
                    }
                    adapter.setmDatas(dataBeanArrayList);
                }
            }
        });
    }

    /**
     * 查询灯具
     *
     */
    private void queryLampLightList(int deviceType) {
//        mMessageLoader.show();
//        Parameter parameter = new Parameter();
//        parameter.put("pageSize", 30);
//        parameter.put("pageNum", pageNum);
//        parameter.put("deviceTypeId", deviceType);
//        parameter.put("siteId", "");
//        parameter.put("streetId", "");
//        parameter.put("areaId", "");
//        parameter.put("lampPostNameOrNum", "");
//        parameter.put("isApp", 1);
//        HttpRequest.GET(mActivity, HttpApi.GET_ALL_LAMP_POLE, parameter, new BeanResponseListener<MapLampCommonDevList>() {
//            @Override
//            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
//                mMessageLoader.dismiss();
//                binding.refreshLayout.finishRefresh();
//                binding.refreshLayout.finishLoadMore();
//
//                if (null == error) {
//                    if(pageNum == 1){
//                        mapLampArrayList.clear();
//                    }
//
//                    //过滤掉没有坐标的灯具
//                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
//                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));
//
//                    //过滤掉没有灯具的灯杆
//                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
//                            filter(dataBean -> !(null == dataBean.getChildrenList()
//                                    || dataBean.getChildrenList().size() == 0
//                            )).collect(Collectors.toList()));
//
//                    for (MapLampCommonDevList.DataBean poleBean : mapLampCommonDevList.getData()) {
//                        for (MapLampCommonDevList.DataBean.ChildrenListBean children : poleBean.getChildrenList()) {
//                            children.getDlmRespDevice().setLampPostLatitude(poleBean.getLampPostLatitude());
//                            children.getDlmRespDevice().setLampPostLongitude(poleBean.getLampPostLongitude());
//                            mapLampArrayList.add(children);
//                        }
//                    }
//
//                    adapter.setmMaplampDatas(mapLampArrayList);
//                }
//
//            }
//        });

        queryLampLightListPage();
    }

    private void queryLampLightListPage(){
        mMessageLoader.show();
        JSONObject json = new JSONObject();
        json.put("pageSize", 30);
        json.put("pageNum", pageNum);
        json.put("sortRule",0);
        json.put("sortType",3);

        HttpRequest.JSONPOST(getActivity(), HttpApi.API_SL_LAMP_DEVICE_PAGE, json.toJSONString(), new BeanResponseListener<LampLightListBean>() {
            @Override
            public void onResponse(LampLightListBean bean, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if(null == error){
                    if(pageNum == 1){
                        mapLampArrayList.clear();
                    }
                    for (LampLightListBean.DataBean.RecordsBean record : bean.getData().getRecords()) {
                        MapLampCommonDevList.DataBean.ChildrenListBean children = new MapLampCommonDevList.DataBean.ChildrenListBean();
                        children.setId(record.getId());
                        children.setLampPostId(record.getLampPostId());
                        children.setName(record.getName());
                        children.setNum(record.getNum());
                        children.setDeviceType(record.getDeviceType());

                        MapLampCommonDevList.DataBean.ChildrenListBean.DlmRespDeviceBean dlmRespDeviceBean = new MapLampCommonDevList.DataBean.ChildrenListBean.DlmRespDeviceBean();
                        if(record.getSlLampPost()!=null) {
                            if(record.getSlLampPost().getName() != null){
                                children.setSuperName(record.getSlLampPost().getName());
                            }
                            if(record.getSlLampPost().getLatitude() >0){
                                dlmRespDeviceBean.setLampPostLatitude(record.getSlLampPost().getLatitude());
                            }
                            if(record.getSlLampPost().getLongitude() >0){
                                dlmRespDeviceBean.setLampPostLongitude(record.getSlLampPost().getLongitude());
                            }
                            if(record.getSlLampPost().getLocation() != null){
                                dlmRespDeviceBean.setSuperName(record.getSlLampPost().getLocation().toString());
                            }
                            children.setDlmRespDevice(dlmRespDeviceBean);

                            mapLampArrayList.add(children);
                        }
                    }

                    adapter.setmMaplampDatas(mapLampArrayList);
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
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", 30);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if (null == error) {
                    if(pageNum== 1){
                        mPowerCabinetList.clear();
                    }

                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    if(powerCabinetList.getData().getRecords() != null){
                        mPowerCabinetList.addAll(powerCabinetList.getData().getRecords());
                    }
                    adapter.setmCabinewDatas(mPowerCabinetList);
                }
            }
        });
    }

    /**
     * 查询集控
     */
    private void queryLampCenterControl() {

        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", 30);
        parameter.put("controlTypeIdList", "4,5");

        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_LIST, parameter, new BeanResponseListener<CenterControlListData>() {
            @Override
            public void onResponse(CenterControlListData powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if (null == error) {
                    if(pageNum== 1){
                        tempCenterControllist.clear();
                    }
                    if(powerCabinetList.getData().getRecords() != null){
                        tempCenterControllist.addAll(powerCabinetList.getData().getRecords());
                    }
                    if (tempCenterControllist != null && tempCenterControllist.size() > 0) {
                        adapter.setmControlDatas(tempCenterControllist);
                    }
                }
            }
        });
    }

    /**
     * 批量删除灯杆
     */
    private void deleateSlLamps(List<String> ids) {
        mMessageLoader.show();

        //把List集合转换为字符串用&隔开
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            stringBuffer.append(ids.get(i).toString().trim() + ",");
        }
        String idss = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

        Parameter parameter = new Parameter();
        parameter.put("ids",idss);
        HttpRequest.DELETE(mActivity, HttpApi.API_DLM_SL_LAMP_POST_BATCH, parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success("删除成功");
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_DENGGAN_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    myHandler.sendEmptyMessage(1000);
                }
            }
        });
    }

    /**
     * 批量删除灯具
     */
    private void deleateSlDevices(List<String> ids) {
        mMessageLoader.show();

        //把List集合转换为字符串用&隔开
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            stringBuffer.append(ids.get(i).toString().trim() + ",");
        }
        String idss = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

        Parameter parameter = new Parameter();
        HttpRequest.DELETE(mActivity, HttpApi.API_SL_LAMP_DEVICE_DELETELIST + idss, parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success("删除成功");
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_DENGJU_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    myHandler.sendEmptyMessage(2000);
                }
            }
        });
    }

    /**
     * 批量删除配电柜
     */
    private void deleateCabinets(List<String> ids) {
        mMessageLoader.show();

        //把List集合转换为字符串用&隔开
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            stringBuffer.append(ids.get(i).toString().trim() + ",");
        }
        String idss = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

        Parameter parameter = new Parameter();
        HttpRequest.DELETE(mActivity, HttpApi.API_DLM_LOCATION_DISTRIBUTE_CABINET_BATCH + idss , parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success("删除成功");
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_PEIDIANGUI_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    myHandler.sendEmptyMessage(3000);
                }
            }
        });
    }

    /**
     * 批量删除集控
     */
    private void deleateControls(List<String> ids) {
        mMessageLoader.show();

        //把List集合转换为字符串用&隔开
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            stringBuffer.append(ids.get(i).toString().trim() + ",");
        }
        String idss = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

        Parameter parameter = new Parameter();
        HttpRequest.DELETE(mActivity, HttpApi.API_DLM_LOCATION_CONTROL_BATCH + idss , parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success("删除成功");
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_JIKONG_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    myHandler.sendEmptyMessage(4000);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgSelect) {
            binding.imgSelect.setSelected(!binding.imgSelect.isSelected());

            if (device_type == LampPoleDetailFragment.deviceType) {
                for (LampDeviceListBean.DataBean.RecordsBean model : dataBeanArrayList) {
//                    model.isSelect() = (binding.imgSelect.isSelected() ? true : false);

                    model.setSelect(binding.imgSelect.isSelected() ? true : false);
                }
                adapter.setmDatas(dataBeanArrayList);
            } else if(device_type == LampLightDetailLightListFragment.deviceType) {
                for (MapLampCommonDevList.DataBean.ChildrenListBean model : mapLampArrayList) {
                    model.is_select = (binding.imgSelect.isSelected() ? true : false);
                }
                adapter.setmMaplampDatas(mapLampArrayList);
            } else if (device_type == PowerCabinetDetailFragment.deviceType) {
                for (PowerCabinetList.DataBean.RecordsBean model : mPowerCabinetList) {
                    model.is_select = (binding.imgSelect.isSelected() ? true : false);
                }
                adapter.setmCabinewDatas(mPowerCabinetList);
            } else if (device_type == CenterControlDetailFragment.deviceType) {
                for (CenterControlListData.DataBean.RecordsBean model : tempCenterControllist) {
                    model.is_select = (binding.imgSelect.isSelected() ? true : false);
                }
                adapter.setmControlDatas(tempCenterControllist);
            }
        } else if(v == binding.btnGo){
            if(device_type == LampPoleDetailFragment.deviceType){
                is_sort = !is_sort;
                Collections.sort(dataBeanArrayList, new Comparator<LampDeviceListBean.DataBean.RecordsBean>(){
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    @Override
                    public int compare(LampDeviceListBean.DataBean.RecordsBean o1, LampDeviceListBean.DataBean.RecordsBean o2) {
                        if(is_sort){
                            return (o2.getCreateTime().toString()).compareTo(o1.getCreateTime().toString());
                        }else {
                            return (o1.getCreateTime().toString()).compareTo(o2.getCreateTime().toString());
                        }

                    }
                });
                adapter.setmDatas(dataBeanArrayList);

            }else if(device_type == LampLightDetailLightListFragment.deviceType){

                is_sort = !is_sort;
                Collections.sort(mapLampArrayList, new Comparator<MapLampCommonDevList.DataBean.ChildrenListBean>(){
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    @Override
                    public int compare(MapLampCommonDevList.DataBean.ChildrenListBean o1, MapLampCommonDevList.DataBean.ChildrenListBean o2) {
                        if(is_sort){
                            return (o2.dlmRespDevice.getCreateTime().toString()).compareTo(o1.dlmRespDevice.getCreateTime().toString());
                        }else {
                            return (o1.dlmRespDevice.getCreateTime().toString()).compareTo(o2.dlmRespDevice.getCreateTime().toString());
                        }

                    }
                });
                adapter.setmMaplampDatas(mapLampArrayList);
            }else if(device_type == PowerCabinetDetailFragment.deviceType){
                is_sort = !is_sort;
                Collections.sort(mPowerCabinetList, new Comparator<PowerCabinetList.DataBean.RecordsBean>(){
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    @Override
                    public int compare(PowerCabinetList.DataBean.RecordsBean o1, PowerCabinetList.DataBean.RecordsBean o2) {
                        if(is_sort){
                            return (o2.getCreateTime().toString()).compareTo(o1.getCreateTime().toString());
                        }else {
                            return (o1.getCreateTime().toString()).compareTo(o2.getCreateTime().toString());
                        }

                    }
                });
                adapter.setmCabinewDatas(mPowerCabinetList);
            }else if(device_type == CenterControlDetailFragment.deviceType){
                is_sort = !is_sort;
                Collections.sort(tempCenterControllist, new Comparator<CenterControlListData.DataBean.RecordsBean>(){
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    @Override
                    public int compare(CenterControlListData.DataBean.RecordsBean o1, CenterControlListData.DataBean.RecordsBean o2) {
                        if(is_sort){
                            return (o2.getCreateTime().toString()).compareTo(o1.getCreateTime().toString());
                        }else {
                            return (o1.getCreateTime().toString()).compareTo(o2.getCreateTime().toString());
                        }

                    }
                });
                adapter.setmControlDatas(tempCenterControllist);
            }
            binding.btnGo.setImageResource(is_sort? R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);
            binding.tvText.setText(is_sort?"正序":"倒序");
        } else if (v == binding.confirm) {//删除
                switch (device_type) {
                    case LampPoleDetailFragment.deviceType:

                        List<String> ids = new ArrayList<>();
                        for (int i = dataBeanArrayList.size() - 1; i >= 0; i--) {
                            LampDeviceListBean.DataBean.RecordsBean model = dataBeanArrayList.get(i);
                            if(model.isSelect()){
                                ids.add(String.valueOf(model.getId()));
                            }
                        }
                        if(ids.size()==0){
                            XToastUtils.success("请选择设备");
                            return;
                        }
                        deleateSlLamps(ids);
                        break;
                    case LampLightDetailLightListFragment.deviceType:
                        List<String> dds = new ArrayList<>();
                        for (int i = mapLampArrayList.size() - 1; i >= 0; i--) {
                            MapLampCommonDevList.DataBean.ChildrenListBean model = mapLampArrayList.get(i);
                            if(model.is_select){
                                dds.add(String.valueOf(model.getId()));
                            }
                        }
                        if(dds.size()==0){
                            XToastUtils.success("请选择设备");
                            return;
                        }
                        deleateSlDevices(dds);
                        break;
                    case PowerCabinetDetailFragment.deviceType:

                        List<String> cids = new ArrayList<>();
                        for (int i = mPowerCabinetList.size() - 1; i >= 0; i--) {
                            PowerCabinetList.DataBean.RecordsBean model = mPowerCabinetList.get(i);
                            if(model.is_select){
                                cids.add(String.valueOf(model.getId()));
                            }
                        }
                        if(cids.size()==0){
                            XToastUtils.success("请选择设备");
                            return;
                        }
                        deleateCabinets(cids);
                        break;
                    case CenterControlDetailFragment.deviceType:

                        List<String> tids = new ArrayList<>();
                        for (int i = tempCenterControllist.size() - 1; i >= 0; i--) {
                            CenterControlListData.DataBean.RecordsBean model = tempCenterControllist.get(i);
                            if(model.is_select){
                                tids.add(String.valueOf(model.getId()));
                            }
                        }
                        if(tids.size()==0){
                            XToastUtils.success("请选择设备");
                            return;
                        }
                        deleateControls(tids);
                        break;
                }
        }
    }

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    for (int i = dataBeanArrayList.size() - 1; i >= 0; i--) {
                        LampDeviceListBean.DataBean.RecordsBean model = dataBeanArrayList.get(i);
                        if(model.isSelect()){
                            dataBeanArrayList.remove(i);
                        }
                    }
                    adapter.setmDatas(dataBeanArrayList);
                    break;
                case 2000:
                    for (int i = mapLampArrayList.size() - 1; i >= 0; i--) {
                        MapLampCommonDevList.DataBean.ChildrenListBean model = mapLampArrayList.get(i);
                        if(model.is_select){
                            mapLampArrayList.remove(i);
                        }
                    }
                    adapter.setmMaplampDatas(mapLampArrayList);
                    break;
                case 3000:
                    for (int i = mPowerCabinetList.size() - 1; i >= 0; i--) {
                        PowerCabinetList.DataBean.RecordsBean model = mPowerCabinetList.get(i);
                        if(model.is_select){
                            mPowerCabinetList.remove(i);
                        }
                    }
                    adapter.setmCabinewDatas(mPowerCabinetList);
                    break;
                case 4000:
                    for (int i = tempCenterControllist.size() - 1; i >= 0; i--) {
                        CenterControlListData.DataBean.RecordsBean model = tempCenterControllist.get(i);
                        if(model.is_select){
                            tempCenterControllist.remove(i);
                        }
                    }
                    adapter.setmControlDatas(tempCenterControllist);
                    break;
            }

            super.handleMessage(msg);
        }
    };
}

