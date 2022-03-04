package com.exc.roadlamp.work.workorder;

import java.util.List;

import lombok.Data;

@Data
public class OrderOperatorList {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":9,"accountName":"测试华管理","name":"华仔666888","password":"Exc123456789","gender":0,"phone":"18211101589","email":null,"status":1,"founderId":1,"createTime":"2020-10-13 09:45:00","updateTime":"2020-12-30 17:43:23","type":2,"areaId":2,"validityPeriod":null,"online":1,"onlineTime":"2021-01-20 11:32:01","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":10,"accountName":"伊吾分区管理员","name":"伊吾1","password":"Exc123456789","gender":0,"phone":"18211101517","email":null,"status":1,"founderId":1,"createTime":"2020-10-13 20:48:57","updateTime":"2020-12-30 16:53:06","type":2,"areaId":2,"validityPeriod":null,"online":1,"onlineTime":"2021-01-15 10:35:54","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":16,"accountName":"管理员01","name":"liuquan","password":"18771013650Lq","gender":0,"phone":"18771013650","email":null,"status":1,"founderId":1,"createTime":"2020-11-09 20:27:47","updateTime":null,"type":2,"areaId":1,"validityPeriod":null,"online":1,"onlineTime":"2020-12-29 11:42:08","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":17,"accountName":"管理员02","name":"liuquan","password":"123456789Lq","gender":0,"phone":"18771010000","email":null,"status":1,"founderId":1,"createTime":"2020-11-09 20:49:20","updateTime":"2020-12-21 16:51:09","type":2,"areaId":1,"validityPeriod":null,"online":1,"onlineTime":"2021-01-19 17:21:40","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":18,"accountName":"测试华1152454","name":"yi","password":"Exc123456789","gender":0,"phone":"18211105968","email":null,"status":1,"founderId":1,"createTime":"2020-11-13 18:05:31","updateTime":"2020-12-02 16:33:05","type":2,"areaId":5,"validityPeriod":null,"online":1,"onlineTime":"2021-01-13 10:36:00","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":19,"accountName":"管理员3-盐城","name":"林工3","password":"Exc123456789","gender":0,"phone":"15988881003","email":null,"status":1,"founderId":1,"createTime":"2020-12-09 10:37:02","updateTime":"2021-01-13 16:33:43","type":2,"areaId":8,"validityPeriod":null,"online":0,"onlineTime":"2021-01-20 11:34:20","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":20,"accountName":"管理员-盐城","name":"林工1","password":"Exc123456789","gender":0,"phone":"15988881001","email":null,"status":1,"founderId":1,"createTime":"2020-12-25 15:28:22","updateTime":"2021-03-19 09:18:44","type":2,"areaId":8,"validityPeriod":null,"online":0,"onlineTime":"2021-03-11 14:53:25","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":21,"accountName":"管理员1","name":"林工2","password":"Exc123456789","gender":0,"phone":"15988881002","email":null,"status":1,"founderId":1,"createTime":"2020-12-25 15:31:10","updateTime":"2021-03-19 09:18:12","type":2,"areaId":8,"validityPeriod":null,"online":1,"onlineTime":"2021-04-12 14:43:34","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":22,"accountName":"101","name":"101","password":"Exc123456789","gender":0,"phone":"13622222222","email":null,"status":1,"founderId":1,"createTime":"2020-12-25 15:59:33","updateTime":null,"type":2,"areaId":1,"validityPeriod":null,"online":1,"onlineTime":"2021-01-19 17:21:27","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":23,"accountName":"102","name":"102","password":"Exc123456789","gender":0,"phone":"15988888102","email":null,"status":1,"founderId":1,"createTime":"2020-12-30 16:44:53","updateTime":"2020-12-30 18:08:25","type":2,"areaId":8,"validityPeriod":null,"online":1,"onlineTime":null,"periodType":1,"failCount":null,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":24,"accountName":"103","name":"103","password":"Exc123456789","gender":0,"phone":"15988888103","email":null,"status":1,"founderId":1,"createTime":"2020-12-30 16:49:58","updateTime":null,"type":2,"areaId":8,"validityPeriod":null,"online":0,"onlineTime":"2021-01-12 17:29:57","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":25,"accountName":"104","name":"104","password":"Exc123456789","gender":0,"phone":"15988888104","email":null,"status":1,"founderId":1,"createTime":"2020-12-30 16:52:47","updateTime":"2021-01-08 18:01:53","type":2,"areaId":5,"validityPeriod":null,"online":1,"onlineTime":"2021-01-14 10:11:33","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null},{"id":26,"accountName":"李锋测试","name":"李锋测试","password":"Exc123456789","gender":0,"phone":"15988888889","email":null,"status":1,"founderId":1,"createTime":"2021-04-12 16:58:44","updateTime":"2021-04-12 17:04:26","type":2,"areaId":14,"validityPeriod":null,"online":0,"onlineTime":"2021-04-13 17:02:48","periodType":1,"failCount":0,"frozenTime":null,"forbidden":1,"roleIds":null,"picId":null,"headPicId":null}]
     */

    private int code;
    private String operate;
    private String message;
    private List<DataBean> data;

    @Data
    public static class DataBean {
        /**
         * id : 9
         * accountName : 测试华管理
         * name : 华仔666888
         * password : Exc123456789
         * gender : 0
         * phone : 18211101589
         * email : null
         * status : 1
         * founderId : 1
         * createTime : 2020-10-13 09:45:00
         * updateTime : 2020-12-30 17:43:23
         * type : 2
         * areaId : 2
         * validityPeriod : null
         * online : 1
         * onlineTime : 2021-01-20 11:32:01
         * periodType : 1
         * failCount : 0
         * frozenTime : null
         * forbidden : 1
         * roleIds : null
         * picId : null
         * headPicId : null
         */

        private int id;
        private String accountName;
        private String name;
        private String password;
        private int gender;
        private String phone;
        private Object email;
        private int status;
        private int founderId;
        private String createTime;
        private String updateTime;
        private int type;
        private int areaId;
        private Object validityPeriod;
        private int online;
        private String onlineTime;
        private int periodType;
        private int failCount;
        private Object frozenTime;
        private int forbidden;
        private Object roleIds;
        private Object picId;
        private Object headPicId;
    }
}
