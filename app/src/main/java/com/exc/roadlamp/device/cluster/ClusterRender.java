package com.exc.roadlamp.device.cluster;

import android.graphics.drawable.Drawable;

import java.util.List;


public interface ClusterRender {
    /**
     * 根据聚合点的元素数目返回渲染背景样式
     *
     * @param clusterNum
     * @param clusterItems 集合点设备集合
     * @return
     */
    Drawable getDrawAble(List<ClusterItem> clusterItems, int clusterNum);
}
