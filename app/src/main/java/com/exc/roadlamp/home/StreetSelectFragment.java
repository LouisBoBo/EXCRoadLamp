package com.exc.roadlamp.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.ActivityAreaSelectBinding;
import com.exc.roadlamp.databinding.FragmentAreaBinding;
import com.exc.roadlamp.home.adapter.AreaSelectAdapter;
import com.exc.roadlamp.home.model.AreaModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;
@Page(name = "创建街道")
public class StreetSelectFragment extends MyBaseFragment implements View.OnClickListener {
    private FragmentAreaBinding binding;
    private AreaSelectAdapter adapter;
    private List<AreaModel> areaModelList;
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
                openNewPage(SiteSelectFragment.class);
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

        binding.imgSelect.setSelected(false);
        binding.editSearch.setHint("输入街道名称");

        binding.btnSearch.setOnClickListener(this::onClick);
        binding.cancle.setOnClickListener(this::onClick);
        binding.confirm.setOnClickListener(this::onClick);
        binding.imgSelect.setOnClickListener(this::onClick);

        initData();
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
            model.setContent("街道" + i);
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
