package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

public class Activity_YJFK extends Activity implements OnClickListener {
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
		setContentView(R.layout.activity_yjfk);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("意见反馈");
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
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
