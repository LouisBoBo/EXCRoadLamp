package com.exc.roadlamp.device.cluster;

import com.amap.api.maps.model.LatLng;
import com.exc.roadlamp.device.DevTypeEnum;


public interface ClusterItem<T> {
    /**
     * 返回聚合元素的地理位置
     *
     * @return
     */
    LatLng getPosition();

    T getT();

    DevTypeEnum getDevTyeEnum();


}
