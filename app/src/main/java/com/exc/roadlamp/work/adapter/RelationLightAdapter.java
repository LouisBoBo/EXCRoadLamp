package com.exc.roadlamp.work.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.work.model.RelationLightModel;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

public class RelationLightAdapter extends RecyclerView.Adapter<RelationLightAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private List<RelationLightModel.DataBean.SystemDeviceListBean> recordsBeanList = new ArrayList<>();

    public RelationLightAdapter(Context context) {
        mContext = context;
    }

    public RelationLightAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(RelationLightAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<RelationLightModel.DataBean.SystemDeviceListBean> recordsBeans){
        recordsBeanList = recordsBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dev_map_search_result_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        RelationLightModel.DataBean.SystemDeviceListBean bean = recordsBeanList.get(i);
        viewHolder.tv_name.setText(bean.getSystemDeviceName());
        viewHolder.tv_names.setVisibility(View.GONE);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_names;


        public ViewHolder(@NonNull View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_names = view.findViewById(R.id.tv_names);
        }
    }
}