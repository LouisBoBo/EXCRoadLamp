package com.exc.roadlamp.work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrderListBean   {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : {"records":[{"orderId":24,"orderName":"75","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 17:09:06","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":23,"orderName":"456","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 17:05:58","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":89,"lampPostName":"11111","partId":"lampPost89"},{"orderId":22,"orderName":"12313","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 16:45:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":92,"lampPostName":"氛围灯测试","partId":"lampPost92"},{"orderId":21,"orderName":"11","alarmTypeId":2,"alarmTypeName":"网关离线","createTime":"2021-03-31 16:44:10","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":20,"orderName":"121","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-30 20:09:46","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":85,"lampPostName":"LORA双灯测试","partId":"lampPost85"},{"orderId":19,"orderName":"111","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-30 20:05:54","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":85,"lampPostName":"LORA双灯测试","partId":"lampPost85"},{"orderId":18,"orderName":"123","alarmTypeId":3,"alarmTypeName":"灯控器电流异常","createTime":"2021-03-19 15:38:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":83,"lampPostName":"盐城灯杆","partId":"lampPost83"},{"orderId":17,"orderName":"1","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-18 14:51:14","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":14,"orderName":"105","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:08:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":59,"lampPostName":null,"partId":"lampPost59"},{"orderId":13,"orderName":"104","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:08:21","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":59,"lampPostName":null,"partId":"lampPost59"},{"orderId":12,"orderName":"103","alarmTypeId":2,"alarmTypeName":"网关离线","createTime":"2020-12-30 17:07:48","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":57,"lampPostName":null,"partId":"lampPost57"},{"orderId":11,"orderName":"102","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:07:20","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":58,"lampPostName":null,"partId":"lampPost58"},{"orderId":4,"orderName":"工单101","alarmTypeId":12,"alarmTypeName":"灯杆倾斜异常","createTime":"2020-11-10 11:42:18","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":45,"lampPostName":"深大灯杆-CAT1","partId":"lampPost45"}],"total":13,"size":9999,"current":1,"searchCount":true,"pages":1}
     */

    private int code;
    private String operate;
    private String message;
    private DataBean data;

    @Data
    public static class DataBean   {
        /**
         * records : [{"orderId":24,"orderName":"75","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 17:09:06","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":23,"orderName":"456","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 17:05:58","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":89,"lampPostName":"11111","partId":"lampPost89"},{"orderId":22,"orderName":"12313","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-31 16:45:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":92,"lampPostName":"氛围灯测试","partId":"lampPost92"},{"orderId":21,"orderName":"11","alarmTypeId":2,"alarmTypeName":"网关离线","createTime":"2021-03-31 16:44:10","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":20,"orderName":"121","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-30 20:09:46","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":85,"lampPostName":"LORA双灯测试","partId":"lampPost85"},{"orderId":19,"orderName":"111","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-30 20:05:54","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":85,"lampPostName":"LORA双灯测试","partId":"lampPost85"},{"orderId":18,"orderName":"123","alarmTypeId":3,"alarmTypeName":"灯控器电流异常","createTime":"2021-03-19 15:38:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":83,"lampPostName":"盐城灯杆","partId":"lampPost83"},{"orderId":17,"orderName":"1","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2021-03-18 14:51:14","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":84,"lampPostName":"LORA测试","partId":"lampPost84"},{"orderId":14,"orderName":"105","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:08:35","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":59,"lampPostName":null,"partId":"lampPost59"},{"orderId":13,"orderName":"104","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:08:21","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":59,"lampPostName":null,"partId":"lampPost59"},{"orderId":12,"orderName":"103","alarmTypeId":2,"alarmTypeName":"网关离线","createTime":"2020-12-30 17:07:48","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":57,"lampPostName":null,"partId":"lampPost57"},{"orderId":11,"orderName":"102","alarmTypeId":1,"alarmTypeName":"灯控器离线","createTime":"2020-12-30 17:07:20","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":58,"lampPostName":null,"partId":"lampPost58"},{"orderId":4,"orderName":"工单101","alarmTypeId":12,"alarmTypeName":"灯杆倾斜异常","createTime":"2020-11-10 11:42:18","creator":null,"creatorName":"超管","statusId":1,"statusName":"待初审","processorId":null,"processorName":null,"overtime":0,"lampPostId":45,"lampPostName":"深大灯杆-CAT1","partId":"lampPost45"}]
         * total : 13
         * size : 9999
         * current : 1
         * searchCount : true
         * pages : 1
         */

        private int total;
        private int size;
        private int current;
        private boolean searchCount;
        private int pages;
        private ArrayList<RecordsBean> records;

        @Data
        public static class RecordsBean   {
            /**
             * orderId : 24
             * orderName : 75
             * alarmTypeId : 1
             * alarmTypeName : 灯控器离线
             * createTime : 2021-03-31 17:09:06
             * creator : null
             * creatorName : 超管
             * statusId : 1
             * statusName : 待初审
             * processorId : null
             * processorName : null
             * overtime : 0
             * lampPostId : 84
             * lampPostName : LORA测试
             * partId : lampPost84
             */

            private int orderId;
            private String orderName;
            private int alarmTypeId;
            private String alarmTypeName;
            private String createTime;
            private Object creator;
            private String creatorName;
            private int statusId;
            private String statusName;
            private Object processorId;
            private Object processorName;
            private int overtime;
            private int lampPostId;
            private String lampPostName;
            private String partId;
        }
    }
}
