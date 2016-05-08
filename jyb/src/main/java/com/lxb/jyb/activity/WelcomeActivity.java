package com.lxb.jyb.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lxb.jyb.R;

public class WelcomeActivity extends Activity {
	private SharedPreferences sp;
	private boolean boolean1;
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		sp = getSharedPreferences("setup", Activity.MODE_PRIVATE);
		boolean1 = sp.getBoolean("isFirst", true);
		editor = sp.edit();
		initXWTitle();
		if (boolean1) {
			startActivity(new Intent(WelcomeActivity.this,
					ViewPagerDemoActivity.class));
			WelcomeActivity.this.finish();
		} else {
			startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
			WelcomeActivity.this.finish();
		}

	}

	private ArrayList<String> xwTitle;

	private void initXWTitle() {
		// TODO Auto-generated method stub
		if (boolean1) {
			xwTitle = new ArrayList<String>();
			xwTitle.add("订阅");
			xwTitle.add("推荐");
			xwTitle.add("头条");
			xwTitle.add("中国");
			xwTitle.add("美国");
			//
			// Set<String> newSet = new HashSet<String>();
			// newSet.addAll(xwTitle);

			editor.putString("xwdef", xwTitle.toString());
			editor.commit();
		}
	}

}
