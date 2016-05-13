package com.lxb.jyb.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxb.jyb.R;

public class UserAnalyzeActivity extends FragmentActivity implements
        OnClickListener {


    private LinearLayout analyze_ll_sy, analyze_ll_jy, analyze_ll_pf,
            analyze_ll_zd, analyze_ll_fx;
    private ImageView imgsy, imgjy, imgpf, imgzd, imgfx;
    private TextView analyze_sy_tv, analyze_jy_tv, analyze_pf_tv,
            analyze_zd_tv, analyze_fx_tv;
    private WebView webView;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_analyze);
//		SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        initView();
        setTabSelection(0);

    }

    /**
     * 设置fragment显示
     *
     * @param i
     */
    private void setTabSelection(int i) {
        // TODO Auto-generated method stub
        retBtn();
        switch (i) {
            case 0:
                imgsy.setSelected(true);
                analyze_sy_tv.setSelected(true);
//                webView.loadUrl();
                break;

            case 1:
                imgjy.setSelected(true);
                analyze_jy_tv.setSelected(true);
                break;
            case 2:
                imgpf.setSelected(true);
                analyze_pf_tv.setSelected(true);
                break;
            case 3:
                imgzd.setSelected(true);
                analyze_zd_tv.setSelected(true);
                break;

            case 4:
                imgfx.setSelected(true);
                analyze_fx_tv.setSelected(true);
                break;
        }
    }


    private void initView() {
        // TODO Auto-generated method stub
        webView = (WebView) findViewById(R.id.webView);
        analyze_ll_sy = (LinearLayout) findViewById(R.id.analyze_ll_sy);
        analyze_ll_jy = (LinearLayout) findViewById(R.id.analyze_ll_jy);
        analyze_ll_pf = (LinearLayout) findViewById(R.id.analyze_ll_pf);
        analyze_ll_zd = (LinearLayout) findViewById(R.id.analyze_ll_zd);
        analyze_ll_fx = (LinearLayout) findViewById(R.id.analyze_ll_fx);
        //
        // main_zt_color = (LinearLayout) findViewById(R.id.main_zt_color);
        //
        // main_zt_color.setBackgroundColor(Color.parseColor("#0075CF"));

        imgsy = (ImageView) findViewById(R.id.imgsy);
        imgjy = (ImageView) findViewById(R.id.imgjy);
        imgzd = (ImageView) findViewById(R.id.imgzd);
        imgpf = (ImageView) findViewById(R.id.imgpf);
        imgfx = (ImageView) findViewById(R.id.imgfx);

        analyze_sy_tv = (TextView) findViewById(R.id.analyze_sy_tv);
        analyze_jy_tv = (TextView) findViewById(R.id.analyze_jy_tv);
        analyze_pf_tv = (TextView) findViewById(R.id.analyze_pf_tv);
        analyze_fx_tv = (TextView) findViewById(R.id.analyze_fx_tv);
        analyze_zd_tv = (TextView) findViewById(R.id.analyze_zd_tv);

        analyze_ll_sy.setOnClickListener(this);
        analyze_ll_jy.setOnClickListener(this);
        analyze_ll_pf.setOnClickListener(this);
        analyze_ll_zd.setOnClickListener(this);
        analyze_ll_fx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.analyze_ll_sy:
                setTabSelection(0);
                break;

            case R.id.analyze_ll_jy:
                setTabSelection(1);
                break;
            case R.id.analyze_ll_pf:
                setTabSelection(2);
                break;
            case R.id.analyze_ll_zd:
                setTabSelection(3);
                break;

            case R.id.analyze_ll_fx:
                setTabSelection(4);
                break;
        }
    }


    private void retBtn() {
        imgsy.setSelected(false);
        imgjy.setSelected(false);
        imgzd.setSelected(false);
        imgpf.setSelected(false);
        imgfx.setSelected(false);
        analyze_sy_tv.setSelected(false);
        analyze_jy_tv.setSelected(false);
        analyze_pf_tv.setSelected(false);
        analyze_fx_tv.setSelected(false);
        analyze_zd_tv.setSelected(false);

    }

}
