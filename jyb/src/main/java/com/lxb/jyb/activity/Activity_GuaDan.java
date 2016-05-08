package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

public class Activity_GuaDan extends Activity implements OnClickListener {

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
		setContentView(R.layout.activity_guadan);
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.top_msg).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.top_msg:
			startActivity(new Intent(Activity_GuaDan.this,
					Activity_CreateGD.class));
			break;
		case R.id.top_return:
			this.finish();
			break;
		}
	}
}
