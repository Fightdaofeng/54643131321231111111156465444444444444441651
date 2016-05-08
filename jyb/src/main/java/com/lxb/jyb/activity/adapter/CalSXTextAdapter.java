package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.CalSXItem;
import com.lxb.jyb.tool.MyClickListener;

public class CalSXTextAdapter extends BaseAdapter {

	private ArrayList<CalSXItem> list;
	private Context context;
	private MyClickListener clickListener;

	public CalSXTextAdapter(ArrayList<CalSXItem> list, Context context,
			MyClickListener clickListener) {
		super();
		this.list = list;
		this.context = context;
		this.clickListener = clickListener;
	}

	public ArrayList<CalSXItem> getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(ArrayList<String> list) {
		if (list != null) {
			this.list = (ArrayList<CalSXItem>) list.clone();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
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

	@SuppressLint("InflateParams")
	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		String str = list.get(position).getName();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.sxgrid_item, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(str);
		holder.tv.setTag(position);
		holder.tv.setOnClickListener(clickListener);
		if (list.get(position).getIsCheck()) {
			holder.tv.setSelected(true);
		} else {
			holder.tv.setSelected(false);
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv;
	}
}
