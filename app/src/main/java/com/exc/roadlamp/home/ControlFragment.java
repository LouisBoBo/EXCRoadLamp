package com.exc.roadlamp.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.R;
import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentAreaBinding;
import com.exc.roadlamp.home.adapter.AreaSelectAdapter;
import com.exc.roadlamp.home.model.AreaModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

@Page(name = "创建3")
public class ControlFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentAreaBinding binding;
    private AreaSelectAdapter adapter;
    private List<AreaModel> areaModelList;

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected com.xuexiang.xui.widget.actionbar.TitleBar initTitle() {
        return null;
    }

    @Override
    protected void initViews() {
        GridLayoutManager device_manager = new GridLayoutManager(getActivity(),1);
        binding.myrecycleView.setLayoutManager(device_manager);
        adapter = new AreaSelectAdapter();
        binding.myrecycleView.setAdapter(adapter);

        adapter.setOnItemClick(new AreaSelectAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                for (AreaModel model : areaModelList) {
                    if(model.isIs_select()) return;
                }
                openNewPage(StreetSelectFragment.class);
            }
        });
        adapter.setOnImageClick(new AreaSelectAdapter.OnImageClick() {
            @Override
            public void imgclick(int index) {
                for(int i=0;i<areaModelList.size();i++){
                    if(index == i){
                        boolean select = areaModelList.get(index).isIs_select();
                        areaModelList.get(index).setIs_select(!select);
                    }
                }
                adapter.setmDatas(areaModelList);
            }
        });

        initData();
        binding.imgSelect.setOnClickListener(this::onClick);
    }


    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentAreaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void initData(){
        areaModelList = new ArrayList<>();
        for(int i=0;i<20;i++){
            AreaModel model = new AreaModel();
            model.setContent("集控" + i);
            model.setIs_select(false);
            areaModelList.add(model);
        }

        adapter.setmDatas(areaModelList);
    }
    @Override
    public void onClick(View v) {
        if(v == binding.imgSelect){
            binding.imgSelect.setSelected(!binding.imgSelect.isSelected());
            for (AreaModel model : areaModelList) {
                model.setIs_select(binding.imgSelect.isSelected()?true:false);
            }
            adapter.setmDatas(areaModelList);
        }
    }
}
