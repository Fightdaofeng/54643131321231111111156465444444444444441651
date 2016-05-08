package com.lxb.jyb.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.CheckTextViewAdapter;
import com.lxb.jyb.bean.NewsCategory;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.HttpConstant;

public class XinWenFLAcitivity extends Activity implements OnClickListener {
	private ArrayList<NewsCategory> item1_list, item2_list;
	private GridView item1, item2;
	private LinearLayout xw_return;
	private SharedPreferences sp;
	private Editor editor;
	private RequestQueue queue;
	private CheckTextViewAdapter adapter1, adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinwen_shaixuan);

		initObject();
		initFindView();
		handler.sendEmptyMessage(2);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// 布局上面的view

				adapter1 = new CheckTextViewAdapter(item1_list,
						XinWenFLAcitivity.this, mListener1);
				item1.setAdapter(adapter1);

				break;
			case 2:
				// 布局下面的view
				adapter2 = new CheckTextViewAdapter(item2_list,
						XinWenFLAcitivity.this, mListener2);
				item2.setAdapter(adapter2);
				break;
			case 3:
				break;
			}
		}
	};

	private void initFindView() {
		// TODO Auto-generated method stub
		item1 = (GridView) findViewById(R.id.item_del);
		item2 = (GridView) findViewById(R.id.item_add);
		findViewById(R.id.top_return).setOnClickListener(this);
		findViewById(R.id.top_txt).setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

		}
		return super.onKeyDown(keyCode, event);
	}

	private void saveChes() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < item1_list.size(); i++) {
			list.add(item1_list.get(i).getNewsCategory());
		}
		editor.putString("xwdef", list.toString());
		editor.commit();
		XinWenFLAcitivity.this.setResult(200);
		XinWenFLAcitivity.this.finish();
	}

	private void initObject() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("setup", Activity.MODE_PRIVATE);
		editor = sp.edit();
		queue = Volley.newRequestQueue(this);

		item1_list = new ArrayList<NewsCategory>();
		item2_list = new ArrayList<NewsCategory>();

		initData();
		new RequestCeategory().execute();
	}

	private void initData() {
		// TODO Auto-generated method stub
		String stringSet = sp.getString("xwdef", null);

		if (null != stringSet) {
			String substring = stringSet.substring(1, stringSet.length() - 1);
			String[] split = substring.split(",");
			for (int i = 0; i < split.length; i++) {
				NewsCategory newsCategory = new NewsCategory(split[i].replace(
						" ", ""));
				item1_list.add(newsCategory);
			}
		}
		handler.sendEmptyMessage(1);
	}

	class RequestCeategory extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JsonObjectRequest request = new JsonObjectRequest(
					HttpConstant.NEWS_CATEGORY, null,
					new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							JSONArray array = response.optJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								NewsCategory category = new NewsCategory(
										array.optJSONObject(i));
								int k = 0;
								for (; k < item1_list.size(); k++) {
									if (category.getNewsCategory()
											.equals(item1_list.get(k)
													.getNewsCategory())) {
										break;
									}
								}
								if (k == item1_list.size()) {
									item2_list.add(category);
								}
							}
							handler.sendEmptyMessage(2);
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
	}

	/**
	 * 实现类，响应子fragment中的gridview中的item的checkbox点击事件
	 */
	private MyClickListener mListener1 = new MyClickListener() {

		@Override
		public void myOnClick(int position, View v) {
			int tag = (int) v.getTag();
			if (tag < 5) {
				Toast.makeText(XinWenFLAcitivity.this, "系统默认不可删除",
						Toast.LENGTH_SHORT).show();
			} else {
				NewsCategory newsCategory = item1_list.get(tag);
				item2_list.add(newsCategory);
				item1_list.remove(tag);
				adapter1.setList(item1_list);
				adapter2.setList(item2_list);
			}

		}
	};

	/**
	 * 实现类，响应子fragment中的gridview中的item的checkbox点击事件
	 */
	private MyClickListener mListener2 = new MyClickListener() {

		@Override
		public void myOnClick(int position, View v) {
			int tag = (int) v.getTag();
			NewsCategory newsCategory = item2_list.get(tag);
			item1_list.add(newsCategory);
			item2_list.remove(tag);
			adapter1.setList(item1_list);
			adapter2.setList(item2_list);

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_return:
			this.finish();
			break;

		case R.id.top_txt:
			saveChes();
			break;
		}
	}
}
