package com.lxb.jyb.activity;

import com.lxb.jyb.R;
import com.lxb.jyb.util.SetStatiColor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_TSTZ extends Activity implements OnClickListener {
	private ImageView switch_all, switch_celue, switch_gendan, switch_sixin,
			switch_zhfx, switch_zxts, switch_pinglun, switch_yejian;
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
		setContentView(R.layout.activity_tuisongtongzhi);
		initFind();
		initSel();
	}

	private void initSel() {
		// TODO Auto-generated method stub
		switch_all.setSelected(true);
		switch_celue.setSelected(true);
		switch_gendan.setSelected(true);
		switch_sixin.setSelected(true);
		switch_zhfx.setSelected(true);
		switch_zxts.setSelected(true);
		switch_pinglun.setSelected(true);
	}

	private void initFind() {
		// TODO Auto-generated method stub
		switch_all = (ImageView) findViewById(R.id.switch_all);
		switch_celue = (ImageView) findViewById(R.id.switch_celue);
		switch_gendan = (ImageView) findViewById(R.id.switch_gendan);
		switch_sixin = (ImageView) findViewById(R.id.switch_sixin);
		switch_zhfx = (ImageView) findViewById(R.id.switch_zhfx);
		switch_zxts = (ImageView) findViewById(R.id.switch_zxts);
		switch_pinglun = (ImageView) findViewById(R.id.switch_pinglun);
		switch_yejian = (ImageView) findViewById(R.id.switch_yejian);
		findViewById(R.id.top_return).setOnClickListener(this);
		top_title = (TextView) findViewById(R.id.top_title);
		top_title.setText("推送通知");
		findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);

		switch_all.setOnClickListener(this);
		switch_celue.setOnClickListener(this);
		switch_gendan.setOnClickListener(this);
		switch_sixin.setOnClickListener(this);
		switch_zhfx.setOnClickListener(this);
		switch_zxts.setOnClickListener(this);
		switch_pinglun.setOnClickListener(this);
		switch_yejian.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;
		case R.id.switch_all:
			initSel();
			break;
		case R.id.switch_celue:
			if (switch_celue.isSelected()) {
				switch_celue.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_celue.setSelected(true);
			}
			break;
		case R.id.switch_gendan:
			if (switch_gendan.isSelected()) {
				switch_gendan.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_gendan.setSelected(true);
			}
			break;
		case R.id.switch_sixin:
			if (switch_sixin.isSelected()) {
				switch_sixin.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_sixin.setSelected(true);
			}
			break;
		case R.id.switch_zhfx:
			if (switch_zhfx.isSelected()) {
				switch_zhfx.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_zhfx.setSelected(true);
			}
			break;
		case R.id.switch_zxts:
			if (switch_zxts.isSelected()) {
				switch_zxts.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_zxts.setSelected(true);
			}
			break;
		case R.id.switch_pinglun:
			if (switch_pinglun.isSelected()) {
				switch_pinglun.setSelected(false);
				switch_all.setSelected(false);
			} else {
				switch_pinglun.setSelected(true);
			}
			break;
		case R.id.switch_yejian:
			if (switch_yejian.isSelected()) {
				switch_yejian.setSelected(false);
			} else {
				switch_yejian.setSelected(true);
			}
			break;
		}
	}
}
