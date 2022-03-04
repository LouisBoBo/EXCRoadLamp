package com.exc.roadlamp.device.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampListBinding;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import static android.app.Activity.RESULT_OK;

@Page(name = "设备列表")
public class DeviceControlFragment extends MyBaseFragment implements View.OnClickListener{
    private FragmentLampListBinding binding;
    private TitleBar.ImageAction brushimageAction;
    private TitleBar titleBar;

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        titleBar = super.initTitle();

        creatTitleBarAction();

        return titleBar;
    }

    //创建导航条
    public void creatTitleBarAction(){
        brushimageAction = new TitleBar.ImageAction(R.mipmap.icon_qrcodenew) {
            @Override
            public void performAction(View view) {
                XQRCode.startScan(getActivity(),REQUEST_CODE);//二维码扫描
                ToastUtils.toast("扫描");
            }
        };
        titleBar.addAction(brushimageAction);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLampListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
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
                    JSONObject jsonObj = JSONObject.parseObject(result);

                    DeviceInfoBean infoBean = new DeviceInfoBean();
                    infoBean.device_type = LampLightDetailLightListFragment.deviceType;
                    infoBean.Manufacturer = jsonObj.getString("制造商");
                    infoBean.num = jsonObj.getString("IMEI");
                    infoBean.model = jsonObj.getString("设备类型");

                    openNewPage(DeviceLampAddFragment.class,"device_info",infoBean);

                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    XToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
                }
            }
        }


    }
}
