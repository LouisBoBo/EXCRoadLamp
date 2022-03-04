package com.exc.roadlamp.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.ActivityAreaSelectBinding;
import com.exc.roadlamp.home.adapter.AreaSelectAdapter;
import com.exc.roadlamp.home.adapter.TabFragmentPagerAdapter;
import com.exc.roadlamp.home.model.AreaModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.vertical.TabFragmentManager;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

@Page(name = "创建地址")
public class AreaSelectActivity extends MyBaseFragment implements View.OnClickListener {
    private ActivityAreaSelectBinding binding;
    private AreaSelectAdapter adapter;
    private List<AreaModel> areaModelList;
    private List<Fragment> list;
    private TabFragmentPagerAdapter tabadapter;
    @Override
    protected void initViews() {

        binding.btnArea.setSelected(true);

        binding.btnArea.setOnClickListener(this::onClick);
        binding.btnGroup.setOnClickListener(this::onClick);
        binding.btnControl.setOnClickListener(this::onClick);

        list = new ArrayList<>();
        list.add(new AreaFragment());
        list.add(new GroupFragment());
        list.add(new ControlFragment());

        tabadapter = new TabFragmentPagerAdapter(mActivity.getSupportFragmentManager(),list);
        binding.viewpager.setAdapter(tabadapter);
        binding.viewpager.setCurrentItem(0);
        binding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    handleAreaBtn();
                }else if(position ==1){
                    handleGroupBtn();
                }else if(position == 2){
                    handleControlBtn();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = ActivityAreaSelectBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }



    @Override
    public void onClick(View v) {
        if(v == binding.btnArea){
            binding.viewpager.setCurrentItem(0);

            handleAreaBtn();
        }else if(v == binding.btnGroup){
            binding.viewpager.setCurrentItem(1);

            handleGroupBtn();
        }else if(v == binding.btnControl){
            binding.viewpager.setCurrentItem(2);

            handleControlBtn();
        }
    }

    public void handleAreaBtn(){
        binding.btnArea.setSelected(true);
        binding.btnGroup.setSelected(false);
        binding.btnControl.setSelected(false);

        binding.btnArea.setBackgroundResource(R.drawable.background_btn_left_select);
        binding.btnGroup.setBackgroundResource(R.drawable.background_btn_middle);
        binding.btnControl.setBackgroundResource(R.drawable.background_btn_right);

        binding.btnArea.setTextColor(Color.WHITE);
        binding.btnGroup.setTextColor(Color.GRAY);
        binding.btnControl.setTextColor(Color.GRAY);
    }

    public void handleGroupBtn(){
        binding.btnArea.setSelected(false);
        binding.btnGroup.setSelected(true);
        binding.btnControl.setSelected(false);

        binding.btnArea.setBackgroundResource(R.drawable.background_btn_left);
        binding.btnGroup.setBackgroundResource(R.drawable.background_btn_middle_select);
        binding.btnControl.setBackgroundResource(R.drawable.background_btn_right);

        binding.btnArea.setTextColor(Color.GRAY);
        binding.btnGroup.setTextColor(Color.WHITE);
        binding.btnControl.setTextColor(Color.GRAY);
    }

    public void handleControlBtn(){
        binding.btnArea.setSelected(false);
        binding.btnGroup.setSelected(false);
        binding.btnControl.setSelected(true);

        binding.btnArea.setBackgroundResource(R.drawable.background_btn_left);
        binding.btnGroup.setBackgroundResource(R.drawable.background_btn_middle);
        binding.btnControl.setBackgroundResource(R.drawable.background_btn_right_select);

        binding.btnArea.setTextColor(Color.GRAY);
        binding.btnGroup.setTextColor(Color.GRAY);
        binding.btnControl.setTextColor(Color.WHITE);
    }
}
