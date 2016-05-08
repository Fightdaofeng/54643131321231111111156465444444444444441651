package com.lxb.jyb.fragment;

import com.lxb.jyb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 移动止损
 * 
 * @author liuxiaobin
 *
 */
public class Fragment_ydzs extends Fragment implements OnClickListener {
	private View view;
	private ImageView switch2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_ydzs, null, false);
		initFind();
		return view;
	}

	private void initFind() {
		// TODO Auto-generated method stub
		switch2 = (ImageView) view.findViewById(R.id.switch2);
		switch2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.switch2:
			if (switch2.isSelected()) {
				switch2.setSelected(false);
				view.findViewById(R.id.set_ydzs_lay).setVisibility(View.GONE);
			} else {
				switch2.setSelected(true);
				view.findViewById(R.id.set_ydzs_lay).setVisibility(View.VISIBLE);
			}
			break;
		}
	}

}
