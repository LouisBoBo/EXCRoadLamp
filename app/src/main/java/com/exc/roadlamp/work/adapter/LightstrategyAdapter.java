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
import com.exc.roadlamp.work.model.LightStrategyModel;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

public class LightstrategyAdapter extends RecyclerView.Adapter<LightstrategyAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private List<LightStrategyModel.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();

    public LightstrategyAdapter(Context context) {
        mContext = context;
    }

    public LightstrategyAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(LightstrategyAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public LightstrategyAdapter.OnDeviceClick mOnDeviceClick;
    public interface OnDeviceClick{
        void click(int index);
    }
    public void setOnDeviceClick(LightstrategyAdapter.OnDeviceClick onDeviceClick){mOnDeviceClick = onDeviceClick;}

    public LightstrategyAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(LightstrategyAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(List<LightStrategyModel.DataBean.RecordsBean> recordsBeans){
        recordsBeanList = recordsBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_light_strategy, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        LightStrategyModel.DataBean.RecordsBean bean = recordsBeanList.get(i);
        viewHolder.mTv1.setText("策略名称：" + bean.getName());
        viewHolder.tv_content1.setText("开始日期：" + bean.getStartDate());
        viewHolder.tv_content2.setText("结束日期：" + bean.getEndDate());
        viewHolder.tv_content3.setText("创建日期：" + bean.getCreateTime());
        viewHolder.tv_content4.setText("创建人：" + bean.getCreatorName());

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
        }
    }
}