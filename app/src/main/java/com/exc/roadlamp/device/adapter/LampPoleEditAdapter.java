package com.exc.roadlamp.device.adapter;

import android.content.Context;
import android.util.Log;
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
import com.exc.roadlamp.work.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

import static com.exc.roadlamp.core.webview.AgentWebFragment.TAG;

public class LampPoleEditAdapter extends RecyclerView.Adapter<LampPoleEditAdapter.VH> {
    private LampDeviceBean.DataBean recordsBean;
    private boolean isEdit;
    private List<LampChildrenListBean> childrenListBeans = new ArrayList<>();

    public LampPoleEditAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(LampPoleEditAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(LampDeviceBean.DataBean bean ,boolean isedit) {
        recordsBean = bean;
        isEdit = isedit;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LampPoleEditAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lamp_pole_light, parent, false);
        return new LampPoleEditAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LampPoleEditAdapter.VH holder, int position) {
        LampDeviceBean.DataBean.ChildrenListBean childrenListBean = recordsBean.getChildrenList().get(position);
        holder.device_num.setText("单灯编号：" + childrenListBean.getNum());
        holder.device_model.setText("协议类型：" + childrenListBean.getDlmRespDevice().getModel());
        holder.select_more.setVisibility(isEdit?View.GONE:View.VISIBLE);

        holder.device_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleateClick.click(position);
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
        public TextView device_num;
        public TextView device_model;
        public ImageView select_more;
        public TextView device_del;

        public VH(View v) {
            super(v);
            device_num = v.findViewById(R.id.light_num);
            device_model = v.findViewById(R.id.light_model);
            select_more = v.findViewById(R.id.select_more);
            device_del = v.findViewById(R.id.tv_deleate);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }
}
