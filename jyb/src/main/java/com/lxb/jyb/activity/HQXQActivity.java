package com.lxb.jyb.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.HQData;
import com.lxb.jyb.fragment.DuoKongBiFragment;
import com.lxb.jyb.fragment.Fragment_ZH;
import com.lxb.jyb.fragment.Fragment_cuntou;
import com.lxb.jyb.fragment.JGYJFragment;
import com.lxb.jyb.fragment.TuBiaoFragment;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HQXQActivity extends FragmentActivity implements OnClickListener {
    private TuBiaoFragment tbfragment;
    private DuoKongBiFragment dkbfragment;
    private JGYJFragment jgyjfragment;
    private Fragment_cuntou fragment_cuntou;
    private TextView tv1, tv2, tv3, tv4, top_msg, create_tv,top_title;
    private View v1, v2, v3, v4;
    private FragmentManager fragmentmanager;
    private FragmentTransaction fragmenttransaction;
    private PopupWindow popu1;
    private HQData data;
    private int baoliu;
    private String code, name;
    private RequestQueue queue;
    private TextView newprice, zhangdie, zhangdiefu, high, low, close, open, zxbtn;
    private ImageView static_icon;
    private DecimalFormat df1 = null;
    private DecimalFormat zdf = new DecimalFormat("0.00");
    private SharedPreferences preferences;
    private SharedPreferences.Editor defEditor;
    private Set<String> stringSet;
    private ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hq_xq_activity);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        getIntentDate();
        initFind();

        fragmentmanager = getSupportFragmentManager();
        setTabSelection(0);
        handler.sendEmptyMessage(1);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    new RequestD().execute();
                    break;
                case 2:
                    initViewText();
                    break;
            }
        }
    };

    class RequestD extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            JsonArrayRequest arrayRequest = new JsonArrayRequest(
                    HttpConstant.HQ_HOST + code, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray arg0) {
                    // TODO Auto-generated method stub
                    for (int i = 0; i < arg0.length(); i++) {
                        data = new HQData(arg0.optJSONObject(i),
                                baoliu, name);
                    }
                    handler.sendEmptyMessage(2);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub

                }
            });
            arrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000,//
                    // 默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(arrayRequest);
            return null;
        }
    }

    private void getIntentDate() {
        baoliu = getIntent().getIntExtra("bl", 100);
        code = getIntent().getStringExtra("code");
        name = getIntent().getStringExtra("name");
        queue = Volley.newRequestQueue(this);
        preferences = this.getSharedPreferences("defaulthq",
                Activity.MODE_PRIVATE);
        defEditor = preferences.edit();
        stringSet = preferences.getStringSet("hqdef", null);
        if (null != stringSet) {
            names = new ArrayList<String>();
            names.addAll(stringSet);
        }
    }

    /**
     * 为第一个页面的控件赋值
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initViewText() {

        double close2 = data.getClose();
        // 保留位数计算
        // 涨跌 changepercent
        if (baoliu == 10000) {
            df1 = new DecimalFormat("0.0000");
        } else {
            df1 = new DecimalFormat("0.00");
        }
        double changePercent = data.getChangePercent();
        // 涨跌
        String format = String.format("%f", changePercent);

        double dob = Double.parseDouble(format);
        double zx = dob * 100;

        String percent = zdf.format(zx);
        if ("-".equals(percent.substring(0, 1))) {
            newprice.setTextColor(getResources().getColor(R.color.green));
            zhangdie.setTextColor(getResources().getColor(R.color.green));
            zhangdiefu.setTextColor(getResources().getColor(R.color.green));
            static_icon.setBackground(getResources().getDrawable(R.drawable.shangsanjia));
        } else {
            percent = "+" + percent;
            newprice.setTextColor(getResources().getColor(R.color.hq_red));
            zhangdie.setTextColor(getResources().getColor(R.color.hq_red));
            zhangdiefu.setTextColor(getResources().getColor(R.color.hq_red));
            static_icon.setBackground(getResources().getDrawable(R.drawable.shangsanjiao));
        }
        zhangdiefu.setText(percent + "%");
        // 最新价 newPrice
        double newPrice = data.getNewPrice();
        newprice.setText(df1.format(newPrice));
        // 涨跌 change
        double changeStr = data.getChange();
        zhangdie.setText(df1.format(changeStr));

        // 最高 high
        double hight = data.getHight();
        high.setText(df1.format(hight));
        // 最低 low
        double low2 = data.getLow();
        low.setText(df1.format(low2));
        // 今开盘 open
        double open2 = data.getOpen();
        open.setText(df1.format(open2));
        // 昨收盘

        close.setText(df1.format(close2));

        handler.sendEmptyMessageDelayed(1, 2000);
    }

    private int checkChange(double x, double y) {

        if (x < y) {
            // 跌了，绿色
            return 0;
        } else if (x == y) {
            // 平了，灰色
            return 1;
        } else {
            // 涨了，红色
            return 2;
        }
    }

    private void initFind() {
        // TODO Auto-generated method stub
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        create_tv = (TextView) findViewById(R.id.create_tv);
        top_msg = (TextView) findViewById(R.id.top_msg);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        create_tv.setOnClickListener(this);
        top_msg.setOnClickListener(this);
        findViewById(R.id.top_return).setOnClickListener(this);

        top_title=(TextView)findViewById(R.id.top_title);
        top_title.setText(name);

        newprice = (TextView) findViewById(R.id.newprice);
        zhangdie = (TextView) findViewById(R.id.zhangdie);
        zhangdiefu = (TextView) findViewById(R.id.zhangdiefu);
        high = (TextView) findViewById(R.id.high);
        low = (TextView) findViewById(R.id.low);
        close = (TextView) findViewById(R.id.close);
        open = (TextView) findViewById(R.id.open);
        static_icon = (ImageView) findViewById(R.id.static_icon);
        zxbtn = (TextView) findViewById(R.id.zx_btn);
        zxbtn.setSelected(false);
        zxbtn.setText("加自选");
        for (int i = 0; i < names.size(); i++) {
            if (name.equals(names.get(i))) {
                zxbtn.setSelected(true);
                zxbtn.setText("删自选");

                break;
            }
        }
        zxbtn.setOnClickListener(this);

    }

    private void retBtn() {
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.INVISIBLE);
        v3.setVisibility(View.INVISIBLE);
        v4.setVisibility(View.INVISIBLE);

    }

    /**
     * 隐藏fragment
     *
     * @param fragmenttransaction2
     */
    private void heidFragment(FragmentTransaction fragmenttransaction2) {
        // TODO Auto-generated method stub

        if (tbfragment != null) {
            fragmenttransaction2.hide(tbfragment);
        }
        if (jgyjfragment != null) {
            fragmenttransaction2.hide(jgyjfragment);
        }
        if (dkbfragment != null) {
            fragmenttransaction2.hide(dkbfragment);
        }
        if (fragment_cuntou != null) {
            fragmenttransaction2.hide(fragment_cuntou);
        }
    }

    /**
     * 设置fragment显示
     *
     * @param i
     */
    private void setTabSelection(int i) {
        // TODO Auto-generated method stub
        retBtn();
        fragmenttransaction = fragmentmanager.beginTransaction();
        heidFragment(fragmenttransaction);
        switch (i) {
            case 0:
                tv1.setSelected(true);
                v1.setVisibility(View.VISIBLE);
                if (tbfragment == null) {
                    tbfragment = new TuBiaoFragment();
                    fragmenttransaction.add(R.id.hq_lay, tbfragment);
                } else {
                    fragmenttransaction.show(tbfragment);
                }
                break;
            case 1:
                tv2.setSelected(true);
                v2.setVisibility(View.VISIBLE);
                if (dkbfragment == null) {
                    dkbfragment = new DuoKongBiFragment();
                    fragmenttransaction.add(R.id.hq_lay, dkbfragment);
                } else {
                    fragmenttransaction.show(dkbfragment);
                }
                break;

            case 2:
                tv3.setSelected(true);
                v3.setVisibility(View.VISIBLE);
                if (fragment_cuntou == null) {
                    fragment_cuntou = new Fragment_cuntou();
                    fragmenttransaction.add(R.id.hq_lay, fragment_cuntou);
                } else {
                    fragmenttransaction.show(fragment_cuntou);
                }
                break;
            case 3:
                tv4.setSelected(true);
                v4.setVisibility(View.VISIBLE);
                if (jgyjfragment == null) {
                    jgyjfragment = new JGYJFragment();
                    fragmenttransaction.add(R.id.hq_lay, jgyjfragment);
                } else {
                    fragmenttransaction.show(jgyjfragment);
                }
                break;

        }
        fragmenttransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv1:
                setTabSelection(0);
                break;

            case R.id.tv2:
                setTabSelection(1);
                break;
            case R.id.tv3:
                setTabSelection(2);
                break;
            case R.id.tv4:
                setTabSelection(3);
                break;
            case R.id.top_return:
                this.finish();
                break;
            case R.id.create_tv:

                initPopupWindow();

                break;
            case R.id.top_msg:
                startActivity(new Intent(HQXQActivity.this, MessageActivity.class));
                break;
            case R.id.zx_btn:
                initZiXuan();
                break;
        }
    }

    private void initZiXuan() {
        if (zxbtn.isSelected()) {
            for (int i = 0; i < names.size(); i++) {
                if (name.equals(names.get(i))) {
                    names.remove(i);
                    break;
                }
            }

            zxbtn.setSelected(false);
            zxbtn.setText("加自选");
        } else {
            names.add(name);
            zxbtn.setSelected(true);
            zxbtn.setText("删自选");
        }
        Set<String> newSet = new HashSet<String>();
        newSet.addAll(names);
        defEditor.putStringSet("hqdef", newSet);
        defEditor.commit();
    }

    @SuppressLint("NewApi")
    private void initPopupWindow() {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pp = inflater.inflate(R.layout.jiancang_pop, null);
        popu1 = new PopupWindow(pp, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        popu1.setFocusable(true);
        popu1.setOutsideTouchable(true);
        pp.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = pp.getMeasuredWidth();
        int popupHeight = pp.getMeasuredHeight();

        int[] location = new int[2];
        create_tv.getLocationOnScreen(location);
        // popu1.showAtLocation(create_tv, Gravity.NO_GRAVITY,
        // (location[0]+create_tv.getWidth()/2)-popupWidth/2,
        // location[1]+popupHeight);
        popu1.showAsDropDown(pp, (location[0] + create_tv.getWidth() / 2)
                - popupWidth / 2, location[1] + 60, Gravity.NO_GRAVITY);
        pp.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popu1.isShowing()) {
                    popu1.dismiss();
                }
                return false;
            }
        });

        pp.findViewById(R.id.btn_jc).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(HQXQActivity.this,
                        CreateOrderActivity.class));
                popu1.dismiss();
            }
        });
        pp.findViewById(R.id.btn_gd).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(HQXQActivity.this,
                        Activity_GuaDan.class));
                popu1.dismiss();
            }
        });
    }

}
