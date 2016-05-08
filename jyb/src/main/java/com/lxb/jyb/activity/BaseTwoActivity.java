package com.lxb.jyb.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.util.AbDialogUtil;
import com.lxb.jyb.R;

public class BaseTwoActivity extends FragmentActivity {
  /**
   * the http instance
   */
	protected AbHttpUtil httpInstance;

  /**
   * the http params
   */
	protected AbRequestParams params;

  /**
   * the image loader
   */
//	protected AbImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullscreen();
		initHttp();
	}

	// 去标题栏
	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	public void setExit() {
		finish();
	}

	protected void initHttp() {
		httpInstance = AbHttpUtil.getInstance(this);
		params = new AbRequestParams();
//		imageLoader = AbImageLoader.newInstance(this);
	}

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * @param pClass
	 * @param pBundle
	 */
	protected <T> void startAimActivity(final Class<T> pActClassName) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pActClassName);
		startActivity(_Intent);
	}

	public void showDialog(String message, Boolean flag,
			View.OnClickListener listener) {
		showDialog(message, flag, null);
	}


	public void showDialog(int resId, boolean flag,
			View.OnClickListener listener) {
		showDialog(getString(resId), flag, listener);
	}

	public void showDialog(String message, boolean flag) {
		showDialog(message, true, null);
	}
	public void removeDialog() {
		AbDialogUtil.removeDialog(this);
	}
	public void showDialog(int resId, boolean flag) {
		showDialog(getString(resId), flag);
	}
}
