package com.ch.android.poplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;

import com.ch.android.poplayer.impl.HybirdImpl;
import com.ch.android.poplayer.impl.LayerTouchImpl;
import com.ch.android.poplayer.impl.PushManagerImpl;
import com.ch.android.poplayer.impl.TimerManagerImpl;
import com.ch.android.poplayer.impl.WebConfigImpl;
import com.ch.android.poplayer.interfaces.WebViewConfig;
import com.ch.android.poplayer.interfaces.WebViewListener;
import com.ch.android.poplayer.interfaces.WebViewUiManager;
import com.ch.android.poplayer.pop.PopDismissListener;
import com.ch.android.poplayer.strategy.DialogLayerStrategyImpl;
import com.ch.android.poplayer.strategy.LayerLifecycle;
import com.ch.android.poplayer.strategy.WebViewLayerStrategyImpl;


public class PopLayerView extends View {



    public final String TAG=getClass().getSimpleName();

    private boolean isShow=false;

    public static int STATE_WEBVIEW=1;
    public static int STATE_DIALOG=0;
    public int state=-1;

    private PopDismissListener popDismissListener;

    public void setListener(PopDismissListener listener) {
        this.popDismissListener = listener;
    }

    private LayerLifecycle iLayerStrategy;

    public LayerLifecycle getiLayerStrategy() {
        return iLayerStrategy;
    }

    private DialogLayerStrategyImpl dialogLayerStrategy;
    private Context mContext;

    public PopLayerView(Context context, int state) {
        super(context);
        mContext=context;
        this.state=state;
    }

    public PopLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public PopLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }


    public void initLayerView(WebViewUiManager listener) {

        WebViewConfig webViewConfig=new WebConfigImpl();
        webViewConfig.initHybirdImpl(new HybirdImpl(mContext));
        webViewConfig.initUiImpl(listener);
        webViewConfig.initWebInterfaceImpl(new WebViewListener() {
            @Override
            public void OnWebViewDissmissListener() {
                dismiss();
            }
        });

        PopLayerController hrzLayer= PopLayerController.builder()
                .setPushManagerImpl(new PushManagerImpl())
                .setTimerManagerImpl(new TimerManagerImpl())
                .setLayerTouchImpl(new LayerTouchImpl())
                .setWebViewConfigImpl(webViewConfig)
                .build();


        if(state==STATE_DIALOG){
            if(dialogView!=null){
                dialogLayerStrategy=new DialogLayerStrategyImpl(dialogView,R.style.Dialog_Fullscreen);
                iLayerStrategy=dialogLayerStrategy;
            }
        }else if(state==STATE_WEBVIEW){
            WebViewLayerStrategyImpl webViewLayerStrategy=new WebViewLayerStrategyImpl(webViewConfig,loadScheme);
            webViewLayerStrategy.setLayerTouchSystem(hrzLayer.getLayerTouchImpl());
            iLayerStrategy=webViewLayerStrategy;
        }else {
            iLayerStrategy=null;
            return;
        }

        iLayerStrategy.onCreate(mContext);

        if(state==STATE_DIALOG){
            if(dialogLayerStrategy!=null){
                dialogLayerStrategy.getDialog().setCanceledOnTouchOutside(isDialogOutsideCancel);
                dialogLayerStrategy.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                         dismiss();
                    }
                });
            }
        }

//        hrzLayer.setLayerStrategyChooser(new LayerStrategyChooser(iLayerStrategy,mContext));
//        proxy= (LayerLifecycle) new LayerLifeCycleProxy(hrzLayer).getProxyInstance();
//        proxy.onCreate(mContext);
    }


    ///////////////////////////// HrzLayerView的显示状态 /////////////////////////////////////////

    public void show(){
        iLayerStrategy.onShow(mContext);
        isShow=true;
    }

    public void dismiss(){
        iLayerStrategy.onDismiss(mContext);
        if(popDismissListener!=null){
            popDismissListener.onPopDimiss();
        }
        isShow=false;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        iLayerStrategy.onRecycle(mContext);
    }

    public boolean isShow() {
        return isShow;
    }


    ///////////////////////////// 用户设置DialogView /////////////////////////////////////////

    private  View dialogView;

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public View getDialogView() {
        return dialogView;
    }

    private String loadScheme="";

    public void setLoadScheme(String loadScheme) {
        this.loadScheme = loadScheme;
    }

    private boolean isDialogOutsideCancel=true;

    public void setDialogOutsideCancel(boolean dialogOutsideCancel) {
        isDialogOutsideCancel = dialogOutsideCancel;
    }
}
