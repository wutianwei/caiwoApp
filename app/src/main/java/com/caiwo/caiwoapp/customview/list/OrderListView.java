package com.caiwo.caiwoapp.customview.list;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 可延伸的ListView，高度和列表数量一致
 * 
 */
public class OrderListView extends ListView {

	public OrderListView(Context context) {
		super(context);
	}

	public OrderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OrderListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		}

}
