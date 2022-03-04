package com.exc.roadlamp.device;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.LazyFragment;
import com.exc.roadlamp.device.fragment.DeviceListFragment;
import com.exc.roadlamp.device.fragment.DeviceMapFragment;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.utils.XToastUtils;
import com.xuexiang.xui.widget.tabbar.TabControlView;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 首页-设备
 */
public class HomeDeviceFragment extends LazyFragment {
    private FragmentActivity mActivity;
    /**
     * 设备地图fragment
     */
    private DeviceMapFragment deviceMapFragment;
    /**
     * 设备列表fragment
     */
    private DeviceListFragment deviceListFragment;

    private TabControlView tabControlView;

    private FragmentTransaction tabChangeBeginTransaction;

    private ImageView testimg;
    private boolean ismove ;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home_device;
    }

    @Override
    protected void initView(View rootView) {
        mActivity = getActivity();
        tabControlView = rootView.findViewById(R.id.tabControlView);
        EventBus.getDefault().register(this);

        ismove = false;
        testimg = rootView.findViewById(R.id.testimg);
        testimg.setImageResource(R.mipmap.icon_toleft);
        testimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("x移动多少",String.valueOf(tabControlView.getTranslationY()));
                testimg.setImageResource(ismove?R.mipmap.icon_toleft:R.mipmap.icon_toright);
                testimg.offsetLeftAndRight(ismove?300:-300);
                tabControlView.offsetLeftAndRight(ismove?300:-300);
                ismove = !ismove;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) throws Exception {
        if(eventMessage.getEventEnum() == EventEnum.SHOW_DEVICE_MAP_FROMLIST){

            tabChangeBeginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            if (deviceMapFragment != null) {
                tabChangeBeginTransaction.hide(deviceMapFragment);
            }
            if (deviceListFragment != null) {
                tabChangeBeginTransaction.hide(deviceListFragment);
            }

            tabChangeBeginTransaction.show(deviceMapFragment);
            tabChangeBeginTransaction.commitAllowingStateLoss();
            tabControlView.setDefaultSelection(0);
        }
//        else if(eventMessage.getEventEnum() == EventEnum.SHOW_DEVICE_MAP_PAGE_SWITCH_BTN){
//            tabControlView.setVisibility(View.VISIBLE);
//        }else if(eventMessage.getEventEnum() == EventEnum.HIDE_DEVICE_MAP_PAGE_SWITCH_BTN){
//            tabControlView.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        //默认显示地图
        try {
            tabControlView.setDefaultSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction beginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        deviceMapFragment = new DeviceMapFragment();
        beginTransaction.add(R.id.fragment_layout, deviceMapFragment);
        beginTransaction.commit();

        tabControlView.setOnTabSelectionChangedListener((title, value) -> {
            tabChangeBeginTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            if (deviceMapFragment != null) {
                tabChangeBeginTransaction.hide(deviceMapFragment);
            }
            if (deviceListFragment != null) {
                tabChangeBeginTransaction.hide(deviceListFragment);
            }
            if (value.equals("地图")) {
                if (deviceMapFragment == null) {
                    deviceMapFragment = new DeviceMapFragment();
                    tabChangeBeginTransaction.add(R.id.fragment_layout, deviceMapFragment);
                } else {
                    tabChangeBeginTransaction.show(deviceMapFragment);
                }
                tabControlView.setVisibility(View.VISIBLE);

            }
            if (value.equals("列表")) {
                if (deviceListFragment == null) {
                    deviceListFragment = new DeviceListFragment();
                    tabChangeBeginTransaction.add(R.id.fragment_layout, deviceListFragment);
                } else {
                    tabChangeBeginTransaction.show(deviceListFragment);
                }
                tabControlView.setVisibility(View.VISIBLE);

            }
            tabChangeBeginTransaction.commitAllowingStateLoss();

        });

        tabControlView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                XToastUtils.info("点击了");
                return false;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
