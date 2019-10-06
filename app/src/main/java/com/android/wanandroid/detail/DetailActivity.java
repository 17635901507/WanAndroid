package com.android.wanandroid.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.android.wanandroid.R;
import com.android.wanandroid.app.Constants;
import com.android.wanandroid.widget.LIWebView;
import com.kkk.mvp.base.BaseActivity;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/2 22:09
 */
public class DetailActivity extends BaseActivity {
    private Toolbar mMainToolbar;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
    }

    private void initView() {
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.ARG_PARAM1);
        String link = intent.getStringExtra(Constants.ARG_PARAM2);
        mMainToolbar.setTitle(title);
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setNavigationIcon(R.mipmap.back_arrow);
        mMainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebView(link);

    }



    private void initWebView(String link) {
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        mWebView = (WebView) findViewById(R.id.webview);
        //https://www.jianshu.com/p/345f4d8a5cfa
        //设置网页是否支持JS代码
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置WebView是否支持弹窗
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //设置当前网页显示在APP中的WebView里边
        mWebView.setWebViewClient(new WebViewClient() {
            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            //网页加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            //网页加载失败
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                webView.loadUrl("file:///android_asset/a.html");
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        });
        //设置图片缩放到屏幕大小,两个必须结合使用
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //设置网页可以双击缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);

        //设置false，隐藏系统的缩放控件
        mWebView.getSettings().setDisplayZoomControls(false);

        //设置WebView的缓存
        //有网的话就加载网页，没网的话就加载缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置自动加载图片
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        //设置编码格式
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setWebChromeClient(new WebChromeClient() {
            //获取网页的加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e(TAG, "onProgressChanged: " + newProgress);
                mProgressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.e(TAG, "onReceivedTitle: " + title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "onJsAlert: " + message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        mWebView.loadUrl(link);
    }

    public static void actionStart(Context context, String title, String link) {
        //设置Activity 进场动画
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(context,
                R.anim.activity_open, R.anim.activity_close);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.ARG_PARAM1, title);
        intent.putExtra(Constants.ARG_PARAM2, link);
        ActivityCompat.startActivity(context,intent, compat.toBundle());
    }
    @Override
    public void finish() {
        super.finish();
        ActivityCompat.finishAfterTransition(this);
    }

}
