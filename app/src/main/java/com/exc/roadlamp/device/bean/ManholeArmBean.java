package com.exc.roadlamp.device.bean;

import java.util.List;

public class ManholeArmBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":289252,"content":null,"addr":null,"status":1,"createTime":"2022-02-24 11:34:15","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289251,"content":null,"addr":null,"status":1,"createTime":"2022-02-23 19:22:23","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289250,"content":null,"addr":null,"status":1,"createTime":"2022-02-23 14:01:16","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289249,"content":null,"addr":null,"status":1,"createTime":"2022-02-22 22:04:47","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289248,"content":null,"addr":null,"status":1,"createTime":"2022-02-21 21:39:41","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289247,"content":null,"addr":null,"status":1,"createTime":"2022-02-17 11:15:23","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"}],"total":6,"size":10,"current":1,"searchCount":true,"pages":1}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":289252,"content":null,"addr":null,"status":1,"createTime":"2022-02-24 11:34:15","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289251,"content":null,"addr":null,"status":1,"createTime":"2022-02-23 19:22:23","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289250,"content":null,"addr":null,"status":1,"createTime":"2022-02-23 14:01:16","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289249,"content":null,"addr":null,"status":1,"createTime":"2022-02-22 22:04:47","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289248,"content":null,"addr":null,"status":1,"createTime":"2022-02-21 21:39:41","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"},{"id":289247,"content":null,"addr":null,"status":1,"createTime":"2022-02-17 11:15:23","disposeTime":null,"durationTime":"2022-02-24 15:10:25","disposeCompleteTime":null,"currentTime":"2022-02-24 15:10:25","typeId":87,"typeName":"plc掉电异常","lampPostId":null,"lampPostName":null,"serviceName":null,"serviceId":null,"haveRead":null,"deviceName":"软件测试PLC专用"}]
     * total : 6
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
         * id : 289252
         * content : null
         * addr : null
         * status : 1
         * createTime : 2022-02-24 11:34:15
         * disposeTime : null
         * durationTime : 2022-02-24 15:10:25
         * disposeCompleteTime : null
         * currentTime : 2022-02-24 15:10:25
         * typeId : 87
         * typeName : plc掉电异常
         * lampPostId : null
         * lampPostName : null
         * serviceName : null
         * serviceId : null
         * haveRead : null
         * deviceName : 软件测试PLC专用
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

        public static class RecordsBean {
            private int id;
            private Object content;
            private Object addr;
            private int status;
            private String createTime;
            private Object disposeTime;
            private String durationTime;
            private Object disposeCompleteTime;
            private String currentTime;
            private int typeId;
            private String typeName;
            private Object lampPostId;
            private Object lampPostName;
            private Object serviceName;
            private Object serviceId;
            private Object haveRead;
            private String deviceName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public Object getAddr() {
                return addr;
            }

            public void setAddr(Object addr) {
                this.addr = addr;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getDisposeTime() {
                return disposeTime;
            }

            public void setDisposeTime(Object disposeTime) {
                this.disposeTime = disposeTime;
            }

            public String getDurationTime() {
                return durationTime;
            }

            public void setDurationTime(String durationTime) {
                this.durationTime = durationTime;
            }

            public Object getDisposeCompleteTime() {
                return disposeCompleteTime;
            }

            public void setDisposeCompleteTime(Object disposeCompleteTime) {
                this.disposeCompleteTime = disposeCompleteTime;
            }

            public String getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(String currentTime) {
                this.currentTime = currentTime;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public Object getLampPostId() {
                return lampPostId;
            }

            public void setLampPostId(Object lampPostId) {
                this.lampPostId = lampPostId;
            }

            public Object getLampPostName() {
                return lampPostName;
            }

            public void setLampPostName(Object lampPostName) {
                this.lampPostName = lampPostName;
            }

            public Object getServiceName() {
                return serviceName;
            }

            public void setServiceName(Object serviceName) {
                this.serviceName = serviceName;
            }

            public Object getServiceId() {
                return serviceId;
            }

            public void setServiceId(Object serviceId) {
                this.serviceId = serviceId;
            }

            public Object getHaveRead() {
                return haveRead;
            }

            public void setHaveRead(Object haveRead) {
                this.haveRead = haveRead;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }
        }
    }
}
