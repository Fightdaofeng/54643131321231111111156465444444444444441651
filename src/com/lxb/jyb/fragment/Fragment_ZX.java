package com.lxb.jyb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lxb.jyb.R;

/**
 * 资讯
 * 
 * @author liuxiaobin
 *
 */
public class Fragment_ZX extends Fragment {
	private View view;
	private Button btn_tt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_zx, container, false);

		btn_tt = (Button) view.findViewById(R.id.btn_tt);
		btn_tt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		return view;
	}

}
