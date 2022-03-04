package com.exc.roadlamp.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xuexiang.xpage.AutoPageConfiguration;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.logger.PageLog;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xpage.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置
 *
 * @author xuexiang
 * @since 2018/5/24 下午3:40
 */
public class MyPageConfig {

    /**
     * 页面配置接口
     */
    private PageConfiguration mPageConfiguration;

    /**
     * 页面信息
     */
    private List<PageInfo> mPages = new ArrayList<>();

    /**
     * XPageFragment的容器Activity类名
     */
    private String mContainActivityClassName = MyXPageActivity.class.getCanonicalName();

    private static MyPageConfig gInstance;

    public static MyPageConfig getInstance() {
        if (gInstance == null) {
            synchronized (MyPageConfig.class) {
                if (gInstance == null) {
                    gInstance = new MyPageConfig();
                }
            }
        }
        return gInstance;
    }

    public MyPageConfig setPageConfiguration(PageConfiguration pageConfiguration) {
        mPageConfiguration = pageConfiguration;
        return this;
    }

    /**
     * 初始化页面配置
     *
     * @param application 应用上下文
     */
    public void init(Application application) {
        initPages(application);
    }

    /**
     * 设置调试模式
     *
     * @param tag 标记
     */
    public MyPageConfig debug(String tag) {
        PageLog.debug(tag);
        return this;
    }

    /**
     * 初始化页面信息
     *
     * @param context 上下文
     */
    private void initPages(Context context) {
        if (mPageConfiguration == null) {
            // 没有设置的话，就使用自动注册配置
            mPageConfiguration = new AutoPageConfiguration();
        }
        registerPageInfos(mPageConfiguration.registerPages(context));
        MyCoreConfig.init(context, getPages());
    }

    /**
     * 设置XPageFragment的容器Activity类名
     *
     * @param containActivityClazz 容器Activity类
     */
    public MyPageConfig setContainActivityClazz(@NonNull Class<? extends MyXPageActivity> containActivityClazz) {
        mContainActivityClassName = containActivityClazz.getCanonicalName();
        return this;
    }

    public static String getContainActivityClassName() {
        return getInstance().mContainActivityClassName;
    }

    /**
     * 注册页面信息
     *
     * @param clazz
     * @return
     */
    public MyPageConfig registerPageInfo(Class<?> clazz) {
        mPages.add(getPageInfo(clazz));
        return this;
    }

    /**
     * 注册多个页面信息
     *
     * @param clazz
     * @return
     */
    public MyPageConfig registerPageInfos(Class... clazz) {
        for (int i = 0; i < clazz.length; i++) {
            registerPageInfo(clazz[i]);
        }
        return this;
    }

    /**
     * 注册多个页面信息
     *
     * @param pageInfos
     * @return
     */
    private MyPageConfig registerPageInfos(List<PageInfo> pageInfos) {
        if (pageInfos != null && pageInfos.size() > 0) {
            mPages.clear();
            mPages.addAll(pageInfos);
        }
        return this;
    }

    public List<PageInfo> getPages() {
        return mPages;
    }

    public static PageInfo getPageInfo(Class<?> clazz) {
        Page page = getPage(clazz);
        PageInfo pageInfo = new PageInfo(TextUtils.isEmpty(page.name()) ? clazz.getSimpleName() : page.name(), clazz);
        if (!TextUtils.isEmpty(page.params()[0])) {
            pageInfo.setParams(page.params());
        }
        pageInfo.setAnim(page.anim());
        return pageInfo;
    }

    /**
     * 获取页面信息
     *
     * @param clazz
     * @return
     */
    public static Page getPage(Class<?> clazz) {
        return Utils.checkNotNull(clazz.getAnnotation(Page.class), "Page == null，请检测页面是否漏加 @Page 进行修饰！");
    }
}
