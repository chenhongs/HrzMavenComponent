package com.ch.android.poplayer.strategy;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/6.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.ch.android.poplayer.R;
import com.ch.android.poplayer.interfaces.LayerTouchSystem;
import com.ch.android.poplayer.interfaces.WebViewConfig;
import com.ch.android.poplayer.util.PopUtils;
import com.ch.android.poplayer.webview.PopWebView;

import static android.view.View.GONE;

/**
 * @author  CH
 * 显示透明的wbview窗口
 */
public class WebViewLayerStrategyImpl implements LayerLifecycle,ILayerStrategy{

    public  final String TAG=this.getClass().getSimpleName();

    private WebViewConfig webViewConfig;

    private LayerTouchSystem layerTouchSystem;

    private boolean isWebViewAdded=false;

    private Bitmap bitmap;
    private int alpha;
    private PopWebView myWebView;
    private String loadScheme;

    private View webLayout;


    public void setLayerTouchSystem(LayerTouchSystem layerTouchSystem) {
        this.layerTouchSystem = layerTouchSystem;
    }

    public WebViewLayerStrategyImpl(WebViewConfig webViewConfig, String loadScheme) {
        this.webViewConfig = webViewConfig;
        this.loadScheme=loadScheme;
    }

    @Override
    public void onCreate(Context context) {

        myWebView=((Activity)context).findViewById(R.id.wv);

        if(myWebView==null){
            isWebViewAdded=false;
            webLayout= View.inflate(context, R.layout.web_layout, null);
            myWebView = (PopWebView) webLayout.findViewById(R.id.wv);
            webViewConfig.setUpWebConfig(myWebView, loadScheme);
            myWebView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    //每一次触摸生成bitmap
                    bitmap= PopUtils.getBitmapFromView(myWebView);
                    //获取触摸点的ARGB的alpha值 将位图回收
                    if (null != bitmap) {
                        int pixel = bitmap.getPixel((int)event.getX(), (int)event.getY());
                        alpha = Color.alpha(pixel);
                        Log.e(TAG,event.getX()+"**"+event.getY()+"**"+alpha);
                        bitmap.recycle();
                    }

                    if(alpha==255){//实体
                        if(layerTouchSystem!=null){
                            layerTouchSystem.onTouchSolidArea(myWebView);
                        }
                        Log.e(TAG,"触摸监听器为true 消费调 webview的ontouch不执行");
                        return false;
                    }

                    if(layerTouchSystem!=null){
                        layerTouchSystem.onTouchTransparentArea(myWebView);
                    }
                    Log.e(TAG,"设置触摸监听器:返回false");

                    return false;
                }
            });
        }else {
            isWebViewAdded=true;
        }
    }

    @Override
    public void onShow(Context context) {
        if(isWebViewAdded){
            myWebView.setVisibility(View.VISIBLE);
        }else {
            ((Activity)context).getWindow().addContentView(webLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void onDismiss(Context context) {
        myWebView=((Activity)context).findViewById(R.id.wv);
        if(myWebView!=null){
            myWebView.setVisibility(GONE);
        }
    }

    @Override
    public void onRecycle(Context context) {
        myWebView=((Activity)context).findViewById(R.id.wv);
        if(myWebView!=null){
            myWebView.stopLoading();
//            myWebView.setWebViewListener(null);
            myWebView.clearHistory();
            myWebView.clearCache(true);
            myWebView.loadUrl("about:blank");
            myWebView.pauseTimers();
            myWebView = null;
        }
    }

    @Override
    public void setLayerCanCancel(boolean cancel) {

    }
}
