package com.exc.roadlamp.work.model;

import java.util.List;

public class LightStrategyTypeModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"name":"普通方案","presetBrightState":0,"presetBrightness":0},{"id":2,"name":"周末方案","presetBrightState":0,"presetBrightness":0},{"id":3,"name":"节日模式","presetBrightState":0,"presetBrightness":0},{"id":4,"name":"临时方案","presetBrightState":0,"presetBrightness":0}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1
     * name : 普通方案
     * presetBrightState : 0
     * presetBrightness : 0
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
        private int presetBrightState;
        private int presetBrightness;

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

        public int getPresetBrightState() {
            return presetBrightState;
        }

        public void setPresetBrightState(int presetBrightState) {
            this.presetBrightState = presetBrightState;
        }

        public int getPresetBrightness() {
            return presetBrightness;
        }

        public void setPresetBrightness(int presetBrightness) {
            this.presetBrightness = presetBrightness;
        }
    }
}
