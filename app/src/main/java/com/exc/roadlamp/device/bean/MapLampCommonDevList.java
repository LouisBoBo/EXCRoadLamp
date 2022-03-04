package com.exc.roadlamp.device.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


/**
 * 灯杆相关通用的bean
 */
@Data
public class MapLampCommonDevList implements Serializable {
    /**
     * code : 200
     * operate : success
     * message :
     * data : [{"id":1675,"partId":"lampPost1675","name":"智慧市政院02","lampPostNum":"2222","lampPostModel":"功能01","lampPostLongitude":null,"lampPostLatitude":null,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-04-21 19:09:03","deviceNumber":2,"superId":1,"superName":"健仓站点","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,1","names":"宝安区,松岗街道,健仓站点","childrenList":[{"id":2089,"partId":"device2089","num":"866222058228787","name":"金钢铃02","networkState":0,"lastOnlineTime":"2021-05-04 18:12:59","deviceType":1,"lampPostId":1675,"superId":1675,"superName":"智慧市政院02","dlmRespDevice":{"id":2089,"model":null,"deviceTypeId":14,"factory":"EXC1","name":"金钢铃02","num":"866222058228787","isOnline":0,"deviceState":0,"brightness":45,"createTime":"2021-04-21 19:29:01","lastOnlineTime":"2021-05-04 18:12:59","lampPostId":1675,"lampPostName":"智慧市政院02","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2089,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":121},{"deviceId":2089,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":123},{"deviceId":2089,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:50","parameterId":185},{"deviceId":2089,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:52","parameterId":197}]}},{"id":2090,"partId":"device2090","num":"866222058228787","name":"顶灯02","networkState":0,"lastOnlineTime":"2021-05-04 18:12:59","deviceType":1,"lampPostId":1675,"superId":1675,"superName":"智慧市政院02","dlmRespDevice":{"id":2090,"model":null,"deviceTypeId":22,"factory":"EXC1","name":"顶灯02","num":"866222058228787","isOnline":0,"deviceState":1,"brightness":null,"createTime":"2021-04-21 19:29:01","lastOnlineTime":"2021-05-04 18:12:59","lampPostId":1675,"lampPostName":"智慧市政院02","slLampPost":null,"lampPosition":"氛围灯","lampPositionId":3,"strategyId":268,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2090,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":257},{"deviceId":2090,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":258},{"deviceId":2090,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"氛围灯亮度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"94","parameterId":260}]}}]},{"id":1674,"partId":"lampPost1674","name":"智慧市政院01","lampPostNum":"1111","lampPostModel":"01功能","lampPostLongitude":null,"lampPostLatitude":null,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-04-21 19:08:18","deviceNumber":2,"superId":1,"superName":"健仓站点","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,1","names":"宝安区,松岗街道,健仓站点","childrenList":[{"id":2087,"partId":"device2087","num":"866222058128250","name":"金钢铃01","networkState":0,"lastOnlineTime":"2021-04-22 14:36:40","deviceType":1,"lampPostId":1674,"superId":1674,"superName":"智慧市政院01","dlmRespDevice":{"id":2087,"model":null,"deviceTypeId":14,"factory":"EXC1","name":"金钢铃01","num":"866222058128250","isOnline":0,"deviceState":1,"brightness":45,"createTime":"2021-04-21 19:28:23","lastOnlineTime":"2021-04-22 14:36:40","lampPostId":1674,"lampPostName":"智慧市政院01","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2087,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":121},{"deviceId":2087,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":123},{"deviceId":2087,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:58","parameterId":185},{"deviceId":2087,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:47","parameterId":197}]}},{"id":2088,"partId":"device2088","num":"866222058128250","name":"顶灯01","networkState":0,"lastOnlineTime":"2021-04-22 14:36:40","deviceType":1,"lampPostId":1674,"superId":1674,"superName":"智慧市政院01","dlmRespDevice":{"id":2088,"model":null,"deviceTypeId":22,"factory":"EXC1","name":"顶灯01","num":"866222058128250","isOnline":0,"deviceState":1,"brightness":null,"createTime":"2021-04-21 19:28:24","lastOnlineTime":"2021-04-22 14:36:40","lampPostId":1674,"lampPostName":"智慧市政院01","slLampPost":null,"lampPosition":"氛围灯","lampPositionId":3,"strategyId":268,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2088,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":257},{"deviceId":2088,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":258},{"deviceId":2088,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"氛围灯亮度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"94","parameterId":260}]}}]},{"id":1673,"partId":"lampPost1673","name":"智慧市院02","lampPostNum":"ZHSY2","lampPostModel":null,"lampPostLongitude":null,"lampPostLatitude":null,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-04-19 16:55:16","deviceNumber":1,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":164,"partId":"device164","num":"y60-221-40585","name":"智慧市院02","networkState":0,"lastOnlineTime":"2021-05-10 14:50:00","deviceType":5,"lampPostId":1673,"superId":1673,"superName":"智慧市院02","dlmRespDevice":{"id":164,"ip":"192.168.18.36","port":4533,"width":512,"height":768,"bright":255,"volume":12,"factory":null,"name":"智慧市院02","num":"y60-221-40585","networkState":0,"createTime":"2021-04-20 12:00:27","lastOnlineTime":"2021-05-10 14:50:00","lampPostId":1673,"model":null,"switchState":1,"isPlayProgram":1,"isPlaySubtitle":0}}]},{"id":1672,"partId":"lampPost1672","name":"智慧市院01","lampPostNum":"ZHSY1","lampPostModel":"灯杆","lampPostLongitude":null,"lampPostLatitude":null,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-04-19 16:54:43","deviceNumber":1,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":163,"partId":"device163","num":"y60-221-40580","name":"智慧市院01","networkState":0,"lastOnlineTime":"2021-05-10 15:05:05","deviceType":5,"lampPostId":1672,"superId":1672,"superName":"智慧市院01","dlmRespDevice":{"id":163,"ip":"192.168.18.35","port":8081,"width":512,"height":768,"bright":255,"volume":15,"factory":null,"name":"智慧市院01","num":"y60-221-40580","networkState":0,"createTime":"2021-04-19 16:36:54","lastOnlineTime":"2021-05-10 15:05:05","lampPostId":1672,"model":null,"switchState":1,"isPlayProgram":1,"isPlaySubtitle":0}}]},{"id":1519,"partId":"lampPost1519","name":"测试灯杆","lampPostNum":"12345","lampPostModel":"CSDG","lampPostLongitude":113.985,"lampPostLatitude":22.658,"lampPostManufacturer":"exc","lampPostLocation":null,"createTime":"2021-03-12 14:37:05","deviceNumber":3,"superId":1,"superName":"健仓站点","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,1","names":"宝安区,松岗街道,健仓站点","childrenList":[{"id":1955,"partId":"device1955","num":"10000200001001","name":"太阳能路灯1","networkState":0,"lastOnlineTime":"2021-04-12 19:36:26","deviceType":1,"lampPostId":1519,"superId":1519,"superName":"测试灯杆","dlmRespDevice":{"id":1955,"model":null,"deviceTypeId":21,"factory":null,"name":"太阳能路灯1","num":"10000200001001","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-04-13 19:36:26","lastOnlineTime":"2021-04-12 19:36:26","lampPostId":1519,"lampPostName":"测试灯杆","slLampPost":null,"lampPosition":null,"lampPositionId":null,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1955,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":245}]}},{"id":1956,"partId":"device1956","num":"10000200001002","name":"太阳能路灯2","networkState":0,"lastOnlineTime":"2021-04-12 19:37:16","deviceType":1,"lampPostId":1519,"superId":1519,"superName":"测试灯杆","dlmRespDevice":{"id":1956,"model":null,"deviceTypeId":21,"factory":null,"name":"太阳能路灯2","num":"10000200001002","isOnline":0,"deviceState":0,"brightness":50,"createTime":"2021-04-13 19:37:16","lastOnlineTime":"2021-04-12 19:37:16","lampPostId":1519,"lampPostName":"测试灯杆","slLampPost":null,"lampPosition":null,"lampPositionId":null,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1956,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"50","parameterId":245}]}},{"id":157,"partId":"device157","num":"y60-720-30497","name":"深圳南山信息屏","networkState":0,"lastOnlineTime":"2021-04-21 18:00:05","deviceType":5,"lampPostId":1519,"superId":1519,"superName":"测试灯杆","dlmRespDevice":{"id":157,"ip":"192.168.112.193","port":9999,"width":128,"height":256,"bright":80,"volume":1,"factory":null,"name":"深圳南山信息屏","num":"y60-720-30497","networkState":0,"createTime":"2021-04-13 17:53:55","lastOnlineTime":"2021-04-21 18:00:05","lampPostId":1519,"model":null,"switchState":0,"isPlayProgram":1,"isPlaySubtitle":0}}]},{"id":1516,"partId":"lampPost1516","name":"工厂门口灯杆","lampPostNum":"10010","lampPostModel":null,"lampPostLongitude":113.8576,"lampPostLatitude":22.75054,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:35:34","deviceNumber":1,"superId":1,"superName":"健仓站点","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,1","names":"宝安区,松岗街道,健仓站点","childrenList":[{"id":40,"partId":"device40","num":"c4cdf66ba4874fb7b294a79cc2b7f0d7","name":"工厂门口摄像头","networkState":0,"lastOnlineTime":"2021-01-04 18:11:50","deviceType":4,"lampPostId":1516,"superId":1516,"superName":"工厂门口灯杆","dlmRespDevice":{"id":40,"model":null,"type":3,"ip":"192.168.10.136","port":8000,"factory":null,"name":"工厂门口摄像头","num":"c4cdf66ba4874fb7b294a79cc2b7f0d7","networkState":0,"createTime":"2021-01-04 18:11:50","lastOnlineTime":"2021-01-04 18:11:50","lampPostId":1516,"cameraPreviewUrl":null}}]},{"id":1514,"partId":"lampPost1514","name":"工业区门口灯杆","lampPostNum":"10011","lampPostModel":null,"lampPostLongitude":113.857827,"lampPostLatitude":22.750544,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:34:08","deviceNumber":1,"superId":1,"superName":"健仓站点","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,1","names":"宝安区,松岗街道,健仓站点","childrenList":[{"id":145,"partId":"device145","num":"y60-b20-41237","name":"门口莲花杆","networkState":1,"lastOnlineTime":"2021-05-10 15:35:05","deviceType":5,"lampPostId":1514,"superId":1514,"superName":"工业区门口灯杆","dlmRespDevice":{"id":145,"ip":"192.168.10.154","port":9999,"width":1280,"height":720,"bright":185,"volume":8,"factory":null,"name":"门口莲花杆","num":"y60-b20-41237","networkState":1,"createTime":"2021-03-11 17:56:52","lastOnlineTime":"2021-05-10 15:35:05","lampPostId":1514,"model":null,"switchState":0,"isPlayProgram":0,"isPlaySubtitle":0}}]},{"id":1513,"partId":"lampPost1513","name":"慧眼灯杆左","lampPostNum":"17","lampPostModel":null,"lampPostLongitude":113.857333,"lampPostLatitude":22.750332,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:32:58","deviceNumber":3,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":1840,"partId":"device1840","num":"864162049953881","name":"慧眼灯杆左","networkState":1,"lastOnlineTime":"2021-05-10 15:36:19","deviceType":1,"lampPostId":1513,"superId":1513,"superName":"慧眼灯杆左","dlmRespDevice":{"id":1840,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"慧眼灯杆左","num":"864162049953881","isOnline":1,"deviceState":1,"brightness":100,"createTime":"2021-02-05 11:39:07","lastOnlineTime":"2021-05-10 15:36:19","lampPostId":1513,"lampPostName":"慧眼灯杆左","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1840,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"100","parameterId":26},{"deviceId":1840,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":1840,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:42","parameterId":181},{"deviceId":1840,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:50","parameterId":193}]}},{"id":28,"partId":"device28","num":"6","name":"左慧眼广播","networkState":0,"lastOnlineTime":null,"deviceType":3,"lampPostId":1513,"superId":1513,"superName":"慧眼灯杆左","dlmRespDevice":{"id":28,"model":null,"ip":"192.168.21.24","mac":"123456","volume":50,"factory":null,"name":"左慧眼广播","num":"6","networkState":0,"createTime":"2021-02-05 13:57:08","lastOnlineTime":null,"lampPostId":1513,"termId":5,"deviceType":null}},{"id":49,"partId":"device49","num":"db5f15f5d55646dfaa7f12081a2be78e","name":"慧眼左摄像头1","networkState":0,"lastOnlineTime":"2021-02-05 13:41:15","deviceType":4,"lampPostId":1513,"superId":1513,"superName":"慧眼灯杆左","dlmRespDevice":{"id":49,"model":null,"type":3,"ip":"192.168.21.20","port":9999,"factory":null,"name":"慧眼左摄像头1","num":"db5f15f5d55646dfaa7f12081a2be78e","networkState":0,"createTime":"2021-02-05 13:41:16","lastOnlineTime":"2021-02-05 13:41:15","lampPostId":1513,"cameraPreviewUrl":null}}]},{"id":1512,"partId":"lampPost1512","name":"擎天","lampPostNum":"2","lampPostModel":null,"lampPostLongitude":113,"lampPostLatitude":22,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:31:55","deviceNumber":2,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":52,"partId":"device52","num":"b8b8468d2c1b4760a50bbcbd2ec7f824","name":"擎天摄像头","networkState":1,"lastOnlineTime":"2021-02-05 13:48:35","deviceType":4,"lampPostId":1512,"superId":1512,"superName":"擎天","dlmRespDevice":{"id":52,"model":null,"type":1,"ip":"120.234.53.138","port":9999,"factory":null,"name":"擎天摄像头","num":"b8b8468d2c1b4760a50bbcbd2ec7f824","networkState":1,"createTime":"2021-02-05 13:48:36","lastOnlineTime":"2021-02-05 13:48:35","lampPostId":1512,"cameraPreviewUrl":null}},{"id":141,"partId":"device141","num":"y60-a20-40682","name":"擎天显示屏","networkState":1,"lastOnlineTime":"2021-05-10 15:35:05","deviceType":5,"lampPostId":1512,"superId":1512,"superName":"擎天","dlmRespDevice":{"id":141,"ip":"192.168.21.31","port":2,"width":192,"height":320,"bright":112,"volume":12,"factory":null,"name":"擎天显示屏","num":"y60-a20-40682","networkState":1,"createTime":"2021-02-05 13:52:20","lastOnlineTime":"2021-05-10 15:35:05","lampPostId":1512,"model":null,"switchState":1,"isPlayProgram":1,"isPlaySubtitle":0}}]},{"id":1511,"partId":"lampPost1511","name":"飞羽","lampPostNum":"16","lampPostModel":"8603740559","lampPostLongitude":113.857333,"lampPostLatitude":22.75033,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:31:10","deviceNumber":3,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":1842,"partId":"device1842","num":"860374055992646","name":"飞羽","networkState":1,"lastOnlineTime":"2021-05-10 15:27:36","deviceType":1,"lampPostId":1511,"superId":1511,"superName":"飞羽","dlmRespDevice":{"id":1842,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"飞羽","num":"860374055992646","isOnline":1,"deviceState":1,"brightness":45,"createTime":"2021-02-05 11:41:23","lastOnlineTime":"2021-05-10 15:27:36","lampPostId":1511,"lampPostName":"飞羽","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1842,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":26},{"deviceId":1842,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":1842,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:42","parameterId":181},{"deviceId":1842,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:50","parameterId":193}]}},{"id":53,"partId":"device53","num":"d11b658eb0c147e0a1a09f38129c79cb","name":"飞羽摄像头","networkState":1,"lastOnlineTime":"2021-02-22 15:20:29","deviceType":4,"lampPostId":1511,"superId":1511,"superName":"飞羽","dlmRespDevice":{"id":53,"model":null,"type":3,"ip":"120.234.53.138","port":123,"factory":null,"name":"飞羽摄像头","num":"d11b658eb0c147e0a1a09f38129c79cb","networkState":1,"createTime":"2021-02-22 15:20:29","lastOnlineTime":"2021-02-22 15:20:29","lampPostId":1511,"cameraPreviewUrl":null}},{"id":139,"partId":"device139","num":"y60-a20-40453","name":"飞羽显示屏","networkState":1,"lastOnlineTime":"2021-05-10 15:35:05","deviceType":5,"lampPostId":1511,"superId":1511,"superName":"飞羽","dlmRespDevice":{"id":139,"ip":"192.168.21.41","port":3245,"width":128,"height":256,"bright":143,"volume":12,"factory":null,"name":"飞羽显示屏","num":"y60-a20-40453","networkState":1,"createTime":"2021-02-05 13:50:46","lastOnlineTime":"2021-05-10 15:35:05","lampPostId":1511,"model":null,"switchState":1,"isPlayProgram":1,"isPlaySubtitle":0}}]},{"id":1510,"partId":"lampPost1510","name":"慧眼灯杆右","lampPostNum":"15","lampPostModel":null,"lampPostLongitude":113,"lampPostLatitude":22,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:30:06","deviceNumber":0,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[]},{"id":1509,"partId":"lampPost1509","name":"守望","lampPostNum":"14","lampPostModel":null,"lampPostLongitude":113.857331,"lampPostLatitude":22.750332,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:29:29","deviceNumber":2,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":51,"partId":"device51","num":"0cee6c85fd5547f3888d47ce44871452","name":"守望摄像头","networkState":1,"lastOnlineTime":"2021-02-05 13:48:01","deviceType":4,"lampPostId":1509,"superId":1509,"superName":"守望","dlmRespDevice":{"id":51,"model":null,"type":3,"ip":"120.234.53.138","port":8000,"factory":null,"name":"守望摄像头","num":"0cee6c85fd5547f3888d47ce44871452","networkState":1,"createTime":"2021-02-05 13:48:02","lastOnlineTime":"2021-02-05 13:48:01","lampPostId":1509,"cameraPreviewUrl":null}},{"id":23,"partId":"device23","num":"9c-a5-25-c0-88-28","name":"高低臂守望","networkState":0,"lastOnlineTime":null,"deviceType":7,"lampPostId":1509,"superId":1509,"superName":"守望","dlmRespDevice":{"id":23,"model":null,"ip":"120.234.53.138","port":50000,"factory":"exc","name":"高低臂守望","num":"9c-a5-25-c0-88-28","networkState":0,"createTime":"2021-02-05 13:59:25","lastOnlineTime":null,"lampPostId":1509}}]},{"id":1508,"partId":"lampPost1508","name":"守卫","lampPostNum":"13","lampPostModel":null,"lampPostLongitude":113.857335,"lampPostLatitude":22.750332,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:28:20","deviceNumber":2,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":1908,"partId":"device1908","num":"860374055812968","name":"台州测试","networkState":0,"lastOnlineTime":"2021-03-08 15:17:05","deviceType":1,"lampPostId":1508,"superId":1508,"superName":"守卫","dlmRespDevice":{"id":1908,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"台州测试","num":"860374055812968","isOnline":0,"deviceState":1,"brightness":89,"createTime":"2021-03-09 15:17:05","lastOnlineTime":"2021-03-08 15:17:05","lampPostId":1508,"lampPostName":"守卫","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1908,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"89","parameterId":26},{"deviceId":1908,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":1908,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":181},{"deviceId":1908,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"0","parameterId":193}]}},{"id":50,"partId":"device50","num":"37d1cd02133e4f87931bf0690e17764d","name":"守卫摄像头","networkState":0,"lastOnlineTime":"2021-02-05 13:47:21","deviceType":4,"lampPostId":1508,"superId":1508,"superName":"守卫","dlmRespDevice":{"id":50,"model":null,"type":3,"ip":"192.168.21.131","port":8000,"factory":null,"name":"守卫摄像头","num":"37d1cd02133e4f87931bf0690e17764d","networkState":0,"createTime":"2021-02-05 13:47:21","lastOnlineTime":"2021-02-05 13:47:21","lampPostId":1508,"cameraPreviewUrl":null}}]},{"id":1216,"partId":"lampPost1216","name":"中华飞天","lampPostNum":"12","lampPostModel":null,"lampPostLongitude":113.8574,"lampPostLatitude":22.7504,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-02-05 11:01:52","deviceNumber":2,"superId":46,"superName":"智慧路灯展厅","areaId":1,"areaName":"宝安区","streetId":1,"streetName":"松岗街道","ids":"1,1,46","names":"宝安区,松岗街道,智慧路灯展厅","childrenList":[{"id":1620,"partId":"device1620","num":"860374055870032","name":"1112","networkState":1,"lastOnlineTime":"2021-05-10 15:37:04","deviceType":1,"lampPostId":1216,"superId":1216,"superName":"中华飞天","dlmRespDevice":{"id":1620,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"1112","num":"860374055870032","isOnline":1,"deviceState":1,"brightness":100,"createTime":"2021-02-05 11:03:08","lastOnlineTime":"2021-05-10 15:37:04","lampPostId":1216,"lampPostName":"中华飞天","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1620,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"100","parameterId":26},{"deviceId":1620,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":1620,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:42","parameterId":181},{"deviceId":1620,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:50","parameterId":193}]}},{"id":1838,"partId":"device1838","num":"860411049455429","name":"飞天","networkState":1,"lastOnlineTime":"2021-05-10 15:33:36","deviceType":1,"lampPostId":1216,"superId":1216,"superName":"中华飞天","dlmRespDevice":{"id":1838,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"飞天","num":"860411049455429","isOnline":1,"deviceState":1,"brightness":100,"createTime":"2021-02-05 11:11:35","lastOnlineTime":"2021-05-10 15:33:36","lampPostId":1216,"lampPostName":"中华飞天","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":1838,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"100","parameterId":26},{"deviceId":1838,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":1838,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:42","parameterId":181},{"deviceId":1838,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:50","parameterId":193}]}}]},{"id":436,"partId":"lampPost436","name":"金黄光测试虚拟灯杆","lampPostNum":"0000001","lampPostModel":null,"lampPostLongitude":null,"lampPostLatitude":null,"lampPostManufacturer":null,"lampPostLocation":null,"createTime":"2021-01-19 11:34:59","deviceNumber":1,"superId":12,"superName":"交通信号控制杆","areaId":1,"areaName":"宝安区","streetId":16,"streetName":"金科路罗田路口","ids":"1,16,12","names":"宝安区,金科路罗田路口,交通信号控制杆","childrenList":[{"id":699,"partId":"device699","num":"864162049948469","name":"240W-1","networkState":0,"lastOnlineTime":"2021-01-19 15:29:29","deviceType":1,"lampPostId":436,"superId":436,"superName":"金黄光测试虚拟灯杆","dlmRespDevice":{"id":699,"model":null,"deviceTypeId":7,"factory":"EXC1","name":"240W-1","num":"864162049948469","isOnline":0,"deviceState":1,"brightness":89,"createTime":"2021-01-19 11:35:49","lastOnlineTime":"2021-01-19 15:29:29","lampPostId":436,"lampPostName":"金黄光测试虚拟灯杆","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":699,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"89","parameterId":26},{"deviceId":699,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":36},{"deviceId":699,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"07:01","parameterId":181},{"deviceId":699,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"17:57","parameterId":193}]}}]}]
     */

    public int code;
    public String operate;
    public String message;
    public List<DataBean> data;

    @Data
    public static class DataBean implements Serializable {
        /**
         * id : 1675
         * partId : lampPost1675
         * name : 智慧市政院02
         * lampPostNum : 2222
         * lampPostModel : 功能01
         * lampPostLongitude : null
         * lampPostLatitude : null
         * lampPostManufacturer : null
         * lampPostLocation : null
         * createTime : 2021-04-21 19:09:03
         * deviceNumber : 2
         * superId : 1
         * superName : 健仓站点
         * areaId : 1
         * areaName : 宝安区
         * streetId : 1
         * streetName : 松岗街道
         * ids : 1,1,1
         * names : 宝安区,松岗街道,健仓站点
         * childrenList : [{"id":2089,"partId":"device2089","num":"866222058228787","name":"金钢铃02","networkState":0,"lastOnlineTime":"2021-05-04 18:12:59","deviceType":1,"lampPostId":1675,"superId":1675,"superName":"智慧市政院02","dlmRespDevice":{"id":2089,"model":null,"deviceTypeId":14,"factory":"EXC1","name":"金钢铃02","num":"866222058228787","isOnline":0,"deviceState":0,"brightness":45,"createTime":"2021-04-21 19:29:01","lastOnlineTime":"2021-05-04 18:12:59","lampPostId":1675,"lampPostName":"智慧市政院02","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2089,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":121},{"deviceId":2089,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":123},{"deviceId":2089,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:50","parameterId":185},{"deviceId":2089,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:52","parameterId":197}]}},{"id":2090,"partId":"device2090","num":"866222058228787","name":"顶灯02","networkState":0,"lastOnlineTime":"2021-05-04 18:12:59","deviceType":1,"lampPostId":1675,"superId":1675,"superName":"智慧市政院02","dlmRespDevice":{"id":2090,"model":null,"deviceTypeId":22,"factory":"EXC1","name":"顶灯02","num":"866222058228787","isOnline":0,"deviceState":1,"brightness":null,"createTime":"2021-04-21 19:29:01","lastOnlineTime":"2021-05-04 18:12:59","lampPostId":1675,"lampPostName":"智慧市政院02","slLampPost":null,"lampPosition":"氛围灯","lampPositionId":3,"strategyId":268,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2090,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":257},{"deviceId":2090,"deviceTypeId":null,"filed":"loop_num","unit":"","name":"支路数","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"3","parameterId":258},{"deviceId":2090,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"氛围灯亮度","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"94","parameterId":260}]}}]
         */

        public int id;
        public String partId;
        public String name;
        public String lampPostNum;
        public String lampPostModel;
        public double lampPostLongitude;
        public double lampPostLatitude;
        public Object lampPostManufacturer;
        public Object lampPostLocation;
        public String createTime;
        public int deviceNumber;
        public int superId;
        public String superName;
        public int areaId;
        public String areaName;
        public int streetId;
        public String streetName;
        public String ids;
        public String names;
        public List<ChildrenListBean> childrenList;
        public boolean is_select;

        @Data
        public static class ChildrenListBean implements Serializable {
            /**
             * id : 2089
             * partId : device2089
             * num : 866222058228787
             * name : 金钢铃02
             * networkState : 0
             * lastOnlineTime : 2021-05-04 18:12:59
             * deviceType : 1
             * lampPostId : 1675
             * superId : 1675
             * superName : 智慧市政院02
             * dlmRespDevice : {"id":2089,"model":null,"deviceTypeId":14,"factory":"EXC1","name":"金钢铃02","num":"866222058228787","isOnline":0,"deviceState":0,"brightness":45,"createTime":"2021-04-21 19:29:01","lastOnlineTime":"2021-05-04 18:12:59","lampPostId":1675,"lampPostName":"智慧市政院02","slLampPost":null,"lampPosition":"主路","lampPositionId":1,"strategyId":null,"reserveOne":null,"setDefense":0,"deviceType":null,"slRespSystemDeviceParameterVOS":[{"deviceId":2089,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":121},{"deviceId":2089,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":123},{"deviceId":2089,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:50","parameterId":185},{"deviceId":2089,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:52","parameterId":197}]}
             */

            public int id;
            public String partId;
            public String num;
            public String name;
            public String model;
            public int networkState;
            public String lastOnlineTime;
            public int deviceType;
            public int deviceTypeId;
            public int lampPostId;
            public int superId;
            public String superName;
            public String lampPostName;
            public DlmRespDeviceBean dlmRespDevice;
            public boolean is_select;
            public String createTime;
            public Integer brightness;
            public Integer deviceState;
            public String lampPosition;
            public Integer lampPositionId;
            public Integer isOnline;
            public boolean isSelect;
            @Data
            public static class DlmRespDeviceBean implements Serializable {
                /**
                 * id : 2089
                 * model : null
                 * deviceTypeId : 14
                 * factory : EXC1
                 * name : 金钢铃02
                 * num : 866222058228787
                 * isOnline : 0
                 * deviceState : 0
                 * brightness : 45
                 * createTime : 2021-04-21 19:29:01
                 * lastOnlineTime : 2021-05-04 18:12:59
                 * lampPostId : 1675
                 * lampPostName : 智慧市政院02
                 * slLampPost : null
                 * lampPosition : 主路
                 * lampPositionId : 1
                 * strategyId : null
                 * reserveOne : null
                 * setDefense : 0
                 * deviceType : null
                 * slRespSystemDeviceParameterVOS : [{"deviceId":2089,"deviceTypeId":null,"filed":"brightness","unit":"%","name":"亮度","isMust":1,"regexp":"","icon":"","showFlag":null,"parameterValue":"45","parameterId":121},{"deviceId":2089,"deviceTypeId":null,"filed":"lamp_position_id","unit":"","name":"灯具位置","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"1","parameterId":123},{"deviceId":2089,"deviceTypeId":null,"filed":"at_sunrise","unit":"","name":"日出时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"05:50","parameterId":185},{"deviceId":2089,"deviceTypeId":null,"filed":"down_sunrise","unit":"","name":"日落时间","isMust":0,"regexp":"","icon":"","showFlag":null,"parameterValue":"18:52","parameterId":197}]
                 */

                public int id;
                public Object model;
                public int deviceTypeId;
                public String factory;
                public String name;
                public String num;
                public int isOnline;
                public int deviceState;
                public int brightness;
                public String createTime;
                public String lastOnlineTime;
                public int lampPostId;
                public String lampPostName;
                public Object slLampPost;
                public String lampPosition;
                public int lampPositionId;
                public Object strategyId;
                public Object reserveOne;
                public int setDefense;
                public int deviceType;

                public String superName;
                public String lampPostNum;
                public double lampPostLongitude;
                public double lampPostLatitude;

                public String locationAreaName;
                public String locationSiteName;
                public String locationStreetName;

                public List<SlRespSystemDeviceParameterVOSBean> slRespSystemDeviceParameterVOS;

                @Data
                public static class SlRespSystemDeviceParameterVOSBean implements Serializable {
                    /**
                     * deviceId : 2089
                     * deviceTypeId : null
                     * filed : brightness
                     * unit : %
                     * name : 亮度
                     * isMust : 1
                     * regexp :
                     * icon :
                     * showFlag : null
                     * parameterValue : 45
                     * parameterId : 121
                     */

                    public int deviceId;
                    public Object deviceTypeId;
                    public String filed;
                    public String unit;
                    public String name;
                    public int isMust;
                    public String regexp;
                    public String icon;
                    public Object showFlag;
                    public String parameterValue;
                    public int parameterId;
                }
            }
        }
    }
}
