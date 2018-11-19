package com.ch.android.mavenmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ch.android.common.dagger.component.AppComponent;
import com.ch.android.common.dagger.scope.ActivityScope;


/**
 * Created by mac on 2018/11/10.
 */

@ActivityScope
public class MainActivity extends com.ch.android.common.base.BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle bundle) {
        return R.layout.none_layout;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        Log.d(getClass().getSimpleName(),"initData");

        



    }



    public void test(View v){
//        Log.d(getClass().getSimpleName(),"test");
//        Intent intent=new Intent(this,MyTestService.class);
//        startService(intent);
    }


}
