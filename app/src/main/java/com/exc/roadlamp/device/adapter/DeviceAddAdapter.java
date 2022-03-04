package com.exc.roadlamp.device.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DeviceAddBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.PowerCabinetList;

import java.util.List;

public class DeviceAddAdapter extends RecyclerView.Adapter<DeviceAddAdapter.VH> {
    private int deviceType;//0灯杆 1灯具 2配电柜 3集控
    private List<DeviceAddBean> areaModelList;

    public DeviceAddAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(DeviceAddAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public DeviceAddAdapter.OnSelectClick monSelectClick;
    public interface OnSelectClick {
        void selectclick(int index, TextView type,TextView num);
    }
    public void setOnSelectClick(DeviceAddAdapter.OnSelectClick OnSelectClick) {
        monSelectClick = OnSelectClick;
    }

    public DeviceAddAdapter.OnEditClick monEditClick;
    public interface OnEditClick{
        void editclick(int index, String edittext);
    }
    public void setonEditClick(DeviceAddAdapter.OnEditClick onEditClick){
        monEditClick = onEditClick;
    }

    public DeviceAddAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void deleateclick(int index);
    }
    public void setOnDeleateClick(DeviceAddAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}


    public void setmDatas(List<DeviceAddBean> modelList) {
        areaModelList = modelList;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DeviceAddAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_add, parent, false);
        return new DeviceAddAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAddAdapter.VH holder, int position) {

        holder.setIsRecyclable(false);

        DeviceAddBean bean = areaModelList.get(position);
        holder.title_type.setText("协议类型");
        holder.title_num.setText("单灯编号");
        holder.text_num.setText(bean.device_num);
        holder.text_type.setText(bean.device_type);
        holder.text_line.setVisibility(View.GONE);
        holder.img_mark.setVisibility(View.GONE);
        holder.img_mark2.setVisibility(View.GONE);

        holder.text_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monSelectClick.selectclick(position,holder.text_type,holder.text_num);
            }
        });
        holder.img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monSelectClick.selectclick(position,holder.text_type,holder.text_num);
            }
        });

        holder.text_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                monEditClick.editclick(position,editable.toString());
            }
        });

        holder.tv_deleate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleateClick.deleateclick(position);
//                areaModelList.remove(position);
                //用这个 主要是解决了之前有个删除后刷新，其他条目菜单也会做个菜单动画bug
//                notifyDataSetChanged();
                //或者 notifyItemChanged(i); 也行
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
        public TextView title_num;
        public TextView title_type;
        public TextView text_num;
        public TextView text_type;
        public ImageView img_select;
        public TextView text_line;
        public TextView tv_deleate;
        public ImageView img_mark;
        public ImageView img_mark2;

        public VH(View v) {
            super(v);
            title_type = v.findViewById(R.id.light_site_name_1).findViewById(R.id.tv_title);
            title_num = v.findViewById(R.id.light_device_count_1).findViewById(R.id.tv_title);
            text_type = v.findViewById(R.id.light_site_name_1).findViewById(R.id.tv_content);
            text_num = v.findViewById(R.id.light_device_count_1).findViewById(R.id.tv_content);
            text_line = v.findViewById(R.id.light_device_count_1).findViewById(R.id.bottom_line);
            img_select = v.findViewById(R.id.light_site_name_1).findViewById(R.id.select_more);
            img_mark = v.findViewById(R.id.light_site_name_1).findViewById(R.id.img_mark);
            img_mark2 = v.findViewById(R.id.light_device_count_1).findViewById(R.id.img_mark);
            tv_deleate = v.findViewById(R.id.tv_deleate);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }
}