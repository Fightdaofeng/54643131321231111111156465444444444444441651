package com.lxb.jyb.activity;

import com.alibaba.fastjson.JSONObject;
import com.lxb.jyb.R;
import com.lxb.jyb.tool.IntentCode;
import com.lxb.jyb.tool.Soft;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.lxb.jyb.util.ValidatorUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Activity_XGMM extends Activity implements OnClickListener {
    private TextView top_title, commitbtn, tishi1, tishi2;
    private boolean isKj1 = false, isKj2 = false;
    private EditText pas_dit1, pas_dit2;
    private ImageView yj1, yj2;
    private LinearLayout del1, del2;
    private JSONObject resultJson;
    private String userid;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

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
        setContentView(R.layout.activity_xiugaimima);
        sp = getSharedPreferences(IntentCode.SPUSERINFO, Activity.MODE_PRIVATE);
        editor = sp.edit();
        getSpData();
        initFind();
    }

    private void getSpData() {
        userid = sp.getString("userid", "");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    switch (resultJson.getString("statusCode")) {
                        case "0":
                            Toast.makeText(Activity_XGMM.this, "修改密码成功！", Toast.LENGTH_LONG).show();
                            Activity_XGMM.this.finish();
                            break;
                        case "1":
                            Toast.makeText(Activity_XGMM.this, "修改密码失败！", Toast.LENGTH_LONG).show();
                            break;
                        case "2":
                            Toast.makeText(Activity_XGMM.this, "旧密码填写错误！", Toast.LENGTH_LONG).show();
                            break;
                        case "3":
                            Toast.makeText(Activity_XGMM.this, "无此用户！", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    };

    private void initFind() {
        // TODO Auto-generated method stub
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("修改密码");
        findViewById(R.id.top_return).setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);

        pas_dit1 = (EditText) findViewById(R.id.pas_dit1);
        findViewById(R.id.yj_k1).setOnClickListener(this);
        yj1 = (ImageView) findViewById(R.id.yj_img1);

        pas_dit2 = (EditText) findViewById(R.id.pas_dit2);
        findViewById(R.id.yj_k2).setOnClickListener(this);
        yj2 = (ImageView) findViewById(R.id.yj_img2);

        del1 = (LinearLayout) findViewById(R.id.del1);
        del2 = (LinearLayout) findViewById(R.id.del2);
        tishi1 = (TextView) findViewById(R.id.tishi1);
        tishi2 = (TextView) findViewById(R.id.tishi2);
        commitbtn = (TextView) findViewById(R.id.commit_btn);

        del1.setOnClickListener(this);
        del2.setOnClickListener(this);
        commitbtn.setOnClickListener(this);
        setEditText1();
        setEditText2();

    }

    private void setEditText1() {
        pas_dit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    del1.setVisibility(View.VISIBLE);
                    tishi1.setText("");
                } else {
                    del1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pas_dit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tishi1.setText("");
                    if (!pas_dit1.getText().toString().isEmpty()) {
                        del1.setVisibility(View.VISIBLE);
                    }
                } else {
                    del1.setVisibility(View.GONE);
                    if (!ValidatorUtil.isPassword(pas_dit1.getText().toString())) {
                        tishi1.setText("密码格式不正确！");
                    }
                }
            }
        });

    }

    private void setEditText2() {
        pas_dit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    del2.setVisibility(View.VISIBLE);
                    tishi2.setText("");
                } else {
                    del2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pas_dit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tishi2.setText("");
                    if (!pas_dit2.getText().toString().isEmpty()) {
                        del2.setVisibility(View.VISIBLE);
                    }
                } else {
                    del2.setVisibility(View.GONE);
                    if (!ValidatorUtil.isPassword(pas_dit2.getText().toString())) {
                        tishi2.setText("密码格式不正确！");
                    }
                }
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

            case R.id.yj_k1:
                if (isKj1) {
                    isKj1 = false;
                    yj1.setSelected(false);
                    pas_dit1.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isKj1 = true;
                    yj1.setSelected(true);
                    pas_dit1.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                Soft.setEditTextCursorLocation(pas_dit1);
                break;
            case R.id.yj_k2:
                if (isKj2) {
                    isKj2 = false;
                    yj2.setSelected(false);
                    pas_dit2.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isKj2 = true;
                    yj2.setSelected(true);
                    pas_dit2.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                Soft.setEditTextCursorLocation(pas_dit2);
                break;
            case R.id.del1:
                del1.setVisibility(View.VISIBLE);
                pas_dit1.setText("");
                tishi1.setText("");
                break;
            case R.id.del2:
                del2.setVisibility(View.VISIBLE);
                pas_dit2.setText("");
                tishi2.setText("");
                break;
            case R.id.commit_btn:
                if (ValidatorUtil.isPassword(pas_dit2.getText().toString())) {
                    new AlterPassWord().execute();
                } else {
                    tishi2.setText("密码格式不正确!");
                }
                break;
        }
    }

    class AlterPassWord extends AsyncTask<Void, Void, Void> {
        String oldpwd = pas_dit1.getText().toString();
        String newpwd = pas_dit2.getText().toString();

        String url = HttpConstant.UPDATEPASSWORD + "userid=" + userid + "&oldPwd=" + oldpwd + "&newPwd=" + newpwd;

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
}
