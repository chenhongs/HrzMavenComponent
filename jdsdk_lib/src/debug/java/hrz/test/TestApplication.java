package hrz.test;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/21.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;

import com.ch.android.alibabaSDK.AlibabaSDK;

public class TestApplication extends Application{


    public static TestApplication application=null;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        //初始化与包名无关
        //安全图片 可能和签名有关 需要对应
        AlibabaSDK.initSDK(application);
    }
}
