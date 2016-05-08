package com.lxb.jyb.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.bean.CalendarEntity;
import com.lxb.jyb.tool.Accredit;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.Constants;
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

public class NewListAdapter extends BaseAdapter {

    private ArrayList<CalendarEntity> mItemList;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.DESCRIPTOR);
    private MyClickListener clickListener;
    private Context mContext;
    private CalendarEntity calendar;
    private SharedPreferences sp;

    public NewListAdapter(Context context,
                          ArrayList<CalendarEntity> contentList, MyClickListener clickListener) {
        this.mContext = context;
        this.mItemList = contentList;
        LayoutInflater.from(context);
        this.clickListener = clickListener;
        sp = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
    }

    public ArrayList<CalendarEntity> getmItemList() {
        return mItemList;
    }

    public void setmItemList(ArrayList<CalendarEntity> mItemList) {
        this.mItemList = mItemList;
    }

    public NewListAdapter(Context context, ArrayList<CalendarEntity> itemList) {
        mContext = context;
        LayoutInflater.from(mContext);
        mItemList = itemList;
    }

    @Override
    public int getCount() {
        if (mItemList != null) {
            return mItemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        calendar = mItemList.get(position);
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.calendarlist_item, null);

            holder.country = (ImageView) convertView
                    .findViewById(R.id.calendar_county_image);
            holder.time = (TextView) convertView
                    .findViewById(R.id.alarm_clock_time);
            holder.important = (ImageView) convertView
                    .findViewById(R.id.alarm_Important);
            holder.title = (TextView) convertView
                    .findViewById(R.id.calendar_title);
            holder.qianqi = (TextView) convertView
                    .findViewById(R.id.calendar_befor);
            holder.yuqi = (TextView) convertView
                    .findViewById(R.id.calendar_yuqi);
            holder.gongbu = (TextView) convertView
                    .findViewById(R.id.calendar_gongbu);
            holder.liduo = (TextView) convertView
                    .findViewById(R.id.calendar_liduo);
            holder.likong = (TextView) convertView
                    .findViewById(R.id.calendar_likong);
            holder.liduo_layout = (LinearLayout) convertView
                    .findViewById(R.id.liduo_layout);
            holder.likong_layout = (LinearLayout) convertView
                    .findViewById(R.id.likong_layout);
            holder.settime = (TextView) convertView.findViewById(R.id.settime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.settime.setVisibility(View.VISIBLE);
        holder.time.setVisibility(View.VISIBLE);
        holder.settime.setTag(position);
        holder.settime.setOnClickListener(clickListener);
        holder.time.setText(calendar.getTime().substring(0, 5));
        holder.settime.setText(calendar.getTime().substring(0, 5));
        ImageAdapter.setImageView(calendar.getCountry(), holder.country);
        switch (calendar.getImportance()) {
            case "high":
                holder.important.setBackgroundResource(R.drawable.xing3);
                break;

            case "mid":
                holder.important.setBackgroundResource(R.drawable.xing2);
                break;
            case "low":
                holder.important.setBackgroundResource(R.drawable.xing1);
                break;
        }

        holder.title.setText(calendar.getCountry() + calendar.getPeriod()
                + calendar.getName());
        holder.title.setTag(calendar.getUrl());
        holder.qianqi.setText(calendar.getLastValue());
        // 预期值
        if (calendar.getPredictValue().length() > 1) {
            holder.yuqi.setText(calendar.getPredictValue());

        } else {
            holder.yuqi.setText("— —");
        }
        // 通过SimpleDateFormat获取24小时制时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());
        String currentTime = sdf.format(new Date());
        // 通过SimpleDateFormat获取24小时制时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        String currentd = df.format(new Date());
        if (calendar.getValue().length() > 0
                || ((currentTime.compareTo(calendar.getTime()) > 0) && (currentd
                .compareTo(calendar.getDate()) >= 0))) {

            if (mItemList.get(position).getValue().length() < 1) {
                holder.gongbu.setText("— —");
            } else {
                holder.gongbu.setText(calendar.getValue());
                holder.settime.setVisibility(View.GONE);
                holder.time.setVisibility(View.VISIBLE);
            }
        } else {
            String string = sp.getString(mItemList.get(position).getBasicIndexId()
                    + "today", "1");
            System.out.print("提醒时间:"+string);
            holder.gongbu.setText("— —");

            holder.settime.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.GONE);
            if ("1".equals(string)|| "".equals(string) ) {
                holder.settime.setText(calendar.getTime().substring(0, 5));
                holder.settime.setSelected(false);
            } else {
                holder.settime.setText(string);
                holder.settime.setSelected(true);
            }

        }


        // holder.gongbu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
        //
        // holder.gongbu.getPaint().setAntiAlias(true);// 抗锯齿
        holder.liduo_layout.setVisibility(View.VISIBLE);
        holder.likong_layout.setVisibility(View.VISIBLE);
        // 利多

        if (calendar.getPositiveItem().length() == 0
                || calendar.getImportance().equals("low")) {
            holder.liduo_layout.setVisibility(View.GONE);
        } else {
            holder.liduo.setText(calendar.getPositiveItem());
        }
        // 利空
        if (calendar.getNegativeItem().length() == 0
                || calendar.getImportance().equals("low")) {
            holder.likong_layout.setVisibility(View.GONE);
        } else {
            holder.likong.setText(calendar.getNegativeItem());
        }

        return convertView;
    }

    class ViewHolder {
        private LinearLayout liduo_layout;
        private LinearLayout likong_layout;
        private ImageView country;
        private TextView settime;
        private TextView time;
        private ImageView important;
        private TextView title;
        private TextView qianqi;
        private TextView yuqi;
        private TextView gongbu;
        private TextView liduo;
        private TextView likong;
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent(String title, String content, String url) {

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

        UMImage localImage = new UMImage(mContext, R.drawable.empty_photo);
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
        UMImage image = new UMImage(mContext, BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.empty_photo));
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
        sinaContent
                .setShareImage(new UMImage(mContext, R.drawable.empty_photo));
        mController.setShareMedia(sinaContent);

    }
}
