package com.exc.roadlamp.customview;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.exc.roadlamp.R;

public class PopupViewAddDevice extends PopupWindow {
    private View mView;
    private LinearLayout ll_popmenu_record,ll_popmenu_book,ll_popmenu_search;

    public PopupViewAddDevice(Activity paramActivity, View.OnClickListener paramOnClickListener,
                     int paramInt1, int paramInt2){
        mView = LayoutInflater.from(paramActivity).inflate(R.layout.popwindow_adddevice, null);
        ll_popmenu_record = (LinearLayout) mView.findViewById(R.id.add_qrcode);
        ll_popmenu_book = (LinearLayout) mView.findViewById(R.id.add_manual);
        if (paramOnClickListener != null){
            //设置点击监听
            ll_popmenu_record.setOnClickListener(paramOnClickListener);
            ll_popmenu_book.setOnClickListener(paramOnClickListener);
            setContentView(mView);
            //设置宽度
            setWidth(paramInt1);
            //设置高度
            setHeight(paramInt2);
            //设置显示隐藏动画
//            setAnimationStyle(R.style.AnimTools);
            //设置背景透明
            setBackgroundDrawable(new ColorDrawable(0));
        }
    }
}
