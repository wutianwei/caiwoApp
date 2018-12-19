package com.caiwo.caiwoapp.customview.pulltorefresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.caiwo.caiwoapp.R;


/**
 * 下拉刷新的布局
 * @author bb
 */
public class FooterLoadingLayout extends LoadingLayout {
	/**进度条*/
	private ProgressBar mProgressBar;
	private ImageView mNoData;

	/**
	 * 构造方法
	 * 
	 * @param context context
	 */
	public FooterLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 构造方法
	 * 
	 * @param context context
	 * @param attrs attrs
	 */
	public FooterLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化
	 * 
	 * @param context context
	 */
	private void init(Context context) {
		mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);
		mNoData  = (ImageView) findViewById(R.id.nodata_endImg);
		mProgressBar.setVisibility(View.GONE);
		setState(State.RESET);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(R.layout.view_pulltorefresh_load_footer, null);
		return container;
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
	}

	@Override
	public int getContentSize() {
		View view = findViewById(R.id.pull_to_load_footer_Rel);
		if (null != view) {
			return view.getHeight();
		}

		return (int) (getResources().getDisplayMetrics().density * 40);
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {

		mProgressBar.setVisibility(View.VISIBLE);
		mNoData.setVisibility(View.GONE);
		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		mNoData.setVisibility(View.GONE);
	}

	@Override
	protected void onPullToRefresh() {
		mNoData.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onReleaseToRefresh() {
		mNoData.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onRefreshing() {
		mProgressBar.setVisibility(View.VISIBLE);
		mNoData.setVisibility(View.GONE);
	}

	@Override
	protected void onNoMoreData() {
		mProgressBar.setVisibility(View.INVISIBLE);
		mNoData.setVisibility(View.VISIBLE);
	}
}
