package com.lxb.jyb.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.Directdirec_ParticularstActivity;
import com.lxb.jyb.activity.XiangQingActivity;
import com.lxb.jyb.activity.ZhiBoShaiXuanActivity;
import com.lxb.jyb.activity.adapter.ZhiBoAdapater;
import com.lxb.jyb.bean.ZhiBoEntity;
import com.lxb.jyb.tool.IntentCode;
import com.lxb.jyb.util.HttpConstant;
import com.umeng.analytics.MobclickAgent;

public class ZhiBoFragment extends Fragment {

    private View view;
    private ArrayList<ZhiBoEntity> zblist;
    private ZhiBoAdapater zhibAdapter;
    private PullToRefreshListView listview;
    private RequestQueue queue;
    private int page = 1;
    private ImageView shaixuan;
    private RelativeLayout shuaxin;
    private TextView date;
    private static final String TAG = ZhiBoFragment.class.getSimpleName();
    private boolean zhongyao = false;
    private String endstr;
    private String urlend = "";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    initViews();
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_zhibo, container, false);
        getActivity().findViewById(R.id.shishaixuan).setVisibility(View.GONE);
        getActivity().findViewById(R.id.rishaixuan).setVisibility(View.GONE);
        getActivity().findViewById(R.id.zbshaixuan).setVisibility(View.VISIBLE);
        initData();
        return view;
    }

    private void initData() {
        // TODO Auto-generated method stub
        queue = Volley.newRequestQueue(getActivity());
        sp = getActivity().getSharedPreferences("setup", Activity.MODE_PRIVATE);
        editor = sp.edit();
        new RequestData().execute();
    }

    private void initViews() {
        date = (TextView) view.findViewById(R.id.zhibo_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String data = sdf.format(new java.util.Date());
        date.setText(data);
        shaixuan = (ImageView) getActivity().findViewById(R.id.zbshaixuan);
        shuaxin = (RelativeLayout) view.findViewById(R.id.zhibo_shuaxin);
        listview = (PullToRefreshListView) view.findViewById(R.id.zhibo_lview);
        zhibAdapter = new ZhiBoAdapater(getActivity());
        zhibAdapter.setData(zblist);
        listview.setAdapter(zhibAdapter);
        listview.setMode(Mode.BOTH);
        listview.setOnRefreshListener(new OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                if(page>1){
                    page=page-1;
                }
                new RequestData().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                page++;
                new RequestData().execute();
            }
        });
        shuaxin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                page = 1;
                new RequestData().execute();
//				shuaxin.setBackgroundResource(R.drawable.shuaxin2);
                shuaxin.setSelected(true);
            }
        });
        shaixuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(),
                        ZhiBoShaiXuanActivity.class);
                startActivityForResult(intent, IntentCode.REQUESTCODE);
                // startActivity(new Intent(getActivity(),
                // ZhiBoShaixuanActivity.class));
            }
        });
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                int p = position - 1;
                if (zblist.get(p).getType().equals("Newsflash")) {
                    Intent intent = new Intent(getActivity(),
                            Directdirec_ParticularstActivity.class);
                    intent.putExtra("id", zblist.get(p).getNewsflashs().get(0)
                            .getLiveImportance());
                    intent.putExtra("Date", zblist.get(p).getNewsflashs()
                            .get(0).getLiveDate());
                    intent.putExtra("Time", zblist.get(p).getNewsflashs()
                            .get(0).getLiveTime());
                    intent.putExtra("Title",
                            zblist.get(p).getNewsflashs().get(0).getLiveTitle());
                    intent.putExtra("Text", zblist.get(p).getNewsflashs()
                            .get(0).getLiveText());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(),
                            XiangQingActivity.class);
                    intent.putExtra("id", zblist.get(p).getIndexEvents().get(0)
                            .getBasicIndexId());
                    // intent.putExtra("period", zblist.get(p).getIndexEvents()
                    // .get(0).getPeriod());
                    // intent.putExtra("value", zblist.get(p).getIndexEvents()
                    // .get(0).getValue());
                    startActivity(intent);
                }

            }
        });
    }

    private class RequestData extends AsyncTask<Void, Void, List<ZhiBoEntity>> {

        @Override
        protected List<ZhiBoEntity> doInBackground(Void... params) {
            zblist = new ArrayList<ZhiBoEntity>();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    HttpConstant.NEWS_HOST + page + urlend, null,
                    new Listener<JSONObject>() {
                        public void onResponse(JSONObject arg0) {
                            // TODO Auto-generated method stub
                            JSONArray array = arg0.optJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                ZhiBoEntity boEntity = new ZhiBoEntity(
                                        array.optJSONObject(i));
                                zblist.add(boEntity);
                                // if (boEntity.getType().equals("Newsflash")){
                                // date.setText(boEntity.getNewsflashs().get(0).getLiveDate());
                                // } else{
                                // date.setText(boEntity.getIndexEvents().get(0).getDate());
                                // }
                            }

                            handler.sendEmptyMessage(100);

                        }
                    }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                }
            });
            queue.add(jsonObjectRequest);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<ZhiBoEntity> result) {
            // TODO Auto-generated method stub

//             zhibAdapter.setData(zblist);
            if (zhibAdapter != null) {
                zhibAdapter.notifyDataSetChanged();
            }
            if (listview != null) {
                listview.onRefreshComplete();
            }

            super.onPostExecute(result);
        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        if (resultCode == IntentCode.RESULTCODE) {
//            page = 1;
//          endstr = data.getStringExtra("list");
//            zhongyao = data.getBooleanExtra("zhongyao", false);
//            Log.i("数组", endstr.toString());
////            url = HttpConstant.NEWS_HOST + page + "&importance=" + zhongyao + "&tags=" + arrString.toString();
////            new RequestData().execute();
//        }
////        zhibAdapter.setData(zblist);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.rishaixuan).setVisibility(View.GONE);
        getActivity().findViewById(R.id.shishaixuan).setVisibility(View.GONE);
        getActivity().findViewById(R.id.zbshaixuan).setVisibility(View.VISIBLE);
        MobclickAgent.onPageStart("MainScreen"); // 统计页面，"MainScreen"为页面名称，可自定义
        zhongyao = sp.getBoolean("zbzy", false);
        HashSet<String> strSet = (HashSet<String>) sp.getStringSet("zbset", null);
        String str = "";
        if (null != strSet) {
            ArrayList<String> lit = new ArrayList<>();
            lit.addAll(strSet);
            for (int i = 0; i < lit.size(); i++) {
                str += "," + lit.get(i);
            }
        }
        urlend = "&importance=" + zhongyao + "&tags=" + str;
        new RequestData().execute();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

}