package com.lxb.jyb.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.view.CalendarView;
import com.lxb.jyb.activity.view.MyHorizontalScrollView;
import com.lxb.jyb.tool.GetDate;

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
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ShiJianFragment extends Fragment implements OnClickListener {
	private View view;
	// private TextView main_date;
	// main_Today;
	private LinearLayout top_lay;
	public static Date curdate = new Date();
	private int[] days;
	private TabFragmentPagerAdapter mAdapter;
	private LayoutInflater mInflater;
	private ViewPager mViewPager;
	private RadioGroup rg_nav_content;
	private int indicatorWidth;
	private ImageView iv_nav_indicator;// 横线
	private float currentIndicatorLeft;
	private MyHorizontalScrollView hr;
	private LinearLayout rl_nav;
	static String ARGUMENTS_DAY = "day";
	static String ARGUMENTS_LIST = "list";
	private PopupWindow popupwindow;
	private String date;
	private String[] titles;
	private int year, moth;
	private int index;
	private int isFist = 1;
	private boolean isppShow = false;
	private TextView shijian_pp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_calendar, container, false);

		initDate();
		initTitle();
		initFindViewById();
		// new FinanceTask().execute("");
		initHr();
		setListener();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.findViewById(R.id.rili_pp).setVisibility(View.GONE);
		view.findViewById(R.id.shijian_pp).setVisibility(View.VISIBLE);
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
		}), 5);
		// 其中 ((RadioButton)rg_price.getChildAt(housePriceIndex)).getLeft()-100
	}

	private void initFindViewById() {
		// TODO Auto-generated method stub

		// main_date = (TextView) getActivity().findViewById(R.id.shijian_Date);
		// main_date.setText(moth + "");
		getActivity().findViewById(R.id.shishaixuan)
		.setVisibility(View.VISIBLE);
getActivity().findViewById(R.id.rishaixuan).setVisibility(View.GONE);
getActivity().findViewById(R.id.zbshaixuan).setVisibility(View.GONE);
		top_lay = (LinearLayout) getActivity().findViewById(R.id.top_lin);
		hr = (MyHorizontalScrollView) view.findViewById(R.id.mHsv);
		mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);
		rg_nav_content = (RadioGroup) view.findViewById(R.id.rg_nav_content);
		iv_nav_indicator = (ImageView) view.findViewById(R.id.iv_nav_indicator);
		shijian_pp = (TextView) view.findViewById(R.id.shijian_pp);
		if (moth < 10) {
			shijian_pp.setText("0" + moth + "月\n" + year + "年");
		} else {
			shijian_pp.setText(moth + "月\n" + year + "年");
		}
		shijian_pp.setOnClickListener(this);
		// main_date.setOnClickListener(this);
		// main_Today = (TextView)
		// getActivity().findViewById(R.id.shijian_Today);
		// main_Today.setOnClickListener(this);
	}

	/**
	 * 设置事件监听
	 */
	private void setListener() {
		// TODO Auto-generated method stub
		// if (index > 1) {
		//
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

		indicatorWidth = dm.widthPixels / 8;

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;//
		iv_nav_indicator.setLayoutParams(cursor_Params);
		hr.setParam(rl_nav, this.getActivity());

		String LAYOUT_INFLATER_SERVICE = "layout_inflater";
		mInflater = (LayoutInflater) this.getActivity().getSystemService(
				LAYOUT_INFLATER_SERVICE);

		// LayoutInflater mInflater = LayoutInflater.from(this);

		initNavigationHSV();

		mAdapter = new TabFragmentPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mAdapter);
	}

	private void initNavigationHSV() {
		// TODO Auto-generated method stub
		// rg_nav_content.removeAllViews();

		for (int i = 0; i < days.length; i++) {

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

	public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

		private String indexDay;

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			switch (arg0) {
			case 0:
				ft = new EventFragment();
				Bundle args = new Bundle();
				if (days[arg0] < 9) {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-0" + (days[arg0]);
				} else {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-" + (days[arg0]);
				}
				args.putString(ARGUMENTS_DAY, indexDay);
				ft.setArguments(args);
				break;

			default:
				ft = new EventFragment();

				Bundle args1 = new Bundle();
				if (days[arg0] < 10) {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-0" + (days[arg0]);
				} else {
					String m = moth + "";
					if (moth < 10) {
						m = "0" + moth;
					}
					indexDay = year + "-" + m + "-" + (days[arg0]);
				}
				args1.putString(ARGUMENTS_DAY, indexDay);

				ft.setArguments(args1);

				break;
			}
			return ft;
		}

		@Override
		public int getCount() {

			return days.length;
		}

	}

	private void initTitle() {
		// TODO Auto-generated method stub
		days = GetDate.initDay(date);
		titles = new String[days.length];

		for (int i = 0; i < days.length; i++) {
			if (i < 9) {
				titles[i] = "0" + days[i] + "\n"
						+ GetDate.getWeekDay(year, moth, days[i]);
			} else {
				titles[i] = days[i] + "\n"
						+ GetDate.getWeekDay(year, moth, days[i]);
			}
		}
	}

	private void initDate() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 当前时间

		date = formatter.format(curDate);
		String[] split = date.split("-");
		year = Integer.parseInt(split[0]);
		moth = Integer.parseInt(split[1]);
		index = Integer.parseInt(split[2]);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shijian_pp:
			initPopupWindow();
			break;
		// case R.id.shijian_Today:
		//
		// break;
		default:
			break;
		}
	}

	private void toToday() {
		if (isppShow) {
			initDate();
			if (moth < 10) {
				shijian_pp.setText("0" + moth + "月\n" + year + "年");
			} else {
				shijian_pp.setText(moth + "月\n" + year + "年");
			}
			rg_nav_content.removeAllViews();
			initTitle();
			date.split("-");

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

	@SuppressWarnings("deprecation")
	private void initPopupWindow() {
		// TODO Auto-generated method stub
		// if (popupwindow == null) {
		isppShow = true;
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pp = inflater.inflate(R.layout.calendar_card, null);
		popupwindow = new PopupWindow(pp, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.showAsDropDown(top_lay);
		ImageView imageView = (ImageView) pp.findViewById(R.id.today_img);
		pp.setOnTouchListener(new OnTouchListener() {

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
				toToday();
				popupwindow.dismiss();
			}
		});
		// }
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:

				Date datestr = CalendarView.setDate;
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
						shijian_pp.setText("0" + moth + "月\n" + year + "年");
					} else {
						shijian_pp.setText(moth + "月\n" + year + "年");
					}
					rg_nav_content.removeAllViews();
					initTitle();
					index = d;
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
