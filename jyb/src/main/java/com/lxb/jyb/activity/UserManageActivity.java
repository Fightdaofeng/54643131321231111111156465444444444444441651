package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserManageActivity extends Activity implements OnClickListener {
	private TextView top_btn, top_title, top;
	private LinearLayout top_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usermanage);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initFindView();
	}

	private void initFindView() {
		// TODO Auto-generated method stub
		top_btn = (TextView) findViewById(R.id.top_msg);
		top_btn.setVisibility(View.VISIBLE);
		top_btn.setBackgroundResource(R.drawable.lajitong);
		top_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_msg:
			startActivity(new Intent(UserManageActivity.this,
					UserInfoActivity.class));
			break;

		default:
			break;
		}
	}

}
