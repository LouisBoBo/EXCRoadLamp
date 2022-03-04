package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AllLoopsData implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"branchVOIPage":{"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0},"groupVOIPage":{"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0},"researchLoopVOIPage":{"records":[{"id":221,"name":"回路05","num":"05","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":218,"name":"回路02","num":"02","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":222,"name":"回路06","num":"06","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":219,"name":"回路03","num":"03","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":223,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":220,"name":"回路04","num":"04","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":217,"name":"回路01","num":"01","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":224,"name":"回路08","num":"08","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":215,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-05-27 15:38:57","description":null,"controlId":33,"controlName":"南山集控","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null}],"total":16,"size":9,"current":1,"searchCount":true,"pages":2},"loopVOIPage":{"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0}}
     */

    public int code;
    public String operate;
    public String message;
    public DataBean data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * branchVOIPage : {"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0}
         * groupVOIPage : {"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0}
         * researchLoopVOIPage : {"records":[{"id":221,"name":"回路05","num":"05","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":218,"name":"回路02","num":"02","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":222,"name":"回路06","num":"06","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":219,"name":"回路03","num":"03","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":223,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":220,"name":"回路04","num":"04","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":217,"name":"回路01","num":"01","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":224,"name":"回路08","num":"08","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":215,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-05-27 15:38:57","description":null,"controlId":33,"controlName":"南山集控","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null}],"total":16,"size":9,"current":1,"searchCount":true,"pages":2}
         * loopVOIPage : {"records":[],"total":0,"size":9,"current":1,"searchCount":true,"pages":0}
         */

        public BranchVOIPageBean branchVOIPage;
        public GroupVOIPageBean groupVOIPage;
        public ResearchLoopVOIPageBean researchLoopVOIPage;
        public LoopVOIPageBean loopVOIPage;

        @Data
        public static class BranchVOIPageBean implements Serializable {
            /**
             * records : []
             * total : 0
             * size : 9
             * current : 1
             * searchCount : true
             * pages : 0
             */

            public int total;
            public int size;
            public int current;
            public boolean searchCount;
            public int pages;
            public List<?> records;
        }

        @Data
        public static class GroupVOIPageBean implements Serializable {
            /**
             * records : []
             * total : 0
             * size : 9
             * current : 1
             * searchCount : true
             * pages : 0
             */

            public int total;
            public int size;
            public int current;
            public boolean searchCount;
            public int pages;
            public List<?> records;
        }

        @Data
        public static class ResearchLoopVOIPageBean implements Serializable {
            /**
             * records : [{"id":221,"name":"回路05","num":"05","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":218,"name":"回路02","num":"02","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":222,"name":"回路06","num":"06","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":219,"name":"回路03","num":"03","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":223,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":220,"name":"回路04","num":"04","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":217,"name":"回路01","num":"01","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":224,"name":"回路08","num":"08","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-06-08 15:10:32","description":null,"controlId":34,"controlName":"南山集控-假的","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null},{"id":215,"name":"回路07","num":"07","isOpen":0,"sceneStrategyId":null,"creator":184,"creatorName":"李锋","createTime":"2021-05-27 15:38:57","description":null,"controlId":33,"controlName":"南山集控","areaId":1,"areaName":"宝安区","sn":null,"orders":null,"isUse":0,"cmModuleName":null,"cmModuleLoop":null}]
             * total : 16
             * size : 9
             * current : 1
             * searchCount : true
             * pages : 2
             */

            public int total;
            public int size;
            public int current;
            public boolean searchCount;
            public int pages;
            public List<RecordsBean> records;

            @Data
            public static class RecordsBean implements Serializable {
                /**
                 * id : 221
                 * name : 回路05
                 * num : 05
                 * isOpen : 0
                 * sceneStrategyId : null
                 * creator : 184
                 * creatorName : 李锋
                 * createTime : 2021-06-08 15:10:32
                 * description : null
                 * controlId : 34
                 * controlName : 南山集控-假的
                 * areaId : 1
                 * areaName : 宝安区
                 * sn : null
                 * orders : null
                 * isUse : 0
                 * cmModuleName : null
                 * cmModuleLoop : null
                 */

                public int id;
                public String name;
                public String num;
                public int isOpen;
                public Object sceneStrategyId;
                public int creator;
                public String creatorName;
                public String createTime;
                public Object description;
                public int controlId;
                public String controlName;
                public int areaId;
                public String areaName;
                public Object sn;
                public Object orders;
                public int isUse;
                public Object cmModuleName;
                public Object cmModuleLoop;
            }
        }

        @Data
        public static class LoopVOIPageBean implements Serializable {
            /**
             * records : []
             * total : 0
             * size : 9
             * current : 1
             * searchCount : true
             * pages : 0
             */

            public int total;
            public int size;
            public int current;
            public boolean searchCount;
            public int pages;
            public List<?> records;
        }
    }
}
