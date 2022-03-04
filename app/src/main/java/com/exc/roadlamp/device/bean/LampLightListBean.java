package com.exc.roadlamp.device.bean;

import java.util.List;

public class LampLightListBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"id":1048,"model":"EXC-TL1-P110E-0","deviceTypeId":23,"factory":"EXC1","name":"PLC_2978_单路_主路","num":"866071058582978","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-11-11 17:17:53","lastOnlineTime":"2021-11-10 17:17:53","lampPostId":2213,"lampPostName":"福田灯杆2","slLampPost":{"id":2213,"name":"福田灯杆2","num":"6002","model":null,"longitude":113.898,"latitude":22.652,"manufacturer":null,"location":null,"groupId":4,"siteId":84,"defaultCameraId":null,"createTime":"2021-10-14 16:00:39","singleLampParamReqVOs":null},"lampPosition":"主路","lampPositionId":1,"strategyId":95,"reserveOne":null,"setDefense":null,"deviceType":0,"maxCurrent":null,"deviceName":null,"playingFileNum":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1048,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":0,"regexp":null,"icon":null,"showFlag":null,"parameterValue":"50","parameterId":271},{"deviceId":1048,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":274},{"deviceId":1048,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":275}],"locationControlId":266}],"total":17,"size":1,"current":1,"searchCount":true,"pages":17}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":1048,"model":"EXC-TL1-P110E-0","deviceTypeId":23,"factory":"EXC1","name":"PLC_2978_单路_主路","num":"866071058582978","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-11-11 17:17:53","lastOnlineTime":"2021-11-10 17:17:53","lampPostId":2213,"lampPostName":"福田灯杆2","slLampPost":{"id":2213,"name":"福田灯杆2","num":"6002","model":null,"longitude":113.898,"latitude":22.652,"manufacturer":null,"location":null,"groupId":4,"siteId":84,"defaultCameraId":null,"createTime":"2021-10-14 16:00:39","singleLampParamReqVOs":null},"lampPosition":"主路","lampPositionId":1,"strategyId":95,"reserveOne":null,"setDefense":null,"deviceType":0,"maxCurrent":null,"deviceName":null,"playingFileNum":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1048,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":0,"regexp":null,"icon":null,"showFlag":null,"parameterValue":"50","parameterId":271},{"deviceId":1048,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":274},{"deviceId":1048,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":275}],"locationControlId":266}]
     * total : 17
     * size : 1
     * current : 1
     * searchCount : true
     * pages : 17
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
        private int total;
        private int size;
        private int current;
        private boolean searchCount;
        private int pages;
        /**
         * id : 1048
         * model : EXC-TL1-P110E-0
         * deviceTypeId : 23
         * factory : EXC1
         * name : PLC_2978_单路_主路
         * num : 866071058582978
         * isOnline : 0
         * deviceState : 0
         * brightness : 50
         * createTime : 2021-11-11 17:17:53
         * lastOnlineTime : 2021-11-10 17:17:53
         * lampPostId : 2213
         * lampPostName : 福田灯杆2
         * slLampPost : {"id":2213,"name":"福田灯杆2","num":"6002","model":null,"longitude":113.898,"latitude":22.652,"manufacturer":null,"location":null,"groupId":4,"siteId":84,"defaultCameraId":null,"createTime":"2021-10-14 16:00:39","singleLampParamReqVOs":null}
         * lampPosition : 主路
         * lampPositionId : 1
         * strategyId : 95
         * reserveOne : null
         * setDefense : null
         * deviceType : 0
         * maxCurrent : null
         * deviceName : null
         * playingFileNum : null
         * slRespSystemDeviceParameterVOS : [{"deviceId":1048,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":0,"regexp":null,"icon":null,"showFlag":null,"parameterValue":"50","parameterId":271},{"deviceId":1048,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":274},{"deviceId":1048,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":275}]
         * locationControlId : 266
         */

        private List<RecordsBean> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            private int id;
            private String model;
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


            /**
             * id : 2213
             * name : 福田灯杆2
             * num : 6002
             * model : null
             * longitude : 113.898
             * latitude : 22.652
             * manufacturer : null
             * location : null
             * groupId : 4
             * siteId : 84
             * defaultCameraId : null
             * createTime : 2021-10-14 16:00:39
             * singleLampParamReqVOs : null
             */

            private SlLampPostBean slLampPost;
            private String lampPosition;
            private int lampPositionId;
            private int strategyId;
            private Object reserveOne;
            private Object setDefense;
            private int deviceType;
            private Object maxCurrent;
            private Object deviceName;
            private Object playingFileNum;
            private int locationControlId;

            /**
             * deviceId : 1048
             * deviceTypeId : null
             * filed : brightness
             * unit : %
             * name : 亮度
             * isMust : 0
             * regexp : null
             * icon : null
             * showFlag : null
             * parameterValue : 50
             * parameterId : 271
             */

            private List<SlRespSystemDeviceParameterVOSBean> slRespSystemDeviceParameterVOS;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
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

            public SlLampPostBean getSlLampPost() {
                return slLampPost;
            }

            public void setSlLampPost(SlLampPostBean slLampPost) {
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

            public int getStrategyId() {
                return strategyId;
            }

            public void setStrategyId(int strategyId) {
                this.strategyId = strategyId;
            }

            public Object getReserveOne() {
                return reserveOne;
            }

            public void setReserveOne(Object reserveOne) {
                this.reserveOne = reserveOne;
            }

            public Object getSetDefense() {
                return setDefense;
            }

            public void setSetDefense(Object setDefense) {
                this.setDefense = setDefense;
            }

            public int getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(int deviceType) {
                this.deviceType = deviceType;
            }

            public Object getMaxCurrent() {
                return maxCurrent;
            }

            public void setMaxCurrent(Object maxCurrent) {
                this.maxCurrent = maxCurrent;
            }

            public Object getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(Object deviceName) {
                this.deviceName = deviceName;
            }

            public Object getPlayingFileNum() {
                return playingFileNum;
            }

            public void setPlayingFileNum(Object playingFileNum) {
                this.playingFileNum = playingFileNum;
            }

            public int getLocationControlId() {
                return locationControlId;
            }

            public void setLocationControlId(int locationControlId) {
                this.locationControlId = locationControlId;
            }

            public List<SlRespSystemDeviceParameterVOSBean> getSlRespSystemDeviceParameterVOS() {
                return slRespSystemDeviceParameterVOS;
            }

            public void setSlRespSystemDeviceParameterVOS(List<SlRespSystemDeviceParameterVOSBean> slRespSystemDeviceParameterVOS) {
                this.slRespSystemDeviceParameterVOS = slRespSystemDeviceParameterVOS;
            }

            public static class SlLampPostBean {
                private int id;
                private String name;
                private String num;
                private Object model;
                private double longitude;
                private double latitude;
                private Object manufacturer;
                private Object location;
                private int groupId;
                private int siteId;
                private Object defaultCameraId;
                private String createTime;
                private Object singleLampParamReqVOs;
                public String locationAreaName;
                public String locationSiteName;
                public String locationStreetName;

                public String getLocationAreaName() {
                    return locationAreaName;
                }

                public void setLocationAreaName(String locationAreaName) {
                    this.locationAreaName = locationAreaName;
                }

                public String getLocationSiteName() {
                    return locationSiteName;
                }

                public void setLocationSiteName(String locationSiteName) {
                    this.locationSiteName = locationSiteName;
                }

                public String getLocationStreetName() {
                    return locationStreetName;
                }

                public void setLocationStreetName(String locationStreetName) {
                    this.locationStreetName = locationStreetName;
                }

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

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public Object getModel() {
                    return model;
                }

                public void setModel(Object model) {
                    this.model = model;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public Object getManufacturer() {
                    return manufacturer;
                }

                public void setManufacturer(Object manufacturer) {
                    this.manufacturer = manufacturer;
                }

                public Object getLocation() {
                    return location;
                }

                public void setLocation(Object location) {
                    this.location = location;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
                }

                public int getSiteId() {
                    return siteId;
                }

                public void setSiteId(int siteId) {
                    this.siteId = siteId;
                }

                public Object getDefaultCameraId() {
                    return defaultCameraId;
                }

                public void setDefaultCameraId(Object defaultCameraId) {
                    this.defaultCameraId = defaultCameraId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public Object getSingleLampParamReqVOs() {
                    return singleLampParamReqVOs;
                }

                public void setSingleLampParamReqVOs(Object singleLampParamReqVOs) {
                    this.singleLampParamReqVOs = singleLampParamReqVOs;
                }
            }

            public static class SlRespSystemDeviceParameterVOSBean {
                private int deviceId;
                private Object deviceTypeId;
                private String filed;
                private String unit;
                private String name;
                private int isMust;
                private Object regexp;
                private Object icon;
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

                public Object getRegexp() {
                    return regexp;
                }

                public void setRegexp(Object regexp) {
                    this.regexp = regexp;
                }

                public Object getIcon() {
                    return icon;
                }

                public void setIcon(Object icon) {
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
