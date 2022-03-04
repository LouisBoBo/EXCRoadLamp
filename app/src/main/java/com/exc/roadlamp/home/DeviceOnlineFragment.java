package com.exc.roadlamp.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.databinding.FragmentAreaBinding;
import com.exc.roadlamp.databinding.FragmentDeviceOnlineBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.EnergyMonthModel;
import com.exc.roadlamp.home.model.NumberCountModel;
import com.exc.roadlamp.home.model.SolarModel;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DeviceOnlineFragment extends Fragment {
    private View mainView;
    private HorizontalProgressView light_pre;
    private FragmentDeviceOnlineBinding binding;

    private NumberCountModel.DataBean dataBean;
    private EnergyMonthModel.DataBean energydataBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDeviceOnlineBinding.inflate(inflater,container,false);
        EventBus.getDefault().register(this);
        initview();
        initData();

        return binding.getRoot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.SELECT_TAB_CLICK) {
            initData();
            return;
        }
    }

    public void initview(){
        binding.lightPre.deviceImage.setImageResource(R.mipmap.home_icon_lamp);
        binding.cameraPre.deviceImage.setImageResource(R.mipmap.home_icon_camera);
        binding.screenPre.deviceImage.setImageResource(R.mipmap.home_icon_screen);
        binding.radioPre.deviceImage.setImageResource(R.mipmap.home_icon_broadcast);
        binding.sensorPre.deviceImage.setImageResource(R.mipmap.home_icon_sensor);
        binding.APPre.deviceImage.setImageResource(R.mipmap.home_icon_aapp);

        binding.lightPre.deviceName.setText("灯控");
        binding.cameraPre.deviceName.setText("摄像头");
        binding.screenPre.deviceName.setText("屏幕");
        binding.radioPre.deviceName.setText("广播");
        binding.sensorPre.deviceName.setText("传感器");
        binding.APPre.deviceName.setText("AP");

        binding.lightPre.hpvMath.startProgressAnimation();
        binding.cameraPre.hpvMath.startProgressAnimation();
        binding.screenPre.hpvMath.startProgressAnimation();
        binding.radioPre.hpvMath.startProgressAnimation();
        binding.sensorPre.hpvMath.startProgressAnimation();
        binding.APPre.hpvMath.startProgressAnimation();
    }

    public void initData(){
        Parameter parameter = new Parameter();
        parameter.put("type", 0);
        HttpRequest.GET(getActivity(), HttpApi.POST_NUMBER_COUNT, parameter, new BeanResponseListener<NumberCountModel>() {
            @Override
            public void onResponse(NumberCountModel numberCountModel, Exception error) {
                if(numberCountModel.getCode() == 200){
                    dataBean = numberCountModel.getData();
                    refreshUI();
                }

            }
        });
    }

    //刷新界面
    private void refreshUI(){
        for (NumberCountModel.DataBean.DeviceBean deviceBean : dataBean.getDevice()) {
            int deviceType = deviceBean.getDeviceType();
            switch (deviceType){
                case 1:
                    binding.lightPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.lightPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.lightPre.hpvMath,deviceBean.getOnlineRate());
                    break;
                case 2:
                    binding.cameraPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.cameraPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.cameraPre.hpvMath,deviceBean.getOnlineRate());
                    break;
                case 3:
                    binding.screenPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.screenPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.screenPre.hpvMath,deviceBean.getOnlineRate());
                    break;
                case 4:
                    binding.radioPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.radioPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.radioPre.hpvMath,deviceBean.getOnlineRate());
                    break;
                case 5:
                    binding.sensorPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.sensorPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.sensorPre.hpvMath,deviceBean.getOnlineRate());
                    break;
                case 6:
                    binding.APPre.deviceNum.setText(String.valueOf(deviceBean.getOnlineNumber()));
                    binding.APPre.deviceScale.setText(String.valueOf(deviceBean.getOnlineRate())+"%");
                    handlehpv(binding.APPre.hpvMath,deviceBean.getOnlineRate());
                    break;

                default:
                    break;
            }
        }
    }

    //设置进度条
    public void handlehpv(HorizontalProgressView view , int onlineRate){
        view.setProgressTextMoved(true);
        view.setStartProgress(0);
        view.setEndProgress(onlineRate);
        view.setStartColor(Color.parseColor("#FF8F5D"));
        view.setEndColor(Color.parseColor("#F54EA2"));
        view.setProgressCornerRadius(10);
        view.setProgressDuration(2000);
        view.setTrackEnabled(true);
        view.startProgressAnimation();
    }
}
