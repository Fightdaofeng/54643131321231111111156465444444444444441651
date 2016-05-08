package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.fragment.Fragment_GR;
import com.lxb.jyb.tool.Soft;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class Activity_LOGON extends Activity implements OnClickListener {
	private boolean isKj = false;
	private EditText pas_dit;
	private ImageView yanjing;

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
		setContentView(R.layout.activity_logon);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.wangjimima).setOnClickListener(this);
		findViewById(R.id.to_login).setOnClickListener(this);
		findViewById(R.id.logon_btn).setOnClickListener(this);
		findViewById(R.id.top_return).setOnClickListener(this);
		pas_dit = (EditText) findViewById(R.id.pas_dit);
		findViewById(R.id.yj_k).setOnClickListener(this);
		yanjing = (ImageView) findViewById(R.id.yj_img);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wangjimima:
			startActivity(new Intent(this, Activity_ZHMM.class));
			break;

		case R.id.to_login:
			startActivity(new Intent(this, Activity_LOGIN.class));
			break;
		case R.id.logon_btn:
			this.setResult(200);
			this.finish();
			break;
		case R.id.top_return:
			this.finish();
			break;
		case R.id.yj_k:
			if (isKj) {
				isKj = false;
				yanjing.setSelected(false);
				pas_dit.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			} else {
				isKj = true;
				yanjing.setSelected(true);
				pas_dit.setInputType(InputType.TYPE_CLASS_TEXT);
			}
			Soft.setEditTextCursorLocation(pas_dit);
			break;
		}
	}
}
