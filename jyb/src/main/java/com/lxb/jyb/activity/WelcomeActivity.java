package com.lxb.jyb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lxb.jyb.MyApplication;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.UpdataInfo;
import com.lxb.jyb.util.DownLoadManager;
import com.lxb.jyb.util.pase.UpdataInfoParser;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WelcomeActivity extends Activity {
	private SharedPreferences sp;
	private boolean boolean1;
	private Editor editor;
	private MyApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		sp = getSharedPreferences("setup", Activity.MODE_PRIVATE);
		boolean1 = sp.getBoolean("isFirst", true);
		editor = sp.edit();
		application=(MyApplication)this.getApplication();
		application.addAct(this);
		initXWTitle();
		if (boolean1) {
			startActivity(new Intent(WelcomeActivity.this,
					ViewPagerDemoActivity.class));
			WelcomeActivity.this.finish();
		} else {
			startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
			WelcomeActivity.this.finish();
		}

	}

	private ArrayList<String> xwTitle;

	private void initXWTitle() {
		// TODO Auto-generated method stub
		if (boolean1) {
			xwTitle = new ArrayList<String>();
			xwTitle.add("订阅");
			xwTitle.add("推荐");
			xwTitle.add("头条");
			xwTitle.add("中国");
			xwTitle.add("美国");
			//
			// Set<String> newSet = new HashSet<String>();
			// newSet.addAll(xwTitle);

			editor.putString("xwdef", xwTitle.toString());
			editor.commit();
		}
	}
	private final String TAG = this.getClass().getName();
	private final int UPDATA_NONEED = 0;
	private final int UPDATA_CLIENT = 1;
	private final int GET_UNDATAINFO_ERROR = 2;
	private final int SDCARD_NOMOUNTED = 3;
	private final int DOWN_ERROR = 4;
	private UpdataInfo info;
	private String localVersion;
	private boolean isEnter = false;
	private Builder dialog;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 40:
					if (!isEnter) {
						if (dialog == null) {
							dialog = new Builder(WelcomeActivity.this)
									.setTitle("网络异常").setMessage("网络异常，请检查网络是否连接。")
									.setPositiveButton("是", new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog,
															int which) {
											// TODO Auto-generated method stub
											Intent wifiSettingsIntent = new Intent(
													"android.settings.WIFI_SETTINGS");
											startActivity(wifiSettingsIntent);
											application.exitAppAll();
										}
									});
							dialog.setCancelable(true);
							dialog.show();
						}
					}
					break;
				case 80:
					Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
					break;
				case UPDATA_NONEED:
					Intent intent1 = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent1);
					WelcomeActivity.this.finish();
					break;
				case UPDATA_CLIENT:
					// 对话框通知用户升级程序
					showUpdataDialog();
					break;
				case GET_UNDATAINFO_ERROR:
					// 服务器超时
					Toast.makeText(getApplicationContext(), "获取服务器更新信息失败",Toast.LENGTH_LONG)
							.show();
					break;
				case DOWN_ERROR:
					// 下载apk失败
					Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
					break;
			}
		};
	};
	private String getVersionName() throws Exception {
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}
	public class CheckVersionTask implements Runnable {
		InputStream is;

		public void run() {
			try {
				String path = getResources().getString(R.string.url_server);
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					// 从服务器获得一个输入流
					is = conn.getInputStream();
				}
				info = UpdataInfoParser.getUpdataInfo(is);
				if (info.getVersion().equals(localVersion)) {
					Log.i(TAG, "版本号相同");
					Message msg = new Message();
					msg.what = UPDATA_NONEED;
					handler.sendMessage(msg);
					// LoginMain();
				} else {
					Log.i(TAG, "版本号不相同 ");
					Message msg = new Message();
					msg.what = UPDATA_CLIENT;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = GET_UNDATAINFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	/*
         *
         * 弹出对话框通知用户更新程序
         *
         * 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性, 对话框的内容,样式,按钮
         * 3.通过builder 创建一个对话框 4.对话框show()出来
         */
	protected void showUpdataDialog() {
		Builder builer = new Builder(this);
		builer.setTitle("版本升级");
		builer.setMessage(info.getDescription());
		// 当点确定按钮时从服务器上下载 新的apk 然后安装 װ
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "下载apk,更新");
				downLoadApk();
			}
		});
		builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// do sth
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}

	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(
							info.getUrl(), pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = DOWN_ERROR;

					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
