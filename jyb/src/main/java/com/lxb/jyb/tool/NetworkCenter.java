package com.lxb.jyb.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCenter {
	/**
	 * 网络状态检查̬
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkConnection(Context context) {
		return checkNetwork_LXB(context);
	}

	/**
	 * 检查是否有网络连接
	 * 
	 * @param context
	 * @return true=
	 */
	public static boolean checkNetwork_LXB(Context context) {
		if (isNetworkConnected(context)) {
			if (isWifiConnected(context)) {
				// WiFi
				return true;
			}
			if (isMobileConnected(context)) {
				// 3G
				return true;
			}
		}

		return false;
	}

	/**
	 * 检查网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		// TODO
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE); // 获得系统连接服务
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();// 获得网络信息
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 检查WiFi是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);// 获得手机WIFI信息
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 检查手机网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable()) {
				return networkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
