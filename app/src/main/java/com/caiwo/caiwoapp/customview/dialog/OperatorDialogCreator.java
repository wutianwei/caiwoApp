package com.caiwo.caiwoapp.customview.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.caiwo.caiwoapp.R;


/**
 * 正常提示框的基类
 */
public class OperatorDialogCreator extends AbsDialogCreator {

	private Button ok_Btn,cancle_Btn;
	private TextView title, content;
	private View line_View;
	private String titleTips = "提示";
	private String contentStr = "确认要这样做吗？";
	private String cancelText = "取消";
	private String okText = "确定";
	private boolean needCancel = true;
	private boolean needOk = true;
	private MyDialogOnCancleClickListener OnCancleClickListener;
	private MyDialogOnOkClickListener clickListener;
	private float isSetContentSize = 0;
    private boolean  isCancleListener;
	private int cancelTxColor = 0, okTxColor = 0;

	public OperatorDialogCreator(Context context, boolean needinputMethod) {
		super(context, needinputMethod);
	}

	public OperatorDialogCreator(Context context, boolean needinputMethod,
								 boolean isCancleListener) {
		super(context, needinputMethod);
		this.isCancleListener = isCancleListener;
	}

	/**
	 * 设置取消按钮文本
	 */
	public void setCancelText(String cancel) {
		this.cancelText = cancel;
	}
	/**
	 * 设置取消按钮文本颜色
	 *
	 * @param cancelColor
	 */
	public void setCancelTextColor(int cancelColor) {
		this.cancelTxColor = cancelColor;
	}
	/**
	 * 设置确定按钮文本
	 * @param ok
	 */
	public void setOkText(String ok) {
		this.okText = ok;
	}
	/**
	 * 设置确定按钮文本颜色
	 */
	public void setOkTextColor(int okColor) {
		this.okTxColor = okColor;
	}

	/**
	 * 设置确定按钮文本
	 */
	public void setOkGone(boolean needOk) {
		this.needOk = needOk;
	}

	/**
	 * 设置标题
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.titleTips = title;
	}

	/**
	 * 设置内容
	 */
	public void setContent(String content) {
		this.contentStr = content;
	}

	/**
	 * 设置内容自踢大小
	 */
	public void setContentSize(float isSetContentSize) {
		this.isSetContentSize = isSetContentSize;
	}


	/**
	 * 设置是否需要取消按钮
	 */
	public void setNeedCancelBtn(boolean inneed) {
		needCancel = inneed;
	}

	@Override
	public int getRootViewId() {
		return R.layout.dialog_operator;
	}

	/**
	 * 设置确定按钮监听事件
	 */
	public void setOnOKListener(MyDialogOnOkClickListener clickListener) {
		this.clickListener = clickListener;
	}

	/**
	 * 设置取消按钮监听事件
	 */
	public void setOnCancleListener(MyDialogOnCancleClickListener clickListener) {
		this.OnCancleClickListener = clickListener;
	}


	@Override
	public void findView(View rootView) {

		title = (TextView) rootView.findViewById(R.id.tv_operator_title);
		content = (TextView) rootView.findViewById(R.id.tv_operator_content);
		cancle_Btn = (Button) rootView.findViewById(R.id.cancle_Btn);
		ok_Btn = (Button) rootView.findViewById(R.id.ok_Btn);
		line_View =  rootView.findViewById(R.id.line_View);

		if (!needCancel) {
			cancle_Btn.setVisibility(View.GONE);
			line_View.setVisibility(View.GONE);
//			ok_Btn.setBackgroundResource(R.drawable.bg_tx_selecter_round_bottom);
		}

		if(!needOk){
			ok_Btn.setVisibility(View.GONE);
			line_View.setVisibility(View.GONE);
//			cancle_Btn.setBackgroundResource(R.drawable.bg_tx_selecter_round_bottom);
		}

		if(cancelTxColor != 0){
			cancle_Btn.setTextColor(cancelTxColor);
		}
		if(okTxColor != 0){
			ok_Btn.setTextColor(okTxColor);
		}

		if(titleTips==null||titleTips.equals("")){
			title.setVisibility(View.GONE);
		}else{
			title.setText(titleTips);
		}
		cancle_Btn.setText(cancelText);
		ok_Btn.setText(okText);
		content.setText(contentStr);
		if(isSetContentSize != 0){
			content.setTextSize(isSetContentSize);
		}

		if(isCancleListener){
			cancle_Btn.setOnClickListener(new MyCancleCLickListener(OnCancleClickListener));
		}else{
			cancle_Btn.setOnClickListener(new MyOnDismissListener());
		}

		ok_Btn.setOnClickListener(new MyOkCLickListener(clickListener));
	}

	public class MyOkCLickListener implements OnClickListener {

		private MyDialogOnOkClickListener clickListener;

		public MyOkCLickListener(MyDialogOnOkClickListener clickListener) {
			this.clickListener = clickListener;
		}

		@Override
		public void onClick(View v) {
			dismiss();
			if (clickListener != null) {
				clickListener.onOK();
			}
		}
	}


	public class MyCancleCLickListener implements OnClickListener {

		private MyDialogOnCancleClickListener clickListener;

		public MyCancleCLickListener(MyDialogOnCancleClickListener clickListener) {
			this.clickListener = clickListener;
		}

		@Override
		public void onClick(View v) {
			dismiss();
			if (clickListener != null) {
				clickListener.onCancle();
			}
		}
	}

	/**
	 * 点击确认按钮回调监听事件
	 */
	public interface MyDialogOnOkClickListener {
		/**
		 * 点击确定后要做什么事
		 */
		public void onOK();

	}
public void setGone(){
	if(null != title){
		title.setVisibility(View.GONE);
	}
	}
	/**
	 * 点击取消按钮回调监听事件
	 */
	public interface MyDialogOnCancleClickListener {
		/**
		 * 点击取消后要做什么事
		 */
		public void onCancle();

	}

}





