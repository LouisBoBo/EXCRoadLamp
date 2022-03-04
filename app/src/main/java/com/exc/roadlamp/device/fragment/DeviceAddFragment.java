package com.exc.roadlamp.device.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDeviceAddBinding;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.adapter.DeviceAddAdapter;
import com.exc.roadlamp.device.adapter.DeviceListAdapter;
import com.exc.roadlamp.device.adapter.DeviceSelectAdapter;
import com.exc.roadlamp.device.adapter.LampPoleEditAdapter;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceAddBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.DeviceSystemBean;
import com.exc.roadlamp.device.bean.LampCommonDetail;
import com.exc.roadlamp.device.bean.LampDeviceBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.ProjectList;
import com.exc.roadlamp.device.cluster.ClusterItem;
import com.exc.roadlamp.device.cluster.ClusterOverlay;
import com.exc.roadlamp.device.util.DevFiddlerUtils;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.PermissionInterceptor;
import com.exc.roadlamp.utils.PermissionTypeEnum;
import com.exc.roadlamp.utils.UpLoadUtil;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.BrowsePicFragment;
import com.exc.roadlamp.work.workorder.ImgAdapter;
import com.exc.roadlamp.work.workorder.OrderPic;
import com.exc.roadlamp.work.workorder.SelectPicDialog;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.exc.roadlamp.work.workorder.addressselector.OnRegionDataSetListener;
import com.exc.roadlamp.work.workorder.addressselector.RegionBean;
import com.exc.roadlamp.work.workorder.addressselector.RegionLevel;
import com.exc.roadlamp.work.workorder.addressselector.RegionSelectDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xutil.XUtil.getContentResolver;
import static com.xuexiang.xutil.data.SPUtils.getSharedPreferences;

@Page(anim = CoreAnim.slide)
public class DeviceAddFragment extends MyBaseFragment implements View.OnClickListener,AMap.OnMapLoadedListener, AMap.OnCameraChangeListener {
    FragmentDeviceAddBinding binding;

    private ArrayList<OrderPic> showPicList = new ArrayList<>();
    private List<MapLampCommonDevList.DataBean> mLampPoleListForFiddler = new ArrayList<>();
    private ImgAdapter imgAdapter;
    private SelectPicDialog selectPicDialog;
    private List<DeviceSystemBean.DataBean> dataBeanList;
    private List<String> mStreeOption = new ArrayList<>();
    private List<DeviceAddBean> deviceAddBeanList = new ArrayList<>();
    private DeviceAddAdapter listadapter;
    private int typeSelectOption = 0;
    private int light_count = 0;

    public static final int TAKE_CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int TAKE_GALLERY_PERMISSION_REQUEST_CODE = 101;
    public static final int TAKE_CAMERA_PIC_FILE_REQUEST_CODE = 200;
    public static final int TAKE_GALLERY_PIC_FILE_REQUEST_CODE = 201;

    private LatLng defCurrentCityPoint = new LatLng(30.973649, 111.896328);// 默认的中心点经纬度
    private ArrayList<ClusterItem> clusterItems;
    private BottomSheetBehavior mBottomSheetBehavior;
    private PermissionInterceptor mPermissionInterceptor;
    private AMapLocation mLocation;//当前具体位置
    private TextureMapView mapView;
    private ClusterOverlay mClusterOverlay;
    private AMap aMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    //路灯杆位置列表
    private List<WorkAreaListData.DataBean> areListData;
    private List<RegionBean> lightPole1List = new ArrayList<>();
    private List<RegionBean> lightPole2List = new ArrayList<>();
    private List<RegionBean> lightPole3List = new ArrayList<>();
    private RegionSelectDialog regionSelectDialog;
    private String lightPole1_name;
    private String lightPole2_name;
    private String lightPole3_name;
    private Integer area_id;
    private Integer stree_id;
    private Integer site_id;
    private String site_name;
    private WorkAreaListData.DataBean areaSelect1Data;
    private WorkAreaListData.DataBean.ChildrenListBeanXX areaSelect2Data;
    private WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX areaSelect3Data;

    private TitleBar.ImageAction brushimageAction;
    private TitleBar titleBar;
    public static final int REQUEST_CODE = 111;
    private SharedPreferences preferences;

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        titleBar = super.initTitle();
        String titleStr = "";
        switch (device_info.device_type) {
            case LampPoleDetailFragment.deviceType:
                titleStr = "新增灯杆";
                break;
            case LampLightDetailLightListFragment.deviceType:
                titleStr = "新增灯具";
                break;
            case PowerCabinetDetailFragment.deviceType:
                titleStr = "新增配电柜";
                break;
            case CenterControlDetailFragment.deviceType:
                titleStr = "新增集控";
                break;
        }
        titleBar.setTitle(titleStr);
        creatTitleBarAction();
        return titleBar;
    }


    //创建导航条
    public void creatTitleBarAction(){
        brushimageAction = new TitleBar.ImageAction(R.mipmap.icon_qrcodenewest) {
            @Override
            public void performAction(View view) {
                getcode();
            }
        };
        titleBar.addAction(brushimageAction);
    }

    public void getcode(){
        XQRCode.startScan(this, REQUEST_CODE, 0);//二维码扫描
        ToastUtils.toast("扫描");
    }

    @Override
    protected void initViews() {

        binding.tvDeviceName.tvTitle.setText("设备名称:");
        binding.tvDeviceNum.tvTitle.setText("设备编号:");
        binding.tvDeviceCount.tvTitle.setText("设备型号:");
        binding.tvSiteName.tvTitle.setText("街道名称:");
        binding.tvDeviceProduct.tvTitle.setText("设备厂家:");

        binding.tvDeviceName.tvContent.setHint("点击输入");
        binding.tvDeviceNum.tvContent.setHint("点击输入");
        binding.tvDeviceCount.tvContent.setHint("点击输入");
        binding.tvSiteName.tvContent.setHint("点击输入");
        binding.tvDeviceProduct.tvContent.setHint("点击输入");
        binding.deviceCoordinate.longitudeContent.setHint("点击输入");
        binding.deviceCoordinate.latitudeContent.setHint("点击输入");

        //初始化时，传入RegionLevel设置三级联动or四级联动）
        regionSelectDialog = new RegionSelectDialog(mActivity, RegionLevel.LEVEL_THREE);
        preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));


        crareLightListView();

        if(device_info != null) {
            binding.tvDeviceName.tvContent.setText(device_info.name);
            binding.tvDeviceNum.tvContent.setText(device_info.num);
            binding.tvDeviceCount.tvContent.setText(device_info.model);
            binding.tvSiteName.tvContent.setText(device_info.sitename);
            binding.tvDeviceProduct.tvContent.setText(device_info.Manufacturer);

            binding.lampLightInfo.setVisibility(device_info.device_type == LampPoleDetailFragment.deviceType?View.VISIBLE:View.GONE);
            binding.lightRecyclerview.setVisibility(device_info.device_type == LampPoleDetailFragment.deviceType?View.VISIBLE:View.GONE);

            if(device_info.device_type == LampPoleDetailFragment.deviceType){
                String siteid = preferences.getString("site_id","");
                String sitename = preferences.getString("site_name","");

                if(siteid.length() >0 && sitename.length()>0) {
                    site_id = Integer.parseInt(siteid);
                    site_name = sitename;
                    binding.tvSiteName.tvContent.setText(site_name);
                }
                binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
                binding.tvDeviceProduct.getRoot().setVisibility(View.GONE);
            }else if(device_info.device_type == LampLightDetailLightListFragment.deviceType){
                binding.tvDeviceProduct.imgMark.setVisibility(View.GONE);
            }else if(device_info.device_type == PowerCabinetDetailFragment.deviceType){
                binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
                binding.tvDeviceProduct.getRoot().setVisibility(View.GONE);
            }else {
                binding.tvDeviceCount.imgMark.setVisibility(View.GONE);
                binding.tvDeviceProduct.imgMark.setVisibility(View.GONE);
            }
        }

        binding.tvCancel.setOnClickListener(this::onClick);
        binding.tvConfirm.setOnClickListener(this::onClick);
        binding.tvSiteName.tvContent.setOnClickListener(this::onClick);
        binding.tvSiteName.tvBaseview.setOnClickListener(this::onClick);
        binding.tvSiteName.selectMore.setOnClickListener(this::onClick);
        binding.llBackCurrentLocation.setOnClickListener(this::onClick);
        binding.lightAdd.setOnClickListener(this::onClick);
        binding.lightDel.setOnClickListener(this::onClick);

        queryLocationList();
        queryLampPosition();
        queryLampDevice();

        initListData();
    }

    public void initListData(){
        light_count = 1;
        DeviceAddBean bean = new DeviceAddBean();
        bean.device_num = "";
        deviceAddBeanList.add(bean);
        listadapter.setmDatas(deviceAddBeanList);
    }
    public void crareLightListView(){
        //设备列表
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(),1);
        binding.lightRecyclerview.setLayoutManager(device_manager);
        listadapter = new DeviceAddAdapter();
        binding.lightRecyclerview.setAdapter(listadapter);
        listadapter.setOnItemClick(new DeviceAddAdapter.OnItemClick() {
            @Override
            public void click(int index) {

            }
        });
        listadapter.setOnSelectClick(new DeviceAddAdapter.OnSelectClick() {
            @Override
            public void selectclick(int index, TextView type, TextView num) {
                showStreeConstellationPickerView(index,type,num);
            }
        });

        listadapter.setonEditClick(new DeviceAddAdapter.OnEditClick() {
            @Override
            public void editclick(int index, String edittext) {
                deviceAddBeanList.get(index).device_num = edittext;
            }
        });
    }
    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDeviceAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //先拿位置
        try {
            MapsInitializer.updatePrivacyShow(mActivity, true, true);
            MapsInitializer.updatePrivacyAgree(mActivity,true);
            checkPermissionAndSetMapCenterPos(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 权限申请检查
     *
     * @param savedInstanceState
     */
    public void checkPermissionAndSetMapCenterPos(Bundle savedInstanceState) {
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
                            locationClientOption.setInterval(2000);
                            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                            locationClientSingle.setLocationOption(locationClientOption);
                            locationClientSingle.setLocationListener(location -> {

                                AMapOptions aOptions = new AMapOptions();

                                if (location != null && location.getLatitude() > 0) {//拿到了位置信息
                                    mLocation = location;
                                    defCurrentCityPoint = new LatLng(location.getLatitude(), location.getLongitude());
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 19f, 0, 0));

                                } else {//没有拿到位置信息
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 5f, 0, 0));

                                }

                                mapView = new TextureMapView(getContext(), aOptions);
                                mapView.onCreate(savedInstanceState);
                                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                binding.reMap.addView(mapView, mParams);
                                binding.llBackCurrentLocation.bringToFront();


                                if (aMap == null) {
                                    aMap = mapView.getMap();
                                }
                                mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
                                mUiSettings.setZoomControlsEnabled(false);
                                mUiSettings.setMyLocationButtonEnabled(false);

                                aMap.setOnMapLoadedListener(DeviceAddFragment.this::onMapLoaded);
                                aMap.setOnCameraChangeListener(DeviceAddFragment.this);

                                getMaplocation();
                            });
                            locationClientSingle.startLocation();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionReject(String[] permissions) {
                    }

                    public void onLocationChanged(AMapLocation amapLocation) {
                        if (amapLocation != null) {
                            if (amapLocation.getErrorCode() == 0) {
                                //定位成功回调信息，设置相关消息
                                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                                amapLocation.getLatitude();//获取纬度
                                amapLocation.getLongitude();//获取经度
                                amapLocation.getAccuracy();//获取精度信息

                            } else {
                                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                                Log.e("AmapError","location Error, ErrCode:"
                                        + amapLocation.getErrorCode() + ", errInfo:"
                                        + amapLocation.getErrorInfo());
                            }
                        }
                    }
                })
                .checkPermission();

    }

    public void getMaplocation(){
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

        //以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.showMyLocation(true);
    }

    @Override
    public void onMapLoaded() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_location)));
        Marker mPositionMark = aMap.addMarker(markerOptions);
        mPositionMark.showInfoWindow();//主动显示indowindowm
        mPositionMark.setPositionByPixels((mapView.getWidth() / 2)  ,(mapView.getHeight() / 2));
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        binding.deviceCoordinate.longitudeContent.setText(String.valueOf(cameraPosition.target.longitude));
        binding.deviceCoordinate.latitudeContent.setText(String.valueOf(cameraPosition.target.latitude));
        binding.scrollerLayout.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        binding.deviceCoordinate.longitudeContent.setText(String.valueOf(cameraPosition.target.longitude));
        binding.deviceCoordinate.latitudeContent.setText(String.valueOf(cameraPosition.target.latitude));
        binding.scrollerLayout.requestDisallowInterceptTouchEvent(false);
    }


    @Override
    public void onClick(View v) {
        if(v == binding.tvCancel){
            popToBack();
        }else if(v == binding.tvSiteName.tvBaseview || v == binding.tvSiteName.tvContent || v == binding.tvSiteName.selectMore){
            showAreaBottomView();
        }else if(v == binding.tvConfirm){

            if(binding.tvDeviceName.tvContent.getText().length() == 0){
                XToastUtils.info("请输入设备名称");
                return;
            }
            if(binding.tvDeviceNum.tvContent.getText().length() == 0){
                XToastUtils.info("请输入设备编号");
                return;
            }
            if(binding.tvDeviceCount.tvContent.getText().length() == 0){
                if(device_info.device_type == LampLightDetailLightListFragment.deviceType){
                    XToastUtils.info("请输入设备型号");
                    return;
                }
            }
            if(binding.tvSiteName.tvContent.getText().length() == 0){
                XToastUtils.info("请输入站点名称");
                return;
            }

            switch (device_info.device_type) {
                case LampPoleDetailFragment.deviceType:
                    LampPoleAdd();
                    break;
                case LampLightDetailLightListFragment.deviceType:
                    LampDeviceAdd();
                    break;
                case PowerCabinetDetailFragment.deviceType:
                    CabinetAdd();
                    break;
                case CenterControlDetailFragment.deviceType:
                    ControlAdd();
                    break;
            }
        }else if(v == binding.llBackCurrentLocation){
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defCurrentCityPoint, 19f));
        }
        else if(v == binding.lightAdd){//添加灯具信息

            if(light_count<=10) {
                light_count ++;

                DeviceAddBean bean = new DeviceAddBean();
                bean.device_num = "";
                deviceAddBeanList.add(bean);
                listadapter.setmDatas(deviceAddBeanList);
            }else {
                XToastUtils.info("最多可添加10条");
            }

            if(light_count >1){
                binding.lightDel.setVisibility(View.VISIBLE);
            }
            binding.scrollerLayout.scrollTo(0,10000);
        }else if(v == binding.lightDel){

            if(light_count >1){
                light_count --;

                deviceAddBeanList.remove(deviceAddBeanList.size()-1);
                listadapter.setmDatas(deviceAddBeanList);
            }

            if(light_count == 1) {
                binding.lightDel.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        XLogger.debug(true);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_CAMERA_PIC_FILE_REQUEST_CODE:

                    break;
                case TAKE_GALLERY_PIC_FILE_REQUEST_CODE:

                    break;
                case REQUEST_CODE:
                    if(data == null){
                        getActivity().finish();
                    }
                    //处理二维码扫描结果
                    handleScanResult(data);
                    break;
                default:


                    break;
            }
        }
    }

    //验证设备编号
    private void uniqueCode(){

    }
    //添加灯杆
    private void LampPoleAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("name", binding.tvDeviceName.tvContent.getText());
            body.put("num", binding.tvDeviceNum.tvContent.getText());
            body.put("model", binding.tvDeviceCount.tvContent.getText());
            body.put("siteId", site_id);
            body.put("manufacturer", binding.tvDeviceProduct.tvContent.getText());
            body.put("longitude", binding.deviceCoordinate.longitudeContent.getText());
            body.put("latitude", binding.deviceCoordinate.latitudeContent.getText());
            body.put("location", binding.tvSiteName.tvContent.getText());

            JSONArray list = new JSONArray();
            for (DeviceAddBean bean : deviceAddBeanList) {
                JSONObject body1 = new JSONObject();
                body1.put("num", bean.device_num);
                body1.put("deviceTypeId", bean.device_type_id);
                body1.put("deviceType",bean.device_type);
                body1.put("lampPostNumber",0);
                list.put(body1);
            }

            body.put("singleLampParamReqVOs", list);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpRequest.JSONPOST(mActivity, HttpApi.API_DLM_SL_LAMP_POST_APPADD, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {

                    //获取editor对象
                    SharedPreferences.Editor editor = preferences.edit();
                    //存储数据时选用对应类型的方法
                    editor.putString("site_id",String.valueOf(site_id));
                    editor.putString("site_name",site_name);
                    //提交保存数据
                    editor.commit();

                    XToastUtils.success(mapLampCommonDevList.message);
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_DENGGAN_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }
            }
        });
    }

    //添加灯具
    private void LampDeviceAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {

            body.put("deviceTypeId", 7);//测试用

            JSONObject body1 = new JSONObject();
            body1.put("name", binding.tvDeviceName.tvContent.getText());
            body1.put("lampPositionId", 1);

            JSONArray list = new JSONArray();
            list.put(body1);

            body.put("loopParamVOList", list);
            body.put("num", binding.tvDeviceNum.tvContent.getText());
            body.put("lampPostId",2177);

        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest.JSONPOST(mActivity, HttpApi.API_SL_LAMP_DEVICE_ADD, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.message);
                    popToBack();
                }
            }
        });
    }

    //添加配电柜
    private void CabinetAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("name", binding.tvDeviceName.tvContent.getText());
            body.put("num", binding.tvDeviceNum.tvContent.getText());
            body.put("model", binding.tvDeviceCount.tvContent.getText());
            body.put("areaId", area_id);
            body.put("streetId", stree_id);
            body.put("manufacturer", binding.tvDeviceProduct.tvContent.getText());
            body.put("longitude", binding.deviceCoordinate.longitudeContent.getText());
            body.put("latitude", binding.deviceCoordinate.latitudeContent.getText());
            body.put("location", binding.tvSiteName.tvContent.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest.JSONPOST(mActivity, HttpApi.API_DLM_LOCATION_DISTRIBUTE_CABINET_ADD, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.message);
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_PEIDIANGUI_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }
            }
        });
    }

    //添加集控
    private void ControlAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("cabinetId", 145);//测试用
            body.put("typeId", 4);
            body.put("name", binding.tvDeviceName.tvContent.getText());
            body.put("num", binding.tvDeviceNum.tvContent.getText());
            body.put("location", binding.tvSiteName.tvContent.getText());

        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest.JSONPOST(mActivity, HttpApi.API_DLM_LOCATION_CONTROL_ADD, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success(mapLampCommonDevList.message);
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_JIKONG_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }
            }
        });
    }

    /**
     * 查询灯具位置
     */
    private void queryLampPosition() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi.API_SL_LAMP_POSITION_LIST, parameter, new BeanResponseListener<WorkAreaListData>() {
            @Override
            public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                if(error == null) {

                }
            }
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
                }
            }
        });
    }

    /**
     * 查询设备类型
     */
    private void queryLampDevice() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        parameter.put("filed","device_type");
        HttpRequest.GET(mActivity, HttpApi.API_DLM_SYSTEM_AREA_PARAMETER_BYFILED, parameter, new BeanResponseListener<DeviceSystemBean>() {
            @Override
            public void onResponse(DeviceSystemBean deviceSystemBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    dataBeanList = deviceSystemBean.getData();
                    for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                        mStreeOption.add(dataBean.getName());
                    }
                }
            }
        });
    }

    private void showStreeConstellationPickerView(int index, TextView type, TextView num) {
        typeSelectOption = mStreeOption.size()/2;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            type.setText(mStreeOption.get(options1));
            num.setHint("请输入"+dataBeanList.get(options1).getSystemDeviceType().getNumLength()+"字");
            deviceAddBeanList.get(index).device_type = mStreeOption.get(options1);
            deviceAddBeanList.get(index).device_type_id = dataBeanList.get(options1).getValue();

            typeSelectOption = options1;

            return false;
        })
                .setTitleText("选择协议类型")
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }

    /**
     * 分区 街道 站点选择框
     * @param areListData
     */
    private void showAreaListDialog(List<WorkAreaListData.DataBean> areListData) {

        /**
         * 一共四级，每一级都用id做区分
         */

        if (null == areListData || areListData.size() == 0) {
            XToastUtils.error("数据异常或无灯杆");
            return;
        }
        lightPole1List = new ArrayList<>();
        for (WorkAreaListData.DataBean dataBean : areListData) {
            RegionBean temp = new RegionBean(dataBean.getId(), dataBean.getName());
            lightPole1List.add(temp);
        }

        regionSelectDialog.setOnRegionDataSetListenr(new OnRegionDataSetListener() {

            //必须有返回值
            @Override
            public List<RegionBean> setProvinceList() {
                return lightPole1List;
            }

            //必须有返回值
            @Override
            public List<RegionBean> setOnProvinceSelected(RegionBean regionBean1) {
                lightPole1_name = regionBean1.getName();
                area_id = regionBean1.getId();

                //查找选中的第1级下的第2级的数据
                ArrayList<RegionBean> lightPole2List = new ArrayList<>();
                areaSelect1Data = null;
                for (WorkAreaListData.DataBean dataBean : areListData) {
                    if (regionBean1.getId() == dataBean.getId()) {
                        areaSelect1Data = dataBean;
                        break;
                    }
                }
                if (null == areaSelect1Data || areaSelect1Data.getChildrenList().size() == 0) {
                    return lightPole2List;
                }
                for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : areaSelect1Data.getChildrenList()) {
                    lightPole2List.add(new RegionBean(childrenListBeanXX.getId(), childrenListBeanXX.getName()));
                }
                return lightPole2List;
            }

            @Override
            public List<RegionBean> setOnCitySelected(RegionBean regionBean2) {
                lightPole2_name = regionBean2.getName();
                stree_id = regionBean2.getId();

                ArrayList<RegionBean> lightPole3List = new ArrayList<>();
                //查找选中的第2级下的第3级的数据
                for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : areaSelect1Data.getChildrenList()) {
                    if (regionBean2.getId() == childrenListBeanXX.getId()) {
                        areaSelect2Data = childrenListBeanXX;
                    }
                }
                for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX childrenListBeanX : areaSelect2Data.getChildrenList()) {
                    lightPole3List.add(new RegionBean(childrenListBeanX.getId(), childrenListBeanX.getName()));
                }
                return lightPole3List;
            }

            @Override
            public List<RegionBean> setOnZoneSelected(RegionBean regionBean3) {
                lightPole3_name = regionBean3.getName();
                site_id = regionBean3.getId();
                myHandler.sendEmptyMessage(123);
                return null;
            }

            @Override
            public void setOnAreaSelected(RegionBean regionBean4) {

            }

        });


        //显示
        regionSelectDialog.showDialog();
    }

    private void showAreaBottomView(){
        List<WorkAreaListData.DataBean> fiddlerList = new ArrayList<>();
        fiddlerList = areListData;

        if (fiddlerList != null && fiddlerList.size() == 0) {
            XToastUtils.error("暂时没有数据，请稍后");
            return;
        }

        //处理筛选
        DevFiddlerUtils.showAreaSiteDialog(mActivity, device_info.device_type, fiddlerList,
                (int showDeviceTypeId, DevFiddlerUtils.SelectResult selectResult) -> {

                    area_id = selectResult.areaId;
                    stree_id = selectResult.streetId;
                    site_id = selectResult.siteId;
                    site_name = selectResult.siteName;
                    binding.tvSiteName.tvContent.setText(selectResult.siteName);
                });
    }

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    binding.tvSiteName.tvContent.setText(lightPole1_name + "," + lightPole2_name + "," + lightPole3_name);
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            handleScanResult(data);
        }
    }

    /**
     * 处理二维码扫描结果
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    Log.i("测试用",result);
                    if(result != ""){
                        String[] arr = result.split("_");
                        if(arr.length >3) {
                            if (arr.length > 1 && arr[1].length() > 0) {
                                device_info.num = arr[1];
                            }
                            if (arr.length > 3 && arr[3].length() > 0) {
                                device_info.model = arr[3];
                            }
                        }else if(arr.length >2){
                            if (arr.length > 1 && arr[1].length() > 0) {
                                device_info.num = arr[1];
                            }
                        }
                    }

                    //填充设备编号
                    if(device_info.num != null){

                        for (DeviceAddBean bean : deviceAddBeanList) {
                            if(bean.device_num == null || bean.device_num.length() ==0){
                                bean.device_num = device_info.num;

                                if(device_info.model != null)
                                {
                                    for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                                        if(dataBean.getName().contains(device_info.model)){
                                            bean.device_type = device_info.model;
                                            bean.device_type_id = dataBean.getValue();

                                            break;
                                        }
                                    }

                                    if (bean.device_type_id == null) {
                                        String systemId = "";
                                        String name = "";
                                        for (DeviceSystemBean.DataBean dataBean : dataBeanList) {
                                            if (device_info.model.equals("EXC-TL1-N110E-2") && dataBean.getName().equals("EXC-TL1-N110E-STQ-Ⅱ")) {
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N110E-3") && dataBean.getName().equals("EXC-TL1-N110E-SMO-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N210E-2") && dataBean.getName().equals("EXC-TL1-N210E-STQ-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N210E-3") && dataBean.getName().equals("EXC-TL1-N210E-SMO-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-L110E-2") && dataBean.getName().equals("EXC-TL1-L110E-0")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-L210E-2") && dataBean.getName().equals("EXC-TL1-L210E-0")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C110E-2") && dataBean.getName().equals("EXC-TL1-C110E-SWQ-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C210E-2") && dataBean.getName().equals("EXC-TL1-C210E-SWQ-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C310E-2") && dataBean.getName().equals("EXC-TL1-C21ME-FWQ-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C110E-TFLX") && dataBean.getName().equals("EXC-TL1-C11ME-FWQ-Ⅱ")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }


                                        }

                                        bean.device_type = name;
                                        bean.device_type_id = systemId;
                                    }
                                }
                                break;
                            }
                        }

                        listadapter.setmDatas(deviceAddBeanList);
                    }

                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    XToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
                }
            }
        }

    }
}
