package com.exc.roadlamp.work.workorder;

import android.widget.ImageView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.work.OrderListBean;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.xuexiang.xui.utils.ResUtils;

import java.util.ArrayList;


public class OrderListAdapter extends SmartRecyclerAdapter<OrderListBean.DataBean.RecordsBean> {
    public OrderListAdapter() {
        super(R.layout.item_order_list);
    }

    public OrderListAdapter(ArrayList<OrderListBean.DataBean.RecordsBean> data) {
        super(data, R.layout.item_order_list);
    }

    /**
     * 绑定布局控件
     *
     * @param holder
     * @param model
     * @param position
     */
    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListBean.DataBean.RecordsBean model, int position) {


//        holder.text(android.R.id.text1, ResUtils.getResources().getString(R.string.item_example_number_title, position));
//        holder.text(android.R.id.text2, ResUtils.getResources().getString(R.string.item_example_number_abstract, position));
//        holder.textColorId(android.R.id.text2, R.color.xui_config_color_light_blue_gray);
        holder.text(R.id.tv_order_name, model.getOrderName());
        holder.text(R.id.order_status, model.getStatusName());
        holder.text(R.id.order_time, model.getCreateTime());
        ImageView  iv_left_icon  = (ImageView) holder.findView(R.id.iv_left_icon);

        switch (model.getStatusId()) {

            case 1: //待初审
            case 5://待审核
            case 7://已超时
                iv_left_icon.setImageResource(R.drawable.order_dsh_icon);
                break;
            case 2://被驳回
                iv_left_icon.setImageResource(R.drawable.order_bh_icon);
                break;
            case 3://待处理
                iv_left_icon.setImageResource(R.drawable.order_dcl_icon);
                break;
            case 4://跟进中
            case 6://审核通过
                iv_left_icon.setImageResource(R.drawable.order_gjz_icon);
                break;


        }


    }


}
