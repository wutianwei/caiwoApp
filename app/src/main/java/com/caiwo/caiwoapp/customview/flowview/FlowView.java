package com.caiwo.caiwoapp.customview.flowview;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Scroller;


import com.caiwo.caiwoapp.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class FlowView extends AdapterView<Adapter> {

    private static final int TOUCH_STATE_REST = 0;
    private static final int INVALID_SCREEN = -1;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private static final int SNAP_VELOCITY = 1000;

    private LinkedList<View> loadedViews;
    private int curBuffIndex;
    private int curAdapterIndex;
    private int sideBuff = 2;
    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private int touchState = TOUCH_STATE_REST;
    private float lastMotionX;
    private int touchSlop;
    private int maxVelocity;
    private int curScreen;
    private int nextScreen = INVALID_SCREEN;
    private boolean firstLayout = true;
    private ViewSwitchListener switchListener;
    private Adapter flowAdapter;
    private int lastScrollDir;
    private AdapterDataSetObserver dataSetObserver;
    private IFlowIndicator indicator;
    private int lastOrientation = -1;

    private OnGlobalLayoutListener orientationChangeListener =
            new OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver()
                            .removeGlobalOnLayoutListener(orientationChangeListener);

                    setSelection(curAdapterIndex);
                }
            };

    public FlowView(Context context) {
        super(context);
        flowInit();
    }

    public FlowView(Context context, int sideBuff){
        super(context);
        this.sideBuff = sideBuff;
        flowInit();
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
                R.styleable.FlowView);
        sideBuff = styledAttrs.getInt(R.styleable.FlowView_bufferSize, sideBuff);
        flowInit();
    }

    private void flowInit(){
        loadedViews = new LinkedList<View>();
        scroller = new Scroller(getContext());
        final ViewConfiguration config = ViewConfiguration.get(getContext());
        touchSlop = config.getScaledTouchSlop();
        maxVelocity = config.getScaledMaximumFlingVelocity();
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        if(newConfig.orientation != lastOrientation){
            lastOrientation = newConfig.orientation;
            getViewTreeObserver().addOnGlobalLayoutListener(orientationChangeListener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(widthMode != MeasureSpec.EXACTLY && !isInEditMode()){
            throw new IllegalStateException(
                    "ViewFlow can only be used in EXACTLY mode.");
        }

        //final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode != MeasureSpec.EXACTLY && !isInEditMode()){
            throw new IllegalStateException(
                    "ViewFlow can only be used in EXACTLY mode.");
        }

        final int count = getChildCount();
        for(int i = 0; i < count; i ++){
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        if(firstLayout){
            scroller.startScroll(0, 0, curScreen * width, 0, 0);
            firstLayout = false;
        }
    }

    @Override
    public Adapter getAdapter() {
        return flowAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        setAdapter(adapter, 0);
    }

    public void setAdapter(Adapter adapter, int initPosition) {
        if(flowAdapter != null){
            flowAdapter.unregisterDataSetObserver(dataSetObserver);
        }

        flowAdapter = adapter;
        if(flowAdapter != null){
            dataSetObserver = new AdapterDataSetObserver();
            flowAdapter.registerDataSetObserver(dataSetObserver);
        }

        if(flowAdapter != null && flowAdapter.getCount() > 0){
            setSelection(initPosition);
        }
    }

    @Override
    public View getSelectedView() {
        return curBuffIndex < loadedViews.size()
                ? loadedViews.get(curBuffIndex) : null;
    }

    @Override
    public int getSelectedItemPosition() {
        return curAdapterIndex;
    }

    public void setFlowIndicator(IFlowIndicator indicator) {
        this.indicator = indicator;
        indicator.setFlowView(this);
    }

    @Override
    public void setSelection(int position) {
        nextScreen = INVALID_SCREEN;
        scroller.forceFinished(true);

        if(flowAdapter != null){
            position = Math.max(0, position);
            position = Math.min(position, flowAdapter.getCount() - 1);

            ArrayList<View> recycleViews = new ArrayList<View>();
            View recycleView;
            while(!loadedViews.isEmpty()){
                recycleViews.add(recycleView = loadedViews.remove());
                detachViewFromParent(recycleView);
            }

            View curView = makeAndAddView(position, true,
                    recycleViews.isEmpty() ? null : recycleViews.remove(0));
            loadedViews.addLast(curView);

            for(int offset = 1; sideBuff - offset  >= 0; offset ++){
                int leftIndex = position - offset;
                int rightIndex = position + offset;
                if(leftIndex >= 0){
                    loadedViews.addFirst(makeAndAddView(leftIndex, false,
                            recycleViews.isEmpty() ? null : recycleViews.remove(0)));
                }

                if(rightIndex < flowAdapter.getCount()){
                    loadedViews.addLast(makeAndAddView(rightIndex, true,
                            recycleViews.isEmpty() ? null : recycleViews.remove(0)));
                }
            }

            curBuffIndex = loadedViews.indexOf(curView);
            curAdapterIndex = position;

            for(View v : recycleViews){
                removeDetachedView(v, false);
            }
            requestLayout();

            setVisibleView(curBuffIndex, false);
            if(indicator != null){
                indicator.onSwitched(loadedViews.get(curBuffIndex),
                        curAdapterIndex);
            }
            if(switchListener != null){
                switchListener.onSwitched(loadedViews.get(curBuffIndex),
                        curAdapterIndex);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(getChildCount() == 0){
            return false;
        }

        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        final int action = event.getAction();
        final float x = event.getX();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }

                lastMotionX = x;

                touchState = scroller.isFinished() ?
                        TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_MOVE:
                final int absDeltaX = (int) Math.abs(x - lastMotionX);
                boolean isXMoved = absDeltaX > touchSlop;
                if(isXMoved){
                    touchState = TOUCH_STATE_SCROLLING;
                }

                if(touchState == TOUCH_STATE_SCROLLING){
                    //阻止父节点派发Touch事件
                    requestDisallowInterceptTouchEvent(true);

                    final int deltaX = (int) (lastMotionX - x);
                    lastMotionX = x;

                    final int scrollX = getScrollX();
                    if(deltaX < 0){
                        if(scrollX > 0){
                            scrollBy(Math.max(-scrollX, deltaX), 0);
                        }
                    }else if(deltaX > 0){
                        final int availableToScroll = getChildAt(getChildCount() - 1).getRight()
                                - scrollX - getWidth();

                        if (availableToScroll > 0) {
                            scrollBy(Math.min(availableToScroll, deltaX), 0);
                        }
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchState == TOUCH_STATE_SCROLLING) {
                    final VelocityTracker velTracker = velocityTracker;
                    velTracker.computeCurrentVelocity(1000, maxVelocity);
                    int velocityX = (int) velTracker.getXVelocity();

                    if (velocityX > SNAP_VELOCITY && curScreen > 0) {
                        // Fling hard enough to move left
                        snapToScreen(curScreen - 1);
                    } else if (velocityX < -SNAP_VELOCITY
                            && curScreen < getChildCount() - 1) {
                        // Fling hard enough to move right
                        snapToScreen(curScreen + 1);
                    } else {
                        snapToDestination();
                    }

                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        velocityTracker = null;
                    }
                }

                touchState = TOUCH_STATE_REST;
                break;

            case MotionEvent.ACTION_CANCEL:
                snapToDestination();
                touchState = TOUCH_STATE_REST;
        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        int childLeft = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final int childWidth = child.getMeasuredWidth();
                child.layout(childLeft, 0, childLeft + childWidth,
                        child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void snapToDestination() {
        final int screenWidth = getWidth();
        final int whichScreen = (getScrollX() + (screenWidth / 2))
                / screenWidth;

        snapToScreen(whichScreen);
    }

    private void snapToScreen(int whichScreen) {
        lastScrollDir = whichScreen - curScreen;
        if (!scroller.isFinished()){
            return;
        }

        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));

        nextScreen = whichScreen;

        final int newX = whichScreen * getWidth();
        final int delta = newX - getScrollX();
        scroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(getChildCount() == 0){
            return false;
        }

        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        final int action = event.getAction();
        final float x = event.getX();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }

                lastMotionX = x;

                touchState = scroller.isFinished() ?
                        TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_MOVE:
                final int absDeltaX = (int) Math.abs(x - lastMotionX);
                boolean isXMoved = absDeltaX > touchSlop;
                if(isXMoved){
                    touchState = TOUCH_STATE_SCROLLING;
                }

                if(touchState == TOUCH_STATE_SCROLLING){
                    //阻止父节点派发Touch事件
                    requestDisallowInterceptTouchEvent(true);

                    final int deltaX = (int) (lastMotionX - x);
                    lastMotionX = x;

                    final int scrollX = getScrollX();
                    if(deltaX < 0){
                        if(scrollX > 0){
                            scrollBy(Math.max(-scrollX, deltaX), 0);
                        }
                    }else if(deltaX > 0){
                        final int availableToScroll = getChildAt(getChildCount() - 1).getRight()
                                - scrollX - getWidth();

                        if (availableToScroll > 0) {
                            scrollBy(Math.min(availableToScroll, deltaX), 0);
                        }
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchState == TOUCH_STATE_SCROLLING) {
                    final VelocityTracker velTracker = velocityTracker;
                    velTracker.computeCurrentVelocity(1000, maxVelocity);
                    int velocityX = (int) velTracker.getXVelocity();

                    if (velocityX > SNAP_VELOCITY && curScreen > 0) {
                        // Fling hard enough to move left
                        snapToScreen(curScreen - 1);
                    } else if (velocityX < -SNAP_VELOCITY
                            && curScreen < getChildCount() - 1) {
                        // Fling hard enough to move right
                        snapToScreen(curScreen + 1);
                    } else {
                        snapToDestination();
                    }

                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        velocityTracker = null;
                    }
                }

                touchState = TOUCH_STATE_REST;
                break;

            case MotionEvent.ACTION_CANCEL:
                touchState = TOUCH_STATE_REST;
        }

        return false;
    }

    @Override
    protected void onScrollChanged(int h, int v, int oldh, int oldv) {
        super.onScrollChanged(h, v, oldh, oldv);
        if (indicator != null) {
            /*
             * The actual horizontal scroll origin does typically not match the
             * perceived one. Therefore, we need to calculate the perceived
             * horizontal scroll origin here, since we use a view buffer.
             */
            int hPerceived = h + (curAdapterIndex - curBuffIndex) * getWidth();
            indicator.onScrolled(hPerceived, v, oldh, oldv);
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        } else if (nextScreen != INVALID_SCREEN) {
            curScreen = Math.max(0, Math.min(nextScreen, getChildCount() - 1));
            nextScreen = INVALID_SCREEN;
            postViewSwitched(lastScrollDir);
        }
    }

    private void postViewSwitched(int direction) {
        if (direction == 0){
            if(curScreen == getChildCount() - 1) {
                switchListener.onLastPageMove();
            }
            return;
        }

        if (direction > 0) { // to the right
            curAdapterIndex ++;
            curBuffIndex ++;

            View recycleView = null;

            // Remove view outside buffer range
            if (curAdapterIndex > sideBuff) {
                recycleView = loadedViews.removeFirst();
                detachViewFromParent(recycleView);
                // removeView(recycleView);
                curBuffIndex --;
            }


            // Add new view to buffer
            int newBufferIndex = curAdapterIndex + sideBuff;
            if (newBufferIndex < flowAdapter.getCount())
                loadedViews.addLast(makeAndAddView(newBufferIndex, true,
                        recycleView));

        } else { // to the left
            curAdapterIndex --;
            curBuffIndex --;
            View recycleView = null;


            // Remove view outside buffer range
            if (flowAdapter.getCount() - 1 - curAdapterIndex > sideBuff) {
                recycleView = loadedViews.removeLast();
                detachViewFromParent(recycleView);
            }

            // Add new view to buffer
            int newBufferIndex = curAdapterIndex - sideBuff;
            if (newBufferIndex > -1) {
                loadedViews.addFirst(makeAndAddView(newBufferIndex, false,
                        recycleView));
                curBuffIndex ++;
            }

        }

        requestLayout();
        setVisibleView(curBuffIndex, true);
        if (indicator != null && loadedViews.size() > curBuffIndex) {
            indicator.onSwitched(loadedViews.get(curBuffIndex),
                    curAdapterIndex);
        }
        if (switchListener != null) {
            switchListener.onSwitched(loadedViews.get(curBuffIndex),
                    curAdapterIndex);
        }
        logBuff();
    }

    public int getViewsCount() {
        return flowAdapter.getCount();
    }

    private void setVisibleView(int buffIndex, boolean inUIThread) {
        curScreen = Math.max(0, Math.min(buffIndex, getChildCount() - 1));
        int deltaX = curScreen * getWidth() - scroller.getCurrX();
        scroller.startScroll(scroller.getCurrX(), scroller.getCurrY(),
                deltaX, 0, 0);

        if(deltaX == 0){
            onScrollChanged(scroller.getCurrX() + deltaX, scroller.getCurrY(),
                    scroller.getCurrX() + deltaX, scroller.getCurrY());
        }

        if(inUIThread){
            invalidate();
        }else{
            postInvalidate();
        }
    }

    public void setOnViewSwitchListener(ViewSwitchListener l) {
        switchListener = l;
    }

    public class AdapterDataSetObserver extends DataSetObserver{

        @Override
        public void onChanged() {
            View v = getChildAt(curBuffIndex);
            if(v != null){
                for(int i = 0, j = flowAdapter.getCount(); i < j; i ++){
                    if(v.equals(flowAdapter.getItem(i))){
                        curAdapterIndex = i;
                        break;
                    }
                }
            }

            resetFocus();
        }

        @Override
        public void onInvalidated() {

        }
    }


    public static interface ViewSwitchListener{

        public void onSwitched(View view, int position);
        public void onLastPageMove();
    }



    private void resetFocus() {
        logBuff();

        loadedViews.clear();
        removeAllViewsInLayout();

        for(int i = Math.max(0, curAdapterIndex - sideBuff);
            i < Math.min(flowAdapter.getCount(), curAdapterIndex + sideBuff + 1);
            i ++){
            loadedViews.addLast(makeAndAddView(i, true, null));

            if(i == curAdapterIndex){
                curBuffIndex = loadedViews.size() - 1;
            }
        }

        logBuff();
        requestLayout();
    }

    private View makeAndAddView(int position, boolean addToEnd, View convertView) {
        View view = flowAdapter.getView(position, convertView, this);

        return setupChild(view, addToEnd, convertView != null);
    }

    private View setupChild(View child, boolean addToEnd, boolean recycle) {
        LayoutParams p = child.getLayoutParams();
        if(p == null){
            p = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.FILL_PARENT, 0);
        }

        if(recycle){
            attachViewToParent(child, addToEnd ? -1 : 0, p);
        }else{
            addViewInLayout(child, addToEnd ? -1 : 0, p, true);
        }

        return child;
    }

    private void logBuff() {
        Log.d("flow view", "Size of loadedViews: " + loadedViews.size() +
                "X: " + scroller.getCurrX() + ", Y: " + scroller.getCurrY());

        Log.d("flow view", "IndexInAdapter: " + curAdapterIndex
                + ", IndexInBuffer: " + curBuffIndex + "," + getHeight() + "," + getWidth());
    }

}

