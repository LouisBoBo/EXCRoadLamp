package com.exc.roadlamp.device.bean;

import com.exc.roadlamp.device.adapter.LampChildrenListBean;

import java.util.List;

public class LampDeviceListBean {

    /**
     * code : 200
     * operate : success
     * message :
     * data : {"records":[{"id":2177,"partId":"lampPost2177","name":"南山软测-灯杆4","lampPostNum":"ns104","lampPostModel":null,"lampPostLongitude":113.33,"lampPostLatitude":22.25,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-08-20 15:27:48","deviceNumber":0,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[]},{"id":2175,"partId":"lampPost2175","name":"南山软测-灯杆3","lampPostNum":"999","lampPostModel":"777","lampPostLongitude":111.896328,"lampPostLatitude":30.973649,"lampPostManufacturer":"exc","lampPostLocation":"南山区,科技园,海王银河科技大厦","createTime":"2021-08-19 18:06:09","deviceNumber":1,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":38,"partId":"device38","num":"00-14-97-2e-a5-23","name":"软测3000E-云农","networkState":0,"lastOnlineTime":"2021-08-20 18:20:00","deviceType":7,"lampPostId":2175,"superId":2175,"superName":"南山软测-灯杆3","dlmRespDevice":{"id":38,"model":"EXC","ip":"183.14.28.98","port":33606,"factory":"EXC","name":"软测3000E-云农","num":"00-14-97-2e-a5-23","networkState":0,"createTime":"2021-08-20 16:27:40","lastOnlineTime":"2021-08-20 18:20:00","lampPostId":2175}}]},{"id":2008,"partId":"lampPost2008","name":"南山测试-灯杆1","lampPostNum":"ns101","lampPostModel":null,"lampPostLongitude":113.88,"lampPostLatitude":22.89,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-08-09 11:34:27","deviceNumber":1,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":3137,"partId":"device3137","num":"008008008008008","name":"南山测试灯具","networkState":0,"lastOnlineTime":"2021-08-23 14:01:52","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}}]},{"id":1692,"partId":"lampPost1692","name":"南山软测-灯杆2","lampPostNum":"NSSY001","lampPostModel":null,"lampPostLongitude":113.898,"lampPostLatitude":22.686,"lampPostManufacturer":"南山实验室","lampPostLocation":null,"createTime":"2021-05-20 15:27:24","deviceNumber":2,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":39,"partId":"device39","num":"00-14-97-33-72-e6","name":"软测2000E-富奥","networkState":1,"lastOnlineTime":"2021-08-24 15:42:44","deviceType":7,"lampPostId":1692,"superId":1692,"superName":"南山软测-灯杆2","dlmRespDevice":{"id":39,"model":"exc","ip":"183.14.29.91","port":35046,"factory":"富奥通","name":"软测2000E-富奥","num":"00-14-97-33-72-e6","networkState":1,"createTime":"2021-08-20 17:28:47","lastOnlineTime":"2021-08-24 15:42:44","lampPostId":1692}},{"id":8,"partId":"device8","num":"NAtest","name":"南山实验室","networkState":0,"lastOnlineTime":"2021-06-11 14:11:13","deviceType":16,"lampPostId":1692,"superId":1692,"superName":"南山软测-灯杆2","dlmRespDevice":{"id":8,"model":null,"ip":"192.168.112.2","port":9999,"factory":null,"name":"南山实验室","num":"NAtest","networkState":null,"createTime":"2021-05-20 17:39:50","lastOnlineTime":"2021-06-11 14:11:13","lampPostId":1692}}]}],"total":4,"size":9999,"current":1,"searchCount":true,"pages":1}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * records : [{"id":2177,"partId":"lampPost2177","name":"南山软测-灯杆4","lampPostNum":"ns104","lampPostModel":null,"lampPostLongitude":113.33,"lampPostLatitude":22.25,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-08-20 15:27:48","deviceNumber":0,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[]},{"id":2175,"partId":"lampPost2175","name":"南山软测-灯杆3","lampPostNum":"999","lampPostModel":"777","lampPostLongitude":111.896328,"lampPostLatitude":30.973649,"lampPostManufacturer":"exc","lampPostLocation":"南山区,科技园,海王银河科技大厦","createTime":"2021-08-19 18:06:09","deviceNumber":1,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":38,"partId":"device38","num":"00-14-97-2e-a5-23","name":"软测3000E-云农","networkState":0,"lastOnlineTime":"2021-08-20 18:20:00","deviceType":7,"lampPostId":2175,"superId":2175,"superName":"南山软测-灯杆3","dlmRespDevice":{"id":38,"model":"EXC","ip":"183.14.28.98","port":33606,"factory":"EXC","name":"软测3000E-云农","num":"00-14-97-2e-a5-23","networkState":0,"createTime":"2021-08-20 16:27:40","lastOnlineTime":"2021-08-20 18:20:00","lampPostId":2175}}]},{"id":2008,"partId":"lampPost2008","name":"南山测试-灯杆1","lampPostNum":"ns101","lampPostModel":null,"lampPostLongitude":113.88,"lampPostLatitude":22.89,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-08-09 11:34:27","deviceNumber":1,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":3137,"partId":"device3137","num":"008008008008008","name":"南山测试灯具","networkState":0,"lastOnlineTime":"2021-08-23 14:01:52","deviceType":1,"lampPostId":2008,"superId":2008,"superName":"南山测试-灯杆1","dlmRespDevice":{"id":3137,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"南山测试灯具","num":"008008008008008","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-08-24 14:01:52","lastOnlineTime":"2021-08-23 14:01:52","lampPostId":2008,"lampPostName":"南山测试-灯杆1","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":3137,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":26},{"deviceId":3137,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":3137,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":3137,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}}]},{"id":1692,"partId":"lampPost1692","name":"南山软测-灯杆2","lampPostNum":"NSSY001","lampPostModel":null,"lampPostLongitude":113.898,"lampPostLatitude":22.686,"lampPostManufacturer":"南山实验室","lampPostLocation":null,"createTime":"2021-05-20 15:27:24","deviceNumber":2,"superId":7,"superName":"海王银河科技大厦","areaId":12,"areaName":"南山区","streetId":8,"streetName":"科技园","ids":"12,8,7","names":"南山区,科技园,海王银河科技大厦","childrenList":[{"id":39,"partId":"device39","num":"00-14-97-33-72-e6","name":"软测2000E-富奥","networkState":1,"lastOnlineTime":"2021-08-24 15:42:44","deviceType":7,"lampPostId":1692,"superId":1692,"superName":"南山软测-灯杆2","dlmRespDevice":{"id":39,"model":"exc","ip":"183.14.29.91","port":35046,"factory":"富奥通","name":"软测2000E-富奥","num":"00-14-97-33-72-e6","networkState":1,"createTime":"2021-08-20 17:28:47","lastOnlineTime":"2021-08-24 15:42:44","lampPostId":1692}},{"id":8,"partId":"device8","num":"NAtest","name":"南山实验室","networkState":0,"lastOnlineTime":"2021-06-11 14:11:13","deviceType":16,"lampPostId":1692,"superId":1692,"superName":"南山软测-灯杆2","dlmRespDevice":{"id":8,"model":null,"ip":"192.168.112.2","port":9999,"factory":null,"name":"南山实验室","num":"NAtest","networkState":null,"createTime":"2021-05-20 17:39:50","lastOnlineTime":"2021-06-11 14:11:13","lampPostId":1692}}]}]
     * total : 4
     * size : 9999
     * current : 1
     * searchCount : true
     * pages : 1
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
         * id : 2177
         * partId : lampPost2177
         * name : 南山软测-灯杆4
         * lampPostNum : ns104
         * lampPostModel : null
         * lampPostLongitude : 113.33
         * lampPostLatitude : 22.25
         * lampPostManufacturer : null
         * lampPostLocation : null
         * createTime : 2021-08-20 15:27:48
         * deviceNumber : 0
         * superId : 7
         * superName : 海王银河科技大厦
         * areaId : 12
         * areaName : 南山区
         * streetId : 8
         * streetName : 科技园
         * ids : 12,8,7
         * names : 南山区,科技园,海王银河科技大厦
         * childrenList : []
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
            private List<LampChildrenListBean> childrenList;
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

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

            public List<LampChildrenListBean> getChildrenList() {
                return childrenList;
            }

            public void setChildrenList(List<LampChildrenListBean> childrenList) {
                this.childrenList = childrenList;
            }
        }
    }
}
