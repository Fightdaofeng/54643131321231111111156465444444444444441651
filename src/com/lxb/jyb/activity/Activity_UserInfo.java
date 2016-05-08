package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Activity_UserInfo extends Activity {
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
		setContentView(R.layout.geren_ziliao);
	}

}
