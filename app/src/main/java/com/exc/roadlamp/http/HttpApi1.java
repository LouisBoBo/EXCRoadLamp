package com.exc.roadlamp.http;


public class HttpApi1 {
    /**
     * 设备控制
     */
    public static final String DEVICE_CONTROL = HttpApi.SERVICES_ADDRESS_DEVICE_CONTROL + HttpApi.DEVICE_CONTROL_PORT + "/api/sl/lamp/device/control";

    /**
     * 查询配电柜集合
     */
    public static final String GET_POWER_CABINET_LIST = HttpApi.SERVICES_ADDRESS_DEVICE_POWER_CABINET + HttpApi.LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet/page";


    /**
     * 查询配电柜详情（主要是获取集控）
     */
    public static final String GET_POWER_CABINET_DETAIL = HttpApi.SERVICES_ADDRESS_DEVICE_POWER_CABINET + HttpApi.LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet";


    /**
     * 查询集控列表
     */
    public static final String GET_CENTER_CONTROL_LIST = HttpApi.SERVICES_ADDRESS_DEVICE_POWER_CABINET + HttpApi.LAMP_LOCATION_PORT + "/api/dlm/location/control/page";


    /**
     * 查询集控详情
     */
    public static final String GET_CENTER_CONTROL_DETAIL = HttpApi.SERVICES_ADDRESS_DEVICE_POWER_CABINET + HttpApi.LAMP_LOCATION_PORT + "/api/dlm/location/control";


    /**
     * 查询所有的回路
     */
    public static final String GET_ALL_LOOPS = HttpApi.SERVICES_ADDRESS_DEVICE_POWER_CABINET + HttpApi.LAMP_LOCATION_PORT + "/api/dlm/control/loop/page";

}
