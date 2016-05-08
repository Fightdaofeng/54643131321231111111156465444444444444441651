package com.lxb.jyb.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.HQData;
import com.lxb.jyb.bean.OrderDataBean;
import com.lxb.jyb.fragment.Fragment_ZH;
import com.lxb.jyb.tool.TestUtil;
import com.lxb.jyb.util.HQTitleUtil;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

/**
 * 建仓-新挂单界面
 *
 * @author Liuxiaobin
 */
public class CreateOrderActivity extends FragmentActivity implements
        OnClickListener {
    private LinearLayout zuoduo_layout, zuokong_layout;
    private TextView top_title, sel_jypz;
    private ImageView switch1, switch2, zy_jia, zy_jian, zs_jia, zsjian;
    private String testUpdate;
    private RequestQueue queue;
    private JSONObject object;
    private StringBuffer result;
    private JsonObjectRequest request;
    private String account = "900171576", broker = "GMI", symbol, lots,
            ordertype, sl, tp, comment = "Android Test", price = "1340",
            expiretime;
    private EditText edit_lots, edit_zy, edit_zs;
    private HQData hqdata;
    private String stringExtra;
    private SimpleDateFormat sdf;
    private OrderDataBean bean;
    private double mode;
    private String string;
    private double parseDouble;
    private double put;
    private MiusThread miusThread;
    private boolean isOnLongClick, isDouble = false;
    private PlusThread plusThread;
    private String edname = "";
    private CompentOnTouch touch;
    private String titck;
    private int length;
    private String msginfo;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_neworder);
        initFind();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String string2;
            double parseDouble2;
            AlertDialog.Builder builder = null;
            switch (msg.what) {
                case 1:
                    // 减
                    switch (edname) {
                        case "zy":
                            string2 = edit_zy.getText().toString();
                            parseDouble2 = Double.parseDouble(string2);
                            edit_zy.setText((parseDouble2 - mode) + "");
                            break;

                        case "zs":
                            string2 = edit_zs.getText().toString();
                            parseDouble2 = Double.parseDouble(string2);
                            edit_zs.setText((parseDouble2 - mode) + "");
                            break;

                    }
                    break;

                case 2:
                    // 加
                    switch (edname) {
                        case "zy":
                            string2 = edit_zy.getText().toString();
                            parseDouble2 = Double.parseDouble(string2);
                            edit_zy.setText((parseDouble2 + mode) + "");
                            break;

                        case "zs":
                            string2 = edit_zs.getText().toString();
                            parseDouble2 = Double.parseDouble(string2);
                            edit_zs.setText((parseDouble2 + mode) + "");
                            break;
                    }
                    break;
                case 101:
                    String zy, zs;
                    if (tp.equals("0")) {
                        zs = "未设置！";
                    } else {
                        zs = tp;
                    }
                    if (sl.equals("0")) {
                        zy = "未设置！";
                    } else {
                        zy = sl;
                    }
                    builder = new Builder(
                            CreateOrderActivity.this);

                    builder.setMessage("\"订单号：" + titck + "  " + symbol + "  买进"
                            + lots + "止损：" + zs + "  止盈：" + zy);

                    builder.setTitle("下单成功");
                    builder.setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    CreateOrderActivity.this.finish();

                                }

                            });


                    break;
                case 102:
                    zuoduo_layout.setClickable(false);
                    zuokong_layout.setClickable(false);

                    break;
                case 404:
                    if (ordertype.equals("0")) {
                        zuokong_layout.setClickable(false);
                    } else {
                        zuoduo_layout.setClickable(false);
                    }
                    switch (msginfo) {
                        case "Not enough money":
                            builder = new Builder(
                                    CreateOrderActivity.this);

                            builder.setMessage("账户余额不足！");

                            builder.setTitle("下单失败");
                            builder.setPositiveButton("确认",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();

                                        }

                                    });

                            break;
                        case "Market is closed":
                            builder = new Builder(
                                    CreateOrderActivity.this);

                            builder.setMessage("该品种暂未开市！");

                            builder.setTitle("下单失败");
                            builder.setPositiveButton("确认",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();

                                        }

                                    });

                            break;
                    }

                    break;
            }
            builder.show();
        }

        ;
    };

    private void initFind() {
        // TODO Auto-generated method stub
        queue = Volley.newRequestQueue(this);

        findViewById(R.id.top_return).setOnClickListener(this);
        findViewById(R.id.top_msg).setVisibility(View.INVISIBLE);
        zuoduo_layout = (LinearLayout) findViewById(R.id.zuoduo_btn);
        zuoduo_layout.setOnClickListener(this);

        zuokong_layout = (LinearLayout) findViewById(R.id.zuokong_btn);
        zuokong_layout.setOnClickListener(this);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("新订单");


        switch1 = (ImageView) findViewById(R.id.switch1);
        switch2 = (ImageView) findViewById(R.id.switch2);
        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);

        edit_zy = (EditText) findViewById(R.id.edit_zy);
        edit_zs = (EditText) findViewById(R.id.edit_zs);
        edit_lots = (EditText) findViewById(R.id.edit_lots);
        findViewById(R.id.sel_jypz).setOnClickListener(this);

        zy_jia = (ImageView) findViewById(R.id.zy_jia);
        zy_jian = (ImageView) findViewById(R.id.zy_jian);
        zs_jia = (ImageView) findViewById(R.id.zs_jia);
        zsjian = (ImageView) findViewById(R.id.zs_jian);
        sel_jypz = (TextView) findViewById(R.id.jypz_tv);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        touch = new CompentOnTouch();
        zy_jia.setOnClickListener(this);
        zy_jian.setOnClickListener(this);
        zs_jia.setOnClickListener(this);
        zsjian.setOnClickListener(this);
        mode = getMode(edit_zy);
        // zy_jian.setOnTouchListener(touch);
        // zy_jia.setOnTouchListener(touch);
        // zs_jia.setOnTouchListener(touch);
        // zsjian.setOnTouchListener(touch);

    }

    @Override
    public void onClick(View v) {
        // TODO 点击事件

        switch (v.getId()) {
            case R.id.top_return:
                this.finish();
                break;

            case R.id.switch1:
                if (switch1.isSelected()) {
                    switch1.setSelected(false);
                    findViewById(R.id.set_1).setVisibility(View.GONE);
                } else {
                    switch1.setSelected(true);
                    findViewById(R.id.set_1).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.switch2:
                if (switch2.isSelected()) {
                    switch2.setSelected(false);
                    findViewById(R.id.set_2).setVisibility(View.GONE);
                } else {
                    switch2.setSelected(true);
                    findViewById(R.id.set_2).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.zuoduo_btn:
                if (checkEditString()) {
                    initBean();
                    result = TestUtil.getResult();
                    ordertype = "0";
                    bean = new OrderDataBean(account, broker, symbol, lots, ordertype,
                            sl, tp, comment, price, expiretime);

                    getHttpData();
                }
                break;
            case R.id.zuokong_btn:
                if (checkEditString()) {
                    initBean();
                    ordertype = "1";
                    bean = new OrderDataBean(account, broker, symbol, lots, ordertype,
                            sl, tp, comment, price, expiretime);
                    getHttpData();
                }
                break;
            case R.id.sel_jypz:
                startActivityForResult(new Intent(this, SelJYPZActivity.class), 101);
                break;
            case R.id.zy_jia:

                string = edit_zy.getText().toString().replace(" ", "");

                parseDouble = Double.parseDouble(string);
                put = parseDouble + mode;
                if (isDouble) {
                    edit_zy.setText((put + "0").substring(0, length));
                } else {
                    edit_zy.setText(put + "");
                }
                break;
            case R.id.zy_jian:
                string = edit_zy.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(string);
                put = parseDouble - mode;
                if (isDouble) {
                    edit_zy.setText((put + "0").substring(0, length));
                } else {
                    edit_zy.setText((put + ""));
                }
                edit_zy.postInvalidate();
                break;
            case R.id.zs_jia:
                string = edit_zs.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(string);
                put = parseDouble + mode;
                if (isDouble) {
                    edit_zs.setText((put + "0").substring(0, length));
                } else {
                    edit_zs.setText((put + ""));
                }
                break;
            case R.id.zs_jian:
                string = edit_zs.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(string);
                put = parseDouble - mode;
                if (isDouble) {
                    edit_zs.setText((put + "0").substring(0, length));
                } else {
                    edit_zs.setText((put + ""));
                }
                edit_zs.postInvalidate();
                break;
        }
    }

    private boolean checkEditString() {
        if ("无".equals(sel_jypz.getText().toString())) {
            Toast.makeText(CreateOrderActivity.this, "请选择交易品种!", Toast.LENGTH_LONG).show();
            return false;
        }
        String str = edit_lots.getText().toString();
        if (str.isEmpty()) {
            Toast.makeText(CreateOrderActivity.this, "请输入交易数量！", Toast.LENGTH_LONG).show();
            return false;
        }
        int indexdian = str.indexOf(".");
        int indexmo = str.lastIndexOf(".");
        if (indexdian != indexmo || indexdian == 0 || indexdian == str.length() - 1) {
            Toast.makeText(CreateOrderActivity.this, "输入的数量格式不正确！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void onTouchChange(String methodName, int eventAction) {
        // 按下松开分别对应启动停止减线程方法
        if ("mius".equals(methodName)) {
            if (eventAction == MotionEvent.ACTION_DOWN) {
                miusThread = new MiusThread();
                isOnLongClick = true;
                miusThread.start();
            } else if (eventAction == MotionEvent.ACTION_UP) {
                if (miusThread != null) {
                    isOnLongClick = false;
                }
            } else if (eventAction == MotionEvent.ACTION_MOVE) {
                if (miusThread != null) {
                    isOnLongClick = true;
                }
            }
        }
        // 按下松开分别对应启动停止加线程方法
        else if ("plus".equals(methodName)) {
            if (eventAction == MotionEvent.ACTION_DOWN) {
                plusThread = new PlusThread();
                isOnLongClick = true;
                plusThread.start();
            } else if (eventAction == MotionEvent.ACTION_UP) {
                if (plusThread != null) {
                    isOnLongClick = false;
                }
            } else if (eventAction == MotionEvent.ACTION_MOVE) {
                if (plusThread != null) {
                    isOnLongClick = true;
                }
            }
        }
    }

    // 减操作
    class MiusThread extends Thread {
        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(200);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    // 加操作
    class PlusThread extends Thread {
        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(200);
                    handler.sendEmptyMessage(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    // Touch事件
    class CompentOnTouch implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                // 这是btnMius下的一个层，为了增强易点击性
                case R.id.zy_jian:
                    edname = "zy";
                    onTouchChange("mius", event.getAction());
                    break;
                // 这里也写，是为了增强易点击性
                case R.id.zs_jian:
                    edname = "zs";
                    onTouchChange("mius", event.getAction());
                    break;
                case R.id.zy_jia:
                    edname = "zy";
                    onTouchChange("plus", event.getAction());
                    break;
                case R.id.zs_jia:
                    edname = "zs";
                    onTouchChange("plus", event.getAction());
                    break;
            }
            return true;
        }
    }

    private double getMode(EditText editText) {
        int wei;
        double re = 0;
        String string = editText.getText().toString();
        length = string.length();
        int dian = string.indexOf(".");
        if (dian > 0) {
            wei = length - (dian + 1);
        } else {
            wei = 0;
        }
        switch (wei) {
            case 0:
                re = 1;
                break;
            case 1:
                isDouble = true;

                re = 0.1;
                break;
            case 2:
                isDouble = true;
                re = 0.01;
                break;
            case 3:
                isDouble = true;
                re = 0.001;
                break;
            case 4:
                isDouble = true;
                re = 0.0001;
                break;
        }

        return re;
    }

    private void initBean() {
        Date date = new Date();
        expiretime = sdf.format(date);
        symbol = sel_jypz.getText().toString();
        lots = edit_lots.getText().toString();
        if (switch1.isSelected()) {
            sl = edit_zy.getText().toString();
        } else {
            sl = "0";
        }
        if (switch2.isSelected()) {
            tp = edit_zs.getText().toString();
        } else {
            tp = "0";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 202) {
                stringExtra = data.getStringExtra("symbol");
                sel_jypz.setText(stringExtra);
            }
        }
    }

    public void getHttpData() {

        try {
            request = new JsonObjectRequest(Method.POST,
                    HttpConstant.CREATE_ORDER, new JSONObject(bean.toJSON()
                    .toString()), new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jsonObject) {
                    JSONObject optJSONObject = jsonObject
                            .optJSONObject("data");
                    Log.i("请求:",bean.toJSON().toString());
                    Log.i("借口:",HttpConstant.CALENDAR_HOST);

                    titck = optJSONObject.optString("ticket");
                    String optString = jsonObject.optString("msg");
                    if ("".equals(optString)) {
                        handler.sendEmptyMessage(101);
                    } else {
                        msginfo = optString.toString();
                        handler.sendEmptyMessage(404);

                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    Log.i("info", arg0.toString());
                    Toast.makeText(CreateOrderActivity.this, "错误信息" + arg0.toString(), Toast.LENGTH_LONG);
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
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 超时时间10s,最大重连次数2次
        queue.add(request);
    }

}
