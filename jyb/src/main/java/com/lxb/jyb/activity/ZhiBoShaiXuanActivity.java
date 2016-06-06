package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.ZBSXTextAdapter;
import com.lxb.jyb.bean.DataEntity;
import com.lxb.jyb.bean.ZBFLBean;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ZhiBoShaiXuanActivity extends Activity implements View.OnClickListener {
    private ArrayList<ZBFLBean> zbflBeen;
    private ArrayList<DataEntity> diqulist;
    private ArrayList<DataEntity> yhlist;
    private ArrayList<DataEntity> fllist;
    private GridView fenleigrid, yanghanggrid, diqugrid;
    private TextView top_title, top_txt, item_all, item_zy;
    private ZBSXTextAdapter fenleiadapter, yanghangadapter, diquadapter;
    private String[] selectString;

    private boolean zhongYao = false;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ArrayList<String> selList;
//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zhiboshaixuan);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        sp = getSharedPreferences("setup", Activity.MODE_PRIVATE);
        editor = sp.edit();
        initSpData();
        initFind();


        new getZBFL().execute();
    }

    private void initSpData() {
        selList = new ArrayList<>();
        HashSet<String> strSet = (HashSet<String>) sp.getStringSet("zbset", null);
        selList.addAll(strSet);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initData();
                    initGridView();
                    break;
            }
        }
    };

    private void initGridView() {

        fenleiadapter = new ZBSXTextAdapter(fllist, this, listener1);
        fenleigrid.setAdapter(fenleiadapter);
        diquadapter = new ZBSXTextAdapter(diqulist, this, listener2);
        diqugrid.setAdapter(diquadapter);
        yanghangadapter = new ZBSXTextAdapter(yhlist, this, listener3);
        yanghanggrid.setAdapter(yanghangadapter);

        setListViewHeightBasedOnChildren(fenleigrid);
        setListViewHeightBasedOnChildren(diqugrid);
        setListViewHeightBasedOnChildren(yanghanggrid);
    }

    private void initFind() {

        findViewById(R.id.top_msg).setVisibility(View.GONE);
        top_txt = (TextView) findViewById(R.id.top_txt);
        top_txt.setVisibility(View.VISIBLE);
        top_txt.setText("完成");
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("直播筛选");
        item_all = (TextView) findViewById(R.id.item_all);
        item_zy = (TextView) findViewById(R.id.item_zy);
        fenleigrid = (GridView) findViewById(R.id.fenlei_grid);
        diqugrid = (GridView) findViewById(R.id.diqu_grid);
        yanghanggrid = (GridView) findViewById(R.id.yanghang_grid);

        if (zhongYao) {
            item_zy.setSelected(true);
        } else {
            item_all.setSelected(true);
        }
        initClick();
    }

    private void initClick() {
        findViewById(R.id.top_return).setOnClickListener(this);
        top_txt.setOnClickListener(this);
        item_zy.setOnClickListener(this);
        item_all.setOnClickListener(this);
    }

    private void initData() {
        diqulist = new ArrayList<>();
        fllist = new ArrayList<>();
        yhlist = new ArrayList<>();
        for (int i = 0; i < zbflBeen.size(); i++) {
            if ("央行".equals(zbflBeen.get(i).getKey())) {
                yhlist.addAll(zbflBeen.get(i).getData());

            } else if ("地区".equals(zbflBeen.get(i).getKey())) {
                diqulist.addAll(zbflBeen.get(i).getData());
            } else if ("筛选".equals(zbflBeen.get(i).getKey())) {
                fllist.addAll(zbflBeen.get(i).getData());
            }
        }
        for (int k = 0; k < selList.size(); k++) {
            String str = selList.get(k);
            for (int i = 0; i < yhlist.size(); i++) {
                if (str.equals(yhlist.get(i).getId())) {
                    yhlist.get(i).setIscheck(true);
                }
            }
            for (int i = 0; i < diqulist.size(); i++) {
                if (str.equals(diqulist.get(i).getId())) {
                    diqulist.get(i).setIscheck(true);
                }
            }
            for (int i = 0; i < fllist.size(); i++) {
                if (str.equals(fllist.get(i).getId())) {
                    fllist.get(i).setIscheck(true);
                }
            }

//            if (yhlist.indexOf(str) >= 0) {
//                yhlist.get(yhlist.indexOf(str)).setIscheck(true);
//            }
//            if (diqulist.indexOf(str) >= 0) {
//                diqulist.get(diqulist.indexOf(str)).setIscheck(true);
//            }
//            if (fllist.indexOf(str) >= 0) {
//                fllist.get(fllist.indexOf(str)).setIscheck(true);
//            }
        }

    }
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight()+20;
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_return:
                this.finish();
                break;
            case R.id.item_all:
                zhongYao = false;
                item_all.setSelected(true);
                item_zy.setSelected(false);
                break;
            case R.id.item_zy:
                zhongYao = true;
                item_zy.setSelected(true);
                item_all.setSelected(false);
                break;
            case R.id.top_txt:
                savaChecks();
                break;
        }
    }

    private void savaChecks() {
//        selectString = (String[]) stringList.toArray();
        HashSet<String> strSet = new HashSet<>();
        strSet.addAll(selList);
        editor.putStringSet("zbset", strSet);
        editor.putBoolean("zbzy", zhongYao);
        editor.commit();
//        Intent intent = getIntent();
//
//        intent.putExtra("list", stringList.toString());
//        intent.putExtra("zhongyao", zhongYao);
//
//
//        this.setResult(IntentCode.RESULTCODE, intent);
        this.finish();

    }

    MyClickListener listener1 = new MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            String cid = fllist.get(position).getId();
            if (v.isSelected()) {
                v.setSelected(false);
                if (selList.contains(cid)) {
                    selList.remove(cid);
                }

            } else {
                v.setSelected(true);
                if (!selList.contains(cid)) {
                    selList.add(cid);
                }

            }

        }
    };
    MyClickListener listener2 = new MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            String cid = diqulist.get(position).getId();
            if (v.isSelected()) {
                v.setSelected(false);
                if (selList.contains(cid)) {
                    selList.remove(cid);
                }

            } else {
                v.setSelected(true);
                if (!selList.contains(cid)) {
                    selList.add(cid);
                }

            }
        }
    };
    MyClickListener listener3 = new MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            String cid = yhlist.get(position).getId();
            if (v.isSelected()) {
                v.setSelected(false);
                if (selList.contains(cid)) {
                    selList.remove(cid);
                }

            } else {
                v.setSelected(true);
                if (!selList.contains(cid)) {
                    selList.add(cid);
                }

            }
        }
    };


    class getZBFL extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            zbflBeen = new ArrayList<>();
            String url = HttpConstant.ZBFLHOST;
            HttpGet get = new HttpGet(url);
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(get);
            /*若状态码为200 ok*/
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
            /*读*/
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject dataobject = JSONObject.parseObject(strResult);
                    JSONArray dataarray = dataobject.getJSONArray("data");
                    for (int i = 0; i < dataarray.size(); i++) {
                        JSONObject arrobject = dataarray.getJSONObject(i);
                        ZBFLBean bean = new ZBFLBean();
                        bean.setKey(arrobject.getString("key"));
                        JSONObject listobject = arrobject.getJSONObject("value");


                        JSONArray idarray = JSONArray.parseArray(listobject.keySet().toString());

//                        Log.i("Key",idarray.toString());
//                        ArrayList<String> valuelist = new ArrayList<>();
//                        for (int t = 0; t < listobject.size(); t++) {
//                            valuelist.add(listobject.getString((String) idarray.));
//                        }
                        ArrayList<DataEntity> list = new ArrayList<>();
                        for (int k = 0; k < idarray.size(); k++) {
                            DataEntity entity = new DataEntity(idarray.get(k).toString(), listobject.getString(idarray.get(k).toString()));
                            list.add(entity);
                        }
                        bean.setData(list);
                        zbflBeen.add(bean);
                        Log.i("所有的KEY：", idarray.toString());
                        if (null != zbflBeen) {
                            handler.sendEmptyMessage(1);
                        }
                    }

                    Log.i("jieguo:", strResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("错误：", e.toString());
            }
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
