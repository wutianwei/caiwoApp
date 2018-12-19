package com.caiwo.caiwoapp.customview.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.app.MyApplication;


public abstract class AbsDialogCreator {

	private AbsDiadlog mDialog;
	private Context mContext;
	private LayoutInflater inflater;

	/**
	 * 可取消？
	 */
	private boolean cancable = true;

	/**
	 * 外部可点击？
	 */
	private boolean touchable = true;

	/**
	 * 获取根布局ID
	 *
	 * @return
	 */
	public abstract int getRootViewId();

	/**
	 * 根视图
	 */
	private View rootView;

	/**
	 * 绑定布局
	 *
	 * @param rootView
	 */
	public abstract void findView(View rootView);

	/**
	 * 消除外部持有
	 */
	public void onDestroy(){
		try {
			mContext = null;
			inflater = null;
			if(null != mDialog){
				if(mDialog.isShowing()){
					mDialog.dismiss();
					mDialog = null;
				}
			}
		}catch (Exception ex){}
	}

	public AbsDialogCreator(Context context, boolean needinputMethod) {
		this(context, R.style.dialog_transtpotation, needinputMethod);
	}

	public AbsDialogCreator(Context context, int theme, boolean needinputMethod) {
		this.mContext = context;
		this.inflater = LayoutInflater.from(context.getApplicationContext());
		this.rootView = inflater.inflate(getRootViewId(), null);
		this.mDialog = new AbsDiadlog(mContext, theme, needinputMethod);
	}



	/**
	 * 获取对话框
	 *
	 */
	public AbsDiadlog getInstance(){
		return mDialog;
	};


	/**
	 * 设置对话框是否监听返回键
	 *
	 * @param
	 */
	public void setOnKeyListener() {
		this.mDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
				if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
					return true;
				}else{
					return false;
				}
			}
		});
	}



	/**
	 * 设置对话框是否能被取消
	 *
	 * @param cancable
	 */
	public void setCancable(boolean cancable) {
		this.cancable = cancable;
	}

	/**
	 * 设置对话框是否能在外部点击取消
	 *
	 * @param touchable
	 */
	public void setTouchable(boolean touchable) {
		this.touchable = touchable;
	}

	/**
	 * 获取当前对话框的上下文
	 *
	 * @return
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * 获取视图过滤器
	 *
	 * @return
	 */
	public LayoutInflater getInflater() {
		return inflater;
	}

	/**
	 * 设置相对屏幕位置，主要是为了居中或者居下
	 */
	public void createDialog(int gravity, int width, int height,boolean isZoom) {
		mDialog.setCancelable(cancable);
		mDialog.setCanceledOnTouchOutside(touchable);
		findView(rootView);
		mDialog.setContentView(rootView);
		Window win = mDialog.getWindow();
		win.setGravity(gravity);
		if(isZoom){
			win.setWindowAnimations(R.style.PopupAnimation_Up);//从下至上
		}else{
			win.setWindowAnimations(R.style.Dialog_Public);//缩放
		}
		LayoutParams p = win.getAttributes();
		p.width = width;
		p.height = height;
		win.setAttributes(p);
	}

	/**
	 * 设置相对屏幕位置 普通模式
	 */
	public void createDialog(int gravity, int width, int height) {
		mDialog.setCancelable(cancable);
		mDialog.setCanceledOnTouchOutside(touchable);
		findView(rootView);
		mDialog.setContentView(rootView);
		Window win = mDialog.getWindow();
		win.setGravity(gravity);
		LayoutParams p = win.getAttributes();
		p.width = width;
		p.height = height;
		win.setAttributes(p);
	}

	public void createDialog(int gravity) {
		int width = MyApplication.getScreenWidth();
		int height = MyApplication.getScreenHeight();
		createDialog(gravity, width * 4 / 5, height / 3, false);
	}

	public Dialog getDialog() {
		return mDialog;
	}

	/**
	 * 显示对话框
	 */
	public void show() {
		try{
			if (mDialog != null && !mDialog.isShowing()) {
				mDialog.show();
			}
		}catch(RuntimeException ex){
		}
	}
	/**
	 * 显示对话框
	 */
	public void show_SYSTEM() {
		try{
			if (mDialog != null && !mDialog.isShowing()) {
//				mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//				mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
				mDialog.show();
			}else{
			}
		}catch(RuntimeException ex){
		}
	}


	/**
	 * 隐藏对话框
	 */
	public void dismiss() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	/**
	 * 隐藏对话框
	 */
	public boolean isshowing() {
		if (mDialog != null && mDialog.isShowing()) {
			if(mDialog.isShowing()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}


	/**
	 * 隐藏对话框监听事件 ，凡是可以控制隐藏对话框控件都可以继承这个
	 *
	 */
	public class MyOnDismissListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			dismiss();
		}
	}
}
