package com.ch.android.mavenmodule.mine;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/22.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ch.android.JdSdk.JdSdkRouter;
import com.ch.android.alibabaSDK.AlibabaSDK;
import com.ch.android.common.base.delegate.AppLifecycles;

public class AppExtension implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull Context context) {
        Log.e("xxx","attachBaseContext");
    }

    @Override
    public void onCreate(@NonNull Application application) {
        Log.e("xxx","onCreate");
        JdSdkRouter.init(application,"447f389022c343f09294898204070742","a07e8bdc4929448382503f9bba815975");
        AlibabaSDK.initSDK(application);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
