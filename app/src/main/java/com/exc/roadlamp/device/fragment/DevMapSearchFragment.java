package com.exc.roadlamp.device.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDevMapSearchBinding;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.adapter.DevMapSearchResultListAdapter;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.ManholeList;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.common.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Page(name = "搜索灯杆")
public class DevMapSearchFragment extends MyBaseFragment {
    @AutoWired(name = "showDeviceTypeId")
    int showDeviceTypeId;
    private FragmentDevMapSearchBinding binding;
    private DevMapSearchResultListAdapter adapter;
    private String etStr;
    private ArrayList<DevMapSearchResultListBean> resultListBeans;

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        String titleStr = "搜索";
        switch (showDeviceTypeId) {
            case LampPoleDetailFragment.deviceType:
                titleStr = "搜索灯杆";
                binding.et.setHint("请输入灯杆名称或编号~");
                break;
            case LampLightDetailLightListFragment.deviceType:
                titleStr = "搜索灯具";
                binding.et.setHint("请输入灯具名称或编号~");
                break;
            case PowerCabinetDetailFragment.deviceType:
                titleStr = "搜索配电柜";
                binding.et.setHint("请输入配电柜名称或编号");
                break;
            case CenterControlDetailFragment.deviceType:
                titleStr = "搜索集控";
                binding.et.setHint("请输入集控名称或编号");
                break;
            case ManholeDetailFragment.deviceType:
                titleStr = "搜索井盖";
                binding.et.setHint("请输入井盖名称或编号");
                break;
        }

        titleBar.setTitle(titleStr);
        return titleBar;
    }

    @Override
    protected void initArgs() {
        needArgs(this);
    }

    @Override
    protected void initViews() {


        WidgetUtils.initRecyclerView(binding.recyclerView, 0);
        adapter = new DevMapSearchResultListAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            popToBack();
            EventMessage<DevMapSearchResultListBean> eventMessage = new EventMessage<>();
            eventMessage.setEventEnum(EventEnum.DEV_MAP_SEARCH_RESULT_LIST_CLICK);
            eventMessage.setData(resultListBeans.get(position));
            EventBus.getDefault().post(eventMessage);

        });


        binding.et.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(v);
                return true;
            }
            return false;
        });
        binding.btnSearch.setOnClickListener(v -> {
            doSearch(v);
        });
    }

    private void doSearch(View view) {
        etStr = binding.et.getText().toString().trim();
        if (StringUtils.isEmpty(etStr)) {

            switch (showDeviceTypeId) {
                case LampPoleDetailFragment.deviceType:
                    XToastUtils.error("请输入灯杆名称或编号~");
                    break;
                case LampLightDetailLightListFragment.deviceType:
                    XToastUtils.error("请输入灯具名称或编号~");
                    break;
                case PowerCabinetDetailFragment.deviceType:
                    XToastUtils.error("请输入配电柜名称或编号");
                    break;
                case CenterControlDetailFragment.deviceType:
                    XToastUtils.error("请输入集控名称或编号~");
                    break;
                case ManholeDetailFragment.deviceType:
                    XToastUtils.error("请输入井盖名称或编号~");
                    break;
            }

            return;
        }
        KeyboardUtils.hideSoftInput(view);

        switch (showDeviceTypeId) {
            case LampPoleDetailFragment.deviceType:
                queryLamp();
                break;
            case LampLightDetailLightListFragment.deviceType:
                queryLampLightListPage();
                break;
            case PowerCabinetDetailFragment.deviceType:
                queryAllPowerCabinet();
                break;
            case CenterControlDetailFragment.deviceType:
                queryLampCenterControl();
                break;
            case ManholeDetailFragment.deviceType:
                queryManholeList();
                break;
        }


    }

    /**
     * 查询集控
     */
    private void queryLampCenterControl() {

        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 99999999);
        parameter.put("controlTypeIdList", "4,5");
        parameter.put("newName", etStr);
        parameter.put("name", etStr);



        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_LIST, parameter, new BeanResponseListener<CenterControlListData>() {
            @Override
            public void onResponse(CenterControlListData powerCabinetList, Exception error) {
                if (null == error) {
                    List<CenterControlListData.DataBean.RecordsBean> tempCenterControl = powerCabinetList.getData().getRecords();
                    if (tempCenterControl == null || tempCenterControl.size() == 0) {
                        mMessageLoader.dismiss();
                        XToastUtils.error("没有搜索到集控");
                        return;
                    }
                    queryAllPowerCabinetForCenterControl(tempCenterControl);
                }
            }
        });
    }


    private void queryAllPowerCabinetForCenterControl(List<CenterControlListData.DataBean.RecordsBean> tempCenterControlList) {
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 0);
        parameter.put("pageSize", 99999999);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉没有坐标的灯具
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    List<PowerCabinetList.DataBean.RecordsBean> tempPowerCabinetList = powerCabinetList.getData().getRecords();

                    if (tempPowerCabinetList == null || tempPowerCabinetList.size() == 0) {
                        XToastUtils.error("数据异常");
                        return;
                    }
                    //增加配电柜信息
                    for (CenterControlListData.DataBean.RecordsBean centerControl : tempCenterControlList) {
                        for (PowerCabinetList.DataBean.RecordsBean powerCabinet : tempPowerCabinetList) {
                            if (centerControl.getCabinetId() == powerCabinet.id) {
                                centerControl.setPowerCabinetInfo(powerCabinet);
                            }
                        }
                    }

                    List<CenterControlListData.DataBean.RecordsBean> searchControlResult = tempCenterControlList;

                    resultListBeans = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean dataBean : searchControlResult) {
                        DevMapSearchResultListBean bean = new DevMapSearchResultListBean();
                        bean.setDevId(dataBean.getId());
                        bean.setDevName(dataBean.getName());
                        bean.setDeviceType(showDeviceTypeId);
                        resultListBeans.add(bean);
                    }
                    adapter.setList(resultListBeans);


                }
            }
        });
    }

    /**
     * 查询灯杆上的设备
     */
    private void queryLamp() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("deviceTypeId", showDeviceTypeId);
        parameter.put("siteId", "");
        parameter.put("streetId", "");
        parameter.put("areaId", "");
        parameter.put("lampPostNameOrNum", etStr);
        HttpRequest.GET(mActivity, HttpApi.GET_ALL_LAMP_POLE, parameter, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {

                    if (mapLampCommonDevList.code != 200) {
                        XToastUtils.error(mapLampCommonDevList.message);
                        return;
                    }

                    if (null == mapLampCommonDevList.getData() || mapLampCommonDevList.getData().size() == 0) {
                        XToastUtils.error("没有搜索到灯杆~");
                        return;
                    }
                    //过滤没有坐标的设备
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));
                    if (mapLampCommonDevList.getData().size() == 0) {
                        XToastUtils.error("没有搜索到灯杆~");
                        return;
                    }
                    resultListBeans = new ArrayList<>();
                    for (MapLampCommonDevList.DataBean dataBean : mapLampCommonDevList.getData()) {
                        DevMapSearchResultListBean bean = new DevMapSearchResultListBean();
                        bean.setLampPostId(dataBean.getId());
                        bean.setDevId(dataBean.getId());
                        bean.setDevName(dataBean.getName());
                        bean.setDeviceType(showDeviceTypeId);
                        bean.setDetailNames(dataBean.names);
                        resultListBeans.add(bean);
                    }
                    adapter.setList(resultListBeans);
                }
            }
        });
    }

    /**
     * 查询灯具
     */
    private void queryLampLightListPage(){
        mMessageLoader.show();
        JSONObject json = new JSONObject();
        json.put("pageSize", 999999);
        json.put("pageNum", 1);
        json.put("sortRule",0);
        json.put("sortType",3);
        json.put("name", etStr);


        HttpRequest.JSONPOST(getActivity(), HttpApi.API_SL_LAMP_DEVICE_PAGE, json.toJSONString(), new BeanResponseListener<LampLightListBean>() {
            @Override
            public void onResponse(LampLightListBean lightbean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){

                    resultListBeans = new ArrayList<>();
                    for (LampLightListBean.DataBean.RecordsBean record : lightbean.getData().getRecords()) {

                        DevMapSearchResultListBean bean = new DevMapSearchResultListBean();
                        bean.setLampPostId(record.getLampPostId());
                        bean.setDevId(record.getId());
                        bean.setDevName(record.getName());
                        bean.setDeviceType(showDeviceTypeId);
                        resultListBeans.add(bean);
                    }

                    adapter.setList(resultListBeans);
                }
            }
        });
    }

    /**
     * 查询井盖
     */
    private void queryManholeList() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("name", etStr);

        HttpRequest.GET(mActivity, HttpApi.API_ED_ED_MANHOLE_COVER_DEVICE_PAGE, parameter, new BeanResponseListener<ManholeList>() {
            @Override
            public void onResponse(ManholeList manholeList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    resultListBeans = new ArrayList<>();

                    for (ManholeList.DataBean.RecordsBean dataBean : manholeList.getData().getRecords()) {
                        DevMapSearchResultListBean bean = new DevMapSearchResultListBean();
                        bean.setDevId(dataBean.getId());
                        bean.setDevName(dataBean.getName());
                        bean.setDeviceType(showDeviceTypeId);
                        resultListBeans.add(bean);
                    }
                    adapter.setList(resultListBeans);
                }
            }
        });
    }

    /**
     * 查询配电柜
     */
    /**
     * 查询配电柜列表
     */
    private void queryAllPowerCabinet() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 666666);
        parameter.put("name", etStr);


        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));

                    resultListBeans = new ArrayList<>();

                    for (PowerCabinetList.DataBean.RecordsBean record : powerCabinetList.getData().getRecords()) {
                        DevMapSearchResultListBean bean = new DevMapSearchResultListBean();
                        bean.setDevId(record.getId());
                        bean.setDevName(record.getName());
                        bean.setDeviceType(showDeviceTypeId);
                        resultListBeans.add(bean);
                    }
                    adapter.setList(resultListBeans);
                }
            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDevMapSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
