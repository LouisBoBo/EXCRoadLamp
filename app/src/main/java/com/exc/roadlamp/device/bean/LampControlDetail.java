package com.exc.roadlamp.device.bean;

import java.util.List;

public class LampControlDetail {


    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"id":315,"name":"plc集控-0c","num":"plc10259","location":null,"typeId":5,"typeName":"EXC-GLA0605E-P20","isOnline":1,"createTime":null,"lastOnlineTime":"2021-12-28 14:23:30","cabinetId":161,"cabinetName":"南山配电柜1","areaId":null,"areaName":null,"ip":"192.168.112.168","port":"9999","mac":"00-14-97-38-de-0c","description":null,"softwareVertion":"v1.12","dlmRespElectricityMeterDataVO":{"electricMeterAddr":1,"currentCombinedActiveTotalEnergy":0,"updateTime":"2021-12-28 14:23:00","aphaseVoltage":232.6,"bphaseVoltage":0,"cphaseVoltage":0,"aphaseCurrent":0,"bphaseCurrent":0,"cphaseCurrent":0,"aphaseActivePower":0,"aphasePowerFactorNumber":1,"bphaseActivePower":0,"bphasePowerFactorNumber":1,"cphaseActivePower":0,"cphasePowerFactorNumber":1},"dlmRespCmModuleVOList":[],"dlmRespDistributeCabinetVO":{"id":161,"name":"南山配电柜1","num":"10001","state":1,"areaId":12,"areaName":null,"streetId":8,"streetName":null,"longitude":113.94170300000002,"latitude":22.540635999999996,"location":"南山区（软件测试）,科技园,海王银河科技大厦","createTime":"2021-10-14 11:27:47","description":null,"sunRise":null,"sunSet":null,"controlOfCabinetVOList":null,"controlCount":null,"lastOnlineTime":null}}
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 315
     * name : plc集控-0c
     * num : plc10259
     * location : null
     * typeId : 5
     * typeName : EXC-GLA0605E-P20
     * isOnline : 1
     * createTime : null
     * lastOnlineTime : 2021-12-28 14:23:30
     * cabinetId : 161
     * cabinetName : 南山配电柜1
     * areaId : null
     * areaName : null
     * ip : 192.168.112.168
     * port : 9999
     * mac : 00-14-97-38-de-0c
     * description : null
     * softwareVertion : v1.12
     * dlmRespElectricityMeterDataVO : {"electricMeterAddr":1,"currentCombinedActiveTotalEnergy":0,"updateTime":"2021-12-28 14:23:00","aphaseVoltage":232.6,"bphaseVoltage":0,"cphaseVoltage":0,"aphaseCurrent":0,"bphaseCurrent":0,"cphaseCurrent":0,"aphaseActivePower":0,"aphasePowerFactorNumber":1,"bphaseActivePower":0,"bphasePowerFactorNumber":1,"cphaseActivePower":0,"cphasePowerFactorNumber":1}
     * dlmRespCmModuleVOList : []
     * dlmRespDistributeCabinetVO : {"id":161,"name":"南山配电柜1","num":"10001","state":1,"areaId":12,"areaName":null,"streetId":8,"streetName":null,"longitude":113.94170300000002,"latitude":22.540635999999996,"location":"南山区（软件测试）,科技园,海王银河科技大厦","createTime":"2021-10-14 11:27:47","description":null,"sunRise":null,"sunSet":null,"controlOfCabinetVOList":null,"controlCount":null,"lastOnlineTime":null}
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
        private int id;
        private String name;
        private String num;
        private Object location;
        private int typeId;
        private String typeName;
        private int isOnline;
        private Object createTime;
        private String lastOnlineTime;
        private int cabinetId;
        private String cabinetName;
        private Object areaId;
        private Object areaName;
        private String ip;
        private String port;
        private String mac;
        private Object description;
        private String softwareVertion;
        /**
         * electricMeterAddr : 1
         * currentCombinedActiveTotalEnergy : 0.0
         * updateTime : 2021-12-28 14:23:00
         * aphaseVoltage : 232.6
         * bphaseVoltage : 0.0
         * cphaseVoltage : 0.0
         * aphaseCurrent : 0.0
         * bphaseCurrent : 0.0
         * cphaseCurrent : 0.0
         * aphaseActivePower : 0.0
         * aphasePowerFactorNumber : 1.0
         * bphaseActivePower : 0.0
         * bphasePowerFactorNumber : 1.0
         * cphaseActivePower : 0.0
         * cphasePowerFactorNumber : 1.0
         */

        private DlmRespElectricityMeterDataVOBean dlmRespElectricityMeterDataVO;
        /**
         * id : 161
         * name : 南山配电柜1
         * num : 10001
         * state : 1
         * areaId : 12
         * areaName : null
         * streetId : 8
         * streetName : null
         * longitude : 113.94170300000002
         * latitude : 22.540635999999996
         * location : 南山区（软件测试）,科技园,海王银河科技大厦
         * createTime : 2021-10-14 11:27:47
         * description : null
         * sunRise : null
         * sunSet : null
         * controlOfCabinetVOList : null
         * controlCount : null
         * lastOnlineTime : null
         */

        private DlmRespDistributeCabinetVOBean dlmRespDistributeCabinetVO;
        private List<?> dlmRespCmModuleVOList;

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

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getLastOnlineTime() {
            return lastOnlineTime;
        }

        public void setLastOnlineTime(String lastOnlineTime) {
            this.lastOnlineTime = lastOnlineTime;
        }

        public int getCabinetId() {
            return cabinetId;
        }

        public void setCabinetId(int cabinetId) {
            this.cabinetId = cabinetId;
        }

        public String getCabinetName() {
            return cabinetName;
        }

        public void setCabinetName(String cabinetName) {
            this.cabinetName = cabinetName;
        }

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
            this.areaId = areaId;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getSoftwareVertion() {
            return softwareVertion;
        }

        public void setSoftwareVertion(String softwareVertion) {
            this.softwareVertion = softwareVertion;
        }

        public DlmRespElectricityMeterDataVOBean getDlmRespElectricityMeterDataVO() {
            return dlmRespElectricityMeterDataVO;
        }

        public void setDlmRespElectricityMeterDataVO(DlmRespElectricityMeterDataVOBean dlmRespElectricityMeterDataVO) {
            this.dlmRespElectricityMeterDataVO = dlmRespElectricityMeterDataVO;
        }

        public DlmRespDistributeCabinetVOBean getDlmRespDistributeCabinetVO() {
            return dlmRespDistributeCabinetVO;
        }

        public void setDlmRespDistributeCabinetVO(DlmRespDistributeCabinetVOBean dlmRespDistributeCabinetVO) {
            this.dlmRespDistributeCabinetVO = dlmRespDistributeCabinetVO;
        }

        public List<?> getDlmRespCmModuleVOList() {
            return dlmRespCmModuleVOList;
        }

        public void setDlmRespCmModuleVOList(List<?> dlmRespCmModuleVOList) {
            this.dlmRespCmModuleVOList = dlmRespCmModuleVOList;
        }

        public static class DlmRespElectricityMeterDataVOBean {
            private int electricMeterAddr;
            private double currentCombinedActiveTotalEnergy;
            private String updateTime;
            private double aphaseVoltage;
            private double bphaseVoltage;
            private double cphaseVoltage;
            private double aphaseCurrent;
            private double bphaseCurrent;
            private double cphaseCurrent;
            private double aphaseActivePower;
            private double aphasePowerFactorNumber;
            private double bphaseActivePower;
            private double bphasePowerFactorNumber;
            private double cphaseActivePower;
            private double cphasePowerFactorNumber;

            public int getElectricMeterAddr() {
                return electricMeterAddr;
            }

            public void setElectricMeterAddr(int electricMeterAddr) {
                this.electricMeterAddr = electricMeterAddr;
            }

            public double getCurrentCombinedActiveTotalEnergy() {
                return currentCombinedActiveTotalEnergy;
            }

            public void setCurrentCombinedActiveTotalEnergy(double currentCombinedActiveTotalEnergy) {
                this.currentCombinedActiveTotalEnergy = currentCombinedActiveTotalEnergy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public double getAphaseVoltage() {
                return aphaseVoltage;
            }

            public void setAphaseVoltage(double aphaseVoltage) {
                this.aphaseVoltage = aphaseVoltage;
            }

            public double getBphaseVoltage() {
                return bphaseVoltage;
            }

            public void setBphaseVoltage(double bphaseVoltage) {
                this.bphaseVoltage = bphaseVoltage;
            }

            public double getCphaseVoltage() {
                return cphaseVoltage;
            }

            public void setCphaseVoltage(double cphaseVoltage) {
                this.cphaseVoltage = cphaseVoltage;
            }

            public double getAphaseCurrent() {
                return aphaseCurrent;
            }

            public void setAphaseCurrent(double aphaseCurrent) {
                this.aphaseCurrent = aphaseCurrent;
            }

            public double getBphaseCurrent() {
                return bphaseCurrent;
            }

            public void setBphaseCurrent(double bphaseCurrent) {
                this.bphaseCurrent = bphaseCurrent;
            }

            public double getCphaseCurrent() {
                return cphaseCurrent;
            }

            public void setCphaseCurrent(double cphaseCurrent) {
                this.cphaseCurrent = cphaseCurrent;
            }

            public double getAphaseActivePower() {
                return aphaseActivePower;
            }

            public void setAphaseActivePower(double aphaseActivePower) {
                this.aphaseActivePower = aphaseActivePower;
            }

            public double getAphasePowerFactorNumber() {
                return aphasePowerFactorNumber;
            }

            public void setAphasePowerFactorNumber(double aphasePowerFactorNumber) {
                this.aphasePowerFactorNumber = aphasePowerFactorNumber;
            }

            public double getBphaseActivePower() {
                return bphaseActivePower;
            }

            public void setBphaseActivePower(double bphaseActivePower) {
                this.bphaseActivePower = bphaseActivePower;
            }

            public double getBphasePowerFactorNumber() {
                return bphasePowerFactorNumber;
            }

            public void setBphasePowerFactorNumber(double bphasePowerFactorNumber) {
                this.bphasePowerFactorNumber = bphasePowerFactorNumber;
            }

            public double getCphaseActivePower() {
                return cphaseActivePower;
            }

            public void setCphaseActivePower(double cphaseActivePower) {
                this.cphaseActivePower = cphaseActivePower;
            }

            public double getCphasePowerFactorNumber() {
                return cphasePowerFactorNumber;
            }

            public void setCphasePowerFactorNumber(double cphasePowerFactorNumber) {
                this.cphasePowerFactorNumber = cphasePowerFactorNumber;
            }
        }

        public static class DlmRespDistributeCabinetVOBean {
            private int id;
            private String name;
            private String num;
            private int state;
            private int areaId;
            private Object areaName;
            private int streetId;
            private Object streetName;
            private double longitude;
            private double latitude;
            private String location;
            private String createTime;
            private Object description;
            private Object sunRise;
            private Object sunSet;
            private Object controlOfCabinetVOList;
            private Object controlCount;
            private Object lastOnlineTime;

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

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public Object getAreaName() {
                return areaName;
            }

            public void setAreaName(Object areaName) {
                this.areaName = areaName;
            }

            public int getStreetId() {
                return streetId;
            }

            public void setStreetId(int streetId) {
                this.streetId = streetId;
            }

            public Object getStreetName() {
                return streetName;
            }

            public void setStreetName(Object streetName) {
                this.streetName = streetName;
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

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public Object getSunRise() {
                return sunRise;
            }

            public void setSunRise(Object sunRise) {
                this.sunRise = sunRise;
            }

            public Object getSunSet() {
                return sunSet;
            }

            public void setSunSet(Object sunSet) {
                this.sunSet = sunSet;
            }

            public Object getControlOfCabinetVOList() {
                return controlOfCabinetVOList;
            }

            public void setControlOfCabinetVOList(Object controlOfCabinetVOList) {
                this.controlOfCabinetVOList = controlOfCabinetVOList;
            }

            public Object getControlCount() {
                return controlCount;
            }

            public void setControlCount(Object controlCount) {
                this.controlCount = controlCount;
            }

            public Object getLastOnlineTime() {
                return lastOnlineTime;
            }

            public void setLastOnlineTime(Object lastOnlineTime) {
                this.lastOnlineTime = lastOnlineTime;
            }
        }
    }
}
