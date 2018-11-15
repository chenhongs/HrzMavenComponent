package com.ch.android.common.configuration;



import android.widget.Toast;

import com.ch.android.common.dagger.module.GlobalConfigModule;
import com.ch.android.common.exp.NoNetWorkException;

import io.reactivex.observers.DisposableObserver;

/**
 * @author：admin on 2017/4/18 15:14.
 */

public abstract class ErrorDisposableObserver<T> extends DisposableObserver<T> {
    @Override
    public void onError(Throwable e) {
        //此处可按状态码解析或error类型，进行分别处理其他error,此处只处理一种
        if(e instanceof NoNetWorkException){

        }
    }
}
