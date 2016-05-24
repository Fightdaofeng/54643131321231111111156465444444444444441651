package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.PhoneBean;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Activity_LOGIN extends Activity implements OnClickListener {
    private TextView top_title, get_logincode, phone_tishi, pas_tishi;
    private LinearLayout phone_del, pwd_del;
    private boolean isKj = false;
    private EditText login_phone, pas_dit, login_code;
    private ImageView yanjing;
    private ObjtoJson util;
    private JSONObject loginstatus;

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
        setContentView(R.layout.activity_login);
        util = new ObjtoJson();
        initFind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IntentCode.REQUESTCODE){
                if(resultCode==IntentCode.RESULTCODE){
                    Activity_LOGIN.this.setResult(IntentCode.RESULTCODE);
                    this.finish();
                }
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    switch (loginstatus.getString("statusCode")) {
                        case "0":
                            //注册成功
                            Intent intent = new Intent(Activity_LOGIN.this, Activity_PUTINFO.class);
                            intent.putExtra("action", "login");
                            intent.putExtra("phone", login_phone.getText());
                            intent.putExtra("password", pas_dit.getText());
                            startActivityForResult(intent, IntentCode.REQUESTCODE);
                            break;
                        case "1":
                            //注册失败
                            Toast.makeText(Activity_LOGIN.this, "注册失败！", Toast.LENGTH_LONG).show();
                            break;
                        case "2":
                            //验证码错误
                            Toast.makeText(Activity_LOGIN.this, "验证码错误！", Toast.LENGTH_LONG).show();
                            break;
                        case "3":
                            //已经注册过
                            Toast.makeText(Activity_LOGIN.this, "手机号已注册！", Toast.LENGTH_LONG).show();
                            break;
                    }

                    break;
            }
        }
    };

    private void initFind() {
        // TODO Auto-generated method stub
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("注册");
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        findViewById(R.id.top_return).setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);
        pas_dit = (EditText) findViewById(R.id.pas_dit);
        findViewById(R.id.yj_k).setOnClickListener(this);
        yanjing = (ImageView) findViewById(R.id.yj_img);
        get_logincode = (TextView) findViewById(R.id.get_logincode);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_code = (EditText) findViewById(R.id.login_code);
        get_logincode.setOnClickListener(this);
        findViewById(R.id.to_xieyi).setOnClickListener(this);
        phone_tishi = (TextView) findViewById(R.id.phone_tishi);
        pas_tishi = (TextView) findViewById(R.id.pas_tishi);
        phone_del = (LinearLayout) findViewById(R.id.phone_del);
        pwd_del = (LinearLayout) findViewById(R.id.pwd_del);
        phone_del.setOnClickListener(this);
        pwd_del.setOnClickListener(this);

        login_phone.addTextChangedListener(new TextWatcher() {
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
                    pwd_del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    String phone = login_phone.getText().toString();
                    if (ValidatorUtil.isMobile(phone)) {
                        phone_tishi.setText("");
                    } else {
                        phone_tishi.setText("手机号错误！");
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
                        pas_tishi.setText("");
                    } else {
                        pas_tishi.setText("密码格式错误！");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.to_xieyi:
                startActivity(new Intent(Activity_LOGIN.this, XieYiActivity.class));
                break;
            case R.id.top_return:
                this.finish();
                break;

            case R.id.login_btn:

                new LoginMeth().execute();

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
            case R.id.get_logincode:
                //获取验证码
                if (ValidatorUtil.isMobile(login_phone.getText().toString())) {

                    new getCode().execute();
                    get_logincode.setSelected(true);
                    new TimeCount(60000, 1000).start();
                } else {
                    Toast.makeText(Activity_LOGIN.this, "请输入正确的手机号码！", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.phone_del:
                login_phone.setText("");
                phone_del.setVisibility(View.GONE);

                break;
            case R.id.pwd_del:
                pas_dit.setText("");
                pwd_del.setVisibility(View.GONE);
                break;

        }
    }

    class LoginMeth extends AsyncTask<Void, Void, Void> {

        String phone = login_phone.getText().toString();
        String password = pas_dit.getText().toString();
        String code = login_code.getText().toString();

        @Override
        protected Void doInBackground(Void... params) {
            String url=HttpConstant.REGISTERHOST+"?username="+phone+"&password="+password+"&code="+code;
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpPost = new HttpGet(url);

            try {
                // 为httpPost设置HttpEntity对象
//                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//                parameters.add(new BasicNameValuePair("username", phone));
//                parameters.add(new BasicNameValuePair("password", password));
//                parameters.add(new BasicNameValuePair("code", code));
//                HttpEntity entity = new UrlEncodedFormEntity(parameters);
//                httpPost.setEntity(entity);
                // httpClient执行httpPost表单提交
                HttpResponse response = httpClient.execute(httpPost);
                // 得到服务器响应实体对象
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String string = EntityUtils.toString(responseEntity, "utf-8");
                    Log.i("返回的注册信息", string);
//                    String str = util.toJson(string);
                    loginstatus = JSONObject.parseObject(string);
                    handler.sendEmptyMessage(1);
                    System.out.println("表单上传成功！");
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

    /**
     * 验证码倒计时
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            get_logincode.setText("重新获取");
            get_logincode.setClickable(true);
            get_logincode.setSelected(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            get_logincode.setClickable(false);
            get_logincode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    class getCode extends AsyncTask<Void, Void, Void> {
        String phone = login_phone.getText().toString();
        PhoneBean phonebean = new PhoneBean(phone);
        //        String jsonstr = util.toJson(phonebean);
        String url = HttpConstant.GETLOGINCODE + phone;

        @Override
        protected Void doInBackground(Void... params) {
            HttpGet get = new HttpGet(url);
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(get);
            /*若状态码为200 ok*/
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
            /*读*/
                    String strResult = EntityUtils.toString(httpResponse.getEntity());

                    Log.i("jieguo:", strResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("错误：", e.toString());
            }
            return null;
        }
    }

}
