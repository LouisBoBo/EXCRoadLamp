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
 * ??????-??????-??????
 */
@Page(anim = CoreAnim.none)
public class DeviceMapFragment extends MyBaseFragment implements View.OnClickListener, AMap.OnMapLoadedListener, ClusterClickListener, ClusterRender {
    private TextureMapView mapView;
    private ClusterOverlay mClusterOverlay;
    /**
     * ?????????????????????dp???
     */
    private int clusterRadius = 70;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<>();
    private FragmentDeviveMapBinding binding;
    private AMap aMap;
    private LatLng defCurrentCityPoint = new LatLng(30.973649, 111.896328);// ???????????????????????????
    private UiSettings mUiSettings;//????????????UiSettings??????

    private int siteId = -1;
    private int streetId = -1;
    private int areaId = -1;

    private boolean is_search = false;//????????????

    private ImageView switchView;
    private SeekBar seekBar;
    private int all_progress =0;
    private boolean all_check = false;



    /**
     * ?????????????????????????????????
     */
    private List<MapLampCommonDevList.DataBean> mLampPoleList = new ArrayList<>();

    /**
     * ?????????????????????????????????
     */
    private List<MapLampCommonDevList.DataBean> mLampPoleListForFiddler = new ArrayList<>();


    /**
     * ?????????????????????????????????
     */
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> mLampLightList = new ArrayList<>();


    /**
     * ????????????????????????(?????????)
     */
    private List<MapLampCommonDevList.DataBean> mapLampForLightListFiddler = new ArrayList<>();

    /**
     * ????????????????????????????????????
     */
    private List<PowerCabinetList.DataBean.RecordsBean> mPowerCabinetList = new ArrayList<>();


    /**
     * ?????????????????????????????????
     */
    private List<CenterControlListData.DataBean.RecordsBean> mCenterControlList = new ArrayList<>();

    /**
     * ?????????????????????????????????
     */
    private List<ManholeList.DataBean.RecordsBean> mManholeList = new ArrayList<>();

    /**
     * ??????????????????????????????
     */
    private ArrayList<ClusterItem> clusterItems;
    private BottomSheetBehavior mBottomSheetBehavior;
    private PermissionInterceptor mPermissionInterceptor;
    /**
     * ??????????????????
     */
    private AMapLocation mLocation;

    /**
     * ?????????????????????????????????     1-100????????????????????????  101?????????
     * 0 ??????
     * 1 ??????
     * 2 AP
     * 3 ??????
     * 4 ?????????
     * 5 ?????????
     * 7 ?????????
     */
    private int showDeviceTypeId = 10000;
    /**
     * ?????????????????????????????????
     */
    private MapLampCommonDevList.DataBean poleSearchResult;


    /**
     * ????????????????????????????????????????????????
     */
    private MapLampCommonDevList.DataBean lightPoleSearchResult;
    private MapLampCommonDevList.DataBean lightSearchResult = null;


    /**
     * ??????????????????????????????
     */
    private PowerCabinetList.DataBean.RecordsBean powerCabinetSearchResult;

    /**
     * ?????????????????????????????????
     */
    private CenterControlListData.DataBean.RecordsBean centerControlSearchResult;

    /**
     * ?????????????????????????????????
     */
    private ManholeList.DataBean.RecordsBean manholeSearchResult;

    /**
     * ????????????????????????
     */
    private int bottomSheetBehaviorHeight;


    /**
     * @return ????????? null????????????????????????
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
     * ???????????????
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
                case LampPoleDetailFragment.deviceType://??????
                    binding.lightPole.setVisibility(View.VISIBLE);
                    binding.rlLampPole.setChecked(true);
                    //???????????????????????????????????????????????????
                    queryLampPoleList(false,bean);

                    break;

                case LampLightDetailLightListFragment.deviceType://??????
                    binding.lightPole.setVisibility(View.VISIBLE);
                    binding.rlLampLight.setChecked(true);
                    queryLampLightList(false,bean);

                    break;

                case PowerCabinetDetailFragment.deviceType://?????????
                    binding.lightPole.setVisibility(View.GONE);
                    binding.rlPowerCabinet.setChecked(true);
                    queryAllPowerCabinet(false,bean);

                    break;

                case CenterControlDetailFragment.deviceType://??????
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

        //????????????
        MapsInitializer.updatePrivacyShow(mActivity, true, true);
        MapsInitializer.updatePrivacyAgree(mActivity,true);
        checkPermissionAndSetMapCenterPos(savedInstanceState);

    }


    /**
     * ??????????????????
     *
     * @param savedInstanceState
     */
    public void checkPermissionAndSetMapCenterPos(Bundle savedInstanceState){
        mPermissionInterceptor = new PermissionInterceptor(this);
        mPermissionInterceptor.setPermissionsPrompt("???????????????????????????APP?????????????????????????????????APP?????????????????????");
        mPermissionInterceptor.setPermissionType(PermissionTypeEnum.ACCESS_COARSE_LOCATION);
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,


        };
        mPermissionInterceptor.setPermissions(permissions)
                .isForcePermission(true)//????????????
                .setPermissionListener(new PermissionInterceptor.OnPermissionResult() {
                    @Override
                    public void onPermissionAllAllow() {

                        try {
                            AMapLocationClient locationClientSingle = new AMapLocationClient(mActivity);
                            AMapLocationClientOption locationClientOption = new AMapLocationClientOption();

                            //??????????????????
                            locationClientOption.setOnceLocation(true);
                            // ????????????
                            locationClientOption.setNeedAddress(true);
                            locationClientOption.setLocationCacheEnable(false);
                            locationClientSingle.setLocationOption(locationClientOption);
                            locationClientSingle.setLocationListener(location -> {

                                AMapOptions aOptions = new AMapOptions();

                                if (location != null && location.getLatitude() > 0) {//?????????????????????
                                    mLocation = location;
                                    defCurrentCityPoint = new LatLng(location.getLatitude(), location.getLongitude());
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 13f, 0, 0));

                                } else {//????????????????????????
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
                                mUiSettings = aMap.getUiSettings();//?????????UiSettings?????????
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

    //???????????????????????????
    private void initMapData() {


        binding.tvSearch.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.llFiddler.setOnClickListener(this);
        binding.llBackCurrentLocation.setOnClickListener(this);
        binding.lightPole.setOnClickListener(this::onClick);
        binding.lightPole.setVisibility(View.VISIBLE);

        //????????????????????????
        initMyLocationBluePoint();

        //???????????????
        binding.nestedGroup1.setOnCheckedChangeListener((group, checkedId) -> {
                    //????????????
                    switch (checkedId) {
                        case R.id.rl_lamp_ser://??????
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
                        case R.id.rl_lamp_sel://??????
                            switch (showDeviceTypeId) {
                                case LampPoleDetailFragment.deviceType:
                                case LampLightDetailLightListFragment.deviceType:
                                case CenterControlDetailFragment.deviceType:
                                    fiddlerClick();
                                    break;
                                default:
                                    XToastUtils.error("??????????????????????????????");
                                    binding.rlLampSel.setChecked(false);
                                    break;
                            }

                            break;
                    }
                }
        );

        //??????????????????
        binding.rlLampPole.setChecked(true);
        binding.nestedGroup2.setOnCheckedChangeListener((group, checkedId) -> {
                    if(is_search){
                        return;
                    }
                    mBackDrawAbles.clear();
                    areaId = -1;
                    streetId = -1;
                    siteId = -1;
                    //????????????
                    switch (checkedId) {
                        case R.id.rl_lamp_pole://??????
                            binding.lightPole.setVisibility(View.VISIBLE);
                            queryLampPoleList(false,null);
                            break;
                        case R.id.rl_lamp_light://??????   //BottomSheet??????
                            binding.lightPole.setVisibility(View.VISIBLE);
                            queryLampLightList(false,null);
                            break;
                        case R.id.rl_power_cabinet://?????????
                            binding.lightPole.setVisibility(View.GONE);
                            queryAllPowerCabinet(false,null);
                            break;
                        case R.id.rl_controller://??????
                            binding.lightPole.setVisibility(View.GONE);
                            queryAllCenterControl(-1,null);
                            break;
                        case R.id.rl_manhole://??????
                            binding.lightPole.setVisibility(View.GONE);
                            queryManholeList(false,null);
                            break;
                    }
                }
        );
        mBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);


        //????????????????????????
        queryLampPoleList(false,null);
        //???????????????????????????????????????,?????????????????????????????????,??????Behavior????????????????????????????????????.
//        mGestureDetector = new GestureDetector(mActivity,
//                new GestureDetector.SimpleOnGestureListener() {
//                    //?????????????????? -- ?????????????????????or??????
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
     * ??????????????????????????????
     */
    private void initMyLocationBluePoint() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.interval(1000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
        aMap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???


    }

    @Override
    protected void onTouchDownAction(MotionEvent ev) {
//        mGestureDetector.onTouchEvent(ev);
        super.onTouchDownAction(ev);
    }

    /**
     * ????????????
     *
     * @param isFiddler ??????????????????????????????
     */
    private void queryLampPoleList(boolean isFiddler,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("deviceTypeId", LampPoleDetailFragment.deviceType);
//        parameter.put("deviceTypeId", 1);//?????????

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
                    binding.tvDevCountName.setText("???????????????");
                    binding.tvDevCount.setText(mapLampCommonDevList.getData().size() + "");

                    if (!isFiddler) {
                        mLampPoleListForFiddler.clear();
                        mLampPoleListForFiddler = mapLampCommonDevList.getData();
                    }


                    //??????????????????????????????
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));
                    mLampPoleList = mapLampCommonDevList.getData();

                    if(bean != null){
                        //?????????????????????
                        for (MapLampCommonDevList.DataBean pole : mLampPoleList) {
                            if (bean.getDevId() == pole.getId()) {
                                poleSearchResult = pole;
                                break;
                            }
                        }
                        if (null == poleSearchResult) {
                            XToastUtils.error("??????????????????");
                        }

                    }

                    if(!is_search){
                         Log.i("???????????????","?????????");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));

                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.SHOW_LAMP_POLE);

                    showDeviceTypeId = LampPoleDetailFragment.deviceType;
                    binding.tvSearch.setText("????????????");

                }
            }
        });
    }

    /**
     * ????????????
     *
     * @param isFiddler ??????????????????????????????
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
                    //??????????????????????????????
                    mapLampCommonDevList.setData(mapLampCommonDevList.getData().stream().
                            filter(dataBean -> !(dataBean.lampPostLatitude <= 0 || dataBean.lampPostLongitude <= 0)).collect(Collectors.toList()));


                    //??????????????????????????????
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
                    binding.tvDevCountName.setText("???????????????");
                    binding.tvDevCount.setText(mLampLightList.size() + "");

                    Log.i("?????????", String.valueOf(mLampPoleList.size()));
                    if(bean !=null){
                        //????????????????????????????????????
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
                            XToastUtils.error("??????????????????");
                        }

                    }

                    if(!is_search) {
                        Log.i("???????????????","?????????");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));

                        }
                    }

                    initMapLampMarkerS(DevTypeEnum.SHOW_LAMP_LIGHT);

                    showDeviceTypeId = LampLightDetailLightListFragment.deviceType;
                    binding.tvSearch.setText("????????????");

                }

            }
        });
    }


    /**
     * ??????????????????????????????????????????????????????????????????????????????
     */
    private void queryAllCenterControl(int newAreaId,DevMapSearchResultListBean bean) {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 99999999);
        parameter.put("controlTypeIdList", "4,5");
        if (newAreaId >= 0) {
            parameter.put("newAreaId", newAreaId);//????????????
            parameter.put("areaId", newAreaId);//????????????
        }
        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_LIST, parameter, new BeanResponseListener<CenterControlListData>() {
            @Override
            public void onResponse(CenterControlListData powerCabinetList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    List<CenterControlListData.DataBean.RecordsBean> tempCenterControl = powerCabinetList.getData().getRecords();
                    if (tempCenterControl == null || tempCenterControl.size() == 0) {
                        mMessageLoader.dismiss();
                        XToastUtils.error("????????????");
                        return;
                    }
                    queryAllPowerCabinetForCenterControl(tempCenterControl,bean);
                }
            }
        });
    }

    /**
     * ?????????????????????????????????????????????
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
                    //?????????????????????????????????
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    List<PowerCabinetList.DataBean.RecordsBean> tempPowerCabinetList = powerCabinetList.getData().getRecords();

                    if (tempPowerCabinetList == null || tempPowerCabinetList.size() == 0 || tempCenterControlList == null) {
                        XToastUtils.error("????????????");
                        return;
                    }
                    //?????????????????????
                    for (CenterControlListData.DataBean.RecordsBean centerControl : tempCenterControlList) {
                        for (PowerCabinetList.DataBean.RecordsBean powerCabinet : tempPowerCabinetList) {
                            if (centerControl.getCabinetId() == powerCabinet.id) {
                                centerControl.setPowerCabinetInfo(powerCabinet);
                            }
                        }
                    }

                    XLogger.e("????????????" + tempCenterControlList.size());
                    mCenterControlList = tempCenterControlList;

                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_controller));
                    binding.tvDevCountName.setText("???????????????");
                    binding.tvDevCount.setText(mCenterControlList.size() + "");


                    if(bean !=null){
                        //????????????????????????????????????
                        for (CenterControlListData.DataBean.RecordsBean centerControl : mCenterControlList) {
                            if (bean.getDevId() == centerControl.getId()) {
                                centerControlSearchResult = centerControl;
                                break;
                            }
                        }

                        if (null == centerControlSearchResult) {
                            XToastUtils.error("??????????????????");
                        }

                    }

                    if(!is_search) {
                        Log.i("???????????????","?????????");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));
                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.CENTER_CONTROLLER);

                    showDeviceTypeId = CenterControlDetailFragment.deviceType;
                    binding.tvSearch.setText("????????????");


                }

            }
        });
    }


    /**
     * ?????????????????????
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
                    //?????????????????????????????????
                    powerCabinetList.getData().setRecords(powerCabinetList.getData().getRecords().stream().
                            filter(dataBean -> !(dataBean.getLatitude() <= 0 || dataBean.getLongitude() <= 0)).collect(Collectors.toList()));
                    mPowerCabinetList = powerCabinetList.getData().getRecords();

                    binding.ivDevCount.setImageDrawable(mActivity.getDrawable(R.drawable.icon_dev_map_right_btn_power_cabinet));
                    binding.tvDevCountName.setText("??????????????????");
                    binding.tvDevCount.setText(mPowerCabinetList.size() + "");

                    if(bean !=null){
                        //????????????????????????????????????
                        for (PowerCabinetList.DataBean.RecordsBean powerControl : mPowerCabinetList) {
                            if (bean.getDevId() == powerControl.getId()) {
                                powerCabinetSearchResult = powerControl;
                                break;
                            }
                        }

                        if (null == powerCabinetSearchResult) {
                            XToastUtils.error("??????????????????");
                        }

                    }

                    if(!is_search) {
                        Log.i("??????????????????","?????????");
                        if (mLocation != null && mLocation.getLatitude() > 0) {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 10f));
                        } else {
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 5f));
                        }
                    }
                    initMapLampMarkerS(DevTypeEnum.POWER_CABINET);

                    showDeviceTypeId = PowerCabinetDetailFragment.deviceType;
                    binding.tvSearch.setText("??????????????????");

                }
            }
        });
    }

    /**
     * ????????????
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
                    binding.tvDevCountName.setText("???????????????");
                    binding.tvDevCount.setText(manholeList.getData().getRecords().size() + "");

                    if(bean != null){
                        //?????????????????????
                        for (ManholeList.DataBean.RecordsBean powerControl : mManholeList) {
                            if (bean.getDevId() == powerControl.getId()) {
                                manholeSearchResult = powerControl;
                                break;
                            }
                        }

                        if (null == manholeSearchResult) {
                            XToastUtils.error("??????????????????");
                        }
                    }

                    initMapLampMarkerS(DevTypeEnum.SHOW_MANHOLE);

                    binding.tvSearch.setText("????????????");
                    showDeviceTypeId = ManholeDetailFragment.deviceType;
                }
            }
        });
    }

    /**
     * ???????????????
     *
     * @param devTypeEnum ????????????
     */
    private void initMapLampMarkerS(DevTypeEnum devTypeEnum) {

        if (null != mClusterOverlay) {
            mClusterOverlay.onDestroy();
        }
        clusterItems = new ArrayList<>();
        LatLng latlng;

        switch (devTypeEnum) {
            case SHOW_LAMP_POLE://??????
                for (MapLampCommonDevList.DataBean dataBean : mLampPoleList) {
                    latlng = new LatLng(dataBean.lampPostLatitude, dataBean.lampPostLongitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, dataBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;
            case SHOW_LAMP_LIGHT://??????
                for (MapLampCommonDevList.DataBean.ChildrenListBean childrenListBean : mLampLightList) {
                    latlng = new LatLng(childrenListBean.getDlmRespDevice().lampPostLatitude, childrenListBean.getDlmRespDevice().lampPostLongitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, childrenListBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;
            case POWER_CABINET://?????????
                for (PowerCabinetList.DataBean.RecordsBean dataBean : mPowerCabinetList) {
                    latlng = new LatLng(dataBean.latitude, dataBean.longitude, false);
                    RegionItem regionItem = new RegionItem(latlng, devTypeEnum, dataBean);
                    if (null != regionItem) {
                        clusterItems.add(regionItem);
                    }
                }
                break;

            case CENTER_CONTROLLER://??????
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
            case SHOW_MANHOLE://??????
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


        //---- ???????????????????????????------
        if (null != poleSearchResult) { //??????????????????????????????

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

        if (null != lightPoleSearchResult) { //??????????????????????????????

            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            Log.i("?????????Longitude=",String.valueOf(lightPoleSearchResult.getLampPostLongitude()));
            Log.i("?????????Latitude=",String.valueOf(lightPoleSearchResult.getLampPostLatitude()));

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

        if(null != powerCabinetSearchResult){ //???????????????
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

        if (null != centerControlSearchResult) { //??????????????????????????????

            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);


            FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            //?????????????????????????????????????????????????????????
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
     * ??????????????????????????????
     *
     * @param marker       ??????????????????
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

        if (clusterItems.size() == 1) {//?????????????????????
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN);
            EventBus.getDefault().post(eventMessage);

            switch (clusterItems.get(0).getDevTyeEnum()) {
                case SHOW_LAMP_POLE://??????????????????
                    MapLampCommonDevList.DataBean clickLampPole = (MapLampCommonDevList.DataBean) clusterItems.get(0).getT();
                    beginTransaction.replace(binding.flBottomsheet.getId(), LampPoleDetailFragment.getInstance(clickLampPole));
                    beginTransaction.commit();
                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;

                case SHOW_LAMP_LIGHT://??????????????????
                    MapLampCommonDevList.DataBean.ChildrenListBean clickLampLight = (MapLampCommonDevList.DataBean.ChildrenListBean) clusterItems.get(0).getT();

                    MapLampCommonDevList.DataBean clickPole = null;


                    //?????????????????????
                    for (MapLampCommonDevList.DataBean pole : mapLampForLightListFiddler) {
                        if (clickLampLight.lampPostId == pole.id)
                        {
                            clickPole = pole;
                            break;
                        }
                    }
                    if (null == clickPole) {
                        XToastUtils.error("????????????");
                        return;
                    }
//                    beginTransaction.replace(binding.flBottomsheet.getId(), LampLightDetailFragment.getInstance(clickPole));
//                    beginTransaction.commit();
//                    mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                    //?????????????????????????????????
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
                        //??????Dialog
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

                                .setTitleText("???????????????")
                                .setDividerColor(Color.BLACK)
                                //????????????????????????????????????
                                .isRestoreItem(true)
                                //???????????????????????????
                                .setTextColorCenter(mActivity.getColor(R.color.text_color_333333))
                                .setContentTextSize(16)
                                .isDialog(false)
                                .build();

                        pvOptions.setPicker(lampLightNames);//???????????????
                        pvOptions.show();
                    }

                    break;

                case POWER_CABINET://?????????????????????
                    queryPowerCabinetDetailByPowerCabinetId(beginTransaction, ((PowerCabinetList.DataBean.RecordsBean) clusterItems.get(0).getT()).getId());
                    break;

                case CENTER_CONTROLLER://??????
                    //?????????????????????????????????????????????????????????
                    CenterControlListData.DataBean.RecordsBean centerControl = (CenterControlListData.DataBean.RecordsBean) clusterItems.get(0).getT();
                    List<CenterControlListData.DataBean.RecordsBean> showCenterControlList = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean recordsBean : mCenterControlList) {
                        if (centerControl.powerCabinetInfo.id == recordsBean.powerCabinetInfo.id) {
                            showCenterControlList.add(recordsBean);
                        }
                    }
                    CenterControlAllData centerControlAllData = new CenterControlAllData();
                    if (showCenterControlList.size() == 1) {//??????????????????1?????????
                        centerControlAllData.setCenterControl(centerControl);

                        CenterControlUtil.getCenterControlAllDetail(mActivity, mMessageLoader, centerControlAllData, allCenterControlAllData -> {
                            beginTransaction.replace(binding.flBottomsheet.getId(), CenterControlDetailFragment.getInstance(allCenterControlAllData));
                            beginTransaction.commit();
                            mBottomSheetBehavior.setPeekHeight(bottomSheetBehaviorHeight);
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        });
                        return;
                    }
                    //????????????????????????????????????
                    List<String> centerControlNames = new ArrayList<>();
                    for (CenterControlListData.DataBean.RecordsBean showCenterControl : showCenterControlList) {
                        centerControlNames.add(showCenterControl.name);
                    }
                    //??????Dialog
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

                            .setTitleText("???????????????")
                            .setDividerColor(Color.BLACK)
                            //????????????????????????????????????
                            .isRestoreItem(true)
                            //???????????????????????????
                            .setTextColorCenter(mActivity.getColor(R.color.text_color_333333))
                            .setContentTextSize(16)
                            .isDialog(false)
                            .build();

                    pvOptions.setPicker(centerControlNames);//???????????????
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
     * ??????????????????????????????????????????
     *
     * @param beginTransaction
     * @param powerCabinetId   ?????????ID
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
     * ????????????????????????????????????????????????????????????
     *
     * @param clusterNum   ????????????????????????1????????????????????????
     * @param clusterItems ?????????????????????
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
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_on_h);//??????
                        }else {
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_out_h);//??????
                        }

                        int status = manholeDataBean.getStatus();
                        if(status == 1){
                            bitmapDrawable = mActivity.getDrawable(R.drawable.map_main_icon_gai_bad_n);//??????
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
            case R.id.iv_search://??????  ???????????????????????????????????????
                switch (showDeviceTypeId) {
                    case LampPoleDetailFragment.deviceType:
                    case LampLightDetailLightListFragment.deviceType:
                    case CenterControlDetailFragment.deviceType:
                        openNewPage(DevMapSearchFragment.class, "showDeviceTypeId", showDeviceTypeId);
                        break;
                    default:
                        XToastUtils.error("??????????????????????????????");
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
                        XToastUtils.error("??????????????????????????????");
                        break;
                }
                break;

            case R.id.ll_back_current_location:
                if (mLocation == null || mLocation.getLatitude() <= 0) {
                    XToastUtils.error("??????????????????????????????~");
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

        //???????????????????????????????????????
        if (showDeviceTypeId == CenterControlDetailFragment.deviceType) {//??????
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
            XToastUtils.error("???????????????????????????????????????");
            return;
        }
        //????????????
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


    //????????????
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
        if (!all_check) { //????????????
            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
        } else {//????????????
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

    //???????????????????????????
    public void control(boolean isswitch) throws JSONException {
        mMessageLoader.show();
        org.json.JSONArray idlist = new JSONArray();
        org.json.JSONArray typelist = new JSONArray();
        org.json.JSONArray lightnesses = new JSONArray();

        for (MapLampCommonDevList.DataBean dataBean : mLampPoleList) {
            idlist.put(dataBean.getId());
            typelist.put(all_check?1:0); //0??? 1???
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
                    XToastUtils.success("????????????");

                    if(isswitch){
                        if (!all_check) { //????????????
                            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
                            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
                        } else {//????????????
                            switchView.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
                            seekBar.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
                        }
                    }

                    //??????editor??????
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    SharedPreferences.Editor editor = preferences.edit();
                    //??????????????????????????????????????????
                    editor.putString("lightnesse",String.valueOf(all_progress));
                    editor.putString("type",all_check==true?"1":"0");
                    //??????????????????
                    editor.commit();
                }
            }
        });
    }
}
