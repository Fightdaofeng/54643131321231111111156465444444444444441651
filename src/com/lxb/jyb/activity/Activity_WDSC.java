package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_WDSC extends Activity implements OnClickListener {
	private TextView bj, wc;
	// xinwen_tv, celue_tv, handan_tv;
	// private LinearLayout xinwen_lay, celue_lay, handan_lay;

	private TextView top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		setContentView(R.layout.activity_wodeshoucang);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		
		bj = (TextView) findViewById(R.id.top_bj);
		wc = (TextView) findViewById(R.id.top_wc);
		// xinwen_tv = (TextView) findViewById(R.id.xinwen_tv);
		// celue_tv = (TextView) findViewById(R.id.celue_tv);
		// handan_tv = (TextView) findViewById(R.id.handan_tv);
		// xinwen_lay = (LinearLayout) findViewById(R.id.xinwen_lay);
		// celue_lay = (LinearLayout) findViewById(R.id.celue_lay);
		// handan_lay = (LinearLayout) findViewById(R.id.handan_lay);

//		xinwen_lay.setOnClickListener(this);
//		celue_lay.setOnClickListener(this);
//		handan_lay.setOnClickListener(this);

		bj.setOnClickListener(this);
		wc.setOnClickListener(this);

//		findViewById(R.id.top_msg).setVisibility(View.GONE);
		findViewById(R.id.top_return).setOnClickListener(this);
	}

//	private void setSelectTab(int index) {
//		retBtn();
//		switch (index) {
//		case 0:
//			xinwen_lay.setSelected(true);
//			xinwen_tv.setSelected(true);
//			break;
//
//		case 1:
//			celue_lay.setSelected(true);
//			celue_tv.setSelected(true);
//			break;
//		case 2:
//			handan_lay.setSelected(true);
//			handan_tv.setSelected(true);
//			break;
//		}
//
//	}
//
//	private void retBtn() {
//		// TODO Auto-generated method stub
//		xinwen_tv.setSelected(false);
//		celue_tv.setSelected(false);
//		handan_tv.setSelected(false);
//		xinwen_lay.setSelected(false);
//		celue_lay.setSelected(false);
//		handan_lay.setSelected(false);
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;
		// case R.id.xinwen_lay:
		// setSelectTab(0);
		// break;
		//
		// case R.id.celue_lay:
		// setSelectTab(1);
		// break;
		// case R.id.handan_lay:
		// setSelectTab(2);
		// break;
		case R.id.top_bj:

			break;
		case R.id.top_wc:

			break;
		}
	}
}
