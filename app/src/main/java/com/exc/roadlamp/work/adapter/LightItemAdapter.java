package com.exc.roadlamp.work.adapter;

import android.annotation.SuppressLint;
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
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

public class LightItemAdapter extends RecyclerView.Adapter<LightItemAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private List<MapLampCommonDevList.DataBean.ChildrenListBean> recordsBeanList = new ArrayList<>();

    public LightItemAdapter(Context context) {
        mContext = context;
    }

    public LightItemAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(LightItemAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public LightItemAdapter.OnDeviceClick mOnDeviceClick;
    public interface OnDeviceClick{
        void click(int index);
    }
    public void setOnDeviceClick(LightItemAdapter.OnDeviceClick onDeviceClick){mOnDeviceClick = onDeviceClick;}

    public LightItemAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(LightItemAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(List<MapLampCommonDevList.DataBean.ChildrenListBean> recordsBeans){
        recordsBeanList = recordsBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lightcontrol_list_manager, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        MapLampCommonDevList.DataBean.ChildrenListBean bean = recordsBeanList.get(i);
        viewHolder.mTv1.setText(bean.getName());
        viewHolder.tv_content1.setText(bean.getNum());
        viewHolder.tv_content2.setText(bean.getModel());
        viewHolder.tv_content3.setText(bean.getLampPostName());
        viewHolder.tv_content4.setText(String.valueOf(bean.getBrightness())+"%");
        viewHolder.tv_content6.setText(bean.getCreateTime());
        viewHolder.tv_content5.setText(bean.getDlmRespDevice().getLocationAreaName()+bean.getDlmRespDevice().getLocationStreetName()+bean.getDlmRespDevice().getLocationSiteName());

        viewHolder.img_online.setBackgroundResource(bean.isOnline==1?R.color.divier_green:R.color.pop_item_bg_pressed);
        viewHolder.img_onlight.setBackgroundResource(bean.deviceState==1?R.color.divier_green:R.color.pop_item_bg_pressed);

        viewHolder.img_online.setText(bean.isOnline==1?"在线":"离线");
        viewHolder.img_onlight.setText(bean.deviceState==1?"开灯":"关灯");

        viewHolder.mSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.mSwipe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        viewHolder.mTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 点击了菜单->删除");
                mOnDeleateClick.click(i);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(i);
            }
        });

        viewHolder.tv_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeviceClick.click(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View mLl_item;
        private final TextView mTv1;
        private final View mTv3;
        private final SwipeMenuLayout mSwipe;
        private TextView tv_content1;
        private TextView tv_content2;
        private TextView tv_content3;
        private TextView tv_content4;
        private TextView tv_content5;
        private TextView tv_content6;
        private TextView tv_device;
        private TextView img_online;
        private TextView img_onlight;

        public ViewHolder(@NonNull View view) {
            super(view);
            mLl_item = view.findViewById(R.id.ll_item);
            mTv1 = view.findViewById(R.id.tv_content);
            mTv3 = view.findViewById(R.id.tv_menu2);
            mSwipe = view.findViewById(R.id.swipe_menu_layout);
            tv_content1 = view.findViewById(R.id.content1);
            tv_content2 = view.findViewById(R.id.content2);
            tv_content3 = view.findViewById(R.id.content3);
            tv_content4 = view.findViewById(R.id.content4);
            tv_content5 = view.findViewById(R.id.content5);
            tv_content6 = view.findViewById(R.id.content6);
            tv_device = view.findViewById(R.id.device_go);
            img_online = view.findViewById(R.id.img_online);
            img_onlight = view.findViewById(R.id.img_onlight);
        }
    }
}