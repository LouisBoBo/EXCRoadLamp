package com.exc.roadlamp.device.bean;

import java.util.List;

public class DeviceControlTypeBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"type":"SZ"},{"id":2,"type":"HT"},{"id":3,"type":"ZKZL"},{"id":4,"type":"EXC"}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1
     * type : SZ
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
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
