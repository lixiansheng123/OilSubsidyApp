package com.yuedong.oilsubsidyapp.framework;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuedong.oilsubsidyapp.R;


/** 基本的Dialog 实现标题然后提供内容給子类覆盖 */
public class BaseDialog extends Dialog {
	private Context mContext;
	private boolean mCanCanceledOnTouchOutSide;
	/** 允许回退消失 */
	private boolean mCancelable;
	// 信息框
	private TextView mMessageView = null;
	// 标题
	private TextView mTitleView = null;
	private Button mBtnNegative;

	private static BaseDialog mDialog;

	public BaseDialog(Context context) {
		this(context, R.style.dialog);
		mCanCanceledOnTouchOutSide = false;
		mCancelable = true;
		this.mContext = context;
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		mCanCanceledOnTouchOutSide = false;
		mCancelable = true;
		this.mContext = context;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mCanCanceledOnTouchOutSide)
			return super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_CANCEL:
			dismiss();
			break;
		default:
			return super.onTouchEvent(event);
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && !mCancelable) {
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
			if (mContext instanceof BaseActivity) {
//				((BaseActivity) mContext).clearAsyncTask();
			}

			if (mDialog.mBtnNegative != null) {
				mDialog.mBtnNegative.performClick();
			}
			try {
				getContext();
			} catch (Exception e) {
			}

		}
		return super.onKeyDown(keyCode, event);

	}

	public void setDialogCancelable(boolean cancelable) {
		mDialog.setCancelable(cancelable);
		mCancelable = cancelable;
	}

	public void setCanCanceledOnTouchOutSide(boolean bFlag) {
		mCanCanceledOnTouchOutSide = bFlag;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dismiss();
		}

	};

	public void dismissAfterDelay(long time) {
		mHandler.sendEmptyMessageDelayed(0, time);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		mTitleView.setText(title);
	}

	public void setMessage(CharSequence message) {
		mMessageView.setText(message);
	}

	public String getMessage() {
		return mMessageView.getText().toString();
	}

	public static class Builder {
		BaseDialog dialog;
		// private Button mBtnNegative;
		private Button mBtnPositive;
		private ImageView mImgViewIcon;
		private Context mContext;
		private String mMessage;
		private String mTitle;
		private ProgressBar progressBar;

		public Builder(Context context) {
			mContext = context;
		}

		/**
		 * @return 创建带两个按钮的提示对话框
		 */
		public BaseDialog createTitleMessage2BtnDialog() {
			LayoutInflater layoutinflater = LayoutInflater.from(getContext());
			dialog = new BaseDialog(mContext);
			View view = layoutinflater.inflate(R.layout.layout_dialog_title_message_2btn, null);
			dialog.mTitleView = (TextView) view.findViewById(R.id.tv_dialog_title);
			dialog.mMessageView = (TextView) view.findViewById(R.id.tv_dialog_content);
			dialog.mBtnNegative = (Button) view.findViewById(R.id.btn_left_id);
			mBtnPositive = (Button) view.findViewById(R.id.btn_right_id);
			dialog.setContentView(view);
			Window window = dialog.getWindow();
			window.setBackgroundDrawableResource(R.drawable.transparentpic);
			mDialog = dialog;
			return dialog;
		}

		public BaseDialog createMessage1BtnDialog() {
			LayoutInflater layoutinflater = LayoutInflater.from(getContext());
			dialog = new BaseDialog(mContext);
			View view = layoutinflater.inflate(R.layout.layout_dialog_title_message_2btn, null);
			view.findViewById(R.id.btn_left_id).setVisibility(View.GONE);
			view.findViewById(R.id.tv_dialog_title).setVisibility(View.GONE);
			dialog.mMessageView = (TextView) view.findViewById(R.id.tv_dialog_content);
			mBtnPositive = (Button) view.findViewById(R.id.btn_right_id);
			dialog.setContentView(view);
			Window window = dialog.getWindow();
			window.setBackgroundDrawableResource(R.drawable.transparentpic);
			mDialog = dialog;
			return dialog;
		}

		/**
		 * @return 创建带银行卡绑定提示对话框
		 */
		public BaseDialog createTitleBindBank2BtnDialog(View view) {
			dialog = new BaseDialog(mContext);
			// View view =
			// layoutinflater.inflate(R.layout.layout_dialog_title_message_2btn,
			// null);
			// dialog.mTitleView = (TextView)
			// view.findViewById(R.id.tv_dialog_title);
			// dialog.mMessageView = (TextView)
			// view.findViewById(R.id.tv_dialog_content);
			// dialog.mBtnNegative = (Button)
			// view.findViewById(R.id.btn_left_id);
			// mBtnPositive = (Button) view.findViewById(R.id.btn_right_id);
			dialog.setContentView(view);
			Window window = dialog.getWindow();
			window.setBackgroundDrawableResource(R.drawable.transparentpic);
			mDialog = dialog;
			return dialog;
		}

		/**
		 * @return 创建下载对话框
		 */
		public BaseDialog createDownloadingDialog() {
			LayoutInflater layoutinflater = LayoutInflater.from(getContext());
			dialog = new BaseDialog(mContext);
			View view = layoutinflater.inflate(R.layout.layout_dialog_upgrading, null);
			dialog.mTitleView = (TextView) view.findViewById(R.id.upgrading_dialog_title);
			dialog.mBtnNegative = (Button) view.findViewById(R.id.btn_ugrade_cancel);
			progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
			dialog.setContentView(view);
			dialog.setCanCanceledOnTouchOutSide(false);
			mDialog = dialog;
			return dialog;
		}

//		/**
//		 * @return 创建网络加载对话框
//		 */
//		public BaseDialog creatNetLoadingDialog() {
//			LayoutInflater layoutinflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
//			View view = layoutinflater.inflate(R.layout.layout_dialog_net, null);
//			dialog = new BaseDialog(mContext);
//			dialog.mMessageView = (TextView) view.findViewById(R.id.message_tv);
//			final ProgressWheel wheel = (ProgressWheel) view.findViewById(R.id.net_loading_pb);
//			dialog.setOnShowListener(new OnShowListener() {
//
//				@Override
//				public void onShow(DialogInterface dialog) {
//					wheel.spin();
//				}
//			});
//			dialog.setContentView(view);
//			dialog.setCanCanceledOnTouchOutSide(false);
//			dialog.setCancelable(false);
//			dialog.setOnDismissListener(new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//					wheel.stopSpinning();
//				}
//			});
//			mDialog = dialog;
//			return dialog;
//		}

		/**
		 * @return 创建提示对话框
		 */
		public BaseDialog createMessageDialog() {
			LayoutInflater layoutinflater = LayoutInflater.from(getContext());
			dialog = new BaseDialog(mContext);
			View view = layoutinflater.inflate(R.layout.layout_dialog_message, null);
			dialog.mMessageView = (TextView) view.findViewById(R.id.tv_message);
			dialog.mTitleView = (TextView) view.findViewById(R.id.tv_dialog_title);
			dialog.setContentView(view);
			dialog.setCanCanceledOnTouchOutSide(false);
			dialog.setCancelable(false);
			mDialog = dialog;
			return dialog;
		}

		public Context getContext() {
			return mContext;
		}

		public Builder setMessage(String s) {
			mMessage = s;
			mDialog.mMessageView.setText(mMessage);
			return this;
		}

		public Builder setIconResource(int iconID) {
			if (mImgViewIcon != null) {
				mImgViewIcon.setBackgroundResource(iconID);
			}
			return this;
		}

		public Builder setNegativeButton(int text, OnClickListener onclicklistener) {
			dialog.mBtnNegative.setText(text);
			final OnClickListener listener = onclicklistener;
			dialog.mBtnNegative.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					listener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
				}
			});
			return this;
		}

		public Builder setPositiveButton(int text, OnClickListener onclicklistener) {
			mBtnPositive.setText(text);
			final OnClickListener listener = onclicklistener;
			mBtnPositive.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
				}
			});
			return this;
		}

		public Builder setTitle(int title) {
			mTitle = (String) mContext.getText(title);
			mDialog.mTitleView.setText(mTitle);
			return this;
		}

		public Builder setTitle(String s) {
			mTitle = s;
			mDialog.mTitleView.setText(mTitle);
			return this;
		}

		public ProgressBar getProgressBar() {
			return progressBar;
		}
	}
}
