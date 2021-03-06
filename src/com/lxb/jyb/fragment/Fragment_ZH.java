package com.lxb.jyb.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.Activity_GuaDan;
import com.lxb.jyb.activity.BDMT4Activity;
import com.lxb.jyb.activity.CreateOrderActivity;
import com.lxb.jyb.activity.MessageActivity;
import com.lxb.jyb.activity.OrderActivity;
import com.lxb.jyb.activity.UserAnalyzeActivity;
import com.lxb.jyb.activity.UserInfoActivity;
import com.lxb.jyb.activity.adapter.XYchicangAdapter;
import com.lxb.jyb.activity.view.XCSlideMenu;
import com.lxb.jyb.bean.OrderSelect;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.tool.TestUtil;
import com.lxb.jyb.util.HttpConstant;

/**
 * 我的账户
 * 
 * @author liuxiaobin
 *
 */
@SuppressLint("InflateParams")
public class Fragment_ZH extends Fragment implements OnClickListener {
	private View view;
	private XCSlideMenu xcSlideMenu;
	private ImageView btnSwitch;
	private TextView menu1;
	private LinearLayout pro, leftmune3, leftmune4;
	private TextView create_tv, msg_tv, xycc_tv, lszj_tv, kaishi_ri,
			jiesu_riqi;
	private PopupWindow popu1, popu2, popu3;
	private LinearLayout xycc_lay, history_lay;
	private boolean isLeft = false;
	private LinearLayout top_user;
	private View selriqi_pp;
	private TextView all_tv, start_tv, start_riqi, end_tv, end_riqi, sel_tv;
	private Calendar calendar;
	private DatePickerDialog dialog;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	private XYchicangAdapter xyadapter;
	private PullToRefreshListView listview;
	private RequestQueue queue;
	private ArrayList<OrderSelect> nowOrder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_wdzh, container, false);

		queue = Volley.newRequestQueue(getActivity());
		initFindView();
		createBView();
		handler.sendEmptyMessage(5);
		new RequesCC().execute();
		return view;
	}

	Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				listview = (PullToRefreshListView) view
						.findViewById(R.id.cc_list);
				xyadapter = new XYchicangAdapter(nowOrder, getActivity(),
						listener);
				listview.setAdapter(xyadapter);
				listview.setOnRefreshListener(new OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub
						new RequesCC().execute();
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub

					}
				});
				listview.setOnItemClickListener(itemlistener);
				break;

			case 2:
				// 删除
				break;
			case 5:
				
				break;
				
			}
		};
	};
	OnItemClickListener itemlistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT)
					.show();
			ArrayList<OrderSelect> list = xyadapter.getList();
			OrderSelect orderSelect = list.get(position);
			Intent intent = new Intent(Fragment_ZH.this.getActivity(),
					OrderActivity.class);
			intent.putExtra("bean", orderSelect);
			Fragment_ZH.this.startActivity(intent);
		}
	};

	private void createBView() {
		// System.out.println("宽度"+pro.getWidth());
		LinearLayout lLayout = new LinearLayout(this.getActivity());
		LinearLayout.LayoutParams lLayoutlayoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		// lLayout.setBackgroundColor(R.color.huise);
		lLayout.setLayoutParams(lLayoutlayoutParams);
		lLayout.setBackgroundColor(R.color.jd2);
		TextView textView = new TextView(getActivity());
		textView.setText("保证金70%");
		textView.setTextSize(18);
		textView.setTextColor(Color.parseColor("#ffffff"));
		textView.setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(0,
				ViewGroup.LayoutParams.MATCH_PARENT, 7);

		textView.setLayoutParams(txtParams);
		textView.setBackgroundColor(Color.parseColor("#72D3FD"));

		View view = new View(getActivity());
		LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(0,
				ViewGroup.LayoutParams.MATCH_PARENT, 3);
		view.setLayoutParams(viewParams);

		lLayout.addView(textView);
		lLayout.addView(view);

		pro.addView(lLayout);

	}

	private void initFindView() {
		// TODO Auto-generated method stub
		xcSlideMenu = (XCSlideMenu) view.findViewById(R.id.slideMenu);
		// menu1 = (TextView) view.findViewById(R.id.menu1);
		btnSwitch = (ImageView) view.findViewById(R.id.user_left);
		pro = (LinearLayout) view.findViewById(R.id.spring_lay);
		create_tv = (TextView) view.findViewById(R.id.create_tv);
		msg_tv = (TextView) view.findViewById(R.id.msg_tv);
		top_user = (LinearLayout) view.findViewById(R.id.top_user);
		view.findViewById(R.id.add_user).setOnClickListener(this);
		kaishi_ri = (TextView) view.findViewById(R.id.start_riqi);
		jiesu_riqi = (TextView) view.findViewById(R.id.end_riqi);

		Calendar calendar = Calendar.getInstance();
		jiesu_riqi.setText(sdf.format(calendar.getTime()));

		int days = calendar.get(Calendar.DAY_OF_MONTH) - 30;
		calendar.set(Calendar.DAY_OF_MONTH, days);
		kaishi_ri.setText(sdf.format(calendar.getTime()));

		leftmune4 = (LinearLayout) view.findViewById(R.id.menu4);
		leftmune3 = (LinearLayout) view.findViewById(R.id.menu3);
		xycc_tv = (TextView) view.findViewById(R.id.xycc_tv);
		lszj_tv = (TextView) view.findViewById(R.id.lszj_tv);

		xycc_lay = (LinearLayout) view.findViewById(R.id.xycc_lay);
		history_lay = (LinearLayout) view.findViewById(R.id.history_lay);
		xcSlideMenu.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("" + i);
		}

		setClick();
	}

	private StringBuffer pc;
	MyClickListener listener = new MyClickListener() {

		@Override
		public void myOnClick(final int position, View v) {
			final OrderSelect orderSelect = nowOrder.get(position);
			AlertDialog.Builder builder = new Builder(
					Fragment_ZH.this.getActivity());
			builder.setMessage("\"订单号：" + orderSelect.getTicket() + "  "
					+ orderSelect.getSymbol() + "  " + orderSelect.getLots()
					+ "手\"" + "是否确认平仓");

			builder.setTitle("平仓提示");

			builder.setNegativeButton("确认",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							pc = TestUtil.getPC(orderSelect.getTicket());
							getHttpData();
							nowOrder.remove(position);
							xyadapter.setList(nowOrder);
							xyadapter.notifyDataSetChanged();
						}
					});
			builder.setPositiveButton("取消",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}

					});

			builder.show();

		}
	};

	public void getHttpData() {

		JsonObjectRequest request = null;
		try {
			request = new JsonObjectRequest(Method.POST,
					HttpConstant.CLOSE_ORDER, new JSONObject(pc.toString()),
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject jsonObject) {

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
							// TODO Auto-generated method stub
							Log.i("info", arg0.toString());
						}
					}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap<String, String> headers = new HashMap<>();
					headers.put("Accept", "application/json");
					headers.put("Content-Type",
							"application/json; charset=UTF-8");
					return headers;
				}
			};
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 超时时间10s,最大重连次数2次
		queue.add(request);
	}

	private void setClick() {
		// TODO Auto-generated method stub
		btnSwitch.setClickable(true);
		top_user.setClickable(true);
		top_user.setOnClickListener(this);
		btnSwitch.setOnClickListener(this);
		create_tv.setOnClickListener(this);
		msg_tv.setOnClickListener(this);
		leftmune3.setOnClickListener(this);
		leftmune4.setOnClickListener(this);
		xycc_tv.setOnClickListener(this);
		lszj_tv.setOnClickListener(this);
		history_lay.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_left:
		case R.id.top_user:
			xcSlideMenu.switchMenu();
			if (isLeft) {

				isLeft = false;
				getActivity().findViewById(R.id.main_ll_bg).setVisibility(
						View.VISIBLE);
			} else {
				getActivity().findViewById(R.id.main_ll_bg).setVisibility(
						View.GONE);
				isLeft = true;
			}
			break;
		case R.id.create_tv:

			initPopupWindow();

			break;
		case R.id.msg_tv:
			startActivity(new Intent(Fragment_ZH.this.getActivity(),
					MessageActivity.class));
			break;
		case R.id.menu3:
			startActivity(new Intent(Fragment_ZH.this.getActivity(),
					UserInfoActivity.class));
			break;
		case R.id.menu4:
			startActivity(new Intent(Fragment_ZH.this.getActivity(),
					UserAnalyzeActivity.class));
			break;
		case R.id.add_user:
			startActivity(new Intent(Fragment_ZH.this.getActivity(),
					BDMT4Activity.class));
			break;
		case R.id.xycc_tv:
			initCCPP(0);
			break;
		case R.id.lszj_tv:
			initCCPP(1);
			break;
		case R.id.history_lay:
			initpp3();
			break;
		case R.id.pp_all_tv:
			all_tv.setSelected(true);
			start_tv.setSelected(false);
			start_riqi.setSelected(false);
			end_tv.setSelected(false);
			end_riqi.setSelected(false);

			break;
		case R.id.pp_start_tv:
			all_tv.setSelected(false);
			start_tv.setSelected(true);
			start_riqi.setSelected(true);
			end_tv.setSelected(true);
			end_riqi.setSelected(true);
			calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(getActivity(),
					new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							start_riqi.setText(year + "." + (monthOfYear + 1)
									+ "." + dayOfMonth);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) - 1,
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.pp_end_tv:
			all_tv.setSelected(false);
			start_tv.setSelected(true);
			start_riqi.setSelected(true);
			end_tv.setSelected(true);
			end_riqi.setSelected(true);
			calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(getActivity(),
					new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							end_riqi.setText(year + "." + (monthOfYear + 1)
									+ "." + dayOfMonth);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		}
	}

	private void initpp3() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selriqi_pp = inflater.inflate(R.layout.selriqi_pp, null);
		popu3 = new PopupWindow(selriqi_pp, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popu3.setFocusable(true);
		popu3.setOutsideTouchable(true);
		popu3.showAsDropDown(history_lay);

		all_tv = (TextView) selriqi_pp.findViewById(R.id.pp_all_tv);
		start_tv = (TextView) selriqi_pp.findViewById(R.id.pp_start_tv);
		start_riqi = (TextView) selriqi_pp.findViewById(R.id.pp_start_riqi);
		end_tv = (TextView) selriqi_pp.findViewById(R.id.pp_end_tv);
		end_riqi = (TextView) selriqi_pp.findViewById(R.id.pp_end_riqi);
		sel_tv = (TextView) selriqi_pp.findViewById(R.id.pp_sel_btn);

		Calendar calendar = Calendar.getInstance();
		end_riqi.setText(sdf.format(calendar.getTime()));

		int days = calendar.get(Calendar.DAY_OF_MONTH) - 30;
		calendar.set(Calendar.DAY_OF_MONTH, days);
		start_riqi.setText(sdf.format(calendar.getTime()));
		all_tv.setSelected(false);
		start_tv.setSelected(true);
		start_riqi.setSelected(true);
		end_tv.setSelected(true);
		end_riqi.setSelected(true);

		selriqi_pp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popu3.isShowing()) {
					popu3.dismiss();
				}
				return false;
			}
		});
		all_tv.setOnClickListener(this);
		start_tv.setOnClickListener(this);
		end_tv.setOnClickListener(this);
		sel_tv.setOnClickListener(this);
	}

	private void initCCPP(int index) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pp = inflater.inflate(R.layout.chicang_pp, null);
		popu2 = new PopupWindow(pp, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popu2.setFocusable(true);
		popu2.setOutsideTouchable(true);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.tp_ly);
		popu2.showAsDropDown(layout);
		pp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popu2.isShowing()) {
					popu2.dismiss();
				}
				return false;
			}
		});
		LinearLayout lay1 = (LinearLayout) pp.findViewById(R.id.pp_xycc);
		LinearLayout lay2 = (LinearLayout) pp.findViewById(R.id.pp_lszj);
		final ImageView v1 = (ImageView) pp.findViewById(R.id.xycc_gou);
		final ImageView v2 = (ImageView) pp.findViewById(R.id.lszj_gou);

		if (index == 0) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.GONE);
			lay1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (popu2.isShowing()) {
						popu2.dismiss();
					}
				}
			});
			lay2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v1.setVisibility(View.GONE);
					v2.setVisibility(View.VISIBLE);
					lszj_tv.setVisibility(View.VISIBLE);
					xycc_tv.setVisibility(View.GONE);
					xycc_lay.setVisibility(View.GONE);
					history_lay.setVisibility(View.VISIBLE);
					if (popu2.isShowing()) {
						popu2.dismiss();
					}

				}
			});
		} else {
			v2.setVisibility(View.VISIBLE);
			v1.setVisibility(View.GONE);
			lay1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v1.setVisibility(View.VISIBLE);
					v2.setVisibility(View.GONE);
					lszj_tv.setVisibility(View.GONE);
					xycc_tv.setVisibility(View.VISIBLE);
					xycc_lay.setVisibility(View.VISIBLE);
					history_lay.setVisibility(View.GONE);
					if (popu2.isShowing()) {
						popu2.dismiss();
					}
				}
			});
			lay2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (popu2.isShowing()) {
						popu2.dismiss();
					}

				}
			});
		}
	}

	private void initPopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pp = inflater.inflate(R.layout.jiancang_pop, null);
		popu1 = new PopupWindow(pp, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popu1.setFocusable(true);
		popu1.setOutsideTouchable(true);
		pp.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int popupWidth = pp.getMeasuredWidth();
		int popupHeight = pp.getMeasuredHeight();

		int[] location = new int[2];
		create_tv.getLocationOnScreen(location);

		popu1.showAsDropDown(pp, (location[0] + create_tv.getWidth() / 2)
				- popupWidth / 2, location[1] + 60, Gravity.NO_GRAVITY);
		pp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popu1.isShowing()) {
					popu1.dismiss();
				}
				return false;
			}
		});

		pp.findViewById(R.id.btn_jc).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Fragment_ZH.this.getActivity(),
						CreateOrderActivity.class));
				popu1.dismiss();
			}
		});
		pp.findViewById(R.id.btn_gd).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Fragment_ZH.this.getActivity(),
						Activity_GuaDan.class));
				popu1.dismiss();
			}
		});
	}

	class RequesCC extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			nowOrder = new ArrayList<OrderSelect>();
			JsonObjectRequest request = new JsonObjectRequest(
					HttpConstant.SEL_ORDER, null, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							String optString = response.optString("statusCode");
							if (optString.equals("0")) {
								JSONArray array = response.optJSONArray("data");
								for (int i = 0; i < array.length(); i++) {
									JSONObject opt = array.optJSONObject(i);
									OrderSelect orderSelect = new OrderSelect(
											opt);
									nowOrder.add(orderSelect);
								}
								handler.sendEmptyMessage(1);
							} else {
								String msg = response.optString("msg");
								Toast.makeText(getActivity(), msg,
										Toast.LENGTH_LONG).show();
							}

						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
							// TODO Auto-generated method stub

						}
					});
			queue.add(request);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (xyadapter != null) {
				xyadapter.notifyDataSetChanged();
			}
			if (listview != null) {
				listview.onRefreshComplete();
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		new RequesCC().execute();
		super.onResume();
	}
}
