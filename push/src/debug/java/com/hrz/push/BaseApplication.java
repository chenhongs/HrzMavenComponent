package com.hrz.push;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/17.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Application;
import android.content.Context;

import com.huawei.android.hms.agent.HMSAgent;

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
       HMSAgent.init(this);
    }
}
