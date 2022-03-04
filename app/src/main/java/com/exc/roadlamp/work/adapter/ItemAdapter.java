package com.exc.roadlamp.work.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.adapter.LampPoleEditAdapter;
import com.exc.roadlamp.device.bean.LampDeviceListBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.ljp.swipemenu.SwipeMenuLayout;


/*
 *@创建者       L_jp
 *@创建时间     2019/6/9 13:31.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private List<LampDeviceListBean.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();

    public ItemAdapter(Context context) {
        mContext = context;
    }

    public ItemAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(ItemAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public ItemAdapter.OnDeviceClick mOnDeviceClick;
    public interface OnDeviceClick{
        void click(int index);
    }
    public void setOnDeviceClick(ItemAdapter.OnDeviceClick onDeviceClick){mOnDeviceClick = onDeviceClick;}


    public ItemAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(ItemAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(List<LampDeviceListBean.DataBean.RecordsBean> recordsBeans){
        recordsBeanList = recordsBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lamppole_list_manager, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        LampDeviceListBean.DataBean.RecordsBean bean = recordsBeanList.get(i);
        viewHolder.mTv1.setText(bean.getName());
        viewHolder.tv_num.setText(bean.getLampPostNum());
        viewHolder.tv_content2.setText("挂载设备：" + bean.getDeviceNumber());
        viewHolder.tv_content3.setText("项目名称：" + bean.getAreaName());
        viewHolder.tv_content4.setText("区域名称：" + bean.getStreetName());
        viewHolder.tv_content5.setText("街道名称：" + bean.getSuperName());

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
        private TextView tv_num;
        private TextView tv_content2;
        private TextView tv_content3;
        private TextView tv_content4;
        private TextView tv_content5;
        private TextView tv_device;

        public ViewHolder(@NonNull View view) {
            super(view);
            mLl_item = view.findViewById(R.id.ll_item);
            mTv1 = view.findViewById(R.id.tv_content);
            mTv3 = view.findViewById(R.id.tv_menu2);
            mSwipe = view.findViewById(R.id.swipe_menu_layout);
            tv_num = view.findViewById(R.id.tv_num);
            tv_content2 = view.findViewById(R.id.content2);
            tv_content3 = view.findViewById(R.id.content3);
            tv_content4 = view.findViewById(R.id.content4);
            tv_content5 = view.findViewById(R.id.content5);
            tv_device = view.findViewById(R.id.device_go);
        }
    }
}

