package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.PhoneBean;
import com.lxb.jyb.tool.IntentCode;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.util.ValidatorUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Activity_ZHMM extends Activity implements OnClickListener {
    private TextView top_title, getcode, nextbtn, tishi, codetishi;
    private EditText phone_edit, code_edit;
    private LinearLayout phonedel;
    private JSONObject resultJson;

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
        setContentView(R.layout.activity_zhmm);
        initFind();
    }

    private void initFind() {
        // TODO Auto-generated method stub
        findViewById(R.id.top_return).setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("找回密码");
        nextbtn = (TextView) findViewById(R.id.next_btn);
        getcode = (TextView) findViewById(R.id.get_logincode);
        phone_edit = (EditText) findViewById(R.id.login_phone);
        code_edit = (EditText) findViewById(R.id.yz_code);
        phonedel = (LinearLayout) findViewById(R.id.phone_del);
        codetishi = (TextView) findViewById(R.id.code_tishi);
        tishi = (TextView) findViewById(R.id.phone_tishi);
        getcode.setOnClickListener(this);
        phonedel.setOnClickListener(this);
        nextbtn.setOnClickListener(this);
        phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    phonedel.setVisibility(View.VISIBLE);
                } else {
                    phonedel.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tishi.setText("");
                    if (!phone_edit.getText().toString().isEmpty()) {
                        phonedel.setVisibility(View.VISIBLE);
                    }
                } else {
                    phonedel.setVisibility(View.GONE);
                    if (!ValidatorUtil.isMobile(phone_edit.getText().toString())) {
                        tishi.setText("请输入正确的手机号码！");
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.next_btn:
                if (!ValidatorUtil.isMobile(phone_edit.getText().toString())) {
                    tishi.setText("请输入正确的手机号码！");
                } else if (code_edit.getText().toString().length() < 6) {
                    codetishi.setText("请输入六位验证码");
                } else {
                    new GetPassWord().execute();
                }
                break;

            case R.id.top_return:
                this.finish();
                break;
            case R.id.phone_del:
                phonedel.setVisibility(View.GONE);
                phone_edit.setText("");
                tishi.setText("");

                break;
            case R.id.get_logincode:
                //获取验证码
                if (ValidatorUtil.isMobile(phone_edit.getText().toString())) {

                    new getCode().execute();
                    getcode.setSelected(true);
                    new TimeCount(60000, 1000).start();
                } else {
                    tishi.setText("请输入正确的手机号码！");
                }
                break;
        }

    }

    /**
     * 验证码倒计时
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//     参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getcode.setText("重新获取");
            getcode.setClickable(true);
            getcode.setSelected(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getcode.setClickable(false);
            getcode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    class GetPassWord extends AsyncTask<Void, Void, Void> {
        String phone = phone_edit.getText().toString();

        String url = HttpConstant.GETPASSWORD + "username=" + phone + "&code=" + code_edit.getText().toString();

        @Override
        protected Void doInBackground(Void... params) {
            HttpGet get = new HttpGet(url);
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(get);
            /*若状态码为200 ok*/
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
            /*读*/
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    resultJson = JSONObject.parseObject(strResult);
                    handler.sendEmptyMessage(2);
                    Log.i("jieguo:", strResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("错误：", e.toString());
            }
            return null;
        }
    }

    class getCode extends AsyncTask<Void, Void, Void> {
        String phone = phone_edit.getText().toString();

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
                    handler.sendEmptyMessage(1);
                    Log.i("jieguo:", strResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("错误：", e.toString());
            }
            return null;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    nextbtn.setSelected(true);
                    nextbtn.setEnabled(true);
                    break;
                case 2:
                    switch (resultJson.getString("statusCode")) {
                        case "0":
                            Intent intent = new Intent(Activity_ZHMM.this, Activity_CZMM.class);
                            intent.putExtra("userid", resultJson.getString("userid"));
                            startActivityForResult(intent,
                                    IntentCode.REQUESTCODE);
                            break;
                        case "1":
                            Toast.makeText(Activity_ZHMM.this, "验证码错误！", Toast.LENGTH_LONG).show();
                            break;
                        case "2":
                            Toast.makeText(Activity_ZHMM.this, "该用户未注册！", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentCode.REQUESTCODE) {
            if (resultCode == IntentCode.RESULTCODE) {
                this.finish();
            }
        }
    }
}
