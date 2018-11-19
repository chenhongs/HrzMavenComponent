package com.ch.android.poplayer.webview;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.text.TextUtils;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ch.android.poplayer.interfaces.HybirdManager;
import com.ch.android.poplayer.interfaces.WebViewUiManager;


public class PopWebViewChromeClient extends WebChromeClient{


    private HybirdManager listener;
    private WebViewUiManager popWebViewListener;

    public void setListener(HybirdManager listener,WebViewUiManager webViewListener) {
        this.listener = listener;
        this.popWebViewListener=webViewListener;
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(newProgress>=80){
           if(popWebViewListener!=null){
               popWebViewListener.hideLoading();
           }
        }
    }

    /**
     * 显示播放视频视图
     * @param view
     * @param callback
     */
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if(popWebViewListener!=null){
            popWebViewListener.showFullScreenView(view);
        }
        super.onShowCustomView(view, callback);
    }


    /*
     * 隐藏播放视频视图
     */
    @Override
    public void onHideCustomView() {
        if(popWebViewListener!=null){
            popWebViewListener.hideFullScreenView();
        }
        super.onHideCustomView();
    }




    @Override
    public void onReceivedTitle(WebView view, String title) {
        //注入JSBridge的时机
        if(listener!=null){
            listener.injectJsBridge(view);
        }
        super.onReceivedTitle(view, title);
    }


    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        String postJson=message.replaceAll("\\\\", "");
        if (!TextUtils.isEmpty(postJson)) {
            if(listener!=null){
                listener.invokeAppServices(postJson);
            }
            return true;
        }
        return false;
    }
}
