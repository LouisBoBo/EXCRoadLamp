package com.exc.roadlamp.work.model;

import java.util.List;

public class AddBingModel {

    /**
     * code : 200
     * operate : success
     * message : 添加成功
     * data : [1910,1911]
     */

    private int code;
    private String operate;
    private String message;
    private List<Integer> data;

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

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
