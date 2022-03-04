package com.exc.roadlamp.work.control;

import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentControlRelationBinding;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampControlDetail;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.fragment.DeviceLampAddFragment;
import com.exc.roadlamp.device.fragment.LampLightDetailLightListFragment;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.work.adapter.ControlAdapter;
import com.exc.roadlamp.work.adapter.RelationLightAdapter;
import com.exc.roadlamp.work.model.RelationLightModel;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "关联灯具")
public class ControlRelationLight extends MyBaseFragment implements View.OnClickListener{
    private FragmentControlRelationBinding binding;
    private RelationLightAdapter adapter;
    private List<RelationLightModel.DataBean.SystemDeviceListBean> deviceListBeans = new ArrayList<>();

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.TextAction("去关联") {
            @Override
            public void performAction(View view) {
                showCodePopview();
            }
        });
        return titleBar;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.CONTROL_BINDING_SUCCESS) {

            queryControl();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RelationLightAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);

        queryControl();
    }

    public void initData(){

    }

    /**
     * 查询集控关联的灯具
     */
    private void queryControl() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_DLM_SL_LAMP_POST_GETPLCBYLOCATIONCONTROID + device_info.id, parameter, new BeanResponseListener<RelationLightModel>() {
            @Override
            public void onResponse(RelationLightModel relationLightModel, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && relationLightModel != null) {
                    deviceListBeans.clear();
                    for (RelationLightModel.DataBean datum : relationLightModel.getData()) {
                        for (RelationLightModel.DataBean.SystemDeviceListBean systemDeviceListBean : datum.getSystemDeviceList()) {
                            if(systemDeviceListBean.getChosen() == 1 && systemDeviceListBean.getChosenByOther() !=1){
                                deviceListBeans.add(systemDeviceListBean);
                            }
                        }
                    }
                }
                binding.tvTips.setVisibility(deviceListBeans.size()>0?View.GONE:View.VISIBLE);
                adapter.setmDatas(deviceListBeans);
            }
        });
    }

    private void showCodePopview(){
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_qrcode, null);
        contentView.findViewById(R.id.qcode_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//扫码
                bottomDialog.dismiss();

                DeviceInfoBean lampinfoBean = new DeviceInfoBean();
                lampinfoBean.device_type = LampLightDetailLightListFragment.deviceType;
                lampinfoBean.isqrcode = true;
                lampinfoBean.isbinding = true;
                lampinfoBean.control_id = device_info.id;
                openNewPage(DeviceLampAddFragment.class,"device_info",lampinfoBean);
            }
        });
        contentView.findViewById(R.id.shoudong_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//手动
                bottomDialog.dismiss();

                openNewPage(LightBindingFragment.class,"device_info",device_info);
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
    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentControlRelationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
