package com.lxb.jyb.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.view.CalendarView;
import com.lxb.jyb.activity.view.MyHorizontalScrollView;
import com.lxb.jyb.tool.GetDate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarHomeFragment extends Fragment implements OnClickListener,
		OnTouchListener {
	private View view;
	// private TextView main_Date;// 右上角日历
	private LinearLayout top_lay;
	private String date;// 日期
	private int year, moth;
	private int[] day;// 天数组
	private String indexDay;
	private String[] titles;
	static String ARGUMENTS_DAY = "day";
	public static String ARGUMENTS_LIST = "list";
	private MyHorizontalScrollView hr;
	private int indicatorWidth;
	private ImageView iv_nav_indicator;
	private LinearLayout rl_nav;
	private LayoutInflater mInflater;
	private TabFragmentPagerAdapter mAdapter2;
	private RadioGroup rg_nav_content;
	private ViewPager mViewPager;
	private float currentIndicatorLeft;
	public static PopupWindow popupwindow;
	private int index;
	private String[] spls;
	public static Date curdate = new Date();
	private boolean isppShow = false;
	private int isFist = 1;
	private TextView rili_pp;

	// private TextView moni;

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_calendar, container, false);

		initData();
		initTitle();
		indexDay = date;
		initview();
		initHr();
		setListener();
		// handler.sendEmptyMessage(500);
		return view;
	}

	public void onPause() {
		super.onPause();
	}

	private void initTitle() {
		// TODO Auto-generated method stub
		day = GetDate.initDay(date);
		titles = new String[day.length];

		for (int i = 0; i < day.length; i++) {
			if (i < 9) {
				titles[i] = "0" + day[i] + "\n"
						+ GetDate.getWeekDay(year, moth, day[i]);
			} else {
				titles[i] = day[i] + "\n"
						+ GetDate.getWeekDay(year, moth, day[i]);
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// MobclickAgent.onPageStart("MainScreen"); //
		// 统计页面，"MainScreen"为页面名称，可自定义
		view.findViewById(R.id.rili_pp).setVisibility(View.VISIBLE);
		view.findViewById(R.id.shijian_pp).setVisibility(View.GONE);
		if (isFist == 1) {
			isFist = 2;
			setHr();
		}
	}

	private void setHr() {
		new Handler().postDelayed((new Runnable() {
			@Override
			public void run() {
				TranslateAnimation animation = new TranslateAnimation(
						currentIndicatorLeft, ((RadioButton) rg_nav_content
								.getChildAt(index - 1)).getLeft(), 0f, 0f);
				animation.setInterpolator(new LinearInterpolator());
				animation.setDuration(100);
				animation.setFillAfter(true);

				iv_nav_indicator.startAnimation(animation);
				mViewPager.setCurrentItem(index - 1);
				((RadioButton) rg_nav_content.getChildAt(index - 1))
						.performClick();
				hr.scrollTo(
						(index > 1 ? indicatorWidth * index : 0)
								- ((RadioButton) rg_nav_content.getChildAt(2))
										.getLeft(), 0);
			}
		}), 10);
		// 其中 ((RadioButton)rg_price.getChildAt(housePriceIndex)).getLeft()-100
		// toToday();
	}

	/**
	 * 设置事件监听
	 */
	private void setListener() {
		// TODO Auto-generated method stub
		// if (index > 1) {
		// mViewPager.setCurrentItem(index - 1);
		// }
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (rg_nav_content != null
						&& rg_nav_content.getChildCount() > position) {

					((RadioButton) rg_nav_content.getChildAt(position))
							.performClick();

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		rg_nav_content
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (rg_nav_content.getChildAt(checkedId) != null) {

							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							iv_nav_indicator.startAnimation(animation);

							mViewPager.setCurrentItem(checkedId); // ViewPager

							currentIndicatorLeft = ((RadioButton) rg_nav_content
									.getChildAt(checkedId)).getLeft();

							hr.smoothScrollTo(
									(checkedId > 1 ? ((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft()
											: 0)
											- ((RadioButton) rg_nav_content
													.getChildAt(2)).getLeft(),
									0);
						}
					}
				});
	}

	private void initHr() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		indicatorWidth = (dm.widthPixels / 9)+1;

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;//
		iv_nav_indicator.setLayoutParams(cursor_Params);
		hr.setParam(rl_nav, this.getActivity());

		String LAYOUT_INFLATER_SERVICE = "layout_inflater";
		mInflater = (LayoutInflater) this.getActivity().getSystemService(
				LAYOUT_INFLATER_SERVICE);

		// LayoutInflater mInflater = LayoutInflater.from(this);
		initNavigationHSV();

		mAdapter2 = new TabFragmentPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mAdapter2);
	}

	private void initNavigationHSV() {
		// TODO Auto-generated method stub

		for (int i = 0; i < day.length; i++) {

			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
			rb.setId(i);
//			Spannable spannable = new SpannableString(titles[i]);
//			spannable.setSpan(new AbsoluteSizeSpan(48), 0, 2,
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			rb.setText(titles[i]);
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));
			rg_nav_content.addView(rb);

		}

	}

	private void initview() {
		// moni = (TextView) view.findViewById(R.id.moni_jiaoyi);
		hr = (MyHorizontalScrollView) view.findViewById(R.id.mHsv);
		mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
		// main_Date.setText(moth + "");
		top_lay = (LinearLayout) getActivity().findViewById(R.id.top_lin);
		rg_nav_content = (RadioGroup) view.findViewById(R.id.rg_nav_content);
		iv_nav_indicator = (ImageView) view.findViewById(R.id.iv_nav_indicator);
		rili_pp = (TextView) view.findViewById(R.id.rili_pp);
		if (moth < 10) {
			rili_pp.setText("0" + moth + "月\n" + year + "年");
		} else {
			rili_pp.setText(moth + "月\n" + year + "年");
		}
		rili_pp.setOnClickListener(this);
		// main_Date.setOnClickListener(this);
		// main_today = (TextView) getActivity().findViewById(R.id.rili_Today);
		// main_today.setOnClickListener(this);
		// moni.setOnClickListener(this);
	}

	public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			switch (arg0) {

			case 0:
				ft = new CalendarFragment();
				Bundle args = new Bundle();
				if (day[arg0] < 9) {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-0" + (day[arg0]);

				} else {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-" + (day[arg0]);
				}
				// getJson(indexDay);
				args.putString(ARGUMENTS_DAY, indexDay);
				// new getJSONArray().execute("");
				// args.putParcelableArrayList(ARGUMENTS_LIST, mArrayList);

				ft.setArguments(args);
				break;

			default:
				ft = new CalendarFragment();

				Bundle args1 = new Bundle();
				if (day[arg0] < 10) {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-0" + (day[arg0]);
				} else {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-" + (day[arg0]);
				}
				// getJson(indexDay);
				args1.putString(ARGUMENTS_DAY, indexDay);
				// new getJSONArray().execute("");
				// args1.putParcelableArrayList(ARGUMENTS_LIST, mArrayList);

				ft.setArguments(args1);

				break;
			}

			return ft;
		}

		@Override
		public int getCount() {

			return day.length;
		}

	}

	public void onClick(View v) {
		// TODO 点击事件
		switch (v.getId()) {
		case R.id.rili_pp:
			initPopupWindow();
			break;
		}
	}

	private void toToday() {
		// TODO Auto-generated method stub
		if (isppShow) {
			initData();
			if (moth < 10) {
				rili_pp.setText("0" + moth + "月\n" + year + "年");
			} else {
				rili_pp.setText(moth + "月\n" + year + "年");
			}
			rg_nav_content.removeAllViews();
			initTitle();
			date.split("-");
			curdate = new Date();
			indexDay = date;
			initHr();
			setListener();
			setHr();
		} else {
			TranslateAnimation animation = new TranslateAnimation(
					currentIndicatorLeft,
					((RadioButton) rg_nav_content.getChildAt(index - 1))
							.getLeft(), 0f, 0f);
			animation.setInterpolator(new LinearInterpolator());
			animation.setDuration(100);
			animation.setFillAfter(true);

			iv_nav_indicator.startAnimation(animation);
			mViewPager.setCurrentItem(index - 1);
			((RadioButton) rg_nav_content.getChildAt(index - 1)).performClick();
			hr.scrollTo(
					(index > 1 ? ((RadioButton) rg_nav_content
							.getChildAt(index - 1)).getLeft() : 0)
							- ((RadioButton) rg_nav_content.getChildAt(2))
									.getLeft(), 0);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void initData() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 当前时间

		date = formatter.format(curDate);
		spls = date.split("-");
		year = Integer.parseInt(spls[0]);
		moth = Integer.parseInt(spls[1]);
		index = Integer.parseInt(spls[2]);

	}

	private void initPopupWindow() {
		// TODO Auto-generated method stub
		// if (popupwindow == null) {
		isppShow = true;
		ImageView imageView;
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pp = inflater.inflate(R.layout.calendar_card, null);
		imageView = (ImageView) pp.findViewById(R.id.today_img);
		popupwindow = new PopupWindow(pp, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.showAsDropDown(top_lay);
		pp.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupwindow.isShowing()) {
					popupwindow.dismiss();
					handler.sendEmptyMessage(100);
				}
				return false;
			}
		});
		pp.findViewById(R.id.clos_pp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				handler.sendEmptyMessage(100);
			}
		});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), "点击了按钮", 1).show();
				toToday();
				popupwindow.dismiss();
			}
		});
		// }
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				handler.sendEmptyMessage(100);
			}
			break;

		default:
			break;
		}
		return true;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:

				Date datestr = CalendarView.setDate;
				CalendarHomeFragment.curdate = datestr;
				if (datestr == null) {
					datestr = new Date(System.currentTimeMillis());
				}
				String conversionDate = GetDate.conversionDate(datestr);
				String[] split = conversionDate.split("-");
				int y = Integer.parseInt(split[0]);
				int m = Integer.parseInt(split[1]);
				int d = Integer.parseInt(split[2]);
				if (m == moth && y == year) {
					System.out.println(d + "--" + y + "---" + m);
					mViewPager.setCurrentItem(d - 1);
					((RadioButton) rg_nav_content.getChildAt(d - 1))
							.performClick();
					hr.scrollTo(
							(d > 1 ? ((RadioButton) rg_nav_content
									.getChildAt(d - 1)).getLeft() : 0)
									- ((RadioButton) rg_nav_content
											.getChildAt(2)).getLeft(), 0);
				} else {
					date = conversionDate;
					year = y;
					moth = m;
					if (moth < 10) {
						rili_pp.setText("0" + moth + "月\n" + year + "年");
					} else {
						rili_pp.setText(moth + "月\n" + year + "年");
					}
					rg_nav_content.removeAllViews();
					initTitle();
					index = d;
					indexDay = date;
					initHr();
					setListener();
				}
				break;
			default:
				break;
			}
		}
	};

}