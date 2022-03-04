package com.exc.roadlamp.home.model;

import java.util.List;

public class WeekLightModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"timeList":["2021-06-22","2021-06-23","2021-06-24","2021-06-25","2021-06-26","2021-06-27","2021-06-28"],"energyList":[4.3,0.2,6.6,6.7,0,0,5.3],"lightingRateList":[60,20,66.67,64.29,0,0,57.14]}
     */

    private int code;
    private String operate;
    private String message;
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
        private List<String> timeList;
        private List<Double> energyList;
        private List<Double> lightingRateList;

        public List<String> getTimeList() {
            return timeList;
        }

        public void setTimeList(List<String> timeList) {
            this.timeList = timeList;
        }

        public List<Double> getEnergyList() {
            return energyList;
        }

        public void setEnergyList(List<Double> energyList) {
            this.energyList = energyList;
        }

        public List<Double> getLightingRateList() {
            return lightingRateList;
        }

        public void setLightingRateList(List<Double> lightingRateList) {
            this.lightingRateList = lightingRateList;
        }
    }
}
