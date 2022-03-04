package com.exc.roadlamp.device.bean;

import java.util.List;

public class DeviceSystemBean {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":467,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-N110E-3","value":"18","remarks":"单回路自研nb单灯移动版","systemDeviceType":{"id":467,"name":"EXC-TL1-N110E-3","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"单回路","description":"单回路自研nb单灯移动版","numLength":15}},{"id":466,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-C210E-2","value":"15","remarks":"双回路自研cat1单灯电信版","systemDeviceType":{"id":466,"name":"EXC-TL1-C210E-2","edition":"1.0","protocol":"http","socket":"cat1","factoryId":1,"loopType":"双回路","description":"双回路自研cat1单灯电信版","numLength":15}},{"id":463,"areaId":12,"filed":"device_type","unit":null,"name":"HuaTi","value":"12","remarks":"华体单灯","systemDeviceType":{"id":463,"name":"HuaTi","edition":"1.0","protocol":"http","socket":"nb","factoryId":3,"loopType":"三回路","description":"华体单灯","numLength":20}},{"id":464,"areaId":12,"filed":"device_type","unit":null,"name":"ZKZL","value":"13","remarks":"中科智联单灯","systemDeviceType":{"id":464,"name":"ZKZL","edition":"1.0","protocol":"http","socket":"nb","factoryId":4,"loopType":"单回路","description":"中科智联单灯","numLength":12}},{"id":458,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-N110E-2","value":"7","remarks":"单回路自研nb单灯电信版","systemDeviceType":{"id":458,"name":"EXC-TL1-N110E-2","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"单回路","description":"单回路自研nb单灯电信版","numLength":15}},{"id":469,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-C110E-TFLX","value":"22","remarks":"单回路cat1氛围灯电信版","systemDeviceType":{"id":469,"name":"EXC-TL1-C110E-TFLX","edition":"1.0","protocol":"http","socket":"cat1","factoryId":1,"loopType":"单回路","description":"单回路cat1氛围灯电信版","numLength":15}},{"id":462,"areaId":12,"filed":"device_type","unit":null,"name":"SZ10-R1A-NB_30-1","value":"11","remarks":"顺舟nb单灯（电信平台版）","systemDeviceType":{"id":462,"name":"SZ10-R1A-NB_30-1","edition":"1.0","protocol":"https","socket":"nb","factoryId":2,"loopType":"单回路","description":"顺舟nb单灯","numLength":36}},{"id":470,"areaId":12,"filed":"device_type","unit":null,"name":"SZ10-R1A-NB_30-2","value":"17","remarks":"顺舟nb单灯（顺舟平台版）","systemDeviceType":{"id":470,"name":"SZ10-R1A-NB_30-2","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"单回路","description":"顺舟nb单灯（顺舟平台版）","numLength":15}},{"id":471,"areaId":12,"filed":"device_type","unit":null,"name":"solar-light","value":"21","remarks":"太阳能灯","systemDeviceType":{"id":471,"name":"solar-light","edition":"1.0","protocol":"http","socket":"http","factoryId":null,"loopType":null,"description":"太阳能灯","numLength":14}},{"id":468,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-C310E-2","value":"20","remarks":"自研cat1氛围灯电信版","systemDeviceType":{"id":468,"name":"EXC-TL1-C310E-2","edition":"1.0","protocol":"http","socket":"cat1","factoryId":1,"loopType":"三回路","description":"自研cat1氛围灯电信版","numLength":15}},{"id":461,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-L210E-2","value":"10","remarks":"双回路自研lora单灯新平台版","systemDeviceType":{"id":461,"name":"EXC-TL1-L210E-2","edition":"1.0","protocol":"https","socket":"lora","factoryId":1,"loopType":"双回路","description":"双回路自研lora单灯新平台版","numLength":16}},{"id":465,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-C110E-2","value":"14","remarks":"单回路自研cat1单灯电信版","systemDeviceType":{"id":465,"name":"EXC-TL1-C110E-2","edition":"1.0","protocol":"http","socket":"cat1","factoryId":1,"loopType":"单回路","description":"单回路自研cat1单灯电信版","numLength":15}},{"id":460,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-L110E-2","value":"9","remarks":"单回路自研lora单灯新平台版","systemDeviceType":{"id":460,"name":"EXC-TL1-L110E-2","edition":"1.0","protocol":"http","socket":"lora","factoryId":1,"loopType":"单回路","description":"单回路自研lora单灯新平台版","numLength":16}},{"id":472,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-N210E-3","value":"19","remarks":"双回路自研nb单灯移动版","systemDeviceType":{"id":472,"name":"EXC-TL1-N210E-3","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"双回路","description":"双回路自研nb单灯移动版","numLength":15}},{"id":459,"areaId":12,"filed":"device_type","unit":null,"name":"EXC-TL1-N210E-2","value":"8","remarks":"双回路自研nb单灯电信版","systemDeviceType":{"id":459,"name":"EXC-TL1-N210E-2","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"双回路","description":"双回路自研nb单灯电信版","numLength":15}}]
     */

    private int code;
    private String operate;
    private String message;
    /**
     * id : 467
     * areaId : 12
     * filed : device_type
     * unit : null
     * name : EXC-TL1-N110E-3
     * value : 18
     * remarks : 单回路自研nb单灯移动版
     * systemDeviceType : {"id":467,"name":"EXC-TL1-N110E-3","edition":"1.0","protocol":"http","socket":"nb","factoryId":1,"loopType":"单回路","description":"单回路自研nb单灯移动版","numLength":15}
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
        private int areaId;
        private String filed;
        private Object unit;
        private String name;
        private String value;
        private String remarks;
        /**
         * id : 467
         * name : EXC-TL1-N110E-3
         * edition : 1.0
         * protocol : http
         * socket : nb
         * factoryId : 1
         * loopType : 单回路
         * description : 单回路自研nb单灯移动版
         * numLength : 15
         */

        private SystemDeviceTypeBean systemDeviceType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getFiled() {
            return filed;
        }

        public void setFiled(String filed) {
            this.filed = filed;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public SystemDeviceTypeBean getSystemDeviceType() {
            return systemDeviceType;
        }

        public void setSystemDeviceType(SystemDeviceTypeBean systemDeviceType) {
            this.systemDeviceType = systemDeviceType;
        }

        public static class SystemDeviceTypeBean {
            private int id;
            private String name;
            private String edition;
            private String protocol;
            private String socket;
            private int factoryId;
            private String loopType;
            private String description;
            private int numLength;

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

            public String getEdition() {
                return edition;
            }

            public void setEdition(String edition) {
                this.edition = edition;
            }

            public String getProtocol() {
                return protocol;
            }

            public void setProtocol(String protocol) {
                this.protocol = protocol;
            }

            public String getSocket() {
                return socket;
            }

            public void setSocket(String socket) {
                this.socket = socket;
            }

            public int getFactoryId() {
                return factoryId;
            }

            public void setFactoryId(int factoryId) {
                this.factoryId = factoryId;
            }

            public String getLoopType() {
                return loopType;
            }

            public void setLoopType(String loopType) {
                this.loopType = loopType;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getNumLength() {
                return numLength;
            }

            public void setNumLength(int numLength) {
                this.numLength = numLength;
            }
        }
    }
}
