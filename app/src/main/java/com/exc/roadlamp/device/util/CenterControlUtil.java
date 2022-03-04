package com.exc.roadlamp.device.util;

import android.app.Activity;

import com.exc.roadlamp.device.bean.AllLoopsData;
import com.exc.roadlamp.device.bean.CenterControlAllData;
import com.exc.roadlamp.device.bean.CenterControlDetail;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.stream.Collectors;

public class CenterControlUtil {
    private static Activity mActivity;
    private static IMessageLoader mMessageLoader;
    private static CenterControlAllData mCenterControlAllData;
    private static OnRequestCenterControlAllDetail mOnRequestCenterControlAllDetail;

    public interface OnRequestCenterControlAllDetail {
        void requestFinish(CenterControlAllData allCenterControlAllData);
    }

    /**
     * 查询集控的具体详情和集控的回路列表后组装数据后回调
     *
     * @param activity
     * @param iMessageLoader       加载框
     * @param centerControlAllData 集控数据
     */
    public static void getCenterControlAllDetail(Activity activity, IMessageLoader iMessageLoader,
                                                 CenterControlAllData centerControlAllData, OnRequestCenterControlAllDetail onRequestCenterControlAllDetail) {
        mActivity = activity;
        mMessageLoader = iMessageLoader;
        mCenterControlAllData = centerControlAllData;
        mOnRequestCenterControlAllDetail = onRequestCenterControlAllDetail;
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi1.GET_CENTER_CONTROL_DETAIL + "/" + centerControlAllData.getCenterControl().getId(), parameter, new BeanResponseListener<CenterControlDetail>() {
            @Override
            public void onResponse(CenterControlDetail centerControlDetail, Exception error) {
                if (null == error) {
                    mCenterControlAllData.setCenterControlDetail(centerControlDetail.data);
                    getCenterControlLoop();
                }
            }
        });

    }

    /**
     * 查询回路
     */
    public static void getCenterControlLoop() {
        Parameter parameter = new Parameter();
        parameter.put("sortType", 3);
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 9999);
        HttpRequest.GET(mActivity, HttpApi1.GET_ALL_LOOPS, parameter, new BeanResponseListener<AllLoopsData>() {
            @Override
            public void onResponse(AllLoopsData allLoopsData, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    //过滤掉其他回路
                    allLoopsData.getData().getResearchLoopVOIPage().setRecords(
                            allLoopsData.getData().getResearchLoopVOIPage().getRecords().stream().filter(
                                    dataBean -> dataBean.controlId == mCenterControlAllData.centerControl.id
                            ).collect(Collectors.toList())
                    );

                    mCenterControlAllData.setLoopsList(allLoopsData.getData().getResearchLoopVOIPage().getRecords());
                    mOnRequestCenterControlAllDetail.requestFinish(mCenterControlAllData);
                }
            }
        });
    }
}
