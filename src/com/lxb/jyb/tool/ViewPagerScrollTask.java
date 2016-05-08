package com.lxb.jyb.tool;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

public class ViewPagerScrollTask implements Runnable {
	private Context mcontext;
	private ViewPager mviewPager;
	private List<ImageView> mimageViews;
	private int mcurrentItem = 0;

	public ViewPagerScrollTask(Context context, ViewPager viewPager,
			List<ImageView> imageViews, int currentItem) {
		this.mviewPager = viewPager;
		this.mimageViews = imageViews;
		this.mcurrentItem = currentItem;
		this.mcontext = context;
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mviewPager.setCurrentItem(mcurrentItem);
		};
	};

	@Override
	public void run() {
		synchronized (mviewPager) {
			System.out.println("mcurrentItem: " + mcurrentItem);
			mcurrentItem = (mcurrentItem + 1) % mimageViews.size();
			handler.obtainMessage().sendToTarget();
		}
	}

}