package com.exc.roadlamp.work.model;

import java.util.List;

public class StrategyActionModel {

    /**
     * name : 测试场景
     * id : 1
     * dlmReqSceneActionVOList : [{"executionTime":"11:3","isOpen":1,"endDate":"2021-12-25","cycleTypes":["3","7"],"startDate":"2021-12-24"}]
     */
    private int strategytype;
    private String name;
    private int id;
    /**
     * executionTime : 11:3
     * isOpen : 1
     * endDate : 2021-12-25
     * cycleTypes : ["3","7"]
     * startDate : 2021-12-24
     */

    private List<DlmReqSceneActionVOListBean> dlmReqSceneActionVOList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrategytype() {
        return strategytype;
    }

    public void setStrategytype(int strategytype) {
        this.strategytype = strategytype;
    }

    public List<DlmReqSceneActionVOListBean> getDlmReqSceneActionVOList() {
        return dlmReqSceneActionVOList;
    }

    public void setDlmReqSceneActionVOList(List<DlmReqSceneActionVOListBean> dlmReqSceneActionVOList) {
        this.dlmReqSceneActionVOList = dlmReqSceneActionVOList;
    }

    public static class DlmReqSceneActionVOListBean {
        private String executionTime;
        private int isOpen;
        private int id;
        private int lightModeId;
        private String action;
        private String endDate;
        private String startDate;
        private int progress;
        private List<String> cycleTypes;

        public int getLightModeId() {
            return lightModeId;
        }

        public void setLightModeId(int lightModeId) {
            this.lightModeId = lightModeId;
        }

        public String getExecutionTime() {
            return executionTime;
        }

        public void setExecutionTime(String executionTime) {
            this.executionTime = executionTime;
        }

        public int getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public List<String> getCycleTypes() {
            return cycleTypes;
        }

        public void setCycleTypes(List<String> cycleTypes) {
            this.cycleTypes = cycleTypes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }
}
