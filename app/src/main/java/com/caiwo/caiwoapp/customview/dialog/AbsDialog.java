package com.caiwo.caiwoapp.customview.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.caiwo.caiwoapp.customview.dialog.interfaces.DialogCallBack;


public abstract class AbsDialog implements DialogCallBack
,DialogInterface.OnClickListener

{  
	@SuppressWarnings("unused")
	private Context context = null;  
	public AbsDialog(Context context)  
	{  
		this.context = context;  
	}  
	
	public void onClick(DialogInterface v, int buttonId)  
	{  
		onClick(buttonId);  
		
		  
	}  
	public void dialogFinished(AbsDialog dialog, int buttonId)  
	{  
		
	}  
	public void show(){
		
	}
}  
