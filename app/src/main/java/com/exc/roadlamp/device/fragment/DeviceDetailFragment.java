package com.exc.roadlamp.device.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDeviceDetailBinding;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampCommonDetail;
import com.exc.roadlamp.device.bean.LampControlDetail;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(anim = CoreAnim.slide)
public class DeviceDetailFragment extends MyBaseFragment implements OnBannerListener , View.OnClickListener {
    private FragmentDeviceDetailBinding binding;
    private ArrayList<String> list_path;
    private Banner banner;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        if(eventMessage.getEventEnum() == EventEnum.SHOW_DEVICE_INFO_QRCODE){
            XToastUtils.toast("生产日期:" + "20218888", Toast.LENGTH_LONG);
        }
    }

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setTitle(device_info.name);
        return titleBar;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDeviceDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        binding.tvDeviceName.tvTitle.setText("设备名称:");
        binding.tvDeviceNum.tvTitle.setText("设备编号:");
        binding.tvDeviceCount.tvTitle.setText("设备型号:");
        binding.tvSiteName.tvTitle.setText("街道名称:");
        binding.tvDeviceProduct.tvTitle.setText("设备厂家:");

        binding.tvDeviceName.imgMark.setVisibility(View.GONE);
        binding.tvDeviceNum.imgMark.setVisibility(View.GONE);
        binding.tvDeviceCount.imgMark.setVisibility(View.GONE);
        binding.tvSiteName.imgMark.setVisibility(View.GONE);
        binding.tvDeviceProduct.imgMark.setVisibility(View.GONE);

        binding.tvDeviceName.tvContent.setEnabled(false);
        binding.tvDeviceNum.tvContent.setEnabled(false);
        binding.tvDeviceCount.tvContent.setEnabled(false);
        binding.tvSiteName.tvContent.setEnabled(false);
        binding.tvDeviceProduct.tvContent.setEnabled(false);

        binding.tvSiteName.selectMore.setVisibility(View.INVISIBLE);

        binding.tvScanmap.setOnClickListener(this::onClick);
        binding.tvDeleate.setOnClickListener(this::onClick);

        setBanner();

        switch (device_info.device_type){
            case LampPoleDetailFragment.deviceType:
                queryLamp();
                break;

            case LampLightDetailLightListFragment.deviceType:
                binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
                binding.tvDeviceProduct.getRoot().setVisibility(View.GONE);
                queryLampDevice();
                break;

            case PowerCabinetDetailFragment.deviceType:
                binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
                binding.tvDeviceProduct.getRoot().setVisibility(View.GONE);
                queryCabinet();
                break;

            case CenterControlDetailFragment.deviceType:
                binding.tvDeviceCount.getRoot().setVisibility(View.GONE);
                binding.tvDeviceProduct.getRoot().setVisibility(View.GONE);
                queryControl();
                break;
        }

    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //设置图片数据
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/2a919def19fc47e3aa0d75d8c227ab1b.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d027d1efc0564c44bb979ba0bd21f560.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/bbb930d66e5a48baa8d3c143544d7631.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/fb1721b8c9be4da9949fcdd26fc902a2.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/08b58dde9b284638b44e2d03c4cb9acf.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d3caeb6129ee43df87f5c1e1058d96fc.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/9fd01c4add07473db31ba850f20a7232.jpg");
        list_path.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");

        banner = binding.banner;

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ImgLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this::OnBannerClick)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        openNewPage(DeviceBigImageFragment.class,"phonto_index",position);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.tvScanmap){//查看地图
            popToBack();

            //切换到地图界面
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.SHOW_DEVICE_MAP_FROMLIST);
            EventBus.getDefault().post(eventMessage);

            //在地图上定位所选择的设备
            DevMapSearchResultListBean mapbean = new DevMapSearchResultListBean();
            if(device_info.device_type == LampLightDetailLightListFragment.deviceType){
                mapbean.setLampPostId(device_info.lamppostid);
            }
            mapbean.setDevId(device_info.id);
            mapbean.setDevName(device_info.name);
            mapbean.setDeviceType(device_info.device_type);

            EventMessage<DevMapSearchResultListBean> mapeventMessage = new EventMessage<>();
            mapeventMessage.setEventEnum(EventEnum.DEV_MAP_SEARCH_RESULT_LIST_CLICK);
            mapeventMessage.setData(mapbean);
            EventBus.getDefault().post(mapeventMessage);
        }else if(v == binding.tvDeleate){//删除
            switch (device_info.device_type) {
                case LampLightDetailLightListFragment.deviceType:
                    List<String> dds = new ArrayList<>();
                    dds.add(String.valueOf(device_info.id));
                    deleateSlDevices(dds);
                    break;
                case PowerCabinetDetailFragment.deviceType:

                    List<String> cids = new ArrayList<>();
                    cids.add(String.valueOf(device_info.id));
                    deleateCabinets(cids);
                    break;
                case CenterControlDetailFragment.deviceType:

                    List<String> tids = new ArrayList<>();
                    tids.add(String.valueOf(device_info.id));
                    deleateControls(tids);
                    break;
            }
        }
    }

    //自定义的图片加载器
    private class ImgLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    /**
     * 查询灯杆详情
     */
    private void queryLamp() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_DETAIL + device_info.id, parameter, new BeanResponseListener<LampCommonDetail>() {
            @Override
            public void onResponse(LampCommonDetail lampCommonDetail, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && lampCommonDetail != null) {

                    binding.tvDeviceName.tvContent.setText(lampCommonDetail.getData().getName());
                    binding.tvDeviceNum.tvContent.setText(lampCommonDetail.getData().getNum());
                    binding.tvDeviceCount.tvContent.setText(String.valueOf(lampCommonDetail.getData().getModel()));
                    binding.tvSiteName.tvContent.setText(String.valueOf(lampCommonDetail.getData().getLocation()));
                    binding.tvDeviceProduct.tvContent.setText(String.valueOf(lampCommonDetail.getData().getManufacturer()));
                    binding.deviceCoordinate.longitudeContent.setText(String.valueOf(lampCommonDetail.getData().getLongitude()));
                    binding.deviceCoordinate.latitudeContent.setText(String.valueOf(lampCommonDetail.getData().getLatitude()));
                }
            }
        });
    }

    /**
     * 查询灯具详情
     */
    private void queryLampDevice() {

        binding.tvDeviceName.tvContent.setText(device_info.name);
        binding.tvDeviceNum.tvContent.setText(device_info.num);
        binding.tvSiteName.tvContent.setText(device_info.sitename);
        binding.tvDeviceCount.tvContent.setText(String.valueOf(device_info.model));
        binding.tvDeviceProduct.tvContent.setText(String.valueOf(device_info.Manufacturer));
        binding.deviceCoordinate.longitudeContent.setText(String.valueOf(device_info.longuide));
        binding.deviceCoordinate.latitudeContent.setText(String.valueOf(device_info.latitude));

        if(device_info.sitename != null && device_info.sitename.toString() != null){
            String str = device_info.sitename.toString();
            String[] strarray= str.split(",");
            if(strarray.length>2){
                binding.tvSiteName.tvContent.setText(strarray[2]);
            }
        }
    }

    /**
     * 查询配电柜详情
     */
    private void queryCabinet() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_DLM_LOCATION_DISTRIBUTE_CABINET + device_info.id, parameter, new BeanResponseListener<LampCommonDetail>() {
            @Override
            public void onResponse(LampCommonDetail lampCommonDetail, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && lampCommonDetail != null) {

                    binding.tvDeviceName.tvContent.setText(lampCommonDetail.getData().getName());
                    binding.tvDeviceNum.tvContent.setText(lampCommonDetail.getData().getNum());
                    binding.tvDeviceCount.tvContent.setText(String.valueOf(lampCommonDetail.getData().getModel()));
                    binding.tvSiteName.tvContent.setText(String.valueOf(lampCommonDetail.getData().getLocation()));
                    binding.tvDeviceProduct.tvContent.setText(String.valueOf(lampCommonDetail.getData().getManufacturer()));
                    binding.deviceCoordinate.longitudeContent.setText(String.valueOf(lampCommonDetail.getData().getLongitude()));
                    binding.deviceCoordinate.latitudeContent.setText(String.valueOf(lampCommonDetail.getData().getLatitude()));

                    if(lampCommonDetail.getData() !=null && lampCommonDetail.getData().getLocation() != null && lampCommonDetail.getData().getLocation().toString() != null){
                        String str = lampCommonDetail.getData().getLocation().toString();
                        String[] strarray= str.split(",");
                        if(strarray.length>2){
                            binding.tvSiteName.tvContent.setText(strarray[2]);
                        }
                    }
                }
            }
        });
    }

    /**
     * 查询集控详情
     */
            private void queryControl() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_DLM_LOCATION_CONTROL + device_info.id, parameter, new BeanResponseListener<LampControlDetail>() {
            @Override
            public void onResponse(LampControlDetail lampCommonDetail, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && lampCommonDetail != null) {

                    binding.tvDeviceName.tvContent.setText(lampCommonDetail.getData().getName());
                    binding.tvDeviceNum.tvContent.setText(lampCommonDetail.getData().getNum());
                    if(lampCommonDetail.getData().getDlmRespDistributeCabinetVO() != null) {
                        binding.deviceCoordinate.longitudeContent.setText(String.valueOf(lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLongitude()));
                        binding.deviceCoordinate.latitudeContent.setText(String.valueOf(lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLatitude()));
                        binding.tvSiteName.tvContent.setText(String.valueOf(lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLocation()));

                        if(lampCommonDetail.getData() != null && lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLocation() != null && lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLocation().toString() != null){
                            String str = lampCommonDetail.getData().getDlmRespDistributeCabinetVO().getLocation().toString();
                            String[] strarray= str.split(",");
                            if(strarray.length>2){
                                binding.tvSiteName.tvContent.setText(strarray[2]);
                            }
                        }
                    }
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
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success(workAreaListData.getMessage());
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_DENGJU_SUCCESS);
                    EventBus.getDefault().post(eventMessage);

                    popToBack();
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
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success(workAreaListData.getMessage());
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_PEIDIANGUI_SUCCESS);
                    EventBus.getDefault().post(eventMessage);

                    popToBack();
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
                if(error == null) {
                    mMessageLoader.dismiss();
                    XToastUtils.success(workAreaListData.getMessage());
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ADD_JIKONG_SUCCESS);
                    EventBus.getDefault().post(eventMessage);

                    popToBack();
                }
            }
        });
    }
}
