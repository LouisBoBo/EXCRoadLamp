package com.exc.roadlamp.work.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.work.model.StrategyActionModel;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

public class ControlAdapter extends RecyclerView.Adapter<ControlAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private List<CenterControlListData.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();

    public ControlAdapter(Context context) {
        mContext = context;
    }

    public ControlAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(ControlAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public ControlAdapter.OnDeviceClick mOnDeviceClick;
    public interface OnDeviceClick{
        void click(int index);
    }
    public void setOnDeviceClick(ControlAdapter.OnDeviceClick onDeviceClick){mOnDeviceClick = onDeviceClick;}

    public ControlAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(ControlAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(List<CenterControlListData.DataBean.RecordsBean> beanList){
        recordsBeanList = beanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_control_action, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        CenterControlListData.DataBean.RecordsBean bean = recordsBeanList.get(i);
        viewHolder.mTv1.setText(bean.getName());
        viewHolder.tv_num.setText(bean.getNum());
        viewHolder.tv_content1.setText(bean.getTypeName());
        viewHolder.tv_content2.setText("所属配电柜：" + bean.getCabinetName());
        viewHolder.tv_content3.setText("所属项目：" + bean.getAreaName());
        viewHolder.tv_content4.setText("最后在线时间：" + bean.getLastOnlineTime());
        viewHolder.tv_content5.setText("灯具数量：" + bean.getRelationCount());

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

        viewHolder.tv_scan_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeviceClick.click(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(recordsBeanList != null)
        {
            return recordsBeanList.size();
        }else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View mLl_item;
        private final TextView mTv1;
        private final View mTv3;
        private final SwipeMenuLayout mSwipe;
        private TextView tv_num;
        private TextView tv_scan_loop;
        private TextView tv_content1;
        private TextView tv_content2;
        private TextView tv_content3;
        private TextView tv_content4;
        private TextView tv_content5;

        public ViewHolder(@NonNull View view) {
            super(view);
            mLl_item = view.findViewById(R.id.ll_item);
            mTv1 = view.findViewById(R.id.tv_content);
            mTv3 = view.findViewById(R.id.tv_menu2);
            mSwipe = view.findViewById(R.id.swipe_menu_layout);
            tv_num = view.findViewById(R.id.tv_num);
            tv_scan_loop = view.findViewById(R.id.scan_loop);
            tv_content1 = view.findViewById(R.id.content1);
            tv_content2 = view.findViewById(R.id.content2);
            tv_content3 = view.findViewById(R.id.content3);
            tv_content4 = view.findViewById(R.id.content4);
            tv_content5 = view.findViewById(R.id.content5);
        }
    }
}