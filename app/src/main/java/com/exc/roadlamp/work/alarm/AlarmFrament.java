package com.exc.roadlamp.work.alarm;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.activity.MainActivity;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.customview.PopupView;
import com.exc.roadlamp.databinding.FragmentAlarmBinding;
import com.exc.roadlamp.home.model.AreaModel;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.home.model.NumberCountModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.work.adapter.AlarmAdapter;
import com.exc.roadlamp.work.model.AlarmNewsModel;
import com.kongzue.baseokhttp.util.Parameter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Page(name = "我的消息")
public class AlarmFrament extends MyBaseFragment implements View.OnClickListener {
    private FragmentAlarmBinding binding;
    private AlarmAdapter adapter;
    private List<AlarmNewsModel.DataBean.RecordsBean> areaModelList;
    private List<AlarmNewsModel.DataBean.RecordsBean> helpareaModelList;
    private List<AlarmNewsModel.DataBean.RecordsBean> faultareaModelList;

    private AlarmNewsModel.DataBean dataBean;
    private PopupView topPopWindow;
    private TitleBar titleBar;
    private SmartRefreshLayout refreshLayout;
    private int pageNum;
    private List<Integer> select_indexs;
    private Boolean is_allred;

    private  TitleBar.ImageAction brushimageAction;
    private  TitleBar.ImageAction shaixuanimageAction;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AlarmNewsModel.DataBean.RecordsBean model){
        for (Integer selectIndex : select_indexs) {
            areaModelList.get(selectIndex).setHaveRead(1);
        }
        adapter.setmDatas(areaModelList);
        refreshTitleBarAction();
    }

    @Override
    protected TitleBar initTitle() {
        titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        creatTitleBarAction();

        return titleBar;
    }

    //创建导航条
    public void creatTitleBarAction(){
        brushimageAction = new TitleBar.ImageAction(is_allred?R.mipmap.line_dark_brush:R.mipmap.line_brush) {
            @Override
            public void performAction(View view) {
                if(!is_allred) showCoverDialog();
            }
        };
        titleBar.addAction(brushimageAction);

        shaixuanimageAction = new TitleBar.ImageAction(R.mipmap.icon_shaixuan) {
            @Override
            public void performAction(View view) {
                showTopRightPopMenu();
            }
        };
        titleBar.addAction(shaixuanimageAction);
    }

    //刷新导航条
    public void refreshTitleBarAction(){
        for (AlarmNewsModel.DataBean.RecordsBean recordsBean : areaModelList) {
            if(recordsBean.getHaveRead() == 0){
                is_allred = false;
                break;
            }
        }
        if(!is_allred){
            titleBar.removeAction(brushimageAction);
            titleBar.removeAction(shaixuanimageAction);
            creatTitleBarAction();
        }
    }

    public void showCoverDialog(){
        DialogLoader.getInstance().showConfirmDialog(
                getContext(),
                getString(R.string.tip_bluetooth_permission),
                getString(R.string.tip_yes),
                (dialog, which) -> {
                    dialog.dismiss();
                    httpNewsRed();
                },
                getString(R.string.tip_no),
                (dialog, which) -> {
                    dialog.dismiss();
                }
        );
    }

    @Override
    protected void initViews() {
        is_allred = true;
        select_indexs = new ArrayList<>();
        EventBus.getDefault().register(this);
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(),1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new AlarmAdapter();
        binding.myrecycleView.setAdapter(adapter);

        adapter.setOnItemClick(new AlarmAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                select_indexs.add(index);
                AlarmNewsModel.DataBean.RecordsBean recordsBean = areaModelList.get(index);
                openNewPage(AlarmDetailFragment.class,"alarm_detail",recordsBean);
            }

        });

        pageNum = 1;
        initData();

        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            initData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            initData();
        });

    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentAlarmBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    //消息列表
    public void initData(){

        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", 30);
        HttpRequest.GET(getActivity(), HttpApi.API_WOA_ALARM_NEWS, parameter, new BeanResponseListener<AlarmNewsModel>() {
            @Override
            public void onResponse(AlarmNewsModel alarmNewsModel, Exception error) {
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
                if(alarmNewsModel.getCode() == 200){
                    if(pageNum == 1){
                        areaModelList = new ArrayList<>();
                        select_indexs = new ArrayList<>();
                    }
                    dataBean = alarmNewsModel.getData();
                    areaModelList.addAll(dataBean.getRecords());
                    adapter.setmDatas(areaModelList);

                    refreshTitleBarAction();
                }

            }
        });
    }

    //消息标为全读
    public void httpNewsRed(){
        Parameter parameter = new Parameter();

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.API_WOA_ALARM_NEWS_ALL, parameter, new BeanResponseListener<BaseModel>() {
            @Override
            public void onResponse(BaseModel model, Exception error) {
                mMessageLoader.dismiss();
                if(model.getCode() == 200){
                    handleNewsAll();
                    titleBar.removeAction(brushimageAction);
                    titleBar.removeAction(shaixuanimageAction);
                    is_allred = true;
                    creatTitleBarAction();
                    ToastUtils.toast(model.getMessage());
                }
            }
        });
    }

    public void handleHelpData(){
        helpareaModelList = new ArrayList<>();
        for (AlarmNewsModel.DataBean.RecordsBean recordsBean : areaModelList) {
            if(recordsBean.getServiceName().equals("一键求助")){
                helpareaModelList.add(recordsBean);
            }
        }
        adapter.setmDatas(helpareaModelList);
    }
    public void handleFaultData(){
        faultareaModelList = new ArrayList<>();
        for (AlarmNewsModel.DataBean.RecordsBean recordsBean : areaModelList) {
            if(recordsBean.getServiceName().equals("故障告警")){
                faultareaModelList.add(recordsBean);
            }
        }
        adapter.setmDatas(faultareaModelList);
    }

    public void handleNewsAll(){
        for (AlarmNewsModel.DataBean.RecordsBean recordsBean : areaModelList) {
            recordsBean.setHaveRead(1);
        }
        adapter.setmDatas(areaModelList);
    }

    /**
     * 显示右上角popup菜单
     */
    private void showTopRightPopMenu() {
        if (topPopWindow == null) {
            //(activity,onclicklistener,width,height)
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            topPopWindow = new PopupView(getActivity(), this, width, 300);
            //监听窗口的焦点事件，点击窗口外面则取消显示
            topPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        topPopWindow.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        topPopWindow.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        topPopWindow.showAsDropDown(titleBar, 0, 0);
        //如果窗口存在，则更新
        topPopWindow.update();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_popmenu_record:
                adapter.setmDatas(areaModelList);
                ToastUtils.toast("全部");
                break;
            case R.id.ll_popmenu_book:
                handleFaultData();
                ToastUtils.toast("故障报警");
                break;
            case R.id.ll_popmenu_search:
                handleHelpData();
                ToastUtils.toast("一键求助");
                break;
            default:
                break;
        }
        topPopWindow.dismiss();
    }

}
