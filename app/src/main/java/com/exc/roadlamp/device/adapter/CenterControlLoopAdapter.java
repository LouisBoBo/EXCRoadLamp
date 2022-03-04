
package com.exc.roadlamp.device.adapter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.exc.roadlamp.R;
import com.exc.roadlamp.bean.BaseBean;
import com.exc.roadlamp.device.bean.AllLoopsData;
import com.exc.roadlamp.device.customview.MyPSwitchView;
import com.exc.roadlamp.http.HttpApi;
import com.exc.roadlamp.http.HttpRequest;
import com.exc.roadlamp.utils.XToastUtils;
import com.exc.roadlamp.http.BeanResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;

import org.jetbrains.annotations.NotNull;

public class CenterControlLoopAdapter extends BaseQuickAdapter<AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean, BaseViewHolder> {

    private Activity mActivity;
    public IMessageLoader mMessageLoader;
    public boolean needSwitchListener = true;
    public boolean allSwitchListener = false;

    public CenterControlLoopAdapter(Activity activity, IMessageLoader iMessageLoader) {
        super(R.layout.item_center_control_loop_list);
        this.mActivity = activity;
        this.mMessageLoader = iMessageLoader;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllLoopsData.DataBean.ResearchLoopVOIPageBean.RecordsBean loop) {
        int position = baseViewHolder.getAdapterPosition();

        View bottom_view = baseViewHolder.findView(R.id.bottom_view);
        MyPSwitchView status_switch = baseViewHolder.findView(R.id.status_switch);
        status_switch.isclick = true;
        status_switch.mSlidable = true;

        if (position == getItemCount() - 1) {
            bottom_view.setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.tv_loop_name, loop.name);
        status_switch.setChecked(loop.isOpen == 1);


        status_switch.setOnSwitchCheckListener(isChecked -> {

            if(allSwitchListener){//如果是全开全关不走后面
                if(position == getItemCount()-1){
                    allSwitchListener = false;
                }
                return;
            }

            if (!needSwitchListener) {
                needSwitchListener = true;
                return;
            }

            mMessageLoader.show();
            Parameter parameter = new Parameter();
            int isOpen = isChecked ? 1 : 0;
            parameter.put("isOpen", isOpen);
            parameter.put("loopId", loop.id);

            HttpRequest.GET(mActivity, HttpApi.XIA_FA_LOOP, parameter, new BeanResponseListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean result, Exception error) {
                    mMessageLoader.dismiss();
                    if (error == null) {
                        XToastUtils.success(result.getMessage());
                        return;
                    }
                    XToastUtils.error(result.getMessage());
                    needSwitchListener = false;
                    status_switch.toggle();
                }
            });


        });

    }
}
