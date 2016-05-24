package com.lxb.jyb.push;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.json.JSONException;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.CustomDialogActivity;
import com.lxb.jyb.activity.XiangQingActivity;

public class PollingService extends Service {

	public static final String ACTION = "com.ryantang.service.PollingService";

	private Notification mNotification;
	private NotificationManager mManager;
	private SharedPreferences sp1;
	private SharedPreferences sp2;
	private SharedPreferences sp;

	private String phone;
	private Editor editor3;
	private Editor editor1;
	private Editor editor2;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		initNotifiManager();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		new PollingThread().start();
	}

	// 初始化通知栏配置
	private void initNotifiManager() {
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		mNotification = new Notification();
		mNotification.icon = icon;
		mNotification.tickerText = "交易宝";
		mNotification.defaults |= Notification.DEFAULT_SOUND;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		sp = getSharedPreferences("config", Activity.MODE_PRIVATE);
		phone = sp.getString("phone", "1");
		sp1 = getSharedPreferences(phone + "tipdata", Activity.MODE_PRIVATE);
		sp2 = getSharedPreferences(phone + "tippush", Activity.MODE_PRIVATE);

		editor1 = sp1.edit();
		editor2 = sp2.edit();
		editor3=sp.edit();

	}

	// 弹出Notification
	@SuppressWarnings("deprecation")
	private void showNotification(Tip paseTip) {
		// int requestCode = (int) System.currentTimeMillis();

		mNotification.when = System.currentTimeMillis();
		// mManager.notify(requestCode,mNotification);
		// Navigator to the new activity when click the notification title
		String time = paseTip.getTime().substring(11, 16);
		String country = paseTip.getCountry();
		String period = paseTip.getPeriod();
		String name = paseTip.getName();
		String predictValue = paseTip.getPredictValue();
		String lastValue = paseTip.getLastValue();
		String value = paseTip.getValue();
		String conte = time + "即将公布" + country + period + name + "\n" + "预测值:"
				+ predictValue + "前值:" + lastValue + "公布值:" + value;

		Intent i = new Intent(this, XiangQingActivity.class);
		i.putExtra("id", paseTip.getId());
		i.putExtra("period", paseTip.getPeriod());
		i.putExtra("value", paseTip.getValue());
		// i.putExtra("content", conte);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
				Intent.FLAG_ACTIVITY_NEW_TASK);

		mNotification.setLatestEventInfo(this,
				getResources().getString(R.string.app_name), conte,
				pendingIntent);
		// KeyguardManager 对锁屏进行管理
		KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		System.out.println("弹出!");
		// 用于判断当前是否处于锁屏状态。
		if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
			// 是，则弹通知nofification
			mManager.notify(0, mNotification);
		} else {
			// 否则弹出dialog
			Intent intent = new Intent();
			intent.setClass(this, CustomDialogActivity.class);
			intent.putExtra("id", paseTip.getId());
			intent.putExtra("title", conte);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			startActivity(intent);
		}
	}

	/**
	 * Polling thread 模拟向Server轮询的异步线程
	 * 
	 * @Author Ryan
	 * @Create 2013-7-13 上午10:18:34
	 */
	int count = 0;

	class PollingThread extends Thread {
		@Override
		public void run() {

			// 通过SimpleDateFormat获取24小时制时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
					Locale.getDefault());
			String currentTime = sdf.format(new Date());
			Set<String> stringSet = sp1.getStringSet("tipid", null);
			if (stringSet != null) {
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.addAll(stringSet);
				for (int i = 0; i < arrayList.size(); i++) {
					String string = sp2.getString(arrayList.get(i), "kong");
					if (!"".equals(string)) {
//						System.out.println(string);
						try {
							Tip paseTip = TipPase.paseTip(string);

							if (currentTime.equals(paseTip.getTime().substring(
									0, paseTip.getTime().length() - 3))) {
								showNotification(paseTip);
//								System.out.println("New message!");
								// 清除文件中保存的数据
								editor2.putString(arrayList.get(i), "");
								editor2.commit();
								// 弹出过通知了，清除文件中保存的ID
								arrayList.remove(i);
								Set<String> set = new HashSet<String>();
								set.addAll(arrayList);
								editor1.putStringSet("tipid", set);
								editor1.commit();
								String sid=paseTip.getId();
								editor3.putString(sid+"today","");
								editor3.commit();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
//			System.out.println("Polling...");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		System.out.println("Service:onDestroy");
	}

}