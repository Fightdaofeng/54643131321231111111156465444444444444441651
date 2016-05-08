package com.lxb.jyb.fragment;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.Activity_LOGON;
import com.lxb.jyb.activity.Activity_Setting;
import com.lxb.jyb.activity.Activity_WDSC;
import com.lxb.jyb.activity.Activity_YJFK;
import com.lxb.jyb.tool.NetworkCenter;
import com.lxb.jyb.util.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 个人
 * 
 * @author Liuxiaobin
 *
 */
public class Fragment_GR extends Fragment implements OnClickListener {
	private View view;

	private TextView to_logon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_geren, container, false);
		initFind();
		return view;
	}

	private void initFind() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.set_lay).setOnClickListener(this);
		view.findViewById(R.id.yjfk_lay).setOnClickListener(this);
		view.findViewById(R.id.wdsc_lay).setOnClickListener(this);
		to_logon = (TextView) view.findViewById(R.id.to_logon);
		to_logon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_lay:
			startActivity(new Intent(Fragment_GR.this.getActivity(),
					Activity_Setting.class));
			break;

		case R.id.yjfk_lay:
			startActivity(new Intent(Fragment_GR.this.getActivity(),
					Activity_YJFK.class));
			break;
		case R.id.wdsc_lay:
			startActivity(new Intent(Fragment_GR.this.getActivity(),
					Activity_WDSC.class));
			break;
		case R.id.to_logon:
			startActivityForResult(new Intent(Fragment_GR.this.getActivity(),
					Activity_LOGON.class), Constants.REQUESTCODE);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQUESTCODE) {
			if (resultCode == Constants.RESULTCODE) {
				to_logon.setVisibility(View.GONE);
				view.findViewById(R.id.head_lay).setVisibility(View.VISIBLE);
			}
		}
	}

}
