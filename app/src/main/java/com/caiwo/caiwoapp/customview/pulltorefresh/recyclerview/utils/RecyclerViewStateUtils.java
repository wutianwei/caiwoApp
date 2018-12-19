package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.utils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.loadingview.DefaultLoadingFooter;


/**
 * Created by bb on 2016/11/10.
 *
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 *
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class RecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, DefaultLoadingFooter.State state, View.OnClickListener errorListener) {

        if(instance==null || instance.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        DefaultLoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (DefaultLoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state);

            if (state == DefaultLoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }
            //recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        } else {
            footerView = new DefaultLoadingFooter(instance);
            footerView.setState(state);

            if (state == DefaultLoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    public static DefaultLoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                DefaultLoadingFooter footerView = (DefaultLoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return DefaultLoadingFooter.State.Normal;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     * @param state
     */
    public static void setFooterViewState(RecyclerView recyclerView, DefaultLoadingFooter.State state) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                DefaultLoadingFooter footerView = (DefaultLoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                footerView.setState(state);
            }
        }
    }
}
