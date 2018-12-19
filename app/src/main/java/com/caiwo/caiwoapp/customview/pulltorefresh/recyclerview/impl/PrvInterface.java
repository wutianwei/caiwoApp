package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by bb on 2016/11/08.
 * recyclerview回调接口
 */
public interface PrvInterface{
    void setOnRefreshComplete();
    void setEmptyView(View emptyView);
    void setAdapter(RecyclerView.Adapter adapter);
    RecyclerView.LayoutManager getLayoutManager();
    void setSwipeEnable(boolean enable);//设置是否可以下拉
    boolean isSwipeEnable();//返回当前组件是否可以下拉
    RecyclerView getRecyclerView();
    void setLayoutManager(RecyclerView.LayoutManager layoutManager);
    void release();
}
