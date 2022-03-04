package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PowerCabinetList implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":114,"name":"测试","num":"W1006","state":1,"areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","longitude":114.07267,"latitude":22.52938,"location":null,"createTime":"2021-05-26 14:02:24","description":null,"sunRise":null,"sunSet":null,"controlOfCabinetVOList":null,"controlCount":2}],"total":1,"size":99999999,"current":1,"searchCount":true,"pages":1}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * records : [{"id":114,"name":"测试","num":"W1006","state":1,"areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","longitude":114.07267,"latitude":22.52938,"location":null,"createTime":"2021-05-26 14:02:24","description":null,"sunRise":null,"sunSet":null,"controlOfCabinetVOList":null,"controlCount":2}]
         * total : 1
         * size : 99999999
         * current : 1
         * searchCount : true
         * pages : 1
         */

        public int total;
        public int size;
        public int current;
        public boolean searchCount;
        public int pages;
        public List<RecordsBean> records;

        @Data
        public static class RecordsBean implements Serializable {
            /**
             * id : 114
             * name : 测试
             * num : W1006
             * state : 1
             * areaId : 1
             * areaName : 宝安区
             * streetId : 1
             * streetName : 松岗街道
             * longitude : 114.07267
             * latitude : 22.52938
             * location : null
             * createTime : 2021-05-26 14:02:24
             * description : null
             * sunRise : null
             * sunSet : null
             * controlOfCabinetVOList : null
             * controlCount : 2
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
            public Object location;
            public String createTime;
            public Object description;
            public Object sunRise;
            public Object sunSet;
            public Object controlOfCabinetVOList;
            public int controlCount;
            public boolean is_select;
        }
    }
}
