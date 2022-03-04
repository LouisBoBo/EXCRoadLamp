package com.exc.roadlamp.device.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.TextureMapView;

public class MyTextureMapView extends TextureMapView {
    private Context context;

    public MyTextureMapView(Context context) {
        super(context);
    }

    public MyTextureMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public MyTextureMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public MyTextureMapView(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        // view 加载完成时回调
        this.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup child = (ViewGroup) getChildAt(0);//地图框架

// child.getChildAt(0).setVisibility(View.GONE);//地图

                        child.getChildAt(2).setVisibility(View.GONE);//logo

 child.getChildAt(5).setVisibility(View.GONE);//缩放按钮

// child.getChildAt(6).setVisibility(View.VISIBLE);//定位按钮

// child.getChildAt(7).setVisibility(View.VISIBLE);//指南针
                    }
                });
    }

}
