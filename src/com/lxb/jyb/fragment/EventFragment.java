package com.lxb.jyb.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.EventLayoutAdapter;
import com.lxb.jyb.bean.Finance;
import com.lxb.jyb.util.HttpConstant;

public class EventFragment extends Fragment {
	private View view;
	private PullToRefreshListView eventlist;
	private EventLayoutAdapter layoutAdapter;
	private String date;
	private ArrayList<Finance> mArrayList;
	private RequestQueue queue;
	protected WeakReference<View> weakView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (weakView == null || weakView.get() == null) {
			view = inflater.inflate(R.layout.event_fragment, null);
			initDate();

			// addListen();
			weakView = new WeakReference<View>(view);
		} else {
			ViewGroup parent = (ViewGroup) weakView.get().getParent();
			if (parent != null) {
				parent.removeView(weakView.get());
			}
		}
		return weakView.get();
	}

	private void initDate() {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
		String str = bundle.getString(ShiJianFragment.ARGUMENTS_DAY);
		if ("".equals(str)) {

		} else {
			date = str;
		}
		mArrayList = new ArrayList<Finance>();
		queue = Volley.newRequestQueue(getActivity());
		new RequestData().execute();
	}

	private class RequestData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
					HttpConstant.THINGS_HOST + date, null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							JSONArray array = response.optJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								Finance finance = new Finance(
										array.optJSONObject(i));
								mArrayList.add(finance);
							}
							Collections.sort(mArrayList);
							handler.sendEmptyMessage(100);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							System.out.println("请求错误:" + error.toString());
						}
					});
			// 3 将JsonObjectRequest添加到RequestQueue
			queue.add(mJsonObjectRequest);
			return null;
		}
	}

	private void initView() {
		eventlist = (PullToRefreshListView) view.findViewById(R.id.event_list);
		// TODO Auto-generated method stub
		if (mArrayList.size() == 0) {
			eventlist.setVisibility(View.GONE);
			view.findViewById(R.id.tishi).setVisibility(View.VISIBLE);
			return;
		} else {
			eventlist.setVisibility(View.VISIBLE);
			view.findViewById(R.id.tishi).setVisibility(View.GONE);
		}

		layoutAdapter = new EventLayoutAdapter(mArrayList, getActivity());
		eventlist.setAdapter(layoutAdapter);
		
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 100:
				// Collections.sort(mArrayList, new Comparator<Finance>() {
				// /**
				// *
				// * @param lhs
				// * @param rhs
				// * @return an integer < 0 if lhs is less than rhs, 0 if they
				// * are equal, and > 0 if lhs is greater than
				// * rhs,比较数据大小时,这里比的是时间
				// */
				// @Override
				// public int compare(Finance lhs, Finance rhs) {
				// Date date1 = DateUtil.stringToDate(lhs.getEventTime());
				// Date date2 = DateUtil.stringToDate(rhs.getEventTime());
				// // 对日期字段进行升序，如果欲降序可采用after方法
				// if (date1.after(date2)) {
				// return 1;
				// }
				// return -1;
				// }
				// });
				initView();
				break;

			default:
				break;
			}

		};
	};
}
