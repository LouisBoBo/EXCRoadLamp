package com.exc.roadlamp.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.DialogBottomSheet2Binding;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class TestFragment extends MyBaseFragment {
    private DialogBottomSheet2Binding binding;


    @Override
    protected TitleBar initTitle() {
        return null;
    }


    @Override
    protected void initViews() {
        initListData();
    }

    private void initListData() {
        binding.recycler.setLayoutManager(new GridLayoutManager(mActivity, 3));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(new TestRecyclerViewAdapter(mActivity));


    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = DialogBottomSheet2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
