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
import com.exc.roadlamp.work.model.ActionOptionModel;
import com.exc.roadlamp.work.model.LightStrategyModel;
import com.exc.roadlamp.work.model.StrategyActionModel;

import java.util.ArrayList;
import java.util.List;

import cn.ljp.swipemenu.SwipeMenuLayout;

public class AddStrategyAdapter extends RecyclerView.Adapter<AddStrategyAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    private StrategyActionModel strategyActionModel;
    private List<StrategyActionModel> recordsBeanList = new ArrayList<>();

    public AddStrategyAdapter(Context context) {
        mContext = context;
    }

    public AddStrategyAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(AddStrategyAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public AddStrategyAdapter.OnDeviceClick mOnDeviceClick;
    public interface OnDeviceClick{
        void click(int index);
    }
    public void setOnDeviceClick(AddStrategyAdapter.OnDeviceClick onDeviceClick){mOnDeviceClick = onDeviceClick;}

    public AddStrategyAdapter.OnDeleateClick mOnDeleateClick;
    public interface OnDeleateClick{
        void click(int index);
    }
    public void setOnDeleateClick(AddStrategyAdapter.OnDeleateClick onDeviceClick){mOnDeleateClick = onDeviceClick;}

    public void setmDatas(List<StrategyActionModel> beans){
//        strategyActionModel = model;
//        if(model.getDlmReqSceneActionVOList().size() >0) {
//            recordsBeanList.add(strategyActionModel.getDlmReqSceneActionVOList().get(0));
//        }

        recordsBeanList = beans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_strategy_action, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        StrategyActionModel model = recordsBeanList.get(i);
        viewHolder.mTv1.setText("动作名称：" + "动作" + i);
        viewHolder.tv_content1.setText("动作类型：" + model.getDlmReqSceneActionVOList().get(0).getAction());
        viewHolder.tv_content3.setText("执行时间：" + model.getDlmReqSceneActionVOList().get(0).getExecutionTime());
        viewHolder.tv_content4.setText("开始时间：" + model.getDlmReqSceneActionVOList().get(0).getStartDate());
        viewHolder.tv_content5.setText("结束时间：" + model.getDlmReqSceneActionVOList().get(0).getEndDate());

        if(model.getStrategytype()==1){
            viewHolder.tv_content2.setText("方式：" + (model.getDlmReqSceneActionVOList().get(0).getIsOpen()==1?"开":"关"));
        }else {
            viewHolder.tv_content2.setText("亮度：" + model.getDlmReqSceneActionVOList().get(0).getProgress() + "%");
        }

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
        if(recordsBeanList.size() >0)
        {
            return recordsBeanList.size();
        }else return 0;
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
        }
    }
}