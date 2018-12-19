package com.caiwo.caiwoapp.customview.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.caiwo.caiwoapp.R;


/**
 *  上拉加载FooterView
 * @author bb
 */
public class FooterView extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_NODATA = 3;
	private final int ROTATE_ANIM_DURATION = 180;

	private View mLayout;

	private View mProgressBar;

	private ImageView nodata_endImg;

	//    private ImageView mHintImage;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private int mState = STATE_NORMAL;

	public FooterView(Context context) {
		super(context);
		initView(context);
	}

	public FooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		mLayout = LayoutInflater.from(context).inflate(R.layout.view_pulltorefresh_load_footer, null);
		mLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		addView(mLayout);

		mProgressBar = mLayout.findViewById(R.id.pull_to_load_footer_progressbar);
		nodata_endImg = (ImageView) mLayout.findViewById(R.id.nodata_endImg);

		mRotateUpAnim = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(180);
		mRotateUpAnim.setFillAfter(true);

		mRotateDownAnim = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * Set footer view state
	 *
	 * @see #STATE_LOADING
	 * @see #STATE_NORMAL
	 * @see #STATE_READY
	 *
	 * @param state
	 */
	public void setState(int state) {
		
		if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
			nodata_endImg.setVisibility(View.GONE);
		}else {
			mProgressBar.setVisibility(View.GONE);
			nodata_endImg.setVisibility(View.GONE);
		}

		switch (state) {
		case STATE_NORMAL:
			mProgressBar.setVisibility(View.GONE);
			nodata_endImg.setVisibility(View.GONE);
			break;

		case STATE_READY:
			if (mState != STATE_READY) {
			}
			break;

		case STATE_LOADING:
			nodata_endImg.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			break;
		case STATE_NODATA:
			mProgressBar.setVisibility(View.GONE);
			nodata_endImg.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		


		mState = state;
	}

	/**
	 * Set footer view bottom margin.
	 *
	 * @param margin
	 */
	public void setBottomMargin(int margin) {
		if (margin < 0) return;
		LayoutParams lp = (LayoutParams) mLayout.getLayoutParams();
		lp.bottomMargin = margin;
		mLayout.setLayoutParams(lp);
	}

	/**
	 * Get footer view bottom margin.
	 *
	 * @return
	 */
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams) mLayout.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mProgressBar.setVisibility(View.GONE);
		nodata_endImg.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mProgressBar.setVisibility(View.VISIBLE);
		nodata_endImg.setVisibility(View.GONE);
	}

	/**
	 * nodata status
	 */
	public void nodata() {
		mProgressBar.setVisibility(View.GONE);
		nodata_endImg.setVisibility(View.VISIBLE);
	}


	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) mLayout.getLayoutParams();
		lp.height = 0;
		mLayout.setLayoutParams(lp);
	}




	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams) mLayout.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mLayout.setLayoutParams(lp);
	}

}
