package com.exc.roadlamp.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.exc.roadlamp.R;
import com.exc.roadlamp.databinding.FragmentDeviceOnlineBinding;
import com.exc.roadlamp.databinding.FragmentDeviceOrderBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.home.model.OrderAnalysisModel;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.http.BeanResponseListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class DeviceOrderFragment extends Fragment implements OnChartValueSelectedListener,
        OnChartGestureListener {

    private FragmentDeviceOrderBinding binding;
    private PieChart mPieChart;
    private OrderAnalysisModel.DataBean dataBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeviceOrderBinding.inflate(inflater,container,false);
        mPieChart = (PieChart) binding.mPieChart;
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

    }

    public void initData(){

        //工单概况
        Parameter parameter = new Parameter();
        parameter.put("frame", 1);
        HttpRequest.GET(getActivity(), HttpApi.API_WOA_ORDER_ANALYSIS, parameter, new BeanResponseListener<OrderAnalysisModel>() {
            @Override
            public void onResponse(OrderAnalysisModel orderAnalysisModel, Exception error) {
                if(orderAnalysisModel.getCode() == 200){
                    dataBean = orderAnalysisModel.getData();
                    refrestUI();
                }

            }
        });


    }

    //刷新界面
    public void refrestUI(){
        binding.tvOrderNum.setText(String.valueOf(dataBean.getAllCount()));
        binding.tvBeingNum.setText("正在处理:" + String.valueOf(dataBean.getBeingCount()));
        binding.tvNoNum.setText("未处理:" +String.valueOf(dataBean.getNoCount()));
        binding.tvOvertimeNum.setText("已超时:" +String.valueOf(dataBean.getOvertimeCount()));
        initPieChart(dataBean.getBeingCount(),dataBean.getNoCount(),dataBean.getOvertimeCount());
    }
    //饼状图
    public void initPieChart(int on_handle , int no_handle,int time_handle) {


        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(0, -3, 8, 0);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText("");
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.YELLOW);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(false);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        mPieChart.setDrawHoleEnabled(false);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(on_handle, ""));
        entries.add(new PieEntry(no_handle, ""));
        entries.add(new PieEntry(time_handle, ""));

        //设置数据
        setPieData(entries);
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        // 不显示图例
        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);

        mPieChart.setDrawEntryLabels(true);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLUE);
        mPieChart.setEntryLabelTextSize(0f);
    }

    private void setPieData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);

        int[] VORDIPLOM_COLORS = {
//                Color.rgb(0, 255, 127), Color.rgb(255, 130, 71),Color.rgb(255, 130, 171)
                Color.BLUE,Color.YELLOW,Color.RED
        };

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
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
