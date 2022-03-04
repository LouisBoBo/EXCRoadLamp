package com.exc.roadlamp.home.model;

public class OrderAnalysisModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"beingCount":0,"noCount":0,"overtimeCount":0,"allCount":0}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * beingCount : 0
     * noCount : 0
     * overtimeCount : 0
     * allCount : 0
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
        private int beingCount;
        private int noCount;
        private int overtimeCount;
        private int allCount;

        public int getBeingCount() {
            return beingCount;
        }

        public void setBeingCount(int beingCount) {
            this.beingCount = beingCount;
        }

        public int getNoCount() {
            return noCount;
        }

        public void setNoCount(int noCount) {
            this.noCount = noCount;
        }

        public int getOvertimeCount() {
            return overtimeCount;
        }

        public void setOvertimeCount(int overtimeCount) {
            this.overtimeCount = overtimeCount;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }
    }
}
