package com.exc.roadlamp.work.strategy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentStrategyActionBinding;
import com.exc.roadlamp.device.bean.DeviceInfoBean;
import com.exc.roadlamp.device.bean.DeviceSystemBean;
import com.exc.roadlamp.device.fragment.DeviceLampAddFragment;
import com.exc.roadlamp.device.fragment.LampLightDetailLightListFragment;
import com.exc.roadlamp.home.model.WeekLightModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.model.ActionOptionModel;
import com.exc.roadlamp.work.model.LightStrategyModel;
import com.exc.roadlamp.work.model.StrategyActionModel;
import com.exc.roadlamp.work.model.StrategyTypeModel;
import com.google.gson.JsonObject;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xuexiang.xutil.data.SPUtils.getSharedPreferences;

@Page(name = "????????????")
public class StrategyActionAdd extends MyBaseFragment implements View.OnClickListener{
    private FragmentStrategyActionBinding binding;
    private List<String> mPublicOption = new ArrayList<>();
    private List<String> mActionOption = new ArrayList<>();
    private List<String> mModelOption = new ArrayList<>();
    private List<String> mSwitchOption = new ArrayList<>();
    private List<String> mWeekList = new ArrayList<>();
    private List<ActionOptionModel.DataBean> recordsBeanList = new ArrayList<>();
    private List<ActionOptionModel.DataBean> modelrecordsBeanList = new ArrayList<>();

    private StrategyActionModel strategyActionModel;

    private ArrayList<TextView> pubviews;

    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    private TextView tv_week1;
    private TextView tv_week2;
    private TextView tv_week3;
    private TextView tv_week4;
    private TextView tv_week5;
    private TextView tv_week6;
    private TextView tv_week7;

    private int typeSelectOption = 0;
    private int progress = 0;

    @AutoWired(name = "strategy_type")
    StrategyTypeModel strategyTypeModel;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    public void onClick(View v) {
        typeSelectOption =0;

        if(v == binding.tvAction1.tvContent || v == binding.tvAction1.selectMore){
            showStreeConstellationPickerView(1,binding.tvAction1.tvContent);
        }else if(v == binding.tvAction2.tvContent || v == binding.tvAction2.selectMore){
            showStreeConstellationPickerView(2,binding.tvAction2.tvContent);
        }else if(v == binding.tvAction3.tvContent || v == binding.tvAction3.selectMore){
            showCodePopview();
        }else if(v == binding.tvAction4.tvContent || v == binding.tvAction4.selectMore){
            showTimePickerDialog(getActivity(),  2, binding.tvAction4.tvContent, calendar);
        }else if(v == binding.tvAction5.tvContent || v == binding.tvAction5.selectMore){
            showDatePickerDialog(getActivity(),  2, binding.tvAction5.tvContent, calendar);
        }else if(v == binding.tvAction6.tvContent || v == binding.tvAction6.selectMore){
            showDatePickerDialog(getActivity(),  2, binding.tvAction6.tvContent, calendar);
        }else if(v == binding.tvAction7.tvContent || v == binding.tvAction7.selectMore){
            showWeekPopview();
        }else if(v == binding.tvAction11.tvContent || v == binding.tvAction11.selectMore){
            showStreeConstellationPickerView(3,binding.tvAction11.tvContent);
        }else if(v == binding.tvConfirm){
            if(binding.tvAction5.tvContent.getText().length()>0 && binding.tvAction6.tvContent.getText().length()>0){
                if(isDate2Bigger(binding.tvAction5.tvContent.getText().toString(),binding.tvAction6.tvContent.getText().toString())){
                    handelActionData();
                }else
                    XToastUtils.info("????????????????????????????????????");
            }else
                handelActionData();


//            if(binding.tvAction5.tvContent.getText() != null && binding.tvAction6.tvContent.getText() != null &&isDate2Bigger(binding.tvAction5.tvContent.getText().toString(),binding.tvAction6.tvContent.getText().toString())){
//                handelActionData();
//            }else {
//                XToastUtils.info("????????????????????????????????????");
//                return;
//            }

        }
    }

    @Override
    protected void initViews() {
        binding.tvAction1.tvTitle.setText("????????????");
        binding.tvAction11.tvTitle.setText("??????");
        binding.tvAction2.tvTitle.setText("??????");
        binding.tvAction3.tvTitle.setText("??????");
        binding.tvAction4.tvTitle.setText("????????????");
        binding.tvAction5.tvTitle.setText("????????????");
        binding.tvAction6.tvTitle.setText("????????????");
        binding.tvAction7.tvTitle.setText("??????");

        if(strategyTypeModel.strategy_type == 1){
            binding.tvAction2.tvTitle.setText("????????????");
            binding.tvAction3.getRoot().setVisibility(View.GONE);
        }

        binding.tvAction6.imgMark.setVisibility(View.INVISIBLE);
        binding.tvAction7.imgMark.setVisibility(View.INVISIBLE);
        binding.tvAction7.bottomLine.setVisibility(View.GONE);

        binding.tvAction1.tvContent.setOnClickListener(this::onClick);
        binding.tvAction2.tvContent.setOnClickListener(this::onClick);
        binding.tvAction3.tvContent.setOnClickListener(this::onClick);
        binding.tvAction4.tvContent.setOnClickListener(this::onClick);
        binding.tvAction5.tvContent.setOnClickListener(this::onClick);
        binding.tvAction6.tvContent.setOnClickListener(this::onClick);
        binding.tvAction7.tvContent.setOnClickListener(this::onClick);
        binding.tvAction11.tvContent.setOnClickListener(this::onClick);

        binding.tvAction1.selectMore.setOnClickListener(this::onClick);
        binding.tvAction2.selectMore.setOnClickListener(this::onClick);
        binding.tvAction3.selectMore.setOnClickListener(this::onClick);
        binding.tvAction4.selectMore.setOnClickListener(this::onClick);
        binding.tvAction5.selectMore.setOnClickListener(this::onClick);
        binding.tvAction6.selectMore.setOnClickListener(this::onClick);
        binding.tvAction7.selectMore.setOnClickListener(this::onClick);
        binding.tvAction11.selectMore.setOnClickListener(this::onClick);
        binding.tvConfirm.setOnClickListener(this::onClick);

        initData();
    }

    public void initData(){
        mSwitchOption.add("???");
        mSwitchOption.add("???");

        getModelData();
        getActionData();
    }

    //????????????
    public void getActionData(){
        mMessageLoader.show();
        Parameter json = new Parameter();

        String url = HttpApi.API_SL_LAMP_LIGHT_MODE_OPTION;
        HttpRequest.GET(getActivity(), url, json, new BeanResponseListener<ActionOptionModel>() {
            @Override
            public void onResponse(ActionOptionModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    modelrecordsBeanList.addAll(bean.getData());
                    for (ActionOptionModel.DataBean dataBean : modelrecordsBeanList) {

                        mModelOption.add(dataBean.getName());
                    }
                }
            }
        });
    }
    //??????????????????
    public void getModelData(){
        mMessageLoader.show();
        Parameter json = new Parameter();

        String url = HttpApi.API_SL_SYSTEM_TIMING_MODE_OPTION;
        HttpRequest.GET(getActivity(), url, json, new BeanResponseListener<ActionOptionModel>() {
            @Override
            public void onResponse(ActionOptionModel bean, Exception error) {
                mMessageLoader.dismiss();
                if(null == error){
                    recordsBeanList.addAll(bean.getData());
                    for (ActionOptionModel.DataBean dataBean : recordsBeanList) {
                        if(dataBean.getMutexGroup() ==1) {
                            mActionOption.add(dataBean.getName());
                        }
                    }
                }
            }
        });
    }


    //????????????
    public void handelActionData(){
        //??????id
        Integer id = 0;
        String action = "";
        for (ActionOptionModel.DataBean dataBean : recordsBeanList) {
            if(binding.tvAction1.tvContent.getText().toString().contains(dataBean.getName())){
                id = dataBean.getId();
                action = dataBean.getName();
            }
        }

        Integer lightModeId =0;
        for (ActionOptionModel.DataBean dataBean : modelrecordsBeanList) {
            if(binding.tvAction11.tvContent.getText().toString().contains(dataBean.getName())){
                lightModeId = dataBean.getId();
            }
        }


        //??????
        List<String> cycletypes = new ArrayList<>();
        for (String s : mWeekList) {
            if(s.contains("??????")){
                cycletypes.add("1");
            }else if(s.contains("??????")){
                cycletypes.add("2");
            }else if(s.contains("??????")){
                cycletypes.add("3");
            }else if(s.contains("??????")){
                cycletypes.add("4");
            }else if(s.contains("??????")){
                cycletypes.add("5");
            }else if(s.contains("??????")){
                cycletypes.add("6");
            }else if(s.contains("??????")){
                cycletypes.add("7");
            }
        }

        strategyActionModel = new StrategyActionModel();
        strategyActionModel.setStrategytype(strategyTypeModel.strategy_type);

        List<StrategyActionModel.DlmReqSceneActionVOListBean> dlmReqSceneActionVOList = new ArrayList<>();
        StrategyActionModel.DlmReqSceneActionVOListBean listBean = new StrategyActionModel.DlmReqSceneActionVOListBean();
        listBean.setIsOpen(binding.tvAction2.tvContent.getText().toString().contains("???")?1:0);
        listBean.setExecutionTime(binding.tvAction4.tvContent.getText().toString());
        listBean.setStartDate(binding.tvAction5.tvContent.getText().toString());
        listBean.setEndDate(binding.tvAction6.tvContent.getText().toString());
        listBean.setCycleTypes(cycletypes);
        listBean.setId(id);
        listBean.setAction(action);

        if(strategyActionModel.getStrategytype() ==0)
        {
            listBean.setLightModeId(lightModeId);
            listBean.setProgress(progress);
        }
        dlmReqSceneActionVOList.add(listBean);
        strategyActionModel.setDlmReqSceneActionVOList(dlmReqSceneActionVOList);

        EventBus.getDefault().post(strategyActionModel);
        popToBack();
    }

    private void showStreeConstellationPickerView(int index, TextView type) {
        String str_title = "??????????????????";
        switch (index){
            case 1:
                mPublicOption = mActionOption;
                break;
            case 2:
                mPublicOption = mSwitchOption;
                break;
            case 3:
                str_title = "????????????";
                mPublicOption = mModelOption;
        }

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            type.setText(mPublicOption.get(options1));
            typeSelectOption = options1;

            if(binding.tvAction1.tvContent.getText().toString().contains("???????????????")){
                binding.tvAction11.getRoot().setVisibility(View.VISIBLE);
                binding.tvAction7.getRoot().setVisibility(View.GONE);
                binding.tvAction6.bottomLine.setVisibility(View.GONE);
            }else {
                binding.tvAction11.getRoot().setVisibility(View.GONE);
                binding.tvAction7.getRoot().setVisibility(View.VISIBLE);
                binding.tvAction6.bottomLine.setVisibility(View.VISIBLE);
            }

            return false;
        })
                .setTitleText(str_title)
                .setSelectOptions(typeSelectOption)
                .build();
        pvOptions.setPicker(mPublicOption);
        pvOptions.show();
    }

    //?????????
    private void showCodePopview(){
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_light, null);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
                binding.tvAction3.tvContent.setText(progress + "%");
            }
        });
        contentView.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
            }
        });

        SeekBar sk = contentView.findViewById(R.id.sk);
        sk.setProgress(progress);
        TextView sktitle = contentView.findViewById(R.id.tv_title);
        sktitle.setText("??????:" + progress + "%");
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int pro, boolean fromUser) {
                progress = pro;
                sktitle.setText("??????:" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {

                }catch (Exception e){
                    e.printStackTrace();
                };
            }
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.MyDialog);
        bottomDialog.show();
    }

    //??????
    private void showWeekPopview(){
        Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_week, null);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
                mWeekList = new ArrayList<>();
                for (TextView tv : pubviews) {
                    if(tv.isSelected()){
                        mWeekList.add(tv.getText().toString());
                    }
                }
                binding.tvAction7.tvContent.setText(String.join(",", mWeekList.stream().map(String::valueOf).collect(Collectors.toList())));
            }
        });
        contentView.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//??????
                bottomDialog.dismiss();
            }
        });

        tv_week1 = contentView.findViewById(R.id.tv_week1);
        tv_week2 = contentView.findViewById(R.id.tv_week2);
        tv_week3 = contentView.findViewById(R.id.tv_week3);
        tv_week4 = contentView.findViewById(R.id.tv_week4);
        tv_week5= contentView.findViewById(R.id.tv_week5);
        tv_week6 = contentView.findViewById(R.id.tv_week6);
        tv_week7 = contentView.findViewById(R.id.tv_week7);

        pubviews = new ArrayList<>();
        pubviews.add(tv_week1);
        pubviews.add(tv_week2);
        pubviews.add(tv_week3);
        pubviews.add(tv_week4);
        pubviews.add(tv_week5);
        pubviews.add(tv_week6);
        pubviews.add(tv_week7);

        for (TextView view : pubviews) {
            for (String s : mWeekList) {
                if(s.contains(view.getText().toString())){
                    view.setSelected(true);
                }
            }
            view.setBackgroundResource(view.isSelected()?R.drawable.background_build_selector:R.drawable.background_build_unselector);
        }


        tv_week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });
        tv_week7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWeekData((TextView) v);
            }
        });

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.MyDialog);
        bottomDialog.show();
    }

    public void handleWeekData(TextView tv){
        tv.setSelected(!tv.isSelected());
        tv.setBackgroundResource(tv.isSelected()?R.drawable.background_build_selector:R.drawable.background_build_unselector);
    }


    /**
     * ????????????
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // ???????????????(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // ????????????????????????????????????????????????????????????
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                if(monthOfYear <10){
                    if(dayOfMonth <10){
                        tv.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }else {
                        tv.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }else {
                    if(dayOfMonth <10){
                        tv.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }else {
                        tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }
            }
        }
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * ????????????
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // ????????????TimePickerDialog??????????????????????????????
        // ???????????????Activity???context?????????
        new TimePickerDialog( activity,themeResId,
                // ???????????????
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay + ":" + minute);
                        if(hourOfDay<10){
                            if(minute<10){
                                tv.setText("0" + hourOfDay + ":" + "0" + minute);
                            }else {
                                tv.setText("0" + hourOfDay + ":" + minute);
                            }
                        }else {
                            if(minute<10){
                                tv.setText(hourOfDay + ":" + "0" + minute);
                            }else {
                                tv.setText(hourOfDay + ":" + minute);
                            }
                        }

                    }
                }
                // ??????????????????
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true????????????24?????????
                ,true).show();
    }

    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentStrategyActionBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
