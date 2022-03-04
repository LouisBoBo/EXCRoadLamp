package com.exc.roadlamp.home.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.home.model.AreaModel;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

public class AreaSelectAdapter extends RecyclerView.Adapter<AreaSelectAdapter.VH> {
    private List<AreaModel> areaModelList;


    public OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public OnImageClick monImageClick;
    public interface OnImageClick{
        void imgclick(int index);
    }
    public void setOnImageClick(OnImageClick onImageClick){
        monImageClick = onImageClick;
    }

    public void setmDatas(List<AreaModel> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_select, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.context.setText(areaModelList.get(position).getContent());

        if (areaModelList.get(position).isIs_select()) {
            holder.img_select.setImageResource(R.mipmap.video_select);
        } else {
            holder.img_select.setImageResource(R.mipmap.video_normal);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {

                mOnItemClick.click(position);

            }
        });

        holder.img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monImageClick.imgclick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return areaModelList.size() > 0 ? areaModelList.size() : 0;
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public ImageView img_select;
        public TextView context;
        public TextView text;

        public VH(View v) {
            super(v);
            img_select = v.findViewById(R.id.img_select);
            context = v.findViewById(R.id.tv_content);
            text = v.findViewById(R.id.tv_text);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}