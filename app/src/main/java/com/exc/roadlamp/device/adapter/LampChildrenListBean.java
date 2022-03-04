package com.exc.roadlamp.device.adapter;

import java.util.List;

public class LampChildrenListBean {

    /**
     * id : 3137
     * partId : device3137
     * num : 008008008008008
     * name : 南山测试灯具
     * networkState : 0
     * lastOnlineTime : 2021-08-23 14:01:52
     * deviceType : 1
     * lampPostId : 2008
     * superId : 2008
     * superName : 南山测试-灯杆1
     * dlmRespDevice : {"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}
     */

    private int id;
    private String partId;
    private String num;
    private String name;
    private int networkState;
    private String lastOnlineTime;
    private int deviceType;
    private int lampPostId;
    private int superId;
    private String superName;
    private boolean show;
    private boolean select;
    private int progress;
    private boolean checked;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * id : 3137
     * model : null
     * deviceTypeId : 7
     * factory : EXC1
     * name : 南山测试灯具
     * num : 008008008008008
     * isOnline : 0
     * deviceState : 0
     * brightness : 50
     * createTime : 2021-08-24 14:01:52
     * lastOnlineTime : 2021-08-23 14:01:52
     * lampPostId : 2008
     * lampPostName : 南山测试-灯杆1
     * slLampPost : null
     * lampPosition : 主路
     * lampPositionId : 1
     * strategyId : null
     * reserveOne : null
     * setDefense : 0
     * deviceType : null
     * maxCurrent : null
     * slRespSystemDeviceParameterVOS : [{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]
     */

    private DlmRespDeviceBean dlmRespDevice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNetworkState() {
        return networkState;
    }

    public void setNetworkState(int networkState) {
        this.networkState = networkState;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getLampPostId() {
        return lampPostId;
    }

    public void setLampPostId(int lampPostId) {
        this.lampPostId = lampPostId;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public DlmRespDeviceBean getDlmRespDevice() {
        return dlmRespDevice;
    }

    public void setDlmRespDevice(DlmRespDeviceBean dlmRespDevice) {
        this.dlmRespDevice = dlmRespDevice;
    }

    public static class DlmRespDeviceBean {
        private int id;
        private Object model;
        private int deviceTypeId;
        private String factory;
        private String name;
        private String num;
        private int isOnline;
        private int deviceState;
        private int brightness;
        private String createTime;
        private String lastOnlineTime;
        private int lampPostId;
        private String lampPostName;
        private Object slLampPost;
        private String lampPosition;
        private int lampPositionId;
        private Object strategyId;
        private Object reserveOne;
        private int setDefense;
        private Object deviceType;
        private Object maxCurrent;
        /**
         * deviceId : 3137
         * deviceTypeId : null
         * filed : brightness
         * unit : %
         * name : 亮度
         * isMust : 1
         * regexp :
         * icon :
         * showFlag : null
         * parameterValue : 50
         * parameterId : 26
         */

        private List<SlRespSystemDeviceParameterVOSBean> slRespSystemDeviceParameterVOS;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public int getDeviceTypeId() {
            return deviceTypeId;
        }

        public void setDeviceTypeId(int deviceTypeId) {
            this.deviceTypeId = deviceTypeId;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public int getDeviceState() {
            return deviceState;
        }

        public void setDeviceState(int deviceState) {
            this.deviceState = deviceState;
        }

        public int getBrightness() {
            return brightness;
        }

        public void setBrightness(int brightness) {
            this.brightness = brightness;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastOnlineTime() {
            return lastOnlineTime;
        }

        public void setLastOnlineTime(String lastOnlineTime) {
            this.lastOnlineTime = lastOnlineTime;
        }

        public int getLampPostId() {
            return lampPostId;
        }

        public void setLampPostId(int lampPostId) {
            this.lampPostId = lampPostId;
        }

        public String getLampPostName() {
            return lampPostName;
        }

        public void setLampPostName(String lampPostName) {
            this.lampPostName = lampPostName;
        }

        public Object getSlLampPost() {
            return slLampPost;
        }

        public void setSlLampPost(Object slLampPost) {
            this.slLampPost = slLampPost;
        }

        public String getLampPosition() {
            return lampPosition;
        }

        public void setLampPosition(String lampPosition) {
            this.lampPosition = lampPosition;
        }

        public int getLampPositionId() {
            return lampPositionId;
        }

        public void setLampPositionId(int lampPositionId) {
            this.lampPositionId = lampPositionId;
        }

        public Object getStrategyId() {
            return strategyId;
        }

        public void setStrategyId(Object strategyId) {
            this.strategyId = strategyId;
        }

        public Object getReserveOne() {
            return reserveOne;
        }

        public void setReserveOne(Object reserveOne) {
            this.reserveOne = reserveOne;
        }

        public int getSetDefense() {
            return setDefense;
        }

        public void setSetDefense(int setDefense) {
            this.setDefense = setDefense;
        }

        public Object getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Object deviceType) {
            this.deviceType = deviceType;
        }

        public Object getMaxCurrent() {
            return maxCurrent;
        }

        public void setMaxCurrent(Object maxCurrent) {
            this.maxCurrent = maxCurrent;
        }

        public List<SlRespSystemDeviceParameterVOSBean> getSlRespSystemDeviceParameterVOS() {
            return slRespSystemDeviceParameterVOS;
        }

        public void setSlRespSystemDeviceParameterVOS(List<SlRespSystemDeviceParameterVOSBean> slRespSystemDeviceParameterVOS) {
            this.slRespSystemDeviceParameterVOS = slRespSystemDeviceParameterVOS;
        }

        public static class SlRespSystemDeviceParameterVOSBean {
            private int deviceId;
            private Object deviceTypeId;
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

            public Object getDeviceTypeId() {
                return deviceTypeId;
            }

            public void setDeviceTypeId(Object deviceTypeId) {
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
}
