package com.exc.roadlamp.device.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDeviveMapBinding;
import com.exc.roadlamp.device.bean.CenterControlAllData;
import com.exc.roadlamp.device.bean.ManholeList;
import com.exc.roadlamp.device.bean.ProjectList;
import com.exc.roadlamp.device.util.CenterControlUtil;
import com.exc.roadlamp.device.util.DevFiddlerUtils;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.PowerCabinetDetail;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.cluster.ClusterClickListener;
import com.exc.roadlamp.device.cluster.ClusterItem;
import com.exc.roadlamp.device.cluster.ClusterOverlay;
import com.exc.roadlamp.device.cluster.ClusterRender;
import com.exc.roadlamp.device.cluster.RegionItem;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.ImageUtils;
import com.exc.roadlamp.utils.PermissionInterceptor;
import com.exc.roadlamp.utils.PermissionTypeEnum;
import com.exc.roadlamp.utils.XToastUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.xuexiang.xutil.data.SPUtils.getSharedPreferences;

/**
 * 首页-设备-地图
 */
@Page(anim = CoreAnim.none)
public class DeviceMapFragment extends MyBaseFragment implements View.OnClickListener, AMap.OnMapLoadedListener, ClusterClickListener, ClusterRender {
    private TextureMapView mapView;
    private ClusterOverlay mClusterOverlay;
    /**
     * 聚合点的大小（dp）
     */
    private int clusterRadius = 70;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<>();
    private FragmentDeviveMapBinding binding;
    private AMap aMap;
    private LatLng defCurrentCityPoint = new LatLng(30.973649, 111.896328);// 默认的中心点经纬度
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private int siteId = -1;
    private int streetId = -1;
    private int areaId = -1;

    private boolean is_search = false;//是否搜索

    private ImageView switchView;
    private SeekBar seekBar;
    private int all_progress =0;
    private boolean all_check = false;



    /**
     * 在地图上显示的灯杆集合
     */
    private List<MapLampCommonDevList.DataBean> mLampPoleList = new ArrayList<>();

    /**
     * 在地图上显示的灯杆集合
     */
    private List<MapLampCommonDevList.DataBean> mLampPoleListForFiddler = new ArrayList<>();


    /**
     * 在地图上显示的灯具集合
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mLampLightList = new ArrayList<>();


    /**
     * 有灯具的灯杆集合(筛选用)
     */
    private List<MapLampCommonDevList.DataBean> mapLampForLightListFiddler = new ArrayList<>();

    /**
     * 在地图上显示的配电柜集合
     */
    private List<PowerCabinetList.DataBean.RecordsBean> mPowerCabinetList = new ArrayList<>();


    /**
     * 在地图上显示的集控集合
     */
    private List<CenterControlListData.DataBean.RecordsBean> mCenterControlList = new ArrayList<>();

    /**
     * 在地图上显示的井盖集合
     */
    private List<ManholeList.DataBean.RecordsBean> mManholeList = new ArrayList<>();

    /**
     * 当前在地图上显示的点
     */
    private ArrayList<ClusterItem> clusterItems;
    private BottomSheetBehavior mBottomSheetBehavior;
    private PermissionInterceptor mPermissionInterceptor;
    /**
     * 当前具体位置
     */
    private AMapLocation mLocation;

    /**
     * 当前正在显示的设备类型     1-100都是灯杆上的设备  101是集控
     * 0 灯杆
     * 1 灯具
     * 2 AP
     * 3 广播
     * 4 摄像头
     * 5 显示屏
     * 7 传感器
     */
    private int showDeviceTypeId = 10000;
    /**
     * 搜索结果里面选择的灯杆
     */
    private MapLampCommonDevList.DataBean poleSearchResult;


    /**
     * 搜索结果里面选择的灯具所在的灯杆
     */
    private MapLampCommonDevList.DataBean lightPoleSearchResult;
    private MapLampCommonDevList.DataBean lightSearchResult = null;


    /**
     * 搜索里面选择的配电柜
     */
    private PowerCabinetList.DataBean.RecordsBean powerCabinetSearchResult;

    /**
     * 搜索结果里面选择的集控
     */
    private CenterControlListData.DataBean.RecordsBean centerControlSearchResult;

    /**
     * 搜索结果里面选择的井盖
     */
    private ManholeList.DataBean.RecordsBean manholeSearchResult;

    /**
     * 设备详情弹窗高度
     */
    private int bottomSheetBehaviorHeight;


    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDeviveMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        initEventBus();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.DEV_MAP_SEARCH_RESULT_LIST_CLICK) {
            DevMapSearchResultListBean bean = (DevMapSearchResultListBean) eventMessage.getData();

            is_search = true;
            switch (bean.getDeviceType()) {
                case LampPoleDetailFragment.deviceType://灯杆
                    binding.lightPole.setVisibility(View.VISIBLE);
                    binding.rlLampPole.setChecked(true);
                    //重新查询灯杆后弹出搜索到的灯杆详情
                    queryLampPoleList(false,bean);

                    break;

                case LampLightDetailLightListFragment.deviceType://灯具
                    binding.lightPole.setVisibility(View.VISIBLE);
                    binding.rlLampLight.setChecked(true);
                    queryLampLightList(false,bean);

                    break;

                case PowerCabinetDetailFragment.deviceType://配电柜
                    binding.lightPole.setVisibility(View.GONE);
                    binding.rlPowerCabinet.setChecked(true);
                    queryAllPowerCabinet(false,bean);

                    break;

                case CenterControlDetailFragment.deviceType://集控
                    binding.lightPole.setVisibility(View.GONE);
                    binding.rlController.setChecked(true);
                    queryAllCenterControl(-1,bean);

                    break;

                case ManholeDetailFragment.deviceType:
                    binding.lightPole.setVisibility(View.GONE);
                    binding.rlManhole.setChecked(true);
                    queryManholeList(false,bean);
                    break;
            }
            return;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Point outSize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        bottomSheetBehaviorHeight = (outSize.y) / 2 - 200;

        //先拿位置
        MapsInitializer.updatePrivacyShow(mActivity, true, true);
        MapsInitializer.updatePrivacyAgree(mActivity,true);
        checkPermissionAndSetMapCenterPos(savedInstanceState);

    }


    /**
     * 权限申请检查
     *
     * @param savedInstanceState
     */
    public void checkPermissionAndSetMapCenterPos(Bundle savedInstanceState){
        mPermissionInterceptor = new PermissionInterceptor(this);
        mPermissionInterceptor.setPermissionsPrompt("获取不到定位授权，APP将无法正常使用，请允许APP获取定位权限！");
        mPermissionInterceptor.setPermissionType(PermissionTypeEnum.ACCESS_COARSE_LOCATION);
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,


        };
        mPermissionInterceptor.setPermissions(permissions)
                .isForcePermission(true)//是否强制
                .setPermissionListener(new PermissionInterceptor.OnPermissionResult() {
                    @Override
                    public void onPermissionAllAllow() {

                        try {
                            AMapLocationClient locationClientSingle = new AMapLocationClient(mActivity);
                            AMapLocationClientOption locationClientOption = new AMapLocationClientOption();

                            //使用单次定位
                            locationClientOption.setOnceLocation(true);
                            // 地址信息
                            locationClientOption.setNeedAddress(true);
                            locationClientOption.setLocationCacheEnable(false);
                            locationClientSingle.setLocationOption(locationClientOption);
                            locationClientSingle.setLocationListener(location -> {

                                AMapOptions aOptions = new AMapOptions();

                                if (location != null && location.getLatitude() > 0) {//拿到了位置信息
                                    mLocation = location;
                                    defCurrentCityPoint = new LatLng(location.getLatitude(), location.getLongitude());
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 13f, 0, 0));

                                } else {//没有拿到位置信息
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 5f, 0, 0));

                                }

                                mapView = new TextureMapView(getContext(), aOptions);
                                mapView.onCreate(savedInstanceState);
                                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                binding.reMap.addView(mapView, mParams);
                                if (aMap == null) {
                                    aMap = mapView.getMap();
                                }
                                mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
                                mUiSettings.setZoomControlsEnabled(false);
                                mUiSettings.setMyLocationButtonEnabled(false);

                                aMap.setOnMapLoadedListener(DeviceMapFragment.this::onMapLoaded);


                            });
                            locationClientSingle.startLocation();
                        }catch (Exception e){
                            e.printStackTrace();
                        };

                    }

                    @Override
                    public void onPermissionReject(String[] permissions) {
                    }
                })
                .checkPermission();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionInterceptor.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onMapLoaded() {
//        checkPermissionAndSetMapCenterPos();

        initMapData();

    }

    //初始化地图页面数据
    private void initMapData() {


        binding.tvSearch.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.llFiddler.setOnClickListener(this);
        binding.llBackCurrentLocation.setOnClickListener(this);
        binding.lightPole.setOnClickListener(this::onClick);
        binding.lightPole.setVisibility(View.VISIBLE);

        //处理用户的小蓝点
        initMyLocationBluePoint();

        //搜索、筛选
        binding.nestedGroup1.setOnCheckedChangeListener((group, checkedId) -> {
                    //切换设备
                    switch (checkedId) {
                        case R.id.rl_lamp_ser://搜索
                            switch (showDeviceTypeId) {
                                case LampPoleDetailFragment.deviceType:
                                case LampLightDetailLightListFragment.deviceType:
                                case CenterControlDetailFragment.deviceType:
                                case PowerCabinetDetailFragment.deviceType:
                                case ManholeDetailFragment.deviceType:
                                    openNewPage(DevMapSearchFragment.class, "showDeviceTypeId", showDeviceTypeId);
                                    binding.rlLampSer.setChecked(false);
                                    break;
                                default:
                                    binding.rlLampSer.setChecked(false);
                                    break;
                            }

                            break;
                        case R.id.rl_lamp_sel://筛选
                            switch (showDeviceTypeId) {
                                case LampPoleDetailFragment.deviceType:
                                case LampLightDetailLightListFragment.deviceType:
                                case CenterControlDetailFragment.deviceType:
                                    fiddlerClick();
                                    break;
                                default:
                                    XToastUtils.error("此类设备暂不支持筛选");
                                    binding.rlLampSel.setChecked(false);
                                    break;
                            }

                            break;
                    }
                }
        );

        //默认选中灯杆
        binding.rlLampPole.setChecked(true);
        binding.nestedGroup2.setOnCheckedChangeListener((group, checkedId) -> {
                    if(is_search){
                        return;
                    }
                    mBackDrawAbles.clear();
                    areaId = -1;
                    streetId = -1;
                    siteId = -1;
                    //切换设备
                    switch (checkedId) {
                        case R.id.rl_lamp_pole://灯杆
                            binding.lightPole.setVisibility(View.VISIBLE);
                            queryLampPoleList(false,null);
                            break;
                        case R.id.rl_lamp_light://灯具   //BottomSheet测试
                            binding.lightPole.setVisibility(View.VISIBLE);
                            queryLampLightList(false,null);
                            break;
                        case R.id.rl_power_cabinet://配电柜
                            binding.lightPole.setVisibility(View.GONE);
                            queryAllPowerCabinet(false,null);
                            break;
                        case R.id.rl_controller://集控
                            binding.lightPole.setVisibility(View.GONE);
                            queryAllCenterControl(-1,null);
                            break;
                        case R.id.rl_manhole://井盖
                            binding.lightPole.setVisibility(View.GONE);
                            queryManholeList(false,null);
                            break;
                    }
                }
        );
        mBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);


        //默认查询灯杆列表
        queryLampPoleList(false,null);
        //这里使用的是界面手势识别器,可以识别界面的单击事件,来给Behavior设置相应的属性来控制隐藏.
//        mGestureDetector = new GestureDetector(mActivity,
//                new GestureDetector.SimpleOnGestureListener() {
//                    //处理单击事件 -- 控制面板的显示or隐藏
//                    @Override
//                    public boolean onSingleTapConfirmed(MotionEvent e) {
//                        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                            return true;
//                        }
//                        return super.onSingleTapConfirmed(e);
//                    }
//                });

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                EventMessage eventMessage = new EventMessage();
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    eventMessage.setEventEnum(EventEnum.SHOW_DEVICE_MAP_PAGE_SWITCH_BTN);
                } else {
                    eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
                }
                EventBus.getDefault().post(eventMessage);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Log.e("onSlide-mBottomSheetBehavior", slideOffset + "");
            }
        });
    }

    /**
     * 处理用户位置的小蓝点
     */
    private void initMyLocationBluePoint() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


    }

    @Override
    protected void onTouchDownAction(MotionEvent ev) {
//        mGestureDetector.onTouchEvent(ev);
        super.onTouchDownAction(ev);
    }

    /**
     * 查询灯杆
     *
     * @param isFiddler 是否是筛选结果的处理
     */
    private void queryLampPoleList(boolean isFiddler,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("deviceTypeId", LampPoleDetailFragment.deviceType);
//        parameter.put("deviceTypeId", 1);//测试用

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


                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_lamp_pole));
                    binding.tvDevCountName.setText("灯杆总数：");
                    binding.tvDevCount.setText(mapLampCommonDevList.getData().size() + "");

                    if (!isFiddler) {
                        mLampPoleListForFiddler.clear();
                        mLampPoleListForFiddler = mapLampCommonDevList.getData();
                    }


                    //过滤掉没有坐标的灯杆
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));
                    mLampPoleList = mapLampCommonDevList.getData();

                    if(bean != null){
                        //找到对应的灯杆
                        for (MapLampCommonDevList.DataBean pole : mLampPoleList) {
                            if (bean.getDevId() == pole.getId()) {
                                poleSearchResult = pole;
                                break;
                            }
                        }
                        if (null == poleSearchResult) {
                            XToastUtils.error("未找到此设备");
                        }

                    }

                    if(!is_search){
                         Log.i("测试用灯杆","来过了");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));

                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.SHOW_LAMP_POLE);

                    showDeviceTypeId = LampPoleDetailFragment.deviceType;
                    binding.tvSearch.setText("搜索灯杆");

                }
            }
        });
    }

    /**
     * 查询路灯
     *
     * @param isFiddler 是否是筛选结果的处理
     */
    private void queryLampLightList(boolean isFiddler,DevMapSearchResultListBean bean) {
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
                    if (!isFiddler)
                    {
                        mapLampForLightListFiddler.clear();
                        mapLampForLightListFiddler = mapLampCommonDevList.getData();
                    }
                    //过滤掉没有坐标的灯具
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));


                    //过滤掉没有灯具的灯杆
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(null == dataBean.getChildrenList()
                                    || dataBean.getChildrenList().size() == 0
                            )).collect(Collectors.toList()));


                    mLampLightList.clear();
                    for (MapLampCommonDevList.DataBean poleBean : mapLampCommonDevList.getData()) {
                        for (MapLampCommonDevList.DataBean.ChildrenListBean children : poleBean.getChildrenList()) {
                            children.getDlmRespDevice().setLampPostLatitude(poleBean.getLampPostLatitude());
                            children.getDlmRespDevice().setLampPostLongitude(poleBean.getLampPostLongitude());
                            mLampLightList.add(children);
                        }
                    }


                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_lamp_dev));
                    binding.tvDevCountName.setText("灯具总数：");
                    binding.tvDevCount.setText(mLampLightList.size() + "");

                    Log.i("测试用", String.valueOf(mLampPoleList.size()));
                    if(bean !=null){
                        //找到搜索的灯具所在的灯杆
                        for (MapLampCommonDevList.DataBean pole : mapLampCommonDevList.getData()) {
                            if (bean.getLampPostId() == pole.getId()) {
                                lightPoleSearchResult = pole;
                                lightSearchResult = new MapLampCommonDevList.DataBean(); ;

                                List<MapLampCommonDevList.DataBean.ChildrenListBean> lampLightlist = new ArrayList<>();
                                lightSearchResult.setChildrenList(pole.getChildrenList());
                                lightSearchResult.setName(pole.name);
                                lightSearchResult.setAreaName(pole.areaName);
                                lightSearchResult.setStreetName(pole.streetName);
                                lightSearchResult.setSuperName(pole.superName);
                                lightSearchResult.setLampPostNum(pole.lampPostNum);
                                lightSearchResult.setNames(pole.names);

                                List<MapLampCommonDevList.DataBean.ChildrenListBean>lightlist = new ArrayList<>();
                                for (MapLampCommonDevList.DataBean.ChildrenListBean childrenListBean : pole.getChildrenList()) {
                                    if(childrenListBean.getId() == bean.getDevId()){
                                        lightlist.add(childrenListBean);
                                        lightSearchResult.setChildrenList(lightlist);
                                        break;
                                    }
                                }
                                break;
                            }
                        }

                        if (null == lightPoleSearchResult) {
                            XToastUtils.error("未找到此设备");
                        }

                    }

                    if(!is_search) {
                        Log.i("测试用灯具","来过了");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));

                        }
                    }

                    initMapLampMarkerS(DevTypeEnum.SHOW_LAMP_LIGHT);

                    showDeviceTypeId = LampLightDetailLightListFragment.deviceType;
                    binding.tvSearch.setText("搜索灯具");

                }

            }
        });
    }


    /**
     * 查询集控列表（需要拿到配电柜列表数据和集控列表合并）
     */
    private void queryAllCenterControl(int newAreaId,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 99999999);
        parameter.put("controlTypeIdList", "4,5");
        if (newAreaId >= 0) {
            parameter.put("newAreaId", newAreaId);//区域筛选
            parameter.put("areaId", newAreaId);//区域筛选
        }
        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_LIST, parameter, new BeanResponseListener<CenterControlListData>() {
            @Override
            public void onResponse(CenterControlListData powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    List<CenterControlListData.DataBean.RecordsBean> tempCenterControl = powerCabinetList.getData().getRecords();
                    if (tempCenterControl == null || tempCenterControl.size() == 0) {
                        mMessageLoader.dismiss();
                        XToastUtils.error("暂无集控");
                        return;
                    }
                    queryAllPowerCabinetForCenterControl(tempCenterControl,bean);
                }
            }
        });
    }

    /**
     * 查询配电柜列表（给集控增坐标）
     *
     * @param tempCenterControlList
     */
    private void queryAllPowerCabinetForCenterControl(List<CenterControlListData.DataBean.RecordsBean> tempCenterControlList,DevMapSearchResultListBean bean) {
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 0);
        parameter.put("pageSize", 99999999);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    List<PowerCabinetList.DataBean.RecordsBean> tempPowerCabinetList = powerCabinetList.getData().getRecords();

                    if (tempPowerCabinetList == null || tempPowerCabinetList.size() == 0 || tempCenterControlList == null) {
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

                    XLogger.e("集控数量" + tempCenterControlList.size());
                    mCenterControlList = tempCenterControlList;

                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_controller));
                    binding.tvDevCountName.setText("集控总数：");
                    binding.tvDevCount.setText(mCenterControlList.size() + "");


                    if(bean !=null){
                        //找到搜索的灯具所在的灯杆
                        for (CenterControlListData.DataBean.RecordsBean centerControl : mCenterControlList) {
                            if (bean.getDevId() == centerControl.getId()) {
                                centerControlSearchResult = centerControl;
                                break;
                            }
                        }

                        if (null == centerControlSearchResult) {
                            XToastUtils.error("未找到此设备");
                        }

                    }

                    if(!is_search) {
                        Log.i("测试用集控","来过了");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));
                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.CENTER_CONTROLLER);

                    showDeviceTypeId = CenterControlDetailFragment.deviceType;
                    binding.tvSearch.setText("搜索集控");


                }

            }
        });
    }


    /**
     * 查询配电柜列表
     */
    private void queryAllPowerCabinet(boolean isFiddler,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 0);
        parameter.put("pageSize", 99999999);
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_LIST, parameter, new BeanResponseListener<PowerCabinetList>() {
            @Override
            public void onResponse(PowerCabinetList powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉没有坐标的配电柜
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    mPowerCabinetList = powerCabinetList.getData().getRecords();

                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_power_cabinet));
                    binding.tvDevCountName.setText("配电柜总数：");
                    binding.tvDevCount.setText(mPowerCabinetList.size() + "");

                    if(bean !=null){
                        //找到搜索的灯具所在的灯杆
                        for (PowerCabinetList.DataBean.RecordsBean powerControl : mPowerCabinetList) {
                            if (bean.getDevId() == powerControl.getId()) {
                                powerCabinetSearchResult = powerControl;
                                break;
                            }
                        }

                        if (null == powerCabinetSearchResult) {
                            XToastUtils.error("未找到此设备");
                        }

                    }

                    if(!is_search) {
                        Log.i("测试用配电柜","来过了");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));
                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.POWER_CABINET);

                    showDeviceTypeId = PowerCabinetDetailFragment.deviceType;
                    binding.tvSearch.setText("暂不支持搜索");

                }
            }
        });
    }

    /**
     * 查询井盖
     */
    private void queryManholeList(boolean isFiddler,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_ED_ED_MANHOLE_COVER_DEVICE_PAGE, parameter, new BeanResponseListener<ManholeList>() {
            @Override
            public void onResponse(ManholeList manholeList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    mManholeList = manholeList.getData().getRecords();

                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_controller));
                    binding.tvDevCountName.setText("井盖总数：");
                    binding.tvDevCount.setText(manholeList.getData().getRecords().size() + "");

                    if(bean != null){
                        //找到搜索的井盖
                        for (ManholeList.DataBean.RecordsBean powerControl : mManholeList) {
                            if (bean.getDevId() == powerControl.getId()) {
                                manholeSearchResult = powerControl;
                                break;
                            }
                        }

                        if (null == manholeSearchResult) {
                            XToastUtils.error("未找到此设备");
                        }
                    }

                    initMapLampMarkerS(DevTypeEnum.SHOW_MANHOLE);

                    binding.tvSearch.setText("搜索井盖");
                    showDeviceTypeId = ManholeDetailFragment.deviceType;
                }
            }
        });
    }

    /**
     * 处理地图点
     *
     * @param devTypeEnum 设备类型
     */
    private void initMapLampMarkerS(DevTypeEnum devTypeEnum) {

        if (null != mClusterOverlay) {
            mClusterOverlay.onDestroy();
        }
        clusterItems = new ArrayList<>();
        LatLng latlng;

        switch (devTypeEnum) {
            case SHOW_LAMP_POLE://灯杆
                for (MapLampCommonDevList.DataBean dataBean : mLampPoleList) {
                    latlng = new LatLng(dataBean.lampPostLatitude, dataBean.lampPostLongitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, dataBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;
            case SHOW_LAMP_LIGHT://灯具
                for (MapLampCommonDevList.DataBean.ChildrenListBean childrenListBean : mLampLightList) {
                    latlng = new LatLng(childrenListBean.getDlmRespDevice().lampPostLatitude, childrenListBean.getDlmRespDevice().lampPostLongitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, childrenListBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;
            case POWER_CABINET://配电柜
                for (PowerCabinetList.DataBean.RecordsBean dataBean : mPowerCabinetList) {
                    latlng = new LatLng(dataBean.latitude, dataBean.longitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, dataBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;

            case CENTER_CONTROLLER://集控
                for (CenterControlListData.DataBean.RecordsBean dataBean : mCenterControlList) {
                    if(dataBean.powerCabinetInfo != null) {
                        latlng = new LatLng(dataBean.powerCabinetInfo.latitude, dataBean.powerCabinetInfo.longitude, false);
                        RegionItem regionItem = new RegionItem(latlng, devTypeEnum, dataBean);
                        if (null != regionItem) {
                            clusterItems.add(regionItem);
                        }
                    }
                }
                break;
            case SHOW_MANHOLE://井盖
                for (ManholeList.DataBean.RecordsBean recordsBean : mManholeList) {
                    latlng = new LatLng(recordsBean.getLatitude(), recordsBean.getLongitude(), false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, recordsBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;
        }


        if (clusterItems.size() == 0) {
            return;
        }
        mClusterOverlay = new ClusterOverlay(aMap, clusterItems,
                dp2px(mActivity, clusterRadius),
                mActivity);
        mClusterOverlay.setClusterRenderer(this);
        mClusterOverlay.setOnClusterClickListener(this);


        //---- 以下是处理搜索结果------
        if (null != poleSearchResult) { //弹出搜索到的灯杆详情

            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            LatLng searchResultLatLng = new LatLng(poleSearchResult.getLampPostLatitude(), poleSearchResult.getLampPostLongitude());
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchResultLatLng, 20f));
            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            LampPoleDetailFragment fragment = LampPoleDetailFragment.getInstance(poleSearchResult);
            beginTransaction.replace(binding.flBottomsheet.getId(), fragment);
            beginTransaction.commitAllowingStateLoss();
            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            poleSearchResult = null;
            is_search = false;
            return;
        }

        if (null != lightPoleSearchResult) { //弹出搜索到的灯具详情

            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            Log.i("测试用Longitude=",String.valueOf(lightPoleSearchResult.getLampPostLongitude()));
            Log.i("测试用Latitude=",String.valueOf(lightPoleSearchResult.getLampPostLatitude()));

            LatLng searchResultLatLng = new LatLng(lightPoleSearchResult.getLampPostLatitude(), lightPoleSearchResult.getLampPostLongitude());
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchResultLatLng, 20f));
            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            LampLightDetailFragment fragment = LampLightDetailFragment.getInstance(lightSearchResult);
            beginTransaction.replace(binding.flBottomsheet.getId(), fragment);
            beginTransaction.commitAllowingStateLoss();
            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            lightPoleSearchResult = null;
            is_search = false;
            return;
        }

        if(null != powerCabinetSearchResult){ //配电柜详情
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            LatLng searchResultLatLng = new LatLng(powerCabinetSearchResult.getLatitude(), powerCabinetSearchResult.getLongitude());
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchResultLatLng, 20f));
            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();

            queryPowerCabinetDetailByPowerCabinetId(beginTransaction, powerCabinetSearchResult.getId());
            powerCabinetSearchResult = null;
            is_search = false;
            return;
        }

        if (null != centerControlSearchResult) { //弹出搜索到的集控详情

            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);


            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            //找到和此集控在同一个配电柜的所有的集控
//            CenterControlListData.DataBean.RecordsBean centerControl = (CenterControlListData.DataBean.RecordsBean) clusterItems.get(0).getT();
            CenterControlAllData centerControlAllData = new CenterControlAllData();
            centerControlAllData.setCenterControl(centerControlSearchResult);
            CenterControlUtil.getCenterControlAllDetail(mActivity, mMessageLoader, centerControlAllData, allCenterControlAllData -> {

                if(centerControlSearchResult != null && centerControlSearchResult.getPowerCabinetInfo() != null) {
                    LatLng searchResultLatLng = new LatLng(centerControlSearchResult.getPowerCabinetInfo().getLatitude(), centerControlSearchResult.getPowerCabinetInfo().getLongitude());
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchResultLatLng, 20f));

                    beginTransaction.replace(binding.flBottomsheet.getId(), CenterControlDetailFragment.getInstance(allCenterControlAllData));
                    beginTransaction.commitAllowingStateLoss();
                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    centerControlSearchResult = null;
                    is_search = false;
                }
            });

            return;
        }

        if(null != manholeSearchResult){
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            LatLng searchResultLatLng = new LatLng(manholeSearchResult.getLatitude(), manholeSearchResult.getLongitude());
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchResultLatLng, 20f));
            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            ManholeDetailFragment fragment = ManholeDetailFragment.getInstance(manholeSearchResult);
            beginTransaction.replace(binding.flBottomsheet.getId(), fragment);
            beginTransaction.commitAllowingStateLoss();
            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            manholeSearchResult = null;
            is_search = false;
            return;
        }

    }


    /**
     * 聚合点点击的回调处理
     *
     * @param marker       点击的聚合点
     * @param clusterItems
     */
    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());
        }

        LatLngBounds latLngBounds = builder.build();
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
        FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();

        if (clusterItems.size() == 1) {//点击的是单个点
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            switch (clusterItems.get(0).getDevTyeEnum()) {
                case SHOW_LAMP_POLE://点击的是灯杆
                    MapLampCommonDevList.DataBean clickLampPole = (MapLampCommonDevList.DataBean) clusterItems.get(0).getT();
                    beginTransaction.replace(binding.flBottomsheet.getId(), LampPoleDetailFragment.getInstance(clickLampPole));
                    beginTransaction.commit();
                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;

                case SHOW_LAMP_LIGHT://点击的是灯具
                    MapLampCommonDevList.DataBean.ChildrenListBean clickLampLight = (MapLampCommonDevList.DataBean.ChildrenListBean) clusterItems.get(0).getT();

                    MapLampCommonDevList.DataBean clickPole = null;


                    //找到具体的灯杆
                    for (MapLampCommonDevList.DataBean pole : mapLampForLightListFiddler) {
                        if (clickLampLight.lampPostId == pole.id)
                        {
                            clickPole = pole;
                            break;
                        }
                    }
                    if (null == clickPole) {
                        XToastUtils.error("数据有误");
                        return;
                    }
//                    beginTransaction.replace(binding.flBottomsheet.getId(), LampLightDetailFragment.getInstance(clickPole));
//                    beginTransaction.commit();
//                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                    //同一个灯杆里有多个灯具
                    List<String> lampLightNames = new ArrayList<>();
                    for (MapLampCommonDevList.DataBean.ChildrenListBean childrenListBean : clickPole.getChildrenList()) {
                        lampLightNames.add(childrenListBean.name);
                    }

                    if(lampLightNames.size() ==1){
                        beginTransaction.replace(binding.flBottomsheet.getId(), LampLightDetailFragment.getInstance(clickPole));
                        beginTransaction.commit();
                        mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }else if(lampLightNames.size() >1){
                        //弹出Dialog
                        List<MapLampCommonDevList.DataBean.ChildrenListBean> lampLightlist = new ArrayList<>();
                        MapLampCommonDevList.DataBean finalClickPole = new MapLampCommonDevList.DataBean();
                        finalClickPole.setChildrenList(clickPole.getChildrenList());
                        finalClickPole.setName(clickPole.name);
                        finalClickPole.setAreaName(clickPole.areaName);
                        finalClickPole.setStreetName(clickPole.streetName);
                        finalClickPole.setSuperName(clickPole.superName);
                        finalClickPole.setLampPostNum(clickPole.lampPostNum);
                        finalClickPole.setNames(clickPole.names);
                        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, (v, options1, options2, options3) -> {

                            if (String.valueOf(finalClickPole.getChildrenList().get(options1).getName()).equals(lampLightNames.get(options1))) {
                                lampLightlist.add(finalClickPole.getChildrenList().get(options1));
                                finalClickPole.setChildrenList(lampLightlist);

                                beginTransaction.replace(binding.flBottomsheet.getId(), LampLightDetailFragment.getInstance(finalClickPole));
                                beginTransaction.commit();
                                mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }
                            return false;
                        })

                                .setTitleText("请选择灯具")
                                .setDividerColor(Color.BLACK)
                                //切换选项时，还原到第一项
                                .isRestoreItem(true)
                                //设置选中项文字颜色
                                .setTextColorCenter(mActivity.getColor(R.color.text_color_333333))
                                .setContentTextSize(16)
                                .isDialog(false)
                                .build();

                        pvOptions.setPicker(lampLightNames);//一级选择器
                        pvOptions.show();
                    }

                    break;

                case POWER_CABINET://点击的是配电柜
                    queryPowerCabinetDetailByPowerCabinetId(beginTransaction, ((PowerCabinetList.DataBean.RecordsBean) clusterItems.get(0).getT()).getId());
                    break;

                case CENTER_CONTROLLER://集控
                    //找到和此集控在同一个配电柜的所有的集控
                    CenterControlListData.DataBean.RecordsBean centerControl = (CenterControlListData.DataBean.RecordsBean) clusterItems.get(0).getT();
                    List<CenterControlListData.DataBean.RecordsBean> showCenterControlList = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean recordsBean : mCenterControlList) {
                        if (centerControl.powerCabinetInfo.id == recordsBean.powerCabinetInfo.id) {
                            showCenterControlList.add(recordsBean);
                        }
                    }
                    CenterControlAllData centerControlAllData = new CenterControlAllData();
                    if (showCenterControlList.size() == 1) {//配电柜里只有1个集控
                        centerControlAllData.setCenterControl(centerControl);

                        CenterControlUtil.getCenterControlAllDetail(mActivity, mMessageLoader, centerControlAllData, allCenterControlAllData -> {
                            beginTransaction.replace(binding.flBottomsheet.getId(), CenterControlDetailFragment.getInstance(allCenterControlAllData));
                            beginTransaction.commit();
                            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        });
                        return;
                    }
                    //同一个配电柜里有多个集控
                    List<String> centerControlNames = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean showCenterControl : showCenterControlList) {
                        centerControlNames.add(showCenterControl.name);
                    }
                    //弹出Dialog
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, (v, options1, options2, options3) -> {
                        centerControlAllData.setCenterControl(showCenterControlList.get(options1));
                        CenterControlUtil.getCenterControlAllDetail(mActivity, mMessageLoader, centerControlAllData, allCenterControlAllData -> {
                            beginTransaction.replace(binding.flBottomsheet.getId(), CenterControlDetailFragment.getInstance(allCenterControlAllData));
                            beginTransaction.commit();
                            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        });
                        return false;
                    })

                            .setTitleText("请选择集控")
                            .setDividerColor(Color.BLACK)
                            //切换选项时，还原到第一项
                            .isRestoreItem(true)
                            //设置选中项文字颜色
                            .setTextColorCenter(mActivity.getColor(R.color.text_color_333333))
                            .setContentTextSize(16)
                            .isDialog(false)
                            .build();

                    pvOptions.setPicker(centerControlNames);//一级选择器
                    pvOptions.show();
                    break;
                case SHOW_MANHOLE:
                    ManholeList.DataBean.RecordsBean clickhole = (ManholeList.DataBean.RecordsBean) clusterItems.get(0).getT();
                    beginTransaction.replace(binding.flBottomsheet.getId(), ManholeDetailFragment.getInstance(clickhole));
                    beginTransaction.commit();
                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;
            }

        }
    }

    /**
     * 通过配电柜查询配电柜相关信息
     *
     * @param beginTransaction
     * @param powerCabinetId   配电柜ID
     */
    private void queryPowerCabinetDetailByPowerCabinetId(FragmentTransaction beginTransaction, int powerCabinetId) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi1.GET_POWER_CABINET_DETAIL + "/" + powerCabinetId, parameter, new BeanResponseListener<PowerCabinetDetail>() {
            @Override
            public void onResponse(PowerCabinetDetail powerCabinetDetail, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    beginTransaction.replace(binding.flBottomsheet.getId(), PowerCabinetDetailFragment.getInstance(powerCabinetDetail));
                    beginTransaction.commit();
                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


    }

    /**
     * 根据聚合点的元素数目返回渲染背景样式图标
     *
     * @param clusterNum   集合点数量（当为1时，是单个设备）
     * @param clusterItems 集合点设备集合
     * @return
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public Drawable getDrawAble(List<ClusterItem> clusterItems, int clusterNum) {
        int radius = dp2px(mActivity, 65);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                switch (clusterItems.get(0).getDevTyeEnum()) {
                    case SHOW_LAMP_POLE:
                        MapLampCommonDevList.DataBean deviceBeanPole = (MapLampCommonDevList.DataBean) clusterItems.get(0).getT();
                        bitmapDrawable =
                                mActivity.getDrawable(
                                        R.drawable.icon_map_lamp);
                        break;
                    case SHOW_LAMP_LIGHT:
                        MapLampCommonDevList.DataBean.ChildrenListBean deviceBeanLight = (MapLampCommonDevList.DataBean.ChildrenListBean) clusterItems.get(0).getT();
                        if(deviceBeanLight.getDlmRespDevice().getIsOnline() == 1){
                            bitmapDrawable =
                                    mActivity.getDrawable(
                                            R.drawable.icon_map_lamp_open);
                        }else {
                            bitmapDrawable =
                                    mActivity.getDrawable(
                                            R.drawable.icon_map_lamp_dev);
                        }

                        break;
                    case POWER_CABINET:

                        PowerCabinetList.DataBean.RecordsBean devicePowerCabinet = (PowerCabinetList.DataBean.RecordsBean) clusterItems.get(0).getT();


                        bitmapDrawable =
                                mActivity.getDrawable(
                                        R.drawable.icon_power_cabinet);
                        break;

                    case CENTER_CONTROLLER:

                        CenterControlListData.DataBean.RecordsBean devCenterControl = (CenterControlListData.DataBean.RecordsBean) clusterItems.get(0).getT();
                        if(devCenterControl.getIsOnline() ==1){
                            bitmapDrawable =
                                    mActivity.getDrawable(
                                            R.drawable.icon_controller);
                        }else {
                            bitmapDrawable =
                                    mActivity.getDrawable(
                                            R.drawable.icon_controller_close);
                        }

                        break;
                    case SHOW_MANHOLE:

                        ManholeList.DataBean.RecordsBean manholeDataBean = (ManholeList.DataBean.RecordsBean)clusterItems.get(0).getT();

                        int networkState = manholeDataBean.getNetworkState();
                        if(networkState == 1){
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_on_h);//在线
                        }else {
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_out_h);//离线
                        }

                        int status = manholeDataBean.getStatus();
                        if(status == 1){
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_bad_n);//告警
                        }

                        break;
                }

                bitmapDrawable = ImageUtils.zoomDrawable(bitmapDrawable, 250, 364);

                mBackDrawAbles.put(1, bitmapDrawable);
            }
            return bitmapDrawable;
        } else if (clusterNum < 5) {
            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search://搜索  （只能搜索灯杆相关的设备）
                switch (showDeviceTypeId) {
                    case LampPoleDetailFragment.deviceType:
                    case LampLightDetailLightListFragment.deviceType:
                    case CenterControlDetailFragment.deviceType:
                        openNewPage(DevMapSearchFragment.class, "showDeviceTypeId", showDeviceTypeId);
                        break;
                    default:
                        XToastUtils.error("此类设备暂不支持搜索");
                        break;
                }
                break;
            case R.id.tv_search:
                binding.ivSearch.performClick();
                break;
            case R.id.ll_fiddler:

                switch (showDeviceTypeId) {
                    case LampPoleDetailFragment.deviceType:
                    case LampLightDetailLightListFragment.deviceType:
                    case CenterControlDetailFragment.deviceType:
                        fiddlerClick();
                        break;
                    default:
                        XToastUtils.error("此类设备暂不支持筛选");
                        break;
                }
                break;

            case R.id.ll_back_current_location:
                if (mLocation == null || mLocation.getLatitude() <= 0) {
                    XToastUtils.error("未获取到当前位置信息~");
                    return;
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 18f));
                break;

            case R.id.light_pole:
                showBottom();
                break;

        }

    }

    private void fiddlerClick() {
        binding.rlLampSel.setChecked(false);

        //不是灯杆上的设备就单独处理
        if (showDeviceTypeId == CenterControlDetailFragment.deviceType) {//集控
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
                              queryAllCenterControl(selectResult.projectId,null);
                          }
                      });
                    }
                }
            });

            return;
        }


        List<MapLampCommonDevList.DataBean> fiddlerList = new ArrayList<>();
        fiddlerList = mapLampForLightListFiddler;
//        switch (showDeviceTypeId) {
//            case LampPoleDetailFragment.deviceType:
//                fiddlerList = mapLampForLightListFiddler;
//                break;
//            case LampLightDetailLightListFragment.deviceType:
//                fiddlerList = mLampPoleListForFiddler;
//                break;
//        }

        if (fiddlerList.size() == 0) {
            XToastUtils.error("暂时没有数据可筛选，请稍后");
            return;
        }
        //处理筛选
        DevFiddlerUtils.showPoleDevFiddlerDialog(mActivity, showDeviceTypeId, fiddlerList,
                (int showDeviceTypeId, DevFiddlerUtils.SelectResult selectResult) -> {

                    areaId = selectResult.areaId;
                    streetId = selectResult.streetId;
                    siteId = selectResult.siteId;

                    switch (showDeviceTypeId) {
                        case LampPoleDetailFragment.deviceType:
                            queryLampPoleList(true,null);
                            break;
                        case LampLightDetailLightListFragment.deviceType:
                            queryLampLightList(true,null);
                            break;

                    }


                });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mapView) {
            mapView.onResume();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mapView) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mClusterOverlay.onDestroy();
        if (null != mapView) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (null != mapView) {
            mapView.onPause();
        }
    }


    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private Bitmap drawCircle(int radius, int color) {

        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }


    //底部弹框
    private void showBottom() {
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.item_lamps_setting, null);
        seekBar = contentView.findViewById(R.id.sk);
        switchView = contentView.findViewById(R.id.status_switch);
        TextView progresstv = contentView.findViewById(R.id.progress);

        SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
        String lightnesse = preferences.getString("lightnesse","");
        if(lightnesse.length() >0){
            progresstv.setText(lightnesse +"%");
            seekBar.setProgress(Integer.parseInt(lightnesse));
            all_progress = Integer.parseInt(lightnesse);
        }

        all_check = preferences.getString("type","").equals("1")?true:false;
        if (!all_check) { //关闭状态
            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
        } else {//开启状态
            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 20) {
                    seekBar.setProgress(20);
                    progress = 20;
                }
                progresstv.setText(progress + "%");
                all_progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    all_check = preferences.getString("type","").equals("1")?true:false;
                    control(false);
                }catch (Exception e){
                    e.printStackTrace();
                };
            }
        });

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    all_check = preferences.getString("type","").equals("1")?true:false;
                    all_check = !all_check;
                    control(true);
                }catch (Exception e){
                    e.printStackTrace();
                };
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

    //控制灯具全开、全关
    public void control(boolean isswitch) throws JSONException {
        mMessageLoader.show();
        org.json.JSONArray idlist = new JSONArray();
        org.json.JSONArray typelist = new JSONArray();
        org.json.JSONArray lightnesses = new JSONArray();

        for (MapLampCommonDevList.DataBean dataBean : mLampPoleList) {
            idlist.put(dataBean.getId());
            typelist.put(all_check?1:0); //0关 1开
            lightnesses.put(all_progress);
        }

        JSONObject body = new JSONObject();
        body.put("lampPostIdList",idlist);
        body.put("lightnesses", lightnesses);
        body.put("types", typelist);
        HttpRequest.JSONPOST(mActivity, HttpApi.DEVICE_CONTROL, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success("下发成功");

                    if(isswitch){
                        if (!all_check) { //关闭状态
                            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
                            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
                        } else {//开启状态
                            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
                            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
                        }
                    }

                    //获取editor对象
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    SharedPreferences.Editor editor = preferences.edit();
                    //存储数据时选用对应类型的方法
                    editor.putString("lightnesse",String.valueOf(all_progress));
                    editor.putString("type",all_check==true?"1":"0");
                    //提交保存数据
                    editor.commit();
                }
            }
        });
    }
}
