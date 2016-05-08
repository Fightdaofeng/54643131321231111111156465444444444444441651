package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.HQserachlistItemAdapter;
import com.lxb.jyb.bean.HQData;
import com.lxb.jyb.bean.HQentity;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.util.HQTitleUtil;
import com.lxb.jyb.util.SetStatiColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HQSerchActivity extends Activity implements View.OnClickListener {
    private PullToRefreshListView pulllistView;
    private ListView listview;
    private ArrayList<HQentity> hQentities = new ArrayList<>();
    private ArrayList<HQentity> sallist;
    private ArrayList<String> zixuanlist = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private HQserachlistItemAdapter adapter;
    private TextView quxiao;
    private EditText serchedit;

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
        setContentView(R.layout.hq_serach);
        getHQentity();
        initFindView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    listview = (ListView) findViewById(R.id.serch_list);

                    adapter = new HQserachlistItemAdapter(hQentities, HQSerchActivity.this, clicklistener);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(listener);
                    break;
                case 2:
                    sallist = new ArrayList<HQentity>();
                    String str = serchedit.getText().toString().toUpperCase();
                    for (int i = 0; i < hQentities.size(); i++) {
                        String code = hQentities.get(i).getCode();
                        String name = hQentities.get(i).getName();
                        if (code.indexOf(str) != -1
                                || name.indexOf(str) != -1) {
                            sallist.add(hQentities.get(i));
                        }
                    }
                    adapter.setList(sallist);
                    listview.setOnItemClickListener(listener);
                    break;
            }
        }
    };
    OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            ArrayList<HQentity> putList = new ArrayList<HQentity>();
            putList = adapter.getList();
            Intent intent = new Intent(HQSerchActivity.this,
                    HQXQActivity.class);
            intent.putExtra("bl", putList.get(position).getBaoliu());
            intent.putExtra("code", putList.get(position).getCode());
            intent.putExtra("name", putList.get(position).getName());
            startActivityForResult(intent, 100);
//            HQSerchActivity.this.finish();
        }
    };

    private void initFindView() {

        quxiao = (TextView) findViewById(R.id.serch_qx);
        quxiao.setOnClickListener(this);
        serchedit = (EditText) findViewById(R.id.serch_edt);


        serchedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getHQentity() {
        preferences = this.getSharedPreferences("defaulthq",
                Activity.MODE_PRIVATE);
        editor = preferences.edit();
        hQentities = HQTitleUtil.getAllItem();
        Set<String> stringSet = preferences.getStringSet("hqdef", null);
        ArrayList<String> names = null;
        if (null == stringSet) {

        } else {
            names = new ArrayList<String>();
            names.addAll(stringSet);
        }
        if (null != names && names.size() > 0) {
            for (int i = 0; i < names.size(); i++) {
                String string = names.get(i);
                for (int k = 0; k < hQentities.size(); k++) {
                    if (string.equals(hQentities.get(k).getName())) {
                        System.out.println("当前自选的行情有:"
                                + hQentities.get(k).getName());
                        hQentities.get(k).setCheck(true);
                    }
                }
            }
        }
        handler.sendEmptyMessage(1);
    }

    MyClickListener clicklistener = new MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            Set<String> stringSet = preferences.getStringSet("hqdef", null);

            ArrayList<String> names = new ArrayList<String>();
            ;
            if (null == stringSet) {

            } else {

                names.addAll(stringSet);
            }
            if (v.isSelected()) {
                hQentities.get(position).setCheck(false);

                v.setSelected(false);
                adapter.setList(hQentities);

            } else {
                v.setSelected(true);
                hQentities.get(position).setCheck(true);
                adapter.setList(hQentities);
            }
            boolean isF = true;
            String name = hQentities.get(position).getName();
            for (int i = 0; i < names.size(); i++) {
                if (name.equals(names.get(i))) {
                    names.remove(i);
                    isF = false;
                    break;
                }
            }
            if (isF) {
                names.add(name);
            }
            Set<String> newSet = new HashSet<String>();
            newSet.addAll(names);
            editor.putStringSet("hqdef", newSet);
            editor.commit();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.setResult(200);
            this.finish();

            return true;

        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.serch_qx:
                if (serchedit.getText().toString().length() == 0) {
                    this.setResult(200);
                    this.finish();
                } else {
                    serchedit.setText("");
                }
                break;
        }
    }
}
