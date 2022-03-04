package com.exc.roadlamp.work.model;

import java.util.List;

public class ActionOptionModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"name":"日落之前"},{"id":2,"name":"日落之后"},{"id":3,"name":"日出之前"},{"id":4,"name":"日出之后"}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1
     * name : 日落之前
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
        private int mutexGroup;

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

        public int getMutexGroup() {
            return mutexGroup;
        }

        public void setMutexGroup(int mutexGroup) {
            this.mutexGroup = mutexGroup;
        }
    }
}
