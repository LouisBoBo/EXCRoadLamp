package com.exc.roadlamp.work.workorder;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.bean.BaseBean;
import com.exc.roadlamp.databinding.FragmentOrderAuditBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.MyDateUtil;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.MyTimePickerBuilder;
import com.exc.roadlamp.work.MyTimePickerType;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xrouter.utils.TextUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xutil.data.DateUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lombok.SneakyThrows;

@Page(name = "工单审核")
public class OrderAuditFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentOrderAuditBinding binding;
    private Activity mActivity;
    @AutoWired(name = "orderDetail")
    OrderDetailBean orderDetailBean;
    private int selectOperatorId = -1;
    private String finishDataStr;//结束日期
    private String finishTimeStr = "23:59";//结束时间
    private TimePickerView mDatePicker;
    private String[] mTimeOption;
    private TimePickerView mTimePicker;
    private int opinion = 1;//1为通过
    private JsonMap submitJsonMap;


    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));
        String titleStr = "工单详情";
        switch (orderDetailBean.getData().getWoaRespOrderVO().getStatusId()) {
            case 1: //待初审
                titleStr = "待初审";
                break;
            case 5://待审核
                titleStr = "待审核";
                break;
            case 7://已超时
                titleStr = "已超时";
                break;
            case 2://被驳回
                titleStr = "被驳回";
                break;
            case 3://待处理
                titleStr = "待处理";
                break;
            case 4://跟进中
                titleStr = "跟进中";
                break;
            case 6://审核通过
                titleStr = "审核通过";
                break;
        }
        titleBar.setTitle(titleStr);
        return titleBar;
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentOrderAuditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        mActivity = getActivity();
        finishDataStr = DateUtils.millis2String(System.currentTimeMillis(), DateUtils.yyyyMMdd.get());
//        finishTimeStr = DateUtils.millis2String(System.currentTimeMillis(),DateUtils.HHmm.get());
        binding.tvCompleteDate.setText(finishDataStr);
        binding.tvCompleteTime.setText(finishTimeStr);
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.getNowDate();
        calendar.setTime(date);
        initNote();
    }

    @Override
    protected void initListeners() {
        binding.rlOpinion.setOnClickListener(this);
        binding.rlOperator.setOnClickListener(this);
        binding.rlCompleteDate.setOnClickListener(this);
        binding.rlCompleteTime.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }


    @SneakyThrows
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_opinion:
                ArrayList<String> optionList = new ArrayList<>();
                optionList.add("通过");
                optionList.add("不通过");
                OptionsPickerView pvOptionsDate = new OptionsPickerBuilder(getContext(), (view, options1, options2, options3) -> {
                    binding.tvOpinion.setText(optionList.get(options1));
                    if (optionList.get(options1).equals("不通过")) {
                        opinion = 0;
                    } else {
                        opinion = 1;
                    }

                    return false;
                })
                        .setTitleText("选择处理意见")
                        .setTitleSize(17)
                        .setCancelColor(0xFF1C7AFE)
                        .setSubmitColor(0xFF1C7AFE)
                        .setSelectOptions(0)
                        .build();
                pvOptionsDate.setPicker(optionList);
                pvOptionsDate.show();
                break;
            case R.id.rl_operator:
                showSelectOperatorDialog();
                break;
            case R.id.rl_complete_date:
                if (mDatePicker == null) {
                    mDatePicker = new TimePickerBuilder(mActivity, (date, view) -> {
                        String selectDateStr = DateUtils.date2String(date, DateUtils.yyyyMMdd.get());
                        String nowDateStr = DateUtils.millis2String(System.currentTimeMillis(), DateUtils.yyyyMMdd.get());
                        //比较两个日期的大小
                        try {
                            if (MyDateUtil.dateToStampOnlyDay(selectDateStr) < MyDateUtil.dateToStampOnlyDay(nowDateStr)) {
                                XToastUtils.error("完成日期不能小于当前日期");
                                return;
                            }
                            binding.tvCompleteDate.setText(selectDateStr);
                            finishDataStr = selectDateStr;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    })
                            .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setSubmitColor(Color.parseColor("#1C7AFE"))
                            .setCancelColor(Color.parseColor("#1C7AFE"))
                            .setTitleText("选择处理完成日期")
                            .build();
                }
                mDatePicker.show();
                break;
            case R.id.rl_complete_time:
                if (mTimePicker == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtils.getNowDate());
                    mTimePicker = new MyTimePickerBuilder(getContext(), (date, view) -> {
                        finishTimeStr = DateUtils.date2String(date, DateUtils.HHmm.get());
                        binding.tvCompleteTime.setText(finishTimeStr);
                    })
                            .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                            .setType(MyTimePickerType.TIME_1)
                            .setSubmitColor(Color.parseColor("#1C7AFE"))
                            .setCancelColor(Color.parseColor("#1C7AFE"))
                            .setTitleText("选择处理完成时间")
                            .setDate(calendar)
                            .build();
                }
                mTimePicker.show();
                break;
            case R.id.btn_cancel:
                popToBack();
                break;
            case R.id.btn_submit:
                checkSubmit();
                break;


        }

    }

    private void showSelectOperatorDialog() {

        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi.ORDER_OPERATOR_LIST, parameter, new BeanResponseListener<OrderOperatorList>() {
            @Override
            public void onResponse(OrderOperatorList orderOperatorList, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {

                    if (orderOperatorList.getData() == null || orderOperatorList.getData().size() == 0) {
                        ToastUtils.toast("没有处理人员");
                        return;
                    }

                    ArrayList<String> operatorNameStrs = new ArrayList<>();
                    for (OrderOperatorList.DataBean bean : orderOperatorList.getData()) {
                        operatorNameStrs.add(bean.getName());
                    }
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
                        selectOperatorId = orderOperatorList.getData().get(options1).getId();
                        binding.tvOperator.setText(operatorNameStrs.get(options1));
                        return false;
                    })
                            .setTitleText("请选择处理人员")
                            .setTitleSize(17)
                            .setCancelColor(0xFF1C7AFE)
                            .setSubmitColor(0xFF1C7AFE)
                            .setSelectOptions(0)
                            .build();
                    pvOptions.setPicker(operatorNameStrs);
                    pvOptions.show();


                }


            }
        });


    }

    /**
     * 提交之前的检查
     * @throws ParseException
     */
    private void checkSubmit() throws ParseException {
        submitJsonMap = new JsonMap();
        submitJsonMap.put("opinion", opinion);
        submitJsonMap.put("description", binding.etNote.getText().toString().trim());
        if (opinion == 0) {
            doSubmit();
            return;
        }
        if (selectOperatorId == -1) {
            ToastUtils.toast("请选择处理人员");
            return;
        }

        String subFinishTime = finishDataStr + " " + finishTimeStr;
        if (MyDateUtil.dateToStampOnlyDay2(subFinishTime) <= DateUtils.getNowMills()) {
            ToastUtils.toast("选择处理时间小于当前时间");
            return;
        }

        submitJsonMap.put("processor", selectOperatorId);
        submitJsonMap.put("finishTime", subFinishTime);
        submitJsonMap.put("id", orderDetailBean.getData().getWoaRespOrderVO().getOrderId());
        doSubmit();
    }

    /**
     * 提交
     */
    private void doSubmit() {
        mMessageLoader.show();
        HttpRequest.JSONPOST(mActivity, HttpApi.ORDER_CHUSHEN, submitJsonMap, new BeanResponseListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean result, Exception error) {
                mMessageLoader.dismiss();
                if(error == null){
                    XToastUtils.success("初审成功");
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setEventEnum(EventEnum.ORDER_OPERATOR_SUCCESS);
                    EventBus.getDefault().post(eventMessage);
                    popToBack();
                }

            }
        });

    }


    private void initNote() {
        binding.etNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取输入框中的数据
                String edit = binding.etNote.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etNote.setText(20 - s.length() + "");
                //如果EditText中的数据不为空，且长度大于指定的最大长度
                if (!TextUtils.isEmpty(s) && s.length() > 20) {
                    //删除指定长度之后的数据
                    s.delete(20, binding.etNote.getSelectionEnd());
                    XToastUtils.error("超出长度");
                }
            }
        });

    }


}
