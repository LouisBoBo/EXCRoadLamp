package com.exc.roadlamp.my;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentChangeUserinfoBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.jar.Attributes;

@Page(name = "修改个人信息")
public class ChangeUserInfoFragment extends MyBaseFragment  implements View.OnClickListener {
    private FragmentChangeUserinfoBinding binding;

    @AutoWired(name = "info_type_name")
    String info_type_name;

    @Override
    protected TitleBar initTitle() {

        TitleBar titleBar = super.initTitle();
        titleBar.setTitle(info_type_name);
        return titleBar;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        binding.etInput.setHint("请输入" + info_type_name);
        binding.confirmBtn.setOnClickListener(this::onClick);

        binding.etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //允许最大输入长度
        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int inputcount = 11 - start - count;
                binding.tvNum.setText(String.valueOf(inputcount));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentChangeUserinfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(binding.etInput.getText().toString());
        popToBack();
    }
}
