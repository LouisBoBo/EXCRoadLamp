package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CenterControlDetail implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"id":33,"name":"南山集控","num":"100258","location":null,"typeId":4,"typeName":"EXC","isOnline":1,"createTime":null,"lastOnlineTime":"2021-06-08 16:07:24","cabinetId":114,"cabinetName":"南山开发-配电柜","areaId":null,"areaName":null,"ip":"183.14.28.124","port":"9999","mac":"00-14-97-33-ce-3d","dlmRespElectricityMeterDataVO":{"electricMeterAddr":81,"currentCombinedActiveTotalEnergy":0.01,"updateTime":"2021-06-08 16:07:02","aphaseVoltage":0,"bphaseVoltage":0,"cphaseVoltage":229.12,"aphaseCurrent":0,"bphaseCurrent":0,"cphaseCurrent":0,"aphaseActivePower":0,"aphasePowerFactorNumber":1,"bphaseActivePower":0,"bphasePowerFactorNumber":1,"cphaseActivePower":0,"cphasePowerFactorNumber":1},"dlmRespCmModuleVOList":[]}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * id : 33
         * name : 南山集控
         * num : 100258
         * location : null
         * typeId : 4
         * typeName : EXC
         * isOnline : 1
         * createTime : null
         * lastOnlineTime : 2021-06-08 16:07:24
         * cabinetId : 114
         * cabinetName : 南山开发-配电柜
         * areaId : null
         * areaName : null
         * ip : 183.14.28.124
         * port : 9999
         * mac : 00-14-97-33-ce-3d
         * dlmRespElectricityMeterDataVO : {"electricMeterAddr":81,"currentCombinedActiveTotalEnergy":0.01,"updateTime":"2021-06-08 16:07:02","aphaseVoltage":0,"bphaseVoltage":0,"cphaseVoltage":229.12,"aphaseCurrent":0,"bphaseCurrent":0,"cphaseCurrent":0,"aphaseActivePower":0,"aphasePowerFactorNumber":1,"bphaseActivePower":0,"bphasePowerFactorNumber":1,"cphaseActivePower":0,"cphasePowerFactorNumber":1}
         * dlmRespCmModuleVOList : []
         */

        public int id;
        public String name;
        public String num;
        public Object location;
        public int typeId;
        public String typeName;
        public int isOnline;
        public Object createTime;
        public String lastOnlineTime;
        public int cabinetId;
        public String cabinetName;
        public Object areaId;
        public Object areaName;
        public String ip;
        public String port;
        public String mac;
        public DlmRespElectricityMeterDataVOBean dlmRespElectricityMeterDataVO;
        public List<?> dlmRespCmModuleVOList;

        @Data
        public static class DlmRespElectricityMeterDataVOBean implements Serializable {
            /**
             * electricMeterAddr : 81
             * currentCombinedActiveTotalEnergy : 0.01
             * updateTime : 2021-06-08 16:07:02
             * aphaseVoltage : 0
             * bphaseVoltage : 0
             * cphaseVoltage : 229.12
             * aphaseCurrent : 0
             * bphaseCurrent : 0
             * cphaseCurrent : 0
             * aphaseActivePower : 0
             * aphasePowerFactorNumber : 1
             * bphaseActivePower : 0
             * bphasePowerFactorNumber : 1
             * cphaseActivePower : 0
             * cphasePowerFactorNumber : 1
             */

            public String electricMeterAddr;
            public String currentCombinedActiveTotalEnergy;
            public String updateTime;
            public String aphaseVoltage;
            public String bphaseVoltage;
            public String cphaseVoltage;
            public String aphaseCurrent;
            public String bphaseCurrent;
            public String cphaseCurrent;
            public String aphaseActivePower;
            public String aphasePowerFactorNumber;
            public String bphaseActivePower;
            public String bphasePowerFactorNumber;
            public String cphaseActivePower;
            public String cphasePowerFactorNumber;
        }
    }
}
