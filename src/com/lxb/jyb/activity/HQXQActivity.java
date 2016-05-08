package com.lxb.jyb.activity;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.fragment.DuoKongBiFragment;
import com.lxb.jyb.fragment.Fragment_ZH;
import com.lxb.jyb.fragment.Fragment_cuntou;
import com.lxb.jyb.fragment.JGYJFragment;
import com.lxb.jyb.fragment.TuBiaoFragment;
import com.lxb.jyb.util.SetStatiColor;

public class HQXQActivity extends FragmentActivity implements OnClickListener {
	private TuBiaoFragment tbfragment;
	private DuoKongBiFragment dkbfragment;
	private JGYJFragment jgyjfragment;
	private Fragment_cuntou fragment_cuntou;
	private TextView tv1, tv2, tv3, tv4, top_msg, create_tv;
	private View v1, v2, v3, v4;
	private FragmentManager fragmentmanager;
	private FragmentTransaction fragmenttransaction;
	private PopupWindow popu1;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hq_xq_activity);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initFind();
		fragmentmanager = getSupportFragmentManager();
		setTabSelection(0);
	}

	private void initFind() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		create_tv = (TextView) findViewById(R.id.create_tv);
		top_msg = (TextView) findViewById(R.id.top_msg);

		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v3 = findViewById(R.id.v3);
		v4 = findViewById(R.id.v4);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		create_tv.setOnClickListener(this);
		top_msg.setOnClickListener(this);
		findViewById(R.id.top_return).setOnClickListener(this);

	}

	private void retBtn() {
		tv1.setSelected(false);
		tv2.setSelected(false);
		tv3.setSelected(false);
		tv4.setSelected(false);
		v1.setVisibility(View.INVISIBLE);
		v2.setVisibility(View.INVISIBLE);
		v3.setVisibility(View.INVISIBLE);
		v4.setVisibility(View.INVISIBLE);

	}

	/**
	 * 隐藏fragment
	 * 
	 * @param fragmenttransaction2
	 */
	private void heidFragment(FragmentTransaction fragmenttransaction2) {
		// TODO Auto-generated method stub

		if (tbfragment != null) {
			fragmenttransaction2.hide(tbfragment);
		}
		if (jgyjfragment != null) {
			fragmenttransaction2.hide(jgyjfragment);
		}
		if (dkbfragment != null) {
			fragmenttransaction2.hide(dkbfragment);
		}
		if (fragment_cuntou != null) {
			fragmenttransaction2.hide(fragment_cuntou);
		}
	}

	/**
	 * 设置fragment显示
	 * 
	 * @param i
	 */
	private void setTabSelection(int i) {
		// TODO Auto-generated method stub
		retBtn();
		fragmenttransaction = fragmentmanager.beginTransaction();
		heidFragment(fragmenttransaction);
		switch (i) {
		case 0:
			tv1.setSelected(true);
			v1.setVisibility(View.VISIBLE);
			if (tbfragment == null) {
				tbfragment = new TuBiaoFragment();
				fragmenttransaction.add(R.id.hq_lay, tbfragment);
			} else {
				fragmenttransaction.show(tbfragment);
			}
			break;
		case 1:
			tv2.setSelected(true);
			v2.setVisibility(View.VISIBLE);
			if (dkbfragment == null) {
				dkbfragment = new DuoKongBiFragment();
				fragmenttransaction.add(R.id.hq_lay, dkbfragment);
			} else {
				fragmenttransaction.show(dkbfragment);
			}
			break;

		case 2:
			tv3.setSelected(true);
			v3.setVisibility(View.VISIBLE);
			if (fragment_cuntou == null) {
				fragment_cuntou = new Fragment_cuntou();
				fragmenttransaction.add(R.id.hq_lay, fragment_cuntou);
			} else {
				fragmenttransaction.show(fragment_cuntou);
			}
			break;
		case 3:
			tv4.setSelected(true);
			v4.setVisibility(View.VISIBLE);
			if (jgyjfragment == null) {
				jgyjfragment = new JGYJFragment();
				fragmenttransaction.add(R.id.hq_lay, jgyjfragment);
			} else {
				fragmenttransaction.show(jgyjfragment);
			}
			break;

		}
		fragmenttransaction.commitAllowingStateLoss();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv1:
			setTabSelection(0);
			break;

		case R.id.tv2:
			setTabSelection(1);
			break;
		case R.id.tv3:
			setTabSelection(2);
			break;
		case R.id.tv4:
			setTabSelection(3);
			break;
		case R.id.top_return:
			this.finish();
			break;
		case R.id.create_tv:

			initPopupWindow();

			break;
		case R.id.top_msg:
			startActivity(new Intent(HQXQActivity.this, MessageActivity.class));
			break;
		}
	}

	private void initPopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pp = inflater.inflate(R.layout.jiancang_pop, null);
		popu1 = new PopupWindow(pp, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popu1.setFocusable(true);
		popu1.setOutsideTouchable(true);
		pp.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int popupWidth = pp.getMeasuredWidth();
		int popupHeight = pp.getMeasuredHeight();

		int[] location = new int[2];
		create_tv.getLocationOnScreen(location);
		// popu1.showAtLocation(create_tv, Gravity.NO_GRAVITY,
		// (location[0]+create_tv.getWidth()/2)-popupWidth/2,
		// location[1]+popupHeight);
		popu1.showAsDropDown(pp, (location[0] + create_tv.getWidth() / 2)
				- popupWidth / 2, location[1]+60, Gravity.NO_GRAVITY);
		pp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popu1.isShowing()) {
					popu1.dismiss();
				}
				return false;
			}
		});

		pp.findViewById(R.id.btn_jc).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(HQXQActivity.this,
						CreateOrderActivity.class));
				popu1.dismiss();
			}
		});
		pp.findViewById(R.id.btn_gd).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(HQXQActivity.this,
						Activity_GuaDan.class));
				popu1.dismiss();
			}
		});
	}

}
