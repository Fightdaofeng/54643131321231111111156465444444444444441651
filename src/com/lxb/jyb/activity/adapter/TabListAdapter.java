package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.DataPoint;

public class TabListAdapter extends BaseAdapter {
	private ArrayList<DataPoint> list;
	private Context context;

	public TabListAdapter(ArrayList<DataPoint> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			HolderView holderView;
			DataPoint point = list.get(position);
			if (null == convertView) {
				holderView = new HolderView();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.tablist_item, null, false);

				holderView.moth_txt = (TextView) convertView
						.findViewById(R.id.moth_txt);
				holderView.data_txt = (TextView) convertView
						.findViewById(R.id.data_txt);
				convertView.setTag(holderView);
			} else {
				holderView = (HolderView) convertView.getTag();
			}
			holderView.moth_txt.setText(point.getDate());
			holderView.data_txt.setText(point.getValue());

		return convertView;
	}

	class HolderView {
		private TextView data_txt;
		private TextView moth_txt;
	}
}
