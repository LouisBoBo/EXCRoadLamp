package com.exc.roadlamp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.adapter.base.MyFragmentPagerAdapter;
import com.exc.roadlamp.base.MyXPageActivity;
import com.exc.roadlamp.bean.CheckModel;
import com.exc.roadlamp.databinding.ActivityHomeBinding;
import com.exc.roadlamp.device.HomeDeviceFragment;
import com.exc.roadlamp.device.bean.LoopItemSwitchBean;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.HomeFragment;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.my.MyFragment;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.utils.sdkinit.XUpdateInit;
import com.exc.roadlamp.work.WorkFragment;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xutil.common.ClickUtils;

import org.greenrobot.eventbus.EventBus;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends MyXPageActivity implements ClickUtils.OnClick2ExitListener {
    private MainActivity instance;
    private ActivityHomeBinding binding;
    private Map<String, View> mPageMap = new HashMap<>();

    public static int sStatusbarHeight;

    public final String[] mTitles = {"首页", "地图", "工作", "我的"};
    public final int[] mSelectIcons = {R.drawable.test_home_tab_img_check, R.drawable.test_map_tab_img_check, R.drawable.test_work_tab_img_check, R.drawable.test_my_tab_img_check};
    public final int[] mNormalIcons = {R.drawable.test_home_tab_img, R.drawable.test_map_tab_img, R.drawable.test_work_tab_img, R.drawable.test_my_tab_img};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
//        StatusBarUtils.translucent(this);
        sStatusbarHeight = StatusBarUtils.getStatusBarHeight(this);
        instance = this;
        initView();
    }

    @Override
    public void onBackPressed() {
        ClickUtils.exitBy2Click(2000, this);
    }

    private void initView() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeDeviceFragment());
        fragments.add(new WorkFragment());
        fragments.add(new MyFragment());


        binding.tabbar.setTitles(mTitles);
        binding.tabbar.setNormalIcons(mNormalIcons);
        binding.tabbar.setSelectedIcons(mSelectIcons);
        //页面可以滑动
        binding.tabbar.setGradientEnable(true);
        binding.tabbar.setPageAnimateEnable(true);
        binding.tabbar.generate();

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        binding.viewPager.setOffscreenPageLimit(fragments.size());
        binding.viewPager.setAdapter(adapter);
        binding.tabbar.setContainer(binding.viewPager);
        binding.tabbar.setTabListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int index) {
                if(index == 0){
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.SELECT_TAB_CLICK);
                    EventBus.getDefault().post(eventMessage);
                }
            }

            @Override
            public boolean onInterruptSelect(int index) {
                return false;
            }
        });


    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");

    }

    @Override
    public void onExit() {
        moveTaskToBack(true);
    }

}
