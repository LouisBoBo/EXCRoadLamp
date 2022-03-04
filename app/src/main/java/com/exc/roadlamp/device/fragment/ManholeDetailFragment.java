package com.exc.roadlamp.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentLampPoleDetailBinding;
import com.exc.roadlamp.databinding.FragmentManholeDetailBinding;
import com.exc.roadlamp.device.DevTypeEnum;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.exc.roadlamp.device.bean.ManholeArmBean;
import com.exc.roadlamp.device.bean.ManholeList;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class ManholeDetailFragment extends MyBaseFragment {
    private FragmentManholeDetailBinding binding;
    public static final String DATA_KEY = "DEV_ARGS_KEY";
    private ManholeList.DataBean.RecordsBean recordsBean;
    public static final int deviceType = 103;

    @Override
    protected void initViews() {
        initData();
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentManholeDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static ManholeDetailFragment getInstance(ManholeList.DataBean.RecordsBean dataBean) {
        ManholeDetailFragment holeDetailFragment = new ManholeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, dataBean);
        holeDetailFragment.setArguments(args);
        return holeDetailFragment;
    }

    public void initData(){
        recordsBean = (ManholeList.DataBean.RecordsBean) getArguments().getSerializable(DATA_KEY);
        refeshUI();

        queryManholeStatus();
    }

    public void refeshUI(){
        binding.tvHolename.setText(recordsBean.getName());
        binding.tvNum.setText(recordsBean.getNum());
        binding.holeAngle.setText("井盖角度:" + recordsBean.getLimitUpper()+"°");
        binding.holeDeploymentStatus.setText("布防状态:" + (recordsBean.getDeployDefence()==1?"开":"关"));
        binding.holeArea.setText(recordsBean.getLocationAreaName());
        binding.holeStree.setText(recordsBean.getLocationStreetName());
        binding.holeStie.setText(recordsBean.getLocationSiteName());
        binding.holeTime.setText("最后在线时间:" + recordsBean.getLastOnlineTime());

        if(recordsBean.getNetworkState() == 1){
            binding.tvIsonline.setText("在线");
            binding.tvIsonline.setBackgroundResource(R.color.divier_green);
        }else {
            binding.tvIsonline.setText("离线");
            binding.tvIsonline.setBackgroundResource(R.color.region_black);
        }

        if(recordsBean.getStatus() == 1){
            binding.tvIsopen.setText("开启");
            binding.tvIsopen.setBackgroundResource(R.color.divier_green);
        }else {
            binding.tvIsopen.setText("关闭");
            binding.tvIsopen.setBackgroundResource(R.color.region_black);
        }
    }

    /**
     * 查询井盖
     */
    private void queryManholeStatus() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        //alarmTypeId=76&pageNum=1&pageSize=10
//        parameter.put("alarmTypeId",76);

        HttpRequest.GET(mActivity, HttpApi.API_WOA_ALARM_QUERY, parameter, new BeanResponseListener<ManholeArmBean>() {
            @Override
            public void onResponse(ManholeArmBean manholeList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {

                    for (ManholeArmBean.DataBean.RecordsBean record : manholeList.getData().getRecords()) {
                        if(record.getDeviceName().equals(recordsBean.getName())){
                            String almstaus = "";
                            //井盖位移报警 76，井盖强震动报警  77 ，井盖倾角报警 78
                            switch (record.getTypeId()){
                                case 76:
                                    almstaus = "井盖位移报警";
                                    break;
                                case 77:
                                    almstaus = "井盖强震动报警";
                                    break;
                                case 78:
                                    almstaus = "井盖倾角报警";
                                    break;

                            }
                            binding.holeTitle.setText(almstaus);
                            binding.holeTitle.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        });
    }
}
