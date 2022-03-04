package com.exc.roadlamp.work.model;

import java.util.List;

public class AlarmNewsModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":988,"content":"井盖J09K4J","addr":null,"status":null,"createTime":"2021-06-10 17:16:57","disposeTime":null,"durationTime":null,"disposeCompleteTime":null,"currentTime":null,"typeId":null,"typeName":null,"lampPostId":null,"lampPostName":"无","serviceName":"故障告警","serviceId":1,"haveRead":0,"deviceName":"无"}],"total":1236,"size":1,"current":1,"searchCount":true,"pages":1236}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":988,"content":"井盖J09K4J","addr":null,"status":null,"createTime":"2021-06-10 17:16:57","disposeTime":null,"durationTime":null,"disposeCompleteTime":null,"currentTime":null,"typeId":null,"typeName":null,"lampPostId":null,"lampPostName":"无","serviceName":"故障告警","serviceId":1,"haveRead":0,"deviceName":"无"}]
     * total : 1236
     * size : 1
     * current : 1
     * searchCount : true
     * pages : 1236
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
         * id : 988
         * content : 井盖J09K4J
         * addr : null
         * status : null
         * createTime : 2021-06-10 17:16:57
         * disposeTime : null
         * durationTime : null
         * disposeCompleteTime : null
         * currentTime : null
         * typeId : null
         * typeName : null
         * lampPostId : null
         * lampPostName : 无
         * serviceName : 故障告警
         * serviceId : 1
         * haveRead : 0
         * deviceName : 无
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
            private String content;
            private Object addr;
            private Object status;
            private String createTime;
            private Object disposeTime;
            private Object durationTime;
            private Object disposeCompleteTime;
            private Object currentTime;
            private Object typeId;
            private Object typeName;
            private Object lampPostId;
            private String lampPostName;
            private String serviceName;
            private int serviceId;
            private int haveRead;
            private String deviceName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getAddr() {
                return addr;
            }

            public void setAddr(Object addr) {
                this.addr = addr;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
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

            public Object getDurationTime() {
                return durationTime;
            }

            public void setDurationTime(Object durationTime) {
                this.durationTime = durationTime;
            }

            public Object getDisposeCompleteTime() {
                return disposeCompleteTime;
            }

            public void setDisposeCompleteTime(Object disposeCompleteTime) {
                this.disposeCompleteTime = disposeCompleteTime;
            }

            public Object getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(Object currentTime) {
                this.currentTime = currentTime;
            }

            public Object getTypeId() {
                return typeId;
            }

            public void setTypeId(Object typeId) {
                this.typeId = typeId;
            }

            public Object getTypeName() {
                return typeName;
            }

            public void setTypeName(Object typeName) {
                this.typeName = typeName;
            }

            public Object getLampPostId() {
                return lampPostId;
            }

            public void setLampPostId(Object lampPostId) {
                this.lampPostId = lampPostId;
            }

            public String getLampPostName() {
                return lampPostName;
            }

            public void setLampPostName(String lampPostName) {
                this.lampPostName = lampPostName;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }

            public int getHaveRead() {
                return haveRead;
            }

            public void setHaveRead(int haveRead) {
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
