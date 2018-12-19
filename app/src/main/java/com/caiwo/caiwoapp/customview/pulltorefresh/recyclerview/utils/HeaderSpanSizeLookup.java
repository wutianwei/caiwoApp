package com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.utils;

import android.support.v7.widget.GridLayoutManager;

import com.caiwo.caiwoapp.customview.pulltorefresh.recyclerview.HeaderAndFooterRecyclerViewAdapter;


/**
 * Created by bb on 2016/11/10.
 * <p/>
 * RecyclerView为GridLayoutManager时，设置了HeaderView，就会用到这个SpanSizeLookup
 */
public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(HeaderAndFooterRecyclerViewAdapter adapter, int spanSize) {
        this.adapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}