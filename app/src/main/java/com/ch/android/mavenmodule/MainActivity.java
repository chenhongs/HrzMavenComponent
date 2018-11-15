package com.ch.android.mavenmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ch.android.JdSdkRouter;
import com.ch.android.common.dagger.component.AppComponent;
import com.ch.android.common.util.ArmsUtils;
import com.ch.android.cssdk.base.BaseActivity;
import com.ch.android.cssdk.util.KeFuSdkUtil;
import com.ch.android.resource.HrzResourceManagerImpl;
import com.ch.android.servicecomponent.ModuleAService;
import com.ch.android.servicecomponent.ServiceLoader;
import com.google.zxing.view.CapturePage;
import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/11/10.
 */

public class MainActivity extends com.ch.android.common.base.BaseActivity {

//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        CapturePage capturePage=new CapturePage(this,null);
////        HrzResourceManagerImpl hrzResourceManager;
////        JdSdkRouter.init(getApplication(),"","");
////
////        ModuleAService moduleAService= (ModuleAService) ServiceLoader.getInstance().getService(ModuleAService.class);
////        moduleAService.fuck();
//
//        //with 返回RequestManager
////        Glide.with(this).load(url).into(imageView);
//
////        Logger.e(ArmsUtils.obtainAppComponentFromContext(this).toString());
//
//
////        Intent intent = new Intent(MainActivity.this,MyTestService.class);
////        startService(intent);
//
//
//
//
//    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        Logger.e(appComponent.toString());
    }

    @Override
    public int initView(@Nullable Bundle bundle) {
        return R.layout.none_layout;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }
}
