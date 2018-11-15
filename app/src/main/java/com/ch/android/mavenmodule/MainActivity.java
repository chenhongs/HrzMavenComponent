package com.ch.android.mavenmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ch.android.JdSdkRouter;
import com.ch.android.cssdk.util.KeFuSdkUtil;
import com.ch.android.resource.HrzResourceManagerImpl;
import com.ch.android.servicecomponent.ModuleAService;
import com.ch.android.servicecomponent.ServiceLoader;
import com.google.zxing.view.CapturePage;

/**
 * Created by mac on 2018/11/10.
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapturePage capturePage=new CapturePage(this,null);
        HrzResourceManagerImpl hrzResourceManager;
        JdSdkRouter.init(getApplication(),"","");

        ModuleAService moduleAService= (ModuleAService) ServiceLoader.getInstance().getService(ModuleAService.class);
        moduleAService.fuck();

        //with 返回RequestManager
//        Glide.with(this).load(url).into(imageView);


    }
}
