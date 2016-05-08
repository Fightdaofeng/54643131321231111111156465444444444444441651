package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.NewsBean;

public class WebListViewAdapter extends BaseAdapter {
	private ArrayList<NewsBean> list;
	private ViewHolder holder;
	private Context context;

	public WebListViewAdapter(ArrayList<NewsBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size() > 0 ? 5 : 0;
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
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.web_list_item, null, false);

			holder.tv_title = (TextView) convertView
					.findViewById(R.id.xw_list_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_title.setText(list.get(position).getNewsTitle());
		return convertView;
	}

	class ViewHolder {
		private TextView tv_title;
	}
}
