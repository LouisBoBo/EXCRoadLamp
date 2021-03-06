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

    private LatLng defCurrentCityPoint = new LatLng(30.973649, 111.896328);// ???????????????????????????
    private ArrayList<ClusterItem> clusterItems;
    private BottomSheetBehavior mBottomSheetBehavior;
    private PermissionInterceptor mPermissionInterceptor;
    private AMapLocation mLocation;//??????????????????
    private TextureMapView mapView;
    private ClusterOverlay mClusterOverlay;
    private AMap aMap;
    private UiSettings mUiSettings;//????????????UiSettings??????
    //?????????????????????
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
                titleStr = "????????????";
                break;
            case LampLightDetailLightListFragment.deviceType:
                titleStr = "????????????";
                break;
            case PowerCabinetDetailFragment.deviceType:
                titleStr = "???????????????";
                break;
            case CenterControlDetailFragment.deviceType:
                titleStr = "????????????";
                break;
        }
        titleBar.setTitle(titleStr);
        creatTitleBarAction();
        return titleBar;
    }


    //???????????????
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
        XQRCode.startScan(this, REQUEST_CODE, 0);//???????????????
        ToastUtils.toast("??????");
    }

    @Override
    protected void initViews() {

        binding.tvDeviceName.tvTitle.setText("????????????:");
        binding.tvDeviceNum.tvTitle.setText("????????????:");
        binding.tvDeviceCount.tvTitle.setText("????????????:");
        binding.tvSiteName.tvTitle.setText("????????????:");
        binding.tvDeviceProduct.tvTitle.setText("????????????:");

        binding.tvDeviceName.tvContent.setHint("????????????");
        binding.tvDeviceNum.tvContent.setHint("????????????");
        binding.tvDeviceCount.tvContent.setHint("????????????");
        binding.tvSiteName.tvContent.setHint("????????????");
        binding.tvDeviceProduct.tvContent.setHint("????????????");
        binding.deviceCoordinate.longitudeContent.setHint("????????????");
        binding.deviceCoordinate.latitudeContent.setHint("????????????");

        //?????????????????????RegionLevel??????????????????or???????????????
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
        //????????????
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

        //????????????
        try {
            MapsInitializer.updatePrivacyShow(mActivity, true, true);
            MapsInitializer.updatePrivacyAgree(mActivity,true);
            checkPermissionAndSetMapCenterPos(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @param savedInstanceState
     */
    public void checkPermissionAndSetMapCenterPos(Bundle savedInstanceState) {
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
                            locationClientOption.setInterval(2000);
                            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                            locationClientSingle.setLocationOption(locationClientOption);
                            locationClientSingle.setLocationListener(location -> {

                                AMapOptions aOptions = new AMapOptions();

                                if (location != null && location.getLatitude() > 0) {//?????????????????????
                                    mLocation = location;
                                    defCurrentCityPoint = new LatLng(location.getLatitude(), location.getLongitude());
                                    aOptions.camera(new CameraPosition(defCurrentCityPoint, 19f, 0, 0));

                                } else {//????????????????????????
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
                                mUiSettings = aMap.getUiSettings();//?????????UiSettings?????????
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
                                //?????????????????????????????????????????????
                                amapLocation.getLocationType();//??????????????????????????????????????????????????????????????????????????????
                                amapLocation.getLatitude();//????????????
                                amapLocation.getLongitude();//????????????
                                amapLocation.getAccuracy();//??????????????????

                            } else {
                                //??????????????????ErrCode???????????????errInfo???????????????????????????????????????
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
        myLocationStyle = new MyLocationStyle();//??????????????????????????????myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????

        //?????????????????????5.1.0??????????????????
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.interval(2000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);?????????????????????????????????????????????????????????
        aMap.setMyLocationEnabled(false);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???

        myLocationStyle.showMyLocation(true);
    }

    @Override
    public void onMapLoaded() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_location)));
        Marker mPositionMark = aMap.addMarker(markerOptions);
        mPositionMark.showInfoWindow();//????????????indowindowm
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
                XToastUtils.info("?????????????????????");
                return;
            }
            if(binding.tvDeviceNum.tvContent.getText().length() == 0){
                XToastUtils.info("?????????????????????");
                return;
            }
            if(binding.tvDeviceCount.tvContent.getText().length() == 0){
                if(device_info.device_type == LampLightDetailLightListFragment.deviceType){
                    XToastUtils.info("?????????????????????");
                    return;
                }
            }
            if(binding.tvSiteName.tvContent.getText().length() == 0){
                XToastUtils.info("?????????????????????");
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
        else if(v == binding.lightAdd){//??????????????????

            if(light_count<=10) {
                light_count ++;

                DeviceAddBean bean = new DeviceAddBean();
                bean.device_num = "";
                deviceAddBeanList.add(bean);
                listadapter.setmDatas(deviceAddBeanList);
            }else {
                XToastUtils.info("???????????????10???");
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
                    //???????????????????????????
                    handleScanResult(data);
                    break;
                default:


                    break;
            }
        }
    }

    //??????????????????
    private void uniqueCode(){

    }
    //????????????
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

                    //??????editor??????
                    SharedPreferences.Editor editor = preferences.edit();
                    //??????????????????????????????????????????
                    editor.putString("site_id",String.valueOf(site_id));
                    editor.putString("site_name",site_name);
                    //??????????????????
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

    //????????????
    private void LampDeviceAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {

            body.put("deviceTypeId", 7);//?????????

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

    //???????????????
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

    //????????????
    private void ControlAdd() {
        mMessageLoader.show();
        JSONObject body = new JSONObject();
        try {
            body.put("cabinetId", 145);//?????????
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
     * ??????????????????
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
     * ???????????? ?????? ??????
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
     * ??????????????????
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
            num.setHint("?????????"+dataBeanList.get(options1).getSystemDeviceType().getNumLength()+"???");
            deviceAddBeanList.get(index).device_type = mStreeOption.get(options1);
            deviceAddBeanList.get(index).device_type_id = dataBeanList.get(options1).getValue();

            typeSelectOption = options1;

            return false;
        })
                .setTitleText("??????????????????")
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(mStreeOption);
        pvOptions.show();
    }

    /**
     * ?????? ?????? ???????????????
     * @param areListData
     */
    private void showAreaListDialog(List<WorkAreaListData.DataBean> areListData) {

        /**
         * ??????????????????????????????id?????????
         */

        if (null == areListData || areListData.size() == 0) {
            XToastUtils.error("????????????????????????");
            return;
        }
        lightPole1List = new ArrayList<>();
        for (WorkAreaListData.DataBean dataBean : areListData) {
            RegionBean temp = new RegionBean(dataBean.getId(), dataBean.getName());
            lightPole1List.add(temp);
        }

        regionSelectDialog.setOnRegionDataSetListenr(new OnRegionDataSetListener() {

            //??????????????????
            @Override
            public List<RegionBean> setProvinceList() {
                return lightPole1List;
            }

            //??????????????????
            @Override
            public List<RegionBean> setOnProvinceSelected(RegionBean regionBean1) {
                lightPole1_name = regionBean1.getName();
                area_id = regionBean1.getId();

                //??????????????????1????????????2????????????
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
                //??????????????????2????????????3????????????
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


        //??????
        regionSelectDialog.showDialog();
    }

    private void showAreaBottomView(){
        List<WorkAreaListData.DataBean> fiddlerList = new ArrayList<>();
        fiddlerList = areListData;

        if (fiddlerList != null && fiddlerList.size() == 0) {
            XToastUtils.error("??????????????????????????????");
            return;
        }

        //????????????
        DevFiddlerUtils.showAreaSiteDialog(mActivity, device_info.device_type, fiddlerList,
                (int showDeviceTypeId, DevFiddlerUtils.SelectResult selectResult) -> {

                    area_id = selectResult.areaId;
                    stree_id = selectResult.streetId;
                    site_id = selectResult.siteId;
                    site_name = selectResult.siteName;
                    binding.tvSiteName.tvContent.setText(selectResult.siteName);
                });
    }

    //??????????????????UI????????????
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
     * ???????????????????????????
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    Log.i("?????????",result);
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

                    //??????????????????
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
                                            if (device_info.model.equals("EXC-TL1-N110E-2") && dataBean.getName().equals("EXC-TL1-N110E-STQ-???")) {
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N110E-3") && dataBean.getName().equals("EXC-TL1-N110E-SMO-???")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N210E-2") && dataBean.getName().equals("EXC-TL1-N210E-STQ-???")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-N210E-3") && dataBean.getName().equals("EXC-TL1-N210E-SMO-???")){
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
                                            }else if(device_info.model.equals("EXC-TL1-C110E-2") && dataBean.getName().equals("EXC-TL1-C110E-SWQ-???")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C210E-2") && dataBean.getName().equals("EXC-TL1-C210E-SWQ-???")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C310E-2") && dataBean.getName().equals("EXC-TL1-C21ME-FWQ-???")){
                                                systemId = dataBean.getValue();
                                                name = dataBean.getName();
                                                break;
                                            }else if(device_info.model.equals("EXC-TL1-C110E-TFLX") && dataBean.getName().equals("EXC-TL1-C11ME-FWQ-???")){
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
                    XToastUtils.toast("?????????????????????", Toast.LENGTH_LONG);
                }
            }
        }

    }
}
