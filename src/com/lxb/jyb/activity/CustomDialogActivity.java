package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lxb.jyb.R;

public class CustomDialogActivity extends Activity {
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip_dialog);
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub
		id = getIntent().getStringExtra("id");
		String title = getIntent().getStringExtra("title");
		Button btnDgConfirm = (Button) findViewById(R.id.btn_dg_confirm);
		Button btnDgCancel = (Button) findViewById(R.id.btn_dg_cancel);
		TextView tvMessage = (TextView) findViewById(R.id.tv_message);
		tvMessage.setText(title);
		btnDgConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogActivity.this.finish();
				Intent intent = new Intent(CustomDialogActivity.this,
						XiangQingActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
		btnDgCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogActivity.this.finish();
			}
		});
	}

}
