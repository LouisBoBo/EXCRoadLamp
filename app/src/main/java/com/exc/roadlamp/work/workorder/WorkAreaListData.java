package com.exc.roadlamp.work.workorder;


import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class WorkAreaListData implements Serializable {

    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":14,"partId":"area14","name":"深圳","description":null,"createTime":"2020-12-29 16:30:42","deviceNumber":2,"childrenList":[{"id":14,"partId":"street14","name":"粤海","description":null,"createTime":"2020-12-29 16:30:59","deviceNumber":2,"superId":14,"superName":"深圳","childrenList":[{"id":14,"partId":"site14","name":"深大1号","description":null,"createTime":"2020-12-29 16:32:11","deviceNumber":2,"superId":14,"superName":"粤海","childrenList":[{"id":84,"partId":"lampPost84","name":"LORA测试","lampPostNum":"ewfer","lampPostModel":"LORA","lampPostLongitude":113.959025,"lampPostLatitude":22.541624,"lampPostManufacturer":"EXC","lampPostLocation":null,"createTime":"2021-03-11 14:57:12","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":82,"partId":"lampPost82","name":"11013","lampPostNum":"11013","lampPostModel":null,"lampPostLongitude":115.91884,"lampPostLatitude":28.63909,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:58:49","deviceNumber":2,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":72,"partId":"lampPost72","name":"11003","lampPostNum":"11003","lampPostModel":"11003","lampPostLongitude":115.92477,"lampPostLatitude":28.63205,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-06 14:18:05","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":10,"partId":"area10","name":"常州","description":null,"createTime":"2020-12-09 10:33:40","deviceNumber":3,"childrenList":[{"id":13,"partId":"street13","name":"常州街道","description":null,"createTime":"2020-12-09 10:33:52","deviceNumber":3,"superId":10,"superName":"常州","childrenList":[{"id":13,"partId":"site13","name":"常州站点","description":null,"createTime":"2020-12-09 10:34:04","deviceNumber":3,"superId":13,"superName":"常州街道","childrenList":[{"id":56,"partId":"lampPost56","name":"常州灯杆2","lampPostNum":"11002","lampPostModel":null,"lampPostLongitude":115.92489,"lampPostLatitude":28.63214,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-12-09 10:35:16","deviceNumber":1,"superId":13,"superName":"常州站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":55,"partId":"lampPost55","name":"常州灯杆1","lampPostNum":"11001","lampPostModel":null,"lampPostLongitude":115.9249,"lampPostLatitude":28.63204,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-12-09 10:34:38","deviceNumber":2,"superId":13,"superName":"常州站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":8,"partId":"area8","name":"盐城","description":"+2","createTime":"2020-10-14 10:43:13","deviceNumber":38,"childrenList":[{"id":8,"partId":"street8","name":"街道1","description":null,"createTime":"2020-10-14 14:51:36","deviceNumber":38,"superId":8,"superName":"盐城","childrenList":[{"id":7,"partId":"site7","name":"站点1","description":null,"createTime":"2020-10-14 14:51:46","deviceNumber":38,"superId":8,"superName":"街道1","childrenList":[{"id":83,"partId":"lampPost83","name":"盐城灯杆","lampPostNum":"1008611","lampPostModel":null,"lampPostLongitude":113.985,"lampPostLatitude":22.696,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-03-03 16:51:40","deviceNumber":1,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":81,"partId":"lampPost81","name":"11012","lampPostNum":"11012","lampPostModel":null,"lampPostLongitude":115.91884,"lampPostLatitude":28.63914,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:57:53","deviceNumber":3,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":80,"partId":"lampPost80","name":"11010","lampPostNum":"11010","lampPostModel":null,"lampPostLongitude":115.91858,"lampPostLatitude":28.63908,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:51:12","deviceNumber":0,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":79,"partId":"lampPost79","name":"11007","lampPostNum":"11007","lampPostModel":null,"lampPostLongitude":115.92528,"lampPostLatitude":28.63203,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:50:25","deviceNumber":1,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":78,"partId":"lampPost78","name":"11009","lampPostNum":"11009","lampPostModel":null,"lampPostLongitude":115.91858,"lampPostLatitude":28.63912,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:50:25","deviceNumber":2,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":77,"partId":"lampPost77","name":"11008","lampPostNum":"11008","lampPostModel":null,"lampPostLongitude":115.92526,"lampPostLatitude":28.63215,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:47:53","deviceNumber":1,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":76,"partId":"lampPost76","name":"11006","lampPostNum":"11006","lampPostModel":null,"lampPostLongitude":115.92507,"lampPostLatitude":28.63204,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:45:13","deviceNumber":0,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":75,"partId":"lampPost75","name":"11005","lampPostNum":"11005","lampPostModel":null,"lampPostLongitude":115.92475,"lampPostLatitude":28.63214,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:40:08","deviceNumber":1,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":74,"partId":"lampPost74","name":"11004","lampPostNum":"11004","lampPostModel":null,"lampPostLongitude":115.92506,"lampPostLatitude":28.63214,"lampPostManufacturer":"测试厂家","lampPostLocation":"地下","createTime":"2021-01-07 14:40:08","deviceNumber":2,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":54,"partId":"lampPost54","name":"盐城灯杆6","lampPostNum":"10016","lampPostModel":null,"lampPostLongitude":115.91785,"lampPostLatitude":28.6391,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-26 14:48:03","deviceNumber":6,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":53,"partId":"lampPost53","name":"盐城灯杆5","lampPostNum":"10015","lampPostModel":null,"lampPostLongitude":115.91782,"lampPostLatitude":28.63903,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-26 14:46:25","deviceNumber":2,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":52,"partId":"lampPost52","name":"盐城灯杆4","lampPostNum":"10012","lampPostModel":null,"lampPostLongitude":120.154,"lampPostLatitude":33.39,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-26 14:20:49","deviceNumber":2,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":51,"partId":"lampPost51","name":"盐城灯杆3","lampPostNum":"10011","lampPostModel":null,"lampPostLongitude":120.153,"lampPostLatitude":33.401,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-26 14:19:52","deviceNumber":3,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":50,"partId":"lampPost50","name":"盐城灯杆2","lampPostNum":"10010","lampPostModel":null,"lampPostLongitude":113.98,"lampPostLatitude":22.566,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-25 16:33:49","deviceNumber":7,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":32,"partId":"lampPost32","name":"盐城灯杆1","lampPostNum":"00001","lampPostModel":"21213","lampPostLongitude":113.94063,"lampPostLatitude":22.53589,"lampPostManufacturer":"555522","lampPostLocation":"666111","createTime":"2020-10-20 21:02:17","deviceNumber":4,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":30,"partId":"lampPost30","name":"AFD ","lampPostNum":"ASDF ","lampPostModel":"ASDF ","lampPostLongitude":120.151,"lampPostLatitude":33.39,"lampPostManufacturer":"22","lampPostLocation":null,"createTime":"2020-10-14 15:09:36","deviceNumber":3,"superId":7,"superName":"站点1","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":6,"partId":"area6","name":"南山区（勿删）","description":"超管所属分区，勿删1","createTime":"2020-04-27 20:18:16","deviceNumber":4,"childrenList":[{"id":11,"partId":"street11","name":"粤海街道","description":null,"createTime":"2020-11-04 10:26:28","deviceNumber":4,"superId":6,"superName":"南山区（勿删）","childrenList":[{"id":10,"partId":"site10","name":"深大站点","description":null,"createTime":"2020-11-04 10:26:39","deviceNumber":4,"superId":11,"superName":"粤海街道","childrenList":[{"id":49,"partId":"lampPost49","name":"深大灯杆","lampPostNum":"dg004","lampPostModel":null,"lampPostLongitude":113.985,"lampPostLatitude":22.555,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-23 10:05:15","deviceNumber":3,"superId":10,"superName":"深大站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":48,"partId":"lampPost48","name":"深大灯杆-NB","lampPostNum":"sd002","lampPostModel":null,"lampPostLongitude":113.94147,"lampPostLatitude":22.5409,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-16 15:32:39","deviceNumber":0,"superId":10,"superName":"深大站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":45,"partId":"lampPost45","name":"深大灯杆-CAT1","lampPostNum":"sd001","lampPostModel":null,"lampPostLongitude":113.958,"lampPostLatitude":22.558,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-11-04 10:28:36","deviceNumber":1,"superId":10,"superName":"深大站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":5,"partId":"area5","name":"隆化县","description":null,"createTime":"2020-09-15 17:22:47","deviceNumber":9,"childrenList":[{"id":4,"partId":"street4","name":"苔山公园街","description":null,"createTime":"2020-09-15 17:26:47","deviceNumber":9,"superId":5,"superName":"隆化县","childrenList":[{"id":4,"partId":"site4","name":"路西","description":null,"createTime":"2020-09-15 17:27:29","deviceNumber":9,"superId":4,"superName":"苔山公园街","childrenList":[{"id":40,"partId":"lampPost40","name":"隆化灯杆3","lampPostNum":"dg003","lampPostModel":null,"lampPostLongitude":117.74,"lampPostLatitude":41.33,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-10-27 11:19:39","deviceNumber":1,"superId":4,"superName":"路西","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":39,"partId":"lampPost39","name":"隆化灯杆2","lampPostNum":"dg002","lampPostModel":"扫码NB","lampPostLongitude":117.7395,"lampPostLatitude":41.332,"lampPostManufacturer":"EXC","lampPostLocation":"灯杆顶部","createTime":"2020-10-27 10:42:08","deviceNumber":5,"superId":4,"superName":"路西","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":31,"partId":"lampPost31","name":"隆化灯杆1","lampPostNum":"dg001","lampPostModel":null,"lampPostLongitude":117.74,"lampPostLatitude":41.34,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-10-16 11:36:13","deviceNumber":3,"superId":4,"superName":"路西","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":4,"partId":"area4","name":"秋锦园1","description":"南运河秋锦园","createTime":"2020-09-15 14:38:30","deviceNumber":0,"childrenList":[{"id":3,"partId":"street3","name":"东绿道","description":null,"createTime":"2020-09-15 14:39:02","deviceNumber":0,"superId":4,"superName":"秋锦园1","childrenList":[{"id":3,"partId":"site3","name":"路灯","description":null,"createTime":"2020-09-15 14:39:33","deviceNumber":0,"superId":3,"superName":"东绿道","childrenList":[{"id":10,"partId":"lampPost10","name":"秋锦园01","lampPostNum":"NYH-QJY-01","lampPostModel":"欧普","lampPostLongitude":123.43514,"lampPostLatitude":41.785549,"lampPostManufacturer":"中山","lampPostLocation":null,"createTime":"2020-09-15 14:44:20","deviceNumber":0,"superId":3,"superName":"路灯","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":3,"partId":"area3","name":"沈阳1","description":null,"createTime":"2020-09-14 13:45:58","deviceNumber":3,"childrenList":[{"id":2,"partId":"street2","name":"运河","description":null,"createTime":"2020-09-14 13:46:14","deviceNumber":3,"superId":3,"superName":"沈阳1","childrenList":[{"id":2,"partId":"site2","name":"黎明园","description":null,"createTime":"2020-09-14 13:46:26","deviceNumber":3,"superId":2,"superName":"运河","childrenList":[{"id":7,"partId":"lampPost7","name":"黎明园-灯杆3","lampPostNum":"dg323456","lampPostModel":"exc-1","lampPostLongitude":111,"lampPostLatitude":22,"lampPostManufacturer":"exc","lampPostLocation":null,"createTime":"2020-09-14 13:52:41","deviceNumber":1,"superId":2,"superName":"黎明园","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":6,"partId":"lampPost6","name":"黎明园-灯杆2","lampPostNum":"dg223456","lampPostModel":"exc-1","lampPostLongitude":111,"lampPostLatitude":22,"lampPostManufacturer":"exc","lampPostLocation":null,"createTime":"2020-09-14 13:49:15","deviceNumber":2,"superId":2,"superName":"黎明园","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":2,"partId":"area2","name":"伊吾","description":"新疆伊吾项目124","createTime":"2020-08-24 10:01:47","deviceNumber":2,"childrenList":[{"id":7,"partId":"street7","name":"伊吾街道","description":null,"createTime":"2020-10-13 20:51:26","deviceNumber":2,"superId":2,"superName":"伊吾","childrenList":[{"id":6,"partId":"site6","name":"伊吾站点","description":null,"createTime":"2020-10-13 20:51:34","deviceNumber":2,"superId":7,"superName":"伊吾街道","childrenList":[{"id":29,"partId":"lampPost29","name":"伊吾灯杆2","lampPostNum":"jy002","lampPostModel":null,"lampPostLongitude":113.33,"lampPostLatitude":22.22,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-10-14 13:44:43","deviceNumber":0,"superId":6,"superName":"伊吾站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":28,"partId":"lampPost28","name":"伊吾灯杆1","lampPostNum":"yw001","lampPostModel":null,"lampPostLongitude":113.99,"lampPostLatitude":55.58,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-10-14 10:53:08","deviceNumber":2,"superId":6,"superName":"伊吾站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null},{"id":1,"partId":"area1","name":"沈阳","description":"沈阳项目","createTime":"2020-08-24 10:01:14","deviceNumber":2,"childrenList":[{"id":1,"partId":"street1","name":"测试街道","description":"测试街道","createTime":"2020-08-24 17:04:21","deviceNumber":2,"superId":1,"superName":"沈阳","childrenList":[{"id":1,"partId":"site1","name":"测试站点","description":"测试站点","createTime":"2020-08-24 17:04:30","deviceNumber":2,"superId":1,"superName":"测试街道","childrenList":[{"id":43,"partId":"lampPost43","name":"cgcsss","lampPostNum":"123","lampPostModel":null,"lampPostLongitude":21,"lampPostLatitude":21,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-10-28 18:27:45","deviceNumber":0,"superId":1,"superName":"测试站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":3,"partId":"lampPost3","name":"南山中科智联测试灯杆","lampPostNum":"nstest001","lampPostModel":null,"lampPostLongitude":113.95268,"lampPostLatitude":22.53599,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2020-09-08 14:55:33","deviceNumber":2,"superId":1,"superName":"测试站点","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}],"lampState":null,"lampBrightness":null}]
     */

    private int code;
    private String operate;
    private String message;
    private List<DataBean> data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * id : 14
         * partId : area14
         * name : 深圳
         * description : null
         * createTime : 2020-12-29 16:30:42
         * deviceNumber : 2
         * childrenList : [{"id":14,"partId":"street14","name":"粤海","description":null,"createTime":"2020-12-29 16:30:59","deviceNumber":2,"superId":14,"superName":"深圳","childrenList":[{"id":14,"partId":"site14","name":"深大1号","description":null,"createTime":"2020-12-29 16:32:11","deviceNumber":2,"superId":14,"superName":"粤海","childrenList":[{"id":84,"partId":"lampPost84","name":"LORA测试","lampPostNum":"ewfer","lampPostModel":"LORA","lampPostLongitude":113.959025,"lampPostLatitude":22.541624,"lampPostManufacturer":"EXC","lampPostLocation":null,"createTime":"2021-03-11 14:57:12","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":82,"partId":"lampPost82","name":"11013","lampPostNum":"11013","lampPostModel":null,"lampPostLongitude":115.91884,"lampPostLatitude":28.63909,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:58:49","deviceNumber":2,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":72,"partId":"lampPost72","name":"11003","lampPostNum":"11003","lampPostModel":"11003","lampPostLongitude":115.92477,"lampPostLatitude":28.63205,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-06 14:18:05","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]}]
         * lampState : null
         * lampBrightness : null
         */

        private int id;
        private String partId;
        private String name;
        private Object description;
        private String createTime;
        private int deviceNumber;
        private Object lampState;
        private Object lampBrightness;
        private List<ChildrenListBeanXX> childrenList;

        @Data
        public static class ChildrenListBeanXX implements Serializable {
            /**
             * id : 14
             * partId : street14
             * name : 粤海
             * description : null
             * createTime : 2020-12-29 16:30:59
             * deviceNumber : 2
             * superId : 14
             * superName : 深圳
             * childrenList : [{"id":14,"partId":"site14","name":"深大1号","description":null,"createTime":"2020-12-29 16:32:11","deviceNumber":2,"superId":14,"superName":"粤海","childrenList":[{"id":84,"partId":"lampPost84","name":"LORA测试","lampPostNum":"ewfer","lampPostModel":"LORA","lampPostLongitude":113.959025,"lampPostLatitude":22.541624,"lampPostManufacturer":"EXC","lampPostLocation":null,"createTime":"2021-03-11 14:57:12","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":82,"partId":"lampPost82","name":"11013","lampPostNum":"11013","lampPostModel":null,"lampPostLongitude":115.91884,"lampPostLatitude":28.63909,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:58:49","deviceNumber":2,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":72,"partId":"lampPost72","name":"11003","lampPostNum":"11003","lampPostModel":"11003","lampPostLongitude":115.92477,"lampPostLatitude":28.63205,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-06 14:18:05","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]}]
             */

            private int id;
            private String partId;
            private String name;
            private Object description;
            private String createTime;
            private int deviceNumber;
            private int superId;
            private String superName;
            private List<ChildrenListBeanX> childrenList;

            @Data
            public static class ChildrenListBeanX implements Serializable {
                /**
                 * id : 14
                 * partId : site14
                 * name : 深大1号
                 * description : null
                 * createTime : 2020-12-29 16:32:11
                 * deviceNumber : 2
                 * superId : 14
                 * superName : 粤海
                 * childrenList : [{"id":84,"partId":"lampPost84","name":"LORA测试","lampPostNum":"ewfer","lampPostModel":"LORA","lampPostLongitude":113.959025,"lampPostLatitude":22.541624,"lampPostManufacturer":"EXC","lampPostLocation":null,"createTime":"2021-03-11 14:57:12","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":82,"partId":"lampPost82","name":"11013","lampPostNum":"11013","lampPostModel":null,"lampPostLongitude":115.91884,"lampPostLatitude":28.63909,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-07 14:58:49","deviceNumber":2,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null},{"id":72,"partId":"lampPost72","name":"11003","lampPostNum":"11003","lampPostModel":"11003","lampPostLongitude":115.92477,"lampPostLatitude":28.63205,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-06 14:18:05","deviceNumber":0,"superId":14,"superName":"深大1号","areaId":null,"areaName":null,"streetId":null,"streetName":null,"ids":null,"names":null,"childrenList":null}]
                 */

                private int id;
                private String partId;
                private String name;
                private Object description;
                private String createTime;
                private int deviceNumber;
                private int superId;
                private String superName;
                private List<ChildrenListBean> childrenList;

                @Data
                public static class ChildrenListBean implements Serializable {
                    /**
                     * id : 84
                     * partId : lampPost84
                     * name : LORA测试
                     * lampPostNum : ewfer
                     * lampPostModel : LORA
                     * lampPostLongitude : 113.959025
                     * lampPostLatitude : 22.541624
                     * lampPostManufacturer : EXC
                     * lampPostLocation : null
                     * createTime : 2021-03-11 14:57:12
                     * deviceNumber : 0
                     * superId : 14
                     * superName : 深大1号
                     * areaId : null
                     * areaName : null
                     * streetId : null
                     * streetName : null
                     * ids : null
                     * names : null
                     * childrenList : null
                     */

                    private int id;
                    private String partId;
                    private String name;
                    private String lampPostNum;
                    private String lampPostModel;
                    private double lampPostLongitude;
                    private double lampPostLatitude;
                    private String lampPostManufacturer;
                    private Object lampPostLocation;
                    private String createTime;
                    private int deviceNumber;
                    private int superId;
                    private String superName;
                    private Object areaId;
                    private Object areaName;
                    private Object streetId;
                    private Object streetName;
                    private Object ids;
                    private Object names;
                    private Object childrenList;
                }
            }
        }
    }
}
