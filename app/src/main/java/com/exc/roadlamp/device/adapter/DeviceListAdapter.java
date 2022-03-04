package com.exc.roadlamp.device.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.VH> {
    private int deviceType;//0灯杆 1灯具 2配电柜 3集控
    private List<LampDeviceListBean.DataBean.RecordsBean> areaModelList;
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> childrenListBeanList;
    private List<PowerCabinetList.DataBean.RecordsBean> recordsBeanList;
    private List<CenterControlListData.DataBean.RecordsBean> recordsControlBeanList;

    private List<LampDeviceListBean.DataBean.RecordsBean> oldareaModelList;
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> oldchildrenListBeanList;
    private List<PowerCabinetList.DataBean.RecordsBean> oldrecordsBeanList;
    private List<CenterControlListData.DataBean.RecordsBean> oldrecordsControlBeanList;

    public DeviceListAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(DeviceListAdapter.OnItemClick onItemClick) {
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
        if(oldareaModelList == null){
            oldareaModelList = areaModelList;
        }
        notifyDataSetChanged();
    }

    public void setmMaplampDatas(List<MapLampCommonDevList.DataBean.ChildrenListBean> modelList){
        deviceType = 1;
        childrenListBeanList = modelList;
        if(oldchildrenListBeanList == null){
            oldchildrenListBeanList = childrenListBeanList;
        }
        notifyDataSetChanged();
    }
    public void setmCabinewDatas(List<PowerCabinetList.DataBean.RecordsBean> modelList) {
        deviceType = 2;
        recordsBeanList = modelList;
        if(oldrecordsBeanList == null){
            oldrecordsBeanList = recordsBeanList;
        }
        notifyDataSetChanged();
    }

    public void setmControlDatas(List<CenterControlListData.DataBean.RecordsBean> modelList) {
        deviceType = 3;
        recordsControlBeanList = modelList;
        if(oldrecordsControlBeanList == null){
            oldrecordsControlBeanList = recordsControlBeanList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceListAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceListAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceListAdapter.VH holder, int position) {
        if(deviceType ==0){
            holder.context.setText(areaModelList.get(position).getName());
            holder.text.setText(areaModelList.get(position).getLampPostNum());
        }else if(deviceType == 1){
            holder.context.setText(childrenListBeanList.get(position).getName());
            holder.text.setText(childrenListBeanList.get(position).getNum());
        }else if(deviceType == 2){
            holder.context.setText(recordsBeanList.get(position).getName());
            holder.text.setText(recordsBeanList.get(position).getNum());
        }else if(deviceType == 3){
            holder.context.setText(recordsControlBeanList.get(position).getName());
            holder.text.setText(recordsControlBeanList.get(position).getNum());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(position);
            }
        });

        holder.btn_go.setOnClickListener(new View.OnClickListener() {
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
        public TextView context;
        public TextView text;
        public ImageView btn_go;
        public VH(View v) {
            super(v);
            context = v.findViewById(R.id.tv_content);
            text = v.findViewById(R.id.tv_text);
            btn_go = v.findViewById(R.id.btn_go);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }
}