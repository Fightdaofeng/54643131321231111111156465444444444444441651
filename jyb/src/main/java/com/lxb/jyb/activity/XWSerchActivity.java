package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.Fragment_All_Adapter;
import com.lxb.jyb.activity.adapter.ListStringAdapter;
import com.lxb.jyb.activity.adapter.SerachXW_Adapter;
import com.lxb.jyb.bean.NewsBean;
import com.lxb.jyb.bean.SerachXWBean;
import com.lxb.jyb.tool.NetworkCenter;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class XWSerchActivity extends Activity implements OnClickListener {

    private TextView quxiao;
    private EditText editxw;
    private PullToRefreshListView pullToRefreshListView;
    private ListView listview, historyListview;
    private TextView serch_btn,tishi;
    private SerachXW_Adapter resultAdater;
    private ArrayList<NewsBean> resultList;
    private String inputstring;
    private ListStringAdapter stringAdapter;
    private LinearLayout del_btn, layout1, layout2,layout3;
    private boolean isRefresh;
    private int page = 1;
    private boolean jxLoadMore = true;
    private boolean isFirst = true;
    public int currentsize = 0;
    private RelativeLayout top;
    private ArrayList<String> history, hislist;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private HashSet<String> hisSet;
    private Button clear_history;

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
        sp = getSharedPreferences("config", Activity.MODE_PRIVATE);
        editor = sp.edit();
        history = new ArrayList<>();
        hisSet = new HashSet<>();
        initFind();
        initSP();

    }

    private void initSP() {
        hisSet = (HashSet<String>) sp.getStringSet("history", null);
        if (null != hisSet) {
            hislist = new ArrayList<>();
            history.addAll(hisSet);
            hislist.addAll(hisSet);
            stringAdapter = new ListStringAdapter(hislist, this);
            historyListview.setAdapter(stringAdapter);
        }

    }


    private void initFind() {
        // TODO Auto-generated method stub
        quxiao = (TextView) findViewById(R.id.quxiao);
        editxw = (EditText) findViewById(R.id.edit_xw);
        clear_history = (Button) findViewById(R.id.clear_history);
        clear_history.setOnClickListener(this);
        historyListview = (ListView) findViewById(R.id.xw_history_list);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.result_list);
        listview = pullToRefreshListView.getRefreshableView();
        serch_btn = (TextView) findViewById(R.id.serch_btn);
        del_btn = (LinearLayout) findViewById(R.id.ed_del);
        layout1 = (LinearLayout) findViewById(R.id.layout_1);
        layout2 = (LinearLayout) findViewById(R.id.jieguo_lay);
        layout3= (LinearLayout) findViewById(R.id.layout_3);
        tishi= (TextView) findViewById(R.id.tishi);
        top = (RelativeLayout) findViewById(R.id.main_rl_listview_to_top_arrow);
        resultList = new ArrayList<>();
        pullToRefreshListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        if (NetworkCenter.isNetworkConnected(XWSerchActivity.this)) {
                            if (!isRefresh) {
                                new RequestBeanTask().execute();
                                isRefresh = true;
                            }
                        } else {
                            Toast.makeText(XWSerchActivity.this, "无网络连接",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        editxw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    del_btn.setVisibility(View.VISIBLE);
                    quxiao.setVisibility(View.GONE);
                    serch_btn.setVisibility(View.VISIBLE);
                } else {
                    souing();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        serch_btn.setOnClickListener(this);
        del_btn.setOnClickListener(this);
        quxiao.setOnClickListener(this);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean newsBean = resultList.get(position);


                Intent intent = new Intent(XWSerchActivity.this,
                        XinWenWebActivity.class);

                intent.putExtra("newsid", newsBean.getNewsId());
                intent.putParcelableArrayListExtra("list", resultList);
                startActivity(intent);
            }
        });

        historyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list = stringAdapter.getList();
                inputstring = list.get(position);
                editxw.setText(inputstring);
                serachMeth();

            }
        });

    }


    private void souing() {
        layout3.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        top.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        quxiao.setVisibility(View.VISIBLE);
        serch_btn.setVisibility(View.GONE);

        del_btn.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.quxiao:
                this.finish();
                break;
            case R.id.serch_btn:
                inputstring = editxw.getText().toString();
                if (inputstring.length() > 0) {

                    serachMeth();
                }
                break;
            case R.id.ed_del:
                editxw.setText("");
                souing();
                break;
            case R.id.clear_history:
                editor.putStringSet("history", null);
                editor.commit();
                hislist.clear();
                stringAdapter.setList(hislist);
                break;
        }
    }

    private void serachMeth() {

        new RequestBeanTask().execute();
    }


    class RequestBeanTask extends AsyncTask<Void, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(Void... params) {

            // TODO Auto-generated method stub
            // arrayList.clear();
            ArrayList<NewsBean> list = new ArrayList<>();
            String url = HttpConstant.NEWSSERACH + inputstring
                    + HttpConstant.NEWS_PAGE + 1;
            HttpClient client = new DefaultHttpClient();
            HttpGet get = null;
            get = new HttpGet(url);
            HttpResponse response;
            try {
                response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine()
                        .getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, "utf-8");
                    JSONArray array = JSONObject.parseObject(data).getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        NewsBean bean = new NewsBean(object, "serach");
                        list.add(bean);
                        // if (i == array.length() - 1) {
                        // page++;
                        // }
                    }
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<NewsBean> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            resultList.clear();
            resultList.addAll(result);
            if(resultList.size()>0) {
                layout3.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                Collections.sort(resultList);

                resultAdater = new SerachXW_Adapter(XWSerchActivity.this, resultList,
                        listview, handler, top);
                listview.setAdapter(resultAdater);
                if (isRefresh) {
                    pullToRefreshListView.onRefreshComplete();
                    isRefresh = false;
                }
                int j = 0;
                for (; j < history.size(); j++) {
                    if (history.get(j).equals(inputstring)) {
                        break;
                    }
                }
                if (j == history.size()) {
                    history.add(inputstring);
                }
                if (history.size() > 10) {
                    for (int i = history.size() - 10; i >= 0; i--) {
                        history.remove(i);
                    }

                }
                if (null != stringAdapter) {
                    stringAdapter.setList(history);
                }
                hisSet = new HashSet<>();
                hisSet.addAll(history);
                editor.putStringSet("history", hisSet);
                editor.commit();
            }else{
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                tishi.setText("\""+inputstring+"\"");

            }

        }
    }

    class LoadMroe extends AsyncTask<Void, Void, List<SerachXWBean>> {

        @Override
        protected List<SerachXWBean> doInBackground(Void... arg0) {

            ArrayList<SerachXWBean> list = new ArrayList<SerachXWBean>();
            page++;
            String url = HttpConstant.NEWSSERACH + inputstring
                    + HttpConstant.NEWS_PAGE + page;

            HttpClient client = new DefaultHttpClient();
            HttpGet get = null;
            get = new HttpGet(url);
            HttpResponse response;
            try {
                response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine()
                        .getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, "utf-8");
                    JSONObject jsobject = JSONObject.parseObject(data);
                    if (null != jsobject) {
                        JSONArray array = jsobject.getJSONArray("data");
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            NewsBean bean = new NewsBean(object, "serach");
                            resultList.add(resultList.size(), bean);
                        }
                        Collections.sort(resultList);
                        handler.sendEmptyMessage(2);
                        if (resultList.size() > 300) {
                            jxLoadMore = false;
                        }
                        handler.sendEmptyMessage(125);
                        currentsize = resultList.size();
                    }
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return list;
        }

    }

    private void loadMore() {
        handler.sendEmptyMessage(108);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 107:
                    loadMore();
                    break;
                case 108:
                    if (jxLoadMore) {
                        new LoadMroe().execute();
                        resultAdater.setDeviceList(resultList);
                    } else {
                        Toast.makeText(XWSerchActivity.this, "无更多数据", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }
    };
}
