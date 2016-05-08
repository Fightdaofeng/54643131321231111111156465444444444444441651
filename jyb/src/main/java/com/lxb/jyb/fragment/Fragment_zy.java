package com.lxb.jyb.fragment;

import com.lxb.jyb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 止盈止损
 * 
 * @author liuxiaobin
 *
 */
public class Fragment_zy extends Fragment implements OnClickListener {
	private View view;
	private ImageView switch1, switch2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_zyzs, null, false);
		initFind();
		return view;
	}

	private void initFind() {
		// TODO Auto-generated method stub
		switch1 = (ImageView) view.findViewById(R.id.switch1);
		switch2 = (ImageView) view.findViewById(R.id.switch2);
		
		switch1.setOnClickListener(this);
		switch2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.switch1:
			if (switch1.isSelected()) {
				switch1.setSelected(false);
				view.findViewById(R.id.set_1).setVisibility(View.GONE);
			} else {
				switch1.setSelected(true);
				view.findViewById(R.id.set_1).setVisibility(View.VISIBLE);
			}
			break;

		case R.id.switch2:
			if (switch2.isSelected()) {
				switch2.setSelected(false);
				view.findViewById(R.id.set_2).setVisibility(View.GONE);
			} else {
				switch2.setSelected(true);
				view.findViewById(R.id.set_2).setVisibility(View.VISIBLE);
			}
			break;
		}
	}

}
