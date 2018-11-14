package com.ch.android.servicecomponent;


import android.database.Observable;

/**
 * Created by mac on 2018/11/13.
 */

public interface BaseInvokeService {

    /**
     * 普通的同步方法调用
     */
    String syncMethodOfApp();

    /**
     * 以 RxJava 形式封装的异步方法
     */
//    Observable<String> asyncMethod1OfApp();

    /**
     * 以 Callback 形式封装的异步方法
     */
    void asyncMethod2OfApp(ServiceCallBack<String> callback);

}
