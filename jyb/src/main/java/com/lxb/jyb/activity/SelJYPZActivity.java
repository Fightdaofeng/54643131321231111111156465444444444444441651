package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.JYPZitem_adapter;
import com.lxb.jyb.bean.JYPZBean;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.util.SymbolUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelJYPZActivity extends Activity implements OnClickListener {
    private TextView top_title;
    private ArrayList<String> list;
    private ListView listview;
    private RequestQueue queue;
    private JYPZitem_adapter adapter;
    private EditText edit_jypz;
    private ArrayList<JYPZBean> parseExcel;
    private ArrayList<JYPZBean> saipase;
    private LinearLayout ed_del;

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
        setContentView(R.layout.activity_seljypz);
        queue = Volley.newRequestQueue(this);

        initFind();
        new Request().execute();

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    parseExcel = SymbolUtil.parseExcel(SelJYPZActivity.this, list);

                    listview = (ListView) findViewById(R.id.jypz_list);
                    adapter = new JYPZitem_adapter(SelJYPZActivity.this, parseExcel);
                    adapter.setList(parseExcel);
                    listview.setAdapter(adapter);


                    listview.setOnItemClickListener(listener);
                    break;

                case 3:
                    String string = edit_jypz.getText().toString().toUpperCase();
                    saipase = new ArrayList<JYPZBean>();
                    for (int i = 0; i < parseExcel.size(); i++) {
                        JYPZBean bean = parseExcel.get(i);
                        String code = bean.getCode();
                        String name = bean.getName();
                        if (code.indexOf(string) != -1
                                || name.indexOf(string) != -1) {
                            saipase.add(new JYPZBean(code, name, true));
                        }
                    }
                    adapter.setList(saipase);
                    adapter.notifyDataSetChanged();

//
                    break;
                case 4:
                    //请求不到数据

                        System.out.println("禁止软键盘");
                        edit_jypz.setInputType(InputType.TYPE_NULL);

                    break;
            }
        }
    };
    OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            ArrayList<JYPZBean> list2 = adapter.getList();
            JYPZBean jypzBean = list2.get(position);
            Intent intent = new Intent();
            intent.putExtra("symbol", jypzBean.getCode());
            intent.putExtra("name", jypzBean.getName());
            SelJYPZActivity.this.setResult(202, intent);
            SelJYPZActivity.this.finish();
        }
    };

    private void initFind() {
        // TODO Auto-generated method stub

        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("选择交易品种");
        findViewById(R.id.top_return).setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        edit_jypz = (EditText) findViewById(R.id.edit_jypz);
        ed_del = (LinearLayout) findViewById(R.id.ed_del);
        ed_del.setOnClickListener(this);

        edit_jypz.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    ed_del.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessage(3);

                } else {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    class Request extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            list = new ArrayList<String>();
            JsonObjectRequest request = new JsonObjectRequest(
                    HttpConstant.SEL_SYMBOL, null, new Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject arg0) {
                    // TODO Auto-generated method stub
                    JSONArray optJSONArray = arg0.optJSONArray("data");
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        String optString = optJSONArray.optString(i);
                        list.add(optString);
                    }
                    handler.sendEmptyMessage(1);
                }
            }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    handler.sendEmptyMessage(4);
                }
            });
            queue.add(request);
            return null;
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.top_return:
                this.finish();
                break;

            case R.id.ed_del:
                edit_jypz.setText("");
                ed_del.setVisibility(View.GONE);
                break;
        }
    }

}
