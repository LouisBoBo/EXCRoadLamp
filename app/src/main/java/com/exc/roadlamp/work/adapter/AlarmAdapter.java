package com.exc.roadlamp.work.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.home.model.AreaModel;
import com.exc.roadlamp.work.model.AlarmNewsModel;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.VH> {
    private List<AlarmNewsModel.DataBean.RecordsBean> areaModelList;


    public AlarmAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(AlarmAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<AlarmNewsModel.DataBean.RecordsBean> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmAdapter.VH(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.VH holder, int position) {
        holder.context.setText(areaModelList.get(position).getContent());
        holder.type.setText(areaModelList.get(position).getServiceName());
        holder.text.setText(areaModelList.get(position).getCreateTime());

        //havered 1已读 0未读
        if(areaModelList.get(position).getHaveRead() == 0){
            holder.baseview.setBackgroundResource(R.drawable.background_btn_select);
            holder.pointImg.setVisibility(View.VISIBLE);
        }else {
            holder.baseview.setBackgroundResource(R.drawable.background_btn_normal);
            holder.pointImg.setVisibility(View.GONE);
            holder.context.setTextColor(R.color.pop_item_bg_pressed);
        }

        if(areaModelList.get(position).getServiceName().equals("故障告警")){
            holder.headImg.setImageResource(R.mipmap.my_msg_icon_warn_n);
        }else {
            holder.headImg.setImageResource(R.mipmap.my_msg_icon_help_n);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {

                mOnItemClick.click(position);

            }
        });
    }


    @Override
    public int getItemCount() {
        if(areaModelList == null || areaModelList.size() ==0){
            return 0;
        }else
            return areaModelList.size();
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public View baseview;
        public ImageView headImg;
        public ImageView pointImg;
        public TextView context;
        public TextView text;
        public TextView type;

        public VH(View v) {
            super(v);
            baseview = v.findViewById(R.id.baseview);
            headImg = v.findViewById(R.id.head_img);
            pointImg = v.findViewById(R.id.point_img);
            context = v.findViewById(R.id.tv_content);
            text = v.findViewById(R.id.tv_time);
            type = v.findViewById(R.id.tv_text);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}