package com.lxb.jyb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lxb.jyb.R;
import com.lxb.jyb.bean.Gaidanorder;
import com.lxb.jyb.bean.OrderSelect;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.pase.ObjtoJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 止盈止损
 *
 * @author liuxiaobin
 */
public class Fragment_zy extends Fragment implements OnClickListener {
    private View view;
    private ImageView zy_jia, zy_jian, zs_jia, zs_jian;
    private ImageView switch1, switch2;
    private OrderSelect select;
    private EditText edit_zy, edit_zs;
    private int length;
    private boolean isDouble = false;
    private double tpminvalue, slmainvalue, parseDouble, put;
    private String editvalue;
    private Button commit_btn;
    private Gaidanorder gaidanorder;
    private String account = "900171576", broker = "GMI", ticket, price = "0", sl, tp, expiretime;
    private String jsonString;
    private RequestQueue queue;
    private JsonObjectRequest reuqest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_zyzs, null, false);
        getIntentValue();
        initFind();
        return view;
    }


    private void initFind() {
        // TODO Auto-generated method stub
        switch1 = (ImageView) view.findViewById(R.id.switch1);
        switch2 = (ImageView) view.findViewById(R.id.switch2);
        edit_zs = (EditText) view.findViewById(R.id.edit_zs);
        edit_zy = (EditText) view.findViewById(R.id.edit_zy);
        zy_jia = (ImageView) view.findViewById(R.id.zy_jia);
        zy_jian = (ImageView) view.findViewById(R.id.zy_jian);
        zs_jia = (ImageView) view.findViewById(R.id.zs_jia);
        zs_jian = (ImageView) view.findViewById(R.id.zs_jian);
        commit_btn = (Button) view.findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(this);

        zy_jia.setOnClickListener(this);
        zy_jian.setOnClickListener(this);
        zs_jia.setOnClickListener(this);
        zs_jian.setOnClickListener(this);
        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);

        sl = select.getSl();
        tp = select.getTp();
        if ("0".equals(sl) || "0.0".equals(sl)) {
            view.findViewById(R.id.set_2).setVisibility(View.GONE);
            switch2.setSelected(false);
        } else {
            view.findViewById(R.id.set_2).setVisibility(View.VISIBLE);
            switch2.setSelected(true);
            tpminvalue = getMode(sl);
            slmainvalue = getMode(sl);
        }
        edit_zs.setText(sl);
        if ("0".equals(tp) || "0.0".equals(tp)) {
            view.findViewById(R.id.set_1).setVisibility(View.GONE);
            switch1.setSelected(false);
        } else {
            tpminvalue = getMode(tp);
            slmainvalue = getMode(tp);
            view.findViewById(R.id.set_1).setVisibility(View.VISIBLE);
            switch1.setSelected(true);
        }
        edit_zy.setText(tp);
        ticket = select.getTicket();



    }

    /**
     * 获取传递过来的对象
     */
    private void getIntentValue() {
        select = getActivity().getIntent().getParcelableExtra("bean");
        queue = Volley.newRequestQueue(this.getActivity());
    }

    /**
     * 取末尾间
     *
     * @param string
     * @return
     */
    private double getMode(String string) {
        int wei;
        double re = 0;

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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        editvalue = "";
        parseDouble = 0;
        put = 0;
        switch (v.getId()) {
            case R.id.switch1:
                if (switch1.isSelected()) {
                    switch1.setSelected(false);
                    view.findViewById(R.id.set_1).setVisibility(View.GONE);
                } else {
                    switch1.setSelected(true);
                    view.findViewById(R.id.set_1).setVisibility(View.VISIBLE);
                }
                break;

            case R.id.switch2:
                if (switch2.isSelected()) {
                    switch2.setSelected(false);
                    view.findViewById(R.id.set_2).setVisibility(View.GONE);
                } else {
                    switch2.setSelected(true);
                    view.findViewById(R.id.set_2).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.zy_jia:

                editvalue = edit_zy.getText().toString();
                parseDouble = Double.parseDouble(editvalue);
                put = parseDouble + tpminvalue;
                if (isDouble) {
                    edit_zy.setText(String.valueOf(put));
                } else {
                    edit_zy.setText(put + "");
                }
                break;
            case R.id.zy_jian:
                editvalue = edit_zy.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(editvalue);
                put = parseDouble - tpminvalue;
                if (isDouble) {
                    String s = String.valueOf(put);
                    edit_zy.setText(s);
                } else {
                    edit_zy.setText((put + ""));
                }
                edit_zy.postInvalidate();
                break;
            case R.id.zs_jia:
                editvalue = edit_zs.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(editvalue);
                put = parseDouble + slmainvalue;

                if (isDouble) {
                    String s = String.valueOf(put);
                    edit_zs.setText(s);
                } else {
                    edit_zs.setText((put + ""));
                }
                break;
            case R.id.zs_jian:
                editvalue = edit_zs.getText().toString().replace(" ", "");
                parseDouble = Double.parseDouble(editvalue);
                put = parseDouble - slmainvalue;
                String s = String.valueOf(put);
                if (isDouble) {

                    edit_zs.setText(s);
                } else {
                    edit_zs.setText((put + ""));
                }
                edit_zs.postInvalidate();
                break;
            case R.id.commit_btn:
                initBean();
                Gaidanorder order = new Gaidanorder();
                order.setAccount(account);
                order.setBroker(broker);
                order.setExpiretime(expiretime);
                order.setPrice(price);
                order.setSl(sl);
                order.setTicket(ticket);
                order.setTp(tp);
                ObjtoJson util = new ObjtoJson();

                jsonString = util.toJson(order);
                Log.i("转换的JSON：", jsonString);
                updateOrder();
                break;
        }
    }

    private void updateOrder() {
        try {
            reuqest = new JsonObjectRequest(Request.Method.PUT,
                    HttpConstant.UPDATE_ORDER, new JSONObject(jsonString
            ), new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jsonObject) {
                    Log.i("成功info", jsonObject.toString());
                    Toast.makeText(Fragment_zy.this.getActivity(), "修改成功！" , Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    Log.i("失败info", arg0.toString());
                    Toast.makeText(Fragment_zy.this.getActivity(), "错误信息" + arg0.toString(), Toast.LENGTH_LONG).show();
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
            e.printStackTrace();
            Log.i("JSON转换失败！", e.toString());
        }
        // 超时时间10s,最大重连次数2次
        queue.add(reuqest);
    }

    private void initBean() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        expiretime = sdf.format(date);
        if (switch1.isSelected()) {
            tp = edit_zy.getText().toString();
        }
        if (switch2.isSelected()) {
            sl = edit_zs.getText().toString();
        }
    }
}
