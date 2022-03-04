package com.exc.roadlamp.work.workorder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class OrderDetailBean {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"woaRespOrderVO":{"orderId":25,"orderName":"1111","alarmTypeId":15,"alarmTypeName":"井盖开盖异常","orderAddr":"111","createTime":"2021-04-01 17:43:35","creatorId":1,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"processorRoleId":null,"processorRoleName":null,"description":"111","finishTime":null,"overtime":0,"updateTime":"2021-04-01 17:43:35","startHandleTime":null,"lampPostId":92,"lampPostName":"单灯氛围灯测试","partId":"lampPost92"},"woaRespOrderPicVOList":[{"orderPicId":76,"orderPicName":"orderPicture/c7c32be9-3d26-4e2e-88ab-a4c5af50a318.jpg","processId":51,"statusId":1,"createTime":"2021-04-01 17:43:28"},{"orderPicId":77,"orderPicName":"orderPicture/d4f2c9a4-29af-4514-ac54-5d8bf897bb85.png","processId":51,"statusId":1,"createTime":"2021-04-01 17:43:32"}],"woaRespOrderAlarmVOList":[],"woaRespOrderProcessVOList":[{"processId":51,"operatorId":1,"operatorName":"超管","description":"111","statusId":1,"statusName":"待初审","createTime":"2021-04-01 17:43:35"}],"currentTime":"2021-04-12 15:15:05"}
     */

    private int code;
    private String operate;
    private String message;
    private DataBean data;

    @Data
    public static class DataBean {
        /**
         * woaRespOrderVO : {"orderId":25,"orderName":"1111","alarmTypeId":15,"alarmTypeName":"井盖开盖异常","orderAddr":"111","createTime":"2021-04-01 17:43:35","creatorId":1,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"processorRoleId":null,"processorRoleName":null,"description":"111","finishTime":null,"overtime":0,"updateTime":"2021-04-01 17:43:35","startHandleTime":null,"lampPostId":92,"lampPostName":"单灯氛围灯测试","partId":"lampPost92"}
         * woaRespOrderPicVOList : [{"orderPicId":76,"orderPicName":"orderPicture/c7c32be9-3d26-4e2e-88ab-a4c5af50a318.jpg","processId":51,"statusId":1,"createTime":"2021-04-01 17:43:28"},{"orderPicId":77,"orderPicName":"orderPicture/d4f2c9a4-29af-4514-ac54-5d8bf897bb85.png","processId":51,"statusId":1,"createTime":"2021-04-01 17:43:32"}]
         * woaRespOrderAlarmVOList : []
         * woaRespOrderProcessVOList : [{"processId":51,"operatorId":1,"operatorName":"超管","description":"111","statusId":1,"statusName":"待初审","createTime":"2021-04-01 17:43:35"}]
         * currentTime : 2021-04-12 15:15:05
         */

        private WoaRespOrderVOBean woaRespOrderVO;
        private String currentTime;
        private List<WoaRespOrderPicVOListBean> woaRespOrderPicVOList;
        private List<?> woaRespOrderAlarmVOList;
        private List<WoaRespOrderProcessVOListBean> woaRespOrderProcessVOList;

        @Data
        public static class WoaRespOrderVOBean {
            /**
             * orderId : 25
             * orderName : 1111
             * alarmTypeId : 15
             * alarmTypeName : 井盖开盖异常
             * orderAddr : 111
             * createTime : 2021-04-01 17:43:35
             * creatorId : 1
             * creatorName : 超管
             * statusId : 1
             * statusName : 待初审
             * processorId : null
             * processorName : null
             * processorRoleId : null
             * processorRoleName : null
             * description : 111
             * finishTime : null
             * overtime : 0
             * updateTime : 2021-04-01 17:43:35
             * startHandleTime : null
             * lampPostId : 92
             * lampPostName : 单灯氛围灯测试
             * partId : lampPost92
             */

            private int orderId;
            private String orderName;
            private int alarmTypeId;
            private String alarmTypeName;
            private String orderAddr;
            private String createTime;
            private int creatorId;
            private String creatorName;
            private int statusId;
            private String statusName;
            private Object processorId;
            private Object processorName;
            private Object processorRoleId;
            private Object processorRoleName;
            private String description;
            private Object finishTime;
            private int overtime;
            private String updateTime;
            private Object startHandleTime;
            private int lampPostId;
            private String lampPostName;
            private String partId;
        }

        @Data
        public static class WoaRespOrderPicVOListBean {
            /**
             * orderPicId : 76
             * orderPicName : orderPicture/c7c32be9-3d26-4e2e-88ab-a4c5af50a318.jpg
             * processId : 51
             * statusId : 1
             * createTime : 2021-04-01 17:43:28
             */

            private int orderPicId;
            private String orderPicName;
            private int processId;
            private int statusId;
            private String createTime;
        }

        @Data
        public static class WoaRespOrderProcessVOListBean {
            /**
             * processId : 51
             * operatorId : 1
             * operatorName : 超管
             * description : 111
             * statusId : 1
             * statusName : 待初审
             * createTime : 2021-04-01 17:43:35
             */

            private int processId;
            private int operatorId;
            private String operatorName;
            private String description;
            private int statusId;
            private String statusName;
            private String createTime;
        }
    }
}
