package com.exc.roadlamp.work.control;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentSelectBindingDeviceBinding;
import com.exc.roadlamp.device.adapter.LampChildrenListBean;
import com.exc.roadlamp.device.adapter.LampDeviceSelectAdapter;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.fragment.DeviceBindingFragment;
import com.exc.roadlamp.device.fragment.LampPoleDetailFragment;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.adapter.LightBindingAdapter;
import com.exc.roadlamp.work.model.RelationLightModel;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.exc.roadlamp.work.workorder.addressselector.RegionBean;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
@Page(name = "手动添加")
public class LightBindingFragment extends MyBaseFragment implements View.OnClickListener{
    private FragmentSelectBindingDeviceBinding binding;
    private int projectSelectOption = 0;
    private int areaSelectOption = 0;
    private int streeSelectOption = 0;
    private int areaId;
    private int streeId;
    private int siteId;
    private int pageNum=1;
    private boolean dosearch = false;

    /**
     * 灯具集合
     */
//    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mapLampArrayList = new ArrayList<>();
    private List<LampDeviceListBean.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private RelationLightModel.DataBean.SystemDeviceListBean selectrecordsBean;
    private LightBindingAdapter adapter;

    private List<RegionBean> projectList = new ArrayList<>();
    private List<RegionBean> areaList = new ArrayList<>();
    private List<RegionBean> streeList = new ArrayList<>();

    private List<String> mProjectOption = new ArrayList<>();
    private List<String> mAreaOption = new ArrayList<>();
    private List<String> mStreeOption = new ArrayList<>();
    private List<RelationLightModel.DataBean.SystemDeviceListBean> deviceListBeans = new ArrayList<>();

    //路灯杆位置列表
    private List<WorkAreaListData.DataBean> areListData;
    private int system_id;

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.projectView.searchView){
            if(mProjectOption.size() > 0) {
                showProjectConstellationPickerView();
            }
        }else if(v ==binding.areaView.searchView){
            if(mAreaOption.size() > 0) {
                showAreaConstellationPickerView();
            }
        }else if(v == binding.streeView.searchView){
            if(mStreeOption.size() > 0) {
                showStreeConstellationPickerView();
            }
        }else if(v == binding.tvConfirm){
            List<String> ids = new ArrayList<>();
//            for (MapLampCommonDevList.DataBean.ChildrenListBean bean : mapLampArrayList) {
//                if(bean.isSelect){
//                    ids.add(String.valueOf(bean.id));
//                }
//            }
            for (RelationLightModel.DataBean.SystemDeviceListBean deviceListBean : deviceListBeans) {
                if(deviceListBean.isSelect()){
                    ids.add(String.valueOf(deviceListBean.getSystemDeviceId()));
                }
            }
            bindingLight(ids);
        }else if(v == binding.btnSearch){
            binding.projectView.searchView.setText("");
            binding.areaView.searchView.setText("");
            binding.streeView.searchView.setText("");
            dosearch = true;
            pageNum = 1;
            queryControl(0,0,0,999999);
        }else if(v == binding.clearBtn){
            binding.searchView.setText("");
        }
    }

    @Override
    protected void initViews() {
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(), 1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new LightBindingAdapter();
        binding.myrecycleView.setAdapter(adapter);
        adapter.setOnItemClick(new LightBindingAdapter.OnItemClick() {
            @Override
            public void click(int index) {
//                MapLampCommonDevList.DataBean.ChildrenListBean srecordsBean = mapLampArrayList.get(index);
//                boolean select = srecordsBean.isSelect();
//                mapLampArrayList.get(index).setSelect(!select);
//
//                for (MapLampCommonDevList.DataBean.ChildrenListBean recordsBean : mapLampArrayList) {
//                    if(recordsBean != srecordsBean){
//                        recordsBean.setSelect(false);
//                    }
//                    if(recordsBean.isSelect()){
//                        selectrecordsBean = recordsBean;
//                    }
//                }
//                adapter.setmDatas(mapLampArrayList);


                RelationLightModel.DataBean.SystemDeviceListBean srecordsBean = deviceListBeans.get(index);
                boolean select = srecordsBean.isSelect();
                deviceListBeans.get(index).setSelect(!select);

                for (RelationLightModel.DataBean.SystemDeviceListBean deviceListBean : deviceListBeans) {
                    if(deviceListBean != srecordsBean){
                        deviceListBean.setSelect(false);
                    }

                    if(deviceListBean.isSelect()){
                        selectrecordsBean = deviceListBean;
                    }
                }
                adapter.setmDatas(deviceListBeans);


            }
        });
        adapter.setOnImageClick(new LightBindingAdapter.OnImageClick() {

            @Override
            public void imgclick(int index) {
                RelationLightModel.DataBean.SystemDeviceListBean srecordsBean = deviceListBeans.get(index);
                boolean select = srecordsBean.isSelect();
                deviceListBeans.get(index).setSelect(!select);

                for (RelationLightModel.DataBean.SystemDeviceListBean deviceListBean : deviceListBeans) {
                    if(deviceListBean != srecordsBean){
                        deviceListBean.setSelect(false);
                    }

                    if(deviceListBean.isSelect()){
                        selectrecordsBean = deviceListBean;
                    }
                }
                adapter.setmDatas(deviceListBeans);
            }
        });

        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(binding.searchView.getText().toString())){
                    if(dosearch){
                        pageNum = 1;
                        queryControl(0,0,0,30);
                    }
                    dosearch = false;
                    binding.clearBtn.setVisibility(View.GONE);
                }else {
                    binding.clearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        binding.projectView.searchView.setHint("项目");
        binding.areaView.searchView.setHint("区域");
        binding.streeView.searchView.setHint("街道");

        binding.projectView.searchView.setOnClickListener(this::onClick);
        binding.areaView.searchView.setOnClickListener(this::onClick);
        binding.streeView.searchView.setOnClickListener(this::onClick);
        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.btnSearch.setOnClickListener(this::onClick);
        binding.clearBtn.setOnClickListener(this::onClick);

        queryLocationList();
        queryControl(0,0,0,30);

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if(dosearch){
                return;
            }
            pageNum = 1;
            queryControl(0,0,0,30);
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(dosearch){
                return;
            }
            pageNum++;
            queryControl(0,0,0,30);
        });
    }

    /**
     * 查询分区 街道 站点
     */
    private void queryLocationList() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("hierarchy", 3);
        HttpRequest.GET(mActivity, HttpApi.API_DLM_LOCATION_AREA_LIST, parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {
                    mMessageLoader.dismiss();
                    areListData = workAreaListData.getData();
                    handelData();
                }
            }
        });
    }

    /**
     * 查询灯具
     */
//    private void queryLampList(int areaId,int streeId, int siteId ,int pagesize){
//        mMessageLoader.show();
//        JSONObject json = new JSONObject();
//        json.put("pageSize", 30);
//        json.put("pageNum", pageNum);
//        json.put("sortRule",0);
//        json.put("sortType",3);
//
//        if(areaId != 0 ){
//            json.put("areaId", areaId);
//        }
//        if(streeId != 0 ){
//            json.put("streetId", streeId);
//        }
//        if(siteId != 0){
//            json.put("siteId", siteId);
//        }
//
//        if(pagesize == 999999){
//            json.put("name", binding.searchView.getText().toString());
//        }
//
//        HttpRequest.JSONPOST(getActivity(), HttpApi.API_SL_LAMP_DEVICE_PAGE, json.toJSONString(), new BeanResponseListener<LampLightListBean>() {
//            @Override
//            public void onResponse(LampLightListBean bean, Exception error) {
//                mMessageLoader.dismiss();
//                binding.refreshLayout.finishRefresh();
//                binding.refreshLayout.finishLoadMore();
//                if(null == error){
//                    if(pageNum == 1){
//                        mapLampArrayList.clear();
//                    }
//                    for (LampLightListBean.DataBean.RecordsBean record : bean.getData().getRecords()) {
//                        MapLampCommonDevList.DataBean.ChildrenListBean children = new MapLampCommonDevList.DataBean.ChildrenListBean();
//                        children.setId(record.getId());
//                        children.setLampPostId(record.getLampPostId());
//                        children.setName(record.getName());
//                        children.setNum(record.getNum());
//                        children.setDeviceType(record.getDeviceType());
//
//                        MapLampCommonDevList.DataBean.ChildrenListBean.DlmRespDeviceBean dlmRespDeviceBean = new MapLampCommonDevList.DataBean.ChildrenListBean.DlmRespDeviceBean();
//                        if(record.getSlLampPost()!=null) {
//                            if(record.getSlLampPost().getName() != null){
//                                children.setSuperName(record.getSlLampPost().getName());
//                            }
//                            if(record.getSlLampPost().getLatitude() >0){
//                                dlmRespDeviceBean.setLampPostLatitude(record.getSlLampPost().getLatitude());
//                            }
//                            if(record.getSlLampPost().getLongitude() >0){
//                                dlmRespDeviceBean.setLampPostLongitude(record.getSlLampPost().getLongitude());
//                            }
//                            if(record.getSlLampPost().getLocation() != null){
//                                dlmRespDeviceBean.setSuperName(record.getSlLampPost().getLocation().toString());
//                            }
//                            children.setDlmRespDevice(dlmRespDeviceBean);
//
//                            mapLampArrayList.add(children);
//                        }
//                    }
//
//                    adapter.setmDatas(mapLampArrayList);
//                }
//            }
//        });
//    }

    /**
     * 查询可以绑定的灯具
     * @param ids
     */

    /**
     * 查询集控关联的灯具
     */
    private void queryControl(int areaId,int streeId, int siteId ,int pagesize) {
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

        if(pagesize == 999999){
            parameter.put("name", binding.searchView.getText().toString());
        }

        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_GETPLCBYLOCATIONCONTROID + device_info.id, parameter, new BeanResponseListener<RelationLightModel>() {
            @Override
            public void onResponse(RelationLightModel relationLightModel, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && relationLightModel != null) {
                    deviceListBeans.clear();
                    for (RelationLightModel.DataBean datum : relationLightModel.getData()) {
                        for (RelationLightModel.DataBean.SystemDeviceListBean systemDeviceListBean : datum.getSystemDeviceList()) {
                            if(systemDeviceListBean.getChosen() == 0 && systemDeviceListBean.getChosenByOther() ==0){
                                deviceListBeans.add(systemDeviceListBean);
                            }
                        }
                    }
                }

                adapter.setmDatas(deviceListBeans);
            }
        });
    }


    //绑定灯具
    private void bindingLight(List<String> ids){
        mMessageLoader.show();
        JSONObject json = new JSONObject();
        json.put("locationControlId", device_info.id);

        JSONArray array = new JSONArray();
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

    public void handelData(){
        //处理第一级项目的数据
        mProjectOption = new ArrayList<>();
        projectList = new ArrayList<>();
        for (WorkAreaListData.DataBean dataBean : areListData) {
            RegionBean temp = new RegionBean(dataBean.getId(), dataBean.getName());
            mProjectOption.add(dataBean.getName());
            projectList.add(temp);
        }
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentSelectBindingDeviceBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private void showProjectConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.projectView.searchView.setText(mProjectOption.get(options1));
            projectSelectOption = options1;
            areaId = projectList.get(options1).getId();
            streeId = 0;
            siteId = 0;
            pageNum = 1;

            mAreaOption = new ArrayList<>();
            areaList = new ArrayList<>();
            //处理第二级分区的数据
            for (WorkAreaListData.DataBean dataBean : areListData) {
                if (projectList.get(options1).getId() == dataBean.getId()) {
                    for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : dataBean.getChildrenList()) {
                        mAreaOption.add(childrenListBeanXX.getName());
                        areaList.add(new RegionBean(childrenListBeanXX.getId(), childrenListBeanXX.getName()));
                    }
                    break;
                }
            }

            queryControl(areaId,0,0,666666);
            return false;
        })
                .setTitleText("选择项目")
                .setSelectOptions(projectSelectOption)
                .build();
        pvOptions.setPicker(mProjectOption);
        pvOptions.show();
    }
    private void showAreaConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.areaView.searchView.setText(mAreaOption.get(options1));
            areaSelectOption = options1;
            streeId = areaList.get(options1).getId();
            siteId = 0;
            pageNum = 1;

            //处理第三级街道数据
            mStreeOption = new ArrayList<>();
            streeList = new ArrayList<>();
            for (WorkAreaListData.DataBean dataBean : areListData) {
                for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : dataBean.getChildrenList()) {
                    if(areaList.get(options1).getId() == childrenListBeanXX.getId()){
                        for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX childrenListBeanX : childrenListBeanXX.getChildrenList()) {
                            mStreeOption.add(childrenListBeanX.getName());
                            streeList.add(new RegionBean(childrenListBeanX.getId(), childrenListBeanX.getName()));
                            break;
                        }
                    }
                }
            }
            queryControl(areaId,streeId,0,666666);
            return false;
        })
                .setTitleText("选择分区")
                .setSelectOptions(areaSelectOption)
                .build();
        pvOptions.setPicker(mAreaOption);
        pvOptions.show();
    }
    private void showStreeConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.streeView.searchView.setText(mStreeOption.get(options1));
            streeSelectOption = options1;
            siteId = streeList.get(options1).getId();
            pageNum = 1;
            queryControl(areaId,streeId,siteId,666666);
            return false;
        })
                .setTitleText("选择街道")
                .setSelectOptions(streeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }
}
