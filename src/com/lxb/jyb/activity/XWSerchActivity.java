package com.lxb.jyb.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.SearchAdapter;
import com.lxb.jyb.activity.adapter.XWSSTextAdapter;
import com.lxb.jyb.activity.view.MyGridView;
import com.lxb.jyb.activity.view.MyListView;
import com.lxb.jyb.activity.view.SearchView;
import com.lxb.jyb.bean.Bean;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.BaseTools;
import com.lxb.jyb.util.SetStatiColor;

public class XWSerchActivity extends Activity implements OnClickListener,
		SearchView.SearchViewListener {
	/**
	 * 搜索结果列表view
	 */
	private ListView lvResults;

	/**
	 * 搜索view
	 */
	private SearchView searchView;

	/**
	 * 热搜框列表adapter
	 */
	private ArrayAdapter<String> hintAdapter;

	/**
	 * 自动补全列表adapter
	 */
	private ArrayAdapter<String> autoCompleteAdapter;

	/**
	 * 搜索结果列表adapter
	 */
	private SearchAdapter resultAdapter;

	/**
	 * <a href="http://lib.csdn.net/base/14" class="replace_word"
	 * title="MySQL知识库" target="_blank"
	 * style="color:#df3434; font-weight:bold;">数据库</a>数据，总数据
	 */
	private List<Bean> dbData;

	/**
	 * 热搜版数据
	 */
	private List<String> hintData;

	/**
	 * 搜索过程中自动补全数据
	 */
	private List<String> autoCompleteData;

	/**
	 * 搜索结果的数据
	 */
	private List<Bean> resultData;

	/**
	 * 默认提示框显示项的个数
	 */
	private static int DEFAULT_HINT_SIZE = 4;

	/**
	 * 提示框显示项的个数
	 */
	private static int hintSize = DEFAULT_HINT_SIZE;

	/**
	 * 设置提示框显示项的个数
	 * 
	 * @param hintSize
	 *            提示框显示个数
	 */
	public static void setHintSize(int hintSize) {
		XWSerchActivity.hintSize = hintSize;
	}

	private TextView quxiao, queding;
	private GridView gridview;
	private ListView listview;
	private int editlength;
	private XWSSTextAdapter gridadapter;

	private ArrayList<String> arraylit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
		setContentView(R.layout.xinwen_serchactivity);
		initData();
		initFind();

	}

	private void initFind() {
		// TODO Auto-generated method stub
		quxiao = (TextView) findViewById(R.id.quxiao);
		queding = (TextView) findViewById(R.id.queding);
		quxiao.setOnClickListener(this);
		queding.setOnClickListener(this);

		gridview = (GridView) findViewById(R.id.xw_grid);

		gridadapter = new XWSSTextAdapter(arraylit, this, clickListener);
		gridview.setAdapter(gridadapter);

		listview = (ListView) findViewById(R.id.xw_history_list);
		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arraylit));
		BaseTools.setListViewHeightBasedOnChildren(listview);

		lvResults = (ListView) findViewById(R.id.main_lv_search_results);
		searchView = (SearchView) findViewById(R.id.main_search_layout);
		// 设置监听
		searchView.setSearchViewListener(this);
		// 设置adapter
		searchView.setTipsHintAdapter(hintAdapter);
		searchView.setAutoCompleteAdapter(autoCompleteAdapter);
		// BaseTools.setListViewHeightBasedOnChildren(searchView);
		lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long l) {
				Toast.makeText(XWSerchActivity.this, position + "",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	TextWatcher listener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			if (editlength > 0) {
				quxiao.setVisibility(View.GONE);
				queding.setVisibility(View.VISIBLE);
			} else {
				quxiao.setVisibility(View.VISIBLE);
				queding.setVisibility(View.GONE);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private void initData() {
		// TODO Auto-generated method stub
		arraylit = new ArrayList<String>();
		arraylit.add("黄金");
		arraylit.add("外汇");
		arraylit.add("美联储");
		arraylit.add("非农");
		arraylit.add("室友");
		arraylit.add("黄金");
		arraylit.add("外汇");
		arraylit.add("美联储");
		arraylit.add("非农");
		arraylit.add("室友");

		// 从数据库获取数据
		getDbData();
		// 初始化热搜版数据
		getHintData();
		// 初始化自动补全数据
		getAutoCompleteData(null);
		// 初始化搜索结果数据
		getResultData(null);

	}

	/**
	 * 获取db 数据
	 */
	private void getDbData() {
		int size = 100;
		dbData = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			dbData.add(new Bean(R.drawable.ic_launcher, "android开发必备技能"
					+ (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
		}
	}

	/**
	 * 获取热搜版data 和adapter
	 */
	private void getHintData() {
		hintData = new ArrayList<>(hintSize);
		for (int i = 1; i <= hintSize; i++) {
			hintData.add("热搜版" + i + "：Android自定义View");
		}
		hintAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, hintData);
	}

	/**
	 * 获取自动补全data 和adapter
	 */
	private void getAutoCompleteData(String text) {
		if (autoCompleteData == null) {
			// 初始化
			autoCompleteData = new ArrayList<>(hintSize);
		} else {
			// 根据text 获取auto data
			autoCompleteData.clear();
			for (int i = 0, count = 0; i < dbData.size() && count < hintSize; i++) {
				if (dbData.get(i).getTitle().contains(text.trim())) {
					autoCompleteData.add(dbData.get(i).getTitle());
					count++;
				}
			}
		}
		if (autoCompleteAdapter == null) {
			autoCompleteAdapter = new ArrayAdapter<>(this,
					android.R.layout.simple_list_item_1, autoCompleteData);
		} else {
			autoCompleteAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 获取搜索结果data和adapter
	 */
	private void getResultData(String text) {
		if (resultData == null) {
			// 初始化
			resultData = new ArrayList<>();
		} else {
			resultData.clear();
			for (int i = 0; i < dbData.size(); i++) {
				if (dbData.get(i).getTitle().contains(text.trim())) {
					resultData.add(dbData.get(i));
				}
			}
		}
		if (resultAdapter == null) {
			resultAdapter = new SearchAdapter(this, resultData,
					R.layout.item_bean_list);
		} else {
			resultAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
	 * 
	 * @param text
	 */
	public void onRefreshAutoComplete(String text) {
		// 更新数据
		getAutoCompleteData(text);
	}

	/**
	 * 点击搜索键时edit text触发的回调
	 * 
	 * @param text
	 */
	public void onSearch(String text) {
		// 更新result数据
		getResultData(text);
		lvResults.setVisibility(View.VISIBLE);
		// 第一次获取结果 还未配置适配器
		if (lvResults.getAdapter() == null) {
			// 获取搜索数据 设置适配器
			lvResults.setAdapter(resultAdapter);
		} else {
			// 更新搜索数据
			resultAdapter.notifyDataSetChanged();
		}
		Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	MyClickListener clickListener = new MyClickListener() {

		@Override
		public void myOnClick(int position, View v) {
			// TODO Auto-generated method stub

		}
	};
}
