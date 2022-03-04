package com.exc.roadlamp.work.workorder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.exc.roadlamp.R;
public class SelectPicDialog extends Dialog implements View.OnClickListener {

    public OnSelectPicDialogBtnClickListener mOnSelectPicDialogBtnClickListener;
    public SelectPicDialog(@NonNull Context context, OnSelectPicDialogBtnClickListener onSelectPicDialogBtnClickListener) {
        super(context,R.style.dialog_bottom_full);
        this.mOnSelectPicDialogBtnClickListener = onSelectPicDialogBtnClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_pic);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);
        window.setWindowAnimations(R.style.dialogButtomInStyle);
        ImageView iv_camera = findViewById(R.id.iv_camera);
        ImageView iv_gallery = findViewById(R.id.iv_gallery);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        iv_camera.setOnClickListener(this);
        iv_gallery.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camera:
                mOnSelectPicDialogBtnClickListener.click(R.id.iv_camera);
                break;
            case R.id.iv_gallery:
                mOnSelectPicDialogBtnClickListener.click(R.id.iv_gallery);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }


    public  interface OnSelectPicDialogBtnClickListener{
        void click(int btnId);
    }
}
