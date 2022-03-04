package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CenterControlListData implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":33,"name":"南山集控","num":"100258","location":null,"typeId":4,"typeName":"EXC","isOnline":1,"createTime":"2021-05-27 15:38:57","lastOnlineTime":"2021-05-31 16:51:00","cabinetId":114,"cabinetName":"南山开发-配电柜","areaId":1,"areaName":"宝安区","ip":null,"port":null,"mac":null,"dlmRespElectricityMeterDataVO":null,"dlmRespCmModuleVOList":null}],"total":1,"size":9,"current":1,"searchCount":true,"pages":1}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * records : [{"id":33,"name":"南山集控","num":"100258","location":null,"typeId":4,"typeName":"EXC","isOnline":1,"createTime":"2021-05-27 15:38:57","lastOnlineTime":"2021-05-31 16:51:00","cabinetId":114,"cabinetName":"南山开发-配电柜","areaId":1,"areaName":"宝安区","ip":null,"port":null,"mac":null,"dlmRespElectricityMeterDataVO":null,"dlmRespCmModuleVOList":null}]
         * total : 1
         * size : 9
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
             * id : 33
             * name : 南山集控
             * num : 100258
             * location : null
             * typeId : 4
             * typeName : EXC
             * isOnline : 1
             * createTime : 2021-05-27 15:38:57
             * lastOnlineTime : 2021-05-31 16:51:00
             * cabinetId : 114
             * cabinetName : 南山开发-配电柜
             * areaId : 1
             * areaName : 宝安区
             * ip : null
             * port : null
             * mac : null
             * dlmRespElectricityMeterDataVO : null
             * dlmRespCmModuleVOList : null
             */

            public int id;
            public String name;
            public String num;
            public Object location;
            public int typeId;
            public String typeName;
            public int isOnline;
            public String createTime;
            public String lastOnlineTime;
            public int cabinetId;
            public String cabinetName;
            public int areaId;
            public String areaName;
            public Object ip;
            public Object port;
            public Object mac;
            public Object dlmRespElectricityMeterDataVO;
            public Object dlmRespCmModuleVOList;
            //集控所在的配电柜信息
            public PowerCabinetList.DataBean.RecordsBean powerCabinetInfo;
            public boolean is_select;
            public int relationCount;
        }
    }
}
