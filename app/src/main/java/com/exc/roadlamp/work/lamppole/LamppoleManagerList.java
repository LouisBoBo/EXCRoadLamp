package com.exc.roadlamp.work.lamppole;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.maps.CameraUpdateFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentHomeWorkBinding;
import com.exc.roadlamp.databinding.FragmentManagerListBinding;
import com.exc.roadlamp.databinding.FragmentMyWorkOrderBinding;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.fragment.CenterControlDetailFragment;
import com.exc.roadlamp.device.fragment.DeviceAddFragment;
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
import java.util.stream.Collectors;

@Page(name = "灯杆管理")
public class LamppoleManagerList extends MyBaseFragment implements View.OnClickListener{
    FragmentManagerListBinding binding;
    ItemAdapter itemAdapter;
    private TitleBar titleBar;

    private int dosearch_screen =0;//0正常1搜索2筛选
    private int pageNum = 1;
    private int pageSize = 30;
    private int siteId = -1;
    private int streetId = -1;
    private int areaId = -1;

    /**
     * 灯杆集合
     */
    private List<LampDeviceListBean.DataBean.RecordsBean> dataBeanArrayList = new ArrayList<>();
    /**
     * 灯杆集合搜索用
     */
    private List<LampDeviceListBean.DataBean.RecordsBean> searchdataBeanArrayList = new ArrayList<>();
    /**
     * 灯杆集合筛选用
     */
    private List<MapLampCommonDevList.DataBean> mapLampForLightListFiddler = new ArrayList<>();

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentManagerListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        if (eventMessage.getEventEnum() == EventEnum.ADD_DENGGAN_SUCCESS) {
            pageNum = 1;
            initData();
        }
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

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
            DeviceInfoBean infoBean = new DeviceInfoBean();
            infoBean.device_type = LampPoleDetailFragment.deviceType;
            openNewPage(DeviceAddFragment.class,"device_info",infoBean);
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemAdapter = new ItemAdapter(getActivity());
        binding.recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClick(new ItemAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = LampPoleDetailFragment.deviceType;
                infoBean.id = searchdataBeanArrayList.get(index).getId();
                infoBean.name = searchdataBeanArrayList.get(index).getName();
                infoBean.num = searchdataBeanArrayList.get(index).getLampPostNum();
                infoBean.sitename = searchdataBeanArrayList.get(index).getNames();
                infoBean.longuide = searchdataBeanArrayList.get(index).getLampPostLongitude();
                infoBean.latitude = searchdataBeanArrayList.get(index).getLampPostLatitude();

                openNewPage(LampPoleEditFragment.class,"device_info",infoBean);
            }
        });
        itemAdapter.setOnDeviceClick(new ItemAdapter.OnDeviceClick() {
                                   @Override
            public void click(int index) {
                LampDeviceListBean.DataBean.RecordsBean recordsBean = searchdataBeanArrayList.get(index);
                LampDeviceListBean.DataBean.RecordsBean datarecordsBean = new LampDeviceListBean.DataBean.RecordsBean();
                datarecordsBean.setId(recordsBean.getId());
                datarecordsBean.setName(recordsBean.getName());

                openNewPage(DeviceLampControlFragment.class,"lamp_pole_info",datarecordsBean);
            }
        });
        itemAdapter.setOnDeleateClick(new ItemAdapter.OnDeleateClick() {
            @Override
            public void click(int index) {
                List<String> ids = new ArrayList<>();
                ids.add(String.valueOf(searchdataBeanArrayList.get(index).getId()));

                DialogLoader.getInstance().showConfirmDialog(
                        getContext(),
                        getString(R.string.del_stratety_permission),
                        getString(R.string.tip_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            deleateSlLamps(ids);
                        },
                        getString(R.string.tip_no),
                        (dialog, which) -> {
                            dialog.dismiss();
                        }
                );
            }
        });
        queryLampLightList();
        initData();

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
    }

    private void initData(){
        if(dosearch_screen == 2){
            queryLampList(areaId,streetId,siteId,"");//筛选
        }else if(dosearch_screen == 1){
            queryLampList(-1,-1,-1,binding.editSearch.getText().toString());//搜索
        }else
            queryLampList(-1,-1,-1,"");//正常
    }
    /**
     * 查询灯杆
     */
    private void queryLampList(int areaId,int streeId, int siteId ,String searchstr) {
        mMessageLoader.show();

        Parameter parameter = new Parameter();
        parameter.put("pageSize", pageSize);
        parameter.put("pageNum", pageNum);
        if(areaId != -1 ){
            parameter.put("areaId", areaId);
        }
        if(streeId != -1 ){
            parameter.put("streetId", streeId);
        }
        if(siteId != -1 ){
            parameter.put("siteId", siteId);
        }

        if(searchstr.length() > 0){
            parameter.put("lampPostNameOrNum", searchstr);
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
                        searchdataBeanArrayList = dataBeanArrayList;
                    }

                    itemAdapter.setmDatas(searchdataBeanArrayList);
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
                mMessageLoader.dismiss();
                if(error == null) {
                    XToastUtils.success("删除成功");
                    initData();//刷新数据
                }
            }
        });
    }
}
