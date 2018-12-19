package com.caiwo.caiwoapp.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

public class AbsDiadlog extends Dialog {

	public boolean needInputMethod = false;

	public AbsDiadlog(Context context, boolean needInputMethod) {
		super(context);
		this.needInputMethod = needInputMethod;
	}

	public AbsDiadlog(Context context, int theme, boolean needInputMethod) {
		super(context, theme);
		this.needInputMethod = needInputMethod;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent); 
		if (!needInputMethod) {
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}
	}
}
