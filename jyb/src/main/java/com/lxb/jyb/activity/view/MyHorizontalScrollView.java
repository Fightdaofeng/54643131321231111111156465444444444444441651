package com.lxb.jyb.activity.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class MyHorizontalScrollView extends HorizontalScrollView {

	private Activity mContext;
	public void setParam(View view, Activity context) {

		this.mContext = context;
		DisplayMetrics metrics = new DisplayMetrics();
		this.mContext.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

	}

	public void setParam(View view, Activity context, ImageView leftImage,
			ImageView rightImage) {

		this.mContext = context;
		DisplayMetrics metrics = new DisplayMetrics();
		this.mContext.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}

	public void showAndHideArrow() {
	}

	public MyHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
}
