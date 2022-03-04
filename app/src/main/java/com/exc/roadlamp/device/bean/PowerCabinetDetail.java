package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PowerCabinetDetail implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"id":114,"name":"南山开发-配电柜","num":"W1006","state":1,"areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","longitude":114.07267,"latitude":22.52938,"location":"测试地址","createTime":"2021-05-26 14:02:24","description":"测试","sunRise":"5:39 ","sunSet":"19:02","controlOfCabinetVOList":[{"controlId":33,"controlName":"南山集控","location":null,"controlTypeId":4,"controlTypeName":"EXC","isOnline":1,"controlNum":"100258"}],"controlCount":null}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * id : 114
         * name : 南山开发-配电柜
         * num : W1006
         * state : 1
         * areaId : 1
         * areaName : 宝安区
         * streetId : 1
         * streetName : 松岗街道
         * longitude : 114.07267
         * latitude : 22.52938
         * location : 测试地址
         * createTime : 2021-05-26 14:02:24
         * description : 测试
         * sunRise : 5:39
         * sunSet : 19:02
         * controlOfCabinetVOList : [{"controlId":33,"controlName":"南山集控","location":null,"controlTypeId":4,"controlTypeName":"EXC","isOnline":1,"controlNum":"100258"}]
         * controlCount : null
         */

        public int id;
        public String name;
        public String num;
        public int state;
        public int areaId;
        public String areaName;
        public int streetId;
        public String streetName;
        public double longitude;
        public double latitude;
        public String location;
        public String createTime;
        public String lastOnlineTime;
        public String description;
        public String sunRise;
        public String sunSet;
        public Object controlCount;
        public List<ControlOfCabinetVOListBean> controlOfCabinetVOList;

        @Data
        public static class ControlOfCabinetVOListBean implements Serializable {
            /**
             * controlId : 33
             * controlName : 南山集控
             * location : null
             * controlTypeId : 4
             * controlTypeName : EXC
             * isOnline : 1
             * controlNum : 100258
             */

            public int controlId;
            public String controlName;
            public Object location;
            public int controlTypeId;
            public String controlTypeName;
            public int isOnline;
            public String controlNum;
        }
    }
}
