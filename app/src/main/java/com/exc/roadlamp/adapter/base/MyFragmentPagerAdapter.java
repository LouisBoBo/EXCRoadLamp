package com.exc.roadlamp.adapter.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> flist) {
        super(fm);
        fragmentList = flist;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
