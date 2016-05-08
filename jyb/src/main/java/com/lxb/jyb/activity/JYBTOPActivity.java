package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

public class JYBTOPActivity extends Activity {
	private PullToRefreshListView pulllistView;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jybtop50);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initFindView();
	}

	private void initFindView() {
		// TODO Auto-generated method stub
		pulllistView = (PullToRefreshListView) findViewById(R.id.jyb_list);
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT,
				AbsListView.LayoutParams.WRAP_CONTENT);
		View header = getLayoutInflater().inflate(R.layout.inc_top,
				pulllistView, false);

		listview = pulllistView.getRefreshableView();
		listview.addHeaderView(header);
	}
}
