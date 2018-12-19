package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.impl;

import android.view.View;

/**
 * Created by bb on 2016/11/10.
 * RecyclerView/ListView/GridView 滑动加载更多时的回调接口
 */
public interface OnListLoadNextPageListener {

    /**
     * 开始加载下一页
     *
     * @param view 当前RecyclerView/ListView/GridView
     */
    public void onLoadMore(View view);

}
