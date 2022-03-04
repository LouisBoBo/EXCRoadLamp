package com.exc.roadlamp.home;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.NumberCountModel;
import com.exc.roadlamp.home.model.WeekLightModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeviceLightFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener,
        OnChartGestureListener {

    private BarChart mBarChart3;
    private BarChart mBarChart7;
    private BarChart mBarChart14;
    private BarChart mBarChart30;
    private int labelCount;
    private WeekLightModel.DataBean dataBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_light,container,false);
        mBarChart3 = view.findViewById(R.id.mBarChar3);
        mBarChart7 = view.findViewById(R.id.mBarChar7);
        mBarChart14 = view.findViewById(R.id.mBarChar14);
        mBarChart30 = view.findViewById(R.id.mBarChar30);

        EventBus.getDefault().register(this);
        initview();
        initData();
        return view;
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
    }

    public void initData(){
        Parameter parameter = new Parameter();
        HttpRequest.GET(getActivity(), HttpApi.API_SL_LAMP_ENERGY_WEEKLIGHTINGRATE, parameter, new BeanResponseListener<WeekLightModel>() {
            @Override
            public void onResponse(WeekLightModel weekLightModel, Exception error) {
                if(weekLightModel.getCode() == 200){
                    dataBean = weekLightModel.getData();
                    if(dataBean.getTimeList().size() >0 && dataBean.getEnergyList().size()>0){
                        initBarchart(mBarChart7);
                    }
                }

            }
        });
    }

    //柱状图
    public void initBarchart(BarChart mBarChart){
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

        float ratio = (float) labelCount/(mBarChart == mBarChart3?3:7);
        //显示的时候是按照多大的比率缩放显示，1f表示不放大缩小
        mBarChart.zoom(ratio,1f,0,0);

        //自定义设置横坐标
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);
        if(mBarChart == mBarChart3){
            xAxis.setLabelCount(3);
        }

        xAxis.setLabelRotationAngle(-60);

        YAxis yAxis = mBarChart.getAxisLeft();
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
        setmBarData(mBarChart,xAxis);
    }

    public void setmBarData(BarChart mBarChart , XAxis xAxis){
        // y 轴数据
        ArrayList<BarEntry> yValues = new ArrayList<>();
        // 2.0 ----x 轴数据
        // ArrayList<String> xValues = new ArrayList<>();
        for (int x = 1; x < dataBean.getTimeList().size() +1; x++) {
            int y = dataBean.getEnergyList().get(x-1).intValue();
            yValues.add(new BarEntry(x, y));
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
                    return YearMonthDayStrToDate(dataBean.getTimeList().get(i-1));
                }else
                    return "";
            }
        });

        if(mBarChart == mBarChart3){
            mBarChart.getViewPortHandler().setMaximumScaleX(0.43f);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
        }else if(mBarChart == mBarChart7){
            mBarChart.getViewPortHandler().setMaximumScaleX(1.0f);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
        }else if(mBarChart == mBarChart14){
            mBarChart.getViewPortHandler().setMaximumScaleX(2.0f);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
        }else if(mBarChart == mBarChart30){
            mBarChart.getViewPortHandler().setMaximumScaleX(4.3f);
            mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
        }

        mBarChart.moveViewToX(-100);


        // 2.0 ---- mBarData = new BarData(xValues, barDataSet);
        BarData mBarData = new BarData(barDataSet);

        mBarChart.setData(mBarData);

        mBarChart.invalidate();
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
