package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;
import java.util.Date;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.Finance;
import com.lxb.jyb.tool.DateUtil;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventLayoutAdapter extends BaseAdapter {
	private ArrayList<Finance> list;
	private Context context;

	public EventLayoutAdapter(ArrayList<Finance> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		Finance finance = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.event_listitem, null, false);
			holder.country = (ImageView) convertView
					.findViewById(R.id.event_country_img);
			holder.title = (TextView) convertView
					.findViewById(R.id.event_title);
			holder.importance = (TextView) convertView
					.findViewById(R.id.event_importance);
			holder.time = (TextView) convertView.findViewById(R.id.event_time);
			holder.status = (TextView) convertView
					.findViewById(R.id.event_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String eventImportance = finance.getEventImportance();
		String tv = "";
		switch (eventImportance) {
		case "mid":
			tv = "中";
			holder.importance.setText(tv);
			holder.importance.setTextColor(Color.RED);
			break;

		case "high":
			tv = "高";
			holder.importance.setText(tv);
			holder.importance.setTextColor(Color.RED);
			TextPaint paint = holder.importance.getPaint();
			paint.setFakeBoldText(true);
			break;
		case "low":
			tv = "低";
			holder.importance.setText(tv);
			holder.importance.setTextColor(Color.BLACK);
			break;
		}
		String eventTime = finance.getEventTime();
		String eventDate = finance.getEventDate();
		String dt = eventDate + " " + eventTime;
		Date datet = DateUtil.stringDate(dt);
		Date curDate = new Date(System.currentTimeMillis());// 当前时间

		if (datet.after(curDate)) {
			holder.status.setText("未公布");
		} else {
			holder.status.setText("已公布");
		}

		ImageAdapter.setImageView(finance.getEventCountry(), holder.country);
		holder.time.setText(finance.getEventTime().substring(0, 5));
		String eventContent = null;
		if (finance.getEventContent().length() == 1) {
			eventContent = finance.getEventTitle();
		} else {
			eventContent = finance.getEventContent();

		}
		System.out.println("时间长度" + eventContent.length());
		holder.title.setText(eventContent);

		// holder.status.setText(finance.getEventStatus());

		return convertView;
	}

	class ViewHolder {
		private ImageView country;
		private TextView title;
		private TextView importance;
		private TextView time;
		private TextView status;
	}

}
