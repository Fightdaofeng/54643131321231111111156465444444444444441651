package com.lxb.jyb.activity.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.HQData;

public class HQItemAdapter extends BaseAdapter {
	private ArrayList<HQData> list;
	private Context context;
	private double changePercent;
	private double close;
	private double high;
	private double low;
	private double newPrice;
	private long time;
	private String name;
	private boolean isHy = false;
	private ListView listview;

	public ListView getListview() {
		return listview;
	}

	public void setListview(ListView listview) {
		this.listview = listview;
	}

	public boolean isHy() {
		return isHy;
	}

	public void setHy(boolean isHy) {
		this.isHy = isHy;
		notifyDataSetChanged();
	}

	public ArrayList<HQData> getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(ArrayList<HQData> list) {
		if (list != null) {
			this.list = (ArrayList<HQData>) list.clone();
		}
		// notifyDataSetChanged();
	}

	public void setListItem(int i, HQData data) {
		if (list != null) {
			list.set(i, data);
			notifyDataSetChanged();
		}
	}

	private int checkStati = 8;

	public HQItemAdapter(ArrayList<HQData> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		// 实例化SharedPreferences对象（第一步）
		// this.sp = context.getSharedPreferences("hqdata",
		// Activity.MODE_PRIVATE);
		// editor = this.sp.edit();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != list) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position < list.size()) {
			return list.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HQData data = list.get(position);
		holder = new ViewHolder();
		if (data != null) {
			if (null == convertView) {

				convertView = LayoutInflater.from(context).inflate(
						R.layout.hq_listitem, null);
				holder.cBx = (TextView) convertView.findViewById(R.id.cBox);
				holder.item_Name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.xianjia = (TextView) convertView
						.findViewById(R.id.xianjia);
				holder.zuigaojia = (TextView) convertView
						.findViewById(R.id.zuigaojia);
				holder.zhangdie = (TextView) convertView
						.findViewById(R.id.zhangdie);
				holder.zuidijia = (TextView) convertView
						.findViewById(R.id.zuidijia);
				holder.static_icon = (ImageView) convertView
						.findViewById(R.id.static_icon);
				holder.static_icon2 = (ImageView) convertView
						.findViewById(R.id.static_icon2);
				holder.st_lay = (LinearLayout) convertView
						.findViewById(R.id.st_lay);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			newPrice = data.getNewPrice();

			name = data.getName();
			changePercent = data.getChangePercent();
			time = data.getTime();
			close = data.getClose();
			high = data.getHight();
			low = data.getLow();

			boolean isnull = false;
			if (newPrice == 0 || newPrice == 0.0) {
				isnull = true;
			}

			int bl = data.getBaoliu();
			DecimalFormat df = null;
			if (bl == 10000) {
				df = new DecimalFormat("0.0000");
			} else {
				df = new DecimalFormat("0.00");
			}
			// 涨跌
			String format = String.format("%f", changePercent);

			double dob = Double.parseDouble(format);
			double zx = dob * 100;

			DecimalFormat zdf = null;
			zdf = new DecimalFormat("0.00");
			String changeP = zdf.format(zx);

			// name
			holder.item_Name.setText(name);
			// updata time
			// String substring = time.substring(0, time.length() - 3);
			if (time != 0) {
				Date date = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String format2 = sdf.format(date);
			}
			Resources resources = context.getResources();
			// 最新价
			if (isnull) {
				holder.xianjia.setText("— —");
				holder.xianjia.setTextColor(resources.getColor(R.color.black));
				holder.zuidijia.setText("— —");
				holder.zuidijia.setTextColor(resources.getColor(R.color.black));
				holder.zhangdie.setText("— —");
				holder.zhangdie.setTextColor(resources.getColor(R.color.black));
				holder.zuigaojia.setText("— —");
				holder.zuigaojia
						.setTextColor(resources.getColor(R.color.black));
				holder.static_icon.setVisibility(View.GONE);
			} else {
				String newP = df.format(newPrice);
				holder.xianjia.setText(newP + "");
				// 先获取sp中的数据
				// float float1 = sp.getFloat(data.getCode(), 0);
				// float parseFloat = Float.parseFloat(newP);
				// if (float1 == parseFloat) {
				// checkStati = 8;
				// } else {
				// // 用putString的方法保存数据
				// editor.putFloat(data.getCode(), parseFloat);
				checkStati = checkStati(newPrice, close);
				// }
				switch (checkStati(newPrice, close)) {
				case 0:
					holder.xianjia.setTextColor(resources
							.getColor(R.color.green));
					holder.static_icon.setVisibility(View.GONE);
					holder.static_icon2.setVisibility(View.VISIBLE);
					holder.static_icon2.setBackground(context.getResources()
							.getDrawable(R.drawable.hqlvsj));
					holder.zhangdie.setText(changeP + "%");
					holder.zhangdie.setTextColor(resources
							.getColor(R.color.green));
					break;
				case 1:
					// 平，设灰色
					holder.xianjia.setTextColor(resources
							.getColor(R.color.black));
					holder.zhangdie.setText(changeP + "%");
					holder.zhangdie.setTextColor(resources
							.getColor(R.color.black));
					holder.static_icon.setVisibility(View.GONE);
					holder.static_icon2.setVisibility(View.GONE);
					break;
				case 2:
					holder.xianjia.setTextColor(resources
							.getColor(R.color.hq_red));
					holder.static_icon2.setVisibility(View.GONE);
					holder.static_icon.setVisibility(View.VISIBLE);
					holder.static_icon.setBackground(context.getResources()
							.getDrawable(R.drawable.hqhsj));
					holder.zhangdie.setText("+" + changeP + "%");
					holder.zhangdie.setTextColor(resources
							.getColor(R.color.hq_red));
					break;
				}

				// 最低价
				String lowStr = df.format(low);
				holder.zuidijia.setText(lowStr);

				String highStr = df.format(high);
				holder.zuigaojia.setText(highStr + "");

				holder.st_lay.setBackground(null);
				if (!isHy && "new".equals(data.getGflags())) {
					changeBackgroude(data, holder, checkStati);
				}
			}
		}

		return convertView;

	}

	public void changeBackgroude(final HQData data, final ViewHolder holder,
			final int swing) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {

			@SuppressLint("NewApi")
			@Override
			public void run() {
				/**
				 * 数据刷新跳动
				 */
				switch (swing) {
				case 0:
					// 跌了绿色
					holder.st_lay.setBackground(context.getResources()
							.getDrawable(R.drawable.hq_item_btn_green_shap));
//					holder.static_icon2.setBackground(context.getResources()
//							.getDrawable(R.drawable.baisanjiaoupdown));
					holder.xianjia.setTextColor(context.getResources()
							.getColor(R.color.white));
					break;
				case 1:
					// 平
					break;
				case 2:
					// 涨了红色
					holder.st_lay.setBackground(context.getResources()
							.getDrawable(R.drawable.hq_item_btn_red_shap));
//					holder.static_icon.setBackground(context.getResources()
//							.getDrawable(R.drawable.baisanjiaoup));
					holder.xianjia.setTextColor(context.getResources()
							.getColor(R.color.white));
					break;

				}

			}

		});

	}

	private int checkStati(double x, double y) {

		if (x < y) {
			// 跌了，绿色
			return 0;
		} else if (x == y) {
			// 平了，灰色
			return 1;
		} else {
			// 涨了，红色
			return 2;
		}
	}

	private int index = -2;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void isHybg() {
		holder.st_lay.setBackgroundColor(Color.TRANSPARENT);
	}

	// 所以我们只需要将半角字符转换为全角字符即可，方法如下
	public String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}

	private ViewHolder holder;

	class ViewHolder {

		private TextView item_Name;
		private TextView xianjia;
		private TextView zuigaojia;
		private ImageView static_icon;
		private ImageView static_icon2;
		private TextView zhangdie;
		private TextView zuidijia;
		private TextView cBx;
		private LinearLayout st_lay;
	}
}
