package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.impl.PrvInterface;


/**
 * Created by bb on 2016/11/08.
 * 自定义上下拉refresherview
 */
public class PullToRefreshRecyclerView extends SwipeRefreshLayout implements PrvInterface {


    private RecyclerView mRecyclerView;

    private RelativeLayout mRootRelativeLayout;

    private View mEmptyView;

    private boolean mIsSwipeEnable = false;

    private AdapterObserver mAdapterObserver;


    public PullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this. initView();
    }


    /**
     * initView
     */
    @SuppressLint("ResourceType")
    private void initView(){
        mRootRelativeLayout = (RelativeLayout)LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.layout_pulltorefresh_recyclerview, null);

        this.addView(mRootRelativeLayout);
        this.setColorSchemeResources(R.color.main_color, R.color.main_color, R.color.main_color, R.color.main_color);
//this.set
//        this.setColorSchemeResources(R.drawable.loading_img, R.drawable.loading_img,
//                R.drawable.loading_img, R.drawable.loading_img);
        mRecyclerView = (RecyclerView)mRootRelativeLayout.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        if(!mIsSwipeEnable) {
            this.setEnabled(false);
        }

    }

    /**
     * 添加加载更多监听
     * @param mOnScrollListener
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener mOnScrollListener){
        if(null != mRecyclerView){
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        }
    }


    /**
     * 刷新完成
     */
    @Override
    public void setOnRefreshComplete() {
        this.setRefreshing(false);
    }

    /**
     * 设置空页面
     * @param emptyView
     */
    @Override
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if(mAdapterObserver == null){
            mAdapterObserver = new AdapterObserver();
        }
        if(adapter != null){
            adapter.registerAdapterDataObserver(mAdapterObserver);
            mAdapterObserver.onChanged();
        }
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if(mRecyclerView != null) {
            return mRecyclerView.getLayoutManager();
        }
        return null;
    }


    /**
     * 设置是否可以下拉
     * @param enable
     */
    @Override
    public void setSwipeEnable(boolean enable) {
        mIsSwipeEnable = enable;
        this.setEnabled(mIsSwipeEnable);
    }

    @Override
    public boolean isSwipeEnable() {
        return mIsSwipeEnable;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }


    /**
     * 设置格式 list/grid/瀑布流
     * @param layoutManager
     */
    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if(mRecyclerView != null){
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    /**
     * 释放
     */
    @Override
    public void release() {
        try{
            mRecyclerView = null;
            mRootRelativeLayout = null;
            mEmptyView = null;
            mAdapterObserver = null;
        }catch(Exception ex){}

    }


    private class AdapterObserver extends RecyclerView.AdapterDataObserver{
        @Override
        public void onChanged() {
            super.onChanged();
            //adapter has change
            if(mRecyclerView == null){
                //here must be wrong ,recyclerView is null????
                return;
            }

            RecyclerView.Adapter<?> adapter =  mRecyclerView.getAdapter();
            if(adapter != null && mEmptyView != null) {
                if(adapter.getItemCount() == 0) {
                    if(mIsSwipeEnable) {
                        PullToRefreshRecyclerView.this.setEnabled(false);
                    }
                    mEmptyView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                else {
                    if(mIsSwipeEnable) {
                        PullToRefreshRecyclerView.this.setEnabled(true);
                    }
                    mEmptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
