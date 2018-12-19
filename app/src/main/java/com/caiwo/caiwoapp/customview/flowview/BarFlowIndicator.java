package com.caiwo.caiwoapp.customview.flowview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.caiwo.caiwoapp.R;


public class BarFlowIndicator extends View implements IFlowIndicator {
    
    private static final int DEFAULT_BAR_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_BACK_COLOR = 0xFF666666;
    
    private Paint barPaint;
    private Paint backPaint;
    private float barHeight = 8.0f;
    private FlowView flowView;
    private int flowWidth = 0;
    private int curScroll = 0;

    public BarFlowIndicator(Context context) {
        super(context);
        init(DEFAULT_BAR_COLOR, DEFAULT_BACK_COLOR);
    }

    public BarFlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedStyles = context.obtainStyledAttributes(attrs,
                R.styleable.BarFlowIndicator);
        
        int barColor = typedStyles.getInt(R.styleable.BarFlowIndicator_barColor,
                DEFAULT_BAR_COLOR);
        int backColor = typedStyles.getInt(R.styleable.BarFlowIndicator_backgroundColor,
                DEFAULT_BACK_COLOR);
        
        init(barColor, backColor);
    }
    
    public void init(int barColor, int backColor) {
        barPaint = new Paint();
        barPaint.setStyle(Style.FILL);
        barPaint.setColor(barColor);
        
        backPaint = new Paint();
        backPaint.setStyle(Style.FILL);
        backPaint.setColor(backColor);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        float count = 2.0f;
        if(flowView != null){
            count = flowView.getViewsCount();
        }
        
        canvas.drawRect(0, 0, getWidth(), barHeight, backPaint);
        canvas.drawRect(curScroll/count, 0, 
                curScroll/count + getWidth()/count, barHeight, barPaint);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }
    
    /**
     * Determines the width of this view
     * 
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Calculate the width according the views count
        else {
            result = (int) (getPaddingLeft() + getPaddingRight() +
                    getWidth() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     * 
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Measure the height
        else {
            result = (int) (barHeight + getPaddingTop() + getPaddingBottom());
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    public void onSwitched(View view, int position) {
        //invalidate();
    }

    @Override
    public void onLastPageMove() {

    }

    @Override
    public void setFlowView(FlowView view) {
        flowView= view;
        flowWidth = flowView.getWidth();
        
        invalidate();
    }

    @Override
    public void onScrolled(int h, int v, int oldh, int oldv) {
        curScroll = h;
        invalidate();
    }

}

