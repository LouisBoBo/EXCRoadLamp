package com.exc.roadlamp.home;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.EnergyMonthModel;
import com.exc.roadlamp.home.model.NumberCountModel;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeviceSingleFragment extends Fragment implements OnChartValueSelectedListener,
        OnChartGestureListener, View.OnClickListener{

    private View mainView;
    private LineChart mLineChar;
    private XAxis xAxis;
    private LineDataSet set1;
    private TextView btn_day_energy;
    private TextView btn_month_eneygy;
    private int day_count;
    private TextView tv_energy_day1;
    private TextView tv_energy_day2;
    private TextView tv_energy_month1;
    private TextView tv_energy_month2;
    private ImageView ig_energy_day;
    private ImageView ig_energy_month;

    private NumberCountModel.DataBean dataBean;
    private EnergyMonthModel.DataBean energydataBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_device_single,container,false);
        btn_day_energy = mainView.findViewById(R.id.btn_day_energy);
        btn_month_eneygy = mainView.findViewById(R.id.btn_monty_energy);
        tv_energy_day1 = mainView.findViewById(R.id.energy_day_text1);
        tv_energy_day2 = mainView.findViewById(R.id.energy_day_text2);
        tv_energy_month1 = mainView.findViewById(R.id.energy_month_text1);
        tv_energy_month2 = mainView.findViewById(R.id.energy_month_text2);
        ig_energy_day = mainView.findViewById(R.id.energy_day_add);
        ig_energy_month = mainView.findViewById(R.id.energy_month_add);

        EventBus.getDefault().register(this);

        initview();
        initData();
        return mainView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.SELECT_TAB_CLICK) {
            initData();
            return;
        }
    }

    public void initview(){
        day_count = 7;

        btn_day_energy.setBackgroundResource(R.drawable.background_btn_left_select);
        btn_month_eneygy.setBackgroundResource(R.drawable.background_btn_right);
        btn_day_energy.setTextColor(Color.WHITE);
        btn_month_eneygy.setTextColor(Color.GRAY);
        btn_day_energy.setSelected(true);
        btn_month_eneygy.setSelected(false);

        btn_day_energy.setOnClickListener(this::onClick);
        btn_month_eneygy.setOnClickListener(this::onClick);
    }

    public void initData(){
        Parameter parameter1 = new Parameter();
        HttpRequest.GET(getActivity(), HttpApi.API_SL_LAMP_ENERGY_COUNT, parameter1, new BeanResponseListener<EnergyMonthModel>() {
            @Override
            public void onResponse(EnergyMonthModel energyMonthModel, Exception error) {
                if(energyMonthModel.getCode() == 200){
                    energydataBean = energyMonthModel.getData();
                    refreshUI();
                }

            }
        });
    }

    //折线图
    public void initLineChart() {

        ArrayList<Entry> values = new ArrayList<>();
        for (int x = 1; x < day_count +1; x++) {
            // 2.0 ----xValues.add(String.valueOf(i));
            int y = 0;
            if(btn_day_energy.isSelected() && energydataBean.getEnergyCountDayList().size()>0){
                y = energydataBean.getEnergyCountDayList().get(x-1).getEnergy();
            }else if(btn_month_eneygy.isSelected() && energydataBean.getEnergyCountMonthList().size()>0){
                y = energydataBean.getEnergyCountMonthList().get(x-1).getEnergy();
            }

            values.add(new Entry(x, y));
        }

        mLineChar = (LineChart) mainView.findViewById(R.id.mLineChar);
        //设置手势滑动事件
        mLineChar.setOnChartGestureListener(this);
        //设置数值选择监听
        mLineChar.setOnChartValueSelectedListener(this);
        //后台绘制
        mLineChar.setDrawGridBackground(false);
        //设置描述文本
        mLineChar.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineChar.setTouchEnabled(true);
        //设置缩放
        mLineChar.setDragEnabled(true);
        //设置推动
        mLineChar.setScaleEnabled(false);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChar.setPinchZoom(true);
        mLineChar.setExtraLeftOffset(-15);

        xAxis = mLineChar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴位置
        xAxis.setDrawGridLines(false);//不绘制网格线
        xAxis.setTextSize(8.0f);//设置轴标签的文字大小。
        xAxis.setAxisMinimum(1f);//X轴从1开始
        xAxis.setAxisMaximum(day_count);
        xAxis.setGranularity(1f);//间隔

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int)value;
                if(btn_day_energy.isSelected()){
                    if(i<=values.size()){
                        return YearMonthDayStrToDate( energydataBean.getEnergyCountDayList().get(i-1).getDateStr());
                    }else
                        return null;
                }else {
                    if(i<=values.size()){
                        return YearMonthStrToDate( energydataBean.getEnergyCountMonthList().get(i-1).getDateStr());
                    }else
                        return null;
                }
            }
        });

//        YAxis leftYAxis = mLineChar.getAxisLeft();
//        leftYAxis.setAxisMinimum(0f);
//        leftYAxis.setDrawGridLines(false);//隐藏纵轴

        YAxis axisRight = mLineChar.getAxisRight();
        axisRight.setEnabled(false);//隐藏右轴  默认显示

        YAxis yAxis = mLineChar.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        //设置数据
        setLineData(values);

        //设置当前窗口只展示固定的坐标点个数
        mLineChar.setVisibleXRangeMaximum(11);
        //默认动画
        mLineChar.animateX(2500);
        //刷新
        mLineChar.invalidate();

        // 得到这个文字
        Legend l = mLineChar.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.NONE);
    }

    //传递数据集
    private void setLineData(ArrayList<Entry> values) {

        mLineChar.moveViewToX(0);

        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();

        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "");

            // 在这里设置线
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setColor(0xA30000FF);
            set1.setCircleColor(0xA30000FF);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFillDrawable(getResources().getDrawable(R.drawable.line_chart_fill));
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 0f}, 0f));
            set1.setFormSize(15.f);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return v+"";
                }
            });

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            //设置数据
            mLineChar.setData(data);
        }

    }

    //刷新界面
    private void refreshUI(){

        tv_energy_day1.setText("日能耗:" + energydataBean.getDayEnergy() + "kWh");
        tv_energy_day2.setText("同比:" + energydataBean.getDayGrowthRate() + "%");
        if(energydataBean.getDayGrowthRate() >0){
            ig_energy_day.setImageResource(R.mipmap.home_icon_up);
        }else if(energydataBean.getDayGrowthRate() <0){
            ig_energy_day.setImageResource(R.mipmap.home_icon_down);
        }

        tv_energy_month1.setText("月能耗:" + energydataBean.getMonthEnergy() + "kWh");
        tv_energy_month2.setText("同比:" + energydataBean.getMonthGrowthRate() + "%");
        if(energydataBean.getMonthGrowthRate() >0){
            ig_energy_month.setImageResource(R.mipmap.home_icon_up);
        }else if(energydataBean.getMonthGrowthRate() <0){
            ig_energy_month.setImageResource(R.mipmap.home_icon_down);
        }
        initLineChart();
    }

    @Override
    public void onClick(View v) {
        if(v == btn_day_energy){
            btn_day_energy.setBackgroundResource(R.drawable.background_btn_left_select);
            btn_month_eneygy.setBackgroundResource(R.drawable.background_btn_right);
            btn_day_energy.setTextColor(Color.WHITE);
            btn_month_eneygy.setTextColor(Color.GRAY);

            btn_day_energy.setSelected(true);
            btn_month_eneygy.setSelected(false);

            day_count = 7;

            if(mLineChar != null){
                mLineChar.removeAllViewsInLayout();
                mLineChar.removeAllViews();
                mLineChar.clear();
                mLineChar.getViewPortHandler().setMaximumScaleX(3.0f);
                mLineChar.getViewPortHandler().setMaximumScaleY(1.0f);
                mLineChar.moveViewToX(0);
            }

            if(energydataBean != null){
                initLineChart();
            }

        }else if(v == btn_month_eneygy){
            btn_month_eneygy.setBackgroundResource(R.drawable.background_btn_right_select);
            btn_day_energy.setBackgroundResource(R.drawable.background_btn_left);
            btn_day_energy.setTextColor(Color.GRAY);
            btn_month_eneygy.setTextColor(Color.WHITE);

            btn_day_energy.setSelected(false);
            btn_month_eneygy.setSelected(true);

            day_count = 6;

            if(mLineChar != null){
                mLineChar.removeAllViewsInLayout();
                mLineChar.removeAllViews();
                mLineChar.clear();
                mLineChar.getViewPortHandler().setMaximumScaleX(1.0f);
                mLineChar.getViewPortHandler().setMaximumScaleY(1.0f);
                mLineChar.moveViewToX(0);
            }

            if(energydataBean != null){
                initLineChart();
            }
        }
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
