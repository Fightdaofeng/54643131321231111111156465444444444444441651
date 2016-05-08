package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.tool.NetworkCenter;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Activity_GYWM extends Activity implements OnClickListener {
	private TextView version_tv, top_title;

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
		setContentView(R.layout.activity_guanyuwomen);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_return).setOnClickListener(this);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("关于我们");

		version_tv = (TextView) findViewById(R.id.version_tv);
		String version = NetworkCenter.getVersion(this);
		version_tv.setText("版本号：V" + version);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
