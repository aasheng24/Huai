package com.cmit.jsbridgedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new JSBridgeWebChromeClient());
        JSBridge.register("bridge",BridgeImpl.class);
        mWebView.loadUrl("file:///android_asset/index.html");
    }


    private void test() {
        String s = "adad dada";
        s.charAt(s.length() - 1);
        String[] b = s.split(" ");
        s.length();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("da");
        List<Integer> ssss = new ArrayList<>();
    }
}
