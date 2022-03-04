package com.exc.roadlamp.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.exc.roadlamp.Constant;
import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentUserinfoBinding;
import com.exc.roadlamp.home.model.BaseModel;
import com.exc.roadlamp.http.BeanResponseListener;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.my.UserModel.UserModel;
import com.exc.roadlamp.utils.PreferencesUtil;
import com.exc.roadlamp.work.model.AlarmNewsModel;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Page (name = "个人信息")
public class UserinfoFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentUserinfoBinding binding;
    private String[] mGendernOption;
    private String[] mAreaOption;
    private String[] mRoleOption;
    private int constellationSelectOption = 0;
    private String info_type_name;

    @AutoWired(name = "user_info")
    UserModel.DataBean userModel;

    @Override
    protected void initArgs() {
        needArgs(this);
    }

    @Override
    protected void initViews() {
        binding.accountView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_1);
        binding.nameView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_1);
        binding.genderView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_2);
        binding.areaView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_3);
        binding.roleView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_1);
        binding.phoneView.imgTitle.setImageResource(R.mipmap.my_mesg_icon_4);

        binding.accountView.tvContent.setText("账号名称");
        binding.nameView.tvContent.setText("姓名");
        binding.genderView.tvContent.setText("性别");
        binding.areaView.tvContent.setText("绑定分区");
        binding.roleView.tvContent.setText("角色");
        binding.phoneView.tvContent.setText("手机号");

        if(userModel != null){
            binding.accountView.tvText.setText(userModel.getAccountName());
            binding.nameView.tvText.setText(userModel.getName());
            binding.genderView.tvText.setText(userModel.getGender()==1?"女":"男");
            binding.areaView.tvText.setText(userModel.getAreaName());
            binding.roleView.tvText.setText(userModel.getRoles().size()>0?userModel.getRoles().get(0).getName():"");
            binding.phoneView.tvText.setText(userModel.getPhone());
        }

        binding.accountView.itemView.setOnClickListener(this::onClick);
        binding.nameView.itemView.setOnClickListener(this::onClick);
        binding.genderView.itemView.setOnClickListener(this::onClick);
        binding.areaView.itemView.setOnClickListener(this::onClick);
        binding.roleView.itemView.setOnClickListener(this::onClick);
        binding.phoneView.itemView.setOnClickListener(this::onClick);
//        binding.btnSubmit.setOnClickListener(this::onClick);

        mGendernOption = ResUtils.getStringArray(R.array.constellation_entry);
        mAreaOption = ResUtils.getStringArray(R.array.constellation_area);
        mRoleOption = ResUtils.getStringArray(R.array.constellation_role);

        EventBus.getDefault().register(this);
    }

    //提交修改的用户信息
    public void submitData(){
        JSONObject json = new JSONObject();
        json.put("user",userModel);
        mMessageLoader.show();
        HttpRequest.JSONPOST(getActivity(), HttpApi.API_UA_USER, json.toJSONString(), new BeanResponseListener<BaseModel>() {
            @Override
            public void onResponse(BaseModel model, Exception error) {
                mMessageLoader.dismiss();
                if(model.getCode() == 200){
                    EventBus.getDefault().post(userModel);
                    popToBack();
                }
                ToastUtils.toast(model.getMessage());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String message){
        if(info_type_name.equals("账号名称")){
            binding.accountView.tvText.setText(message);
            userModel.setAccountName(message);
        }else if(info_type_name.equals("姓名")){
            binding.nameView.tvText.setText(message);
            userModel.setName(message);
        }else if(info_type_name.equals("手机号")){
            binding.phoneView.tvText.setText(message);
            userModel.setPhone(message);
        }
    }
    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentUserinfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
//        if(v == binding.accountView.itemView){
//            info_type_name = userModel.getAccountName().length()>0?userModel.getAccountName() : "账号名称";
//            openNewPage(ChangeUserInfoFragment.class,"info_type_name",info_type_name);
//        }else if(v == binding.nameView.itemView){
//            info_type_name = userModel.getName().length()>0?userModel.getName() : "姓名";
//            openNewPage(ChangeUserInfoFragment.class,"info_type_name",info_type_name);
//        }else if(v == binding.phoneView.itemView){
//            info_type_name = userModel.getPhone().length()>0?userModel.getPhone() : "手机号";
//            openNewPage(ChangeUserInfoFragment.class,"info_type_name",info_type_name);
//        }else if(v == binding.genderView.itemView){
//            showConstellationPickerView();
//        }else if(v == binding.areaView.itemView){
//            showAreaConstellationPickerView();
//        }else if(v == binding.roleView.itemView){
//            showRoleConstellationPickerView();
//        }
    }

    /**
     * 性别选择
     */
    private void showConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.genderView.tvText.setText(mGendernOption[options1]);
            userModel.setGender(mGendernOption[options1].equals("男")?0:1);
            constellationSelectOption = options1;
            return false;
        })
                .setTitleText("性别选择")
                .setSelectOptions(constellationSelectOption)
                .build();
        pvOptions.setPicker(mGendernOption);
        pvOptions.show();
    }

    private void showAreaConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.areaView.tvText.setText(mAreaOption[options1]);
            userModel.setAreaName(mAreaOption[options1]);
            constellationSelectOption = options1;
            return false;
        })
                .setTitleText("绑定分区")
                .setSelectOptions(constellationSelectOption)
                .build();
        pvOptions.setPicker(mAreaOption);
        pvOptions.show();
    }

    private void showRoleConstellationPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
            binding.roleView.tvText.setText(mRoleOption[options1]);
            userModel.setRoleName(mRoleOption[options1]);
            constellationSelectOption = options1;
            return false;
        })
                .setTitleText("角色选择")
                .setSelectOptions(constellationSelectOption)
                .build();
        pvOptions.setPicker(mRoleOption);
        pvOptions.show();
    }
}
