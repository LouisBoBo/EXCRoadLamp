
package com.exc.roadlamp.device.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;


public class LampLightDetailListAdapter extends BaseQuickAdapter<MapLampCommonDevList.DataBean.ChildrenListBean, BaseViewHolder> {
    public LampLightDetailListAdapter() {
        super(R.layout.item_lamp_pole_light_detail_list);
    }

    public OnItemViewChangeListener mOnItemViewChangeListener;

    public interface OnItemViewChangeListener {

        void onViewChange(MapLampCommonDevList.DataBean.ChildrenListBean changeDevBean, int position, int progress, int deviceState);

    }

    public void setOnItemViewChangeListener(OnItemViewChangeListener onItemViewChangeListener) {
        this.mOnItemViewChangeListener = onItemViewChangeListener;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MapLampCommonDevList.DataBean.ChildrenListBean devBean) {
        AtomicInteger deviceState = new AtomicInteger();
        int isOnline =  devBean.getDlmRespDevice().getIsOnline();//在线状态  0离线  1在线
        final int[] mProgress = {devBean.getDlmRespDevice().getBrightness()};
        int position = baseViewHolder.getAdapterPosition();
        SeekBar sk = baseViewHolder.findView(R.id.sk);
        ImageView iv_switch = baseViewHolder.findView(R.id.iv_switch);
        TextView tv_percentage = baseViewHolder.findView(R.id.tv_percentage);
        TextView tv_dev_status = baseViewHolder.findView(R.id.tv_dev_status);
        baseViewHolder.setText(R.id.tv_name, "" + devBean.name);
        baseViewHolder.setText(R.id.tv_num, "" + devBean.num);
        deviceState.set(devBean.getDlmRespDevice().getDeviceState());


        if( isOnline == 1 ){
            tv_dev_status.setText("在线");
            tv_dev_status.setBackgroundColor(Color.parseColor("#59D68D"));
        }else{
            tv_dev_status.setText("离线");
            tv_dev_status.setBackgroundColor(Color.parseColor("#ADADAD"));
        }


        if (deviceState.get() == 0) { //关闭状态
            iv_switch.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_closed));
            sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_closed_seek_bar_drawable));
        } else {//开启状态
            iv_switch.setImageDrawable(getContext().getDrawable(R.drawable.icon_dev_status_open));
            sk.setProgressDrawable(getContext().getDrawable(R.drawable.lamp_dev_open_seek_bar_drawable));
        }
        sk.setProgress(devBean.getDlmRespDevice().getBrightness(), true);
        tv_percentage.setText("亮度 "+ mProgress[0] + "%");
        iv_switch.setOnClickListener(v -> {
                    if (null != mOnItemViewChangeListener) {

                        if(deviceState.get() == 0){
                            deviceState.set(1);
                        }else{
                            deviceState.set(0);
                        }
                        mOnItemViewChangeListener.onViewChange(devBean,position, mProgress[0], deviceState.get());



                    }
                }
        );
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 20){
                    progress = 20;
                    sk.setProgress(progress);
                }
                mProgress[0] = progress;
                tv_percentage.setText("亮度 "+ mProgress[0]  + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (null != mOnItemViewChangeListener) {
                    mOnItemViewChangeListener.onViewChange(devBean,position,seekBar.getProgress(), deviceState.get());
                }
            }
        });

    }


}
