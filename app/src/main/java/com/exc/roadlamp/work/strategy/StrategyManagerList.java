package com.exc.roadlamp.work.strategy;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.customview.PinchImageView;
import com.exc.roadlamp.databinding.StrategyManagerBinding;
import com.exc.roadlamp.work.adapter.TabFragmentPagerAdapter;
import com.exc.roadlamp.work.lightcontrol.LightControlManagerList;
import com.exc.roadlamp.work.model.StrategyTypeModel;
import com.xuexiang.xpage.annotation.Page;

import java.util.ArrayList;
import java.util.List;

@Page(name = "策略管理")
public class StrategyManagerList extends MyBaseFragment implements View.OnClickListener{
    private StrategyManagerBinding binding;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;
    private int pubpositon =0;
    // 滚动条初始偏移量
    private int offset = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;
    @Override
    public void onClick(View v) {
        if(v == binding.btnStrong){
            binding.myViewPager.setCurrentItem(0);
            binding.btnStrong.setTextColor(getResources().getColor(R.color.common_blue));
            binding.btnWeak.setTextColor(getResources().getColor(R.color.region_black));
        }else if(v == binding.btnWeak){
            binding.myViewPager.setCurrentItem(1);
            binding.btnStrong.setTextColor(getResources().getColor(R.color.region_black));
            binding.btnWeak.setTextColor(getResources().getColor(R.color.common_blue));
        }else if(v == binding.addDevice){
            StrategyTypeModel model = new StrategyTypeModel();
            model.strategy_type = pubpositon;
            openNewPage(StrategyManagerAdd.class,"strategy_type",model);
        }
    }

    @Override
    protected void initViews() {
        list = new ArrayList<>();
        list.add(new StrategyManagerFragment());
        list.add(new SceneManagerFragment());
        adapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(),list);
        binding.myViewPager.setAdapter(adapter);
        binding.myViewPager.setCurrentItem(0);
        binding.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int position) {
                pubpositon = position;
                Animation animation = null;
                if(position ==0){
                    binding.btnStrong.setTextColor(getResources().getColor(R.color.common_blue));
                    binding.btnWeak.setTextColor(getResources().getColor(R.color.region_black));
                    animation = new TranslateAnimation(one, 0, 0, 0);

                }else if(position ==1){
                    binding.btnStrong.setTextColor(getResources().getColor(R.color.region_black));
                    binding.btnWeak.setTextColor(getResources().getColor(R.color.common_blue));
                    animation = new TranslateAnimation(offset, one, 0, 0);
                }

                // 将此属性设置为true可以使得图片停在动画结束时的位置
                animation.setFillAfter(true);
                //动画持续时间，单位为毫秒
                animation.setDuration(200);
                //滚动条开始动画
                binding.scrollBar.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.btnStrong.setTextColor(getResources().getColor(R.color.common_blue));
        binding.btnWeak.setTextColor(getResources().getColor(R.color.region_black));

        binding.btnStrong.setOnClickListener(this::onClick);
        binding.btnWeak.setOnClickListener(this::onClick);
        binding.addDevice.setOnClickListener(this::onClick);


        // 获取滚动条的宽度
        bmpW = binding.scrollBar.getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        android.graphics.Matrix matrix = PinchImageView.MathUtils.matrixTake();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        binding.scrollBar.setImageMatrix(matrix);
    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = StrategyManagerBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
