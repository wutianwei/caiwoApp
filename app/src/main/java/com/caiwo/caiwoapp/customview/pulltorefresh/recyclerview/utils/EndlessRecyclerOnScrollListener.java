package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.impl.OnListLoadNextPageListener;


/**
 * Created by bb on 2016/11/10.
 * <p/>
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * Y值的变化
     */
    private ScrollY mScrollY;


    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    public EndlessRecyclerOnScrollListener(ScrollY mScrollY) {
        this.mScrollY = mScrollY;
    }
    public EndlessRecyclerOnScrollListener() {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(null != mScrollY){//外部需要Y
            mScrollY.onScrollY(dy);
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                //Glide.with(LiveApplication.getContext()).resumeRequests();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:// 滚动时
                //Glide.with(LiveApplication.getContext()).pauseRequests();
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:// 滚动到某个位置的动画过程,但没有被触摸滚动.调用 scrollToPosition(int)
                //Glide.with(LiveApplication.getContext()).pauseRequests();
                break;
            default:
                break;
        }

        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE
                && (lastVisibleItemPosition) >= totalItemCount - 1)) {
            onLoadMore(recyclerView);
        }
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    @Override
    public void onLoadMore(final View view) {
    }


    public static enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }
}
