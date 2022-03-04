package com.exc.roadlamp.device.cluster;

import com.amap.api.maps.model.LatLng;
import com.exc.roadlamp.device.DevTypeEnum;


public class RegionItem<T> implements ClusterItem {
    private LatLng mLatLng;
    private DevTypeEnum mDevTypeEnum;
    private T mT;

    public RegionItem(LatLng latLng, DevTypeEnum devTypeEnum, T t) {
        mLatLng = latLng;
        mDevTypeEnum = devTypeEnum;
        mT = t;
    }

    @Override
    public LatLng getPosition() {
        // TODO Auto-generated method stub
        return mLatLng;
    }

    @Override
    public T getT() {
        return mT;
    }

    @Override
    public DevTypeEnum getDevTyeEnum() {
        return mDevTypeEnum;
    }



}
