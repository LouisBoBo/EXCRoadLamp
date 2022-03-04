package com.exc.roadlamp.work.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.work.model.RelationLightModel;

import java.util.ArrayList;
import java.util.List;

public class LightBindingAdapter extends RecyclerView.Adapter<LightBindingAdapter.VH> {
//    private List<MapLampCommonDevList.DataBean.ChildrenListBean> areaModelList;
    private List<RelationLightModel.DataBean.SystemDeviceListBean> areaModelList = new ArrayList<>();
    public LightBindingAdapter.OnItemClick mOnItemClick;

    public interface OnItemClick {
        void click(int index);
    }

    public void setOnItemClick(LightBindingAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public LightBindingAdapter.OnImageClick monImageClick;

    public interface OnImageClick {
        void imgclick(int index);
    }

    public void setOnImageClick(LightBindingAdapter.OnImageClick onImageClick) {
        monImageClick = onImageClick;
    }

    public void setmDatas(List<RelationLightModel.DataBean.SystemDeviceListBean> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LightBindingAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_select, parent, false);
        return new LightBindingAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LightBindingAdapter.VH holder, int position) {

        holder.context.setText(areaModelList.get(position).getSystemDeviceName());
        holder.text.setVisibility(View.GONE);

        if (areaModelList.get(position).isSelect()) {
            holder.img_select.setImageResource(R.mipmap.gouxuan_select);
        } else {
            holder.img_select.setImageResource(R.mipmap.gouxuan_normal);
        }


        holder.img_more.setVisibility(View.GONE);
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
        if(areaModelList !=null){
            return areaModelList.size();
        }else return 0;
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public ImageView img_select;
        public TextView context;
        public TextView text;
        public ImageView img_more;

        public VH(View v) {
            super(v);
            img_select = v.findViewById(R.id.img_select);
            context = v.findViewById(R.id.tv_content);
            text = v.findViewById(R.id.tv_text);
            img_more = v.findViewById(R.id.btn_go);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}
