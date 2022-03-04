package com.exc.roadlamp.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.exc.roadlamp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：带有分享功能的 RecyclerView的适配器
 *
 * @author luzhaowei
 * @email 2497727771@qq.com
 * @time 2017/8/17 16:38
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private List<AppBean> list;

    public TestRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        list = getShareAppList();
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Log.e("11111", list.get(i).toString());
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.item_recyclerview2_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.appTextView.setText(list.get(position).appName);
        holder.iconImageView.setImageDrawable(list.get(position).icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                AppBean appBean = list.get(position);
//                shareIntent.setComponent(new ComponentName(appBean.pkgName, appBean.appLauncherClassName));
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "分享内容");
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(shareIntent);

//                    Intent intent2 = new Intent(Intent.ACTION_SEND);
//                    Uri uri = Uri.fromFile(new File("xxx.jpg"));
//                    intent2.setComponent(new ComponentName(appInfo.pkgName, appInfo.appLauncherClassName));
//                    intent2.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent2.setType("image/*");
//                    startActivity(Intent.createChooser(intent2, "share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    private List<AppBean> getShareAppList() {
        List<AppBean> shareAppInfos = new ArrayList<AppBean>();

        for (int i = 0; i < 100; i++) {

            AppBean appBean = new AppBean();
            appBean.appName = "App"+i;
            appBean.icon = activity.getDrawable(R.drawable.app_logo);
            appBean.pkgName = "123";
            appBean.appLauncherClassName = "1233";
            shareAppInfos.add(appBean);
        }


        return shareAppInfos;
    }


    //////////////////////////////////////////////////
    //  RecyclerView 适配器的 ViewHolder
    /////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView appTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.app_icon_iv);
            appTextView = (TextView) itemView.findViewById(R.id.app_tv);
        }
    }

    /**
     * 封装的实体类
     */
    class AppBean {
        public Drawable icon;
        public String appName;
        public String pkgName;
        public String appLauncherClassName;
    }
}
