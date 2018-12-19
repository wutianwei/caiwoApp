package com.caiwo.caiwoapp.customview.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.caiwo.caiwoapp.R;
import com.caiwo.caiwoapp.customview.dialog.interfaces.DialogClickCallBack;


/**
 * 操作型 Dialog
 */
public class OperateDialog extends AbsDialog{
	private View view = null;  
	String promptValue = null;  
	private Context ctx = null;  
	public OperateDialog(Context activity) {
		super(activity);
		ctx = activity;  
	}

	public void create(String title,String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.CustomProgressDialog);
		view = LayoutInflater.from(ctx).inflate(R.layout.dialog_operate,null);
		builder.setView(view);
		builder.setTitle(message);
		builder.setPositiveButton("确定", this);  
		builder.setNegativeButton("取消", this);  
		builder.create(); 
		builder.show(); 
	}
	public void create(int id,String title,String message,DialogClickCallBack cliak)
	{  view = LayoutInflater.from(ctx).inflate(id,null);
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);  
		builder.setTitle(title);  
		builder.setView(view);  
		builder.create(); 
		builder.show();
	}  

	public void onClick(int buttonId) {
		if(buttonId == DialogInterface.BUTTON1){
			
		}else {
		}
	}

}
