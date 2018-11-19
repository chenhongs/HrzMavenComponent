package com.ch.android.poplayer.interfaces;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.webkit.WebView;

/**
 * 配置webview加载
 */
public interface WebViewConfig {
    /**
     * @param webView
     * @param showScheme 加载的url
     */
    void setUpWebConfig(WebView webView, String showScheme);

    //初始化 交互接口
    void initHybirdImpl(HybirdManager manager);
    //初始化 ui接口
    void initUiImpl(WebViewUiManager manager);

    void initWebInterfaceImpl(WebViewListener listener);

}
