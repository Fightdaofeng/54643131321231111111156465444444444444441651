package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.lxb.jyb.tool.DataCleanManager;
import com.lxb.jyb.util.SetStatiColor;

public class Activity_Setting extends Activity implements OnClickListener {
	private TextView top_title, huancun_tv;
	private String totalCacheSize;

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
		setContentView(R.layout.activity_setting);
		try {
			totalCacheSize = DataCleanManager.getTotalCacheSize(this);
			// Toast.makeText(this, totalCacheSize, 1).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initFind();
	}

	private void initFind() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
		findViewById(R.id.gywm_lay).setOnClickListener(this);
		findViewById(R.id.xgmm_lay).setOnClickListener(this);
		findViewById(R.id.tstz_lay).setOnClickListener(this);
		findViewById(R.id.hc_lay).setOnClickListener(this);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("设置");

		huancun_tv = (TextView) findViewById(R.id.apphuancun_tv);
		huancun_tv.setText(totalCacheSize);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;

		case R.id.gywm_lay:
			startActivity(new Intent(this, Activity_GYWM.class));
			break;
		case R.id.xgmm_lay:
			startActivity(new Intent(this, Activity_XGMM.class));
			break;
		case R.id.tstz_lay:
			startActivity(new Intent(this, Activity_TSTZ.class));
			break;
		case R.id.hc_lay:
			Toast.makeText(this, "正在清理缓存...", Toast.LENGTH_LONG).show();
			DataCleanManager.clearAllCache(this);
			try {
				totalCacheSize = DataCleanManager.getTotalCacheSize(this);
				// Toast.makeText(this, totalCacheSize, 1).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Toast.makeText(this, "缓存已清理", Toast.LENGTH_LONG).show();
			huancun_tv.setText(totalCacheSize);
			break;
		}
	}
}
