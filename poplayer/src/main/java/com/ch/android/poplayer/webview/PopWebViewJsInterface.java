package com.ch.android.poplayer.webview;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.ch.android.poplayer.interfaces.WebViewListener;

public class PopWebViewJsInterface extends Object{

    private WebViewListener listener;

    private WebView mWebView;

    public PopWebViewJsInterface(WebView webView,WebViewListener onWebViewListener) {
        this.mWebView=webView;
        this.listener=onWebViewListener;
    }

    @JavascriptInterface
    public void hidePopLayer() {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                listener.OnWebViewDissmissListener();
                mWebView.setVisibility(View.GONE);
            }
        });
    }


}
