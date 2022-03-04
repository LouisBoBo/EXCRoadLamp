package com.exc.roadlamp.device.bean;

import java.util.List;

public class LampVideoBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"name":"红"},{"id":2,"name":"绿"},{"id":3,"name":"蓝"},{"id":4,"name":"白"},{"id":5,"name":"红渐变"},{"id":6,"name":"绿渐变"},{"id":7,"name":"蓝渐变"},{"id":8,"name":"白渐变"}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1
     * name : 红
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String name;

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
    }
}
