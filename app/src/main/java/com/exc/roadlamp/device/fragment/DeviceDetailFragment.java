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
            XToastUtils.toast("????????????:" + "20218888", Toast.LENGTH_LONG);
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

        binding.tvDeviceName.tvTitle.setText("????????????:");
        binding.tvDeviceNum.tvTitle.setText("????????????:");
        binding.tvDeviceCount.tvTitle.setText("????????????:");
        binding.tvSiteName.tvTitle.setText("????????????:");
        binding.tvDeviceProduct.tvTitle.setText("????????????:");

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
     * ???????????????
     */
    private void setBanner() {
        //????????????????????????
        list_path = new ArrayList<>();
        //??????????????????
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/2a919def19fc47e3aa0d75d8c227ab1b.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d027d1efc0564c44bb979ba0bd21f560.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/bbb930d66e5a48baa8d3c143544d7631.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/fb1721b8c9be4da9949fcdd26fc902a2.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/08b58dde9b284638b44e2d03c4cb9acf.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d3caeb6129ee43df87f5c1e1058d96fc.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/9fd01c4add07473db31ba850f20a7232.jpg");
        list_path.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");

        banner = binding.banner;

        //???????????????????????????????????????????????????????????????????????????
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //????????????????????????????????????????????????
        banner.setImageLoader(new ImgLoader());
        //????????????????????????????????????
        banner.setImages(list_path);
        //?????????????????????????????????????????????????????????????????????????????????????????????
        banner.setBannerAnimation(Transformer.Default);
        //????????????????????????
        banner.setDelayTime(3000);
        //????????????????????????????????????????????????
        banner.isAutoPlay(true);
        //???????????????????????????????????????????????????
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //???????????????????????????????????????????????????????????????????????????????????????????????????
                .setOnBannerListener(this::OnBannerClick)
                //????????????????????????????????????????????????
                .start();
    }

    //????????????????????????
    @Override
    public void OnBannerClick(int position) {
        openNewPage(DeviceBigImageFragment.class,"phonto_index",position);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.tvScanmap){//????????????
            popToBack();

            //?????????????????????
            EventMessage eventMessage = new EventMessage();
            eventMessage.setEventEnum(EventEnum.SHOW_DEVICE_MAP_FROMLIST);
            EventBus.getDefault().post(eventMessage);

            //????????????????????????????????????
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
        }else if(v == binding.tvDeleate){//??????
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

    //???????????????????????????
    private class ImgLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    /**
     * ??????????????????
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
     * ??????????????????
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
     * ?????????????????????
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
     * ??????????????????
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
     * ??????????????????
     */
    private void deleateSlDevices(List<String> ids) {
        mMessageLoader.show();

        //???List???????????????????????????&??????
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
     * ?????????????????????
     */
    private void deleateCabinets(List<String> ids) {
        mMessageLoader.show();

        //???List???????????????????????????&??????
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
     * ??????????????????
     */
    private void deleateControls(List<String> ids) {
        mMessageLoader.show();

        //???List???????????????????????????&??????
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
