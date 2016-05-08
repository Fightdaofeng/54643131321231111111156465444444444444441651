package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

public class BDMT4Activity extends Activity implements OnClickListener {
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
		setContentView(R.layout.activity_bdmt4);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);

		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("绑定MT4账号");
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
	}

	private void setOnclick() {
		// TODO
		findViewById(R.id.top_return).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;

		default:
			break;
		}
	}
}
