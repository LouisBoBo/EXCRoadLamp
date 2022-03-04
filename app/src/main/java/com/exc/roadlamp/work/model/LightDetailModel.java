package com.exc.roadlamp.work.model;

import java.util.List;

public class LightDetailModel {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"deviceId":1908,"deviceTypeId":7,"filed":"voltage","unit":"V","name":"电压","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"22.0","parameterId":46},{"deviceId":1908,"deviceTypeId":7,"filed":"electric_current","unit":"A","name":"电流","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0.0","parameterId":56},{"deviceId":1908,"deviceTypeId":7,"filed":"power","unit":"W","name":"功率","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0.0","parameterId":66},{"deviceId":1908,"deviceTypeId":7,"filed":"electric_energy","unit":"kwh","name":"能耗","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"81.0","parameterId":76},{"deviceId":1908,"deviceTypeId":7,"filed":"moduleTemperature","unit":"℃","name":"温度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"12.8","parameterId":86},{"deviceId":1908,"deviceTypeId":7,"filed":"lamp_time","unit":"h","name":"时长","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0.17","parameterId":96},{"deviceId":1908,"deviceTypeId":7,"filed":"lamp_a_time","unit":"h","name":"累计时长","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"611.05","parameterId":106},{"deviceId":1908,"deviceTypeId":7,"filed":"electric_version","unit":"","name":"软件版本","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"269","parameterId":146},{"deviceId":1908,"deviceTypeId":8,"filed":"voltage","unit":"V","name":"电压","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":47},{"deviceId":1908,"deviceTypeId":8,"filed":"electric_current","unit":"A","name":"电流","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":57},{"deviceId":1908,"deviceTypeId":8,"filed":"power","unit":"W","name":"功率","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":67},{"deviceId":1908,"deviceTypeId":8,"filed":"electric_energy","unit":"kwh","name":"能耗","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":77},{"deviceId":1908,"deviceTypeId":8,"filed":"moduleTemperature","unit":"℃","name":"温度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":87},{"deviceId":1908,"deviceTypeId":8,"filed":"lamp_time","unit":"h","name":"时长","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":97},{"deviceId":1908,"deviceTypeId":8,"filed":"lamp_a_time","unit":"h","name":"累计时长","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":107},{"deviceId":1908,"deviceTypeId":8,"filed":"electric_version","unit":"","name":"软件版本","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":147}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * deviceId : 1908
     * deviceTypeId : 7
     * filed : voltage
     * unit : V
     * name : 电压
     * isMust : 0
     * regexp :
     * icon :
     * showFlag : null
     * parameterValue : 22.0
     * parameterId : 46
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
        private int deviceId;
        private int deviceTypeId;
        private String filed;
        private String unit;
        private String name;
        private int isMust;
        private String regexp;
        private String icon;
        private Object showFlag;
        private String parameterValue;
        private int parameterId;

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public int getDeviceTypeId() {
            return deviceTypeId;
        }

        public void setDeviceTypeId(int deviceTypeId) {
            this.deviceTypeId = deviceTypeId;
        }

        public String getFiled() {
            return filed;
        }

        public void setFiled(String filed) {
            this.filed = filed;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsMust() {
            return isMust;
        }

        public void setIsMust(int isMust) {
            this.isMust = isMust;
        }

        public String getRegexp() {
            return regexp;
        }

        public void setRegexp(String regexp) {
            this.regexp = regexp;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getShowFlag() {
            return showFlag;
        }

        public void setShowFlag(Object showFlag) {
            this.showFlag = showFlag;
        }

        public String getParameterValue() {
            return parameterValue;
        }

        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public int getParameterId() {
            return parameterId;
        }

        public void setParameterId(int parameterId) {
            this.parameterId = parameterId;
        }
    }
}
