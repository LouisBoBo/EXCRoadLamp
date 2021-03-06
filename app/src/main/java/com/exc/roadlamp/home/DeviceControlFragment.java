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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.EnergyControlModel;
import com.exc.roadlamp.home.model.SolarModel;
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
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DeviceControlFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener,
        OnChartGestureListener {
    private TextView btn_day_energy;//?????????
    private TextView btn_month_energy;//?????????
    private TextView tv_day_energy_title;
    private TextView tv_day_energy_content;
    private TextView tv_month_energy_title;
    private TextView tv_month_energy_content;
    private ImageView img_day_energy;
    private ImageView img_month_energy;
    private View mainView;
    private LineChart mLineChar;
    private XAxis xAxis;
    private LineDataSet set1;
    private int day_count;

    private EnergyControlModel energyControlModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_device_control,container,false);

        mLineChar = mainView.findViewById(R.id.mLineChar);

        img_day_energy = mainView.findViewById(R.id.energy_day_add);
        img_month_energy = mainView.findViewById(R.id.energy_month_add);
        btn_day_energy = mainView.findViewById(R.id.btn_day_energy);
        btn_month_energy = mainView.findViewById(R.id.btn_monty_energy);
        tv_day_energy_title = mainView.findViewById(R.id.energy_day_text1);
        tv_day_energy_content = mainView.findViewById(R.id.energy_day_text2);
        tv_month_energy_title = mainView.findViewById(R.id.energy_month_text1);
        tv_month_energy_content = mainView.findViewById(R.id.energy_month_text2);

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
        btn_month_energy.setBackgroundResource(R.drawable.background_btn_right);
        btn_day_energy.setTextColor(Color.WHITE);
        btn_month_energy.setTextColor(Color.GRAY);
        btn_day_energy.setSelected(true);
        btn_month_energy.setSelected(false);

        btn_day_energy.setOnClickListener(this::onClick);
        btn_month_energy.setOnClickListener(this::onClick);
    }

    public void initData(){
        Parameter parameter1 = new Parameter();
        HttpRequest.GET(getActivity(), HttpApi.DLM_CONTROL_ENERGY_COUNT, parameter1, new BeanResponseListener<EnergyControlModel>() {
            @Override
            public void onResponse(EnergyControlModel model, Exception error) {
                if(model.getCode() == 200){
                    energyControlModel = model;
                    initLineChart(day_count);

                    tv_day_energy_title.setText("?????????:" + energyControlModel.getData().getDayEnergy() + "kWh");
                    tv_day_energy_content.setText("??????:" + energyControlModel.getData().getDayGrowthRate() + "%");
                    if(energyControlModel.getData().getDayGrowthRate() >0){
                        img_day_energy.setImageResource(R.mipmap.home_icon_up);
                    }else if(energyControlModel.getData().getDayGrowthRate() <0){
                        img_day_energy.setImageResource(R.mipmap.home_icon_down);
                    }


                    tv_month_energy_title.setText("?????????:" + energyControlModel.getData().getMonthEnergy() + "kWh");
                    tv_month_energy_content.setText("??????:" + energyControlModel.getData().getMonthGrowthRate() + "%");
                    if(energyControlModel.getData().getMonthGrowthRate() >0){
                        img_month_energy.setImageResource(R.mipmap.home_icon_up);
                    }else if(energyControlModel.getData().getMonthGrowthRate() <0){
                        img_month_energy.setImageResource(R.mipmap.home_icon_down);
                    }
                }

            }
        });
    }

    //?????????
    public void initLineChart(int day_count) {
        if(energyControlModel == null){
            return;
        }
        ArrayList<Entry> values = new ArrayList<>();
        for (int x = 1; x < day_count+1; x++) {
            float y = 0.0f;
            if(btn_day_energy.isSelected() && energyControlModel.getData().getEnergyCountDayList().size()>0){
                y = energyControlModel.getData().getEnergyCountDayList().get(x-1).getEnergy();
            }else if(btn_month_energy.isSelected() && energyControlModel.getData().getEnergyCountMonthList().size()>0){
                y = energyControlModel.getData().getEnergyCountMonthList().get(x-1).getEnergy();
            }
            values.add(new Entry(x, y));
        }


        //????????????????????????
        mLineChar.setOnChartGestureListener(this);
        //????????????????????????
        mLineChar.setOnChartValueSelectedListener(this);
        //????????????
        mLineChar.setDrawGridBackground(false);
        //??????????????????
        mLineChar.getDescription().setEnabled(false);
        //????????????????????????
        mLineChar.setTouchEnabled(true);
        //????????????
        mLineChar.setDragEnabled(true);
        //????????????
        mLineChar.setScaleEnabled(false);
        //????????????,???????????????x??????y???????????????
        mLineChar.setPinchZoom(true);
        mLineChar.setExtraLeftOffset(-15);

        xAxis = mLineChar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//??????x?????????
        xAxis.setDrawGridLines(false);//??????????????????
        xAxis.setTextSize(8.0f);//?????????????????????????????????
        xAxis.setAxisMinimum(1f);//X??????1??????
        xAxis.setAxisMaximum(day_count);
        xAxis.setGranularity(1f);//??????

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int)value;
                if(btn_day_energy.isSelected()){
                    if(i<=values.size()){
                        return YearMonthDayStrToDate( energyControlModel.getData().getEnergyCountDayList().get(i-1).getDateStr());
                    }else
                        return null;
                }else {
                    if(i<=values.size()){
                        return YearMonthStrToDate( energyControlModel.getData().getEnergyCountMonthList().get(i-1).getDateStr());
                    }else
                        return null;
                }

            }

        });

//        YAxis leftYAxis = mLineChar.getAxisLeft();
//        leftYAxis.setAxisMinimum(0f);
//        leftYAxis.setDrawGridLines(false);//????????????

        YAxis axisRight = mLineChar.getAxisRight();
        axisRight.setEnabled(false);//????????????  ????????????

        YAxis yAxis = mLineChar.getAxisLeft();
        // ?????????y???
        yAxis.setDrawAxisLine(false);
        // ??????y??????????????????
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // ??????y?????????????????????
        yAxis.setDrawGridLines(false);
        yAxis.setTextSize(12);
        // ??????y??????????????????
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);


        //????????????
        setLineData(values);

        //???????????????????????????????????????????????????
        mLineChar.setVisibleXRangeMaximum(11);
        //????????????
        mLineChar.animateX(2500);
        //??????
        mLineChar.invalidate();

        // ??????????????????
        Legend l = mLineChar.getLegend();
        // ???????????? ...
        l.setForm(Legend.LegendForm.NONE);
    }

    //???????????????
    private void setLineData(ArrayList<Entry> values) {

        mLineChar.moveViewToX(0);

        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();

        } else {
            // ?????????????????????,?????????????????????
            set1 = new LineDataSet(values, "");

            // ??????????????????
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
            //???????????????
            dataSets.add(set1);

            //????????????????????????????????????
            LineData data = new LineData(dataSets);
            //????????????
            mLineChar.setData(data);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == btn_day_energy){
            btn_day_energy.setBackgroundResource(R.drawable.background_btn_left_select);
            btn_month_energy.setBackgroundResource(R.drawable.background_btn_right);
            btn_day_energy.setTextColor(Color.WHITE);
            btn_month_energy.setTextColor(Color.GRAY);

            btn_day_energy.setSelected(true);
            btn_month_energy.setSelected(false);

            day_count = 7;

            mLineChar.removeAllViewsInLayout();
            mLineChar.removeAllViews();
            mLineChar.clear();
            mLineChar.getViewPortHandler().setMaximumScaleX(1.0f);
            mLineChar.getViewPortHandler().setMaximumScaleY(1.0f);
            mLineChar.moveViewToX(0);

            initLineChart(day_count);
        }else if(v == btn_month_energy){
            btn_month_energy.setBackgroundResource(R.drawable.background_btn_right_select);
            btn_day_energy.setBackgroundResource(R.drawable.background_btn_left);
            btn_day_energy.setTextColor(Color.GRAY);
            btn_month_energy.setTextColor(Color.WHITE);

            btn_day_energy.setSelected(false);
            btn_month_energy.setSelected(true);

            day_count = 6;

            mLineChar.removeAllViewsInLayout();
            mLineChar.removeAllViews();
            mLineChar.clear();
            mLineChar.getViewPortHandler().setMaximumScaleX(2.0f);
            mLineChar.getViewPortHandler().setMaximumScaleY(2.0f);
            mLineChar.moveViewToX(0);

            initLineChart(day_count);
        }
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
