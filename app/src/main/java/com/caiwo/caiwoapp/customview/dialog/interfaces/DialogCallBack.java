package com.caiwo.caiwoapp.customview.dialog.interfaces;


public interface DialogCallBack
{
	public void create(String title, String message);
	public void create(int id, String title, String message, DialogClickCallBack click);
	public void show();
	public void onClick(int buttonId);
}
