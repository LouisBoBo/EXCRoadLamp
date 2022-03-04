package com.exc.roadlamp.work.workorder;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.bean.BaseBean;
import com.exc.roadlamp.databinding.FragmentCreateWorkOrderBinding;
import com.exc.roadlamp.eventbus.EventEnum;
import com.exc.roadlamp.eventbus.EventMessage;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.UpLoadUtil;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.work.workorder.addressselector.OnRegionDataSetListener;
import com.exc.roadlamp.work.workorder.addressselector.RegionBean;
import com.exc.roadlamp.work.workorder.addressselector.RegionLevel;
import com.exc.roadlamp.work.workorder.addressselector.RegionSelectDialog;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.utils.TextUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.SneakyThrows;

@Page(name = "创建工单")
public class CreateWorkOrderFragment extends MyBaseFragment implements View.OnClickListener {

    private FragmentCreateWorkOrderBinding binding;

    private RegionSelectDialog regionSelectDialog;
    private Activity mActivity;
    private List<RegionBean> lightPole1List;
    //故障类型列表
    private List<ErrorType.DataBean> errorTypeList;
    //路灯杆位置列表
    private List<WorkAreaListData.DataBean> areListData;

    private String lightPole1_name;
    private String lightPole2_name;
    private String lightPole3_name;
    private String lightPole4_name;
    private WorkAreaListData.DataBean areaSelect1Data;
    private WorkAreaListData.DataBean.ChildrenListBeanXX areaSelect2Data;
    private WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX areaSelect3Data;

    private ImgAdapter imgAdapter;
    private SelectPicDialog selectPicDialog;

    public static final int TAKE_CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int TAKE_GALLERY_PERMISSION_REQUEST_CODE = 101;


    public static final int TAKE_CAMERA_PIC_FILE_REQUEST_CODE = 200;
    public static final int TAKE_GALLERY_PIC_FILE_REQUEST_CODE = 201;

    //----提交的信息
    //提交的灯杆id
    private int submitLightPole1Id = -1;
    private int selectErrorTypeId = -1;
    private ArrayList<OrderPic> showPicList = new ArrayList<>();


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentCreateWorkOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    protected void initViews() {
        mActivity = getActivity();
        //初始化时，传入RegionLevel设置三级联动or四级联动）
        regionSelectDialog = new RegionSelectDialog(mActivity, RegionLevel.LEVEL_FOUR);
        binding.rlErrorType.setOnClickListener(this);
        binding.rlLocation.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        initImgList();

    }

    private void initImgList() {
        if (showPicList.size() < 6) { //最多有6张，不足6张时最后1张部位成添加按钮
            OrderPic tempOrderPic = new OrderPic();
            tempOrderPic.setVirtual(true);
            showPicList.add(tempOrderPic);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);
        binding.imgRecyclerview.setLayoutManager(layoutManager);
        binding.imgRecyclerview.setNestedScrollingEnabled(false);
        imgAdapter = new ImgAdapter();
        binding.imgRecyclerview.setAdapter(imgAdapter);
        imgAdapter.setList(showPicList);


        imgAdapter.setImgChildOnClickListener((clickViewId, position) -> {
            switch (clickViewId) {
                case R.id.iv:
                    if (showPicList.get(position).getFileType() == 0 ) {
                        return;
                    }
                    openPage(BrowsePicFragment.class, "OrderPic", showPicList.get(position));
                    break;
                case R.id.ll_add:

                    selectPicDialog = new SelectPicDialog(mActivity, btnId -> {
                        switch (btnId) {
                            case R.id.iv_camera:
                                takePicFromCamera();
                                break;
                            case R.id.iv_gallery:
                                takePicFromGallery();
                                break;

                        }
                    });
                    selectPicDialog.show();


                    break;
                case R.id.iv_close:

                    new MaterialDialog.Builder(getContext())
                            .content("您确定要删除此项吗？")
                            .positiveText(R.string.lab_yes)
                            .positiveColorRes(R.color.common_blue)
                            .negativeText(R.string.lab_no)
                            .negativeColorRes(R.color.common_blue)
                            .onPositive((dialog, which) -> {

                                showPicList.remove(position);
                                imgAdapter.notifyDataSetChanged();

                            })
                            .show();
                    break;
            }
        });




    }

    private void takePicFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
            this.requestPermissions(permissions, TAKE_GALLERY_PERMISSION_REQUEST_CODE);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ArrayList<String> mimeTypes = new ArrayList();
            mimeTypes.add("image/jpeg");
            mimeTypes.add("image/png");
            mimeTypes.add("image/jpg");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "选择图片"
                ), TAKE_GALLERY_PIC_FILE_REQUEST_CODE
        );


    }

    private void takePicFromCamera() {
        //相机的话 相机权限和存储权限都需要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                )
        ) {
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.CAMERA;
            permissions[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
            this.requestPermissions(permissions, TAKE_CAMERA_PERMISSION_REQUEST_CODE);
            return;
        }
        Intent intent = new Intent();
        intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_CAMERA_PIC_FILE_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        XLogger.debug(true);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_CAMERA_PIC_FILE_REQUEST_CODE:
                    Uri uriImageData;
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    if (null != data.getData()) {
                        uriImageData = data.getData();
                    } else {
                        uriImageData = Uri.parse(

                                MediaStore.Images.Media.insertImage(
                                        mActivity.getContentResolver(),
                                        bitmap,
                                        null,
                                        null
                                )
                        );
                    }
                    XLogger.e("cameraSelectUrl:" + uriImageData);
                    UpLoadPic(uriImageData);

                    break;
                case TAKE_GALLERY_PIC_FILE_REQUEST_CODE:
                    Uri gallerySelectUrl = data.getData();
                    XLogger.e("gallerySelectUrl:" + gallerySelectUrl);
                    UpLoadPic(gallerySelectUrl);
                    break;
                default:


                    break;
            }
        }
    }

    @SneakyThrows
    private void UpLoadPic(Uri gallerySelectUrl) {
        mMessageLoader.show();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = mActivity.managedQuery(gallerySelectUrl, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        File file = new File(img_path);
        XLogger.e("gallerySelectFile:" + file);
        UpLoadUtil.uploadImage(mActivity, file, new UpLoadUtil.UpLoadImgListener() {
            @Override
            public void upLoadSuccess(int imgId) {

                OrderPic orderPic = new OrderPic();
                orderPic.setFilename(file.getPath());
                orderPic.setVirtual(false);
                orderPic.setFileType(1);
                orderPic.setId(imgId);
                orderPic.setXC(true);
                showPicList.add(showPicList.size() - 1, orderPic);
                if (showPicList.size() > 6) {//最多6张
                    showPicList.remove(showPicList.size() - 1);
                }
                mActivity.runOnUiThread(() -> {
                    imgAdapter.setList(showPicList);
                    mMessageLoader.dismiss();
                    selectPicDialog.dismiss();

                });

            }

            @Override
            public void upLoadFail() {
                mMessageLoader.dismiss();
                XToastUtils.error("上传失败，请重试");
            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case TAKE_CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicFromCamera();
                    return;
                }
                //拒绝了
                ToastUtils.toast("请点权限，并允许授权相机和存储权限。");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        String pkg = "com.android.settings";
                        String cls = "com.android.settings.applications.InstalledAppDetails";
                        i.setComponent(new ComponentName(pkg, cls));
                        i.setData(Uri.parse("package:" + mActivity.getPackageName()));
                        startActivity(i);
                    }
                }, 1000);
                break;
            case TAKE_GALLERY_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicFromGallery();
                    return;
                }
                //拒绝了
                ToastUtils.toast("请点权限，并允许授权存储权限。");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        String pkg = "com.android.settings";
                        String cls = "com.android.settings.applications.InstalledAppDetails";
                        i.setComponent(new ComponentName(pkg, cls));
                        i.setData(Uri.parse("package:" + mActivity.getPackageName()));
                        startActivity(i);
                    }
                }, 1000);
                break;
        }
    }


    @Override
    protected void initListeners() {

        binding.etOrderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取输入框中的数据
                String edit = binding.etOrderName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvOrderNum.setText(20 - s.length() + "");
                //如果EditText中的数据不为空，且长度大于指定的最大长度
                if (!TextUtils.isEmpty(s) && s.length() > 20) {
                    //删除指定长度之后的数据
                    s.delete(20, binding.etOrderName.getSelectionEnd());
                    XToastUtils.error("超出长度");
                }
            }
        });

        binding.etLocationLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取输入框中的数据
                String edit = binding.etLocationLocation.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvLocationLocationNum.setText(20 - s.length() + "");
                //如果EditText中的数据不为空，且长度大于指定的最大长度
                if (!TextUtils.isEmpty(s) && s.length() > 20) {
                    //删除指定长度之后的数据
                    s.delete(20, binding.etLocationLocation.getSelectionEnd());
                    XToastUtils.error("超出长度");
                }
            }
        });

        binding.etWorkDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取输入框中的数据
                String edit = binding.etWorkDescribe.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvWorkDescribeNum.setText(30 - s.length() + "");
                //如果EditText中的数据不为空，且长度大于指定的最大长度
                if (!TextUtils.isEmpty(s) && s.length() > 30) {
                    //删除指定长度之后的数据
                    s.delete(30, binding.etWorkDescribe.getSelectionEnd());
                    XToastUtils.error("超出长度");
                }
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_error_type:
                if (null != errorTypeList) {
                    showErrorTypeListDialog(errorTypeList);
                    return;
                }
                HttpRequest.GET(mActivity, HttpApi.GET_LAMP_ERROR_TYPE_LIST, new Parameter(), new BeanResponseListener<ErrorType>() {
                    @Override
                    public void onResponse(ErrorType errorType, Exception error) {

                        if(error == null){
                            errorTypeList = errorType.getData();
                            showErrorTypeListDialog(errorTypeList);
                        }



                    }
                });
                break;
            case R.id.rl_location:
                if (null != areListData) {
                    showAreaListDialog(areListData);
                    return;
                }
                Parameter parameter = new Parameter();
                parameter.put("hierarchy", 4);
                HttpRequest.GET(mActivity, HttpApi.GET_LAMP_ALL_LOCATION, parameter, new BeanResponseListener<WorkAreaListData>() {
                    @Override
                    public void onResponse(WorkAreaListData workAreaListData, Exception error) {
                        if(error == null) {
                            areListData = workAreaListData.getData();
                            showAreaListDialog(areListData);
                        }


                    }
                });
                break;

            case R.id.btn_submit:

                String nameStr = binding.etOrderName.getText().toString();
                if (StringUtils.isEmpty(nameStr)) {
                    XToastUtils.error("请输入工单名称");
                    return;
                }

                String errorType = binding.tvErrorType.getText().toString();
                if (StringUtils.isEmpty(errorType)) {
                    XToastUtils.error("请选择故障类型");
                    return;
                }

                String tvLocationStr = binding.tvLocation.getText().toString();
                if (StringUtils.isEmpty(tvLocationStr)) {
                    XToastUtils.error("请选择故障灯杆");
                    return;
                }


                mMessageLoader.show();
                JsonMap jsonMap = new JsonMap();
                jsonMap.put("name", nameStr);
                jsonMap.put("addr", binding.etLocationLocation.getText().toString());
                jsonMap.put("alarmTypeId", selectErrorTypeId);
                jsonMap.put("description", binding.etWorkDescribe.getText().toString());
                jsonMap.put("lampPostId", submitLightPole1Id);
                ArrayList<Integer> imgIdList = new ArrayList<>();
                for (OrderPic orderPic : showPicList) {
                    if (orderPic.getFileType() == 1) {
                        imgIdList.add(orderPic.getId());
                    }
                }
                jsonMap.put("imgIdList", imgIdList);

                HttpRequest.JSONPOST(mActivity, HttpApi.ORDER_ADD, jsonMap, new BeanResponseListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean baseBean, Exception error) {
                        mMessageLoader.dismiss();
                        if(error == null) {
                            XToastUtils.success(baseBean.getMessage());
                            EventMessage eventMessage = new EventMessage();
                            eventMessage.setEventEnum(EventEnum.ADD_ORDER_SUCCESS);
                            EventBus.getDefault().post(eventMessage);
                            popToBack();
                        }
                    }
                });


                break;
        }
    }

    private void showErrorTypeListDialog(List<ErrorType.DataBean> errorTypeList) {
        if (null == errorTypeList || errorTypeList.size() == 0) {
            XToastUtils.error("数据异常或网络错误");
            return;
        }

        ArrayList<String> errorTypeNameList = new ArrayList<>();
        for (ErrorType.DataBean bean : errorTypeList) {
            errorTypeNameList.add(bean.getName());
        }

        selectErrorTypeId = errorTypeList.get(0).getId();

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (v, options1, options2, options3) -> {
//            ToastUtils.toast(errorTypeNameList.get(options1));
            selectErrorTypeId = errorTypeList.get(options1).getId();
            binding.tvErrorType.setText(errorTypeNameList.get(options1));

            return false;
        })
                .setTitleText("选择故障类型")
                .setTitleSize(17)
                .setCancelColor(0xFF1C7AFE)
                .setSubmitColor(0xFF1C7AFE)
                .setSelectOptions(0)
                .build();
        pvOptions.setPicker(errorTypeNameList);
        pvOptions.show();


    }


    private void showAreaListDialog(List<WorkAreaListData.DataBean> areListData) {

        /**
         * 一共四级，每一级都用id做区分
         */

        if (null == areListData || areListData.size() == 0) {
            XToastUtils.error("数据异常或无灯杆");
            return;
        }
        lightPole1List = new ArrayList<>();
        for (WorkAreaListData.DataBean dataBean : areListData) {
            RegionBean temp = new RegionBean(dataBean.getId(), dataBean.getName());
            lightPole1List.add(temp);
        }

        regionSelectDialog.setOnRegionDataSetListenr(new OnRegionDataSetListener() {

            //必须有返回值
            @Override
            public List<RegionBean> setProvinceList() {
                return lightPole1List;
            }

            //必须有返回值
            @Override
            public List<RegionBean> setOnProvinceSelected(RegionBean regionBean1) {
                lightPole1_name = regionBean1.getName();
                //查找选中的第1级下的第2级的数据
                ArrayList<RegionBean> lightPole2List = new ArrayList<>();
                areaSelect1Data = null;
                for (WorkAreaListData.DataBean dataBean : areListData) {
                    if (regionBean1.getId() == dataBean.getId()) {
                        areaSelect1Data = dataBean;
                        break;
                    }
                }
                if (null == areaSelect1Data || areaSelect1Data.getChildrenList().size() == 0) {
                    return lightPole2List;
                }
                for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : areaSelect1Data.getChildrenList()) {
                    lightPole2List.add(new RegionBean(childrenListBeanXX.getId(), childrenListBeanXX.getName()));
                }
                return lightPole2List;
            }

            @Override
            public List<RegionBean> setOnCitySelected(RegionBean regionBean2) {
                lightPole2_name = regionBean2.getName();
                ArrayList<RegionBean> lightPole3List = new ArrayList<>();
                //查找选中的第2级下的第3级的数据
                for (WorkAreaListData.DataBean.ChildrenListBeanXX childrenListBeanXX : areaSelect1Data.getChildrenList()) {
                    if (regionBean2.getId() == childrenListBeanXX.getId()) {
                        areaSelect2Data = childrenListBeanXX;
                    }
                }
                for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX childrenListBeanX : areaSelect2Data.getChildrenList()) {
                    lightPole3List.add(new RegionBean(childrenListBeanX.getId(), childrenListBeanX.getName()));
                }
                return lightPole3List;
            }

            @Override
            public List<RegionBean> setOnZoneSelected(RegionBean regionBean3) {
                lightPole3_name = regionBean3.getName();
                ArrayList<RegionBean> lightPole4List = new ArrayList<>();
                //查找选中的第3级下的第4级的数据
                for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX childrenListBeanX : areaSelect2Data.getChildrenList()) {
                    if (regionBean3.getId() == childrenListBeanX.getId()) {
                        areaSelect3Data = childrenListBeanX;
                    }
                }
                for (WorkAreaListData.DataBean.ChildrenListBeanXX.ChildrenListBeanX.ChildrenListBean childrenListBean : areaSelect3Data.getChildrenList()) {
                    lightPole4List.add(new RegionBean(childrenListBean.getId(), childrenListBean.getName()));
                }
                return lightPole4List;
            }

            @Override
            public void setOnAreaSelected(RegionBean regionBean4) {
                submitLightPole1Id = regionBean4.getId();
                lightPole4_name = regionBean4.getName();
                binding.tvLocation.setText(lightPole1_name + lightPole2_name + lightPole3_name + lightPole4_name);
            }

        });


        //显示
        regionSelectDialog.showDialog();
    }
}
