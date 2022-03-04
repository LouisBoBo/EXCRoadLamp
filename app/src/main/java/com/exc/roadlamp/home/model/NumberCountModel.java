package com.exc.roadlamp.home.model;

import java.util.List;

public class NumberCountModel {

    /**
     * code : 200
     * operate : success
     * message :
     * data : {"count":19,"device":[{"number":5,"onlineNumber":3,"deviceType":4,"onlineRate":60},{"number":2,"onlineNumber":1,"deviceType":13,"onlineRate":50},{"number":23,"onlineNumber":8,"deviceType":1,"onlineRate":34},{"number":0,"onlineNumber":0,"deviceType":2,"onlineRate":0},{"number":2,"onlineNumber":0,"deviceType":3,"onlineRate":0},{"number":5,"onlineNumber":0,"deviceType":5,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":6,"onlineRate":0},{"number":1,"onlineNumber":0,"deviceType":7,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":12,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":14,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":15,"onlineRate":0}]}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * count : 19
     * device : [{"number":5,"onlineNumber":3,"deviceType":4,"onlineRate":60},{"number":2,"onlineNumber":1,"deviceType":13,"onlineRate":50},{"number":23,"onlineNumber":8,"deviceType":1,"onlineRate":34},{"number":0,"onlineNumber":0,"deviceType":2,"onlineRate":0},{"number":2,"onlineNumber":0,"deviceType":3,"onlineRate":0},{"number":5,"onlineNumber":0,"deviceType":5,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":6,"onlineRate":0},{"number":1,"onlineNumber":0,"deviceType":7,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":12,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":14,"onlineRate":0},{"number":0,"onlineNumber":0,"deviceType":15,"onlineRate":0}]
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int count;
        /**
         * number : 5
         * onlineNumber : 3
         * deviceType : 4
         * onlineRate : 60
         */

        private List<DeviceBean> device;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DeviceBean> getDevice() {
            return device;
        }

        public void setDevice(List<DeviceBean> device) {
            this.device = device;
        }

        public static class DeviceBean {
            private int number;
            private int onlineNumber;
            private int deviceType;
            private int onlineRate;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getOnlineNumber() {
                return onlineNumber;
            }

            public void setOnlineNumber(int onlineNumber) {
                this.onlineNumber = onlineNumber;
            }

            public int getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(int deviceType) {
                this.deviceType = deviceType;
            }

            public int getOnlineRate() {
                return onlineRate;
            }

            public void setOnlineRate(int onlineRate) {
                this.onlineRate = onlineRate;
            }
        }
    }
}
