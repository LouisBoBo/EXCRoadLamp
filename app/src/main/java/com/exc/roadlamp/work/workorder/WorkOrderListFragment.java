package com.exc.roadlamp.work.workorder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentMyWorkOrderBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.work.OrderListBean;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

@Page(name = "我的工单")
public class WorkOrderListFragment extends MyBaseFragment {
    private FragmentMyWorkOrderBinding binding;
    private OrderListAdapter mAdapter;
    private int pageNum = 1;
    private ArrayList<OrderListBean.DataBean.RecordsBean> orderRecordList = new ArrayList<>();

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));

        titleBar.addAction(new TitleBar.ImageAction(R.drawable.order_add) {
            @Override
            public void performAction(View view) {
                openPage(CreateWorkOrderFragment.class);
            }
        });

        return titleBar;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEven(EventMessage eventMessage) {
        if (eventMessage.getEventEnum() == EventEnum.ADD_ORDER_SUCCESS) {
            pageNum = 1;
            initListData();
            return;
        }
        if (eventMessage.getEventEnum() == EventEnum.ORDER_OPERATOR_SUCCESS) {
            pageNum = 1;
            initListData();
            return;
        }

    }

    @Override
    protected void initViews() {
        initEventBus();
        WidgetUtils.initRecyclerView(binding.recyclerView, 0);
        binding.recyclerView.setAdapter(mAdapter = new OrderListAdapter());
        //下拉刷新
        ViewUtils.setViewsFont(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            initListData();
        });

        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            initListData();
        });

        mAdapter.setOnItemClickListener((itemView, position) -> {
            OrderListBean.DataBean.RecordsBean orderRecord = orderRecordList.get(position);
            openPage(OrderDetailFragment.class, "orderRecord", orderRecord);
//            OrderPic orderPic = new OrderPic();
//            orderPic.setFilename("1231321231");
//            openPage(OrderDetailFragment.class, "OrderPic", orderPic);

        });


        initListData();
    }

    private void initListData() {
        Parameter parameter = new Parameter();
        parameter.put("pageNum", pageNum);
        parameter.put("pageSize", 10);
        parameter.put("orderName", "");
        parameter.put("alarmTypeId", "");
        parameter.put("orderStatus", "");
        parameter.put("creator", 1);
        HttpRequest.GET(mActivity, HttpApi.ORDER_LIST, parameter, new BeanResponseListener<OrderListBean>() {
            @Override
            public void onResponse(OrderListBean orderListBean, Exception error) {
                if (null == error) {
                    if (orderListBean.getCode() != 200) {
                        ToastUtils.toast(orderListBean.getMessage());
                        showError();
                        return;
                    }
                    //测试
//                    if(pageNum == 2){
//                        orderListBean.getData().setRecords(orderRecordList);
//                    }

                    if (orderListBean.getData().getRecords() == null
                            || orderListBean.getData().getRecords().size() == 0) {
                        if (pageNum == 1) {
                            binding.llStateful.showEmpty();
                            binding.refreshLayout.setEnableLoadMore(false);
                            return;
                        }
                        binding.refreshLayout.finishLoadMore();
                        return;
                    }
                    if (pageNum == 1) {
                        orderRecordList = orderListBean.getData().getRecords();
                        mAdapter.refresh(orderListBean.getData().getRecords());
                        binding.refreshLayout.resetNoMoreData();
                        binding.llStateful.showContent();
                        binding.refreshLayout.setEnableLoadMore(true);
                        binding.refreshLayout.finishRefresh();
                        return;
                    }
                    orderRecordList.addAll(orderListBean.getData().getRecords());
                    mAdapter.loadMore(orderListBean.getData().getRecords());
                    binding.refreshLayout.finishLoadMore();
                } else {
                    showError();
                }

            }
        });

    }


    private void showOffline() {
        binding.llStateful.showOffline(v -> binding.refreshLayout.autoRefresh());
        binding.refreshLayout.setEnableLoadMore(false);
    }

    private void showError() {
        binding.refreshLayout.finishRefresh();
        binding.refreshLayout.finishLoadMore();
        binding.llStateful.showError(v -> {
                    pageNum = 1;
                    initListData();

                }
        );
        binding.refreshLayout.setEnableLoadMore(false);
    }

//    private enum Status {
//        SUCCESS,
//        EMPTY,
//        ERROR,
//        NO_NET,
//    }

//    private Status getRefreshStatus() {
//        int status = (int) (Math.random() * 10);
//        if (status % 2 == 0) {
//            return Status.SUCCESS;
//        } else if (status % 3 == 0) {
//            return Status.EMPTY;
//        } else if (status % 5 == 0) {
//            return Status.ERROR;
//        } else {
//            return Status.NO_NET;
//        }
//    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentMyWorkOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
