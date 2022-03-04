package com.exc.roadlamp.work.control;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLightStragegyBinding;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.LightInfoBean;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.adapter.LightstrategyAdapter;
import com.exc.roadlamp.work.lightcontrol.LightStrategyManagerList;
import com.exc.roadlamp.work.model.LightStrategyModel;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "下发场景")
public class ControlManagerScene extends MyBaseFragment implements View.OnClickListener{
    FragmentLightStragegyBinding binding;
    LightstrategyAdapter itemAdapter;
    private int pageNum = 1;
    private int pageSize = 30;
    private int strategyId = 0;
    private int dosearch_screen =0;//0正常1搜索

    private List<LightStrategyModel.DataBean.RecordsBean> lightStrategyList = new ArrayList<>();

    @AutoWired(name = "device_info")
    LightInfoBean device_info;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.STRATEGY_ADD_SUCCESS) {
            pageNum = 1;
            initData();
        }
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        return titleBar;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnSearch){
            dosearch_screen = 1;
            initData();
        }else if(v == binding.clearBtn){
            binding.editSearch.setText("");
            dosearch_screen = 0;
            initData();
        }
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);

        //设备列表
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new LightstrategyAdapter(getActivity());
        binding.recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClick(new LightstrategyAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                strategyId = lightStrategyList.get(index).getId();
                showCoverDialog();
            }
        });

        itemAdapter.setOnDeleateClick(new LightstrategyAdapter.OnDeleateClick() {
            @Override
            public void click(int index) {
                strategyId = lightStrategyList.get(index).getId();

                DialogLoader.getInstance().showConfirmDialog(
                        getContext(),
                        getString(R.string.del_stratety_permission),
                        getString(R.string.tip_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            editStragegy(index);
                        },
                        getString(R.string.tip_no),
                        (dialog, which) -> {
                            dialog.dismiss();
                        }
                );
            }
        });

        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(binding.editSearch.getText().toString())){
                    //清除过滤
                    pageNum = 1;
                    dosearch_screen =1;
                    initData();

                    binding.clearBtn.setVisibility(View.GONE);
                }else {
                    binding.clearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.clearBtn.setOnClickListener(this::onClick);
        binding.btnSearch.setOnClickListener(this::onClick);

        initData();

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            //处于搜索时不能刷新
            binding.refreshLayout.finishRefresh();
            binding.refreshLayout.finishLoadMore();

            pageNum = 1;
            initData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //处于搜索时不能加载
            binding.refreshLayout.finishRefresh();
            binding.refreshLayout.finishLoadMore();

            pageNum++;
            initData();
        });
    }

    public void initData(){
        mMessageLoader.show();
        Parameter json = new Parameter();
        json.put("pageSize", pageSize);
        json.put("pageNum", pageNum);
        json.put("controlType",0);
        json.put("sortRule",0);
        json.put("sortType",3);

        if(dosearch_screen == 1){
            json.put("name",binding.editSearch.getText().toString());
        }
        HttpRequest.GET(getActivity(), HttpApi.API_DLM_LOOP_SCENE_STRATEGY_PAGE,json, new BeanResponseListener<LightStrategyModel>() {
            @Override
            public void onResponse(LightStrategyModel bean, Exception error) {
                mMessageLoader.dismiss();
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if(null == error){
                    if(pageNum == 1){
                        lightStrategyList.clear();
                    }

                    lightStrategyList.addAll(bean.getData().getRecords());
                    itemAdapter.setmDatas(lightStrategyList);
                }
            }
        });
    }

    public void editStragegy(Integer index){
        mMessageLoader.show();

        Parameter json = new Parameter();

        String url = HttpApi.API_DLM_LOOP_SCENE_STRATEGY + String.valueOf(strategyId);
        HttpRequest.DELETE(getActivity(),url, json, new BeanResponseListener<LightStrategyModel>() {
            @Override
            public void onResponse(LightStrategyModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    pageNum =1;
                    initData();
                    XToastUtils.info(bean.getMessage());
                }
            }
        });
    }

    //场景下发
    public void executeStragegy(){
        mMessageLoader.show();

        JSONObject json = new JSONObject();
        json.put("strategyId",strategyId);
        json.put("pageSize",0);

        if(device_info.areaId != -1){
            json.put("areaId",device_info.areaId);
        }
        if(device_info.siteId != -1){
            json.put("siteId",device_info.siteId);
        }
        if(device_info.streetId != -1){
            json.put("streetId",device_info.streetId);
        }
        if(device_info.searchName != null){
            json.put("name",device_info.searchName);
        }

        HttpRequest.JSONPOST(getActivity(), HttpApi.API_DLM_LOOP_SCENE_STRATEGY_EXECUTE, json.toJSONString(), new BeanResponseListener<LightStrategyModel>() {
            @Override
            public void onResponse(LightStrategyModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    XToastUtils.info(bean.getMessage());
                }
            }
        });
    }

    public void showCoverDialog(){
        DialogLoader.getInstance().showConfirmDialog(
                getContext(),
                getString(R.string.tip_stratety_permission),
                getString(R.string.tip_yes),
                (dialog, which) -> {
                    dialog.dismiss();
                    executeStragegy();
                },
                getString(R.string.tip_no),
                (dialog, which) -> {
                    dialog.dismiss();
                }
        );
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentLightStragegyBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
