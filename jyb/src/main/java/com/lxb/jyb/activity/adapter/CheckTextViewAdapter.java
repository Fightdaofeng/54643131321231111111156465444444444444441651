package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.NewsCategory;
import com.lxb.jyb.tool.MyClickListener;

public class CheckTextViewAdapter extends BaseAdapter {

	private ArrayList<NewsCategory> list;
	private Context context;
	private MyClickListener clickListener;
	private TextView textview;

	public CheckTextViewAdapter(ArrayList<NewsCategory> list, Context context,
			MyClickListener clickListener) {
		super();
		this.list = list;
		this.context = context;
		this.clickListener = clickListener;
	}

	public ArrayList<NewsCategory> getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(ArrayList<NewsCategory> list) {
		if (list != null) {
			this.list = (ArrayList<NewsCategory>) list.clone();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		textview = new TextView(context);
		textview.setText(" " + list.get(position).getNewsCategory());
		textview.setBackgroundResource(R.drawable.txtview_border);
		textview.setTag(position);
		textview.setGravity(Gravity.CENTER);
		// checkBox.setMaxLines(2);
		textview.setTextSize(12);
		textview.setTextColor(context.getResources().getColor(R.color.black));
		// checkBox.setScaleX(0.8f);
		// checkBox.setScaleY(0.8f);
		// checkBox.setButtonDrawable(R.drawable.check_selected);
		// if (list.get(position).getIsCheck()) {
		//
		// checkBox.setChecked(true);
		// } else {
		// }
		// checkBox.setTag(position);
		textview.setPadding(1, 15, 2, 15);
		textview.setOnClickListener(clickListener);

		return textview;
	}

}
