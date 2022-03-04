package com.exc.roadlamp.device.bean;

import java.util.List;

public class LampDeviceBean {

    /**
     * code : 200
     * operate : success
     * message :
     * data : [{"id":2008,"partId":"lampPost2008","name":"南山测试-灯杆1","lampPostNum":"ns101","lampPostModel":null,"lampPostLongitude":113.88,"lampPostLatitude":22.89,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-08-09 11:34:27","deviceNumber":2,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":3137,"partId":"device3137","num":"008008008008008","name":"南山测试灯具","networkState":0,"lastOnlineTime":"2021-08-23 14:01:52","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":58,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"58","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}},{"id":3153,"partId":"device3153","num":"868618054684725","name":"南山测试-灯杆12","networkState":0,"lastOnlineTime":"2021-08-29 18:21:27","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3153,"model":null,"deviceTypeId":467,"factory":null,"name":"南山测试-灯杆12","num":"868618054684725","isOnline":0,"deviceState":0,"brightness":null,"createTime":"2021-08-30 18:21:27","lastOnlineTime":"2021-08-29 18:21:27","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":null,"lampPositionId":null,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3153,"deviceTypeId":null,"filed":null,"unit":null,"name":null,"isMust":null,"regexp":null,"icon":null,"showFlag":null,"parameterValue":null,"parameterId":null}]}}]}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 2008
     * partId : lampPost2008
     * name : 南山测试-灯杆1
     * lampPostNum : ns101
     * lampPostModel : null
     * lampPostLongitude : 113.88
     * lampPostLatitude : 22.89
     * lampPostManufacturer : null
     * lampPostLocation : null
     * createTime : 2021-08-09 11:34:27
     * deviceNumber : 2
     * superId : 7
     * superName : 海王银河科技大厦
     * areaId : 12
     * areaName : 南山区
     * streetId : 8
     * streetName : 科技园
     * ids : 12,8,7
     * names : 南山区,科技园,海王银河科技大厦
     * childrenList : [{"id":3137,"partId":"device3137","num":"008008008008008","name":"南山测试灯具","networkState":0,"lastOnlineTime":"2021-08-23 14:01:52","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":58,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"58","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}},{"id":3153,"partId":"device3153","num":"868618054684725","name":"南山测试-灯杆12","networkState":0,"lastOnlineTime":"2021-08-29 18:21:27","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3153,"model":null,"deviceTypeId":467,"factory":null,"name":"南山测试-灯杆12","num":"868618054684725","isOnline":0,"deviceState":0,"brightness":null,"createTime":"2021-08-30 18:21:27","lastOnlineTime":"2021-08-29 18:21:27","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":null,"lampPositionId":null,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3153,"deviceTypeId":null,"filed":null,"unit":null,"name":null,"isMust":null,"regexp":null,"icon":null,"showFlag":null,"parameterValue":null,"parameterId":null}]}}]
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
        private String partId;
        private String name;
        private String lampPostNum;
        private Object lampPostModel;
        private double lampPostLongitude;
        private double lampPostLatitude;
        private Object lampPostManufacturer;
        private Object lampPostLocation;
        private String createTime;
        private int deviceNumber;
        private int superId;
        private String superName;
        private int areaId;
        private String areaName;
        private int streetId;
        private String streetName;
        private String ids;
        private String names;
        private List<LampVideoBean.DataBean> lampvideobeans;

        public List<LampVideoBean.DataBean> getLampvideobeans() {
            return lampvideobeans;
        }

        public void setLampvideobeans(List<LampVideoBean.DataBean> lampvideobeans) {
            this.lampvideobeans = lampvideobeans;
        }

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
         * dlmRespDevice : {"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":58,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"maxCurrent":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"58","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}
         */

        private List<ChildrenListBean> childrenList;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLampPostNum() {
            return lampPostNum;
        }

        public void setLampPostNum(String lampPostNum) {
            this.lampPostNum = lampPostNum;
        }

        public Object getLampPostModel() {
            return lampPostModel;
        }

        public void setLampPostModel(Object lampPostModel) {
            this.lampPostModel = lampPostModel;
        }

        public double getLampPostLongitude() {
            return lampPostLongitude;
        }

        public void setLampPostLongitude(double lampPostLongitude) {
            this.lampPostLongitude = lampPostLongitude;
        }

        public double getLampPostLatitude() {
            return lampPostLatitude;
        }

        public void setLampPostLatitude(double lampPostLatitude) {
            this.lampPostLatitude = lampPostLatitude;
        }

        public Object getLampPostManufacturer() {
            return lampPostManufacturer;
        }

        public void setLampPostManufacturer(Object lampPostManufacturer) {
            this.lampPostManufacturer = lampPostManufacturer;
        }

        public Object getLampPostLocation() {
            return lampPostLocation;
        }

        public void setLampPostLocation(Object lampPostLocation) {
            this.lampPostLocation = lampPostLocation;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(int deviceNumber) {
            this.deviceNumber = deviceNumber;
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

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getStreetId() {
            return streetId;
        }

        public void setStreetId(int streetId) {
            this.streetId = streetId;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public List<ChildrenListBean> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<ChildrenListBean> childrenList) {
            this.childrenList = childrenList;
        }

        public static class ChildrenListBean {
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
            private boolean isShow;
            private boolean isSelect;
            private boolean checked;
            private List<String> select_colors;
            private List<Integer> select_colorids;

            public List<Integer> getSelect_colorids() {
                return select_colorids;
            }

            public void setSelect_colorids(List<Integer> select_colorids) {
                this.select_colorids = select_colorids;
            }

            public List<String> getSelect_colors() {
                return select_colors;
            }

            public void setSelect_colors(List<String> select_colors) {
                this.select_colors = select_colors;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            private int progress;


            public int getProgress() {
                return progress;
            }

            public void setProgress(int progress) {
                this.progress = progress;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
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
             * brightness : 58
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
             * slRespSystemDeviceParameterVOS : [{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"58","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]
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
                 * parameterValue : 58
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
    }
}
