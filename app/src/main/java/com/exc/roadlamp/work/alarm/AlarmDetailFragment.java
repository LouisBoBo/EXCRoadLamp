package com.exc.roadlamp.work.alarm;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentAlarmDetailBinding;
import com.exc.roadlamp.home.model.AreaModel;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.work.model.AlarmNewsModel;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Page(name = "详情")
public class AlarmDetailFragment extends MyBaseFragment {
    @AutoWired(name = "alarm_detail")
    AlarmNewsModel.DataBean.RecordsBean areaModel;

    private FragmentAlarmDetailBinding binding;

    //收到消息回主UI刷新界面
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    EventBus.getDefault().post(areaModel);
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        binding.tvContent.setText(areaModel.getContent());
        binding.tvType.setText(areaModel.getServiceName());
        binding.tvTime.setText(areaModel.getCreateTime());

        //如果是未读消息将消息标为已读
        if(areaModel.getHaveRead() == 0){
            initData();
        }
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentAlarmDetailBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    public void initData(){
        List<AlarmNewsModel.DataBean.RecordsBean> recordsBeans = new ArrayList<>();
        recordsBeans.add(areaModel);

        JSONObject json = new JSONObject();
        json.put("listNews",recordsBeans);
        mMessageLoader.show();
        HttpRequest.JSONPOST(getActivity(), HttpApi.API_WOA_ALARM_NEWS_STATUS, json.toJSONString(), new BeanResponseListener<BaseModel>() {
            @Override
            public void onResponse(BaseModel alarmNewsModel, Exception error) {
                mMessageLoader.dismiss();
                if(alarmNewsModel.getCode() == 200){
                    myHandler.sendEmptyMessage(123);
                }
            }
        });
    }
}
