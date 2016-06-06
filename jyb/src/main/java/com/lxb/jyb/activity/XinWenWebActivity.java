package com.lxb.jyb.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.adapter.WebListViewAdapter;
import com.lxb.jyb.activity.view.ObservableScrollView;
import com.lxb.jyb.bean.NewsBean;
import com.lxb.jyb.tool.Accredit;
import com.lxb.jyb.tool.ImageDownLoader;
import com.lxb.jyb.tool.ScrollViewListener;
import com.lxb.jyb.tool.Soft;
import com.lxb.jyb.util.Constants;
import com.lxb.jyb.util.HttpConstant;
import com.lxb.jyb.util.SetStatiColor;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

public class XinWenWebActivity extends Activity implements ScrollViewListener,
        OnClickListener {
    private WebView webview;
    private LinearLayout bottom_lay;
    private ImageView bottom_return, bottom_shared;
    private ListView listview;
    private ObservableScrollView scrolist;
    private WebListViewAdapter listadapter;
    private ArrayList<NewsBean> list;
    private ArrayList<NewsBean> allList;
    private NewsBean bean;
    private String newsId;
    /**
     * 数据源
     */
    private ImageDownLoader loader;

    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    // 手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指按下时的纵坐标。
    private float yDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 记录手指移动时的纵坐标。
    private float yMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    /**
     * 设置透明度渐变动画
     */
    final AlphaAnimation canimation = new AlphaAnimation(1, 0);

    final AlphaAnimation ranimation = new AlphaAnimation(0, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.xw_item_webview);
        findViewById(R.id.xw_xml).setBackgroundColor(
                Color.parseColor("#ffffff"));
        SetStatiColor.setMiuiStatusBarDarkMode(this, true);
        canimation.setDuration(2000);// 设置动画持续时间
        ranimation.setDuration(2000);// 设置动画持续时间
        receiveIntent();

    }

    /**
     * 获取传递过来的数据
     */
    private void receiveIntent() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        newsId = intent.getStringExtra("newsid");
        allList = intent.getParcelableArrayListExtra("list");
        if (!"".equals(newsId) && null != allList) {
            list = new ArrayList<NewsBean>();
            for (int i = 0; i < allList.size(); i++) {

                if (!allList.get(i).getNewsId().equals(newsId)) {
                    list.add(allList.get(i));
                } else {
                    bean = allList.get(i);
                }
            }

            initFind();
        }
    }

    /**
     * 初始化控件
     */
    private void initFind() {
        // TODO Auto-generated method stub
        webview = (WebView) findViewById(R.id.xw_web);
        bottom_lay = (LinearLayout) findViewById(R.id.bottom_lay);
        bottom_return = (ImageView) findViewById(R.id.bottom_return);
        bottom_shared = (ImageView) findViewById(R.id.bottom_share);
        listview = (ListView) findViewById(R.id.xg_list);
        scrolist = (ObservableScrollView) findViewById(R.id.scroll_list);
        bottom_return.setOnClickListener(this);
        bottom_shared.setOnClickListener(this);

        loader = new ImageDownLoader(this);
        initWeb();
    }

    private void initWeb() {
        // TODO Auto-generated method stub
        webview.loadUrl(HttpConstant.NEWS_WEBHOST + newsId);
        WebSettings settings = webview.getSettings();
        settings.setSupportZoom(false); // 支持缩放
        settings.setBuiltInZoomControls(false); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setRenderPriority(RenderPriority.HIGH);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                findViewById(R.id.relative_yw_show).setVisibility(View.GONE);
                findViewById(R.id.bottom_lay).setVisibility(View.VISIBLE);
                scrolist.setScrollViewListener(XinWenWebActivity.this);
                initView();
            }

        });

    }

    private void initView() {
        // TODO Auto-generated method stub
        // findViewById(R.id.xg_lay).setVisibility(View.VISIBLE);
        listadapter = new WebListViewAdapter(list, this);
        listview.setAdapter(listadapter);
        setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(listener);

    }

    OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            // Toast.makeText(XinWenWebActivity.this, "" + position, 1).show();
            Intent intent = new Intent(XinWenWebActivity.this,
                    XinWenWebActivity.class);
            intent.putExtra("newsid", list.get(position).getNewsId());
            intent.putParcelableArrayListExtra("list", allList);
            startActivity(intent);

        }

    };

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i <= 5; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;// if without this statement,the listview will be a
        // little short
        listView.setLayoutParams(params);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        scrollView.scrollTo(x, y);
        findViewById(R.id.xg_lay).setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        // TODO 点击事件
        switch (v.getId()) {
            case R.id.bottom_return:
                this.finish();
                break;

            case R.id.bottom_share:
                accredit = new Accredit(XinWenWebActivity.this, mController);

                mController.getConfig().setSsoHandler(new SinaSsoHandler());
                mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
                accredit.addQQQZonePlatform();
                accredit.addWXPlatform();
                // accredit.setShareContent("金汇财经", mItemList.get(tag).getUrl(),
                // "财经资讯", mController, "");
                String title = bean.getNewsTitle();
                String content = bean.getNewsAbstract();
                Bitmap bitmap = loader.getBitmapCache(bean.getNewsThumbnail());

                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(this.getResources(),
                            R.drawable.ic_launcher);
                }
                String url = HttpConstant.NEWS_WEBHOST + bean.getNewsId();

                accredit.setShareContent(this, title, content, url, bitmap);
                mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,
                        SHARE_MEDIA.DOUBAN, SHARE_MEDIA.RENREN);
                mController.openShare(XinWenWebActivity.this, false);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private Accredit accredit;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.DESCRIPTOR);


}
