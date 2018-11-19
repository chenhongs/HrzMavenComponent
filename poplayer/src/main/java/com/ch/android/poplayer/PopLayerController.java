package com.ch.android.poplayer;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.ch.android.poplayer.impl.PushManagerImpl;
import com.ch.android.poplayer.impl.TimerManagerImpl;
import com.ch.android.poplayer.interfaces.LayerTouchSystem;
import com.ch.android.poplayer.interfaces.NavigationManager;
import com.ch.android.poplayer.interfaces.PushManager;
import com.ch.android.poplayer.interfaces.TimerManager;
import com.ch.android.poplayer.interfaces.WebViewConfig;

/**
 * 装配能力接口
 */
public class PopLayerController {

    private  final String TAG=getClass().getSimpleName();

    //功能组成
    private TimerManager timerManagerImpl;
    private PushManager pushManagerImpl;
    private NavigationManager navigationManagerImpl;
    private WebViewConfig webViewConfigImpl;
    private LayerTouchSystem layerTouchImpl;

    //添加单例 使用内部类实现真正的延迟加载
    //当getInstance方法第一次被调用的时候，它第一次读取SingletonHolder.instance，
    // 内部类SingletonHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静态域，
    // 从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，
    // 并由虚拟机来保证它的线程安全性。

//    public static class Holder {
//        static HrzLayer INSTANCE=new HrzLayer(builders);
//    }
//
//    public static HrzLayer getInstance(Builder builder){
//        // 外围类能直接访问内部类（不管是否是静态的）的私有变量
//        builders=builder;
//        return Holder.INSTANCE;
//    }

    public PopLayerController(Builder builder){//私有化构造
       initialize(builder);
//       onCreate();
   }

    private void initialize(Builder builder) {
        this.timerManagerImpl=builder.timerManagerImpl;
        this.pushManagerImpl=builder.pushManagerImpl;
        this.navigationManagerImpl=builder.navigationManagerImpl;
        this.webViewConfigImpl=builder.webViewConfigImpl;
        this.layerTouchImpl=builder.layerTouchImpl;
    }

    public TimerManager getTimerManagerImpl() {
        return timerManagerImpl;
    }

    public PushManager getPushManagerImpl() {
        return pushManagerImpl;
    }

    public NavigationManager getNavigationManagerImpl() {
        return navigationManagerImpl;
    }

    public WebViewConfig getWebViewConfigImpl() {
        return webViewConfigImpl;
    }

    public LayerTouchSystem getLayerTouchImpl() {
        return layerTouchImpl;
    }


    //////////////////////////////////内部类builder 配置适配层接口//////////////////////////////////////////////

    public static PopLayerController.Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private TimerManager timerManagerImpl;
        private PushManager pushManagerImpl;
        private NavigationManager navigationManagerImpl;
        private WebViewConfig webViewConfigImpl;//包括了 ui接口和交互接口
        private LayerTouchSystem layerTouchImpl;


        public PopLayerController.Builder setTimerManagerImpl(TimerManagerImpl timerManagerImpls) {
            this.timerManagerImpl = timerManagerImpls;
            return this;
        }

        public PopLayerController.Builder setPushManagerImpl(PushManagerImpl pushManagerImpls) {
            this.pushManagerImpl = pushManagerImpls;
            return this;
        }

        public PopLayerController.Builder setWebViewConfigImpl(WebViewConfig webviewImpls) {
            this.webViewConfigImpl = webviewImpls;
            return this;
        }

        public PopLayerController.Builder setLayerTouchImpl(LayerTouchSystem layerTouchImpl) {
            this.layerTouchImpl = layerTouchImpl;
            return this;
        }

        public PopLayerController build(){
            return new PopLayerController(this);
        }

    }




}
