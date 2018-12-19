package com.caiwo.caiwoapp.customview.dialog.interfaces;


import com.caiwo.caiwoapp.customview.dialog.AbsDialog;

public interface IDialogFinishedCallBack
{  
    public static int OK_BUTTON = -1;  
    public static int CANCEL_BUTTON = -2;  
    public void dialogFinished(AbsDialog dialog, int buttonId);
}  
