package com.exc.roadlamp.work.control;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentControlDetailBinding;
import com.exc.roadlamp.databinding.FragmentDevMapCenterControlDetailBinding;
import com.exc.roadlamp.device.adapter.CenterControlLoopAdapter;
import com.exc.roadlamp.device.bean.AllLoopsData;
import com.exc.roadlamp.device.bean.CenterControlAllData;
import com.exc.roadlamp.device.bean.CenterControlDetail;
import com.exc.roadlamp.device.bean.CenterControlListData;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LampControlDetail;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpApi1;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.lightcontrol.LightStrategyManagerList;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.common.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Page(name = "回路控制")
public class ControlDetail extends MyBaseFragment implements View.OnClickListener{
    private FragmentControlDetailBinding binding;
    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private CenterControlLoopAdapter adapter;
    public static final int deviceType = 101;
    private String loopIdsStr = "";
    private int isOpen;
    private LampControlDetail centerControlDetail;
    private List<AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean> loopVOIPageBeanList = new ArrayList<>();


    @AutoWired(name = "device_info")
    DeviceInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }


    @Override
    protected void initViews() {
        queryControl();
        getCenterControlLoop();
    }

    @Override
    protected void initListeners() {
        binding.btAllClose.setOnClickListener(this);
        binding.btAllOpen.setOnClickListener(this);
    }

    /**
     * 查询集控详情
     */
    private void queryControl() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();

        HttpRequest.GET(mActivity, HttpApi.API_DLM_LOCATION_CONTROL + device_info.id, parameter, new BeanResponseListener<LampControlDetail>() {
            @Override
            public void onResponse(LampControlDetail lampCommonDetail, Exception error) {
                mMessageLoader.dismiss();
                if (null == error && lampCommonDetail != null) {

                    if (lampCommonDetail.getData().getDlmRespElectricityMeterDataVO() != null) {
                        centerControlDetail = lampCommonDetail;
                        initTopView();
                    }
                }
            }
        });
    }

    /**
     * 查询回路
     */
    public void getCenterControlLoop() {
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
                                    dataBean -> dataBean.controlId == device_info.id
                            ).collect(Collectors.toList())
                    );

                    loopVOIPageBeanList = allLoopsData.getData().getResearchLoopVOIPage().getRecords();

                    WidgetUtils.initRecyclerView(binding.recyclerView, 0);
                    adapter = new CenterControlLoopAdapter(mActivity,mMessageLoader);
                    binding.recyclerView.setAdapter(adapter);
                    adapter.setList(allLoopsData.getData().getResearchLoopVOIPage().getRecords());

                    if(allLoopsData.getData().getResearchLoopVOIPage().getRecords().size()>0){
                        binding.btAllClose.setVisibility(View.VISIBLE);
                        binding.btAllOpen.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initTopView() {

        binding.tvPowerWatchUpdateTime.setText("更新时间" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getUpdateTime());
        binding.tvAllEnergyConsumption.setText("总能耗(KW.h):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getCurrentCombinedActiveTotalEnergy());
        binding.tvPowerWatchAddress.setText("电表地址:" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getElectricMeterAddr());

        binding.tvVoltageA.setText("A相电压(V):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getAphaseVoltage());
        binding.tvVoltageB.setText("B相电压(V):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getBphaseVoltage());
        binding.tvVoltageC.setText("C相电压(V):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getCphaseVoltage());

        binding.tvPowerCurrentA.setText("A相电流(A):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getAphaseCurrent());
        binding.tvPowerCurrentB.setText("B相电流(A):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getBphaseCurrent());
        binding.tvPowerCurrentC.setText("C相电流(A):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getCphaseCurrent());

        binding.tvGonglvA.setText("A相有功功率(KW):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getAphaseActivePower());
        binding.tvGonglvB.setText("B相有功功率(KW):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getBphaseActivePower());
        binding.tvGonglvC.setText("C相有功功率(KW):" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getCphaseActivePower());

        binding.tvGonglvFactorA.setText("A相功率因数:" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getAphasePowerFactorNumber());
        binding.tvGonglvFactorB.setText("B相功率因数:" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getBphasePowerFactorNumber());
        binding.tvGonglvFactorC.setText("C相功率因数:" + centerControlDetail.getData().getDlmRespElectricityMeterDataVO().getCphasePowerFactorNumber());

    }

    @Override
    public void onClick(View v) {
        String tipsStr = "";
        String okStr = "";

        switch (v.getId()) {
            case R.id.bt_all_open:
                tipsStr = "您确定要开启全部回路吗？";
                okStr = "开启全部回路";
                isOpen = 1;
                break;
            case R.id.bt_all_close:
                tipsStr = "您确定要关闭全部回路吗？";
                okStr = "关闭全部回路";
                isOpen = 0;
                break;
        }
        if (StringUtils.isEmpty(tipsStr)) {
            return;
        }

        new MaterialDialog.Builder(getContext())
                .content(tipsStr)
                .positiveText(okStr)
                .positiveColorRes(R.color.common_blue)
                .negativeText("取消")
                .negativeColorRes(R.color.common_blue)
                .onPositive((dialog, which) -> {
                    ArrayList<String> loopIdsList = new ArrayList<>();
                    org.json.JSONArray idlist = new JSONArray();
                    for(AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean loop : loopVOIPageBeanList){
                        idlist.put(loop.id);
                    }
                    loopIdsStr = loopIdsList.stream().collect(Collectors.joining(","));

                    try {
                        switchLoop(isOpen==1?true:false,idlist);
                    }catch (Exception e){
                        e.printStackTrace();
                    };

                })
                .show();
    }

    //集控回路控制全开全关
    public void switchLoop(boolean open , JSONArray loopids) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("isOpen", open);
        body.put("loopIds", loopids);
        mMessageLoader.show();
        HttpRequest.JSONPOST(mActivity, HttpApi.API_DLM_CONTROL_LOOP_SWITCHALL, body, new BeanResponseListener<MapLampCommonDevList>() {
            @Override
            public void onResponse(MapLampCommonDevList mapLampCommonDevList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    XToastUtils.success("下发成功");

                    for (AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean recordsBean : loopVOIPageBeanList) {
                        recordsBean.setIsOpen(isOpen);
                    }
                    adapter.allSwitchListener = true;
                    adapter.setList(loopVOIPageBeanList);
                }
            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentControlDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
