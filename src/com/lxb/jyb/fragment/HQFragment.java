package com.lxb.jyb.fragment;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.HQSerchActivity;
import com.lxb.jyb.activity.view.MyHorizontalScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HQFragment extends Fragment implements OnClickListener {
	private static HQFragment hq = new HQFragment();

	public HQFragment() {
	}

	/**
	 * @return fragment_hq_hq
	 */
	public static HQFragment getInstance() {
		return hq;
	}

	private View view;
	private String[] titles;
	private ViewPager viewpager;
	private RelativeLayout hq_nav;
	private MyHorizontalScrollView mHsv;
	private RadioGroup hq_nav_content;
	private ImageView iv_nav_indicator;
	private int indicatorWidth;
	private LayoutInflater mInflater;
	public static final String ARGUMENTS_NAME = "title";
	private TabFragmentPagerAdapter mAdapter;
	private float currentIndicatorLeft = 0;
	private TextView serch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_hq, container, false);

		initTitle();

		findViewById();
		initView();
		setListener();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (hq_nav_content != null
						&& hq_nav_content.getChildCount() > position) {

					((RadioButton) hq_nav_content.getChildAt(position))
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

		hq_nav_content
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (hq_nav_content.getChildAt(checkedId) != null) {

							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) hq_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							iv_nav_indicator.startAnimation(animation);

							viewpager.setCurrentItem(checkedId); // ViewPager

							currentIndicatorLeft = ((RadioButton) hq_nav_content
									.getChildAt(checkedId)).getLeft();

							mHsv.smoothScrollTo(
									(checkedId > 1 ? ((RadioButton) hq_nav_content
											.getChildAt(checkedId)).getLeft()
											: 0)
											- ((RadioButton) hq_nav_content
													.getChildAt(2)).getLeft(),
									0);
						}
					}
				});
	}

	/**
	 * 初始化VIEW
	 */
	private void initView() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		indicatorWidth = (dm.widthPixels) / 5;

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;//
		iv_nav_indicator.setLayoutParams(cursor_Params);

		mHsv.setParam(hq_nav, this.getActivity());

		String LAYOUT_INFLATER_SERVICE = "layout_inflater";
		mInflater = (LayoutInflater) this.getActivity().getSystemService(
				LAYOUT_INFLATER_SERVICE);

		// LayoutInflater mInflater = LayoutInflater.from(this);

		initNavigationHSV();

		mAdapter = new TabFragmentPagerAdapter(getChildFragmentManager());
		viewpager.setAdapter(mAdapter);

	}

	private void initNavigationHSV() {
		// TODO Auto-generated method stub
		hq_nav_content.removeAllViews();

		for (int i = 0; i < titles.length; i++) {

			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.hq_nav_radiogroup_item, null);
			rb.setId(i);
			rb.setText(titles[i]);
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));
			hq_nav_content.addView(rb);
		}
	}

	/**
	 * 初始化控件
	 */
	private void findViewById() {
		// TODO Auto-generated method stub
		// leftImage = (ImageView) view.findViewById(R.id.leftimage);
		// rightImage = (ImageView) view.findViewById(R.id.rightimage);
		viewpager = (ViewPager) view.findViewById(R.id.mViewPager);
		view.findViewById(R.id.top_return).setVisibility(View.INVISIBLE);
		hq_nav = (RelativeLayout) view.findViewById(R.id.hq_nav);
		hq_nav_content = (RadioGroup) view.findViewById(R.id.hq_nav_content);
		iv_nav_indicator = (ImageView) view.findViewById(R.id.iv_nav_indicator);
		mHsv = (MyHorizontalScrollView) view.findViewById(R.id.mHsv);

		serch = (TextView) view.findViewById(R.id.top_txt);
		serch.setOnClickListener(this);
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
				ft = new ZiHQFragment();
				Bundle args = new Bundle();
				args.putString(ARGUMENTS_NAME, titles[arg0]);
				ft.setArguments(args);
				break;

			default:
				ft = new ZiHQFragment();

				Bundle args1 = new Bundle();
				args1.putString(ARGUMENTS_NAME, titles[arg0]);
				ft.setArguments(args1);

				break;
			}
			return ft;
		}

		@Override
		public int getCount() {

			return titles.length;
		}

	}

	/**
	 * 初始化标题数据
	 */
	private void initTitle() {
		// TODO Auto-generated method stub
		titles = new String[] { "自选", "外汇", "黄金", "COMEX", "原油", "商品", "全球股指",
				"上海金", "天通银", "伦敦金属" };
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_txt:
			startActivityForResult(new Intent(HQFragment.this.getActivity(),
					HQSerchActivity.class), 100);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 200) {
			int d = 0;
			viewpager.setCurrentItem(0);
			((RadioButton) hq_nav_content.getChildAt(0)).performClick();
			mHsv.scrollTo(
					(d > 1 ? ((RadioButton) hq_nav_content.getChildAt(0))
							.getLeft() : 0)
							- ((RadioButton) hq_nav_content.getChildAt(2))
									.getLeft(), 0);
		}
	}
}
