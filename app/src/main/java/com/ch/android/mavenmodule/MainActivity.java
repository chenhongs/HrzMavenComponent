package com.ch.android.mavenmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ch.android.JdSdkRouter;
import com.ch.android.resource.HrzResourceManagerImpl;
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

    }
}
