package com.exc.roadlamp.device.bean;

import java.util.List;

public class DeviceInfoBean {
   public int id;
   public int lamppostid;
   public int device_size;
   public int device_type;
   public String name;
   public String num;
   public String model;
   public String sitename;
   public String Manufacturer;
   public String info;
   public double longuide;
   public double latitude;
   public boolean isqrcode;
   public boolean islamplist;
   public boolean isbinding;
   public int control_id;
   public List<LampDeviceBean.DataBean.ChildrenListBean> childrenList;
}
