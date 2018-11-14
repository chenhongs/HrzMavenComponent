package com.ch.android.mavenmodule;

import com.ch.android.servicecomponent.ModuleAService;
import com.ch.android.servicecomponent.ServiceCallBack;

/**
 * Created by mac on 2018/11/13.
 */

//@ServiceImpl
public class ModuleAImpl implements ModuleAService {
    @Override
    public void fuck() {

    }

    @Override
    public String syncMethodOfApp() {
        return null;
    }

    @Override
    public void asyncMethod2OfApp(ServiceCallBack<String> callback) {

    }
}
