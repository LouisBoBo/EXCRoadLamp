package com.exc.roadlamp.device.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.LampDeviceBean;
import com.exc.roadlamp.device.bean.LampVideoBean;
import com.exc.roadlamp.device.customview.MyPSwitchView;

import java.util.ArrayList;
import java.util.List;


public class LampDeviceControlAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LampDeviceBean.DataBean recordsBean;
    private List<String> select_colors = new ArrayList<>();
    private List<Integer> select_colorids = new ArrayList<>();
    public LampDeviceControlAdapter.OnItemClick mOnItemClick;

    private Integer pubposition;
    private boolean prohibit;//是否禁止操作

    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(LampDeviceControlAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public LampDeviceControlAdapter.OnImageClick monImageClick;
    public interface OnImageClick {
        void imgclick(int index);
    }
    public void setOnImageClick(LampDeviceControlAdapter.OnImageClick onImageClick) {
        monImageClick = onImageClick;
    }

    public LampDeviceControlAdapter.OnSeekbarClick monSeekbarClick;
    public interface OnSeekbarClick {
        void seekbarclick(int index , int progress);
    }
    public void setOnSeekbarClick(LampDeviceControlAdapter.OnSeekbarClick onSeekbarClick) {
        monSeekbarClick = onSeekbarClick;
    }

    public void setmDatas(LampDeviceBean.DataBean bean , boolean fib) {
        recordsBean = bean;
        prohibit = fib;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(recordsBean !=null){
            return recordsBean.getChildrenList().size();
        }else return 0;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        LampDeviceBean.DataBean.ChildrenListBean childrenListBean = recordsBean.getChildrenList().get(position);

        if(holder instanceof bannerHolder){
            ((bannerHolder) holder).device_name.setText(childrenListBean.getName());
            ((bannerHolder) holder).device_num.setText(childrenListBean.getNum());
//            ((bannerHolder) holder).select_mark.setVisibility(childrenListBean.isShow()?View.VISIBLE:View.INVISIBLE);
            ((bannerHolder) holder).switchView.setChecked(childrenListBean.getDlmRespDevice().getDeviceState()==1?true:false);
            ((bannerHolder) holder).switchView.mSlidable = !prohibit;
            ((bannerHolder) holder).switchView.isclick = !prohibit;

            if(!prohibit){
                if (childrenListBean.isSelect()) {
                    ((bannerHolder) holder).select_mark.setImageResource(R.mipmap.video_select);
                } else {
                    ((bannerHolder) holder).select_mark.setImageResource(R.mipmap.video_normal);
                }
            }

            if(!prohibit){
                ((bannerHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //item 点击事件
                    public void onClick(View v) {
                        mOnItemClick.click(position);
                    }
                });

                ((bannerHolder) holder).select_mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monImageClick.imgclick(position);
                    }
                });
            }

            ((bannerHolder) holder).switchView.setOnSwitchCheckListener(new MyPSwitchView.SwitchCheckListener() {
                @Override
                public void onCheckedChanged(boolean isChecked) {

                    childrenListBean.setChecked(isChecked);
                    childrenListBean.getDlmRespDevice().setDeviceState(isChecked?1:0);
                    recordsBean.getChildrenList().get(position).setChecked(isChecked);

                    monImageClick.imgclick(position);
                }
            });

            if(childrenListBean.getSelect_colors() != null) {
                for (String select_color : childrenListBean.getSelect_colors()) {
                    if (select_color.equals(((bannerHolder) holder).color1.getText())) {
                        ((bannerHolder) holder).color1.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color1.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color2.getText())) {
                        ((bannerHolder) holder).color2.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color2.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color3.getText())) {
                        ((bannerHolder) holder).color3.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color3.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color4.getText())) {
                        ((bannerHolder) holder).color4.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color4.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color5.getText())) {
                        ((bannerHolder) holder).color5.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color5.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color6.getText())) {
                        ((bannerHolder) holder).color6.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color6.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color7.getText())) {
                        ((bannerHolder) holder).color7.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color7.setTextColor(Color.WHITE);
                    }
                    if (select_color.equals(((bannerHolder) holder).color8.getText())) {
                        ((bannerHolder) holder).color8.setBackgroundResource(R.drawable.background_btn_blue);
                        ((bannerHolder) holder).color8.setTextColor(Color.WHITE);
                    }
                }
            }

            if(!prohibit) {
                tempOnClick(((bannerHolder) holder).color1, position);
                tempOnClick(((bannerHolder) holder).color2, position);
                tempOnClick(((bannerHolder) holder).color3, position);
                tempOnClick(((bannerHolder) holder).color4, position);
                tempOnClick(((bannerHolder) holder).color5, position);
                tempOnClick(((bannerHolder) holder).color6, position);
                tempOnClick(((bannerHolder) holder).color7, position);
                tempOnClick(((bannerHolder) holder).color8, position);
            }

        }else{

            ((singleHolder) holder).device_name.setText(childrenListBean.getName());
            ((singleHolder) holder).device_num.setText(childrenListBean.getNum());
//            ((singleHolder) holder).select_mark.setVisibility(childrenListBean.isShow()?View.VISIBLE:View.INVISIBLE);
            ((singleHolder) holder).switchView.setChecked(childrenListBean.getDlmRespDevice().getDeviceState()==1?true:false);
            ((singleHolder) holder).seekBar.setProgress(childrenListBean.getDlmRespDevice().getBrightness());
            ((singleHolder) holder).progress.setText(childrenListBean.getDlmRespDevice().getBrightness()+"%");
            ((singleHolder) holder).seekBar.setEnabled(!prohibit);

            ((singleHolder) holder).switchView.mSlidable = !prohibit;
            ((singleHolder) holder).switchView.isclick = !prohibit;

            if(!prohibit){
                if (childrenListBean.isSelect()) {
                    ((singleHolder) holder).select_mark.setImageResource(R.mipmap.video_select);
                } else {
                    ((singleHolder) holder).select_mark.setImageResource(R.mipmap.video_normal);
                }
            }

            if(!prohibit){
                ((singleHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //item 点击事件
                    public void onClick(View v) {
                        mOnItemClick.click(position);
                    }
                });

                ((singleHolder) holder).select_mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monImageClick.imgclick(position);
                    }
                });
            }

            ((singleHolder) holder).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ((singleHolder) holder).progress.setText(progress + "%");
                    childrenListBean.setProgress(progress);
                    childrenListBean.getDlmRespDevice().setBrightness(progress);
                    recordsBean.getChildrenList().get(position).setProgress(progress);
                    recordsBean.getChildrenList().get(position).setSelect(true);
//                    monImageClick.imgclick(position);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            ((singleHolder) holder).switchView.setOnSwitchCheckListener(new MyPSwitchView.SwitchCheckListener() {
                @Override
                public void onCheckedChanged(boolean isChecked) {
                    childrenListBean.setChecked(isChecked);
                    childrenListBean.getDlmRespDevice().setDeviceState(isChecked?1:0);
                    recordsBean.getChildrenList().get(position).setChecked(isChecked);
                    recordsBean.getChildrenList().get(position).setSelect(true);
//                    monImageClick.imgclick(position);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        LampDeviceBean.DataBean.ChildrenListBean childrenListBean = recordsBean.getChildrenList().get(position);

        if(childrenListBean.getDlmRespDevice().getLampPositionId()==3){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==1){
            return new bannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_color_control, parent, false));
        }else {
            return new singleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_control, parent, false));
        }
    }


    public class singleHolder extends RecyclerView.ViewHolder {

        public TextView device_name;
        public TextView device_num;
        public ImageView select_mark;
        public SeekBar seekBar;
        public TextView progress;
        public MyPSwitchView switchView;

        public singleHolder(View v) {
            super(v);
            device_name = v.findViewById(R.id.device_name);
            device_num = v.findViewById(R.id.device_num);
            select_mark = v.findViewById(R.id.img_mark);
            seekBar = v.findViewById(R.id.sk);
            progress = v.findViewById(R.id.progress);
            switchView = v.findViewById(R.id.status_switch);
        }
    }

    public class bannerHolder extends RecyclerView.ViewHolder{
        public TextView device_name;
        public TextView device_num;
        public ImageView select_mark;
        public MyPSwitchView switchView;
        public TextView color1;
        public TextView color2;
        public TextView color3;
        public TextView color4;
        public TextView color5;
        public TextView color6;
        public TextView color7;
        public TextView color8;

        public bannerHolder(View v) {
            super(v);
            device_name = v.findViewById(R.id.device_name);
            device_num = v.findViewById(R.id.device_num);
            select_mark = v.findViewById(R.id.img_mark);
            switchView = v.findViewById(R.id.status_switch);
            color1 = v.findViewById(R.id.color1);
            color2 = v.findViewById(R.id.color2);
            color3 = v.findViewById(R.id.color3);
            color4 = v.findViewById(R.id.color4);
            color5 = v.findViewById(R.id.color5);
            color6 = v.findViewById(R.id.color6);
            color7 = v.findViewById(R.id.color7);
            color8 = v.findViewById(R.id.color8);
        }
    }


    //颜色按钮点击
    public void tempOnClick(TextView tv,int pos){

        tv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                monImageClick.imgclick(pos);

                if(recordsBean.getChildrenList().get(pos).getSelect_colors() != null) {
                    select_colors = recordsBean.getChildrenList().get(pos).getSelect_colors();
                }else {
                    select_colors = new ArrayList<>();
                }

                if(select_colors.contains(tv.getText().toString())){
                    tv.setBackgroundResource(R.drawable.bg_slide_light);
                    tv.setTextColor(R.color.region_black);
                    select_colors.remove(tv.getText().toString());
                }else {
                    tv.setBackgroundResource(R.drawable.background_btn_blue);
                    tv.setTextColor(Color.WHITE);
                    select_colors.add(tv.getText().toString());
                }

                select_colorids = new ArrayList<>();
                for(int i=0;i<select_colors.size();i++){
                    for (LampVideoBean.DataBean lampvideobean : recordsBean.getLampvideobeans()) {
                        if(select_colors.get(i).equals(lampvideobean.getName())){
                            select_colorids.add(lampvideobean.getId());
                            break;
                        };
                    }
                }
                recordsBean.getChildrenList().get(pos).setSelect_colors(select_colors);
                recordsBean.getChildrenList().get(pos).setSelect_colorids(select_colorids);
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

    }
}