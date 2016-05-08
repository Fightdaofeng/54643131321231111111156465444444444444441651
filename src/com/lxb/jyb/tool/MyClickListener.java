package com.lxb.jyb.tool;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 用于回调的抽象类
 * 
 * @author liu 2015-12-25
 */
public abstract class MyClickListener implements OnClickListener {
	/**
	 * 基类的onClick方法
	 */
	@Override
	public void onClick(View v) {
		myOnClick(Integer.parseInt(v.getTag() + ""), v);
	}

	public abstract void myOnClick(int position, View v);
}