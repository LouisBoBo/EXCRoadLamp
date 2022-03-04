/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.exc.roadlamp.device.adapter;

import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;

import com.exc.roadlamp.R;
import com.exc.roadlamp.device.bean.LampPoleDevListBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;


public class LampPoleDetailDevNameListAdapter extends BaseRecyclerAdapter<LampPoleDevListBean> {


    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    public LampPoleDetailDevNameListAdapter(ArrayList<LampPoleDevListBean> data) {
        super(data);
    }




    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_adapter_lamp_pole_detail_dev_type;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, LampPoleDevListBean item) {
        holder.text(R.id.tv_tag, item.getName());

            holder.select(R.id.tv_tag, getSelectPosition() == position);
    }

    /**
     * 选择
     *
     * @param position
     * @return
     */
    public boolean select(int position) {
         return singleSelect(position);
    }




    /**
     * 单选
     *
     * @param position
     */
    public boolean singleSelect(int position) {
        if (position == getSelectPosition()) {
        } else {
            setSelectPosition(position);
            return true;
        }
        return false;
    }



    /**
     * @return 获取选中的内容
     */
    public String getSelectContent() {
        String value = getSelectItem().getName();
        if (value == null) {
            return "";
        }
        return value;
    }


    /**
     * @return 获取多选的内容
     */
    public List<String> getMultiContent() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(getItem(i).getName());
            }
        }
        return list;
    }
}
