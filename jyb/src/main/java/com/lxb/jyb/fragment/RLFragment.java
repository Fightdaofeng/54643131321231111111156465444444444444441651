package com.lxb.jyb.fragment;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.MessageActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RLFragment extends Fragment implements OnClickListener {

    private CalendarHomeFragment calendarHomeFragment;
    private ShiJianFragment shijianFragment;
    private ZhiBoFragment zhibofragment;
    private FragmentManager fragmentmanager;
    private FragmentTransaction fragmenttransaction;

    private TextView tv_rili, tv_shijian, tv_zhibo;
    private LinearLayout lay_rili, lay_shijian, lay_zhibo;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.first_rlmk, null);
        initView();
        fragmentmanager = getChildFragmentManager();
        setTabSelection(0);
        return view;

    }

    private void setTabSelection(int i) {
        // TODO Auto-generated method stub
        fragmenttransaction = fragmentmanager.beginTransaction();
        heidFragment(fragmenttransaction);
        retBtn();
        switch (i) {

            case 0:
                view.findViewById(R.id.rishaixuan).setVisibility(View.VISIBLE);
                view.findViewById(R.id.shishaixuan).setVisibility(View.GONE);
                view.findViewById(R.id.zbshaixuan).setVisibility(View.GONE);

                lay_rili.setSelected(true);
                tv_rili.setSelected(true);

                if (calendarHomeFragment == null) {
                    calendarHomeFragment = new CalendarHomeFragment();
                    fragmenttransaction.add(R.id.frame_cont, calendarHomeFragment);
                } else {
                    fragmenttransaction.show(calendarHomeFragment);
                }
                break;

            case 1:
                view.findViewById(R.id.rishaixuan).setVisibility(View.GONE);
                view.findViewById(R.id.shishaixuan).setVisibility(View.VISIBLE);
                view.findViewById(R.id.zbshaixuan).setVisibility(View.GONE);

                lay_shijian.setSelected(true);
                tv_shijian.setSelected(true);
                if (shijianFragment == null) {
                    shijianFragment = new ShiJianFragment();
                    fragmenttransaction.add(R.id.frame_cont, shijianFragment);
                } else {
                    fragmenttransaction.show(shijianFragment);
                }
                break;
            case 2:
                view.findViewById(R.id.rishaixuan).setVisibility(View.GONE);
                view.findViewById(R.id.shishaixuan).setVisibility(View.GONE);
                view.findViewById(R.id.zbshaixuan).setVisibility(View.VISIBLE);
                lay_zhibo.setSelected(true);
                tv_zhibo.setSelected(true);
                if (zhibofragment == null) {
                    zhibofragment = new ZhiBoFragment();
                    fragmenttransaction.add(R.id.frame_cont, zhibofragment);
                } else {
                    fragmenttransaction.show(zhibofragment);
                }
        }
        fragmenttransaction.commitAllowingStateLoss();
    }

    private void retBtn() {
        // TODO Auto-generated method stub
        lay_rili.setSelected(false);
        lay_shijian.setSelected(false);

        tv_rili.setSelected(false);
        tv_shijian.setSelected(false);

        lay_zhibo.setSelected(false);
        tv_zhibo.setSelected(false);
    }

    /**
     * 隐藏fragment
     *
     * @param fragmenttransaction2
     */
    private void heidFragment(FragmentTransaction fragmenttransaction2) {
        // TODO Auto-generated method stub
        if (calendarHomeFragment != null) {
            fragmenttransaction2.hide(calendarHomeFragment);
        }
        if (shijianFragment != null) {
            fragmenttransaction2.hide(shijianFragment);
        }
        if (zhibofragment != null) {
            fragmenttransaction2.hide(zhibofragment);
        }
    }

    private void initView() {
        // TODO Auto-generated method stub
        tv_rili = (TextView) view.findViewById(R.id.tv_rili);
        tv_shijian = (TextView) view.findViewById(R.id.tv_shijian);
        tv_zhibo = (TextView) view.findViewById(R.id.tv_zhibo);
        lay_zhibo = (LinearLayout) view.findViewById(R.id.lay_zhibo);
        lay_rili = (LinearLayout) view.findViewById(R.id.lay_rili);
        lay_shijian = (LinearLayout) view.findViewById(R.id.lay_shijian);
        view.findViewById(R.id.xiaoxi).setOnClickListener(this);
        lay_rili.setOnClickListener(this);
        lay_shijian.setOnClickListener(this);
        lay_zhibo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.lay_rili:
                setTabSelection(0);
                break;

            case R.id.lay_shijian:
                setTabSelection(1);
                break;
            case R.id.lay_zhibo:
                setTabSelection(2);
                break;
            case R.id.xiaoxi:
                startActivity(new Intent(RLFragment.this.getActivity(), MessageActivity.class));
                break;
        }
    }

}
