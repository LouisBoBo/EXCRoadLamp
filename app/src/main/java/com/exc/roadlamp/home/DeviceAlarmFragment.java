package com.exc.roadlamp.home;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.databinding.FragmentDeviceAlarmBinding;
import com.exc.roadlamp.databinding.FragmentDeviceOrderBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.AlarmCountModel;
import com.exc.roadlamp.home.model.EnergyMonthModel;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeviceAlarmFragment extends Fragment implements OnChartValueSelectedListener,
        OnChartGestureListener, View.OnClickListener  {
    private TextView btn_day_alarm;
    private TextView btn_month_alarm;
    private BarChart mBarChart;
    private BarData mBarData;
    private XAxis xAxis;
    private YAxis yAxis;
    private int labelCount;
    private AlarmCountModel.DataBean dataBean;
    private FragmentDeviceAlarmBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeviceAlarmBinding.inflate(inflater,container,false);
        mBarChart = binding.mBarChar;
        btn_day_alarm = binding.btnDayAlarm;
        btn_month_alarm = binding.btnMontyAlarm;
        EventBus.getDefault().register(this);
        initview();
        initData();
        return binding.getRoot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.SELECT_TAB_CLICK) {
            initData();
            return;
        }
    }
    public void initview(){

        labelCount = 7;

        btn_day_alarm.setBackgroundResource(R.drawable.background_btn_left_select);
        btn_month_alarm.setBackgroundResource(R.drawable.background_btn_right);
        btn_day_alarm.setTextColor(Color.WHITE);
        btn_month_alarm.setTextColor(Color.GRAY);
        btn_day_alarm.setSelected(true);
        btn_month_alarm.setSelected(false);

        mBarChart.getViewPortHandler().setMaximumScaleX(1.0f);
        mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
//        initBarchart();

        btn_day_alarm.setOnClickListener(this::onClick);
        btn_month_alarm.setOnClickListener(this::onClick);
    }

    public void initData(){

        Parameter parameter1 = new Parameter();
        HttpRequest.GET(getActivity(), HttpApi.API_WOA_ALARM_COUNT, parameter1, new BeanResponseListener<AlarmCountModel>() {
            @Override
            public void onResponse(AlarmCountModel alarmCountModel, Exception error) {
                dataBean = alarmCountModel.getData();
                if(alarmCountModel.getCode() == 200){

                    if(alarmCountModel.getData().getAlarmCountDayList().size() >0 && alarmCountModel.getData().getAlarmCountMonthList().size() >0){
                        initBarchart();
                        refreshUI();
                    }
                }

            }
        });

    }
    public void refreshUI(){
        int month = dataBean.getMonthAlarm();
        int scale = dataBean.getMonthGrowthRate();

        binding.energyDayText1.setText("?????????:" + dataBean.getDayAlarm() + "???");
        binding.energyDayText2.setText("??????:" + dataBean.getDayGrowthRate() + "%");
        if(dataBean.getDayGrowthRate() >0){
            binding.energyDayAdd.setImageResource(R.mipmap.home_icon_up);
        }else if(dataBean.getDayGrowthRate() <0){
            binding.energyDayAdd.setImageResource(R.mipmap.home_icon_down);
        }

        binding.energyMonthText1.setText("?????????:" + month + "???");
        binding.energyMonthText2.setText("??????:" + scale + "%");
        if(dataBean.getMonthGrowthRate() >0){
            binding.energyMonthAdd.setImageResource(R.mipmap.home_icon_up);
        }else if(dataBean.getMonthGrowthRate() <0){
            binding.energyMonthAdd.setImageResource(R.mipmap.home_icon_down);
        }

    }
    //?????????
    public void initBarchart(){
        //????????????????????????????????????????????????????????????
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // ??????60???????????????????????????,drawn?????????
        mBarChart.setMaxVisibleValueCount(60);
        // ???????????????????????????x??????y???
        mBarChart.setPinchZoom(false);
        //????????????????????????
        mBarChart.setDrawGridBackground(false);
        //?????????????????????
        mBarChart.getLegend().setEnabled(false);

        //????????????
        mBarChart.animateX(2500);
        //????????????????????????
        mBarChart.setTouchEnabled(true);
        //????????????????????????
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);//????????????????????????

        float ratio = (float) labelCount/labelCount;
        //??????????????????????????????????????????????????????1f?????????????????????
        mBarChart.zoom(ratio,1f,0,0);

        //????????????????????????
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(labelCount);
        if(btn_day_alarm.isSelected()){
            xAxis.setLabelRotationAngle(-60);
        }else {
            xAxis.setLabelRotationAngle(0);
        }


        yAxis = mBarChart.getAxisLeft();
        yAxis.setLabelCount(8, false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(15f);
        //????????????setStartAtZero(true)
        yAxis.setAxisMinimum(0f);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);

        // ?????? ?????? y ???
        YAxis mRAxis = mBarChart.getAxisRight();
        // ?????? ?????? Y ???
        mRAxis.setEnabled(false);
        // ?????? ?????? Y???
        YAxis mLAxis = mBarChart.getAxisLeft();
        // ?????? ?????? Y ???
        mLAxis.setEnabled(true);
        // ?????? ?????? Y??? ?????????
        mLAxis.setDrawAxisLine(false);
        // ?????? ?????? ?????????
        mLAxis.setDrawGridLines(false);



        //????????????
        setmBarData();
    }

    public void setmBarData(){
        // y ?????????
        ArrayList<BarEntry> yValues = new ArrayList<>();

        if(btn_day_alarm.isSelected()){
            for (int x = 1; x < dataBean.getAlarmCountDayList().size() +1; x++) {
                int y = dataBean.getAlarmCountDayList().get(x-1).getCount();
                yValues.add(new BarEntry(x, y));
            }
        }else {
            for (int x = 1; x < dataBean.getAlarmCountMonthList().size() +1; x++) {
                int y = dataBean.getAlarmCountMonthList().get(x-1).getCount();
                yValues.add(new BarEntry(x, y));
            }
        }

        // y ????????????
        BarDataSet barDataSet = new BarDataSet(yValues, "?????????");
        barDataSet.setColor(getResources().getColor(R.color.common_blue));

        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return v + "";
            }
        });

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int)value;
                if(i<=yValues.size()){
                    if(btn_day_alarm.isSelected()){
                        return YearMonthDayStrToDate(dataBean.getAlarmCountDayList().get(i-1).getCountDate());
                    }else {
                        return YearMonthStrToDate(dataBean.getAlarmCountMonthList().get(i-1).getCountDate());
                    }
                }else
                    return "";
            }
        });

        // 2.0 ---- mBarData = new BarData(xValues, barDataSet);
        mBarData = new BarData(barDataSet);

        mBarChart.setData(mBarData);

        mBarChart.invalidate();
    }

    //?????????????????????
    public String YearMonthStrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getMonth(date) + "???";
    }
    //?????????????????????
    public String YearMonthDayStrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getMonth(date) + "???" + getDay(date);
    }
    public static String getDay(Date date){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("d");
        String ctime = formatter.format(date);
        return ctime;
    }

    public static String getMonth(Date date){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("M");
        String ctime = formatter.format(date);
        return ctime;
    }
    @Override
    public void onClick(View v) {
        if(v == btn_day_alarm){
            btn_day_alarm.setBackgroundResource(R.drawable.background_btn_left_select);
            btn_month_alarm.setBackgroundResource(R.drawable.background_btn_right);
            btn_day_alarm.setTextColor(Color.WHITE);
            btn_month_alarm.setTextColor(Color.GRAY);

            btn_day_alarm.setSelected(true);
            btn_month_alarm.setSelected(false);

            labelCount = 7;
            float ratio = (float) labelCount/labelCount;

            mBarChart.removeAllViews();
            mBarChart.getViewPortHandler().setMaximumScaleX(ratio);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
            initBarchart();
        }else if(v == btn_month_alarm){
            btn_month_alarm.setBackgroundResource(R.drawable.background_btn_right_select);
            btn_day_alarm.setBackgroundResource(R.drawable.background_btn_left);
            btn_day_alarm.setTextColor(Color.GRAY);
            btn_month_alarm.setTextColor(Color.WHITE);

            btn_day_alarm.setSelected(false);
            btn_month_alarm.setSelected(true);

            labelCount = 6;
            float ratio = (float) labelCount/labelCount;

            mBarChart.removeAllViews();
            mBarChart.getViewPortHandler().setMaximumScaleX(ratio);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
            initBarchart();
        }

        initBarchart();
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
