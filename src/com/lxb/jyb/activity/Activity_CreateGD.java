package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

public class Activity_CreateGD extends Activity implements OnClickListener {
	private ImageView switch1, switch2;
	private TextView top_title, zd_btn, zk_btn;

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
		setContentView(R.layout.activity_createguadan);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.sel_jypz).setOnClickListener(this);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("新建挂单");
		switch1 = (ImageView) findViewById(R.id.switch1);
		switch2 = (ImageView) findViewById(R.id.switch2);
		zd_btn = (TextView) findViewById(R.id.zd_btn);
		zk_btn = (TextView) findViewById(R.id.zk_btn);
		zk_btn.setSelected(true);
		zd_btn.setSelected(false);

		zd_btn.setOnClickListener(this);
		zk_btn.setOnClickListener(this);
		switch1.setOnClickListener(this);
		switch2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.switch1:
			if (switch1.isSelected()) {
				switch1.setSelected(false);
				findViewById(R.id.set_1).setVisibility(View.GONE);
			} else {
				switch1.setSelected(true);
				findViewById(R.id.set_1).setVisibility(View.VISIBLE);
			}
			break;

		case R.id.switch2:
			if (switch2.isSelected()) {
				switch2.setSelected(false);
				findViewById(R.id.set_2).setVisibility(View.GONE);
			} else {
				switch2.setSelected(true);
				findViewById(R.id.set_2).setVisibility(View.VISIBLE);
			}
			break;

		case R.id.zd_btn:
			zd_btn.setSelected(true);
			zk_btn.setSelected(false);

			break;
		case R.id.zk_btn:
			zd_btn.setSelected(false);
			zk_btn.setSelected(true);
			break;
		case R.id.top_return:
			this.finish();
			break;
		}
	}
}
