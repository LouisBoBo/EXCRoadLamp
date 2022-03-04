package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 地图上集控详情所需要的数据
 */
@Data
public class CenterControlAllData implements Serializable {
    /**
     * 集控的基本信息（包含集控所在的配电柜）
     */
    public CenterControlListData.DataBean.RecordsBean centerControl;

    /**
     * 集控详情
     */
    public CenterControlDetail.DataBean centerControlDetail;


    /**
     * 回路列表
     */
    public List<AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean> loopsList;


}
