package com.exc.roadlamp.device.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.CameraUpdateFactory;
import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.customview.PopupView;
import com.exc.roadlamp.customview.PopupViewAddDevice;
import com.exc.roadlamp.databinding.FragmentDevListBinding;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.adapter.DeviceListAdapter;
import com.exc.roadlamp.device.adapter.DeviceSelectAdapter;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.LampLightListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.AreaModel;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static android.app.Activity.RESULT_OK;

/**
 * 首页-设备-列表
 */

@Page(anim = CoreAnim.none)
public class DeviceListFragment extends MyBaseFragment implements RecyclerViewHolder.OnItemClickListener<String> , View.OnClickListener {

    private FragmentDevListBinding binding;
    private DeviceListAdapter listadapter;
    private ArrayList<RecyclerViewHolder> holderArrayList;
    private String[] setNames = {"灯杆", "灯具", "配电柜", "集控"};
    private int device_type;//0灯杆1灯具102配电柜103集控
    private int select_index;//选择的设备
    private PopupViewAddDevice topPopWindow;
    private MediaFilter mFilter;
    private boolean is_sort = true;//是否正序
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean dosearch =false;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private static final int TEST_SIZE = 400;
    private static final float TEST_WIDTH_WEIGHT = 0.5f;
    /**
     * 灯杆集合
     */
    private List<LampDeviceListBean.DataBean.RecordsBean> dataBeanArrayList = new ArrayList<>();
    private List<LampDeviceListBean.DataBean.RecordsBean> searchdataBeanArrayList = new ArrayList<>();

    /**
     * 灯具集合
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mapLampArrayList = new ArrayList<>();
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> searchmapLampArrayList = new ArrayList<>();

    /**
     * 在地图上显示的配电柜集合
     */
    private List<PowerCabinetList.DataBean.RecordsBean> mPowerCabinetList = new ArrayList<>();
    private List<PowerCabinetList.DataBean.RecordsBean> searchmPowerCabinetList = new ArrayList<>();

    /**
     * 集控集合
     */
    private List<CenterControlListData.DataBean.RecordsBean> tempCenterControllist = new ArrayList<>();
    private List<CenterControlListData.DataBean.RecordsBean> searchtempCenterControllist = new ArrayList<>();

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDevListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initViews() {

        EventBus.getDefault().register(this);

        device_type = LampPoleDetailFragment.deviceType;
        holderArrayList = new ArrayList<>();

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(manager);
        FlexboxLayoutAdapter adapter = new FlexboxLayoutAdapter(setNames);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        binding.topView.tvContent.setTextColor(Color.BLACK);
        binding.topView.tvText.setTextColor(Color.BLACK);
        binding.topView.tvContent.setTextSize(15);
        binding.topView.tvText.setTextSize(15);
        binding.topView.btnGo.setImageResource(R.mipmap.icon_zhengxu);

        //设备列表
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(),1);
        binding.myrecyclerView.setLayoutManager(device_manager);
        listadapter = new DeviceListAdapter();
        binding.myrecyclerView.setAdapter(listadapter);
        listadapter.setOnItemClick(new DeviceListAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = device_type;
                if(device_type == LampPoleDetailFragment.deviceType){
                    LampDeviceListBean.DataBean.RecordsBean recordsBean = searchdataBeanArrayList.get(index);
                    LampDeviceListBean.DataBean.RecordsBean datarecordsBean = new LampDeviceListBean.DataBean.RecordsBean();
                    datarecordsBean.setId(recordsBean.getId());
                    datarecordsBean.setName(recordsBean.getName());
                    openNewPage(DeviceLampControlFragment.class,"lamp_pole_info",datarecordsBean);

                }else if(device_type == LampLightDetailLightListFragment.deviceType){
                    infoBean.lamppostid = searchmapLampArrayList.get(index).getLampPostId();
                    infoBean.id = searchmapLampArrayList.get(index).getId();
                    infoBean.name = searchmapLampArrayList.get(index).getName();
                    infoBean.num = searchmapLampArrayList.get(index).getNum();
                    if(searchmapLampArrayList.get(index).getDlmRespDevice().getSuperName() != null){
                        infoBean.sitename = searchmapLampArrayList.get(index).getDlmRespDevice().getSuperName();
                    }
                    if(searchmapLampArrayList.get(index).getDlmRespDevice().getFactory() != null){
                        infoBean.Manufacturer = searchmapLampArrayList.get(index).getDlmRespDevice().getFactory();
                    }

                    infoBean.longuide = searchmapLampArrayList.get(index).getDlmRespDevice().getLampPostLongitude();
                    infoBean.latitude = searchmapLampArrayList.get(index).getDlmRespDevice().getLampPostLatitude();

                    openNewPage(DeviceDetailFragment.class,"device_info",infoBean);
                }else if(device_type == PowerCabinetDetailFragment.deviceType){
                    infoBean.id = searchmPowerCabinetList.get(index).getId();
                    infoBean.name = searchmPowerCabinetList.get(index).getName();
                    infoBean.num = searchmPowerCabinetList.get(index).getNum();
                    infoBean.longuide = searchmPowerCabinetList.get(index).getLongitude();
                    infoBean.latitude = searchmPowerCabinetList.get(index).getLatitude();

                    openNewPage(DeviceDetailFragment.class,"device_info",infoBean);
                }else if(device_type== CenterControlDetailFragment.deviceType){
                    infoBean.id = searchtempCenterControllist.get(index).getId();
                    infoBean.name = searchtempCenterControllist.get(index).getName();
                    infoBean.num = searchtempCenterControllist.get(index).getNum();
                    if(searchtempCenterControllist.get(index).powerCabinetInfo != null) {
                        infoBean.longuide = searchtempCenterControllist.get(index).powerCabinetInfo.getLongitude();
                        infoBean.latitude = searchtempCenterControllist.get(index).powerCabinetInfo.getLatitude();
                    }
                    openNewPage(DeviceDetailFragment.class,"device_info",infoBean);
                }

            }
        });
        listadapter.setOnImageClick(new DeviceSelectAdapter.OnImageClick() {
            @Override
            public void imgclick(int index) {
                select_index = index;
                showBottom();
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
                    if(dosearch){
                        pageNum = 1;
                        refreshData();
                    }
                    dosearch = false;
                    binding.clearBtn.setVisibility(View.GONE);
                }else {
                    binding.clearBtn.setVisibility(View.VISIBLE);
//                    getFilter().filter(binding.editSearch.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addDevice.setOnClickListener(this::onClick);
        binding.selectMore.setOnClickListener(this::onClick);
        binding.topView.btnGo.setOnClickListener(this::onClick);
        binding.btnSearch.setOnClickListener(this::onClick);
        binding.clearBtn.setOnClickListener(this::onClick);

        queryLampList(0,0,0,30);

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
            refreshData();
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
            refreshData();
        });
    }

    public void refreshData(){
        if(device_type == LampPoleDetailFragment.deviceType){
            queryLampList(0,0,0,30);
        }else if(device_type == LampLightDetailLightListFragment.deviceType){
            queryLampLightList(30);
        }else if(device_type == PowerCabinetDetailFragment.deviceType){
            queryAllPowerCabinet(30);
        }else if(device_type == CenterControlDetailFragment.deviceType){
            queryLampCenterControl(30);
        }
    }

    public class PolygonView extends View {

        private float widthWeight = 0.5f;
        private int defaultColor = Color.GREEN;
        private int touchColor = Color.BLACK;

        private boolean isTouching = false;

        public PolygonView(Context context) {
            super(context);
        }

        public PolygonView setWidthWeight(float widthWeight) {
            this.widthWeight = widthWeight;
            return this;
        }

        public PolygonView setDefaultColor(int defaultColor) {
            this.defaultColor = defaultColor;
            return this;
        }

        public PolygonView setTouchColor(int touchColor) {
            this.touchColor = touchColor;
            return this;
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.TRANSPARENT);//把整张画布绘制成透明

            Paint paint = new Paint();
            paint.setAntiAlias(true);//去锯齿
            if (isTouching) {//设置paint的颜色
                paint.setColor(touchColor);
            } else {
                paint.setColor(defaultColor);
            }
            paint.setStyle(Paint.Style.FILL);//设置paint的风格

            int width = this.getWidth();//获取到view的宽高
            int height = this.getHeight();

            Path path = new Path();    //通过path绘制平行四边形
            path.moveTo(width * (1 - widthWeight), 0);
            path.lineTo(width, 0);
            path.lineTo(width * widthWeight, height);
            path.lineTo(0, height);
            path.close();

            String text="安卓!";
            canvas.drawText(text, 0,0, paint);
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isTouching = true;
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    isTouching = false;
                    invalidate();
                    break;
            }
            super.onTouchEvent(event);

            //需要return true才能接收到ACTION_DOWN后面的事件
            return true;
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            //判断是否在平行四边形范围内，如果不在就不分发事件
            //isTouching:一旦触摸事件开始，一直到ACTION_UP,一直会处理事件
            if (!isTouching) {
                float x = event.getX();
                float y = event.getY();
                int width = getWidth();
                int height = getHeight();
                float blankWidth = width * (1 - widthWeight);
                if (x < blankWidth) {
                    if (y < x * height / blankWidth) {
                        return false;
                    }
                } else if (x > width * widthWeight) {
                    if (y > (x - width + blankWidth) * height / blankWidth) {
                        return false;
                    }
                }
            }

            return super.dispatchTouchEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        pageNum = 1;
        if(eventMessage.getEventEnum() == EventEnum.ADD_DENGGAN_SUCCESS){
            device_type = LampPoleDetailFragment.deviceType;
            queryLampList(0,0,0,30);
        } else if (eventMessage.getEventEnum() == EventEnum.ADD_DENGJU_SUCCESS) {
            device_type = LampLightDetailLightListFragment.deviceType;
            queryLampLightList(30);
        }else if(eventMessage.getEventEnum() == EventEnum.ADD_PEIDIANGUI_SUCCESS){
            device_type = PowerCabinetDetailFragment.deviceType;
            queryAllPowerCabinet(30);
        }else if(eventMessage.getEventEnum() == EventEnum.ADD_JIKONG_SUCCESS){
            device_type = CenterControlDetailFragment.deviceType;
            queryLampCenterControl(30);
        }
    }

    /**
     * 查询灯杆
     */
    private void queryLampList(int areaId,int streeId, int siteId ,int pagesize) {
        mMessageLoader.show();
        pageSize = pagesize;
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
            parameter.put("lampPostNameOrNum", binding.editSearch.getText().toString());
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

                    listadapter.setmDatas(dataBeanArrayList);
                }
            }
        });
    }

    /**
     * 查询灯具
     *
     */
    private void queryLampLightList(int pagesize) {
//        mMessageLoader.show();
//        pageSize = pagesize;
//        Parameter parameter = new Parameter();
//        parameter.put("deviceTypeId", device_type);
//        parameter.put("siteId", "");
//        parameter.put("streetId", "");
//        parameter.put("areaId", "");
//        parameter.put("lampPostNameOrNum", "");
//        parameter.put("pageSize", pagesize);
//        parameter.put("pageNum", pageNum);
//        parameter.put("isApp", 1);
//        if(pagesize == 999999){
//            parameter.put("lampPostNameOrNum", binding.editSearch.getText().toString());
//        }
//        HttpRequest.GET(mActivity, HttpApi.GET_ALL_LAMP_POLE, parameter, new BeanResponseListener<MapLampCommonDevList>() {
//            @Override
//            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
//                mMessageLoader.dismiss();
//                binding.refreshLayout.finishRefresh();
//                binding.refreshLayout.finishLoadMore();
//                if (null == error) {
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
//                    if(pageNum == 1){
//                        mapLampArrayList.clear();
//                    }
//                    for (MapLampCommonDevList.DataBean poleBean : mapLampCommonDevList.getData()) {
//                        for (MapLampCommonDevList.DataBean.ChildrenListBean children : poleBean.getChildrenList()) {
//                            children.getDlmRespDevice().setLampPostLatitude(poleBean.getLampPostLatitude());
//                            children.getDlmRespDevice().setLampPostLongitude(poleBean.getLampPostLongitude());
//                            children.getDlmRespDevice().setSuperName(poleBean.getSuperName());
//                            mapLampArrayList.add(children);
//                        }
//                    }
//                    searchmapLampArrayList = mapLampArrayList;
//                    listadapter.setmMaplampDatas(mapLampArrayList);
//                }
//
//            }
//        });
        queryLampLightListPage(pagesize);
    }

    private void queryLampLightListPage(int pagesize){
        mMessageLoader.show();
        JSONObject json = new JSONObject();
//        json.put("deviceTypeId",23);
        json.put("pageSize", pagesize);
        json.put("pageNum", pageNum);
        json.put("sortRule",0);
        json.put("sortType",3);

        if(pagesize == 999999){
            json.put("name", binding.editSearch.getText().toString());
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

                    searchmapLampArrayList = mapLampArrayList;
                    listadapter.setmMaplampDatas(mapLampArrayList);
                }
            }
        });
    }
    /**
     * 查询配电柜列表
     */
    private void queryAllPowerCabinet(int pagesize) {
        mMessageLoader.show();
        pageSize = pagesize;
        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", pagesize);

        if(pagesize == 999999){
            parameter.put("name", binding.editSearch.getText().toString());
        }

        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    if(pageNum == 1){
                        mPowerCabinetList.clear();
                    }
                    if(powerCabinetList.getData().getRecords() != null){
                        mPowerCabinetList.addAll(powerCabinetList.getData().getRecords());
                        searchmPowerCabinetList = mPowerCabinetList;
                    }
                    listadapter.setmCabinewDatas(mPowerCabinetList);
                }
            }
        });
    }

    /**
     * 查询集控
     */
    private void queryLampCenterControl(int pagesize) {

        mMessageLoader.show();
        pageSize = pagesize;
        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", pagesize);
        parameter.put("sortRule", 0);
        parameter.put("controlTypeIdList", "4,5");
        if(pagesize == 999999){
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
                        tempCenterControllist.addAll(powerCabinetList.getData().getRecords());
                        queryAllPowerCabinetForCenterControl(tempCenterControllist);
                        searchtempCenterControllist = tempCenterControllist;
                    }

                    listadapter.setmControlDatas(tempCenterControllist);
                }
            }
        });
    }

    /**
     * 查询配电柜列表（给集控增坐标）
     *
     * @param tempCenterControlList
     */
    private void queryAllPowerCabinetForCenterControl(List<CenterControlListData.DataBean.RecordsBean> tempCenterControlList) {
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 999999);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
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
                mMessageLoader.dismiss();
                if(error == null) {
                    XToastUtils.success("删除成功");
//                    myHandler.sendEmptyMessage(2000);

                    pageNum = 1;
                    refreshData();
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
                mMessageLoader.dismiss();
                if(error == null) {
                    XToastUtils.success("删除成功");
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
                mMessageLoader.dismiss();
                if(error == null) {
                    XToastUtils.success("删除成功");
                    myHandler.sendEmptyMessage(4000);
                }
            }
        });
    }

    //设备分类
    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemClick(View itemView, String item, int position) {
        pageNum = 1;
        binding.editSearch.setText("");
        dosearch = false;
        is_sort = true;
        binding.topView.btnGo.setImageResource(is_sort?R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);

        switch (position){
            case 0:
                device_type = LampPoleDetailFragment.deviceType;
                queryLampList(0,0,0,30);
                break;
            case 1:
                device_type = LampLightDetailLightListFragment.deviceType;
                queryLampLightList(30);
                break;
            case 2:
                device_type = PowerCabinetDetailFragment.deviceType;
                queryAllPowerCabinet(30);
                break;
            case 3:
                device_type = CenterControlDetailFragment.deviceType;
                queryLampCenterControl(30);
                break;
        }

        binding.myrecyclerView.scrollToPosition(0);
        for(int i =0;i<holderArrayList.size();i++){
            RecyclerViewHolder recyclerViewHolder = holderArrayList.get(i);
            if(i == position){
                recyclerViewHolder.findViewById(R.id.tv_set_name).setBackgroundResource(R.drawable.background_btn_blue);
                recyclerViewHolder.textColorId(R.id.tv_set_name, R.color.white );
            }else {
                recyclerViewHolder.findViewById(R.id.tv_set_name).setBackgroundResource(R.drawable.background_btn_select);
                recyclerViewHolder.textColorId(R.id.tv_set_name, R.color.black );
            }
        }
    }

    class FlexboxLayoutAdapter extends BaseRecyclerAdapter<String> {
        public FlexboxLayoutAdapter(String[] data) {
            super(data);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_node_set_list;
        }

        @Override
        protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
            holder.text(R.id.tv_set_name, item );
            holderArrayList.add(holder);
            if(position ==0){
                holder.findViewById(R.id.tv_set_name).setBackgroundResource(R.drawable.background_btn_blue);
                holder.textColorId(R.id.tv_set_name, R.color.white );
            }
        }
    }

    private FlexboxLayoutManager getFlexboxLayoutManager() {
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mActivity);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW_REVERSE);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 按正常方向换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }


    @Override
    public void onClick(View v) {
        if(v == binding.selectMore){
            openNewPage(DeviceSelectMoreFragment.class,"device_type",device_type);
        }else if (v == binding.btnSearch){
            pageNum = 1;
            dosearch = true;
            if(device_type == LampPoleDetailFragment.deviceType){
                queryLampList(0,0,0,999999);
            }else if(device_type == LampLightDetailLightListFragment.deviceType){
                queryLampLightList(999999);
            }else if(device_type == PowerCabinetDetailFragment.deviceType){
                queryAllPowerCabinet(999999);
            }else if(device_type == CenterControlDetailFragment.deviceType){
                queryLampCenterControl(999999);
            }
        }else if(v == binding.clearBtn){
            binding.editSearch.setText("");
            dosearch = false;
        }else if(v == binding.addDevice){
            if(device_type == LampLightDetailLightListFragment.deviceType){//如果是灯具就弹框
                showCodePopview();
            }else if(device_type == CenterControlDetailFragment.deviceType){
                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = device_type;
                openNewPage(DeviceCabinetAddFragment.class,"device_info",infoBean);
            }else {
                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = device_type;
                openNewPage(DeviceAddFragment.class,"device_info",infoBean);
            }

        }else if(v ==binding.topView.btnGo){
            if(device_type == LampPoleDetailFragment.deviceType){
                is_sort = !is_sort;
                binding.topView.btnGo.setImageResource(is_sort?R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);
                Collections.sort(searchdataBeanArrayList, new Comparator<LampDeviceListBean.DataBean.RecordsBean>(){
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
                listadapter.setmDatas(searchdataBeanArrayList);

            }else if(device_type == LampLightDetailLightListFragment.deviceType){

                is_sort = !is_sort;
                binding.topView.btnGo.setImageResource(is_sort?R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);
                Collections.sort(searchmapLampArrayList, new Comparator<MapLampCommonDevList.DataBean.ChildrenListBean>(){
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
                listadapter.setmMaplampDatas(searchmapLampArrayList);
            }else if(device_type == PowerCabinetDetailFragment.deviceType){
                is_sort = !is_sort;
                binding.topView.btnGo.setImageResource(is_sort?R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);
                Collections.sort(searchmPowerCabinetList, new Comparator<PowerCabinetList.DataBean.RecordsBean>(){
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
                listadapter.setmCabinewDatas(searchmPowerCabinetList);
            }else if(device_type == CenterControlDetailFragment.deviceType){
                is_sort = !is_sort;
                binding.topView.btnGo.setImageResource(is_sort?R.mipmap.icon_zhengxu:R.mipmap.icon_daoxu);
                Collections.sort(searchtempCenterControllist, new Comparator<CenterControlListData.DataBean.RecordsBean>(){
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
                listadapter.setmControlDatas(searchtempCenterControllist);
            }
        }else {
            switch (v.getId()) {
                case R.id.add_qrcode:

                    DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                    lampinfoBean.device_type = device_type;
                    lampinfoBean.isqrcode = true;
                    openNewPage(DeviceLampAddFragment.class,"device_info",lampinfoBean);

                    break;
                case R.id.add_manual:

                    DeviceInfoBean infoBean = new DeviceInfoBean();
                    infoBean.device_type = device_type;
                    infoBean.isqrcode = false;
                    openNewPage(DeviceLampAddFragment.class,"device_info",infoBean);

                    break;
                default:
                    break;
            }
            topPopWindow.dismiss();
        }
    }





    //底部弹框
    private void showBottom() {
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_normal, null);
        TextView scanmap = contentView.findViewById(R.id.tv_scanmap);
        ImageView scanmap_img = contentView.findViewById(R.id.tv_scanmap_img);

        if(device_type == LampPoleDetailFragment.deviceType){

            scanmap.setText("编辑灯杆信息");
            scanmap_img.setVisibility(View.GONE);
        }else {
            scanmap.setText("查看地图");
            scanmap_img.setVisibility(View.VISIBLE);
        }

        contentView.findViewById(R.id.tv_scanmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//查看地图
                bottomDialog.dismiss();

                if(device_type == LampPoleDetailFragment.deviceType){

                    DeviceInfoBean infoBean = new DeviceInfoBean();
                    infoBean.device_type = device_type;
                    infoBean.id = searchdataBeanArrayList.get(select_index).getId();
                    infoBean.name = searchdataBeanArrayList.get(select_index).getName();
                    infoBean.num = searchdataBeanArrayList.get(select_index).getLampPostNum();
                    infoBean.sitename = searchdataBeanArrayList.get(select_index).getNames();
                    infoBean.longuide = searchdataBeanArrayList.get(select_index).getLampPostLongitude();
                    infoBean.latitude = searchdataBeanArrayList.get(select_index).getLampPostLatitude();

                    openNewPage(LampPoleEditFragment.class,"device_info",infoBean);
                    return;
                }else {
                    //切换到地图界面
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.SHOW_DEVICE_MAP_FROMLIST);
                    EventBus.getDefault().post(eventMessage);


                    //在地图上定位所选择的设备
                    DevMapSearchResultListBean mapbean = new DevMapSearchResultListBean();
                    mapbean.setDeviceType(device_type);

                    if(device_type == LampLightDetailLightListFragment.deviceType){
                        mapbean.setDevId(searchmapLampArrayList.get(select_index).getId());
                        mapbean.setLampPostId(searchmapLampArrayList.get(select_index).getLampPostId());
                        mapbean.setDevName(searchmapLampArrayList.get(select_index).getName());
                    }else if(device_type == PowerCabinetDetailFragment.deviceType){
                        mapbean.setDevId(searchmPowerCabinetList.get(select_index).getId());
                        mapbean.setDevName(searchmPowerCabinetList.get(select_index).getName());
                    }else if(device_type == CenterControlDetailFragment.deviceType){
                        mapbean.setDevId(searchtempCenterControllist.get(select_index).getId());
                        mapbean.setDevName(searchtempCenterControllist.get(select_index).getName());
                    }

                    EventMessage<DevMapSearchResultListBean> mapeventMessage = new EventMessage<>();
                    mapeventMessage.setEventEnum(EventEnum.DEV_MAP_SEARCH_RESULT_LIST_CLICK);
                    mapeventMessage.setData(mapbean);
                    EventBus.getDefault().post(mapeventMessage);
                }
            }
        });
        contentView.findViewById(R.id.tv_deleate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//删除
                bottomDialog.dismiss();
                switch (device_type) {
                    case LampPoleDetailFragment.deviceType:

                        List<String> ids = new ArrayList<>();
                        ids.add(String.valueOf(searchdataBeanArrayList.get(select_index).getId()));
                        deleateSlLamps(ids);
                        break;
                    case LampLightDetailLightListFragment.deviceType:
                        List<String> dds = new ArrayList<>();
                        dds.add(String.valueOf(searchmapLampArrayList.get(select_index).getId()));
                        deleateSlDevices(dds);
                        break;
                    case PowerCabinetDetailFragment.deviceType:

                        List<String> cids = new ArrayList<>();
                        cids.add(String.valueOf(searchmPowerCabinetList.get(select_index).getId()));
                        deleateCabinets(cids);
                        break;
                    case CenterControlDetailFragment.deviceType:

                        List<String> tids = new ArrayList<>();
                        tids.add(String.valueOf(searchtempCenterControllist.get(select_index).getId()));
                        deleateControls(tids);
                        break;
                }
            }
        });
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void showCodePopview(){
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_qrcode, null);
        contentView.findViewById(R.id.qcode_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//扫码
                bottomDialog.dismiss();

                DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                lampinfoBean.device_type = device_type;
                lampinfoBean.isqrcode = true;
                openNewPage(DeviceLampAddFragment.class,"device_info",lampinfoBean);
            }
        });
        contentView.findViewById(R.id.shoudong_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//手动
                bottomDialog.dismiss();

                DeviceInfoBean infoBean = new DeviceInfoBean();
                infoBean.device_type = device_type;
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
    /**
     * 显示右上角popup菜单
     */
    private void showTopRightPopMenu() {
        if (topPopWindow == null) {
            //(activity,onclicklistener,width,height)
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            topPopWindow = new PopupViewAddDevice(getActivity(), this, width, 200);
            //监听窗口的焦点事件，点击窗口外面则取消显示
            topPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        topPopWindow.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        topPopWindow.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        topPopWindow.showAsDropDown( binding.addDevice, 0, 20);
        //如果窗口存在，则更新
        topPopWindow.update();
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
            List<MapLampCommonDevList.DataBean.ChildrenListBean> maplamplist;
            List<PowerCabinetList.DataBean.RecordsBean> cabinetlist;
            List<CenterControlListData.DataBean.RecordsBean> controllist;
            if (TextUtils.isEmpty(charSequence)){
                //如果过滤内容为空则显示全部,把备份的数据替换进去
            }else {

                if(device_type == LampPoleDetailFragment.deviceType){
                    list = new ArrayList<>();
                    for (LampDeviceListBean.DataBean.RecordsBean jiaoYi:searchdataBeanArrayList){//遍历的对象是备份list 这里面才是全部元素
                        //下面是过滤的规则 从JiaoYi对象里面获得匹配内容然后判断是否contains过滤文本
                        if (jiaoYi.getName().contains(charSequence) || jiaoYi.getLampPostNum().contains(charSequence)){
                            list.add(jiaoYi);
                        }
                    }
                    results.values = list;                         //设置value 为list
                    results.count = list.size();                    //设置大小
                }else if(device_type == LampLightDetailLightListFragment.deviceType){
                    maplamplist = new ArrayList<>();
                    for (MapLampCommonDevList.DataBean.ChildrenListBean jiaoYi:searchmapLampArrayList){//遍历的对象是备份list 这里面才是全部元素
                        //下面是过滤的规则 从JiaoYi对象里面获得匹配内容然后判断是否contains过滤文本
                        if (jiaoYi.getName().contains(charSequence) || jiaoYi.getNum().contains(charSequence)){
                            maplamplist.add(jiaoYi);
                        }
                    }
                    results.values = maplamplist;                         //设置value 为list
                    results.count = maplamplist.size();                    //设置大小
                }else if(device_type == PowerCabinetDetailFragment.deviceType){
                    cabinetlist = new ArrayList<>();
                    for (PowerCabinetList.DataBean.RecordsBean jiaoYi:searchmPowerCabinetList){//遍历的对象是备份list 这里面才是全部元素
                        //下面是过滤的规则 从JiaoYi对象里面获得匹配内容然后判断是否contains过滤文本
                        if (jiaoYi.getName().contains(charSequence) || jiaoYi.getNum().contains(charSequence)){
                            cabinetlist.add(jiaoYi);
                        }
                    }
                    results.values = cabinetlist;                         //设置value 为list
                    results.count = cabinetlist.size();                    //设置大小
                }else if(device_type == CenterControlDetailFragment.deviceType){
                    controllist = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean jiaoYi:searchtempCenterControllist){//遍历的对象是备份list 这里面才是全部元素
                        //下面是过滤的规则 从JiaoYi对象里面获得匹配内容然后判断是否contains过滤文本
                        if (jiaoYi.getName().contains(charSequence) || jiaoYi.getNum().contains(charSequence)){
                            controllist.add(jiaoYi);
                        }
                    }
                    results.values = controllist;                         //设置value 为list
                    results.count = controllist.size();                    //设置大小
                }

            }

            return results;
        }

        //在下面的方法中 通知适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(device_type == LampPoleDetailFragment.deviceType){
                listadapter.setmDatas((ArrayList<LampDeviceListBean.DataBean.RecordsBean>) filterResults.values);; //通知适配器数据已经改变
            }else if(device_type == LampLightDetailLightListFragment.deviceType){
                listadapter.setmMaplampDatas((ArrayList<MapLampCommonDevList.DataBean.ChildrenListBean>) filterResults.values);; //通知适配器数据已经改变
            }else if(device_type == PowerCabinetDetailFragment.deviceType){
                listadapter.setmCabinewDatas((ArrayList<PowerCabinetList.DataBean.RecordsBean>) filterResults.values);; //通知适配器数据已经改变
            }else if(device_type == CenterControlDetailFragment.deviceType){
                listadapter.setmControlDatas((ArrayList<CenterControlListData.DataBean.RecordsBean>) filterResults.values);; //通知适配器数据已经改变
            }
        }

    }

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    searchdataBeanArrayList.remove(select_index);
                    listadapter.setmDatas(searchdataBeanArrayList);
                    break;
                case 2000:
                    searchmapLampArrayList.remove(select_index);
                    listadapter.setmMaplampDatas(searchmapLampArrayList);
                    break;
                case 3000:

                    searchmPowerCabinetList.remove(select_index);
                    listadapter.setmCabinewDatas(searchmPowerCabinetList);
                    break;
                case 4000:

                    searchtempCenterControllist.remove(select_index);
                    listadapter.setmControlDatas(searchtempCenterControllist);
                    break;
            }

            super.handleMessage(msg);
        }
    };
}
