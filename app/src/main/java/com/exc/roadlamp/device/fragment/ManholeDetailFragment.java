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
        binding.holeAngle.setText("????????????:" + recordsBean.getLimitUpper()+"??");
        binding.holeDeploymentStatus.setText("????????????:" + (recordsBean.getDeployDefence()==1?"???":"???"));
        binding.holeArea.setText(recordsBean.getLocationAreaName());
        binding.holeStree.setText(recordsBean.getLocationStreetName());
        binding.holeStie.setText(recordsBean.getLocationSiteName());
        binding.holeTime.setText("??????????????????:" + recordsBean.getLastOnlineTime());

        if(recordsBean.getNetworkState() == 1){
            binding.tvIsonline.setText("??????");
            binding.tvIsonline.setBackgroundResource(R.color.divier_green);
        }else {
            binding.tvIsonline.setText("??????");
            binding.tvIsonline.setBackgroundResource(R.color.region_black);
        }

        if(recordsBean.getStatus() == 1){
            binding.tvIsopen.setText("??????");
            binding.tvIsopen.setBackgroundResource(R.color.divier_green);
        }else {
            binding.tvIsopen.setText("??????");
            binding.tvIsopen.setBackgroundResource(R.color.region_black);
        }
    }

    /**
     * ????????????
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
                            //?????????????????? 76????????????????????????  77 ????????????????????? 78
                            switch (record.getTypeId()){
                                case 76:
                                    almstaus = "??????????????????";
                                    break;
                                case 77:
                                    almstaus = "?????????????????????";
                                    break;
                                case 78:
                                    almstaus = "??????????????????";
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
