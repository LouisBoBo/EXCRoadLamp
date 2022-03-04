package com.exc.roadlamp.bean;

import java.io.Serializable;
import java.util.List;

public class LoginInfo   implements Serializable {






    private int code;
    private String message;
    private String operate;


    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }








    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private int gender;
        private String accountName;
        private int roleId;
        private String userName;
        private int userId;
        private String token;
        private String headName;
        private int areaId;
        private String areaName;
        private String roleName;
        private String projectName;
        private List<PermissionListBean> permissionList;
        private List<PermissionsBean> permissions;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeadName() {
            return headName;
        }

        public void setHeadName(String headName) {
            this.headName = headName;
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

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public List<PermissionListBean> getPermissionList() {
            return permissionList;
        }

        public void setPermissionList(List<PermissionListBean> permissionList) {
            this.permissionList = permissionList;
        }

        public List<PermissionsBean> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<PermissionsBean> permissions) {
            this.permissions = permissions;
        }

        public static class PermissionListBean implements Serializable {
            /**
             * code : permission:module:alarm:threshold:add
             * name : 添加阈值
             * orders : 0
             * id : 261
             * sort : 4
             * type : add
             * uri :
             * parentId : 217
             * isShow : 1
             */

            private String code;
            private String name;
            private int orders;
            private int id;
            private int sort;
            private String type;
            private String uri;
            private int parentId;
            private int isShow;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }
        }

        public static class PermissionsBean implements Serializable {
            /**
             * code : sl:module
             * name : 智慧照明
             * respPermissionVOList : [{"code":"sl:module:map","name":"GIS地图","respPermissionVOList":[{"code":"sl:module:map:light","name":"开关灯","respPermissionVOList":[],"id":26,"sort":4,"type":"update","uri":null,"parentId":20,"isShow":1}],"id":20,"sort":3,"type":null,"uri":"intelligent-lighting/GisMap","parentId":1,"isShow":1},{"code":"sl:module:light:strategy","name":"照明策略","respPermissionVOList":[{"code":"sl:module:light:strategy:detail","name":"灯控详情","respPermissionVOList":[],"id":31,"sort":4,"type":"detail","uri":null,"parentId":21,"isShow":1},{"code":"sl:module:light:strategy:light","name":"开关灯","respPermissionVOList":[],"id":32,"sort":4,"type":"update","uri":null,"parentId":21,"isShow":1},{"code":"sl:module:light:strategy:control","name":"定时控制","respPermissionVOList":[],"id":33,"sort":4,"type":"other","uri":null,"parentId":21,"isShow":1}],"id":21,"sort":3,"type":null,"uri":"intelligent-lighting/LightingStrategy","parentId":1,"isShow":1},{"code":"sl:module:strategy:manage","name":"策略管理","respPermissionVOList":[{"code":"sl:module:strategy:manage:add","name":"新增策略","respPermissionVOList":[],"id":36,"sort":4,"type":"add","uri":null,"parentId":22,"isShow":1},{"code":"sl:module:strategy:manage:update","name":"编辑策略","respPermissionVOList":[],"id":37,"sort":4,"type":"update","uri":null,"parentId":22,"isShow":1},{"code":"sl:module:strategy:manage:delete","name":"删除策略","respPermissionVOList":[],"id":38,"sort":4,"type":"delete","uri":null,"parentId":22,"isShow":1},{"code":"sl:module:strategy:manage:detail","name":"策略详情","respPermissionVOList":[],"id":39,"sort":4,"type":"detail","uri":null,"parentId":22,"isShow":1}],"id":22,"sort":3,"type":null,"uri":"intelligent-lighting/StrategicManagement","parentId":1,"isShow":1},{"code":"sl:module:statistics","name":"统计报表","respPermissionVOList":[{"code":"sl:module:statistics:light:rate","name":"亮灯率","respPermissionVOList":[],"id":40,"sort":3,"type":null,"uri":"intelligent-lighting/StatisticsReport/lightRate","parentId":23,"isShow":1},{"code":"sl:module:statistics:energy:consumption","name":"能耗","respPermissionVOList":[],"id":41,"sort":3,"type":null,"uri":"intelligent-lighting/StatisticsReport/energyConsumption","parentId":23,"isShow":1}],"id":23,"sort":2,"type":null,"uri":"intelligent-lighting/StatisticsReport","parentId":1,"isShow":1}]
             * id : 1
             * sort : 1
             * type : null
             * uri : intelligent-lighting
             * parentId : 0
             * isShow : 1
             */

            private String code;
            private String name;
            private int id;
            private int sort;
            private Object type;
            private String uri;
            private int parentId;
            private int isShow;
            private List<RespPermissionVOListBeanX> respPermissionVOList;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public List<RespPermissionVOListBeanX> getRespPermissionVOList() {
                return respPermissionVOList;
            }

            public void setRespPermissionVOList(List<RespPermissionVOListBeanX> respPermissionVOList) {
                this.respPermissionVOList = respPermissionVOList;
            }

            public static class RespPermissionVOListBeanX implements Serializable {
                /**
                 * code : sl:module:map
                 * name : GIS地图
                 * respPermissionVOList : [{"code":"sl:module:map:light","name":"开关灯","respPermissionVOList":[],"id":26,"sort":4,"type":"update","uri":null,"parentId":20,"isShow":1}]
                 * id : 20
                 * sort : 3
                 * type : null
                 * uri : intelligent-lighting/GisMap
                 * parentId : 1
                 * isShow : 1
                 */

                private String code;
                private String name;
                private int id;
                private int sort;
                private Object type;
                private String uri;
                private int parentId;
                private int isShow;
                private List<RespPermissionVOListBean> respPermissionVOList;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public Object getType() {
                    return type;
                }

                public void setType(Object type) {
                    this.type = type;
                }

                public String getUri() {
                    return uri;
                }

                public void setUri(String uri) {
                    this.uri = uri;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public int getIsShow() {
                    return isShow;
                }

                public void setIsShow(int isShow) {
                    this.isShow = isShow;
                }

                public List<RespPermissionVOListBean> getRespPermissionVOList() {
                    return respPermissionVOList;
                }

                public void setRespPermissionVOList(List<RespPermissionVOListBean> respPermissionVOList) {
                    this.respPermissionVOList = respPermissionVOList;
                }

                public static class RespPermissionVOListBean implements Serializable {
                    /**
                     * code : sl:module:map:light
                     * name : 开关灯
                     * respPermissionVOList : []
                     * id : 26
                     * sort : 4
                     * type : update
                     * uri : null
                     * parentId : 20
                     * isShow : 1
                     */

                    private String code;
                    private String name;
                    private int id;
                    private int sort;
                    private String type;
                    private Object uri;
                    private int parentId;
                    private int isShow;
                    private List<?> respPermissionVOList;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getSort() {
                        return sort;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public Object getUri() {
                        return uri;
                    }

                    public void setUri(Object uri) {
                        this.uri = uri;
                    }

                    public int getParentId() {
                        return parentId;
                    }

                    public void setParentId(int parentId) {
                        this.parentId = parentId;
                    }

                    public int getIsShow() {
                        return isShow;
                    }

                    public void setIsShow(int isShow) {
                        this.isShow = isShow;
                    }

                    public List<?> getRespPermissionVOList() {
                        return respPermissionVOList;
                    }

                    public void setRespPermissionVOList(List<?> respPermissionVOList) {
                        this.respPermissionVOList = respPermissionVOList;
                    }
                }
            }
        }
    }
}
