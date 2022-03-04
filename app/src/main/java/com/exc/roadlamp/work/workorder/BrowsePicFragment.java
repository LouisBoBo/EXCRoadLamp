package com.exc.roadlamp.work.workorder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.ActivityBrowsPicBinding;
import com.exc.roadlamp.http.HttpApi;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.File;

@Page(name = "图片浏览")
public class BrowsePicFragment extends MyBaseFragment {
    private ActivityBrowsPicBinding binding;
    private Activity mActivity;
    @AutoWired(name = "OrderPic")
    OrderPic orderPic;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        mActivity = getActivity();
        if (orderPic.getFileType() == 1) {
            Glide.with(getContext()).load(new File(orderPic.getFilename())).into(binding.iv);
            return;
        }
        Glide.with(getContext()).load(HttpApi.SERVICES_ADDRESS + HttpApi.ORDER_PORT + orderPic.getFilename()).into(binding.iv);

    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = ActivityBrowsPicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
