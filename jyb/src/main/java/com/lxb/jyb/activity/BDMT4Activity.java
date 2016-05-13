package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.BDMT4LieBiaoListAdapter;
import com.lxb.jyb.bean.BDMT4userinfo;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 绑定MT4账号界面
 */
public class BDMT4Activity extends Activity implements OnClickListener {
    private TextView top_title;
    private EditText edit_jypt, edit_fwqdz, edit_username, edit_userpwd;
    private Button commit_btn;
    private RequestQueue requestQueue;
    private BDMT4userinfo userinfo;
    private JSONObject object;
    private BDMT4LieBiaoListAdapter adapter;
    private ArrayList<String> listString = new ArrayList<>();
    private ArrayList<String> listString2 = new ArrayList<>();
    private ListView ptlist, fwqlist;
    private String jsonstr = "{\"account\":\"900179430\",\"password\":\"0bxsdsw\",\"broker\":\"GMI\",\"server\":\"GMI-Demo01\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bdmt4);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);

        requestQueue = Volley.newRequestQueue(this);
        initFind();
    }

    private void initFind() {
        // TODO Auto-generated method stub
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("绑定MT4账号");
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        edit_jypt = (EditText) findViewById(R.id.edit_jypt);
        edit_fwqdz = (EditText) findViewById(R.id.edit_fwqdz);
        edit_username = (EditText) findViewById(R.id.user_name);
        edit_userpwd = (EditText) findViewById(R.id.user_pwd);
        commit_btn = (Button) findViewById(R.id.commit_btn);
        ptlist = (ListView) findViewById(R.id.pt_list);
        fwqlist = (ListView) findViewById(R.id.fwq_lsit);
        listString.add("GMI");
        listString2.add("GMI-Demo01");
        setListAdapter(listString, ptlist, 1);
        setListAdapter(listString2, fwqlist, 2);


        edit_jypt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ptlist.getVisibility() == View.GONE) {
                    ptlist.setVisibility(View.VISIBLE);
                } else {
                    ptlist.setVisibility(View.GONE);
                }
            }
        });
        edit_jypt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findViewById(R.id.pt_list).setVisibility(View.VISIBLE);
                if (hasFocus) {
                    //此处为得到焦点事件
                    ptlist.setVisibility(View.VISIBLE);
                } else {
                    //此处为失去焦点
                    ptlist.setVisibility(View.GONE);
                }
            }


        });
        edit_fwqdz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fwqlist.getVisibility() == View.GONE) {
                    fwqlist.setVisibility(View.VISIBLE);
                } else {
                    fwqlist.setVisibility(View.GONE);
                }
            }
        });
        edit_fwqdz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点事件
                    fwqlist.setVisibility(View.VISIBLE);
                } else {
                    //此处为失去焦点
                    fwqlist.setVisibility(View.GONE);
                }
            }
        });
        setOnclick();
    }

    private void setListAdapter(final ArrayList<String> list, ListView listview, final int index) {
        adapter = new BDMT4LieBiaoListAdapter(list, BDMT4Activity.this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BDMT4Activity.this, "当前点击了" + list.get(position), Toast.LENGTH_LONG).show();
                if (index == 1) {
                    edit_jypt.setText(listString.get(position));
                } else if (index == 2) {
                    edit_fwqdz.setText(listString2.get(position));
                }
            }
        });
    }


    private void setOnclick() {
        // TODO
        findViewById(R.id.top_return).setOnClickListener(this);
        commit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.top_return:
                this.finish();
                break;

            case R.id.commit_btn:
                if (checkText()) {
                    userinfo = new BDMT4userinfo();
                    userinfo.setAccount(edit_username.getText().toString());
                    userinfo.setPassword(edit_userpwd.getText().toString());
                    userinfo.setBroker(edit_jypt.getText().toString());
                    userinfo.setServer(edit_fwqdz.getText().toString());
                    try {
                        object = new JSONObject(userinfo.toJSON().toString());
//                        object = new JSONObject(jsonstr);
                    } catch (JSONException e) {

                        e.printStackTrace();
                        Toast.makeText(BDMT4Activity.this, "转换错误" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                    new BDZHPUT().execute();
                }
                break;
        }
    }

    private boolean checkText() {
        if (edit_username.getText().toString().isEmpty()) {
            Toast.makeText(BDMT4Activity.this, "请输入用户名！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edit_userpwd.getText().toString().isEmpty()) {
            Toast.makeText(BDMT4Activity.this, "请输入密码！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edit_jypt.getText().toString().isEmpty()) {
            Toast.makeText(BDMT4Activity.this, "请选择交易平台！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edit_fwqdz.getText().toString().isEmpty()) {
            Toast.makeText(BDMT4Activity.this, "请选择服务器地址！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    class BDZHPUT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, HttpConstant.BDMT4_HOST,
                    object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    System.out.println(jsonObject.toString());
                    Toast.makeText(BDMT4Activity.this, "绑定成功" + jsonObject.toString(), Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("错误信息：", volleyError.toString());
                    Toast.makeText(BDMT4Activity.this, "绑定出问题" + volleyError.toString(), Toast.LENGTH_LONG).show();
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
            requestQueue.add(JsonObjectRequest);
            return null;
        }
    }
}
