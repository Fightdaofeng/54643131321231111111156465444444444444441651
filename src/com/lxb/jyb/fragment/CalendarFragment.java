package com.lxb.jyb.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.CalShaiXuanActivity;
import com.lxb.jyb.activity.XiangQingActivity;
import com.lxb.jyb.activity.adapter.NewListAdapter;
import com.lxb.jyb.bean.CalDetails;
import com.lxb.jyb.bean.CalendarEntity;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.HttpConstant;
import com.umeng.analytics.MobclickAgent;

public class CalendarFragment extends Fragment {
	private View view;
	private ArrayList<CalendarEntity> dataList;
	private NewListAdapter adapter;
	protected WeakReference<View> weakView;
	private PullToRefreshListView pullrefreshlistView;
	private String date;
	private RequestQueue queue;
	private int clickBtnID;
	private String iD = "";
	private ArrayList<CalDetails> list;
	private String value;
	private String period;
	private SharedPreferences sp;
	private boolean isLogin;
	private ImageView calshaixuan;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (weakView == null || weakView.get() == null) {
			view = inflater.inflate(R.layout.calendar, null);
			initData();

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

	private void initData() {
		// TODO Auto-generated method stub

		sp = getActivity()
				.getSharedPreferences("config", Activity.MODE_PRIVATE);
		isLogin = sp.getBoolean("islogin", false);

		Bundle bundle = getArguments();
		String str = bundle.getString(CalendarHomeFragment.ARGUMENTS_DAY);
		if ("".equals(str)) {

		} else {
			date = str;
		}
		// Toast.makeText(getActivity(), "进入了" + date,
		// Toast.LENGTH_SHORT).show();
		dataList = new ArrayList<CalendarEntity>();
		queue = Volley.newRequestQueue(getActivity());
		new RequestData().execute();
	}

	private void initFinds() {
		getActivity().findViewById(R.id.shishaixuan).setVisibility(View.GONE);
		getActivity().findViewById(R.id.rishaixuan).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.zbshaixuan).setVisibility(View.GONE);
		pullrefreshlistView = (PullToRefreshListView) view
				.findViewById(R.id.refreshable_view);
		if (dataList.size() == 0) {
			pullrefreshlistView.setVisibility(View.GONE);
			view.findViewById(R.id.tishi).setVisibility(View.VISIBLE);
			return;
		} else {
			pullrefreshlistView.setVisibility(View.VISIBLE);
			view.findViewById(R.id.tishi).setVisibility(View.GONE);
		}

		adapter = new NewListAdapter(getActivity(), dataList, mListener);
		// adapter.notifyDataSetChanged();
		pullrefreshlistView.setAdapter(adapter);

		pullrefreshlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sp = getActivity().getSharedPreferences("config",
						Activity.MODE_PRIVATE);
				isLogin = sp.getBoolean("islogin", false);

				if (!isLogin) {
					iD = dataList.get(position - 1).getBasicIndexId();
					period = dataList.get(position - 1).getPeriod();
					value = dataList.get(position - 1).getValue();
					new RequestXQ().execute();

				} else {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
					// Intent intent = new Intent(getActivity(),
					// DisembarkActivity.class);
					// getActivity().startActivity(intent);
				}

			}
		});
		pullrefreshlistView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				new RequestData().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub

			}
		});
		calshaixuan = (ImageView) getActivity().findViewById(R.id.rishaixuan);
		calshaixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CalendarFragment.this.getActivity(),
						CalShaiXuanActivity.class);
				intent.putExtra("cal", true);
				startActivityForResult(intent, 102);
			}
		});
	}

	private class RequestData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			dataList = new ArrayList<CalendarEntity>();
			JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
					HttpConstant.CALENDAR_HOST + date, null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							System.out.println(response);
							JSONArray array = response.optJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								CalendarEntity calendar = new CalendarEntity(
										array.optJSONObject(i));
								dataList.add(calendar);
							}
							Collections.sort(dataList);
							handler.sendEmptyMessage(100);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			// 3 将JsonObjectRequest添加到RequestQueue
			queue.add(mJsonObjectRequest);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
			if (pullrefreshlistView != null) {
				pullrefreshlistView.onRefreshComplete();
			}
		}
	}

	public class RequestTime extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog = new ProgressDialog(getActivity());

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("请稍等...");
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			list = new ArrayList<CalDetails>();
			JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
					HttpConstant.DETAILS_HOST + clickBtnID, null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							System.out.println("请求结果:" + response.toString());
							JSONArray array = response.optJSONArray("data");

							for (int i = 0; i < array.length(); i++) {
								CalDetails details = new CalDetails(
										array.optJSONObject(i));

								list.add(details);
							}

							handler.sendEmptyMessage(200);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			// 3 将JsonObjectRequest添加到RequestQueue
			queue.add(mJsonObjectRequest);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.cancel();
		}
	}

	private int sid;
	/**
	 * 实现类，响应按钮点击事件
	 */
	private MyClickListener mListener = new MyClickListener() {

		@Override
		public void myOnClick(int position, View v) {
			sp = getActivity().getSharedPreferences("config",
					Activity.MODE_PRIVATE);
			isLogin = sp.getBoolean("islogin", false);

			if (isLogin) {
				sid = position;
				clickBtnID = Integer.parseInt(dataList.get(position)
						.getBasicIndexId());

				new RequestTime().execute();
			} else {
				Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
						.show();
				// Intent intent = new Intent(getActivity(),
				// DisembarkActivity.class);
				// getActivity().startActivity(intent);
			}
		}
	};

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case 100:
				initFinds();
				break;
			case 200:
				String title = list.get(0).getCountry()
						+ list.get(0).getPeriod() + list.get(0).getName();
				iD = dataList.get(sid).getBasicIndexId();
				period = dataList.get(sid).getPeriod();
				value = dataList.get(sid).getValue();
				String t = dataList.get(sid).getDate() + " "
						+ dataList.get(sid).getTime();
				if (t.length() > 0
						&& !dataList.get(sid).getTime().equals("00:00:00")) {
					// String time = t.substring(0, t.length() - 2);
					// Intent intent = new Intent(getActivity(),
					// SetTimeActivity.class);
					// intent.putExtra("time", t);
					// System.out.println("最原始的ID" + iD);
					// intent.putExtra("id", iD);
					// intent.putExtra("country",
					// dataList.get(sid).getCountry());
					// intent.putExtra("name", dataList.get(sid).getName());
					// intent.putExtra("predictValue", dataList.get(sid)
					// .getPredictValue());
					// intent.putExtra("lastValue", dataList.get(sid)
					// .getLastvalue());
					// intent.putExtra("value", value);
					// intent.putExtra("period", period);
					// intent.putExtra("day", "today");
					// getRootFragment().startActivityForResult(intent, 100);
				} else {
					Toast.makeText(getActivity(), "暂未有公布时间", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case 300:
				// adapter.setId(iD);
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 得到根Fragment
	 * 
	 * @return
	 */
	private Fragment getRootFragment() {
		Fragment fragment = getParentFragment();
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}
		return fragment;

	}

	private class RequestXQ extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
					HttpConstant.DETAILS_HOST + iD, null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							System.out.println("请求结果:" + response.toString());

							intoXq();

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							AlertDialog.Builder builder = new Builder(
									CalendarFragment.this.getActivity());
							builder.setMessage("暂无详情数据，点击返回");

							builder.setTitle("返回提示");

							builder.setPositiveButton("返回",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();

										}

									});
							builder.show();
						}
					});
			// 3 将JsonObjectRequest添加到RequestQueue
			queue.add(mJsonObjectRequest);

			return null;
		}

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面，"MainScreen"为页面名称，可自定义
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void intoXq() {

		Intent intent = new Intent(getActivity(), XiangQingActivity.class);
		intent.putExtra("id", iD);
		handler.sendEmptyMessage(300);
		startActivity(intent);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 102) {

		} else {
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		}
	}
}
