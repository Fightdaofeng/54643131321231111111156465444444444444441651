package com.lxb.jyb.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.CalSXTextAdapter;
import com.lxb.jyb.bean.CalSXItem;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.SetStatiColor;

public class CalShaiXuanActivity extends Activity implements OnClickListener {
    private String[] strings = {"美元", "欧元", "日元", "英镑", "瑞郎", "澳元", "加元",
            "人民币", "黄金", "白银", "石油"};
    private ArrayList<CalSXItem> allList = new ArrayList<CalSXItem>();
    private GridView gridview;
    private LinearLayout xing1, xing2, xing3, top_return;
    private TextView myph, all, top_msg, top_txt, top_title;
    private CalSXTextAdapter adapter;
    private boolean isCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calshaixuan);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        initGetIntent();
        initFind();
        initGrid();
    }

    private void initGetIntent() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        isCal = intent.getBooleanExtra("cal", false);
    }

    private void initGrid() {
        // TODO Auto-generated method stub

        adapter = new CalSXTextAdapter(allList, CalShaiXuanActivity.this,
                mListener1);
        gridview = (GridView) findViewById(R.id.cal_grid);

        gridview.setAdapter(adapter);

    }

    private void initFind() {
        // TODO Auto-generated method stub
        for (int i = 0; i < strings.length; i++) {
            CalSXItem calSXItem = new CalSXItem(strings[i]);
            allList.add(calSXItem);
        }
        findViewById(R.id.sx_lay).setBackgroundColor(
                Color.parseColor("#e9e9ea"));

        top_return = (LinearLayout) findViewById(R.id.top_return);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText("日历筛选");

        top_msg = (TextView) findViewById(R.id.top_msg);
        top_msg.setVisibility(View.GONE);
        top_txt = (TextView) findViewById(R.id.top_txt);
        top_txt.setVisibility(View.VISIBLE);
        top_txt.setText("完成");

        xing1 = (LinearLayout) findViewById(R.id.zy_1);
        xing2 = (LinearLayout) findViewById(R.id.zy_2);
        xing3 = (LinearLayout) findViewById(R.id.zy_3);
        myph = (TextView) findViewById(R.id.myph);
        all = (TextView) findViewById(R.id.all);

        top_return.setOnClickListener(this);
        top_msg.setOnClickListener(this);
        xing1.setOnClickListener(this);
        xing2.setOnClickListener(this);
        xing3.setOnClickListener(this);
        myph.setOnClickListener(this);
        all.setOnClickListener(this);
    }

    /**
     * 实现类，响应子fragment中的gridview中的item的checkbox点击事件
     */
    private MyClickListener mListener1 = new MyClickListener() {

        @Override
        public void myOnClick(int position, View v) {
            all.setSelected(false);
            if (v.isSelected()) {
                v.setSelected(false);
            } else {
                v.setSelected(true);
            }
            int tag = (int) v.getTag();

            String string = allList.get(tag).getName();
            Toast.makeText(CalShaiXuanActivity.this, string, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.zy_1:
                if (xing1.isSelected()) {
                    xing1.setSelected(false);
                } else {
                    xing1.setSelected(true);
                }
                break;

            case R.id.zy_2:
                if (xing2.isSelected()) {
                    xing2.setSelected(false);
                } else {
                    xing2.setSelected(true);
                }
                break;
            case R.id.zy_3:
                if (xing3.isSelected()) {
                    xing3.setSelected(false);
                } else {
                    xing3.setSelected(true);
                }
                break;
            case R.id.myph:
                if (myph.isSelected()) {
                    // xing1.setSelected(false);
                } else {
                    myph.setSelected(true);
                    all.setSelected(false);
                }
                break;
            case R.id.all:
                if (all.isSelected()) {
                    // xing1.setSelected(false);
                } else {
                    myph.setSelected(false);
                    all.setSelected(true);
                    for (int i = 0; i < allList.size(); i++) {
                        allList.get(i).setCheck(false);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.top_return:
                this.setResult(104);
                this.finish();
                break;
            case R.id.top_msg:

                break;
        }
    }

}
