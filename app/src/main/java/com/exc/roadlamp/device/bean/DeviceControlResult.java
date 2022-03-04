package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DeviceControlResult implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"successNum":1,"successLampDeviceList":[{"id":1620,"name":"1112","deviceTypeId":7,"num":"860374055870032","location":null,"isOnline":0,"creator":null,"createTime":"2021-02-05 11:03:08","lastOnlineTime":"2021-05-18 16:31:29","description":null,"lampPostId":1216,"ip":null,"port":null,"mac":null,"strategyId":null,"strategyHistoryId":null,"isDelete":null,"deviceState":1,"reserveOne":"1e5522e22b68490eacaaa96dc73dd03a","brightness":100,"loopNum":null,"locationControlNum":null}],"defaultNum":0,"defaultLampDeviceList":[]}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * successNum : 1
         * successLampDeviceList : [{"id":1620,"name":"1112","deviceTypeId":7,"num":"860374055870032","location":null,"isOnline":0,"creator":null,"createTime":"2021-02-05 11:03:08","lastOnlineTime":"2021-05-18 16:31:29","description":null,"lampPostId":1216,"ip":null,"port":null,"mac":null,"strategyId":null,"strategyHistoryId":null,"isDelete":null,"deviceState":1,"reserveOne":"1e5522e22b68490eacaaa96dc73dd03a","brightness":100,"loopNum":null,"locationControlNum":null}]
         * defaultNum : 0
         * defaultLampDeviceList : []
         */

        public int successNum;
        public int defaultNum;
        public List<SuccessLampDeviceListBean> successLampDeviceList;
        public List<?> defaultLampDeviceList;

        @Data
        public static class SuccessLampDeviceListBean implements Serializable {
            /**
             * id : 1620
             * name : 1112
             * deviceTypeId : 7
             * num : 860374055870032
             * location : null
             * isOnline : 0
             * creator : null
             * createTime : 2021-02-05 11:03:08
             * lastOnlineTime : 2021-05-18 16:31:29
             * description : null
             * lampPostId : 1216
             * ip : null
             * port : null
             * mac : null
             * strategyId : null
             * strategyHistoryId : null
             * isDelete : null
             * deviceState : 1
             * reserveOne : 1e5522e22b68490eacaaa96dc73dd03a
             * brightness : 100
             * loopNum : null
             * locationControlNum : null
             */

            public int id;
            public String name;
            public int deviceTypeId;
            public String num;
            public Object location;
            public int isOnline;
            public Object creator;
            public String createTime;
            public String lastOnlineTime;
            public Object description;
            public int lampPostId;
            public Object ip;
            public Object port;
            public Object mac;
            public Object strategyId;
            public Object strategyHistoryId;
            public Object isDelete;
            public int deviceState;
            public String reserveOne;
            public int brightness;
            public Object loopNum;
            public Object locationControlNum;
        }
    }
}
