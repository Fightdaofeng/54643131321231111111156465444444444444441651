package com.lxb.jyb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {
	private List<Activity> activitys = new ArrayList<Activity>();;
	private static MyApplication mInstance = null;
	private Map<String, String> mmp = new HashMap<String, String>();
	private int Cunrentfragment;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
//		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
//		JPushInterface.init(this); // 初始化 JPush
	}

	public void addAct(Activity act) {
		if (activitys != null) {
			activitys.add(act);
		}
	}

	public void exitAppAll() {
		if (activitys.size() > 0 && activitys != null) {
			for (Activity act : activitys) {
				act.finish();
			}
		}
		System.gc();
	}

	public int getCunrentfragment() {
		return Cunrentfragment;
	}

	public void setCunrentfragment(int cunrentfragment) {
		Cunrentfragment = cunrentfragment;
	}

	public Map<String, String> getMmp() {
		return mmp;
	}

	public void setMmp(Map<String, String> mmp) {
		this.mmp = mmp;
	}

	/**
	 * getInstance(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @returnCJRLApplication
	 * @exception
	 * @since 1.0.0
	 */
	public static MyApplication getInstance() {
		return mInstance;
	}

}
