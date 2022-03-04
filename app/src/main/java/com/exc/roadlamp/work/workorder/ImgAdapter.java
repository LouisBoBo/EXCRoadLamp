package com.exc.roadlamp.work.workorder;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.exc.roadlamp.R;
import com.exc.roadlamp.http.HttpApi;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ImgAdapter extends BaseQuickAdapter<OrderPic, BaseViewHolder> {
    public ImgAdapter() {
        super(R.layout.item_order_edit_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderPic orderPic) {
        RadiusImageView iv = baseViewHolder.findView(R.id.iv);
        LinearLayout ll_add = baseViewHolder.findView(R.id.ll_add);
        ImageView iv_close = baseViewHolder.findView(R.id.iv_close);
        int position = getItemPosition(orderPic);




        if (orderPic.isVirtual()) {
            ll_add.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
            iv_close.setVisibility(View.GONE);

            iv.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.iv,position));
            iv_close.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.iv_close,position));
            ll_add.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.ll_add,position));
            return;
        }

        if (orderPic.isXC()) {
            ll_add.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
            iv_close.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(new File(orderPic.getFilename())).into(iv);

            iv.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.iv,position));
            iv_close.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.iv_close,position));
            ll_add.setOnClickListener(v -> mImgChildOnClickListener.onClick(R.id.ll_add,position));
            return;
        }
        String httImgUrl = HttpApi.SERVICES_ADDRESS + HttpApi.ORDER_PORT + orderPic.getFilename();
        Glide.with(getContext()).load(httImgUrl).into(iv);
        ll_add.setVisibility(View.GONE);
        iv.setVisibility(View.VISIBLE);
        iv_close.setVisibility(View.GONE);
    }

    public interface ImgChildOnClickListener {
        void onClick(int clickViewId, int position);
    }

    ImgChildOnClickListener mImgChildOnClickListener;

    public void setImgChildOnClickListener(ImgChildOnClickListener imgChildOnClickListener) {
        this.mImgChildOnClickListener = imgChildOnClickListener;
    }


}
