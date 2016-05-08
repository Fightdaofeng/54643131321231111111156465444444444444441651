package com.lxb.jyb.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
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
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class Accredit {
	private Activity activity;
	private UMSocialService mController;

	public Accredit(Activity activity, UMSocialService mController) {
		this.activity = activity;
		this.mController = mController;
	}

	public void addQQQZonePlatform() {
		String appId = "1104950027";
		String appKey = "DMxjET0DNshMqOpO";
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId,
				appKey);
		qqSsoHandler.setTargetUrl("http://www.fxgold.com/");
		qqSsoHandler.addToSocialSDK();
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId,
				appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	public void addWXPlatform() {
		// 测试KEY
		// String appId = "wx09324fd705bc049d";
		// String appSecret = "7bebbb57fb82ca8f79509169c84ade48";
		// 发布KEy
		// String appId = "wxea5f2e41a50abb6c";
		// String appSecret = "80a7b4afe2a45947851d65dc50b2919c";
		String appId = "wxd3199831a80190ed";
		String appSecret = "dbcee989c414b6bab9b6be1d5338a450";
		UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
		wxHandler.addToSocialSDK();
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId,
				appSecret);
		wxCircleHandler.showCompressToast(false);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

	}

	public void setShareContent(String title, String weburl,
			String discription, UMSocialService mController, String thumb) {
		Log.i("info", title + " " + weburl + " " + discription);
		System.out.println("title" + title + "weburl" + weburl + "discription"
				+ discription);
		String newTitle = "【" + title + "】" + discription + weburl;

		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,
				"1104950027", "DMxjET0DNshMqOpO");
		qZoneSsoHandler.addToSocialSDK();
		mController.setShareContent(discription);
		UMImage urlImage;
		if (null != thumb && !"".equals(thumb)) {
			urlImage = new UMImage(activity, thumb);
		} else {

			urlImage = new UMImage(activity, R.drawable.ic_launcher);
		}
		// 微信分享
		WeiXinShare(newTitle, title, weburl, mController, urlImage);
		// 朋友圈分享
		circle(newTitle, title, weburl, urlImage);
		// QQ空间分享
		QQzoneShare(newTitle, title, weburl, urlImage);

		// QQ分享
		QQShare(newTitle, title, weburl, urlImage);

		// 新浪分享
		sinaShare(newTitle, weburl, urlImage);

	}

	public void sinaShare(String newTitle, String weburl, UMImage urlImage) {
		// mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.setShareContent(newTitle);
		mController.setShareImage(urlImage);
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();

		mController.getConfig().closeToast();
	}

	public void WeiXinShare(String newTitle, String title, String weburl,
			UMSocialService mController, UMImage image) {
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(newTitle);
		weixinContent.setTitle(title);
		weixinContent.setTargetUrl(weburl);
		weixinContent.setShareMedia(image);
		mController.setShareMedia(weixinContent);
		mController.getConfig().closeToast();
	}

	public void circle(String newTitle, String title, String weburl,
			UMImage image) {
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(newTitle);
		circleMedia.setTitle(title);
		circleMedia.setShareImage(image);
		circleMedia.setTargetUrl(weburl);
		mController.setShareMedia(circleMedia);
	}

	public void QQzoneShare(String newTitle, String title, String weburl,
			UMImage urlImage) {
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(newTitle);
		qzone.setTargetUrl(weburl);
		qzone.setTitle(title);
		qzone.setShareImage(urlImage);
		mController.setShareMedia(qzone);

	}

	public void QQShare(String newTitle, String title, String weburl,
			UMImage urlImage) {
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(newTitle);
		qqShareContent.setTitle(title);
		qqShareContent.setShareImage(urlImage);
		qqShareContent.setTargetUrl(weburl);
		mController.setShareMedia(qqShareContent);
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	public void setShareContent(Context mContext, String title, String content,
			String url, Bitmap bitmap) {

		// 配置SSO
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				(Activity) mContext, "1104950027", "DMxjET0DNshMqOpO");
		qZoneSsoHandler.addToSocialSDK();
		mController.setShareContent(content + "," + url);

		// APP ID：201874, API
		// * KEY：28401c0964f04a72a14c812d6132fcef, Secret
		// * Key：3bf66e42db1e4fa9829b955cc300b737.
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(mContext,
				"201874", "28401c0964f04a72a14c812d6132fcef",
				"3bf66e42db1e4fa9829b955cc300b737");
		mController.getConfig().setSsoHandler(renrenSsoHandler);

		UMImage localImage = new UMImage(mContext, bitmap);
		// UMImage urlImage = new UMImage(activity,
		// "http://www.umeng.com/images/pic/social/integrated_3.png");
		// UMImage resImage = new UMImage(getActivity(), R.drawable.icon);

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(content + "," + url);
		weixinContent.setTitle(title);
		weixinContent.setTargetUrl(url);
		weixinContent.setShareMedia(localImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(content + "," + url);
		circleMedia.setTitle(title);
		circleMedia.setShareMedia(localImage);
		// circleMedia.setShareMedia(uMusic);
		// circleMedia.setShareMedia(video);
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		// 设置renren分享内容
		RenrenShareContent renrenShareContent = new RenrenShareContent();
		renrenShareContent.setShareContent("人人分享内容");
		UMImage image = new UMImage(mContext, bitmap);
		image.setTitle(title);
		renrenShareContent.setShareImage(image);
		renrenShareContent.setAppWebSite(url);
		mController.setShareMedia(renrenShareContent);

		// 设置QQ空间分享内容
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(content + "," + url);
		qzone.setTargetUrl(url);
		qzone.setTitle(title);
		qzone.setShareMedia(localImage);
		// qzone.setShareMedia(uMusic);
		mController.setShareMedia(qzone);

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(content + "," + url);
		qqShareContent.setTitle(title);
		qqShareContent.setShareMedia(localImage);
		qqShareContent.setTargetUrl(url);
		mController.setShareMedia(qqShareContent);

		// 设置邮件分享内容， 如果需要分享图片则只支持本地图片
		MailShareContent mail = new MailShareContent(localImage);
		mail.setTitle(title);
		mail.setShareContent(content + "," + url);
		// 设置tencent分享内容
		mController.setShareMedia(mail);

		// 设置短信分享内容
		SmsShareContent sms = new SmsShareContent();
		sms.setShareContent(content + "," + url);
		// sms.setShareImage(urlImage);
		mController.setShareMedia(sms);

		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setShareContent(content + "," + url);
		sinaContent.setShareImage(new UMImage(mContext, R.drawable.ic_launcher));
		mController.setShareMedia(sinaContent);

	}

	public void performShare(SHARE_MEDIA platform) {

		mController.postShare(activity, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "分享成功";
				} else {
					showText += "分享失败";
				}
				Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();
			}
		});
		mController.getConfig().closeToast();
	}
}
