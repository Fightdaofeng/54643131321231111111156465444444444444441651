package com.lxb.jyb.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.tool.Accredit;
import com.lxb.jyb.util.Constants;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class Directdirec_ParticularstActivity extends Activity implements
		OnClickListener {
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constants.DESCRIPTOR);
	private LinearLayout layout;
	private TextView date;
	private TextView time;
	private TextView title;
	private ImageView text;
	private String ZhiDate;
	private String ZhiTime;
	private String ZhiTitle;
	private String ZhiText;
	private ImageView fenxiang;
	private Accredit accredit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_directdirec__particularst);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		// id = getIntent().getStringExtra("LiveImportance");
		initview();
		// requestData();
		value();
	}

	private void initview() {
		// TODO Auto-generated method stub
		layout = (LinearLayout) findViewById(R.id.two_zhibo);
		date = (TextView) findViewById(R.id.two_date);
		time = (TextView) findViewById(R.id.two_time);
		title = (TextView) findViewById(R.id.two_title);
		text = (ImageView) findViewById(R.id.two_text);
		fenxiang = (ImageView) findViewById(R.id.zhibo_shuaxin);
		findViewById(R.id.top_return).setOnClickListener(this);
		fenxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accredit = new Accredit(Directdirec_ParticularstActivity.this,
						mController);

				mController.getConfig().setSsoHandler(new SinaSsoHandler());
				mController.getConfig()
						.setSsoHandler(new TencentWBSsoHandler());
				accredit.addQQQZonePlatform();
				accredit.addWXPlatform();
				// accredit.setShareContent("金汇财经", mItemList.get(tag).getUrl(),
				// "财经资讯", mController, "");
				String title = ZhiTitle;
				String content = ZhiText;
				String url = "http://zhibo.fxgold.com";
				View view = v.getRootView();

				view.setDrawingCacheEnabled(true);

				view.buildDrawingCache();

				Bitmap bitmap = view.getDrawingCache();
				accredit.setShareContent(Directdirec_ParticularstActivity.this,
						title, content, url, bitmap);
				mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
						SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA,
						SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN,
						SHARE_MEDIA.RENREN);
				mController.openShare(Directdirec_ParticularstActivity.this,
						false);
			}
		});
	}

	private void value() {
		// TODO Auto-generated method stub
		ZhiDate = getIntent().getStringExtra("Date");
		ZhiTime = getIntent().getStringExtra("Time");
		ZhiTitle = getIntent().getStringExtra("Title");
		ZhiText = getIntent().getStringExtra("Title");
		// System.out.println("Date++++++="+ZhiDate);
		// Toast.makeText(Directdirec_ParticularstActivity.this,ZhiDate ,
		// 1).show();
		date.setText(ZhiDate);
		time.setText(ZhiTime);
		String[] ztitle = ZhiTitle.split("<br />");
		String t = "";
		if (!"".equals(ztitle)) {
			for (int i = 0; i < ztitle.length; i++) {
				t = t + ztitle[i] + "\n";
			}
		}
		title.setText(t);
		if (!"".equals(ZhiText)) {
			Bitmap bitmap = getHttpBitmap(HttpConstant.NEWS_HOST);
			text.setImageBitmap(bitmap);
		} else {
			text.setVisibility(View.GONE);
		}
	}

	// private void requestData() {
	// // TODO Auto-generated method stub
	// HttpUtils utils = new HttpUtils();
	// RequestParams params = new RequestParams();
	// params.addBodyParameter("LiveImportance", id);
	// utils.send(HttpMethod.POST, HttpConstant.NEWS_HOST, params,
	// new RequestCallBack<String>() {
	// @Override
	// public void onFailure(HttpException arg0, String arg1) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(),"网络失败",
	// 0).show();
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> arg0) {
	// // TODO Auto-generated method stub
	// BitmapUtils utilsl = new BitmapUtils(
	// Directdirec_ParticularstActivity.this);
	//
	// try {
	// Toast.makeText(getApplicationContext(),"ok",
	// 0).show();
	// JSONArray array = new JSONArray(arg0.result);
	// for (int i = 0; i < array.length(); i++) {
	// JSONObject jb = array.getJSONObject(i);
	// if (id.equals(array.getJSONObject(i).getString(
	// "LiveImportance"))) {
	// date.setText(jb.getString("LiveDate"));
	// time.setText(jb.getString("LiveTime"));
	// title.setText(jb.getString("LiveTitle"));
	// utilsl.display(text, HttpConstant.NEWS_HOST
	// + jb.getString("LiveText"));
	// }
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// });
	// }

	private Bitmap getHttpBitmap(String newsHost) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;

		default:
			break;
		}
	}

}
