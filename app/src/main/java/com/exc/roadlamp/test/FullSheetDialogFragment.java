package com.exc.roadlamp.test;

import android.app.Dialog;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.Nullable;

import com.exc.roadlamp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class FullSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_test, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(500);


//        view.findViewById(R.id.iv1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        });
//
//        view.findViewById(R.id.iv2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);//退出dialog
//            }
//        });



        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
//        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    /**
     * 点击布局里的ImageView，触发的点击事件
     * @param v
     */
    public void doclick(View v) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }
}
