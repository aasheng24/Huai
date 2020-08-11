package com.cmit.jsbridgedemo;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONObject;

public class BridgeImpl implements IBridge {
    public static void showToast(WebView webView, JSONObject parm, final CallBack callBack) {
        Log.i("ezreal","showToast");
        String message = parm.optString("msg");
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
