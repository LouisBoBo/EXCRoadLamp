package com.exc.roadlamp.home.model;

import java.util.List;

public class AlarmCountModel {

    /**
     * code : 0
     * data : {"alarmCountDayList":[{"count":0,"countDate":""}],"alarmCountMonthList":[{"count":0,"countDate":""}],"dayAlarm":0,"dayGrowthRate":0,"monthAlarm":0,"monthGrowthRate":0}
     * message :
     * operate :
     */

    private int code;
    /**
     * alarmCountDayList : [{"count":0,"countDate":""}]
     * alarmCountMonthList : [{"count":0,"countDate":""}]
     * dayAlarm : 0
     * dayGrowthRate : 0
     * monthAlarm : 0
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
        private int dayAlarm;
        private int dayGrowthRate;
        private int monthAlarm;
        private int monthGrowthRate;
        /**
         * count : 0
         * countDate :
         */

        private List<AlarmCountDayListBean> alarmCountDayList;
        /**
         * count : 0
         * countDate :
         */

        private List<AlarmCountMonthListBean> alarmCountMonthList;

        public int getDayAlarm() {
            return dayAlarm;
        }

        public void setDayAlarm(int dayAlarm) {
            this.dayAlarm = dayAlarm;
        }

        public int getDayGrowthRate() {
            return dayGrowthRate;
        }

        public void setDayGrowthRate(int dayGrowthRate) {
            this.dayGrowthRate = dayGrowthRate;
        }

        public int getMonthAlarm() {
            return monthAlarm;
        }

        public void setMonthAlarm(int monthAlarm) {
            this.monthAlarm = monthAlarm;
        }

        public int getMonthGrowthRate() {
            return monthGrowthRate;
        }

        public void setMonthGrowthRate(int monthGrowthRate) {
            this.monthGrowthRate = monthGrowthRate;
        }

        public List<AlarmCountDayListBean> getAlarmCountDayList() {
            return alarmCountDayList;
        }

        public void setAlarmCountDayList(List<AlarmCountDayListBean> alarmCountDayList) {
            this.alarmCountDayList = alarmCountDayList;
        }

        public List<AlarmCountMonthListBean> getAlarmCountMonthList() {
            return alarmCountMonthList;
        }

        public void setAlarmCountMonthList(List<AlarmCountMonthListBean> alarmCountMonthList) {
            this.alarmCountMonthList = alarmCountMonthList;
        }

        public static class AlarmCountDayListBean {
            private int count;
            private String countDate;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getCountDate() {
                return countDate;
            }

            public void setCountDate(String countDate) {
                this.countDate = countDate;
            }
        }

        public static class AlarmCountMonthListBean {
            private int count;
            private String countDate;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getCountDate() {
                return countDate;
            }

            public void setCountDate(String countDate) {
                this.countDate = countDate;
            }
        }
    }
}
