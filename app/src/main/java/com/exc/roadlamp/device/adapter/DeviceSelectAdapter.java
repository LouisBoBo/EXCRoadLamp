package com.exc.roadlamp.device.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;
import com.exc.roadlamp.home.model.AreaModel;

import java.util.List;

public class DeviceSelectAdapter extends RecyclerView.Adapter<DeviceSelectAdapter.VH> {
    private int deviceType;//0灯杆 1灯具 2配电柜 3集控
    private List<LampDeviceListBean.DataBean.RecordsBean> areaModelList;
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> childrenListBeanList;
    private List<PowerCabinetList.DataBean.RecordsBean> recordsBeanList;
    private List<CenterControlListData.DataBean.RecordsBean> recordsControlBeanList;


    public DeviceSelectAdapter.OnItemClick mOnItemClick;

    public interface OnItemClick {
        void click(int index);
    }

    public void setOnItemClick(DeviceSelectAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public DeviceSelectAdapter.OnImageClick monImageClick;

    public interface OnImageClick {
        void imgclick(int index);
    }

    public void setOnImageClick(DeviceSelectAdapter.OnImageClick onImageClick) {
        monImageClick = onImageClick;
    }

    public void setmDatas(List<LampDeviceListBean.DataBean.RecordsBean> modelList) {
        deviceType = 0;
        areaModelList = modelList;
        notifyDataSetChanged();
    }
    public void setmMaplampDatas(List<MapLampCommonDevList.DataBean.ChildrenListBean> modelList){
        deviceType = 1;
        childrenListBeanList = modelList;
        notifyDataSetChanged();
    }
    public void setmCabinewDatas(List<PowerCabinetList.DataBean.RecordsBean> modelList) {
        deviceType = 2;
        recordsBeanList = modelList;
        notifyDataSetChanged();
    }

    public void setmControlDatas(List<CenterControlListData.DataBean.RecordsBean> modelList) {
        deviceType = 3;
        recordsControlBeanList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceSelectAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_select, parent, false);
        return new DeviceSelectAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceSelectAdapter.VH holder, int position) {
        if(deviceType ==0){
            holder.context.setText(areaModelList.get(position).getName());
            holder.text.setText(areaModelList.get(position).getLampPostNum());

            if (areaModelList.get(position).isSelect()) {
                holder.img_select.setImageResource(R.mipmap.video_select);
            } else {
                holder.img_select.setImageResource(R.mipmap.video_normal);
            }
        }else if(deviceType == 1){
            holder.context.setText(childrenListBeanList.get(position).getName());
            holder.text.setText(childrenListBeanList.get(position).getNum());

            if (childrenListBeanList.get(position).is_select()) {
                holder.img_select.setImageResource(R.mipmap.video_select);
            } else {
                holder.img_select.setImageResource(R.mipmap.video_normal);
            }
        }else if(deviceType == 2){
            holder.context.setText(recordsBeanList.get(position).getName());
            holder.text.setText(recordsBeanList.get(position).getNum());

            if (recordsBeanList.get(position).is_select()) {
                holder.img_select.setImageResource(R.mipmap.video_select);
            } else {
                holder.img_select.setImageResource(R.mipmap.video_normal);
            }
        }else if(deviceType == 3){
            holder.context.setText(recordsControlBeanList.get(position).getName());
            holder.text.setText(recordsControlBeanList.get(position).getNum());

            if (recordsControlBeanList.get(position).is_select()) {
                holder.img_select.setImageResource(R.mipmap.video_select);
            } else {
                holder.img_select.setImageResource(R.mipmap.video_normal);
            }
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
        if(deviceType == 0){
            if(areaModelList !=null){
                return areaModelList.size();
            }else return 0;
        }else if(deviceType == 1){
            if(childrenListBeanList !=null){
                return childrenListBeanList.size();
            }else return 0;
        }else if(deviceType == 2){
            if(recordsBeanList !=null){
                return recordsBeanList.size();
            }else return 0;
        }else if(deviceType ==3){
            if(recordsControlBeanList !=null){
                return recordsControlBeanList.size();
            }else return 0;
        }else
            return 0;
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