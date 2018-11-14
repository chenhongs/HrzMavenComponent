package com.ch.android.servicecomponent;

/**
 * Created by mac on 2018/11/13.
 */

public interface ServiceCallBack<T>{

    void loadSuccess(T t);
    void loadFail();
}
