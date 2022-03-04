package com.exc.roadlamp.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.exc.roadlamp.R;

public class DownloadCircleDialog extends Dialog {
    public DownloadCircleDialog(Context context) {
        super(context, R.style.Theme_Ios_Dialog);
    }
    DownloadCircleView circleView;
    TextView tvMsg;
    ConstraintLayout layout_notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_circle_dialog_layout);
        this.setCancelable(false);//设置点击弹出框外部，无法取消对话框
        circleView = findViewById(R.id.circle_view);
        tvMsg = findViewById(R.id.tv_msg);
        layout_notice = findViewById(R.id.layout_notice);
    }
    public void setProgress(int progress) {
        circleView.setProgress(progress);
    }
    public void setMsg(String msg){
        tvMsg.setText(msg);
    }
    public void setHiden(){
        layout_notice.setVisibility(View.GONE);
        getWindow().clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
