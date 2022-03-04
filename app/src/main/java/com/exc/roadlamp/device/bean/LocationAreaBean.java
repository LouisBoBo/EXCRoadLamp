package com.exc.roadlamp.device.bean;

import java.util.List;

public class LocationAreaBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"partId":"area1","name":"健仓路灯展厅","description":"别删除11","createTime":"2020-04-27 19:54:06","deviceNumber":94,"childrenList":[{"id":1,"partId":"street1","name":"松岗街道","description":"不要删除","createTime":"2020-04-27 19:54:25","deviceNumber":94,"superId":1,"superName":"健仓路灯展厅","childrenList":[{"id":69,"partId":"site69","name":"大厅","description":null,"createTime":"2021-07-12 15:23:25","deviceNumber":38,"superId":1,"superName":"松岗街道","childrenList":null},{"id":46,"partId":"site46","name":"展厅","description":"仅含展厅灯杆","createTime":"2021-02-05 11:25:57","deviceNumber":56,"superId":1,"superName":"松岗街道","childrenList":null}]}],"lampState":null,"lampBrightness":null,"longitude":113.841,"latitude":22.688}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 1
     * partId : area1
     * name : 健仓路灯展厅
     * description : 别删除11
     * createTime : 2020-04-27 19:54:06
     * deviceNumber : 94
     * childrenList : [{"id":1,"partId":"street1","name":"松岗街道","description":"不要删除","createTime":"2020-04-27 19:54:25","deviceNumber":94,"superId":1,"superName":"健仓路灯展厅","childrenList":[{"id":69,"partId":"site69","name":"大厅","description":null,"createTime":"2021-07-12 15:23:25","deviceNumber":38,"superId":1,"superName":"松岗街道","childrenList":null},{"id":46,"partId":"site46","name":"展厅","description":"仅含展厅灯杆","createTime":"2021-02-05 11:25:57","deviceNumber":56,"superId":1,"superName":"松岗街道","childrenList":null}]}]
     * lampState : null
     * lampBrightness : null
     * longitude : 113.841
     * latitude : 22.688
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
        private String description;
        private String createTime;
        private int deviceNumber;
        private Object lampState;
        private Object lampBrightness;
        private double longitude;
        private double latitude;
        /**
         * id : 1
         * partId : street1
         * name : 松岗街道
         * description : 不要删除
         * createTime : 2020-04-27 19:54:25
         * deviceNumber : 94
         * superId : 1
         * superName : 健仓路灯展厅
         * childrenList : [{"id":69,"partId":"site69","name":"大厅","description":null,"createTime":"2021-07-12 15:23:25","deviceNumber":38,"superId":1,"superName":"松岗街道","childrenList":null},{"id":46,"partId":"site46","name":"展厅","description":"仅含展厅灯杆","createTime":"2021-02-05 11:25:57","deviceNumber":56,"superId":1,"superName":"松岗街道","childrenList":null}]
         */

        private List<ChildrenListBeanXX> childrenList;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public Object getLampState() {
            return lampState;
        }

        public void setLampState(Object lampState) {
            this.lampState = lampState;
        }

        public Object getLampBrightness() {
            return lampBrightness;
        }

        public void setLampBrightness(Object lampBrightness) {
            this.lampBrightness = lampBrightness;
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

        public List<ChildrenListBeanXX> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<ChildrenListBeanXX> childrenList) {
            this.childrenList = childrenList;
        }

        public static class ChildrenListBeanXX {
            private int id;
            private String partId;
            private String name;
            private String description;
            private String createTime;
            private int deviceNumber;
            private int superId;
            private String superName;
            /**
             * id : 69
             * partId : site69
             * name : 大厅
             * description : null
             * createTime : 2021-07-12 15:23:25
             * deviceNumber : 38
             * superId : 1
             * superName : 松岗街道
             * childrenList : null
             */

            private List<ChildrenListBeanX> childrenList;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public List<ChildrenListBeanX> getChildrenList() {
                return childrenList;
            }

            public void setChildrenList(List<ChildrenListBeanX> childrenList) {
                this.childrenList = childrenList;
            }

            public static class ChildrenListBeanX {
                private int id;
                private String partId;
                private String name;
                private Object description;
                private String createTime;
                private int deviceNumber;
                private int superId;
                private String superName;
                private Object childrenList;

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

                public Object getDescription() {
                    return description;
                }

                public void setDescription(Object description) {
                    this.description = description;
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

                public Object getChildrenList() {
                    return childrenList;
                }

                public void setChildrenList(Object childrenList) {
                    this.childrenList = childrenList;
                }
            }
        }
    }
}
