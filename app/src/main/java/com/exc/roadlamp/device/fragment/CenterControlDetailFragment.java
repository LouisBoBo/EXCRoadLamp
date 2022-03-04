package com.exc.roadlamp.device.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.bean.BaseBean;
import com.exc.roadlamp.databinding.FragmentDevMapCenterControlDetailBinding;
import com.exc.roadlamp.device.adapter.CenterControlLoopAdapter;
import com.exc.roadlamp.device.bean.AllLoopsData;
import com.exc.roadlamp.device.bean.CenterControlAllData;
import com.exc.roadlamp.device.bean.CenterControlDetail;
import com.exc.roadlamp.device.bean.LoopItemSwitchBean;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.common.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 集控详情
 */
public class CenterControlDetailFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentDevMapCenterControlDetailBinding binding;
    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private CenterControlAllData centerControlAllData;
    private CenterControlLoopAdapter adapter;
    public static final int deviceType = 101;
    private String loopIdsStr = "";
    private int isOpen;

    public static CenterControlDetailFragment getInstance(CenterControlAllData centerControlAllData) {
        CenterControlDetailFragment centerControlDetailFragment = new CenterControlDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, centerControlAllData);
        centerControlDetailFragment.setArguments(args);
        return centerControlDetailFragment;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage<LoopItemSwitchBean> eventMessage) {
//        if (eventMessage.getEventEnum() == EventEnum.CENTER_CONTROL_LOOP_SWITCH) {
//            isOpen = eventMessage.getData().isChecked() ? 1 :0;
//            loopIdsStr = eventMessage.getData().getLoopId();
//            switchLoop(false);
//        }
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {
        initEventBus();
        initListData();
    }

    @Override
    protected void initListeners() {
        binding.btAllClose.setOnClickListener(this);
        binding.btAllOpen.setOnClickListener(this);
    }

    private void initListData() {
        centerControlAllData = (CenterControlAllData) getArguments().getSerializable(DATA_KEY);
        initTopView();
        initLoopList();

    }

    private void initTopView() {
        binding.tvCenterControlName.setText(centerControlAllData.centerControl.name);
        binding.tvCenterOnline.setText(centerControlAllData.centerControl.isOnline == 1?"（在线）":"（离线）");
        binding.tvCenterOnline.setTextColor(centerControlAllData.centerControl.isOnline == 1? Color.GREEN:Color.RED);
        binding.tvCenterControlNo.setText(centerControlAllData.centerControl.num);
        binding.tvCabinetName.setText("所属配电柜:" + centerControlAllData.centerControl.cabinetName);
        binding.tvDevType.setText("设备类型:" + centerControlAllData.centerControlDetail.typeName);
        binding.tvProject.setText("所属项目:" + centerControlAllData.centerControl.areaName);
        binding.tvArea.setText("所属区域:" + centerControlAllData.centerControl.areaName);

        if (null == centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO) {
            CenterControlDetail.DataBean.DlmRespElectricityMeterDataVOBean dataVOBean = new CenterControlDetail.DataBean.DlmRespElectricityMeterDataVOBean();
            centerControlAllData.centerControlDetail.setDlmRespElectricityMeterDataVO(dataVOBean);
        }

        binding.tvPowerWatchUpdateTime.setText(centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.updateTime);
        binding.tvAllEnergyConsumption.setText("总能耗(KW.h):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.currentCombinedActiveTotalEnergy);
        binding.tvPowerWatchAddress.setText("电表地址:" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.electricMeterAddr);

        binding.tvVoltageA.setText("A相电压(V):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.aphaseVoltage);
        binding.tvVoltageB.setText("B相电压(V):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.bphaseVoltage);
        binding.tvVoltageC.setText("C相电压(V):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.cphaseVoltage);


        binding.tvPowerCurrentA.setText("A相电流(A):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.aphaseCurrent);
        binding.tvPowerCurrentB.setText("B相电流(A):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.bphaseCurrent);
        binding.tvPowerCurrentC.setText("C相电流(A):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.cphaseCurrent);

        binding.tvGonglvA.setText("A相有功功率(KW):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.aphaseActivePower);
        binding.tvGonglvB.setText("B相有功功率(KW):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.bphaseActivePower);
        binding.tvGonglvC.setText("C相有功功率(KW):" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.cphaseActivePower);

        binding.tvGonglvFactorA.setText("A相功率因数:" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.aphasePowerFactorNumber);
        binding.tvGonglvFactorB.setText("B相功率因数:" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.bphasePowerFactorNumber);
        binding.tvGonglvFactorC.setText("C相功率因数:" + centerControlAllData.centerControlDetail.dlmRespElectricityMeterDataVO.cphasePowerFactorNumber);


    }

    /**
     * 填充回路列表
     */
    private void initLoopList() {
        WidgetUtils.initRecyclerView(binding.recyclerView, 0);
        adapter = new CenterControlLoopAdapter(mActivity,mMessageLoader);
        binding.recyclerView.setAdapter(adapter);
        adapter.setList(centerControlAllData.loopsList);

        if(centerControlAllData.loopsList.size()>0){
            binding.btAllClose.setVisibility(View.VISIBLE);
            binding.btAllOpen.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentDevMapCenterControlDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
        if(centerControlAllData.loopsList.size() == 0){
            XToastUtils.error("暂无回路");
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
                    for(AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean loop : centerControlAllData.loopsList){
//                        loopIdsList.add(loop.id);
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

                    for (AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean recordsBean : centerControlAllData.loopsList) {
                        recordsBean.setIsOpen(isOpen);
                    }
                    adapter.allSwitchListener = true;
                    adapter.setList(centerControlAllData.loopsList);
                }
            }
        });
    }
}
