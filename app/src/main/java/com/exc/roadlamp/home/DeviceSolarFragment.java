package com.exc.roadlamp.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.databinding.FragmentDeviceOnlineBinding;
import com.exc.roadlamp.databinding.FragmentDeviceSolarBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.EnergyMonthModel;
import com.exc.roadlamp.home.model.SolarModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DeviceSolarFragment extends Fragment {

    private SolarModel solarModel;
    private FragmentDeviceSolarBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeviceSolarBinding.inflate(inflater,container,false);
        EventBus.getDefault().register(this);
        initView();
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

    public void initView(){
        binding.baseElectricity.energyDayText1.setText("日发电量:");
        binding.baseElectricity.energyMonthText1.setText("月发电量:");
        binding.baseElectricity.energyDayText2.setText("0kWh");
        binding.baseElectricity.energyMonthText2.setText("0kWh");
        binding.baseElectricity.energyDayIcon.setImageResource(R.mipmap.icon_day_energy);
        binding.baseElectricity.energyMonthIcon.setImageResource(R.mipmap.icon_month_energy);
    }
    public void initData(){

        Parameter parameter1 = new Parameter();
        HttpRequest.POST(getActivity(), HttpApi.SL_LAMP_SOLARLAMP_APP_HISTORY, parameter1, new BeanResponseListener<SolarModel>() {
            @Override
            public void onResponse(SolarModel solarModel, Exception error) {
                if(solarModel.getCode() == 200){
                    binding.baseElectricity.energyDayText2.setText(solarModel.getData().getDayEnergyTotal()+"kWh");
                    binding.baseElectricity.energyMonthText2.setText(solarModel.getData().getMonthEnergyTotal()+"kWh");
                }
            }
        });
    }
}
