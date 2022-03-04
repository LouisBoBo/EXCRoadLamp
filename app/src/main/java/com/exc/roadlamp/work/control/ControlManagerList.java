package com.exc.roadlamp.work.control;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentControlListBinding;
import com.exc.roadlamp.device.adapter.DeviceListAdapter;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.ProjectList;
import com.exc.roadlamp.device.fragment.CenterControlDetailFragment;
import com.exc.roadlamp.device.fragment.DeviceCabinetAddFragment;
import com.exc.roadlamp.device.fragment.DeviceLampAddFragment;
import com.exc.roadlamp.device.fragment.LampLightDetailLightListFragment;
import com.exc.roadlamp.device.fragment.LampPoleDetailFragment;
import com.exc.roadlamp.device.fragment.PowerCabinetDetailFragment;
import com.exc.roadlamp.device.util.DevFiddlerUtils;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.adapter.ControlAdapter;
import com.exc.roadlamp.work.adapter.LightItemAdapter;
import com.exc.roadlamp.work.lightcontrol.LightControlManager;
import com.exc.roadlamp.work.lightcontrol.LightEditManager;
import com.exc.roadlamp.work.lightcontrol.LightStrategyManagerList;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "集中控制器")
public class ControlManagerList extends MyBaseFragment implements View.OnClickListener {
    private FragmentControlListBinding binding;
    private int pageNum = 1;
    private int pageSize = 30;
    private int siteId = -1;
    private int streetId = -1;
    private int areaId = -1;
    private int dosearch_screen =0;//0正常1搜索2筛选
    private boolean dosearch =false;
    private ControlAdapter listadapter;

    /**
     * 集控集合
     */
    private List<CenterControlListData.DataBean.RecordsBean> tempCenterControllist = new ArrayList<>();
    private List<CenterControlListData.DataBean.RecordsBean> searchtempCenterControllist = new ArrayList<>();

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.TextAction("下发场景") {
            @Override
            public void performAction(View view) {
                LightInfoBean infoBean = new LightInfoBean();
                infoBean.searchName = binding.editSearch.getText().toString();
                infoBean.streetId = streetId;
                infoBean.siteId = siteId;
                infoBean.areaId = areaId;
                openNewPage(ControlManagerScene.class,"device_info",infoBean);
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

            mMessageLoader.show();
            Parameter parameter = new Parameter();
            HttpRequest.GET(mActivity, HttpApi.GET_PROJECT_LIST, parameter, new BeanResponseListener<ProjectList>() {
                @Override
                public void onResponse(ProjectList projectList, Exception error) {
                    mMessageLoader.dismiss();
                    if (error == null) {
                        if (projectList.code != 200) {
                            XToastUtils.error(projectList.message);
                            return;
                        }
                        DevFiddlerUtils.showProjectFiddlerDialog(mActivity, projectList.getData(), new DevFiddlerUtils.OnFiddlerListener() {
                            @Override
                            public void onFiddler(int showDeviceTypeId, DevFiddlerUtils.SelectResult selectResult) {
                                areaId = selectResult.projectId;
                                pageNum =1;
                                initData();
                            }
                        });
                    }
                }
            });
        }else if(v == binding.addDevice){
            openNewPage(DeviceCabinetAddFragment.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {

        if(eventMessage.getEventEnum() == EventEnum.ADD_JIKONG_SUCCESS){
            pageNum = 1;
            initData();
        }
    }

    @Override
    protected void initViews() {
        //设备列表
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listadapter = new ControlAdapter(getActivity());
        binding.recyclerView.setAdapter(listadapter);
        listadapter.setOnItemClick(new ControlAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                lampinfoBean.id = searchtempCenterControllist.get(index).id;
                openNewPage(ControlRelationLight.class,"device_info",lampinfoBean);
            }
        });
        listadapter.setOnDeviceClick(new ControlAdapter.OnDeviceClick() {
            @Override
            public void click(int index) {
                DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                lampinfoBean.id = searchtempCenterControllist.get(index).id;
                openNewPage(ControlDetail.class,"device_info",lampinfoBean);
            }
        });
        listadapter.setOnDeleateClick(new ControlAdapter.OnDeleateClick() {
            @Override
            public void click(int index) {
                List<String> tids = new ArrayList<>();
                tids.add(String.valueOf(searchtempCenterControllist.get(index).getId()));

                DialogLoader.getInstance().showConfirmDialog(
                        getContext(),
                        getString(R.string.del_stratety_permission),
                        getString(R.string.tip_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            deleateControls(tids);
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

        initData();

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            //处于搜索时不能刷新
            if(dosearch){
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                return;
            }
            pageNum = 1;
            initData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //处于搜索时不能加载
            if(dosearch){
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                return;
            }
            pageNum++;
            initData();
        });
    }

    /**
     * 查询集控
     */
    private void initData() {

        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", pageSize);
        parameter.put("sortRule", 0);
        parameter.put("controlTypeIdList", "4,5,6,7,8");

        if (areaId >= 0) {
            parameter.put("newAreaId", areaId);//区域筛选
            parameter.put("areaId", areaId);//区域筛选
        }

        if(binding.editSearch.getText().toString().length()>0){
            parameter.put("name", binding.editSearch.getText().toString());
        }
        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_LIST, parameter, new BeanResponseListener<CenterControlListData>() {
            @Override
            public void onResponse(CenterControlListData powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if (null == error) {
                    if(pageNum == 1){
                        tempCenterControllist.clear();
                    }

                    if (powerCabinetList.getData().getRecords() != null)
                    {
//                        for (CenterControlListData.DataBean.RecordsBean record : powerCabinetList.getData().getRecords()) {
//                            if(record.getTypeId()>=5 && record.getTypeId() <=8)
//                            {
//                                tempCenterControllist.add(record);
//                            }
//                        }
                        tempCenterControllist.addAll(powerCabinetList.getData().getRecords());
                        searchtempCenterControllist = tempCenterControllist;
                    }

                    listadapter.setmDatas(searchtempCenterControllist);
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
        binding = FragmentControlListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
