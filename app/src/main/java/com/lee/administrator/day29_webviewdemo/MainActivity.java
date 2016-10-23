package com.lee.administrator.day29_webviewdemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

/**
 * 了解WebView的使用
* WebView一般是用来加载网页内容的，给它个链接，直接能给你打开个网页。*/
public class MainActivity extends AppCompatActivity {
    String urlString = "http://www.jd.com";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.webView);
        final WebSettings settings = webView.getSettings();
        // 为了让网页加载速度快一点，在加载网页的时候，先阻止网络图片加載
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptEnabled(true);     // 设置支持javascript脚本

        //点击超链接后不弹出浏览器窗口，而在WebView控件中加载URL
        webView.setWebViewClient(new WebViewClient(){
            //重写开始加载的回调方法，这里可以做一些正在加载的提示！
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
                Log.i("print", "onPageStarted: ");
                if(dialog == null || dialog.isShowing()) {
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //设置进度条的样式
                    dialog.setTitle("正在加载...");
                    dialog.show();
                }
            }
            //重写页面加载完成的回调，页面加载完成可以开始加载图片。可以取消加载的提示！
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("print", "onPageFinished: ");
//                super.onPageFinished(view, url);
                if(dialog != null) {
                    dialog.dismiss();   //取消对话框
                }
                settings.setBlockNetworkImage(true);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("print", "shouldOverrideUrlLoading: ");
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(urlString); //加载网页

    }
}
