package com.caiwo.caiwoapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 *
 */
public class ToastUtils {

	private Context mContext;
	private static ToastUtils toast;
	private static String toastMsg = "fx";
	private static long oneTime=0;  
	private static long twoTime=0;
	private static Toast mToast;

	public ToastUtils(Context context) {
		this.mContext = context;
	}

	public static ToastUtils getInstance(Context context) {
		if (toast == null) {
			toast = new ToastUtils(context.getApplicationContext());
			oneTime = System.currentTimeMillis();  
		}else{
			twoTime = System.currentTimeMillis(); 
		}
		return toast;
	}

	/**
	 * 显示提示语，时长是固定的
	 * 
	 * @param text
	 */
	public void show(String text) {
		if(null != text && !text.equals("")){
            if(text.equals("无此用户")){
                return;
			}
			show(text, 100);
		}
	}

	/**
	 * 显示提示语，时长自定义
	 * 
	 * @param text
	 * @param duration
	 */
	private void show(String text, int duration) {
		try{
			if(null != text && !text.equals("") && !text.equals(toastMsg)){
				toastMsg = text;
				mToast = Toast.makeText(mContext.getApplicationContext(), text, duration);
				mToast.show();
				oneTime = twoTime;
			}else{
				if(twoTime-oneTime > 3000){ //相同内容间隔3秒 避免重复提交
					if(null != text && !text.equals("")){
						mToast = Toast.makeText(mContext.getApplicationContext(), text, duration);
						mToast.show();
						oneTime = twoTime;  
					}
				}  
			}
		}catch(Exception ex){}
	}

	/**
	 * 取消
	 */
	public void cancle(){
		if(null != mToast){
			mToast.cancel();
			mToast = null;
		}
	}
}
