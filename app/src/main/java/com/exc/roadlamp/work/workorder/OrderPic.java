package com.exc.roadlamp.work.workorder;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderPic   {
    /**
     * 1为已经上传成功的图片（路径为本地图片），2为查看工单详情时的图片（网络路径）
     */
    private int fileType;
    private String filename;
    private int id;
//    private int orderId;
    private String realname;
    private boolean isVirtual;
    private boolean isXC;//是否是相册图片
}
