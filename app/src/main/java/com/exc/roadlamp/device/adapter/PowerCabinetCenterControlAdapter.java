
package com.exc.roadlamp.device.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.PowerCabinetDetail;

import org.jetbrains.annotations.NotNull;

public class PowerCabinetCenterControlAdapter extends BaseQuickAdapter<PowerCabinetDetail.DataBean.ControlOfCabinetVOListBean, BaseViewHolder> {
    public PowerCabinetCenterControlAdapter() {
        super(R.layout.item_power_cabinet_detil_center_control_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PowerCabinetDetail.DataBean.ControlOfCabinetVOListBean cabinetVOListBean) {
        int position = baseViewHolder.getAdapterPosition();



        TextView tv_num = baseViewHolder.findView(R.id.tv_num);
        TextView tv_name = baseViewHolder.findView(R.id.tv_name);
        TextView tv_status = baseViewHolder.findView(R.id.tv_status);
        LinearLayout root = baseViewHolder.findView(R.id.root);


//        if (position == 0) {
//            tv_num.setText("NO");
//            tv_name.setText("设备名称");
//            tv_status.setText("状态");
//            tv_num.setTextSize(15f);
//            tv_name.setTextSize(15f);
//            tv_status.setTextSize(15f);
//        } else {
            tv_num.setText(position + "");
            tv_name.setText(cabinetVOListBean.controlName);
            if (cabinetVOListBean.isOnline == 1) {
                tv_status.setText("在线");
                tv_status.setTextColor(Color.GREEN);
            } else {
                tv_status.setText("离线");
                tv_status.setTextColor(Color.RED);
            }
//        }

//        if(position == 0){
//            root.setBackgroundResource(R.drawable.view_table_line_posttion0);
//        }else if(position ==   getItemCount() -1){
//            root.setBackgroundResource(R.drawable.view_table_line_posttion_last);
//        }else{
//            root.setBackgroundResource(R.drawable.view_table_line);
//
//        }

    }
}
