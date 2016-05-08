package com.lxb.jyb.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.MyApplication;
import com.lxb.jyb.R;
import com.lxb.jyb.fragment.Fragment_fxpg;
import com.lxb.jyb.fragment.Fragment_shouyi;
import com.lxb.jyb.fragment.Fragment_zdqk;
import com.lxb.jyb.fragment.Fragment_zhpf;
import com.lxb.jyb.fragment.Fragment_ZH;
import com.lxb.jyb.fragment.Fragment_jypl;
import com.lxb.jyb.fragment.Fragment_zhpf;
import com.lxb.jyb.util.SetStatiColor;

public class UserAnalyzeActivity extends FragmentActivity implements
		OnClickListener {
	private Fragment_shouyi fragment_shouyi;
	private Fragment_jypl fragment_jypl;
	private Fragment_zhpf fragment_zhpf;
	private Fragment_zdqk fragment_zdqk;
	private Fragment_fxpg fragment_fxpg;
	private MyApplication application;
	private FragmentManager fragmentmanager;
	private FragmentTransaction fragmenttransaction;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static boolean isForeground = false;
	private LinearLayout analyze_ll_sy, analyze_ll_jy, analyze_ll_pf,
			analyze_ll_zd, analyze_ll_fx;
	private ImageView imgsy, imgjy, imgpf, imgzd, imgfx;
	private TextView analyze_sy_tv, analyze_jy_tv, analyze_pf_tv,
			analyze_zd_tv, analyze_fx_tv;
	public static Context content;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_analyze);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		application = (MyApplication) getApplication();
		application.addAct(this);
		initView();
		fragmentmanager = getSupportFragmentManager();
		setTabSelection(0);
		content = this;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// JPushInterface.onResume(this);
		isForeground = true;

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// JPushInterface.onPause(this);
		isForeground = false;

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
			imgsy.setSelected(true);
			analyze_sy_tv.setSelected(true);
			if (fragment_shouyi == null) {
				fragment_shouyi = new Fragment_shouyi();
				fragmenttransaction.add(R.id.frame_content, fragment_shouyi);
			} else {
				fragmenttransaction.show(fragment_shouyi);
			}
			break;

		case 1:
			imgjy.setSelected(true);
			analyze_jy_tv.setSelected(true);
			if (fragment_jypl == null) {
				fragment_jypl = new Fragment_jypl();
				fragmenttransaction.add(R.id.frame_content, fragment_jypl);
			} else {
				fragmenttransaction.show(fragment_jypl);
			}
			break;
		case 2:
			imgpf.setSelected(true);
			analyze_pf_tv.setSelected(true);
			if (fragment_zhpf == null) {
				fragment_zhpf = new Fragment_zhpf();
				fragmenttransaction.add(R.id.frame_content, fragment_zhpf);
			} else {
				fragmenttransaction.show(fragment_zhpf);
			}
			break;
		case 3:
			imgzd.setSelected(true);
			analyze_zd_tv.setSelected(true);
			if (fragment_zdqk == null) {
				fragment_zdqk = new Fragment_zdqk();
				fragmenttransaction.add(R.id.frame_content, fragment_zdqk);
			} else {
				fragmenttransaction.show(fragment_zdqk);
			}
			break;

		case 4:
			imgfx.setSelected(true);
			analyze_fx_tv.setSelected(true);
			if (fragment_fxpg == null) {
				fragment_fxpg = new Fragment_fxpg();
				fragmenttransaction.add(R.id.frame_content, fragment_fxpg);
			} else {
				fragmenttransaction.show(fragment_fxpg);
			}
			break;
		}
		fragmenttransaction.commitAllowingStateLoss();
	}

	/**
	 * 隐藏fragment
	 * 
	 * @param fragmenttransaction2
	 */
	private void heidFragment(FragmentTransaction fragmenttransaction2) {
		// TODO Auto-generated method stub

		if (fragment_shouyi != null) {
			fragmenttransaction2.hide(fragment_shouyi);
		}
		if (fragment_jypl != null) {
			fragmenttransaction2.hide(fragment_jypl);
		}
		if (fragment_zdqk != null) {
			fragmenttransaction2.hide(fragment_zdqk);
		}
		if (fragment_fxpg != null) {
			fragmenttransaction2.hide(fragment_fxpg);
		}
		if (fragment_zhpf != null) {
			fragmenttransaction2.hide(fragment_zhpf);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		analyze_ll_sy = (LinearLayout) findViewById(R.id.analyze_ll_sy);
		analyze_ll_jy = (LinearLayout) findViewById(R.id.analyze_ll_jy);
		analyze_ll_pf = (LinearLayout) findViewById(R.id.analyze_ll_pf);
		analyze_ll_zd = (LinearLayout) findViewById(R.id.analyze_ll_zd);
		analyze_ll_fx = (LinearLayout) findViewById(R.id.analyze_ll_fx);
		//
		// main_zt_color = (LinearLayout) findViewById(R.id.main_zt_color);
		//
		// main_zt_color.setBackgroundColor(Color.parseColor("#0075CF"));

		imgsy = (ImageView) findViewById(R.id.imgsy);
		imgjy = (ImageView) findViewById(R.id.imgjy);
		imgzd = (ImageView) findViewById(R.id.imgzd);
		imgpf = (ImageView) findViewById(R.id.imgpf);
		imgfx = (ImageView) findViewById(R.id.imgfx);

		analyze_sy_tv = (TextView) findViewById(R.id.analyze_sy_tv);
		analyze_jy_tv = (TextView) findViewById(R.id.analyze_jy_tv);
		analyze_pf_tv = (TextView) findViewById(R.id.analyze_pf_tv);
		analyze_fx_tv = (TextView) findViewById(R.id.analyze_fx_tv);
		analyze_zd_tv = (TextView) findViewById(R.id.analyze_zd_tv);

		analyze_ll_sy.setOnClickListener(this);
		analyze_ll_jy.setOnClickListener(this);
		analyze_ll_pf.setOnClickListener(this);
		analyze_ll_zd.setOnClickListener(this);
		analyze_ll_fx.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.analyze_ll_sy:
			setTabSelection(0);
			break;

		case R.id.analyze_ll_jy:
			setTabSelection(1);
			break;
		case R.id.analyze_ll_pf:
			setTabSelection(2);
			break;
		case R.id.analyze_ll_zd:
			setTabSelection(3);
			break;

		case R.id.analyze_ll_fx:
			setTabSelection(4);
			break;
		}
	}

	private static final String TAG = "BaseActivity";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		FragmentManager fm = getSupportFragmentManager();

		int index = requestCode >> 16;
		if (index != 0) {
			index--;
			if (fm.getFragments() == null || index < 0
					|| index >= fm.getFragments().size()) {
				Log.w(TAG, "Activity result fragment index out of range: 0x"
						+ Integer.toHexString(requestCode));
				return;
			}
			Fragment frag = fm.getFragments().get(index);
			if (frag == null) {
				Log.w(TAG, "Activity result no fragment exists for index: 0x"
						+ Integer.toHexString(requestCode));
			} else {
				handleResult(frag, requestCode, resultCode, data);
			}
			return;
		}
	}

	/**
	 * 递归调用，对所有子Fragement生效
	 * 
	 * @param frag
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private void handleResult(Fragment frag, int requestCode, int resultCode,
			Intent data) {
		frag.onActivityResult(requestCode & 0xffff, resultCode, data);
		List<Fragment> frags = frag.getChildFragmentManager().getFragments();
		if (frags != null) {
			for (Fragment f : frags) {
				if (f != null) {
					handleResult(f, requestCode, resultCode, data);
				}
			}
		}
	}

	private void retBtn() {
		imgsy.setSelected(false);
		imgjy.setSelected(false);
		imgzd.setSelected(false);
		imgpf.setSelected(false);
		imgfx.setSelected(false);
		analyze_sy_tv.setSelected(false);
		analyze_jy_tv.setSelected(false);
		analyze_pf_tv.setSelected(false);
		analyze_fx_tv.setSelected(false);
		analyze_zd_tv.setSelected(false);

	}

}
