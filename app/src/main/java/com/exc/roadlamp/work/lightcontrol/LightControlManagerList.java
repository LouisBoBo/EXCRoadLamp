package com.exc.roadlamp.work.lightcontrol;

import android.app.Dialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLightListBinding;
import com.exc.roadlamp.device.adapter.LampPoleEditAdapter;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.fragment.CenterControlDetailFragment;
import com.exc.roadlamp.device.fragment.DeviceLampAddFragment;
import com.exc.roadlamp.device.fragment.DeviceLampControlFragment;
import com.exc.roadlamp.device.fragment.LampLightDetailLightListFragment;
import com.exc.roadlamp.device.fragment.LampPoleDetailFragment;
import com.exc.roadlamp.device.fragment.LampPoleEditFragment;
import com.exc.roadlamp.device.fragment.PowerCabinetDetailFragment;
import com.exc.roadlamp.device.util.DevFiddlerUtils;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.adapter.ItemAdapter;
import com.exc.roadlamp.work.adapter.LightItemAdapter;
import com.exc.roadlamp.work.workorder.CreateWorkOrderFragment;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "灯具列表")
public class LightControlManagerList extends MyBaseFragment implements View.OnClickListener{

    private FragmentLightListBinding binding;
    private LightItemAdapter itemAdapter;
    private int pageNum = 1;
    private int pageSize = 30;
    private int siteId = -1;
    private int streetId = -1;
    private int areaId = -1;
    private int dosearch_screen =0;//0正常1搜索2筛选
    private int select_index;
    /**
     * 灯具集合
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mapLampArrayList = new ArrayList<>();

    /**
     * 搜索灯具集合
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> searchmapLampArrayList = new ArrayList<>();

    /**
     * 灯杆集合筛选用
     */
    private List<MapLampCommonDevList.DataBean> mapLampForLightListFiddler = new ArrayList<>();


    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.TextAction("下发策略") {
            @Override
            public void performAction(View view) {
                LightInfoBean infoBean = new LightInfoBean();
                infoBean.searchName = binding.editSearch.getText().toString();
                infoBean.streetId = streetId;
                infoBean.siteId = siteId;
                infoBean.areaId = areaId;
                openNewPage(LightStrategyManagerList.class,"device_info",infoBean);
            }
        });
        return titleBar;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnSearch){
            dosearch_screen = 1;
            initData();
        }else if(v == binding.clearBtn){
            binding.editSearch.setText("");
            dosearch_screen = 0;
            initData();
        }else if(v == binding.selectMore){
            dosearch_screen = 2;
            if (mapLampForLightListFiddler.size() == 0) {
                XToastUtils.error("暂时没有数据可筛选，请稍后");
                return;
            }
            //处理筛选
            DevFiddlerUtils.showPoleDevFiddlerDialog(mActivity, 0, mapLampForLightListFiddler,
                    (int showDeviceTypeId, DevFiddlerUtils.SelectResult selectResult) -> {

                        areaId = selectResult.areaId;
                        streetId = selectResult.streetId;
                        siteId = selectResult.siteId;

                        pageNum =1;
                        initData();
                    });
        }else if(v == binding.addDevice){
            showCodePopview();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(LightInfoBean lightInfoBean) {
        searchmapLampArrayList.get(select_index).setDeviceState(lightInfoBean.devicestate);
        searchmapLampArrayList.get(select_index).setBrightness(lightInfoBean.progress);

        itemAdapter.setmDatas(searchmapLampArrayList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        pageNum = 1;
        if (eventMessage.getEventEnum() == EventEnum.CHANGE_LIGHT_SUCCESS) {
            initData();
        } else if(eventMessage.getEventEnum() == EventEnum.ADD_DENGJU_SUCCESS){
            initData();
        }
    }


    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        //设备列表
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new LightItemAdapter(getActivity());
        binding.recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClick(new LightItemAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                MapLampCommonDevList.DataBean.ChildrenListBean bean = searchmapLampArrayList.get(index);

                LightInfoBean infoBean = new LightInfoBean();
                infoBean.name = bean.name;
                infoBean.lampPostName = bean.lampPostName;
                infoBean.id = bean.id;
                infoBean.num = bean.num;
                infoBean.deviceTypeId = bean.deviceTypeId;
                infoBean.lampPositionId = bean.lampPositionId;
                infoBean.lampPosition = bean.lampPosition;
                openNewPage(LightEditManager.class,"device_info",infoBean);
            }
        });
        itemAdapter.setOnDeviceClick(new LightItemAdapter.OnDeviceClick() {
            @Override
            public void click(int index) {
                MapLampCommonDevList.DataBean.ChildrenListBean bean = searchmapLampArrayList.get(index);
                select_index = index;

                LightInfoBean infoBean = new LightInfoBean();
                infoBean.id = bean.id;
                infoBean.time = bean.lastOnlineTime;
                infoBean.progress = bean.brightness;
                infoBean.devicestate = bean.deviceState;
                openNewPage(LightControlManager.class,"device_info",infoBean);
            }
        });
        itemAdapter.setOnDeleateClick(new LightItemAdapter.OnDeleateClick() {
            @Override
            public void click(int index) {
                List<String> dds = new ArrayList<>();
                dds.add(String.valueOf(searchmapLampArrayList.get(index).getId()));

                DialogLoader.getInstance().showConfirmDialog(
                        getContext(),
                        getString(R.string.del_stratety_permission),
                        getString(R.string.tip_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            deleateSlDevices(dds);
                        },
                        getString(R.string.tip_no),
                        (dialog, which) -> {
                            dialog.dismiss();
                        }
                );
            }
        });

        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(binding.editSearch.getText().toString())){
                    //清除过滤
                    pageNum = 1;
                    dosearch_screen = 0;
                    initData();

                    binding.clearBtn.setVisibility(View.GONE);
                }else {
                    binding.clearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.clearBtn.setOnClickListener(this::onClick);
        binding.btnSearch.setOnClickListener(this::onClick);
        binding.selectMore.setOnClickListener(this::onClick);
        binding.addDevice.setOnClickListener(this::onClick);

        queryLampLightList();

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            //处于搜索时不能刷新
            binding.refreshLayout.finishRefresh();
            binding.refreshLayout.finishLoadMore();

            pageNum = 1;
            initData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //处于搜索时不能加载
            binding.refreshLayout.finishRefresh();
            binding.refreshLayout.finishLoadMore();

            pageNum++;
            initData();
        });


    }

    private void initData(){
        if(dosearch_screen == 2){
            queryLampLightListPage(areaId,streetId,siteId,"");//筛选
        }else if(dosearch_screen == 1){
            queryLampLightListPage(-1,-1,-1,binding.editSearch.getText().toString());//搜索
        }else
            queryLampLightListPage(-1,-1,-1,"");//正常
    }

    private void queryLampLightListPage(int areaId,int streeId, int siteId ,String searchstr){
        mMessageLoader.show();
        JSONObject json = new JSONObject();
        json.put("pageSize", pageSize);
        json.put("pageNum", pageNum);
        json.put("sortRule",0);
        json.put("sortType",3);

        if(areaId != -1 ){
            json.put("areaId", areaId);
        }
        if(streeId != -1 ){
            json.put("streetId", streeId);
        }
        if(siteId != -1 ){
            json.put("siteId", siteId);
        }

        if(searchstr.length() > 0){
            json.put("name", searchstr);
        }

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
                        children.setLampPostName(record.getLampPostName());
                        children.setName(record.getName());
                        children.setNum(record.getNum());
                        children.setModel(record.getModel());
                        children.setCreateTime(record.getCreateTime());
                        children.setLastOnlineTime(record.getLastOnlineTime());
                        children.setDeviceType(record.getDeviceType());
                        children.setDeviceTypeId(record.getDeviceTypeId());
                        children.setBrightness(record.getBrightness());
                        children.setDeviceState(record.getDeviceState());
                        children.setLampPositionId(record.getLampPositionId());
                        children.setLampPosition(record.getLampPosition());
                        children.setIsOnline(record.getIsOnline());

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
                            if(record.getSlLampPost().getLocationAreaName() != null){
                                dlmRespDeviceBean.setLocationAreaName(record.getSlLampPost().getLocationAreaName().toString());
                            }
                            if(record.getSlLampPost().getLocationStreetName() != null){
                                dlmRespDeviceBean.setLocationStreetName(record.getSlLampPost().getLocationStreetName().toString());
                            }
                            if(record.getSlLampPost().getLocationSiteName() != null){
                                dlmRespDeviceBean.setLocationSiteName(record.getSlLampPost().getLocationSiteName().toString());
                            }
                            children.setDlmRespDevice(dlmRespDeviceBean);

                            mapLampArrayList.add(children);
                        }
                    }

                    searchmapLampArrayList = mapLampArrayList;
                    itemAdapter.setmDatas(mapLampArrayList);
                }
            }
        });
    }

    /**
     * 查询路灯
     */
    private void queryLampLightList() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("deviceTypeId", LampLightDetailLightListFragment.deviceType);

        if (siteId >= 0) {
            parameter.put("siteId", siteId);
        } else {
            parameter.put("siteId", "");
        }

        if (streetId >= 0) {
            parameter.put("streetId", streetId);
        } else {
            parameter.put("streetId", "");
        }

        if (areaId >= 0) {
            parameter.put("areaId", areaId);
        } else {
            parameter.put("areaId", "");
        }

        parameter.put("lampPostNameOrNum", "");
        HttpRequest.GET(mActivity, HttpApi.GET_ALL_LAMP_POLE, parameter, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    mapLampForLightListFiddler.clear();
                    mapLampForLightListFiddler = mapLampCommonDevList.getData();
                }

                initData();
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
                mMessageLoader.dismiss();
                if(error == null) {
                    XToastUtils.success("删除成功");

                    pageNum = 1;
                    initData();
                }
            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLightListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private void showCodePopview(){
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_qrcode, null);
        contentView.findViewById(R.id.qcode_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//扫码
                bottomDialog.dismiss();

                DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                lampinfoBean.device_type = LampLightDetailLightListFragment.deviceType;
                lampinfoBean.isqrcode = true;
                openNewPage(DeviceLampAddFragment.class,"device_info",lampinfoBean);
            }
        });
        contentView.findViewById(R.id.shoudong_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//手动
                bottomDialog.dismiss();

                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = LampLightDetailLightListFragment.deviceType;
                infoBean.isqrcode = false;
                openNewPage(DeviceLampAddFragment.class,"device_info",infoBean);
            }
        });

        contentView.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭
                bottomDialog.dismiss();
            }
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.MyDialog);
        bottomDialog.show();
    }
}
