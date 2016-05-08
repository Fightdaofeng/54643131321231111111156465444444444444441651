package com.lxb.jyb.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.FragAdapter;
import com.lxb.jyb.bean.OrderSelect;
import com.lxb.jyb.fragment.Fragment_pc;
import com.lxb.jyb.fragment.Fragment_ydzs;
import com.lxb.jyb.fragment.Fragment_zd;
import com.lxb.jyb.fragment.Fragment_zy;
import com.lxb.jyb.tool.Accredit;
import com.lxb.jyb.util.Constants;
import com.lxb.jyb.util.SetStatiColor;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

/**
 * 订单界面
 * 
 * @author Liuxiaobin
 *
 */
public class OrderActivity extends FragmentActivity implements OnClickListener {
	private ViewPager viewpager;
	private View v1, v2, v3, v4;
	private LinearLayout top_return;
	private TextView zs_tv, zd_tv, ydzs_tv, pc_tv, top_msg;
	private FragAdapter adapter;
	private Accredit accredit;
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constants.DESCRIPTOR);
	private TextView top_ddcode, time_tv, order_symbol, order_type, order_num,
			order_yingli, order_buy, order_price, order_bzj;
	private OrderSelect orderbean;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		initGetIntent();

		initFindView();
		initViewPager();
		setTextViewBG(0);
	}

	private void initGetIntent() {
		// TODO Auto-generated method stub
		orderbean = getIntent().getParcelableExtra("bean");
	}

	/**
	 * 初始化viewpager
	 */
	private void initViewPager() {
		// TODO Auto-generated method stub
		// 构造适配器
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new Fragment_zd());
		fragments.add(new Fragment_zy());
		fragments.add(new Fragment_ydzs());
		fragments.add(new Fragment_pc());
		adapter = new FragAdapter(getSupportFragmentManager(), fragments);

		// 设定适配器
		viewpager = (ViewPager) findViewById(R.id.order_pager);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(listener);
		viewpager.setCurrentItem(0);
	}

	private void initFindView() {
		// TODO Auto-generated method stub
		zd_tv = (TextView) findViewById(R.id.zd_tv);
		zs_tv = (TextView) findViewById(R.id.zy_tv);
		ydzs_tv = (TextView) findViewById(R.id.ydzs_tv);
		pc_tv = (TextView) findViewById(R.id.pc_tv);

		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v3 = findViewById(R.id.v3);
		v4 = findViewById(R.id.v4);
		addClick();
		top_ddcode = (TextView) findViewById(R.id.top_ddcode);
		time_tv = (TextView) findViewById(R.id.time_tv);
		order_symbol = (TextView) findViewById(R.id.order_symbol);
		order_type = (TextView) findViewById(R.id.order_type);
		order_num = (TextView) findViewById(R.id.order_num);
		order_yingli = (TextView) findViewById(R.id.order_yingli);
		order_buy = (TextView) findViewById(R.id.order_buy);
		order_price = (TextView) findViewById(R.id.order_price);
		order_bzj = (TextView) findViewById(R.id.order_bzj);

		top_ddcode.setText(orderbean.getTicket());
		String opentime = orderbean.getOpentime();
		String replace = opentime.replace("T", " ");
		time_tv.setText("入仓时间：" + replace);
		order_symbol.setText(orderbean.getSymbol());
		String ordertype = orderbean.getOrdertype();
		if ("0".equals(ordertype)) {
			order_type.setText("做多");
			order_type.setBackgroundResource(R.drawable.hq_item_btn_red_shap);
		} else {
			order_type.setText("做空");
			order_type.setBackgroundResource(R.drawable.hq_item_btn_green_shap);
		}
		order_yingli.setText(orderbean.getProfit());

		String profit = orderbean.getProfit().substring(0, 1);
		if ("-".equals(profit)) {
			order_yingli.setTextColor(getResources().getColor(R.color.green));
		} else {
			order_yingli.setTextColor(getResources().getColor(R.color.hq_red));
		}
		order_num.setText(orderbean.getLots() + "手");
		order_buy.setText(orderbean.getOpenprice());
	}

	private void addClick() {
		// TODO Auto-generated method stub
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.top_msg).setOnClickListener(this);
		zd_tv.setOnClickListener(this);
		zs_tv.setOnClickListener(this);
		ydzs_tv.setOnClickListener(this);
		pc_tv.setOnClickListener(this);

	}

	OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			setTextViewBG(arg0);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void setTextViewBG(int arg) {
		zd_tv.setTextColor(Color.parseColor("#848484"));
		zs_tv.setTextColor(Color.parseColor("#848484"));
		ydzs_tv.setTextColor(Color.parseColor("#848484"));
		pc_tv.setTextColor(Color.parseColor("#848484"));
		v1.setVisibility(View.INVISIBLE);
		v2.setVisibility(View.INVISIBLE);
		v3.setVisibility(View.INVISIBLE);
		v4.setVisibility(View.INVISIBLE);
		switch (arg) {
		case 0:
			zd_tv.setTextColor(Color.parseColor("#01a4f2"));
			v1.setVisibility(View.VISIBLE);
			break;
		case 1:
			zs_tv.setTextColor(Color.parseColor("#01a4f2"));
			v2.setVisibility(View.VISIBLE);
			break;
		case 2:
			ydzs_tv.setTextColor(Color.parseColor("#01a4f2"));
			v3.setVisibility(View.VISIBLE);
			break;
		case 3:
			pc_tv.setTextColor(Color.parseColor("#01a4f2"));
			v4.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zd_tv:
			viewpager.setCurrentItem(0);
			setTextViewBG(0);
			break;
		case R.id.zy_tv:
			viewpager.setCurrentItem(1);
			setTextViewBG(1);
			break;
		case R.id.ydzs_tv:
			viewpager.setCurrentItem(2);
			setTextViewBG(2);
			break;
		case R.id.pc_tv:
			viewpager.setCurrentItem(3);
			setTextViewBG(3);
			break;
		case R.id.top_return:
			this.finish();
			break; 
		case R.id.top_msg:
			initFX(v);
			break;
		}
	}

	/**
	 * 初始化分享数据
	 * 
	 * @param v
	 */
	private void initFX(View v) {
		// TODO Auto-generated method stub
		System.out.println("进入");
		accredit = new Accredit(OrderActivity.this, mController);
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		accredit.addQQQZonePlatform();
		accredit.addWXPlatform();
		String title = "";
		String content = "";
		String url = "http://hq.fxgold.com";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
		// Locale.US);

		// String fname = "/sdcard/" + sdf.format(new Date()) + ".png";

		View view = v.getRootView();

		view.setDrawingCacheEnabled(true);

		view.buildDrawingCache();

		Bitmap bitmap = view.getDrawingCache();

		accredit.setShareContent(this, title, content, url, bitmap);
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
				SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN,
				SHARE_MEDIA.RENREN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS);
		mController.openShare(OrderActivity.this, false);
	}
}
