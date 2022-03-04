package com.exc.roadlamp.my;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentChangePwdBinding;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.home.model.NumberCountModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.tip.ToastUtils;

@Page(name = "修改密码")
public class ChangePwdFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentChangePwdBinding binding;
    @Override
    protected void initViews() {
        binding.etNewpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见
        binding.etOldpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见
        binding.etCfmpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见

        binding.imgOldScan.setSelected(false);
        binding.imgNewScan.setSelected(false);

        binding.submitPwd.setOnClickListener(this::onClick);
        binding.imgOldScan.setOnClickListener(this::onClick);
        binding.imgNewScan.setOnClickListener(this::onClick);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentChangePwdBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public void initData(){

        JsonMap json = new JsonMap();
        json.put("password",binding.etNewpwd.getText());
        HttpRequest.JSONPUT(getActivity(), HttpApi.API_UA_USER_MODIFY_PASSWORD, json, new BeanResponseListener<BaseModel>() {

            @Override
            public void onResponse(BaseModel baseModel, Exception error) {
                if(baseModel.getCode() == 200){
                    popToBack();
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        if(v == binding.submitPwd){
            if(binding.etOldpwd.getText().length() ==0 || binding.etNewpwd.getText().length() ==0 || binding.etCfmpwd.getText().length() ==0){
                XToastUtils.success("请完善您的输入");
                return;
            }else if(binding.etOldpwd.getText().length() < 10 || binding.etNewpwd.getText().length() < 10 || binding.etCfmpwd.getText().length() < 10) {
                XToastUtils.success("密码长度不对");
                return;
            }else if(binding.etOldpwd.getText().length() > 16 || binding.etNewpwd.getText().length() > 16 || binding.etCfmpwd.getText().length() > 16) {
                XToastUtils.success("密码长度不对");
                return;
            }else if(!binding.etNewpwd.getText().toString().equals(binding.etCfmpwd.getText().toString())){
                XToastUtils.success("两次输入密码不一致");
                return;
            }

            initData();
        }else if(v == binding.imgOldScan){
            binding.imgOldScan.setSelected(!binding.imgOldScan.isSelected());
            binding.imgOldScan.setImageResource(binding.imgOldScan.isSelected()? R.mipmap.icon_see:R.mipmap.icon_nosee);
            binding.etOldpwd.setTransformationMethod(!binding.imgOldScan.isSelected()?PasswordTransformationMethod.getInstance():HideReturnsTransformationMethod.getInstance()); //密码可见/不可见
        }else if(v == binding.imgNewScan){
            binding.imgNewScan.setSelected(!binding.imgNewScan.isSelected());
            binding.imgNewScan.setImageResource(binding.imgNewScan.isSelected()? R.mipmap.icon_see:R.mipmap.icon_nosee);
            binding.etNewpwd.setTransformationMethod(!binding.imgNewScan.isSelected()?PasswordTransformationMethod.getInstance():HideReturnsTransformationMethod.getInstance()); //密码可见/不可见
        }

    }
}
