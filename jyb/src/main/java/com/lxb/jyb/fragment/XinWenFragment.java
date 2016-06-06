package com.lxb.jyb.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxb.jyb.MyApplication;
import com.lxb.jyb.R;
import com.lxb.jyb.activity.XWSerchActivity;
import com.lxb.jyb.activity.XinWenWebActivity;
import com.lxb.jyb.activity.adapter.Fragment_All_Adapter;
import com.lxb.jyb.activity.view.Advertisement;
import com.lxb.jyb.bean.NewsBannerBean;
import com.lxb.jyb.bean.NewsBean;
import com.lxb.jyb.tool.ImageDownLoader;
import com.lxb.jyb.tool.MyClickListener;
import com.lxb.jyb.tool.NetworkCenter;
import com.lxb.jyb.util.HttpConstant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XinWenFragment extends Fragment implements OnClickListener {
    private View view;

    private MyApplication application;
    private String newsName;
    private int page = 1;
    // private int lodpage = 1;
    private ArrayList<NewsBean> newsBeans;
    private ArrayList<NewsBean> headBeans;
    private ArrayList<NewsBean> arrayList;
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private Fragment_All_Adapter all_Adapter;
    private RelativeLayout relative_yw_show;
    private ProgressBar yw_pro_bar;
    private TextView show_yw_text, edit;
    private boolean jxLoadMore = true;
    private boolean isFirst = true;
    private boolean isRefresh = false;
    public boolean isLoadingMore = false;
    public int currentsize = 0;
    private ArrayList<NewsBannerBean> bannerBeen;
    private ArrayList<Bitmap> bitmaps;
    private ImageDownLoader downLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.xinwen_frg, null, false);
        downLoader = new ImageDownLoader(this.getActivity());
//        new RequestBanner().execute();
        getIntentData();
        return view;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 107:
                    // 加载更多
                    loadMore();
                    break;
                case 2:
                    relative_yw_show.setVisibility(View.GONE);
                    // initView();
                    break;
                case 101:
                    if (isFirst) {
                        Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT)
                                .show();
                        yw_pro_bar.setVisibility(View.GONE);
                        show_yw_text.setText("数据加载失败");
                        relative_yw_show.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                new RequestBeanTask().execute();
                            }
                        });
                    }
                    break;
                case 108:
                    if (jxLoadMore) {
                        new LoadMroe().execute();
                        all_Adapter.setDeviceList(arrayList);
                    } else {
                        Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case 206:
                    new GETbitmap().execute();
//                    handler.sendEmptyMessage(207);
                    break;
                case 207:
                    initHead();
                    listView.addHeaderView(headview);
                    break;
            }

        }
    };


    private boolean isOne = false;
    private boolean isTwo = false;

    /**
     * 接收传递过来的数据
     */
    @SuppressLint("NewApi")
    private void getIntentData() {
        // TODO Auto-generated method stub

        Bundle bundle = getArguments();
        newsName = bundle.getString("tagName", "0").replace(" ", "");
        // Toast.makeText(getActivity(), newsName, 1).show();
        if ("订阅".equals(newsName)) {
            newsName = "*";
            isOne = true;

        }
        if ("推荐".equals(newsName)) {
            isTwo = true;
//            new RequestBanner().execute();
        }
        page = 1;
        // Toast.makeText(getActivity(), "当前进入了" + "" + newsName, 1).show();
        initFind();

    }

    private LinearLayout flash_ad;
    private View headview;

    private void initFind() {
        // TODO Auto-generated method stub


        arrayList = new ArrayList<NewsBean>();
        application = (MyApplication) getActivity().getApplication();
        Volley.newRequestQueue(getActivity());
        relative_yw_show = (RelativeLayout) view
                .findViewById(R.id.relative_yw_show);
        pullToRefreshListView = (PullToRefreshListView) view
                .findViewById(R.id.item_fragment_gjs_refresh);
        yw_pro_bar = (ProgressBar) view.findViewById(R.id.yw_pro_bar);
        show_yw_text = (TextView) view.findViewById(R.id.show_yw_text);
        listView = pullToRefreshListView.getRefreshableView();
        if (isTwo) {
            new RequestBanner().execute();

        }

        if (isOne) {

            flash_ad.setVisibility(View.GONE);
            listView.addHeaderView(headview);
        }
        registerForContextMenu(listView);
        if (NetworkCenter.checkNetworkConnection(getActivity())) {
            if (!isOne) {
                initData();
            }
        } else {
            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
        }
    }

    MyClickListener clickListener = new MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            Log.i("当前点击了：", arrayList.size() + "图");
            NewsBannerBean bean = bannerBeen.get(position);
            Intent intent = new Intent(XinWenFragment.this.getActivity(),
                    XinWenWebActivity.class);
            intent.putExtra("newsid", bean.getNewsId() + "");
            intent.putParcelableArrayListExtra("list", arrayList);
            startActivity(intent);
        }
    };

    private void initHead() {

        headview = LayoutInflater.from(getActivity()).inflate(
                R.layout.news_head, null);
        edit = (TextView) headview.findViewById(R.id.null_edit);
        edit.setOnClickListener(this);

        flash_ad = (LinearLayout) headview.findViewById(R.id.home_ad);


        View initView = new Advertisement(getActivity(), true,
                LayoutInflater.from(getActivity()), 2000)
                .initView(bannerBeen, bitmaps, clickListener);
        flash_ad.addView(initView);
    }

    private void initData() {
        // TODO Auto-generated method stub
        handler.sendEmptyMessageDelayed(101, 10 * 1000);
        new RequestBeanTask().execute();

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                NewsBean newsBean = arrayList.get(position - 2);
                Intent intent = new Intent(XinWenFragment.this.getActivity(),
                        XinWenWebActivity.class);
                intent.putExtra("newsid", newsBean.getNewsId());

                intent.putParcelableArrayListExtra("list", arrayList);
                startActivity(intent);
            }
        });
        pullToRefreshListView
                .setOnRefreshListener(new OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        if (NetworkCenter.isNetworkConnected(getActivity())) {
                            if (!isRefresh) {
                                new RequestBeanTask().execute();
                                isRefresh = true;
                            }
                        } else {
                            Toast.makeText(getActivity(), "无网络连接",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadMore() {
        handler.sendEmptyMessage(108);
    }

    class RequestBeanTask extends AsyncTask<Void, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(Void... params) {

            // TODO Auto-generated method stub
            // arrayList.clear();
            newsBeans = new ArrayList<NewsBean>();
            String url = HttpConstant.NEWS_LISTHOST + newsName
                    + HttpConstant.NEWS_PAGE + 1;
            HttpClient client = new DefaultHttpClient();
            HttpGet get = null;
            get = new HttpGet(url);
            HttpResponse response;
            try {
                response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine()
                        .getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, "utf-8");
                    JSONArray array = JSONObject.parseObject(data).getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        NewsBean bean = new NewsBean(object);
                        newsBeans.add(bean);
                        // if (i == array.length() - 1) {
                        // page++;
                        // }
                    }
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return newsBeans;
        }

        @Override
        protected void onPostExecute(List<NewsBean> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            relative_yw_show.setVisibility(View.GONE);
            arrayList.clear();
            arrayList.addAll(result);
            Collections.sort(arrayList);

            all_Adapter = new Fragment_All_Adapter(getActivity(), arrayList,
                    listView, handler, view);
            listView.setAdapter(all_Adapter);
            application.getMmp().put("2", "2");
            isFirst = false;

            if (isRefresh) {
                pullToRefreshListView.onRefreshComplete();
                isRefresh = false;
            }

            if (arrayList != null && arrayList.size() > 0) {
                application.getMmp().put("2", "2");

            } else {
            }
        }
    }

    class LoadMroe extends AsyncTask<Void, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(Void... arg0) {

            newsBeans = new ArrayList<NewsBean>();
            page++;
            String url = HttpConstant.NEWS_LISTHOST + newsName
                    + HttpConstant.NEWS_PAGE + page;

            HttpClient client = new DefaultHttpClient();
            HttpGet get = null;
            get = new HttpGet(url);
            HttpResponse response;
            try {
                response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine()
                        .getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, "utf-8");
                    JSONArray array = JSONObject.parseObject(data).getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        NewsBean bean = new NewsBean(object);
                        arrayList.add(arrayList.size(), bean);
                    }
                    Collections.sort(arrayList);
                    handler.sendEmptyMessage(2);
                    if (arrayList.size() > 300) {
                        jxLoadMore = false;
                    }
                    handler.sendEmptyMessage(125);
                    currentsize = arrayList.size();
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return newsBeans;
        }

    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        show_yw_text.setText("数据加载中...");
        relative_yw_show.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
        super.onStop();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        if (arrayList != null && arrayList.size() > 0) {
//            application.getMmp().put("2", "2");
//
//        } else {
//        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.null_edit:
                startActivityForResult(new Intent(
                                XinWenFragment.this.getActivity(), XWSerchActivity.class),
                        101);
                break;

            default:
                break;
        }
    }

    class RequestBanner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ;
            bannerBeen = new ArrayList<>();
            HttpClient client = new DefaultHttpClient();
            HttpGet get = null;
            get = new HttpGet(HttpConstant.NEWBANNER_HOST);
            HttpResponse response;
            try {
                response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine()
                        .getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, "utf-8");
                    JSONArray array = JSONObject.parseObject(data).getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        NewsBannerBean bean = new NewsBannerBean(object);
                        bannerBeen.add(bean);
                    }

                    handler.sendEmptyMessage(206);
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    class GETbitmap extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            bitmaps = new ArrayList<>();
            try {
                for (int i = 0; i < bannerBeen.size(); i++) {
                    Log.i("图片地址:", bannerBeen.get(i).getImage());
                    Bitmap bitmap = downLoader.downloadImage(HttpConstant.NSH + bannerBeen.get(i).getImage());
                    bitmaps.add(bitmap);
                }
                handler.sendEmptyMessage(207);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("错误信息：", e.toString());
            }
            return null;
        }

    }
}
