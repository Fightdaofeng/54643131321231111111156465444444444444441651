package com.lxb.jyb.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.FragAdapter;
import com.lxb.jyb.activity.fragment.FM1_XT;
import com.lxb.jyb.activity.fragment.FM1_jhtx;
import com.lxb.jyb.activity.fragment.FM1_sx;
import com.lxb.jyb.fragment.Fragment_pc;
import com.lxb.jyb.fragment.Fragment_ydzs;
import com.lxb.jyb.fragment.Fragment_zd;
import com.lxb.jyb.fragment.Fragment_zy;
import com.lxb.jyb.util.SetStatiColor;

public class MessageActivity extends FragmentActivity implements
		OnClickListener {
	private View v1, v2;
	private TextView fm1_jhtx, fm1_sx, top_title;
	private FragAdapter adapter;
	private ViewPager viewpager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initFind();
		initViewPager();
	}

	/**
	 * 初始化viewpager
	 */
	private void initViewPager() {
		// TODO Auto-generated method stub
		// 构造适配器
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new FM1_jhtx());
		fragments.add(new FM1_XT());
		adapter = new FragAdapter(getSupportFragmentManager(), fragments);

		// 设定适配器
		viewpager = (ViewPager) findViewById(R.id.xiaoxi_pager);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(listener);
		viewpager.setCurrentItem(0);

	}

	private void setTextViewBG(int arg) {
		fm1_jhtx.setTextColor(Color.parseColor("#646464"));
		fm1_sx.setTextColor(Color.parseColor("#646464"));
		v1.setVisibility(View.INVISIBLE);
		v2.setVisibility(View.INVISIBLE);
		switch (arg) {
		case 0:
			fm1_jhtx.setTextColor(Color.parseColor("#01a4f2"));
			v1.setVisibility(View.VISIBLE);
			break;
		case 1:
			fm1_sx.setTextColor(Color.parseColor("#01a4f2"));
			v2.setVisibility(View.VISIBLE);
			break;
		}
	}

	OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			setTextViewBG(arg0);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void initFind() {
		// TODO Auto-generated method stub

		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("消息");

		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		fm1_jhtx = (TextView) findViewById(R.id.fm1_jhtx);
		fm1_sx = (TextView) findViewById(R.id.fm1_sx);
		setTextViewBG(0);
		addClick();
	}

	private void addClick() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		fm1_jhtx.setOnClickListener(this);
		fm1_sx.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fm1_jhtx:
			viewpager.setCurrentItem(0);
			setTextViewBG(0);
			break;
		case R.id.fm1_xt:
			viewpager.setCurrentItem(1);
			setTextViewBG(1);
			break;
		case R.id.top_return:
			this.finish();
			break;
		}
	}
}
