package com.lxb.jyb.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.view.SetTimeWheelView;
import com.lxb.jyb.push.Tip;

public class SetTimeActivity extends Activity implements OnClickListener {
	private static final String[] PLANETS = new String[] { "即时", "提前5分钟",
			"提前10分钟", "提前15分钟", "提前20分钟", "提前25分钟" };
	private SetTimeWheelView wva;
	private int index;
	private String stringExtra;
	private TextView set_timer;
	private long timeInMillis;
	private String format;
	private String id;
	private String value = "";
	private String period;
	private String lastValue;
	private String predictValue;
	private String country;
	private String name;
	private String day;
	private boolean isSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settimeactivity);
		iniTime();
		initFind();
		// initJPush();
	}

	private void iniTime() {
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		format = stringExtra = intent.getStringExtra("time");
		id = intent.getStringExtra("id");
		value = intent.getStringExtra("value");
		period = intent.getStringExtra("period");
		day = intent.getStringExtra("day");
		name = intent.getStringExtra("name");
		lastValue = intent.getStringExtra("lastValue");
		predictValue = intent.getStringExtra("predictValue");
		country = intent.getStringExtra("country");

		Calendar c = Calendar.getInstance();

		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(stringExtra));
			timeInMillis = c.getTimeInMillis();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initFind() {
		// TODO Auto-generated method stub
		wva = (SetTimeWheelView) findViewById(R.id.main_wv);
		wva.setOffset(1);
		wva.setItems(Arrays.asList(PLANETS));
		wva.setOnWheelViewListener(new SetTimeWheelView.OnWheelViewListener() {

			@Override
			public void onSelected(int selectedIndex, String item) {
				Log.d("当前选择了", "selectedIndex: " + selectedIndex + ", item: "
						+ item);
				index = selectedIndex;
				if (index > 0) {
					int t = (index - 1) * 5;
					long stTime = t * 60 * 1000;
					long tiXTime = timeInMillis - stTime;

					Date date = new Date(tiXTime);

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					format = sdf.format(date);
					set_timer.setText(format);
				}
			}
		});
		findViewById(R.id.set_btn).setOnClickListener(this);
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.set_act).setBackgroundColor(
				Color.parseColor("#0075CF"));

		set_timer = (TextView) findViewById(R.id.set_timer);
		set_timer.setText(stringExtra);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_btn:
			isSet = true;
			handler.sendEmptyMessage(100);

			break;
		case R.id.top_return:
//			if ("".equals(format) || format == null) {
//				format = stringExtra;
//			}
//			Intent intent = new Intent();
//			intent.putExtra("time", format);
//			SetTimeActivity.this.setResult(200, intent);
			this.finish();

			break;
		default:
			break;
		}

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				set_timer.setText(format);
				if ("".equals(format) || format == null) {
					format = stringExtra;
				}
				Intent intent = new Intent();
				intent.putExtra("time", format);
				SetTimeActivity.this.setResult(200, intent);
				if (isSet) {
					SharedPreferences mySharedPreferences = getSharedPreferences(
							"config", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString(id + day, format.substring(11, 16));
					editor.commit();
				}
				initTip();
				finish();
				break;
			case 200:

				break;
			default:
				break;
			}
		}

	};

	private void initTip() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences("config",
				Activity.MODE_PRIVATE);
		String string = sp.getString("phone", "1");
		SharedPreferences sp1 = getSharedPreferences(string + "tipdata",
				Activity.MODE_PRIVATE);
		Editor editor = sp1.edit();
		Set<String> set = sp1.getStringSet("tipid", null);
		if (set != null) {
			ArrayList<String> ids = new ArrayList<String>();
			ids.addAll(set);
			ids.add(id);
			Set<String> newSet = new HashSet<String>();
			newSet.addAll(ids);
			editor.putStringSet("tipid", newSet);
			editor.commit();
		} else {
			Set<String> ls = new HashSet<String>();
			ls.add(id);
			editor.putStringSet("tipid", ls);
			editor.commit();
		}
		SharedPreferences sp2 = getSharedPreferences(string + "tippush",
				Activity.MODE_PRIVATE);
		Editor editor2 = sp2.edit();
		Tip tip = new Tip("日历推送", id, value, format, period, country, name,
				predictValue, lastValue);
		editor2.putString(id, tip.toJson());
		editor2.commit();

	}

//	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//			isSet = false;
//			handler.sendEmptyMessage(100);
//			return false;
//		}
//		return super.onKeyDown(keyCode, event);
//
//	};

}
