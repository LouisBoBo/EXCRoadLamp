
package com.exc.roadlamp.device.adapter;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.DevMapSearchResultListBean;
import com.xuexiang.xutil.common.StringUtils;

import org.jetbrains.annotations.NotNull;
public class DevMapSearchResultListAdapter extends BaseQuickAdapter<DevMapSearchResultListBean, BaseViewHolder> {
    public DevMapSearchResultListAdapter() {
        super(R.layout.item_dev_map_search_result_list);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DevMapSearchResultListBean devBean) {
        int position= baseViewHolder.getAdapterPosition();
        String name = devBean.getDevName();
        baseViewHolder.setText(R.id.tv_name,name.trim());
        View line_top = baseViewHolder.findView(R.id.line_top);
        if(position == 0){
            line_top.setVisibility(View.VISIBLE);
        }else{
            line_top.setVisibility(View.GONE);
        }
        String detailNames = devBean.getDetailNames();

        if(!StringUtils.isEmpty(devBean.getDetailNames())){
            baseViewHolder.setText(R.id.tv_names, "（" +  detailNames.trim() +"）");
            baseViewHolder.findView(R.id.tv_names).setVisibility(View.VISIBLE);
        }else{
            baseViewHolder.findView(R.id.tv_names).setVisibility(View.GONE);
        }
    }

}
