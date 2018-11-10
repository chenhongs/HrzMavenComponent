package com.google.zxing.application;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/22.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.util.Log;

import io.github.prototypez.appjoint.core.ModuleSpec;

@ModuleSpec
public class QrcodeModuleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(this.getClass().getSimpleName(),"业务组件初始化");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(getClass().getSimpleName(),"onTerminate");
    }

}
