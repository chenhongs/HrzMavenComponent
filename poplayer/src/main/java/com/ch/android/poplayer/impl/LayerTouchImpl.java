package com.ch.android.poplayer.impl;


import com.ch.android.poplayer.interfaces.LayerTouchSystem;
import com.ch.android.poplayer.webview.PopWebView;

/**
 * Created by mac on 2018/10/29.
 */

public class LayerTouchImpl implements LayerTouchSystem {
    @Override
    public void onTouchTransparentArea(PopWebView webView) {
        webView.setProcessor(1);
    }

    @Override
    public void onTouchSolidArea(PopWebView webView) {
        webView.setProcessor(0);
    }
}
