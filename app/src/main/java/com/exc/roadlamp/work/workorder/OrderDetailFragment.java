package com.exc.roadlamp.work.workorder;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentOrderDetailBinding;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.OrderListBean;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;

@Page(name = "订单详情")
public class OrderDetailFragment extends MyBaseFragment {
    private FragmentOrderDetailBinding binding;
    private Activity mActivity;
    @AutoWired(name = "orderRecord")
    OrderListBean.DataBean.RecordsBean recordsBean;
    private OrderDetailBean mOrderDetailBean;

    private ImgAdapter imgAdapter;
    private ArrayList<OrderPic> showPicList = new ArrayList<>();


    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor(getString(R.string.common_blue_str)));
        String titleStr = "工单详情";
        switch (recordsBean.getStatusId()) {
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
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        mActivity = getActivity();
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);
        binding.imgRecyclerview.setLayoutManager(layoutManager);
        binding.imgRecyclerview.setNestedScrollingEnabled(false);
        imgAdapter = new ImgAdapter();
        binding.imgRecyclerview.setAdapter(imgAdapter);
        initViewData();
    }

    private void initViewData() {
        mMessageLoader.show();
        Parameter parameter = new Parameter();
        HttpRequest.GET(mActivity, HttpApi.ORDER_DETAIL + recordsBean.getOrderId(), parameter, new BeanResponseListener<OrderDetailBean>() {
            @Override
            public void onResponse(OrderDetailBean orderDetailBean, Exception error) {
                mMessageLoader.dismiss();
                if (null == error) {
                    if (orderDetailBean.getCode() != 200) {
                        ToastUtils.toast(orderDetailBean.getMessage());
                        return;
                    }
                    mOrderDetailBean = orderDetailBean;
                    binding.tvOrderName.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getOrderName());
                    binding.tvOrderType.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getAlarmTypeName());
                    binding.tvAddress.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getOrderAddr());
                    binding.tvCreatePerson.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getCreatorName());
                    binding.tvCreateTime.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getCreateTime());
                    binding.tvDescribe.setText(mOrderDetailBean.getData().getWoaRespOrderVO().getDescription());
                    showPicList.clear();
                    if (null == mOrderDetailBean.getData().getWoaRespOrderPicVOList() || mOrderDetailBean.getData().getWoaRespOrderPicVOList().size() == 0) {
                        binding.imgRecyclerview.setVisibility(View.GONE);
                        initBottomBtn();
                        return;
                    }
                    for (OrderDetailBean.DataBean.WoaRespOrderPicVOListBean picVOListBean : mOrderDetailBean.getData().getWoaRespOrderPicVOList()) {
                        OrderPic orderPic = new OrderPic();
                        orderPic.setXC(false);
                        orderPic.setVirtual(false);
                        orderPic.setFilename(picVOListBean.getOrderPicName());
                        showPicList.add(orderPic);
                    }
                    imgAdapter.setList(showPicList);
                    imgAdapter.setOnItemClickListener((adapter, view, position) -> {
                        openPage(BrowsePicFragment.class, "OrderPic", showPicList.get(position));

                    });
                    initBottomBtn();


                }



            }
        });


    }

    /**
     * 底部阿按钮
     */
    private void initBottomBtn() {
        switch (mOrderDetailBean.getData().getWoaRespOrderVO().getStatusId()) {
            case 1: //待初审
                binding.bottomBtn.setText("初审");
                binding.bottomBtn.setOnClickListener(v -> {
                    openPage(OrderAuditFragment.class, "orderDetail", mOrderDetailBean);
                });
                binding.bottomBtn.setVisibility(View.VISIBLE);
                break;
//            case 5://待审核
//                titleStr = "待审核";
//                break;
//            case 7://已超时
//                titleStr = "已超时";
//                break;
//            case 2://被驳回
//                titleStr = "被驳回";
//                break;
//            case 3://待处理
//                titleStr = "待处理";
//                break;
//            case 4://跟进中
//                titleStr = "跟进中";
//                break;
//            case 6://审核通过
//                titleStr = "审核通过";
//                break;
        }
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
