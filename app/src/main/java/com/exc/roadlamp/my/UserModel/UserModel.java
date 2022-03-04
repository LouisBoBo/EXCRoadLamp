package com.exc.roadlamp.my.UserModel;

import java.util.List;

public class UserModel {

    /**
     * code : 0
     * data : {"accountName":"","areaId":0,"areaName":"","forbidden":0,"founderId":0,"founderName":"","gender":0,"headPicVO":{"createTime":"","id":0,"name":""},"id":0,"name":"","online":0,"onlineTime":"","periodType":0,"phone":"","projectPicVO":{"createTime":"","id":0,"name":""},"roleName":"","roles":[{"areaId":0,"createTime":"","founderId":0,"id":0,"name":"","type":0,"updateTime":""}],"type":0,"validityPeriod":""}
     * message :
     * operate :
     */

    private int code;
    /**
     * accountName :
     * areaId : 0
     * areaName :
     * forbidden : 0
     * founderId : 0
     * founderName :
     * gender : 0
     * headPicVO : {"createTime":"","id":0,"name":""}
     * id : 0
     * name :
     * online : 0
     * onlineTime :
     * periodType : 0
     * phone :
     * projectPicVO : {"createTime":"","id":0,"name":""}
     * roleName :
     * roles : [{"areaId":0,"createTime":"","founderId":0,"id":0,"name":"","type":0,"updateTime":""}]
     * type : 0
     * validityPeriod :
     */

    private DataBean data;
    private String message;
    private String operate;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public static class DataBean {
        private String accountName;
        private int areaId;
        private String areaName;
        private int forbidden;
        private int founderId;
        private String founderName;
        private int gender;
        /**
         * createTime :
         * id : 0
         * name :
         */

        private HeadPicVOBean headPicVO;
        private int id;
        private String name;
        private int online;
        private String onlineTime;
        private int periodType;
        private String phone;
        /**
         * createTime :
         * id : 0
         * name :
         */

        private ProjectPicVOBean projectPicVO;
        private String roleName;
        private int type;
        private String validityPeriod;
        /**
         * areaId : 0
         * createTime :
         * founderId : 0
         * id : 0
         * name :
         * type : 0
         * updateTime :
         */

        private List<RolesBean> roles;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
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

        public int getForbidden() {
            return forbidden;
        }

        public void setForbidden(int forbidden) {
            this.forbidden = forbidden;
        }

        public int getFounderId() {
            return founderId;
        }

        public void setFounderId(int founderId) {
            this.founderId = founderId;
        }

        public String getFounderName() {
            return founderName;
        }

        public void setFounderName(String founderName) {
            this.founderName = founderName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public HeadPicVOBean getHeadPicVO() {
            return headPicVO;
        }

        public void setHeadPicVO(HeadPicVOBean headPicVO) {
            this.headPicVO = headPicVO;
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

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getOnlineTime() {
            return onlineTime;
        }

        public void setOnlineTime(String onlineTime) {
            this.onlineTime = onlineTime;
        }

        public int getPeriodType() {
            return periodType;
        }

        public void setPeriodType(int periodType) {
            this.periodType = periodType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public ProjectPicVOBean getProjectPicVO() {
            return projectPicVO;
        }

        public void setProjectPicVO(ProjectPicVOBean projectPicVO) {
            this.projectPicVO = projectPicVO;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getValidityPeriod() {
            return validityPeriod;
        }

        public void setValidityPeriod(String validityPeriod) {
            this.validityPeriod = validityPeriod;
        }

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class HeadPicVOBean {
            private String createTime;
            private int id;
            private String name;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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
        }

        public static class ProjectPicVOBean {
            private String createTime;
            private int id;
            private String name;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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
        }

        public static class RolesBean {
            private int areaId;
            private String createTime;
            private int founderId;
            private int id;
            private String name;
            private int type;
            private String updateTime;

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getFounderId() {
                return founderId;
            }

            public void setFounderId(int founderId) {
                this.founderId = founderId;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
