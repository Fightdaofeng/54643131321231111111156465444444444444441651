package com.lxb.jyb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.Activity_LOGON;
import com.lxb.jyb.activity.Activity_PUTINFO;
import com.lxb.jyb.activity.Activity_Setting;
import com.lxb.jyb.activity.Activity_WDSC;
import com.lxb.jyb.activity.Activity_YJFK;
import com.lxb.jyb.tool.IntentCode;

/**
 * 个人
 *
 * @author Liuxiaobin
 */
public class Fragment_GR extends Fragment implements OnClickListener {
    private View view;
    private ImageView bjzl_tn;
    private TextView to_logon;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String phone;
    private boolean islogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_geren, container, false);
        sp = this.getActivity().getSharedPreferences(IntentCode.SPUSERINFO, Activity.MODE_PRIVATE);
        editor = sp.edit();
        getUserInfo();
        initFind();
        return view;
    }

    private void getUserInfo() {
        islogin = sp.getBoolean("islogin", false);
    }

    private void initFind() {

        // TODO Auto-generated method stub
        view.findViewById(R.id.set_lay).setOnClickListener(this);
        view.findViewById(R.id.yjfk_lay).setOnClickListener(this);
        view.findViewById(R.id.wdsc_lay).setOnClickListener(this);
        bjzl_tn = (ImageView) view.findViewById(R.id.bjzl_btn);
        bjzl_tn.setOnClickListener(this);
        to_logon = (TextView) view.findViewById(R.id.to_logon);
        to_logon.setOnClickListener(this);
        if(islogin){
            to_logon.setVisibility(View.GONE);
            view.findViewById(R.id.bjzl_btn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.head_lay).setVisibility(View.VISIBLE);
        }else{

        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.set_lay:
                startActivity(new Intent(Fragment_GR.this.getActivity(),
                        Activity_Setting.class));
                break;

            case R.id.yjfk_lay:
                startActivity(new Intent(Fragment_GR.this.getActivity(),
                        Activity_YJFK.class));
                break;
            case R.id.wdsc_lay:
                startActivity(new Intent(Fragment_GR.this.getActivity(),
                        Activity_WDSC.class));
                break;
            case R.id.to_logon:
                startActivityForResult(new Intent(Fragment_GR.this.getActivity(),
                        Activity_LOGON.class), IntentCode.GRREQUESTCODE);
                break;
            case R.id.bjzl_btn:
                Intent intent = new Intent(Fragment_GR.this.getActivity(), Activity_PUTINFO.class);
                intent.putExtra("action", "alter");
                startActivityForResult(intent, IntentCode.GRREQUESTCODE);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentCode.GRREQUESTCODE) {
            if (resultCode == IntentCode.RESULTCODE) {
                to_logon.setVisibility(View.GONE);
                view.findViewById(R.id.head_lay).setVisibility(View.VISIBLE);
                bjzl_tn.setVisibility(View.VISIBLE);
            }
            if (resultCode == IntentCode.GRRESULTCODE) {

            }
        }
    }

}
