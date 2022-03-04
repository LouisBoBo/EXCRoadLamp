package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

public class ManholeList {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":1,"name":"前端测试","num":"888888888","longitude":"114.066112","latitude":"22.548515","limitUpper":20,"realData":0,"vibrationThreshold":888,"displacementThreshold":20,"deployDefence":1,"remainingCapacity":null,"signalIntensity":null,"reportingLimit":10,"locationSiteId":92,"locationSiteName":"前端街道1","locationStreetId":101,"locationStreetName":"前端区域1","locationAreaId":83,"locationAreaName":"前端测试","status":0,"deviceVersion":"1.0","uploadCycle":64800,"creator":1,"createTime":"2022-02-16 17:59:34","networkState":0,"lastOnlineTime":null,"location":null}],"total":1,"size":10,"current":1,"searchCount":true,"pages":1}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":1,"name":"前端测试","num":"888888888","longitude":"114.066112","latitude":"22.548515","limitUpper":20,"realData":0,"vibrationThreshold":888,"displacementThreshold":20,"deployDefence":1,"remainingCapacity":null,"signalIntensity":null,"reportingLimit":10,"locationSiteId":92,"locationSiteName":"前端街道1","locationStreetId":101,"locationStreetName":"前端区域1","locationAreaId":83,"locationAreaName":"前端测试","status":0,"deviceVersion":"1.0","uploadCycle":64800,"creator":1,"createTime":"2022-02-16 17:59:34","networkState":0,"lastOnlineTime":null,"location":null}]
     * total : 1
     * size : 10
     * current : 1
     * searchCount : true
     * pages : 1
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
        private int total;
        private int size;
        private int current;
        private boolean searchCount;
        private int pages;
        /**
         * id : 1
         * name : 前端测试
         * num : 888888888
         * longitude : 114.066112
         * latitude : 22.548515
         * limitUpper : 20
         * realData : 0
         * vibrationThreshold : 888
         * displacementThreshold : 20
         * deployDefence : 1
         * remainingCapacity : null
         * signalIntensity : null
         * reportingLimit : 10
         * locationSiteId : 92
         * locationSiteName : 前端街道1
         * locationStreetId : 101
         * locationStreetName : 前端区域1
         * locationAreaId : 83
         * locationAreaName : 前端测试
         * status : 0
         * deviceVersion : 1.0
         * uploadCycle : 64800
         * creator : 1
         * createTime : 2022-02-16 17:59:34
         * networkState : 0
         * lastOnlineTime : null
         * location : null
         */

        private List<RecordsBean> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean implements Serializable {
            private int id;
            private String name;
            private String num;
            private double longitude;
            private double latitude;
            private int limitUpper;
            private int realData;
            private int vibrationThreshold;
            private int displacementThreshold;
            private int deployDefence;
            private Object remainingCapacity;
            private Object signalIntensity;
            private int reportingLimit;
            private int locationSiteId;
            private String locationSiteName;
            private int locationStreetId;
            private String locationStreetName;
            private int locationAreaId;
            private String locationAreaName;
            private int status;
            private String deviceVersion;
            private int uploadCycle;
            private int creator;
            private String createTime;
            private int networkState;
            private Object lastOnlineTime;
            private Object location;

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

            public int getLimitUpper() {
                return limitUpper;
            }

            public void setLimitUpper(int limitUpper) {
                this.limitUpper = limitUpper;
            }

            public int getRealData() {
                return realData;
            }

            public void setRealData(int realData) {
                this.realData = realData;
            }

            public int getVibrationThreshold() {
                return vibrationThreshold;
            }

            public void setVibrationThreshold(int vibrationThreshold) {
                this.vibrationThreshold = vibrationThreshold;
            }

            public int getDisplacementThreshold() {
                return displacementThreshold;
            }

            public void setDisplacementThreshold(int displacementThreshold) {
                this.displacementThreshold = displacementThreshold;
            }

            public int getDeployDefence() {
                return deployDefence;
            }

            public void setDeployDefence(int deployDefence) {
                this.deployDefence = deployDefence;
            }

            public Object getRemainingCapacity() {
                return remainingCapacity;
            }

            public void setRemainingCapacity(Object remainingCapacity) {
                this.remainingCapacity = remainingCapacity;
            }

            public Object getSignalIntensity() {
                return signalIntensity;
            }

            public void setSignalIntensity(Object signalIntensity) {
                this.signalIntensity = signalIntensity;
            }

            public int getReportingLimit() {
                return reportingLimit;
            }

            public void setReportingLimit(int reportingLimit) {
                this.reportingLimit = reportingLimit;
            }

            public int getLocationSiteId() {
                return locationSiteId;
            }

            public void setLocationSiteId(int locationSiteId) {
                this.locationSiteId = locationSiteId;
            }

            public String getLocationSiteName() {
                return locationSiteName;
            }

            public void setLocationSiteName(String locationSiteName) {
                this.locationSiteName = locationSiteName;
            }

            public int getLocationStreetId() {
                return locationStreetId;
            }

            public void setLocationStreetId(int locationStreetId) {
                this.locationStreetId = locationStreetId;
            }

            public String getLocationStreetName() {
                return locationStreetName;
            }

            public void setLocationStreetName(String locationStreetName) {
                this.locationStreetName = locationStreetName;
            }

            public int getLocationAreaId() {
                return locationAreaId;
            }

            public void setLocationAreaId(int locationAreaId) {
                this.locationAreaId = locationAreaId;
            }

            public String getLocationAreaName() {
                return locationAreaName;
            }

            public void setLocationAreaName(String locationAreaName) {
                this.locationAreaName = locationAreaName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDeviceVersion() {
                return deviceVersion;
            }

            public void setDeviceVersion(String deviceVersion) {
                this.deviceVersion = deviceVersion;
            }

            public int getUploadCycle() {
                return uploadCycle;
            }

            public void setUploadCycle(int uploadCycle) {
                this.uploadCycle = uploadCycle;
            }

            public int getCreator() {
                return creator;
            }

            public void setCreator(int creator) {
                this.creator = creator;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getNetworkState() {
                return networkState;
            }

            public void setNetworkState(int networkState) {
                this.networkState = networkState;
            }

            public Object getLastOnlineTime() {
                return lastOnlineTime;
            }

            public void setLastOnlineTime(Object lastOnlineTime) {
                this.lastOnlineTime = lastOnlineTime;
            }

            public Object getLocation() {
                return location;
            }

            public void setLocation(Object location) {
                this.location = location;
            }
        }
    }
}
