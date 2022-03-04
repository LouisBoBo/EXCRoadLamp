package com.exc.roadlamp.http;

public class HttpApi {
    //公共
//    public static  String SERVICES_ADDRESS ;
//    public static  String SERVICES_ADDRESS_LAMP_POLE;
//    public static  String SERVICES_ADDRESS_DEVICE_CONTROL;
//    public static  String SERVICES_ADDRESS_DEVICE_POWER_CABINET ;

    //    生产环境 15988881001 Exc123456789
//    public static  String SERVICES_ADDRESS = "http://42.194.210.113";
//    public static  String SERVICES_ADDRESS_LAMP_POLE = "http://42.194.208.18";
//    public static  String SERVICES_ADDRESS_DEVICE_CONTROL = "http://106.55.15.212";
//    public static  String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://42.194.208.18";

    //测试环境 15988888888 Exc741852963
    public static String SERVICES_ADDRESS = "http://192.168.18.202";
    public static String SERVICES_ADDRESS_LAMP_POLE = "http://192.168.18.201";
    public static String SERVICES_ADDRESS_DEVICE_CONTROL = "http://192.168.18.201";
    public static String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://192.168.18.201";

    //罗书文 15988888888 Exc741852963
//    public static final String SERVICES_ADDRESS = "http://192.168.112.112";
//    public static final String SERVICES_ADDRESS_LAMP_POLE = "http://192.168.112.112";
//    public static final String SERVICES_ADDRESS_DEVICE_CONTROL = "http://192.168.112.112";
//    public static final String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://192.168.112.112";

    //罗鑫
//    public static final String SERVICES_ADDRESS = "http://192.168.112.128";
//    public static final String SERVICES_ADDRESS_LAMP_POLE = "http://192.168.112.128";
//    public static final String SERVICES_ADDRESS_DEVICE_CONTROL = "http://192.168.112.128";
//    public static final String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://192.168.112.128";

    //隆化项目
//    public static final String SERVICES_ADDRESS = "http://121.26.223.53";
//    public static final String SERVICES_ADDRESS_LAMP_POLE = "http://121.26.223.53";
//    public static final String SERVICES_ADDRESS_DEVICE_CONTROL = "http://121.26.223.53";
//    public static final String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://121.26.223.53";

    //南昌惜能项目
//    public static final String SERVICES_ADDRESS = "http://220.175.120.118";
//    public static final String SERVICES_ADDRESS_LAMP_POLE = "http://220.175.120.118";
//    public static final String SERVICES_ADDRESS_DEVICE_CONTROL = "http://220.175.120.118";
//    public static final String SERVICES_ADDRESS_DEVICE_POWER_CABINET = "http://220.175.120.118";

    /**
     * dlm：60027
     * sl：60020
     * ua：60029
     * woa：60028
     */
    public static final String SERVICES_PORT = ":60029";
    public static final String LAMP_LOCATION_PORT = ":60027";
    public static final String ORDER_PORT = ":60028/";
    public static final String DEVICE_CONTROL_PORT = ":60020";
    public static final String LOGIN_PORT = ":62126";
    public static final String JINGAI_PORT = ":60032";


    /**
     * 获取登录公钥
     */
    public static String LOGIN_GET_PUBLIC_KEY = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/public/key";
    /**
     * 登录
     */
    public static String LOGIN = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/login";

    /**
     * 工单-灯杆列表
     */
    public static String GET_LAMP_ALL_LOCATION = SERVICES_ADDRESS + LAMP_LOCATION_PORT + "/api/dlm/location/area/list";
    /**
     * 工单-故障类型列表
     */
    public static String GET_LAMP_ERROR_TYPE_LIST = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/type/pulldown";
    /**
     * 图片上传
     */
    public static String UPLOAD_IMG = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/pic/upload";

    /**
     * 创建工单
     */
    public static String ORDER_ADD = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/add";

    /**
     * 工单-工单列表
     */
    public static String ORDER_LIST = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/list/handle";
//    public static final String ORDER_LIST = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/list/all";
    /**
     * 工单-工单详情
     */
    public static String ORDER_DETAIL = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/get/";

    public static String ORDER_CHUSHEN = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/first/trial";

    public static String ORDER_OPERATOR_LIST = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/pull/down";

    /**
     * 获取带设备基础信息的灯杆详情（和灯杆相关的设备都是这个接口）
     */
    public static String GET_ALL_LAMP_POLE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/get";

    /**
     * 设备在线数据
     */
    public static String POST_NUMBER_COUNT = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/map/number/count";

    /**
     * 集控设备场景回路下发
     */
    public static String XIA_FA_LOOP = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/loop/switch";

    /**
     * 集控设备场景回路下发
     */
    public static String GET_PROJECT_LIST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/pulldown";

    /**
     * 集控设备下关联的灯具
     */
    public static String API_DLM_SL_LAMP_POST_GETPLCBYLOCATIONCONTROID = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/getPlcByLocationControId?locationControId=";
    /**
     * 太阳能统计
     */
    public static String SL_LAMP_SOLARLAMP_APP_HISTORY = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/solarLamp/app/history";

    /**
     * 集控能耗报表
     */
    public static String DLM_CONTROL_ENERGY_COUNT = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/energy/count";

    /**
     * 亮灯率
     */
    public static String API_SL_LAMP_ENERGY_WEEKLIGHTINGRATE = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/energy/weekLightingRate";

    /**
     * 工单概况
     */
    public static String API_WOA_ORDER_ANALYSIS = SERVICES_ADDRESS + ORDER_PORT + "/api/woa/order/analysis";


    /**
     * 告警信息
     */
    public static String API_WOA_ALARM_QUERY = SERVICES_ADDRESS + ORDER_PORT + "/api/woa/alarm/query";


    /**
     * 我的消息
     */
    public static String API_WOA_ALARM_NEWS = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news";

    /**
     * 修改消息状态
     */
    public static String API_WOA_ALARM_NEWS_STATUS = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news/status";

    /**
     * 消息全读
     */
    public static String API_WOA_ALARM_NEWS_ALL = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news/all";

    /**
     * 单灯能耗统计
     */
    public static String API_SL_LAMP_ENERGY_COUNT = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/energy/count";

    /**
     * 告警统计
     */
    public static String API_WOA_ALARM_COUNT = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/count";

    /**
     * 用户详情
     */
    public static String API_UA_USER_ID = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/";

    /**
     * 编辑用户
     */
    public static String API_UA_USER = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user";

    /**
     * 新修改密码
     */
    public static String API_UA_USER_MODIFY_PASSWORD = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/modify/password";

    /**
     * APPKEY
     */
    public static String APPKEY = "bfe8b2bb9748c4baae2adcf2cada248f";

    /**
     * API_KEY
     */
    public static String API_KEY = "db019971c4d3ee1a3d66514d5bb69e95";

    /**
     * 检测APP是否有更新
     */
    public static String API_CHECK_APP = "https://www.pgyer.com/apiv2/app/check?" + "_api_key=db019971c4d3ee1a3d66514d5bb69e95&appKey=bfe8b2bb9748c4baae2adcf2cada248f";

    /**
     * APP安装
     */
    public static String API_INSTALL_APP = "https://www.pgyer.com/apiv2/app/install";

    /**
     * APP下载地址
     */
    public static String APP_DOWNLOAD_URL = "https://www.pgyer.com/apiv2/app/install?_api_key=db019971c4d3ee1a3d66514d5bb69e95&appKey=bfe8b2bb9748c4baae2adcf2cada248f";

    /**
     * 灯杆分页条件查询
     */
    public static String API_DLM_SL_LAMP_POST_PAGE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/page";

    /**
     * 添加灯杆
     */
    public static String API_DLM_SL_LAMP_POST_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/add";

    /**
     * 添加灯杆灯具
     */
    public static String API_DLM_SL_LAMP_POST_APPADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/appAdd";

    /**
     * 编辑灯杆
     */
    public static String API_DLM_SL_LAMP_POST_APPUPDATE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/appUpdate";

    /**
     * 删除灯杆
     */
    public static String API_DLM_SL_LAMP_POST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/";

    /**
     * 批量删除灯杆
     */
    public static String API_DLM_SL_LAMP_POST_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/batch";

    /**
     * 修改灯杆
     */
    public static String API_DLM_SL_LAMP_POST_UPDATE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/update";

    /**
     * 根据灯杆id查询灯杆详情
     */
    public static String API_DLM_SL_LAMP_POST_DETAIL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/";

    /**
     * 根据灯杆id查询灯杆下的设备
     */
    public static String API_DLM_SL_LAMP_POST_GET = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/get";

    /**
     * 分区街道站点
     */
    public static String API_DLM_LOCATION_AREA_LIST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/list";

    /**
     * 添加灯具
     */
    public static String API_SL_LAMP_DEVICE_ADD = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/add";

    public static String API_SL_LAMP_DEVICE_APPADD = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/appAdd";

    /**
     * 灯具详情
     */
    public static String API_SL_LAMP_DEVICE_DETAIL = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/detail/";

    /**
     * 批量删除灯具
     */
    public static String API_SL_LAMP_DEVICE_DELETELIST = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/deleteList/";

    /**
     * 灯具位置
     */
    public static String API_SL_LAMP_POSITION_LIST = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/position/list";

    /**
     * 灯具分页条件查询
     */
    public static String API_SL_LAMP_DEVICE_PAGE = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/page";

    /**
     * 灯具控制
     */
    public static String API_DLM_LOCATION_AREA_WHOLECONTROL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/wholeControl";

    /**
     * 绑定灯具
     */
    public static String API_DLM_SYSTEM_DEVICE_PLC_BINDADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/system/device/plc/bindAdd";

    /**
     * 编辑灯具
     */
    public static String API_SL_LAMP_DEVICE_UNIQUE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/unique";
    public static String API_SL_LAMP_DEVICE_UPDATE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/update";

    /**
     * 灯控策略
     */
    public static String API_SL_LAMP_STRATEGY_PAGE = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/page";

    /**
     * 场景策略
     */
    public static String API_DLM_LOOP_SCENE_STRATEGY_PAGE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/page";

    /**
     * 场景策略编辑
     */
    public static String API_DLM_LOOP_SCENE_STRATEGY = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/";

    /**
     * 下发策略
     */
    public static String API_SL_LAMP_STRATEGY_EXECUTE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/execute";

    /**
     * 下发场景
     */
    public static String API_DLM_LOOP_SCENE_STRATEGY_EXECUTE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/execute";

    /**
     * 策略编辑
     */
    public static String API_SL_LAMP_STRATEGY = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/";

    /**
     * 灯具动作类型
     */
    public static String API_SL_LAMP_LIGHT_MODE_OPTION = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/light/mode/option";

    /**
     * 场景动作类型
     */
    public static String API_SL_SYSTEM_TIMING_MODE_OPTION = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/system/timing/mode/option";

    /**
     * 策略类型
     */
    public static String API_SL_LAMP_STRATEGY_TYPE_PULLDOWN = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/type/pulldown";

    /**
     * 添加配电柜
     */
    public static String API_DLM_LOCATION_DISTRIBUTE_CABINET_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet";

    /**
     * 配电柜详情
     */
    public static String API_DLM_LOCATION_DISTRIBUTE_CABINET = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet/";

    /**
     * 批量删除配电柜
     */
    public static String API_DLM_LOCATION_DISTRIBUTE_CABINET_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet/batch/";

    /**
     * 添加集控
     */
    public static String API_DLM_LOCATION_CONTROL_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control";

    /**
     * 集控详情
     */
    public static String API_DLM_LOCATION_CONTROL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/";

    /**
     * 批量删除集控
     */
    public static String API_DLM_LOCATION_CONTROL_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/batch/";

    /**
     * 井盖
     */
    public static String API_ED_ED_MANHOLE_COVER_DEVICE_PAGE = SERVICES_ADDRESS_LAMP_POLE + JINGAI_PORT + "/api/ed/ed/manhole/cover/device/page";

    /**
     * 根据设备查询设备类型  device_type设备  issue_type灯杆
     */
    public static String API_DLM_SYSTEM_AREA_PARAMETER_BYFILED = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/system/area/parameter/byFiled";

    /**
     * 集控设备类型
     */
    public static String API_DLM_LOCATION_CONTROL_TYPE_OPTION = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/type/option";

    /**
     * 集控回路控制全开全关
     */
    public static String API_DLM_CONTROL_LOOP_SWITCHALL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/loop/switchAll";

    /**
     * 单灯下发控制
     */
    public static String DEVICE_CONTROL = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/control";

    /**
     * 氛围灯开启关闭播放
     */
    public static String API_SL_SYSTEM_DEVICE_SINGLESETATMOSPHEREPARAM = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/system/device/singleSetAtmosphereParam";

    /**
     * 查询氛围灯节目
     */
    public static String API_SL_LAMP_ATMO_PROGRAM_PULLDOWN = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/atmo/program/pulldown";


    public void setIP(String adress,String address_lamp_pole, String services_address_device_control,String services_address_device_power_cabinet) {
        SERVICES_ADDRESS = adress;
        SERVICES_ADDRESS_LAMP_POLE = address_lamp_pole;
        SERVICES_ADDRESS_DEVICE_CONTROL = services_address_device_control;
        SERVICES_ADDRESS_DEVICE_POWER_CABINET = services_address_device_power_cabinet;

        LOGIN_GET_PUBLIC_KEY = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/public/key";

        LOGIN = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/login";

        GET_LAMP_ALL_LOCATION = SERVICES_ADDRESS + LAMP_LOCATION_PORT + "/api/dlm/location/area/list";

        GET_LAMP_ERROR_TYPE_LIST = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/type/pulldown";

        UPLOAD_IMG = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/pic/upload";

        ORDER_ADD = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/add";

        ORDER_LIST = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/list/handle";

        ORDER_DETAIL = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/get/";

        ORDER_CHUSHEN = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/first/trial";

        ORDER_OPERATOR_LIST = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/pull/down";

        GET_ALL_LAMP_POLE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/get";

        POST_NUMBER_COUNT = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/map/number/count";

        XIA_FA_LOOP = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/loop/switch";

        GET_PROJECT_LIST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/pulldown";

        API_DLM_SL_LAMP_POST_GETPLCBYLOCATIONCONTROID = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/getPlcByLocationControId?locationControId=";

        SL_LAMP_SOLARLAMP_APP_HISTORY = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/solarLamp/app/history";

        DLM_CONTROL_ENERGY_COUNT = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/energy/count";

        API_SL_LAMP_ENERGY_WEEKLIGHTINGRATE = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/energy/weekLightingRate";

        API_WOA_ORDER_ANALYSIS = SERVICES_ADDRESS + ORDER_PORT + "api/woa/order/analysis";

        API_WOA_ALARM_QUERY = SERVICES_ADDRESS + ORDER_PORT + "/api/woa/alarm/query";

        API_WOA_ALARM_NEWS = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news";

        API_WOA_ALARM_NEWS_STATUS = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news/status";

        API_WOA_ALARM_NEWS_ALL = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/news/all";

        API_SL_LAMP_ENERGY_COUNT = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/energy/count";

        API_WOA_ALARM_COUNT = SERVICES_ADDRESS + ORDER_PORT + "api/woa/alarm/count";

        API_UA_USER_ID = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/";

        API_UA_USER = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user";

        API_UA_USER_MODIFY_PASSWORD = SERVICES_ADDRESS + SERVICES_PORT + "/api/ua/user/modify/password";

        APPKEY = "bfe8b2bb9748c4baae2adcf2cada248f";

        API_KEY = "db019971c4d3ee1a3d66514d5bb69e95";

        API_CHECK_APP = "https://www.pgyer.com/apiv2/app/check?" + "_api_key=db019971c4d3ee1a3d66514d5bb69e95&appKey=bfe8b2bb9748c4baae2adcf2cada248f";

        API_INSTALL_APP = "https://www.pgyer.com/apiv2/app/install";

        APP_DOWNLOAD_URL = "https://www.pgyer.com/apiv2/app/install?_api_key=db019971c4d3ee1a3d66514d5bb69e95&appKey=bfe8b2bb9748c4baae2adcf2cada248f";

        API_DLM_SL_LAMP_POST_PAGE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/page";

        API_DLM_SL_LAMP_POST_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/add";

        API_DLM_SL_LAMP_POST_APPADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/appAdd";

        API_DLM_SL_LAMP_POST_APPUPDATE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/appUpdate";

        API_DLM_SL_LAMP_POST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/";

        API_DLM_SL_LAMP_POST_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/batch";

        API_DLM_SL_LAMP_POST_UPDATE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/update";

        API_DLM_SL_LAMP_POST_DETAIL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/";

        API_DLM_SL_LAMP_POST_GET = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/sl/lamp/post/get";

        API_DLM_LOCATION_AREA_LIST = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/list";

        API_SL_LAMP_DEVICE_ADD = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/add";

        API_SL_LAMP_DEVICE_APPADD = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/appAdd";

        API_SL_LAMP_DEVICE_DETAIL = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/detail/";

        API_SL_LAMP_DEVICE_DELETELIST = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/deleteList/";

        API_SL_LAMP_POSITION_LIST = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/position/list";

        API_SL_LAMP_DEVICE_PAGE = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/page";

        API_DLM_LOCATION_AREA_WHOLECONTROL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/area/wholeControl";

        API_DLM_SYSTEM_DEVICE_PLC_BINDADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/system/device/plc/bindAdd";

        API_SL_LAMP_DEVICE_UNIQUE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/unique";

        API_SL_LAMP_DEVICE_UPDATE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/update";

        API_SL_LAMP_STRATEGY_PAGE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/page";

        API_DLM_LOOP_SCENE_STRATEGY_PAGE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/page";

        API_DLM_LOOP_SCENE_STRATEGY = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/";

        API_SL_LAMP_STRATEGY_EXECUTE = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/execute";

        API_DLM_LOOP_SCENE_STRATEGY_EXECUTE = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/loop/scene/strategy/execute";

        API_SL_LAMP_STRATEGY = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/";

        API_SL_LAMP_LIGHT_MODE_OPTION = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/light/mode/option";

        API_SL_SYSTEM_TIMING_MODE_OPTION = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/system/timing/mode/option";

        API_SL_LAMP_STRATEGY_TYPE_PULLDOWN = SERVICES_ADDRESS_LAMP_POLE + DEVICE_CONTROL_PORT + "/api/sl/lamp/strategy/type/pulldown";

        API_DLM_LOCATION_DISTRIBUTE_CABINET_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet";

        API_DLM_LOCATION_DISTRIBUTE_CABINET = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet/";

        API_DLM_LOCATION_DISTRIBUTE_CABINET_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/distribute/cabinet/batch/";

        API_DLM_LOCATION_CONTROL_ADD = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control";

        API_DLM_LOCATION_CONTROL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/";

        API_DLM_LOCATION_CONTROL_BATCH = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/batch/";

        API_DLM_SYSTEM_AREA_PARAMETER_BYFILED = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/system/area/parameter/byFiled";

        API_DLM_LOCATION_CONTROL_TYPE_OPTION = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/location/control/type/option";

        API_DLM_CONTROL_LOOP_SWITCHALL = SERVICES_ADDRESS_LAMP_POLE + LAMP_LOCATION_PORT + "/api/dlm/control/loop/switchAll";

        DEVICE_CONTROL = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/device/control";

        API_SL_SYSTEM_DEVICE_SINGLESETATMOSPHEREPARAM = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/system/device/singleSetAtmosphereParam";

        API_SL_LAMP_ATMO_PROGRAM_PULLDOWN = SERVICES_ADDRESS_DEVICE_CONTROL + DEVICE_CONTROL_PORT + "/api/sl/lamp/atmo/program/pulldown";
    }
}
