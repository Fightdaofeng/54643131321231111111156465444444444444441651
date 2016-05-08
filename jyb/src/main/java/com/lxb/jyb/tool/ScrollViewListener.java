package com.lxb.jyb.tool;

import com.lxb.jyb.activity.view.ObservableScrollView;


public interface ScrollViewListener {

	void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy);
	
}