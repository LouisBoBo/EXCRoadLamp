package com.exc.roadlamp.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentSettingsBinding;
import com.exc.roadlamp.databinding.FragmentShouyeSettingBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "首页配置")
public class SettingFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentShouyeSettingBinding binding;
    @Override
    protected void initViews() {
        binding.onlineView.tvTitle.setText("设备在线数据（默认）");
        binding.funcView.tvTitle.setText("功能统计（默认）");
        binding.orderView.tvTitle.setText("工单概览");
        binding.alarmView.tvTitle.setText("告警统计");
        binding.lightView.tvTitle.setText("亮灯率");
        binding.solarView.tvTitle.setText("太阳能发电");

        binding.onlineView.imageMore.setOnClickListener(this::onClick);
        binding.funcView.imageMore.setOnClickListener(this::onClick);
        binding.orderView.imageMore.setOnClickListener(this::onClick);
        binding.alarmView.imageMore.setOnClickListener(this::onClick);
        binding.lightView.imageMore.setOnClickListener(this::onClick);
        binding.solarView.imageMore.setOnClickListener(this::onClick);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentShouyeSettingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
//        if(v == binding.onlineView.imageMore){
//            binding.onlineView.imageMore.setSelected(!binding.onlineView.imageMore.isSelected());
//            binding.onlineView.imageMore.setImageResource(binding.onlineView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }else if(v == binding.funcView.imageMore){
//            binding.funcView.imageMore.setSelected(!binding.funcView.imageMore.isSelected());
//            binding.funcView.imageMore.setImageResource(binding.funcView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }else if(v == binding.orderView.imageMore){
//            binding.orderView.imageMore.setSelected(!binding.orderView.imageMore.isSelected());
//            binding.orderView.imageMore.setImageResource(binding.orderView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }else if(v == binding.alarmView.imageMore){
//            binding.alarmView.imageMore.setSelected(!binding.alarmView.imageMore.isSelected());
//            binding.alarmView.imageMore.setImageResource(binding.alarmView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }else if(v == binding.lightView.imageMore){
//            binding.lightView.imageMore.setSelected(!binding.lightView.imageMore.isSelected());
//            binding.lightView.imageMore.setImageResource(binding.lightView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }else if(v == binding.solarView.imageMore){
//            binding.solarView.imageMore.setSelected(!binding.solarView.imageMore.isSelected());
//            binding.solarView.imageMore.setImageResource(binding.solarView.imageMore.isSelected()?R.mipmap.icon_switch_open:R.mipmap.icon_switch_close);
//        }
    }
}
