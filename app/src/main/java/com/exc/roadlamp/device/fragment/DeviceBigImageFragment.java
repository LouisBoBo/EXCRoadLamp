package com.exc.roadlamp.device.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.ActivityBigImgBinding;
import com.exc.roadlamp.device.adapter.PhotoPagerAdapter;
import com.exc.roadlamp.device.customview.ViewPagerFixed;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

import java.util.ArrayList;

@Page(anim = CoreAnim.none)
public class DeviceBigImageFragment extends MyBaseFragment {
    private ActivityBigImgBinding binding;
    private ViewPagerFixed viewPager;
    private TextView tvNum;

    @AutoWired(name = "phonto_index")
    int phonto_index;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {

        viewPager = binding.viewpager;
        tvNum = binding.tvNum;


        ArrayList list_path = new ArrayList<>();
        //设置图片数据
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/2a919def19fc47e3aa0d75d8c227ab1b.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d027d1efc0564c44bb979ba0bd21f560.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/bbb930d66e5a48baa8d3c143544d7631.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/fb1721b8c9be4da9949fcdd26fc902a2.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/08b58dde9b284638b44e2d03c4cb9acf.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d3caeb6129ee43df87f5c1e1058d96fc.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/9fd01c4add07473db31ba850f20a7232.jpg");
        list_path.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");

        //接收图片数据及位置
        final ArrayList<String> imgData = list_path;
        int clickPosition = phonto_index;

        //添加适配器
        PhotoPagerAdapter viewPagerAdapter = new PhotoPagerAdapter(getChildFragmentManager(), imgData);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(clickPosition);//设置选中图片位置

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvNum.setText(String.valueOf(position + 1) + "/" + imgData.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = ActivityBigImgBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}


