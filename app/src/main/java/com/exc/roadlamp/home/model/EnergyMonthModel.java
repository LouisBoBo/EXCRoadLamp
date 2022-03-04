package com.exc.roadlamp.home.model;

import java.util.List;

public class EnergyMonthModel {


    /**
     * code : 0
     * data : {"dayEnergy":0,"dayGrowthRate":0,"energyCountDayList":[{"dateStr":"","energy":0}],"energyCountMonthList":[{"dateStr":"","energy":0}],"monthEnergy":0,"monthGrowthRate":0}
     * message :
     * operate :
     */

    private int code;
    /**
     * dayEnergy : 0
     * dayGrowthRate : 0
     * energyCountDayList : [{"dateStr":"","energy":0}]
     * energyCountMonthList : [{"dateStr":"","energy":0}]
     * monthEnergy : 0
     * monthGrowthRate : 0
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
        private int dayEnergy;
        private int dayGrowthRate;
        private int monthEnergy;
        private int monthGrowthRate;
        /**
         * dateStr :
         * energy : 0
         */

        private List<EnergyCountDayListBean> energyCountDayList;
        /**
         * dateStr :
         * energy : 0
         */

        private List<EnergyCountMonthListBean> energyCountMonthList;

        public int getDayEnergy() {
            return dayEnergy;
        }

        public void setDayEnergy(int dayEnergy) {
            this.dayEnergy = dayEnergy;
        }

        public int getDayGrowthRate() {
            return dayGrowthRate;
        }

        public void setDayGrowthRate(int dayGrowthRate) {
            this.dayGrowthRate = dayGrowthRate;
        }

        public int getMonthEnergy() {
            return monthEnergy;
        }

        public void setMonthEnergy(int monthEnergy) {
            this.monthEnergy = monthEnergy;
        }

        public int getMonthGrowthRate() {
            return monthGrowthRate;
        }

        public void setMonthGrowthRate(int monthGrowthRate) {
            this.monthGrowthRate = monthGrowthRate;
        }

        public List<EnergyCountDayListBean> getEnergyCountDayList() {
            return energyCountDayList;
        }

        public void setEnergyCountDayList(List<EnergyCountDayListBean> energyCountDayList) {
            this.energyCountDayList = energyCountDayList;
        }

        public List<EnergyCountMonthListBean> getEnergyCountMonthList() {
            return energyCountMonthList;
        }

        public void setEnergyCountMonthList(List<EnergyCountMonthListBean> energyCountMonthList) {
            this.energyCountMonthList = energyCountMonthList;
        }

        public static class EnergyCountDayListBean {
            private String dateStr;
            private int energy;

            public String getDateStr() {
                return dateStr;
            }

            public void setDateStr(String dateStr) {
                this.dateStr = dateStr;
            }

            public int getEnergy() {
                return energy;
            }

            public void setEnergy(int energy) {
                this.energy = energy;
            }
        }

        public static class EnergyCountMonthListBean {
            private String dateStr;
            private int energy;

            public String getDateStr() {
                return dateStr;
            }

            public void setDateStr(String dateStr) {
                this.dateStr = dateStr;
            }

            public int getEnergy() {
                return energy;
            }

            public void setEnergy(int energy) {
                this.energy = energy;
            }
        }
    }
}
