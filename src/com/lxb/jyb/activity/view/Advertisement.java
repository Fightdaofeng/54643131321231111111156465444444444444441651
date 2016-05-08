package com.lxb.jyb.activity.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.AdvertisementAdapter;
import com.lxb.jyb.tool.MyClickListener;

public class Advertisement implements OnPageChangeListener {

	private ViewPager pager;
	private Context context;
	private LayoutInflater inflater;
	private boolean fitXY;
	private int timeDratiation;
	private MyClickListener clickListener;
	List<View> views;
	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	Timer timer;
	TimerTask task;
	int count = 0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0x01:
				int currentPage = (Integer) msg.obj;
				setCurrentDot(currentPage);
				pager.setCurrentItem(currentPage);
				break;

			default:
				break;
			}

		};
	};

	public Advertisement(Context context, boolean fitXY,
			LayoutInflater inflater, int timeDratiation) {
		this.context = context;
		this.inflater = inflater;
		this.fitXY = fitXY;
		this.timeDratiation = timeDratiation;
	}

	public View initView(final ArrayList<String> jsonArray) {
		View view = inflater.inflate(R.layout.advertisement_board, null);

		pager = (ViewPager) view.findViewById(R.id.vpAdvertise);
		pager.setOnPageChangeListener(this);
		pager.setOnClickListener(clickListener);
		views = new ArrayList<View>();
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);

		for (int i = 0; i < jsonArray.size(); i++) {
			if (fitXY) {
				views.add(inflater.inflate(R.layout.advertisement_item_fitxy,
						null));
			} else {
				views.add(inflater.inflate(
						R.layout.advertisement_item_fitcenter, null));
			}
			ll.addView(inflater.inflate(R.layout.advertisement_board_dot, null));
		}
		initDots(ll, view);

		AdvertisementAdapter adapter = new AdvertisementAdapter(context, views,
				jsonArray);
		pager.setOffscreenPageLimit(3);
		pager.setAdapter(adapter);

		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				int currentPage = count % jsonArray.size();
				count++;
				Message msg = Message.obtain();
				msg.what = 0x01;
				msg.obj = currentPage;
				handler.sendMessage(msg);
			}
		};
		timer.schedule(task, 0, timeDratiation);
		return view;
	}

	private void initDots(LinearLayout ll, View view) {
		// TODO Auto-generated method stub

		dots = new ImageView[views.size()];
		System.out.println("图片数量+" + views.size());

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为黄色，即选中状态
	}

	private void setCurrentDot(int position) {
		// TODO Auto-generated method stub
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		count = position;
		setCurrentDot(position);
	}

}
