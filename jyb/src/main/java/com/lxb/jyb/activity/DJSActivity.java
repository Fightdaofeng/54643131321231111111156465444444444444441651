package com.lxb.jyb.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.FragAdapter;
import com.lxb.jyb.fragment.Fragment_sjfx;
import com.lxb.jyb.fragment.Fragment_wys;
import com.lxb.jyb.util.SetStatiColor;

public class DJSActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout m1_layout, m2_layout;
	private TextView t1_tv, t2_tv, tt_tv, dd_tv, top_title,top_msg;
	private ImageView top_return;
	private ViewPager viewpager;
	private FragAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cldetail);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initFindView();

	}

	private void initFindView() {
		// TODO Auto-generated method stub
		m1_layout = (LinearLayout) findViewById(R.id.m1_lay);
		m2_layout = (LinearLayout) findViewById(R.id.m2_lay);
		m1_layout.setOnClickListener(this);
		m2_layout.setOnClickListener(this);

		// 构造适配器
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new Fragment_sjfx());
		fragments.add(new Fragment_wys());
		adapter = new FragAdapter(getSupportFragmentManager(), fragments);
		// 设定适配器
		viewpager = (ViewPager) findViewById(R.id.cld_pager);
		viewpager.setAdapter(adapter);

		viewpager.setOnPageChangeListener(listener);

		Intent intent = getIntent();
		int intExtra = intent.getIntExtra("index", 0);
		// if (intExtra == 0) {
		setTextViewBG(0);
		// viewpager.setCurrentItem(0);
		// } else {
		// setTextViewBG(1);
		// viewpager.setCurrentItem(1);
		// }
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

	private void setTextViewBG(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		// case 0:
		// m1_layout.setSelected(true);
		// m2_layout.setSelected(false);
		// break;
		//
		// case 1:
		// m2_layout.setSelected(true);
		// m1_layout.setSelected(false);
		// break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.m1_lay:
			viewpager.setCurrentItem(0);
			setTextViewBG(0);
			break;

		case R.id.m2_lay:
			viewpager.setCurrentItem(1);
			setTextViewBG(1);
			break;
		}
	}
}
