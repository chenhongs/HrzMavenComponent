package com.ch.android.mavenmodule;

import com.ch.android.servicecomponent.BaseInvokeService;
import com.ch.android.servicecomponent.ServiceCallBack;

/**
 * Created by mac on 2018/11/13.
 */
//@ServiceImpl
public class TestServiceImpl implements BaseInvokeService {

    @Override
    public String syncMethodOfApp() {
        return null;
    }

    @Override
    public void asyncMethod2OfApp(ServiceCallBack<String> callback) {

    }
}
