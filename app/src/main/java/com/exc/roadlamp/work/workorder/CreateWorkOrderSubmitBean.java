package com.exc.roadlamp.work.workorder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CreateWorkOrderSubmitBean implements Serializable {
    /**
     * name : 123
     * alarmTypeId : 3
     * description : 321313
     * addr : 1233123
     * imgIdList : [12,13]
     * lampPostId : 83
     */

    private String name;
    private int alarmTypeId;
    private String description;
    private String addr;
    private String lampPostId;
    private List<Integer> imgIdList;
}
