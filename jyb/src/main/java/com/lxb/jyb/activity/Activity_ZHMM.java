package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.util.Constants;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Activity_ZHMM extends Activity implements OnClickListener {
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
		setContentView(R.layout.activity_zhmm);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("找回密码");
		findViewById(R.id.next_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next_btn:
			startActivityForResult(new Intent(this, Activity_CZMM.class),
					Constants.REQUESTCODE);
			break;

		case R.id.top_return:
			this.finish();
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQUESTCODE) {
			if (resultCode == Constants.RESULTCODE) {
				this.finish();
			}
		}
	}
}
