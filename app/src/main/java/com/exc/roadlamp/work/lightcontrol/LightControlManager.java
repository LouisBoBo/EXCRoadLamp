package com.exc.roadlamp.work.lightcontrol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLightControlManagerBinding;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.model.LightDetailModel;
import com.exc.roadlamp.work.model.LightStrategyModel;
import com.exc.roadlamp.work.workorder.CreateWorkOrderFragment;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.xuexiang.xutil.data.SPUtils.getSharedPreferences;

@Page(name = "控灯")
public class LightControlManager extends MyBaseFragment implements View.OnClickListener{
    private FragmentLightControlManagerBinding binding;

    @AutoWired(name = "device_info")
    LightInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.TextAction("刷新") {
            @Override
            public void performAction(View view) {
                initDta();
            }
        });
        return titleBar;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews() {

        if (device_info.devicestate == 0) { //关闭状态
            binding.lightStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
            binding.sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
        } else {//开启状态
            binding.lightStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
            binding.sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
        }

        binding.tvTime.setText("刷新时间：" + device_info.time);
        binding.progress.setText(device_info.progress +"%");
        binding.sk.setProgress(device_info.progress);
        binding.sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 20) {
                    seekBar.setProgress(20);
                    progress = 20;
                }
                binding.progress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    control(false);
                }catch (Exception e){
                    e.printStackTrace();
                };
            }
        });

        binding.lightStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(Activity.MODE_PRIVATE));
                    control(true);
                }catch (Exception e){
                    e.printStackTrace();
                };
            }
        });

        binding.vVoltage.image.setImageResource(R.mipmap.wind2x);
        binding.vElectric.image.setImageResource(R.mipmap.wet2x);
        binding.vPower.image.setImageResource(R.mipmap.pa2x);
        binding.vEnergy.image.setImageResource(R.mipmap.db2x);
        binding.vTemperature.image.setImageResource(R.mipmap.wendu2x);
        binding.vTime.image.setImageResource(R.mipmap.time_icon);

        initDta();
    }

    //控制灯具全开、全关
    public void control(boolean isswitch) throws JSONException {
        mMessageLoader.show();
        org.json.JSONArray idlist = new JSONArray();
        org.json.JSONArray typelist = new JSONArray();
        org.json.JSONArray lightnesses = new JSONArray();

        idlist.put(device_info.id);
        lightnesses.put(binding.sk.getProgress());
        if(isswitch){
            typelist.put(device_info.devicestate==1?"0":"1"); //0关 1开
        }else {
            typelist.put(String.valueOf(device_info.devicestate));
        }

        JSONObject body = new JSONObject();
        body.put("lampDeviceIdList",idlist);
        body.put("lightnesses", lightnesses);
        body.put("types", typelist);
        HttpRequest.JSONPOST(mActivity, HttpApi.DEVICE_CONTROL, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success("下发成功");

                    if(isswitch){
                        if (device_info.devicestate == 1) { //关闭状态
                            binding.lightStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
                            binding.sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
                            device_info.devicestate = 0;
                        } else {//开启状态
                            binding.lightStatus.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
                            binding.sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
                            device_info.devicestate = 1;
                        }
                    }

                    device_info.progress = binding.sk.getProgress();
                    EventBus.getDefault().post(device_info);
                }
            }
        });
    }

    public void initDta(){
        //API_SL_LAMP_DEVICE_DETAIL
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(getActivity(), HttpApi.API_SL_LAMP_DEVICE_DETAIL+device_info.id, parameter, new BeanResponseListener<LightDetailModel>() {
            @Override
            public void onResponse(LightDetailModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    String[] strs = new String[]{"电压","电流","功率","能耗","温度","时长"};
                    for (String str : strs) {
                        for (LightDetailModel.DataBean datum : bean.getData()) {
                            if(str.contains(datum.getName()) && str.contains("电压")){
                                binding.vVoltage.title.setText("电压");
                                binding.vVoltage.title1.setText(datum.getParameterValue());
                                binding.vVoltage.title2.setText("V");
                                break;
                            }else if(str.contains(datum.getName()) && str.contains("电流")){
                                binding.vElectric.title.setText("电流");
                                binding.vElectric.title1.setText(datum.getParameterValue().toString());
                                binding.vElectric.title2.setText("A");
                                break;
                            }else if(str.contains(datum.getName()) && str.contains("功率")){
                                binding.vPower.title.setText("功率");
                                binding.vPower.title1.setText(datum.getParameterValue().toString());
                                binding.vPower.title2.setText("W");
                                break;
                            }else if(str.contains(datum.getName()) && str.contains("能耗")){
                                binding.vEnergy.title.setText("能耗");
                                binding.vEnergy.title1.setText(datum.getParameterValue().toString());
                                binding.vEnergy.title2.setText("kwh");
                                break;
                            }else if(str.contains(datum.getName()) && str.contains("温度")){
                                binding.vTemperature.title.setText("温度");
                                binding.vTemperature.title1.setText(datum.getParameterValue().toString());
                                binding.vTemperature.title2.setText("℃");
                                break;
                            }else if(str.contains(datum.getName()) && str.contains("时长")){
                                binding.vTime.title.setText("时长");
                                binding.vTime.title1.setText(datum.getParameterValue().toString());
                                binding.vTime.title2.setText("h");
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLightControlManagerBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
