package com.caiwo.caiwoapp.customview.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 自定义Scollview
 * 可回调滑动距离：SVScrollInterface
 */
public class SumScollview extends ScrollView{

	// 滑动距离及坐标  
	private float xDistance, yDistance, xLast, yLast;  

	SVScrollInterface si;

	public SumScollview(Context context, AttributeSet attrs) {
		super(context, attrs);  
	}  

	public SumScollview(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}

	@Override  
	public boolean onInterceptTouchEvent(MotionEvent ev) {  
		switch (ev.getAction()) {  
		case MotionEvent.ACTION_DOWN:  
			xDistance = yDistance = 0f;  
			xLast = ev.getX();  
			yLast = ev.getY();  
			break;  
		case MotionEvent.ACTION_MOVE:  
			final float curX = ev.getX();  
			final float curY = ev.getY();  

			xDistance += Math.abs(curX - xLast);  
			yDistance += Math.abs(curY - yLast);  
			xLast = curX;  
			yLast = curY;  

			if(xDistance > yDistance){  
				return false;  
			}    
		}  

		return super.onInterceptTouchEvent(ev);  
	}

	public void fling(int velocityY) {
		super.fling(velocityY * 2);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(si != null){
			si.onSChanged(l, t, oldl, oldt);
		}
	}

	public void setOnScroolChangeListener(SVScrollInterface scrollInterface) {

		this.si = scrollInterface;	

	}

	/**	
	 * 定义滑动接口
	 * 
	 * @param
	 */

	public interface SVScrollInterface {

		public void onSChanged(int l, int t, int oldl, int oldt);

	}


}
