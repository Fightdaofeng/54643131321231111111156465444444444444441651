package com.lxb.jyb.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.ImageAdapter;
import com.lxb.jyb.activity.view.ChartItem;
import com.lxb.jyb.activity.view.LineChartItem;
import com.lxb.jyb.bean.CalDetails;
import com.lxb.jyb.bean.DataPoint;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

public class XiangQingActivity extends Activity implements OnClickListener {
	private ImageView xq_img, lk, sjfx, wys, potent;
	private LinearLayout xq_return;
	private ArrayList<CalDetails> list;
	private String id;
	private RequestQueue queue;
	ArrayList<String> xText = new ArrayList<String>();
	private ArrayList<Float> point;
	private boolean contains;
	private TextView lay_title, top_msg, xq_title, zuixin_time, jinzhi,
			yucezhi, qianzhi, jigou, pinlv, shiyi, shuoming;

	private String nexttime = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiangqing);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		// application = (CJRLApplication) getApplication();
		// application.addAct(this);
		findViewById(R.id.xq_layout).setBackgroundColor(
				Color.parseColor("#e9e9ea"));
		initFind();
		initData();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		string = sp.getString(id + "time", "1");
		new Handler().postDelayed((new Runnable() {
			@Override
			public void run() {
				// 实例化SharedPreferences对象（第一步）
				SharedPreferences mySharedPreferences = getSharedPreferences(
						"config", Activity.MODE_PRIVATE);
				String string2 = mySharedPreferences.getString(id + "weilai",
						"1");
				if (!"1".equals(string2)) {
				}
			}
		}), 5);

	}

	/** adapter that supports 3 different item types */
	private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

		public ChartDataAdapter(Context context, List<ChartItem> objects) {
			super(context, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(position, convertView,
					getContext());
		}

		@Override
		public int getItemViewType(int position) {
			// return the views type
			return getItem(position).getItemType();
		}

		@Override
		public int getViewTypeCount() {
			return 1; // we have 3 different item-types
		}
	}

	/**
	 * generates a random ChartData object with just one DataSet
	 * 
	 * @return
	 */
	private LineData generateDataLine(int cnt) {

		ArrayList<Entry> e1 = new ArrayList<Entry>();

		for (int i = 0; i < point.size(); i++) {
			e1.add(new Entry(point.get(i), i));
		}

		// LineDataSet d1 = new LineDataSet(e1, "单位 " + calDetails.getUnit());
		LineDataSet d1 = new LineDataSet(e1, "折线图上下左右可伸缩");
		d1.setLineWidth(1.5f);
		d1.setCircleSize(2.5f);
		d1.setHighLightColor(Color.rgb(244, 117, 117));
		d1.setDrawHorizontalHighlightIndicator(true);
		d1.setDrawValues(false);

		ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
		sets.add(d1);

		LineData cd = new LineData(getMonths(), sets);
		return cd;
	}

	private ArrayList<String> getMonths() {

		ArrayList<String> m = new ArrayList<String>();
		for (int i = 0; i < xText.size(); i++) {
			m.add(xText.get(i) == null ? "没有" + i : xText.get(i));
		}

		return m;
	}

	private void initData() {
		// TODO 初始化传递过来的id和value等值
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		list = new ArrayList<CalDetails>();

		queue = Volley.newRequestQueue(this);

		new RequestData().execute();

	}

	private void initFind() {
		// TODO Auto-generated method stub

		xq_img = (ImageView) findViewById(R.id.xq_img);
		lk = (ImageView) findViewById(R.id.lk);
		xq_return = (LinearLayout) findViewById(R.id.top_return);
		xq_return.setOnClickListener(this);
		lay_title = (TextView) findViewById(R.id.top_title);
		lay_title.setText("日历详情");
		top_msg = (TextView) findViewById(R.id.top_msg);
		xq_title = (TextView) findViewById(R.id.xq_title);
		zuixin_time = (TextView) findViewById(R.id.zuixin_time);
		jinzhi = (TextView) findViewById(R.id.jinzhi_val);
		yucezhi = (TextView) findViewById(R.id.yuce_val);
		qianzhi = (TextView) findViewById(R.id.qianzhi_val);
		jigou = (TextView) findViewById(R.id.jigou);
		pinlv = (TextView) findViewById(R.id.pinlv);
		shiyi = (TextView) findViewById(R.id.shiyi);
		shuoming = (TextView) findViewById(R.id.shuoming);
		potent = (ImageView) findViewById(R.id.xq_potent);
	}

	private class RequestData extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog = new ProgressDialog(XiangQingActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("请稍等...");
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
					HttpConstant.DETAILS_HOST + id, null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							System.out.println("请求结果:" + response.toString());
							JSONArray array = response.optJSONArray("data");

							for (int i = 0; i < array.length(); i++) {
								CalDetails details = new CalDetails(
										array.optJSONObject(i));
								list.add(details);
							}
							handler.sendEmptyMessage(100);

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							AlertDialog.Builder builder = new Builder(
									XiangQingActivity.this);
							builder.setMessage("暂无详情数据，点击返回");

							builder.setTitle("返回提示");

							builder.setPositiveButton("返回",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();

											XiangQingActivity.this.finish();
										}

									});
							builder.show();
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

	/**
	 * 初始化布局
	 */
	protected void initView() {
		// TODO Auto-generated method stub
		sjfx = (ImageView) findViewById(R.id.sjfx);
		sjfx.setOnClickListener(this);
		wys = (ImageView) findViewById(R.id.wys);
		wys.setOnClickListener(this);

		ArrayList<DataPoint> dataPoint = list.get(0).getDataPoint();

		point = new ArrayList<Float>();
		for (int i = 0; i < dataPoint.size(); i++) {
			if (dataPoint.get(i).getValue().contains("%")) {
			}
			contains = dataPoint.get(i).getValue().contains("%");
			if (contains) {
				point.add(Float
						.parseFloat(dataPoint
								.get(i)
								.getValue()
								.substring(
										0,
										dataPoint.get(i).getValue().length() - 1)));
			} else {
				point.add(Float.parseFloat(dataPoint.get(i).getValue()));
			}
			xText.add(dataPoint.get(i).getDate());
		}

		ListView lv = (ListView) findViewById(R.id.map_list);

		ArrayList<ChartItem> list = new ArrayList<ChartItem>();

		list.add(new LineChartItem(generateDataLine(1), getApplicationContext()));

		ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(),
				list);
		lv.setAdapter(cda);
		initTxt();
	}

	private void initTxt() {
		CalDetails calDetails = list.get(0);
		if ("".equals(calDetails.getNewstDatePoint().get(0).getValue())) {

		} else {
			// clickImg.setVisibility(View.VISIBLE);
		}

		switch (calDetails.getImportance()) {
		case "high":
			potent.setBackgroundResource(R.drawable.xing3);
			break;

		case "mid":
			potent.setBackgroundResource(R.drawable.xing2);
			break;
		case "low":
			potent.setBackgroundResource(R.drawable.xing1);
			break;
		}

		name = calDetails.getCountry() + calDetails.getPeriod()
				+ calDetails.getName();
		xq_title.setText(name);
		String publishTime = calDetails.getNewstDatePoint().get(0)
				.getPublishTime();
		String substring = publishTime.substring(0, publishTime.length() - 5);
		String zxtime = substring.substring(0, 4) + "."
				+ substring.substring(5, 7) + "."
				+ substring.substring(8, substring.length()) + "  ";
		zuixin_time.setText(zxtime);

		if (!"".equals(calDetails.getNextpublishTime())) {
			String substring2 = calDetails.getNextpublishTime()
					.substring(5, 16);
			nexttime = substring2.substring(0, 2) + "月"
					+ substring2.substring(3, 5) + "日  "
					+ substring2.substring(6, substring2.length());
		} else {
			// findViewById(R.id.click_img).setVisibility(View.VISIBLE);
		}
		ImageAdapter.setImageView(calDetails.getCountry(), xq_img);
		// if(calDetails.getNewstDatePoint().get(0).getPositiveItem())

		jigou.setText(calDetails.getPublishOrganization());
		pinlv.setText(calDetails.getPublishFrequncy());
		String explanation = "数据释义:" + calDetails.getExplanation();
		SpannableString spannableString = new SpannableString(explanation);
		// 设置字体前景色
		spannableString.setSpan(new ForegroundColorSpan(R.color.tmblack), 0, 4,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		shiyi.setText(spannableString);
		shiyi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		shiyi.getPaint().setAntiAlias(true);
		String interpretation = "说明:" + calDetails.getInterpretation();
		SpannableString spannableString1 = new SpannableString(interpretation);
		// 设置字体前景色
		spannableString1.setSpan(new ForegroundColorSpan(R.color.tmblack), 0,
				2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		shuoming.setText(spannableString1);
		String lastValue = calDetails.getNewstDatePoint().get(0).getLastValue();
		if (lastValue.length() > 0) {
			qianzhi.setText(lastValue);
		} else {
			qianzhi.setText("— —");
		}

		String value = calDetails.getNewstDatePoint().get(0).getValue();

		if (value.length() > 0) {
			System.out.println("今值为" + value);
			jinzhi.setText(value);
		} else {
			jinzhi.setText("— —");
		}

		String preValue = calDetails.getNewstDatePoint().get(0).getPreValue();
		if (preValue.length() > 0) {
			yucezhi.setText(preValue);
		} else {
			yucezhi.setText("— —");
		}

		String ld = calDetails.getNewstDatePoint().get(0).getPositiveItem();
		String lk = calDetails.getNewstDatePoint().get(0).getNegativeItem();
		ArrayList<DataPoint> dataPoint = calDetails.getDataPoint();
		ArrayList<String> yearList = new ArrayList<String>();
		if (dataPoint.size() > 0) {
			String date = dataPoint.get(0).getDate().substring(0, 4);
			yearList.add(date);
			for (int i = 1; i < dataPoint.size(); i++) {
				String substring2 = dataPoint.get(i).getDate().substring(0, 4);
				if (!date.equals(substring2)) {
					yearList.add(substring2);
					date = substring2;
				}
			}
		}

	}

	// public void setListViewHeightBasedOnChildren(ListView listView) {
	// ListAdapter listAdapter = table_list.getAdapter();
	// if (listAdapter == null) {
	// return;
	// }
	//
	// int totalHeight = 0;
	// for (int i = 0; i < listAdapter.getCount(); i++) {
	// View listItem = listAdapter.getView(i, null, listView);
	// listItem.measure(0, 0);
	// totalHeight += listItem.getMeasuredHeight();
	// }
	//
	// ViewGroup.LayoutParams params = listView.getLayoutParams();
	// params.height = totalHeight
	// + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	// params.height += 5;// if without this statement,the listview will be a
	// // little short
	// listView.setLayoutParams(params);
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;
		case R.id.sjfx:
			Intent intent = new Intent(XiangQingActivity.this,
					DJSActivity.class);
			intent.putExtra("index", 0);
			startActivity(intent);
			break;
		}
	}

	private void tiaozhuan() {
		// TODO Auto-generated method stub
		if ("".equals(list.get(0).getNextpublishTime())) {
			Toast.makeText(this, "暂无下次公布时间", Toast.LENGTH_LONG).show();

		} else {
			// CalDetails calDetails = calDetails;
			// String nextpublishTime = calDetails.getNextpublishTime();
			// String substring = nextpublishTime.substring(0,
			// nextpublishTime.length() - 2);
			// Intent intent = new Intent(XiangQingActivity.this,
			// SetTimeActivity.class);
			// intent.putExtra("time", substring);
			// intent.putExtra("id", id);
			// intent.putExtra("value", calDetails.getNewstDatePoint().get(0)
			// .getValue());
			// intent.putExtra("period", calDetails.getPeriod());
			// intent.putExtra("country", calDetails.getCountry());
			// intent.putExtra("name", calDetails.getName());
			// intent.putExtra("predictValue",
			// calDetails.getNewstDatePoint().get(0).getPreValue());
			// intent.putExtra("lastValue",
			// calDetails.getNewstDatePoint().get(0)
			// .getLastValue());
			// intent.putExtra("day", "weilai");
			// startActivityForResult(intent, 100);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		new Handler().postDelayed((new Runnable() {
			@Override
			public void run() {

				String get = data.getStringExtra("time");
				if (get != null || !"".equals(get)) {
					// 实例化SharedPreferences对象（第一步）
					SharedPreferences mySharedPreferences = getSharedPreferences(
							"config", Activity.MODE_PRIVATE);
					String string2 = mySharedPreferences.getString(id
							+ "weilai", "1");
					if (!"1".equals(string2)) {
					}
				}
			}
		}), 5);
	}

	// };

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:

				initView();
				// findViewById(R.id.relative_falsh_show).setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}
	};
	private String string;
	private String name;
}
