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

        binding.energyDayText1.setText("日告警:" + dataBean.getDayAlarm() + "件");
        binding.energyDayText2.setText("同比:" + dataBean.getDayGrowthRate() + "%");
        if(dataBean.getDayGrowthRate() >0){
            binding.energyDayAdd.setImageResource(R.mipmap.home_icon_up);
        }else if(dataBean.getDayGrowthRate() <0){
            binding.energyDayAdd.setImageResource(R.mipmap.home_icon_down);
        }

        binding.energyMonthText1.setText("月告警:" + month + "件");
        binding.energyMonthText2.setText("同比:" + scale + "%");
        if(dataBean.getMonthGrowthRate() >0){
            binding.energyMonthAdd.setImageResource(R.mipmap.home_icon_up);
        }else if(dataBean.getMonthGrowthRate() <0){
            binding.energyMonthAdd.setImageResource(R.mipmap.home_icon_down);
        }

    }
    //柱状图
    public void initBarchart(){
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(false);
        //去掉左下角图例
        mBarChart.getLegend().setEnabled(false);

        //默认动画
        mBarChart.animateX(2500);
        //设置是否可以触摸
        mBarChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);//设置是否可以缩放

        float ratio = (float) labelCount/labelCount;
        //显示的时候是按照多大的比率缩放显示，1f表示不放大缩小
        mBarChart.zoom(ratio,1f,0,0);

        //自定义设置横坐标
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
        //这个替换setStartAtZero(true)
        yAxis.setAxisMinimum(0f);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);

        // 获取 右边 y 轴
        YAxis mRAxis = mBarChart.getAxisRight();
        // 隐藏 右边 Y 轴
        mRAxis.setEnabled(false);
        // 获取 左边 Y轴
        YAxis mLAxis = mBarChart.getAxisLeft();
        // 隐藏 左边 Y 轴
        mLAxis.setEnabled(true);
        // 取消 左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(false);
        // 取消 横向 网格线
        mLAxis.setDrawGridLines(false);



        //设置数据
        setmBarData();
    }

    public void setmBarData(){
        // y 轴数据
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

        // y 轴数据集
        BarDataSet barDataSet = new BarDataSet(yValues, "条形图");
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

    //获取年月字符串
    public String YearMonthStrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getMonth(date) + "月";
    }
    //获取月日字符串
    public String YearMonthDayStrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getMonth(date) + "月" + getDay(date);
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
