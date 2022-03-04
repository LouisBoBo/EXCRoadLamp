package com.exc.roadlamp.device.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exc.roadlamp.base.MyBaseFragment;
import com.exc.roadlamp.databinding.FragmentDevMapSearchBinding;
import com.exc.roadlamp.device.bean.MapLampCommonDevList;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Page(name = "筛选")
public class LampPoleFiddlerFragment extends MyBaseFragment {
    private List<MapLampCommonDevList.DataBean> lampPolAreaList;
//    @AutoWired(name = "allLampPoleList")
//    ArrayList<MapLampCommonDevList.DataBean> allLampPoleList;

    @Override
    protected void initViews() {
        lampPolAreaList = lampPolAreaList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MapLampCommonDevList.DataBean :: getAreaId))), ArrayList::new));
        XToast.error(mActivity,lampPolAreaList.size()+"");



//
//       //取出所有的areaId
//        List<Integer> areaIdList = new ArrayList<>();
//        for (MapLampCommonDevList.DataBean bean : allLampPoleList) {
//            areaIdList.add(bean.areaId);
//        }
//        List<Integer> tempAreaIdList = new ArrayList<>();
//        for (int i = 0; i < areaIdList.size(); i++) {
//            if (!tempAreaIdList.contains(areaIdList.get(i))) {
//                tempAreaIdList.add(areaIdList.get(i));
//            }
//        }
//        areaIdList = tempAreaIdList;
//
//
//
//
//
//        for (int i = 0; i < allLampPoleList.size(); i++) {
//            for (int j = 0; j < areaIdList.size(); j++) {
//                if (areaIdList.get(j) == allLampPoleList.get(i).getAreaId()) {
//                    FiddlerAreaBean areaBean = new FiddlerAreaBean();
//                    areaBean.setAreaId(allLampPoleList.get(i).getAreaId());
//                    areaBean.setAreaName(allLampPoleList.get(i).getAreaName());
//
//                    continue;
//
//                }
//            }
//        }


    }

//    @Override
//    protected void initArgs() {
//        needArgs(this);
//    }

    @Override
    protected View setFragmentView(LayoutInflater inflater, ViewGroup container) {
        FragmentDevMapSearchBinding binding = FragmentDevMapSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
