package com.exc.roadlamp.work.model;

import java.util.List;

public class LightStrategyModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":267,"name":"中科101","strategyTypeId":1,"strategyTypeName":"普通方案","startDate":null,"endDate":null,"description":null,"creator":251,"creatorName":"林工1","idSynchro":null,"deviceTypeIdList":null,"createTime":"2021-12-13 17:07:20","slRespStrategyActionVOList":null}],"total":34,"size":1,"current":1,"searchCount":true,"pages":34}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":267,"name":"中科101","strategyTypeId":1,"strategyTypeName":"普通方案","startDate":null,"endDate":null,"description":null,"creator":251,"creatorName":"林工1","idSynchro":null,"deviceTypeIdList":null,"createTime":"2021-12-13 17:07:20","slRespStrategyActionVOList":null}]
     * total : 34
     * size : 1
     * current : 1
     * searchCount : true
     * pages : 34
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
         * id : 267
         * name : 中科101
         * strategyTypeId : 1
         * strategyTypeName : 普通方案
         * startDate : null
         * endDate : null
         * description : null
         * creator : 251
         * creatorName : 林工1
         * idSynchro : null
         * deviceTypeIdList : null
         * createTime : 2021-12-13 17:07:20
         * slRespStrategyActionVOList : null
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
            private String name;
            private int strategyTypeId;
            private String strategyTypeName;
            private Object startDate;
            private Object endDate;
            private Object description;
            private int creator;
            private String creatorName;
            private Object idSynchro;
            private Object deviceTypeIdList;
            private String createTime;
            private Object slRespStrategyActionVOList;

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

            public int getStrategyTypeId() {
                return strategyTypeId;
            }

            public void setStrategyTypeId(int strategyTypeId) {
                this.strategyTypeId = strategyTypeId;
            }

            public String getStrategyTypeName() {
                return strategyTypeName;
            }

            public void setStrategyTypeName(String strategyTypeName) {
                this.strategyTypeName = strategyTypeName;
            }

            public Object getStartDate() {
                return startDate;
            }

            public void setStartDate(Object startDate) {
                this.startDate = startDate;
            }

            public Object getEndDate() {
                return endDate;
            }

            public void setEndDate(Object endDate) {
                this.endDate = endDate;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public int getCreator() {
                return creator;
            }

            public void setCreator(int creator) {
                this.creator = creator;
            }

            public String getCreatorName() {
                return creatorName;
            }

            public void setCreatorName(String creatorName) {
                this.creatorName = creatorName;
            }

            public Object getIdSynchro() {
                return idSynchro;
            }

            public void setIdSynchro(Object idSynchro) {
                this.idSynchro = idSynchro;
            }

            public Object getDeviceTypeIdList() {
                return deviceTypeIdList;
            }

            public void setDeviceTypeIdList(Object deviceTypeIdList) {
                this.deviceTypeIdList = deviceTypeIdList;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getSlRespStrategyActionVOList() {
                return slRespStrategyActionVOList;
            }

            public void setSlRespStrategyActionVOList(Object slRespStrategyActionVOList) {
                this.slRespStrategyActionVOList = slRespStrategyActionVOList;
            }
        }
    }
}
