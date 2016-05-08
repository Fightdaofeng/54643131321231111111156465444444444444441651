package com.lxb.jyb.activity.adapter;

import java.util.List;

import android.content.Context;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.Bean;
import com.lxb.jyb.util.CommonAdapter;
import com.lxb.jyb.util.ViewHolder;

public class SearchAdapter extends CommonAdapter<Bean> {

	public SearchAdapter(Context context, List<Bean> data, int layoutId) {
		super(context, data, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, int position) {
		holder.setImageResource(R.id.item_search_iv_icon,
				mData.get(position).getIconId())
				.setText(R.id.item_search_tv_title,
						mData.get(position).getTitle())
				.setText(R.id.item_search_tv_content,
						mData.get(position).getContent())
				.setText(R.id.item_search_tv_comments,
						mData.get(position).getComments());
	}
}