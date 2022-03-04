package com.exc.roadlamp.device.bean;

import com.xuexiang.xui.widget.picker.wheelview.interfaces.IPickerViewItem;

import java.util.List;

import lombok.Data;

@Data
public class FiddlerAreaBean implements IPickerViewItem {//区域
    public String areaName;
    public int areaId;
    public List<FiddlerStreetBean> street;//街道

    @Override
    public String getPickerViewText() {
        return this.areaName;
    }

    @Data
    public static class FiddlerStreetBean implements IPickerViewItem{
        public String streetName;
        public int streetId;
        public List<FiddlerSiteBean> site;//站点

        @Override
        public String getPickerViewText() {
            return this.streetName;
        }

        @Data
        public static class FiddlerSiteBean implements IPickerViewItem{
            public String siteName;
            public int siteId;

            @Override
            public String getPickerViewText() {
                return this.siteName;
            }
        }
    }







}
