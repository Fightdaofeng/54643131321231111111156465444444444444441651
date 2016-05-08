package com.lxb.jyb.activity.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.ZhiBoEntity;
import com.lxb.jyb.tool.Accredit;
import com.lxb.jyb.util.Constants;
import com.lxb.jyb.util.HttpConstant;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

public class ZhiBoAdapater extends BaseAdapter {
	private Context context;
	private ArrayList<ZhiBoEntity> zblist;
	private ZhiBoEntity l;
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constants.DESCRIPTOR);

	public ZhiBoAdapater(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void setData(ArrayList<ZhiBoEntity> zblist) {
		this.zblist = zblist;
		notifyDataSetChanged();
	}

	public ArrayList<ZhiBoEntity> getZblist() {
		return zblist;
	}

	public void setZblist(ArrayList<ZhiBoEntity> zblist) {
		this.zblist = zblist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (zblist != null) {
			return zblist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return zblist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Toast.makeText(context, "尼玛", 1);
		ViewZhibo z = new ViewZhibo();
		// System.out.println("数据为zhibo："+zblist);
		l = zblist.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.zhibo_item, null, false);
			// z.souyin=(ImageView)
			// convertView.findViewById(R.id.calendar_item_img_state);
			z.time = (TextView) convertView.findViewById(R.id.zhibo_time);
			z.zhong = (TextView) convertView.findViewById(R.id.zhibo_zhong);
			z.bt = (TextView) convertView.findViewById(R.id.zhibo_bt);
			z.nt = (TextView) convertView.findViewById(R.id.zhibo_nr);
			z.nr = (ImageView) convertView.findViewById(R.id.zhibo_nr_img);
			z.gongbu = (LinearLayout) convertView.findViewById(R.id.gongbuzhi);
			z.guoqi = (ImageView) convertView.findViewById(R.id.zhibo_guoqi);
			z.qianzhi = (TextView) convertView.findViewById(R.id.zhibo_qianqi);
			z.yuqizhi = (TextView) convertView.findViewById(R.id.zhibo_yiqi);
			z.gongbuzhi = (TextView) convertView
					.findViewById(R.id.zhibo_gongbu);
			z.lidu = (TextView) convertView.findViewById(R.id.zhibo_duoyuan);
			z.lish = (TextView) convertView.findViewById(R.id.calendar_likong);
			z.liduo = (LinearLayout) convertView
					.findViewById(R.id.liduo_layout);
			z.lishao = (LinearLayout) convertView
					.findViewById(R.id.likong_layout);
			z.zb_fx = (ImageView) convertView.findViewById(R.id.zb_fx);
			z.lish = (TextView) convertView.findViewById(R.id.calendar_likong);
			z.guqi = (LinearLayout) convertView.findViewById(R.id.guoqi);
			// z.guoqi.setVisibility(View.GONE);
			// z.zhong.setVisibility(View.GONE);
			// z.bt.setVisibility(View.GONE);
			z.nr.setVisibility(View.GONE);
			z.nt.setVisibility(View.GONE);
			// z.qianzhi.setVisibility(View.GONE);
			// z.yuqizhi.setVisibility(View.GONE);
			// z.gongbuzhi.setVisibility(View.GONE);
			// z.nrimg.setVisibility(View.GONE);
			// z.liduo.setVisibility(View.GONE);
			// z.lishao.setVisibility(View.GONE);l
			convertView.setTag(z);
		} else {
			z = (ViewZhibo) convertView.getTag();
		}
		final String sharetitle;
		if (l.getType().equals("Newsflash")) {
			int live = Integer.parseInt(l.getNewsflashs().get(0)
					.getLiveImportance());
			String[] title = l.getNewsflashs().get(0).getLiveTitle()
					.split("<br />");
			String t = "";
			for (int i = 0; i < title.length; i++) {
				t = t + title[i] + "\n";
				z.bt.setText(t);
			}
			z.bt.setText(t);
			sharetitle = t;
			if (!"".equals(l.getNewsflashs().get(0).getLiveText())) {
				// utils.display(z.nr,
				// HttpConstant.NEWS_HOST+l.getNewsflashs().get(0).getLiveText());
				Bitmap bitmap = getHttpBitmap(HttpConstant.NEWS_HOST);
				z.nr.setImageBitmap(bitmap);
				z.nt.setText(l.getNewsflashs().get(0).getLiveText());
			} else {
				z.nr.setVisibility(View.GONE);
				z.nt.setVisibility(View.GONE);
			}
			z.time.setText(l.getNewsflashs().get(0).getLiveTime()
					.substring(0, 5));
			switch (live) {
			case 1:
				z.bt.setText(t);
				break;
			case 2:
				z.bt.setTextColor(Color.BLACK);
				break;
			case 3:
				z.bt.setTextColor(android.graphics.Color.parseColor("#FE2829"));
				break;
			}

			z.guqi.setVisibility(View.GONE);
			z.zhong.setVisibility(View.GONE);
			z.gongbu.setVisibility(View.GONE);
			z.lishao.setVisibility(View.GONE);
			z.liduo.setVisibility(View.GONE);
		} else {
			String s = l.getIndexEvents().get(0).getCountry()
					+ l.getIndexEvents().get(0).getPeriod()
					+ l.getIndexEvents().get(0).getName();
			z.bt.setText(s);
			sharetitle = s;
			String importance = l.getIndexEvents().get(0).getImportance();
			z.time.setText(l.getIndexEvents().get(0).getTime().substring(0, 5));
			String zhong = "";
			switch (importance) {
			case "low":
				zhong = "低";
				z.zhong.setText(zhong);
				z.zhong.setTextColor(Color.BLUE);
				z.lishao.setVisibility(View.GONE);
				z.liduo.setVisibility(View.GONE);
				break;
			case "mid":
				zhong = "中";
				z.zhong.setText(zhong);
				z.zhong.setTextColor(Color.RED);
				break;
			case "high":
				zhong = "高";
				z.zhong.setText(zhong);
				z.zhong.setTextColor(Color.RED);
				break;
			}
			ImageAdapter.setImageView(l.getIndexEvents().get(0).getCountry(),
					z.guoqi);
			z.qianzhi.setText("前期：" + l.getIndexEvents().get(0).getLastValue());
			z.yuqizhi.setText("预期：--"
					+ l.getIndexEvents().get(0).getPredictValue());
			TextPaint paint = z.gongbuzhi.getPaint();
			z.gongbuzhi.setTextColor(Color.RED);
			z.gongbuzhi.setText("公布：" + l.getIndexEvents().get(0).getValue());
			z.gongbuzhi.getPaint().setFlags(paint.UNDERLINE_TEXT_FLAG);
			z.lish.setText(l.getIndexEvents().get(0).getNegativeItem());
			z.lidu.setText(l.getIndexEvents().get(0).getPositiveItem());
			z.nr.setVisibility(View.GONE);
			z.nt.setVisibility(View.GONE);

		}
		z.zb_fx.setTag(position);
		z.zb_fx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int tag = Integer.parseInt(v.getTag() + "");
				Accredit accredit = new Accredit((Activity) context,
						mController);

				mController.getConfig().setSsoHandler(new SinaSsoHandler());
				mController.getConfig()
						.setSsoHandler(new TencentWBSsoHandler());
				accredit.addQQQZonePlatform();
				accredit.addWXPlatform();
				// accredit.setShareContent("金汇财经", zblist.get(tag).getUrl(),
				// "财经资讯", mController, "");
				String title = "金汇直播";
				String url = "http://zhibo.fxgold.com";
				View view = v.getRootView();

				view.setDrawingCacheEnabled(true);

				view.buildDrawingCache();

				Bitmap bitmap = view.getDrawingCache();
				accredit.setShareContent(context, title, sharetitle, url,
						bitmap);
				mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
						SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA,
						SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN,
						SHARE_MEDIA.RENREN);
				mController.openShare((Activity) context, false);
			}
		});
		return convertView;
	}

	private Bitmap getHttpBitmap(String newsHost) {
		// TODO Auto-generated method stub
		return null;
	}

	class ViewZhibo {
		TextView time;
		TextView zhong;
		TextView bt;
		TextView nt;
		ImageView zb_fx;
		ImageView nr;
		ImageView guoqi;
		TextView qianzhi;
		TextView yuqizhi;
		TextView gongbuzhi;
		TextView lidu;
		TextView lish;
		LinearLayout liduo;
		LinearLayout lishao;
		LinearLayout gongbu;
		LinearLayout guqi;

	}
}
