package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.tool.IntentCode;
import com.lxb.jyb.tool.Soft;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.util.ValidatorUtil;
import com.lxb.jyb.util.pase.ObjtoJson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录界面
 */
public class Activity_LOGON extends Activity implements OnClickListener {
    private boolean isKj = false;
    private EditText pas_dit, logon_phone;
    private RelativeLayout logon_lay;
    private TextView logonbtn;
    private ImageView yanjing;
    private LinearLayout phone_del, pas_del, quan;
    private JsonObjectRequest request;
    private RequestQueue queue;
    private TextView tishi_tv;
    private JSONObject loginstatus;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ObjtoJson tojson;
    private InputMethodManager imm;

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
        setContentView(R.layout.activity_logon);
        queue = Volley.newRequestQueue(this);
        tojson = new ObjtoJson();
        sp = getSharedPreferences(IntentCode.SPUSERINFO, Activity.MODE_PRIVATE);
        editor = sp.edit();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initFind();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String phone = logon_phone.getText().toString();
                    String password = pas_dit.getText().toString();
                    switch (loginstatus.getString("statusCode")) {

                        case "ok":
                            editor.putString("phone", phone);
                            editor.putString("password", password);
                            editor.putBoolean("islogin",true);
                            editor.commit();
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("islogin", true);
                            Activity_LOGON.this.setResult(IntentCode.RESULTCODE, intent);
                            Activity_LOGON.this.finish();
                            break;
                        case "error":
                            Toast.makeText(Activity_LOGON.this, "登录失败!", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    };

    private void initFind() {
        // TODO Auto-generated method stub
        findViewById(R.id.wangjimima).setOnClickListener(this);
        findViewById(R.id.to_login).setOnClickListener(this);
        logonbtn = (TextView) findViewById(R.id.logon_btn);
        findViewById(R.id.logon_btn).setOnClickListener(this);
        findViewById(R.id.top_return).setOnClickListener(this);
        phone_del = (LinearLayout) findViewById(R.id.phone_del);
        pas_del = (LinearLayout) findViewById(R.id.pwd_del);
        phone_del.setOnClickListener(this);
        pas_del.setOnClickListener(this);

        logon_lay = (RelativeLayout) findViewById(R.id.logon_lay);
        quan = (LinearLayout) findViewById(R.id.quan_tv);
        quan.setOnClickListener(this);
        logon_lay.setOnClickListener(this);
        logon_phone = (EditText) findViewById(R.id.logon_phone);
        pas_dit = (EditText) findViewById(R.id.pas_dit);
        findViewById(R.id.yj_k).setOnClickListener(this);
        yanjing = (ImageView) findViewById(R.id.yj_img);
        tishi_tv = (TextView) findViewById(R.id.tishi_tv);
        logon_lay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pas_dit.clearFocus();
                        logon_phone.clearFocus();
                        logonbtn.setFocusable(true);
                        break;
                }

                return false;

            }
        });
        logon_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    String phone = logon_phone.getText().toString();
                    if (ValidatorUtil.isMobile(phone)) {
                        tishi_tv.setText("");
                    } else {
                        tishi_tv.setText("手机号错误！");
                    }
                }
            }
        });
        pas_dit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    String password = pas_dit.getText().toString();
                    if (ValidatorUtil.isPassword(password)) {
                        tishi_tv.setText("");
                    } else {
                        tishi_tv.setText("密码格式错误！");
                    }
                }
            }
        });
        logon_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    phone_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pas_dit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    pas_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.wangjimima:
                startActivity(new Intent(this, Activity_ZHMM.class));
                break;

            case R.id.to_login:
                startActivity(new Intent(this, Activity_LOGIN.class));
                break;
            case R.id.logon_btn:
                if (ValidatorUtil.isMobile(logon_phone.getText().toString()) && ValidatorUtil.isPassword(pas_dit.getText().toString())) {
                    tishi_tv.setText("");
                    new LogonMeth().execute();
                } else {
                    if (!ValidatorUtil.isMobile(logon_phone.getText().toString())) {
                        tishi_tv.setText("手机号不正确！");
                    } else if (!ValidatorUtil.isPassword(pas_dit.getText().toString())) {
                        tishi_tv.setText("密码格式错误！");
                    }
                }

                break;
            case R.id.top_return:
                this.setResult(IntentCode.MAINRESULTCODE);
                this.finish();
                break;
            case R.id.yj_k:
                if (isKj) {
                    isKj = false;
                    yanjing.setSelected(false);
                    pas_dit.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isKj = true;
                    yanjing.setSelected(true);
                    pas_dit.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                Soft.setEditTextCursorLocation(pas_dit);
                break;
            case R.id.logon_lay:
                logon_lay.setFocusable(true);
                pas_dit.clearFocus();
                logon_phone.clearFocus();
                logonbtn.setFocusable(true);
                break;
            case R.id.quan_tv:
                pas_dit.clearFocus();
                logon_phone.clearFocus();
                logonbtn.setFocusable(true);
                imm.hideSoftInputFromWindow(pas_dit.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(logon_phone.getWindowToken(), 0);
                break;
            case R.id.phone_del:
                logon_phone.setText("");
                phone_del.setVisibility(View.INVISIBLE);
                break;
            case R.id.pwd_del:
                pas_dit.setText("");
                pas_del.setVisibility(View.INVISIBLE);
                break;
        }
    }

    class LogonMeth extends AsyncTask<Void, Void, Void> {

        String phone = logon_phone.getText().toString();
        String password = pas_dit.getText().toString();

        @Override
        protected Void doInBackground(Void... params) {
            String url = HttpConstant.LOGINHOST + "?loginname=" + phone + "&password=" + password;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            try {
                // 为httpPost设置HttpEntity对象
//                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//                parameters.add(new BasicNameValuePair("loginname", phone));
//                parameters.add(new BasicNameValuePair("password", password));
//                HttpEntity entity = new UrlEncodedFormEntity(parameters);
//                httpPost.setEntity(entity);
                // httpClient执行httpPost表单提交
                HttpResponse response = httpClient.execute(httpPost);
                // 得到服务器响应实体对象
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String str = EntityUtils
                            .toString(responseEntity, "utf-8");

                    loginstatus = JSONObject.parseObject(str);

                    Log.i("登录成功", str);
                    handler.sendEmptyMessage(1);
                } else {
                    System.out.println("服务器无响应！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放资源
                httpClient.getConnectionManager().shutdown();
            }
            return null;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IntentCode.REQUESTCODE){
            if(resultCode==IntentCode.RESULTCODE){
                this.setResult(IntentCode.RESULTCODE);
                this.finish();
            }
        }
    }
}
