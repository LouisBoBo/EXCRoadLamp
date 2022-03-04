package com.exc.roadlamp.device.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.LampDeviceBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.customview.MyPSwitchView;

import java.util.ArrayList;
import java.util.List;

public class LampPoleControlAdapter extends RecyclerView.Adapter<LampPoleControlAdapter.VH> {
    private LampDeviceBean.DataBean recordsBean;
    private List<LampChildrenListBean> childrenListBeans = new ArrayList<>();

    public LampPoleControlAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(LampPoleControlAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public LampPoleControlAdapter.OnImageClick monImageClick;
    public interface OnImageClick {
        void imgclick(int index);
    }
    public void setOnImageClick(LampPoleControlAdapter.OnImageClick onImageClick) {
        monImageClick = onImageClick;
    }

    public LampPoleControlAdapter.OnSeekbarClick monSeekbarClick;
    public interface OnSeekbarClick {
        void seekbarclick(int index , int progress);
    }
    public void setOnSeekbarClick(LampPoleControlAdapter.OnSeekbarClick onSeekbarClick) {
        monSeekbarClick = onSeekbarClick;
    }


    public void setmDatas(LampDeviceBean.DataBean bean) {
        recordsBean = bean;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LampPoleControlAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_control, parent, false);
        return new LampPoleControlAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LampPoleControlAdapter.VH holder, int position) {
        LampDeviceBean.DataBean.ChildrenListBean childrenListBean = recordsBean.getChildrenList().get(position);

        holder.device_name.setText(childrenListBean.getName());
        holder.device_num.setText(childrenListBean.getNum());
        holder.select_mark.setVisibility(childrenListBean.isShow()?View.VISIBLE:View.INVISIBLE);
        holder.switchView.setChecked(childrenListBean.getDlmRespDevice().getDeviceState()==1?true:false);
        holder.seekBar.setProgress(childrenListBean.getDlmRespDevice().getBrightness());
        holder.progress.setText(childrenListBean.getDlmRespDevice().getBrightness()+"%");

        holder.switchView.mSlidable = true;
        holder.switchView.isclick = true;

        if (childrenListBean.isSelect()) {
            holder.select_mark.setImageResource(R.mipmap.video_select);
        } else {
            holder.select_mark.setImageResource(R.mipmap.video_normal);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(position);
            }
        });

        holder.select_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monImageClick.imgclick(position);
            }
        });

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.progress.setText(progress + "%");
                childrenListBean.setProgress(progress);
                childrenListBean.getDlmRespDevice().setBrightness(progress);
                recordsBean.getChildrenList().get(position).setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.switchView.setOnSwitchCheckListener(new MyPSwitchView.SwitchCheckListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                childrenListBean.setChecked(isChecked);
                childrenListBean.getDlmRespDevice().setDeviceState(isChecked?1:0);
                recordsBean.getChildrenList().get(position).setChecked(isChecked);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(recordsBean !=null){
            return recordsBean.getChildrenList().size();
        }else return 0;
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public TextView device_name;
        public TextView device_num;
        public ImageView select_mark;
        public SeekBar seekBar;
        public TextView progress;
        public MyPSwitchView switchView;

        public VH(View v) {
            super(v);
            device_name = v.findViewById(R.id.device_name);
            device_num = v.findViewById(R.id.device_num);
            select_mark = v.findViewById(R.id.img_mark);
            seekBar = v.findViewById(R.id.sk);
            progress = v.findViewById(R.id.progress);
            switchView = v.findViewById(R.id.status_switch);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}