package com.exc.roadlamp.device.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentSelectBindingDeviceBinding;
import com.exc.roadlamp.device.adapter.DeviceSelectAdapter;
import com.exc.roadlamp.device.adapter.LampChildrenListBean;
import com.exc.roadlamp.device.adapter.LampDeviceSelectAdapter;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.exc.roadlamp.work.workorder.addressselector.RegionBean;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.imageview.crop.Handle;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Page(name = "选择绑定灯杆")
public class DeviceBindingFragment extends MyBaseFragment implements View.OnClickListener{
    private FragmentSelectBindingDeviceBinding binding;
    private int projectSelectOption = 0;
    private int areaSelectOption = 0;
    private int streeSelectOption = 0;
    private int areaId;
    private int streeId;
    private int siteId;
    private int pageNum=1;
    private boolean dosearch = false;

    private List<LampDeviceListBean.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private List<LampDeviceListBean.DataBean.RecordsBean> searchrecordsBeanList = new ArrayList<>();
    private LampDeviceListBean.DataBean.RecordsBean selectrecordsBean;
    private LampDeviceSelectAdapter adapter;
    private MediaFilter mFilter;

    private List<RegionBean> projectList = new ArrayList<>();
    private List<RegionBean> areaList = new ArrayList<>();
    private List<RegionBean> streeList = new ArrayList<>();

    private List<String> mProjectOption = new ArrayList<>();
    private List<String> mAreaOption = new ArrayList<>();
    private List<String> mStreeOption = new ArrayList<>();

    //路灯杆位置列表
    private List<WorkAreaListData.DataBean> areListData;

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
            EventBus.getDefault().post(selectrecordsBean);
            popToBack();
        }else if(v == binding.btnSearch){
            binding.projectView.searchView.setText("");
            binding.areaView.searchView.setText("");
            binding.streeView.searchView.setText("");
            dosearch = true;
            pageNum = 1;
            queryLampList(0,0,0,999999);
        }else if(v == binding.clearBtn){
            binding.searchView.setText("");
        }
    }

    @Override
    protected void initViews() {
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(), 1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new LampDeviceSelectAdapter();
        binding.myrecycleView.setAdapter(adapter);
        adapter.setOnItemClick(new LampDeviceSelectAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                LampDeviceListBean.DataBean.RecordsBean srecordsBean = searchrecordsBeanList.get(index);
                boolean select = srecordsBean.isSelect();
                searchrecordsBeanList.get(index).setSelect(!select);

                for (LampDeviceListBean.DataBean.RecordsBean recordsBean : searchrecordsBeanList) {
                    if(recordsBean != srecordsBean){
                        recordsBean.setSelect(false);
                    }
                    if(recordsBean.isSelect()){
                        selectrecordsBean = recordsBean;
                    }
                }
                adapter.setmDatas(searchrecordsBeanList);
            }
        });
        adapter.setOnImageClick(new LampDeviceSelectAdapter.OnImageClick() {

            @Override
            public void imgclick(int index) {
                LampDeviceListBean.DataBean.RecordsBean srecordsBean = searchrecordsBeanList.get(index);
                boolean select = srecordsBean.isSelect();
                searchrecordsBeanList.get(index).setSelect(!select);

                for (LampDeviceListBean.DataBean.RecordsBean recordsBean : searchrecordsBeanList) {
                    if(recordsBean != srecordsBean){
                        recordsBean.setSelect(false);
                    }
                    if(recordsBean.isSelect()){
                        selectrecordsBean = recordsBean;
                    }
                }
                adapter.setmDatas(searchrecordsBeanList);
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
                        queryLampList(0,0,0,30);
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
        queryLampList(0,0,0,30);

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if(dosearch){
                return;
            }
            pageNum = 1;
            queryLampList(0,0,0,30);
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(dosearch){
                return;
            }
            pageNum++;
            queryLampList(0,0,0,30);
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
     * 查询灯杆
     */
    private void queryLampList(int areaId,int streeId, int siteId ,int pagesize) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageSize", pagesize);
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
            parameter.put("lampPostNameOrNum", binding.searchView.getText().toString());
        }

        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_PAGE, parameter, new BeanResponseListener<LampDeviceListBean>() {
            @Override
            public void onResponse(LampDeviceListBean lampDeviceListBean, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if(error == null) {
                    if(pageNum == 1){
                        recordsBeanList.clear();
                    }
                    if(lampDeviceListBean.getData().getRecords() != null){
                        recordsBeanList.addAll(lampDeviceListBean.getData().getRecords());
                    }
                    searchrecordsBeanList = recordsBeanList;
                    adapter.setmDatas(recordsBeanList);
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
        parameter.put("deviceTypeId", LampPoleDetailFragment.deviceType);
        parameter.put("siteId", "");
        parameter.put("streetId", "");
        parameter.put("areaId", "");
        parameter.put("lampPostNameOrNum", binding.searchView.getText().toString());
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
                    searchrecordsBeanList.clear();
                    for (MapLampCommonDevList.DataBean dataBean : mapLampCommonDevList.getData()) {
                        LampDeviceListBean.DataBean.RecordsBean bean = new LampDeviceListBean.DataBean.RecordsBean();
                        bean.setId(dataBean.getId());
                        bean.setSuperId(dataBean.getSuperId());
                        bean.setName(dataBean.getName());
                        bean.setNames(dataBean.names);
                        bean.setLampPostNum(dataBean.lampPostNum);

                        List<LampChildrenListBean> childrenListBeans = new ArrayList<>();
                        for (MapLampCommonDevList.DataBean.ChildrenListBean childrenListBean : dataBean.getChildrenList()) {
                            LampChildrenListBean childrenBean = new LampChildrenListBean();
                            childrenBean.setId(childrenListBean.getId());
                            childrenListBeans.add(childrenBean);
                        }
                        bean.setChildrenList(childrenListBeans);

                        searchrecordsBeanList.add(bean);
                    }
                    adapter.setmDatas(searchrecordsBeanList);
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

            queryLampList(areaId,0,0,666666);
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
            queryLampList(areaId,streeId,0,666666);
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
            queryLampList(areaId,streeId,siteId,666666);
            return false;
        })
                .setTitleText("选择街道")
                .setSelectOptions(streeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }

    public Filter getFilter() {
        if (mFilter == null){
            mFilter = new MediaFilter();
        }
        return mFilter;
    }

    class MediaFilter extends Filter{

        //在下面这个方法中定义过滤的规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();    //过滤结果
            List<LampDeviceListBean.DataBean.RecordsBean> list;       //用于存放交易的结果

            if (TextUtils.isEmpty(charSequence)){
                //如果过滤内容为空则显示全部,把备份的数据替换进去
            }else {

                list = new ArrayList<>();
                for (LampDeviceListBean.DataBean.RecordsBean jiaoYi:recordsBeanList){//遍历的对象是备份list 这里面才是全部元素
                    //下面是过滤的规则 从JiaoYi对象里面获得匹配内容然后判断是否contains过滤文本
                    if (jiaoYi.getName().contains(charSequence) || jiaoYi.getLampPostNum().contains(charSequence)){
                        list.add(jiaoYi);
                    }
                }
                results.values = list;                         //设置value 为list
                results.count = list.size();                    //设置大小
                searchrecordsBeanList = list;
            }

            return results;
        }

        //在下面的方法中 通知适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.setmDatas((ArrayList<LampDeviceListBean.DataBean.RecordsBean>) filterResults.values);; //通知适配器数据已经改变
        }

    }
}
