package com.lxb.jyb.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lxb.jyb.R;
import com.lxb.jyb.activity.KLineActivity;
import com.lxb.jyb.util.HttpConstant;

import static android.webkit.WebSettings.RenderPriority;

@SuppressWarnings("deprecation")
public class TuBiaoFragment extends Fragment implements View.OnClickListener {
    private View view;
    private WebView webView;
    private String code;
    private SensorManager sensorManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_tubiao, null);

        initGetIntent();
        initFindView();
        initWeb();
        return view;
    }

    @SuppressLint("ServiceCast")
    private void initGetIntent() {
        code = getActivity().getIntent().getStringExtra("code");
//       sensorManager = (SensorManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        sensorManager.
    }

    private void initFindView() {
        webView = (WebView) view.findViewById(R.id.tb_webview);
        view.findViewById(R.id.shouji_btn).setOnClickListener(this);
    }

    /**
     * 初始化第一个页面的webview
     */
    private void initWeb() {
        // TODO Auto-generated method stub
        String url = HttpConstant.HQ_WEBHOST + code;
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setRenderPriority(RenderPriority.HIGH);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
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
//                refash.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shouji_btn:
                Intent intent = new Intent(TuBiaoFragment.this.getActivity(), KLineActivity.class);
                intent.putExtra("code", code);
                startActivity(intent);
                break;
        }
    }
}
