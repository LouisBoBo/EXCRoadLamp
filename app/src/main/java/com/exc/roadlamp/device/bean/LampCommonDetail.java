package com.exc.roadlamp.device.bean;

public class LampCommonDetail {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"id":1996,"name":"太阳能路灯-庭院灯","num":"太阳能路灯-庭院灯","model":null,"longitude":113,"latitude":22,"manufacturer":null,"location":null,"groupId":null,"siteId":69,"defaultCameraId":null,"createTime":"2021-08-04 15:04:52"}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1996
     * name : 太阳能路灯-庭院灯
     * num : 太阳能路灯-庭院灯
     * model : null
     * longitude : 113.0
     * latitude : 22.0
     * manufacturer : null
     * location : null
     * groupId : null
     * siteId : 69
     * defaultCameraId : null
     * createTime : 2021-08-04 15:04:52
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
        private int id;
        private String name;
        private String num;
        private Object model;
        private double longitude;
        private double latitude;
        private Object manufacturer;
        private Object location;
        private Object groupId;
        private int siteId;
        private Object defaultCameraId;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Object getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(Object manufacturer) {
            this.manufacturer = manufacturer;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getGroupId() {
            return groupId;
        }

        public void setGroupId(Object groupId) {
            this.groupId = groupId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public Object getDefaultCameraId() {
            return defaultCameraId;
        }

        public void setDefaultCameraId(Object defaultCameraId) {
            this.defaultCameraId = defaultCameraId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
