package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.NewListAdapter.ViewHolder;
import com.lxb.jyb.bean.JYPZBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JYPZitem_adapter extends BaseAdapter {
	private Context context;
	private ArrayList<JYPZBean> list;

	public JYPZitem_adapter(Context context, ArrayList<JYPZBean> parseExcel) {
		super();
		this.context = context;
		this.list = parseExcel;
	}

	public ArrayList<JYPZBean> getList() {
		return list;
	}

	public void setList(ArrayList<JYPZBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.jypz_item, null);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv1.setText(list.get(position).getName());
		holder.tv2.setText(list.get(position).getCode());

		return convertView;
	}

	class ViewHolder {
		private TextView tv1;
		private TextView tv2;
	}
}
