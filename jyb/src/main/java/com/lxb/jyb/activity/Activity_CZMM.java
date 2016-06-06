package com.lxb.jyb.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.lxb.jyb.tool.IntentCode;
import com.lxb.jyb.tool.Soft;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.util.ValidatorUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Activity_CZMM extends Activity implements OnClickListener {
    private TextView top_title, tishi;
    private LinearLayout del;
    private boolean isKj = false;
    private EditText pas_dit;
    private ImageView yanjing;
    private String userid;
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
        setContentView(R.layout.activity_remima);
        getIntentData();
        initFind();
    }

    private void getIntentData() {
        userid = getIntent().getStringExtra("userid");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    switch (resultJson.getString("statusCode")) {
                        case "0":
                            Activity_CZMM.this.setResult(IntentCode.RESULTCODE);
                            Activity_CZMM.this.finish();
                            break;
                        case "2":
                            Toast.makeText(Activity_CZMM.this, "找回密码失败！", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    };

    private void initFind() {
        // TODO Auto-generated method stub
        findViewById(R.id.commit_btn).setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("重置密码");
        findViewById(R.id.top_return).setOnClickListener(this);
        pas_dit = (EditText) findViewById(R.id.pas_dit);
        findViewById(R.id.yj_k).setOnClickListener(this);
        yanjing = (ImageView) findViewById(R.id.yj_img);

        tishi = (TextView) findViewById(R.id.phone_tishi);
        del = (LinearLayout) findViewById(R.id.phone_del);
        del.setOnClickListener(this);
        pas_dit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    del.setVisibility(View.VISIBLE);
                } else {
                    del.setVisibility(View.GONE);
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
            case R.id.top_return:
                this.finish();
                break;
            case R.id.phone_del:
                tishi.setText("");
                del.setVisibility(View.GONE);
                pas_dit.setText("");
                break;
            case R.id.commit_btn:
                if (ValidatorUtil.isPassword(pas_dit.getText().toString())) {
                    new AlterPassWord().execute();
                } else {
                    tishi.setText("密码格式错误");
                }

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
        }
    }

    class AlterPassWord extends AsyncTask<Void, Void, Void> {
        String pass = pas_dit.getText().toString();

        String url = HttpConstant.ALTERPASSWORD + "userid=" + userid + "&password=" + pas_dit.getText().toString();

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
//                    Log.i("jieguo:", strResult);
                }
            } catch (IOException e) {
                e.printStackTrace();
//                Log.e("错误：", e.toString());
            }
            return null;
        }
    }
}
