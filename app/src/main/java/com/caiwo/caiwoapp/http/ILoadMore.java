package com.caiwo.caiwoapp.http;

public interface ILoadMore {

	/**
	 * 通知界面操作下拉刷新控件的
	 * 
	 * @param canRefresh<br/>
	 *            true 可以上拉加载更多 <br/>
	 *            false 禁止上拉加载更多
	 */
	public void notifyNoMore(boolean canRefresh);

//	void notifyNoMore();
}
