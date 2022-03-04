package com.exc.roadlamp.device.bean;

import com.xuexiang.xui.widget.picker.wheelview.interfaces.IPickerViewItem;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProjectList implements Serializable {
    /**
     * code : 200
     * operate : success
     * message : 操作成功
     * data : [{"id":1,"name":"健仓路灯展厅","description":"别删除11","createTime":"2020-04-27 19:54:06","lampState":1,"lampBrightness":66,"longitude":113.841,"latitude":22.688}]
     */

    public int code;
    public String operate;
    public String message;
    public List<DataBean> data;

    @Data
    public static class DataBean implements IPickerViewItem {
        /**
         * id : 1
         * name : 健仓路灯展厅
         * description : 别删除11
         * createTime : 2020-04-27 19:54:06
         * lampState : 1
         * lampBrightness : 66
         * longitude : 113.841
         * latitude : 22.688
         */

        public int id;
        public String name;
        public String description;
        public String createTime;
        public int lampState;
        public int lampBrightness;
        public double longitude;
        public double latitude;

        @Override
        public String getPickerViewText() {
           return this.name;
        }
    }
}
