package com.lxb.jyb.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxb.jyb.MyApplication;
import com.lxb.jyb.R;
import com.lxb.jyb.fragment.Fragment_GR;
import com.lxb.jyb.fragment.Fragment_XW;
import com.lxb.jyb.fragment.Fragment_ZH;
import com.lxb.jyb.fragment.HQFragment;
import com.lxb.jyb.fragment.RLFragment;
import com.lxb.jyb.fragment.ZhiBoFragment;
import com.lxb.jyb.push.PollingService;
import com.lxb.jyb.push.PollingUtils;
import com.lxb.jyb.util.SetStatiColor;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
    private SharedPreferences sp;
    private Editor editor;
    // private Fragment_ZX fragment_zx;
    // private Fragment_GS fragment_gs;
    private HQFragment hqFragment;
    // private Fragment_JYQ fragment_jyq;
    private Fragment_ZH fragment_zh;
    private Fragment_GR fragment_gr;
    private RLFragment rlFragment;
    private Fragment_XW fragment_XW;
    private ZhiBoFragment zhiBoFragment;
    private MyApplication application;
    private FragmentManager fragmentmanager;
    private FragmentTransaction fragmenttransaction;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    private LinearLayout main_ll_rl, main_ll_xw, main_ll_self, main_zt_color,
            main_ll_hq, main_ll_wode;
    private ImageView imgrl, imgxw, imggr, imgwd, imghq;
    private TextView main_rl_tv, main_xw_tv, main_wd_tv, main_gr_tv,
            main_hq_tv;
    public static Context content;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main1);
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        sp = getSharedPreferences("setup", Activity.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("isFirst", false);
        editor.commit();
        application = (MyApplication) getApplication();
        application.addAct(this);
        initView();
        fragmentmanager = getSupportFragmentManager();
        setTabSelection(0);
        content = this;
        initJPush();
        // new Exit();

    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initJPush() {
        SharedPreferences preferences = getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        boolean boolean1 = preferences.getBoolean("isPush", false);
//		if (boolean1) {
        // 启动本地定时推送服务
        PollingUtils.startPollingService(MainActivity.this, 2,
                PollingService.class, PollingService.ACTION);
//		}
        // 关闭推送
        // JPushInterface.stopPush(getApplicationContext());
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);
        // JPushInterface.onResume(this);
        isForeground = true;

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
        // JPushInterface.onPause(this);
        isForeground = false;

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
                imgxw.setSelected(true);
                main_xw_tv.setSelected(true);
                if (fragment_XW == null) {
                    fragment_XW = new Fragment_XW();
                    fragmenttransaction.add(R.id.frame_content, fragment_XW);
                } else {
                    fragmenttransaction.show(fragment_XW);
                }
                break;
            case 1:
                imgrl.setSelected(true);
                main_rl_tv.setSelected(true);

                // initPopupwindow();
                toRL();
                break;

            case 2:
                imghq.setSelected(true);
                main_hq_tv.setSelected(true);
                if (hqFragment == null) {
                    hqFragment = new HQFragment();
                    fragmenttransaction.add(R.id.frame_content, hqFragment);
                } else {
                    fragmenttransaction.show(hqFragment);
                }
                break;
            case 3:
                imgwd.setSelected(true);
                main_wd_tv.setSelected(true);
                if (fragment_zh == null) {
                    fragment_zh = new Fragment_ZH();
                    fragmenttransaction.add(R.id.frame_content, fragment_zh);
                } else {
                    fragmenttransaction.show(fragment_zh);
                }
                break;
            case 4:
                findViewById(R.id.main_zt_color).setBackgroundColor(
                        Color.parseColor("#72D3FD"));
                imggr.setSelected(true);
                main_gr_tv.setSelected(true);
                if (fragment_gr == null) {
                    fragment_gr = new Fragment_GR();
                    fragmenttransaction.add(R.id.frame_content, fragment_gr);
                } else {
                    fragmenttransaction.show(fragment_gr);
                }
                break;
        }
        fragmenttransaction.commitAllowingStateLoss();
    }

    private void toHQ() {
        // TODO Auto-generated method stub
        if (hqFragment == null) {
            hqFragment = new HQFragment();
            fragmenttransaction.add(R.id.frame_content, hqFragment);
        } else {
            fragmenttransaction.show(hqFragment);
        }
    }

    private void toRL() {
        // TODO Auto-generated method stub
        if (rlFragment == null) {
            rlFragment = new RLFragment();
            fragmenttransaction.add(R.id.frame_content, rlFragment);
        } else {
            fragmenttransaction.show(rlFragment);
        }
    }

    private void toZB() {
        // TODO Auto-generated method stub
        if (zhiBoFragment == null) {
            zhiBoFragment = new ZhiBoFragment();
            fragmenttransaction.add(R.id.frame_content, zhiBoFragment);
        } else {
            fragmenttransaction.show(zhiBoFragment);
        }
    }

    private void toXW() {
        // TODO Auto-generated method stub
        if (fragment_XW == null) {
            fragment_XW = new Fragment_XW();
            fragmenttransaction.add(R.id.frame_content, fragment_XW);
        } else {
            fragmenttransaction.show(fragment_XW);
        }
    }

    // private void toZX() {
    // // TODO Auto-generated method stub
    // if (fragment_zx == null) {
    // fragment_zx = new Fragment_ZX();
    // fragmenttransaction.add(R.id.frame_content, fragment_zx);
    // } else {
    // fragmenttransaction.show(fragment_zx);
    // }
    // }

    /**
     * 初始化资讯弹出的popupwindow
     */
    private void initPopupwindow() {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(this).inflate(R.layout.home_pp, null);

    }

    /**
     * 隐藏fragment
     *
     * @param fragmenttransaction2
     */
    private void heidFragment(FragmentTransaction fragmenttransaction2) {
        // TODO Auto-generated method stub

        // if (fragment_zx != null) {
        // fragmenttransaction2.hide(fragment_zx);
        // }
        // if (fragment_gs != null) {
        // fragmenttransaction2.hide(fragment_gs);
        // }
        if (fragment_zh != null) {
            fragmenttransaction2.hide(fragment_zh);
        }
        if (fragment_gr != null) {
            fragmenttransaction2.hide(fragment_gr);
        }
        // if (fragment_jyq != null) {
        // fragmenttransaction2.hide(fragment_jyq);
        // }
        if (rlFragment != null) {
            fragmenttransaction2.hide(rlFragment);
        }
        if (hqFragment != null) {
            fragmenttransaction2.hide(hqFragment);
        }
        if (zhiBoFragment != null) {
            fragmenttransaction2.hide(zhiBoFragment);
        }
        if (fragment_XW != null) {
            fragmenttransaction2.hide(fragment_XW);
        }
    }

    private void initView() {
        // TODO Auto-generated method stub

        main_ll_rl = (LinearLayout) findViewById(R.id.main_ll_rili);
        main_ll_xw = (LinearLayout) findViewById(R.id.main_ll_xw);
        main_ll_wode = (LinearLayout) findViewById(R.id.main_ll_wd);
        main_ll_self = (LinearLayout) findViewById(R.id.main_ll_self);
        main_ll_hq = (LinearLayout) findViewById(R.id.main_ll_hq);
        main_zt_color = (LinearLayout) findViewById(R.id.main_zt_color);

        main_zt_color.setBackgroundColor(Color.parseColor("#e9e9ea"));

        imgrl = (ImageView) findViewById(R.id.imgrili);
        imgxw = (ImageView) findViewById(R.id.imgxw);
        imgwd = (ImageView) findViewById(R.id.imgwd);
        imggr = (ImageView) findViewById(R.id.imgself);
        imghq = (ImageView) findViewById(R.id.imghq);

        main_rl_tv = (TextView) findViewById(R.id.main_rl_tv);
        main_xw_tv = (TextView) findViewById(R.id.main_xw_tv);
        main_wd_tv = (TextView) findViewById(R.id.main_wd_tv);
        main_gr_tv = (TextView) findViewById(R.id.main_self_tv);
        main_hq_tv = (TextView) findViewById(R.id.main_hq_tv);

        main_ll_rl.setOnClickListener(this);
        main_ll_xw.setOnClickListener(this);
        main_ll_wode.setOnClickListener(this);
        main_ll_hq.setOnClickListener(this);
        main_ll_self.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.main_ll_xw:
                setTabSelection(0);
                break;
            case R.id.main_ll_rili:
                setTabSelection(1);
                break;
            case R.id.main_ll_hq:
                setTabSelection(2);
                break;
            case R.id.main_ll_wd:
                setTabSelection(3);
                break;

            case R.id.main_ll_self:
                setTabSelection(4);
                break;
        }
    }

    private static final String TAG = "BaseActivity";
    private String phone = "";

    public String getPhone() {
        return this.phone;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
//        if (resultCode == GeRenFragment.RESULT_CODE) {
//            phone = data.getStringExtra(DisembarkActivity.PHONE);
//            return;
//        }

        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
            }
        }
    }
    private void retBtn() {
        findViewById(R.id.main_zt_color).setBackgroundColor(
                Color.parseColor("#e9e9ea"));

        imgrl.setSelected(false);
        imgxw.setSelected(false);
        imgwd.setSelected(false);
        imggr.setSelected(false);
        imghq.setSelected(false);
        main_rl_tv.setSelected(false);
        main_xw_tv.setSelected(false);
        main_wd_tv.setSelected(false);
        main_gr_tv.setSelected(false);
        main_hq_tv.setSelected(false);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {

                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();

                exitTime = System.currentTimeMillis();

            } else {

                application.exitAppAll();

            }

            return true;

        }

        return super.onKeyDown(keyCode, event);

    }


}
