package com.exc.roadlamp.home.model;

public class SolarModel {

    /**
     * code : 0
     * data : {"dayEnergyTotal":0,"monthEnergyTotal":0}
     * message :
     * operate :
     */

    private int code;
    /**
     * dayEnergyTotal : 0
     * monthEnergyTotal : 0
     */

    private DataBean data;
    private String message;
    private String operate;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public static class DataBean {
        private int dayEnergyTotal;
        private int monthEnergyTotal;

        public int getDayEnergyTotal() {
            return dayEnergyTotal;
        }

        public void setDayEnergyTotal(int dayEnergyTotal) {
            this.dayEnergyTotal = dayEnergyTotal;
        }

        public int getMonthEnergyTotal() {
            return monthEnergyTotal;
        }

        public void setMonthEnergyTotal(int monthEnergyTotal) {
            this.monthEnergyTotal = monthEnergyTotal;
        }
    }
}
