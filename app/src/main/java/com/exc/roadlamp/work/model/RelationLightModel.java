package com.exc.roadlamp.work.model;

import java.util.List;

public class RelationLightModel {

    /**
     * code : 200
     * operate : success
     * message : 查询成功
     * data : [{"lampPostName":"PLC-灯杆6","lampPostId":2196,"systemDeviceList":[{"systemDeviceId":12,"systemDeviceName":"PLC灯具-B66","chosen":0,"chosenByOther":0},{"systemDeviceId":31,"systemDeviceName":"PLC灯具-B75","chosen":0,"chosenByOther":0},{"systemDeviceId":105,"systemDeviceName":"4C5A00034800","chosen":0,"chosenByOther":1},{"systemDeviceId":121,"systemDeviceName":"4C5A00037937","chosen":0,"chosenByOther":1},{"systemDeviceId":123,"systemDeviceName":"4C5A00037946","chosen":0,"chosenByOther":1},{"systemDeviceId":174,"systemDeviceName":"4C5A000407AC","chosen":0,"chosenByOther":1},{"systemDeviceId":1847,"systemDeviceName":"F52F04005A4C","chosen":0,"chosenByOther":1}]},{"lampPostName":"二代PLC-灯杆7","lampPostId":2199,"systemDeviceList":[{"systemDeviceId":283,"systemDeviceName":"OTA.1","chosen":0,"chosenByOther":0},{"systemDeviceId":286,"systemDeviceName":"OTA.4","chosen":0,"chosenByOther":0},{"systemDeviceId":1890,"systemDeviceName":"plc灯具-948","chosen":0,"chosenByOther":0}]},{"lampPostName":"研发-PLC双模","lampPostId":2310,"systemDeviceList":[{"systemDeviceId":1865,"systemDeviceName":"双灯22","chosen":0,"chosenByOther":0},{"systemDeviceId":1870,"systemDeviceName":"双灯2F","chosen":0,"chosenByOther":0},{"systemDeviceId":1871,"systemDeviceName":"双灯3A","chosen":0,"chosenByOther":0}]},{"lampPostName":"研发-PLC双灯","lampPostId":2316,"systemDeviceList":[{"systemDeviceId":1888,"systemDeviceName":"PLC-双灯1","chosen":0,"chosenByOther":0}]}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * lampPostName : PLC-灯杆6
     * lampPostId : 2196
     * systemDeviceList : [{"systemDeviceId":12,"systemDeviceName":"PLC灯具-B66","chosen":0,"chosenByOther":0},{"systemDeviceId":31,"systemDeviceName":"PLC灯具-B75","chosen":0,"chosenByOther":0},{"systemDeviceId":105,"systemDeviceName":"4C5A00034800","chosen":0,"chosenByOther":1},{"systemDeviceId":121,"systemDeviceName":"4C5A00037937","chosen":0,"chosenByOther":1},{"systemDeviceId":123,"systemDeviceName":"4C5A00037946","chosen":0,"chosenByOther":1},{"systemDeviceId":174,"systemDeviceName":"4C5A000407AC","chosen":0,"chosenByOther":1},{"systemDeviceId":1847,"systemDeviceName":"F52F04005A4C","chosen":0,"chosenByOther":1}]
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String lampPostName;
        private int lampPostId;
        /**
         * systemDeviceId : 12
         * systemDeviceName : PLC灯具-B66
         * chosen : 0
         * chosenByOther : 0
         */

        private List<SystemDeviceListBean> systemDeviceList;

        public String getLampPostName() {
            return lampPostName;
        }

        public void setLampPostName(String lampPostName) {
            this.lampPostName = lampPostName;
        }

        public int getLampPostId() {
            return lampPostId;
        }

        public void setLampPostId(int lampPostId) {
            this.lampPostId = lampPostId;
        }

        public List<SystemDeviceListBean> getSystemDeviceList() {
            return systemDeviceList;
        }

        public void setSystemDeviceList(List<SystemDeviceListBean> systemDeviceList) {
            this.systemDeviceList = systemDeviceList;
        }

        public static class SystemDeviceListBean {
            private int systemDeviceId;
            private String systemDeviceName;
            private int chosen;
            private int chosenByOther;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getSystemDeviceId() {
                return systemDeviceId;
            }

            public void setSystemDeviceId(int systemDeviceId) {
                this.systemDeviceId = systemDeviceId;
            }

            public String getSystemDeviceName() {
                return systemDeviceName;
            }

            public void setSystemDeviceName(String systemDeviceName) {
                this.systemDeviceName = systemDeviceName;
            }

            public int getChosen() {
                return chosen;
            }

            public void setChosen(int chosen) {
                this.chosen = chosen;
            }

            public int getChosenByOther() {
                return chosenByOther;
            }

            public void setChosenByOther(int chosenByOther) {
                this.chosenByOther = chosenByOther;
            }
        }
    }
}
