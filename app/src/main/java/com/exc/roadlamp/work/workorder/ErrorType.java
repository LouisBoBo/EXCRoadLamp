package com.exc.roadlamp.work.workorder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ErrorType implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"name":"灯控器离线"},{"id":2,"name":"网关离线"},{"id":3,"name":"灯控器电流异常"},{"id":4,"name":"灯控器电压异常"},{"id":5,"name":"灯控器温度异常"},{"id":6,"name":"摄像头离线"},{"id":7,"name":"显示屏离线"},{"id":8,"name":"传感器离线"},{"id":9,"name":"广播离线"},{"id":10,"name":"其他"},{"id":11,"name":"多种类型"},{"id":12,"name":"灯杆倾斜异常"},{"id":13,"name":"灯控器漏电异常"},{"id":14,"name":"垃圾桶溢满异常"},{"id":15,"name":"井盖开盖异常"},{"id":16,"name":"自动重合闸欠压异常"},{"id":17,"name":"自动重合闸过压异常"},{"id":18,"name":"自动重合闸过流异常"},{"id":19,"name":"自动重合闸短路异常"},{"id":20,"name":"自动重合闸漏电异常"},{"id":21,"name":"自动重合闸缺相异常"},{"id":22,"name":"智能锁异常开门"},{"id":23,"name":"超出警戒水位"},{"id":24,"name":"灯具损坏异常"},{"id":25,"name":"pm2.5"},{"id":26,"name":"pm10"},{"id":27,"name":"噪音"},{"id":28,"name":"风速"},{"id":29,"name":"温度"},{"id":30,"name":"湿度"},{"id":31,"name":"气压"},{"id":32,"name":"雨量"},{"id":33,"name":"火灾事故"},{"id":34,"name":"交通拥挤"},{"id":35,"name":"交通事故"},{"id":36,"name":"人群聚集"},{"id":37,"name":"打架斗殴"},{"id":38,"name":"一键求助告警"},{"id":39,"name":"电缆防盗告警"},{"id":40,"name":"电缆防盗前端配电箱开门"},{"id":41,"name":"电缆防盗分机配电箱开门"},{"id":42,"name":"电缆防盗前端设备防拆"},{"id":43,"name":"电缆防盗分机设备防拆"},{"id":44,"name":"电缆防盗前端线路报警"},{"id":45,"name":"电缆防盗分机线路报警"},{"id":46,"name":"电缆防盗前端A相报警"},{"id":47,"name":"电缆防盗分机A相报警"},{"id":48,"name":"电缆防盗前端B相报警"},{"id":49,"name":"电缆防盗分机B相报警"},{"id":50,"name":"电缆防盗前端C相报警"},{"id":51,"name":"电缆防盗分机C相报警"},{"id":52,"name":"电缆防盗前端三相停电"},{"id":53,"name":"电缆防盗分机三相停电"}]
     */

    private int code;
    private String operate;
    private String message;
    private List<DataBean> data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * id : 1
         * name : 灯控器离线
         */

        private int id;
        private String name;
    }
}
