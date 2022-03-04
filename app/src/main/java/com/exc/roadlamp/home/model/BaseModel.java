package com.exc.roadlamp.home.model;

public class BaseModel {

    /**
     * code : 400
     * operate : failed
     * message : 您输入的原密码有误，请重新输入
     * data : null
     */

    private int code;
    private String operate;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
