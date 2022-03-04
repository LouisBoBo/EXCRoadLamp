package com.exc.roadlamp.device.util;

import android.app.Activity;
import android.graphics.Color;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.FiddlerAreaBean;
import com.exc.roadlamp.device.bean.LampDeviceListBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.device.bean.ProjectList;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.WorkAreaListData;
import com.exc.roadlamp.work.workorder.addressselector.RegionBean;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.common.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.Data;

public class DevFiddlerUtils {

    /**
     * 和灯杆相关的设备的删选dialog
     *
     * @param activity
     * @param showDeviceTypeId
     * @param allSiteList
     * @param onFiddlerListener
     */
    public static void showPoleDevFiddlerDialog(Activity activity,
                                                int showDeviceTypeId,
                                                List<MapLampCommonDevList.DataBean> allSiteList,
                                                OnFiddlerListener onFiddlerListener
    ) {


        //处理所有的区域
        List<FiddlerAreaBean> areaList = new ArrayList<>();
        //过滤掉areaId重复的数据，得到区域列表
        List<MapLampCommonDevList.DataBean> tempAllAreaList = allSiteList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MapLampCommonDevList.DataBean::getAreaId))), ArrayList::new));
        for (MapLampCommonDevList.DataBean bean : tempAllAreaList) {
            FiddlerAreaBean areaBean = new FiddlerAreaBean();
            if (!StringUtils.isEmpty(bean.areaName)) {
                areaBean.setAreaId(bean.areaId);
                areaBean.setAreaName(bean.areaName);
                areaList.add(areaBean);
            }
        }

        //给区域集合添加全部选项(第一个)
        FiddlerAreaBean firstArea = new FiddlerAreaBean();
        firstArea.setAreaName("全部");
        firstArea.setAreaId(-1);
        areaList.add(0, firstArea);


        //↓↓↓↓↓↓↓处理数据↓↓↓↓↓↓↓
        List<MapLampCommonDevList.DataBean> tempAllStreetList = allSiteList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MapLampCommonDevList.DataBean::getStreetId))), ArrayList::new));
        List<MapLampCommonDevList.DataBean> tempAllSiteList = allSiteList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MapLampCommonDevList.DataBean::getSuperId))), ArrayList::new));

        for (FiddlerAreaBean areaBean : areaList) {
            //处理第一个区域（全部）
            if (areaBean.getAreaId() == -1) {
                //街道只有1个
                List<FiddlerAreaBean.FiddlerStreetBean> firstStreetList = new ArrayList<>();

                FiddlerAreaBean.FiddlerStreetBean firstStreet = new FiddlerAreaBean.FiddlerStreetBean();
                firstStreet.setStreetName("全部");
                firstStreet.setStreetId(-1);

                //街道的站点也只有一个
                List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean> firstSiteList = new ArrayList<>();
                FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean firstSite = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
                firstSite.setSiteName("全部");
                firstSite.setSiteId(-1);
                firstSiteList.add(firstSite);

                firstStreet.setSite(firstSiteList);

                firstStreetList.add(firstStreet);
                areaBean.setStreet(firstStreetList);
                continue;
            }


            //给每个区域areaBean添加街道集合
            List<FiddlerAreaBean.FiddlerStreetBean> streetList = new ArrayList<>();
            for (MapLampCommonDevList.DataBean tempStreetListBean : tempAllStreetList) {
                if (areaBean.getAreaId() == tempStreetListBean.getAreaId()) {
                    FiddlerAreaBean.FiddlerStreetBean streetBean = new FiddlerAreaBean.FiddlerStreetBean();
                    streetBean.setStreetName(tempStreetListBean.getStreetName());
                    streetBean.setStreetId(tempStreetListBean.getStreetId());
                    //给每个街道添加街道站点
                    List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean> siteList = new ArrayList<>();
                    for (MapLampCommonDevList.DataBean tempSiteListBean : tempAllSiteList) {
                        if (tempSiteListBean.getStreetId() == tempStreetListBean.getStreetId()) {
                            FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean siteBean = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
                            siteBean.setSiteId(tempSiteListBean.getSuperId());
                            siteBean.setSiteName(tempSiteListBean.getSuperName());
                            siteList.add(siteBean);
                        }
                    }

                    //给站点添加第一个（全部）
                    FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean firstSiteBean = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
                    firstSiteBean.setSiteId(-1);
                    firstSiteBean.setSiteName("全部");
                    siteList.add(0, firstSiteBean);

                    //设置街道的站点集合
                    streetBean.setSite(siteList);


                    streetList.add(streetBean);
                }
            }

            //添加第一个街道（全部）
            FiddlerAreaBean.FiddlerStreetBean firstStreet = new FiddlerAreaBean.FiddlerStreetBean();
            firstStreet.setStreetId(-1);
            firstStreet.setStreetName("全部");
            //站点只有1个
            List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean> firstSiteList = new ArrayList<>();
            FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean firstSite = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
            firstSite.setSiteName("全部");
            firstSite.setSiteId(-1);
            firstSiteList.add(firstSite);
            firstStreet.setSite(firstSiteList);
            streetList.add(0, firstStreet);
            areaBean.setStreet(streetList);
        }
        //↑↑↑↑↑↑↑↑↑↑↑↑处理数据↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        XLogger.e("区域数量：" + areaList.size());

        //分割数据
        List<FiddlerAreaBean> areaForDialogList;//区域
        List<List<FiddlerAreaBean.FiddlerStreetBean>> streetForDialogList = new ArrayList<>();//街道集合
        List<List<List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean>>> siteForDialogList = new ArrayList<>();//站点集合
        areaForDialogList = areaList;

        //遍历区域（第一级）
        for (FiddlerAreaBean areaBean : areaForDialogList) {
            //该区域的街道列表（第二级）
            List<FiddlerAreaBean.FiddlerStreetBean> streetList = new ArrayList<>();
            //该区域的所有站点列表（第三级）
            List<List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean>> siteList = new ArrayList<>();

            for (FiddlerAreaBean.FiddlerStreetBean streetBean : areaBean.getStreet()) {
                //添加街道
                FiddlerAreaBean.FiddlerStreetBean street = new FiddlerAreaBean.FiddlerStreetBean();
                street.setStreetName(streetBean.getStreetName());
                street.setStreetId(streetBean.getStreetId());
                streetList.add(street);
                //该街道的所有站点列表
                List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean> siteBeanList = new ArrayList<>();
                //如果无站点数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (streetBean.getSite() == null || streetBean.getSite().size() == 0) {
                    FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean siteBean = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
                    siteBean.setSiteId(-1);
                    siteBean.setSiteName("全部");
                    siteBeanList.add(siteBean);
                } else {
                    siteBeanList.addAll(streetBean.getSite());
                }
                //添加该区域所有站点数据
                siteList.add(siteBeanList);
            }

            /**
             * 添加街道数据
             */
            streetForDialogList.add(streetList);

            /**
             * 添加站点数据
             */
            siteForDialogList.add(siteList);

        }
        //弹出Dialog
        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, (v, options1, options2, options3) -> {
            //返回的分别是三个级别的选中位置
            String tx = areaForDialogList.get(options1).getPickerViewText() + "areaId=" + areaForDialogList.get(options1).getAreaId() + "-" +
                    streetForDialogList.get(options1).get(options2).getPickerViewText() + "streetId=" + streetForDialogList.get(options1).get(options2).getStreetId() + "-" +
                    siteForDialogList.get(options1).get(options2).get(options3).getPickerViewText() + "siteId =" + siteForDialogList.get(options1).get(options2).get(options3).getSiteId();

//            XToastUtils.toast(tx);

            SelectResult selectResult = new SelectResult();
            selectResult.setAreaId(areaForDialogList.get(options1).getAreaId());
            selectResult.setStreetId(streetForDialogList.get(options1).get(options2).getStreetId());
            selectResult.setSiteId(siteForDialogList.get(options1).get(options2).get(options3).getSiteId());
            selectResult.setSiteName(siteForDialogList.get(options1).get(options2).get(options3).getSiteName());

            onFiddlerListener.onFiddler(showDeviceTypeId, selectResult);


            return false;
        })

                .setTitleText("请选择")
                .setDividerColor(0xFFd5d5d5)
                //切换选项时，还原到第一项
                .isRestoreItem(true)
                //设置选中项文字颜色
                .setTextColorCenter(activity.getColor(R.color.text_color_333333))
                .setContentTextSize(16)
                .setSelectOptions(areaForDialogList.size()/2)
                .isDialog(false)
//                .setCyclic(true,false,false)
//                .setSelectOptions(defaultSelectOptions[0], defaultSelectOptions[1], defaultSelectOptions[2])
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, streetForDialogList);//二级选择器*/
        pvOptions.setPicker(areaForDialogList, streetForDialogList, siteForDialogList);//三级选择器

        pvOptions.show();
    }


    public static void showProjectFiddlerDialog(Activity activity,
                                                List<ProjectList.DataBean> projectList,
                                                OnFiddlerListener onFiddlerListener
    ) {


        ProjectList.DataBean allProject = new ProjectList.DataBean();
        allProject.setId(-1);
        allProject.setName("全部");
        projectList.add(0, allProject);

        //弹出Dialog
        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, (v, options1, options2, options3) -> {
            //返回的分别是三个级别的选中位置
            SelectResult selectResult = new SelectResult();
            selectResult.setProjectId(projectList.get(options1).getId());
            selectResult.setProjectName(projectList.get(options1).getName());
            onFiddlerListener.onFiddler(-1, selectResult);
            return false;
        })

                .setTitleText("请选择项目")
                .setDividerColor(Color.BLACK)
                //切换选项时，还原到第一项
                .isRestoreItem(true)
                //设置选中项文字颜色
                .setTextColorCenter(activity.getColor(R.color.text_color_333333))
                .setContentTextSize(16)
                .setSelectOptions(projectList.size()/2)
                .isDialog(false)
//                .setCyclic(true,false,false)
//                .setSelectOptions(defaultSelectOptions[0], defaultSelectOptions[1], defaultSelectOptions[2])
                .build();

        pvOptions.setPicker(projectList);//一级选择器

        pvOptions.show();
    }


    public static void showAreaSiteDialog(Activity activity,
                                          int device_type, List<WorkAreaListData.DataBean> areListData,
                                          OnFiddlerListener onFiddlerListener
    ) {


        List<FiddlerAreaBean> lightPole1List = new ArrayList<>();//第一级
        List<List<FiddlerAreaBean.FiddlerStreetBean>> lightPole2List = new ArrayList<>();//第二级
        List<List<List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean>>> lightPole3List = new ArrayList<>();//第三级

        for (WorkAreaListData.DataBean dataBean : areListData) {
            FiddlerAreaBean temp1 = new FiddlerAreaBean();
            temp1.setAreaId(dataBean.getId());
            temp1.setAreaName(dataBean.getName());
            lightPole1List.add(temp1);

            List<FiddlerAreaBean.FiddlerStreetBean> streenregionBeans = new ArrayList<>();
            List<List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean>> siteregionBeans = new ArrayList<>();

            for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : dataBean.getChildrenList()) {
                FiddlerAreaBean.FiddlerStreetBean temp2 = new FiddlerAreaBean.FiddlerStreetBean();
                temp2.setStreetId(childrenListBeanXX.getId());
                temp2.setStreetName(childrenListBeanXX.getName());
                streenregionBeans.add(temp2);

                List<FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean> reginBeanss = new ArrayList<>();
                for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX childrenListBeanX : childrenListBeanXX.getChildrenList()) {
                    FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean temp3 = new FiddlerAreaBean.FiddlerStreetBean.FiddlerSiteBean();
                    temp3.setSiteId(childrenListBeanX.getId());
                    temp3.setSiteName(childrenListBeanX.getName());
                    reginBeanss.add(temp3);
                }
                siteregionBeans.add(reginBeanss);
            }

            lightPole2List.add(streenregionBeans);
            lightPole3List.add(siteregionBeans);
        }

        //弹出Dialog
        OptionsPickerView pvOptions = new OptionsPickerBuilder(activity, (v, options1, options2, options3) -> {
            //返回的分别是三个级别的选中位置

            DevFiddlerUtils.SelectResult selectResult = new DevFiddlerUtils.SelectResult();
            selectResult.setAreaId(lightPole1List.get(options1).getAreaId());
            selectResult.setStreetId(lightPole2List.get(options1).get(options2).getStreetId());
            selectResult.setSiteId(lightPole3List.get(options1).get(options2).get(options3).getSiteId());
            selectResult.setSiteName(lightPole3List.get(options1).get(options2).get(options3).getSiteName());

            onFiddlerListener.onFiddler(device_type, selectResult);

            return false;
        })

                .setTitleText("请选择")
                .setDividerColor(Color.BLACK)
                //切换选项时，还原到第一项
                .isRestoreItem(true)
                //设置选中项文字颜色
                .setTextColorCenter(activity.getColor(R.color.text_color_333333))
                .setContentTextSize(16)
                .setSelectOptions(lightPole1List.size()/2)
                .isDialog(false)
                .build();

        pvOptions.setPicker(lightPole1List,lightPole2List,lightPole3List);//三级选择器
        pvOptions.show();
    }

    public interface OnFiddlerListener {
        void onFiddler(int showDeviceTypeId, SelectResult selectResult);
    }

    /**
     * 共用的筛选结果
     */
    @Data
    public static class SelectResult {
        public int areaId;
        public int streetId;
        public int siteId;
        public int projectId;
        public String siteName;
        public String projectName;
    }
}
