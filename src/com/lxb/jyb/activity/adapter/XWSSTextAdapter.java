package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.CalSXItem;
import com.lxb.jyb.tool.MyClickListener;

public class XWSSTextAdapter extends BaseAdapter {

	private ArrayList<String> list;
	private Context context;
	private MyClickListener clickListener;

	public XWSSTextAdapter(ArrayList<String> list, Context context,
			MyClickListener clickListener) {
		super();
		this.list = list;
		this.context = context;
		this.clickListener = clickListener;
	}

	public ArrayList<String> getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(ArrayList<String> list) {
		if (list != null) {
			this.list = (ArrayList<String>) list.clone();
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		String str = list.get(position);
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
		holder.tv.setTextSize(15);
		holder.tv.setTextColor(Color.parseColor("#9e9e9e"));
		holder.tv.setBackgroundResource(R.drawable.xukuang);
		holder.tv.setOnClickListener(clickListener);
		return convertView;
	}

	class ViewHolder {
		private TextView tv;
	}
}
