package com.ch.android.cssdk.mvp.model;

import android.app.Application;

import com.ch.android.common.base.mvp.BaseModel;
import com.ch.android.common.dagger.scope.ActivityScope;
import com.ch.android.common.data.IRepositoryManager;
import com.ch.android.cssdk.bean.KeFuLoginBean;
import com.ch.android.cssdk.service.CsService;
import com.google.gson.Gson;


import javax.inject.Inject;

import com.ch.android.cssdk.mvp.contract.CSContract;

import java.util.HashMap;
import java.util.Observable;


@ActivityScope
public class CSModel extends BaseModel implements CSContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CSModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public io.reactivex.Observable<KeFuLoginBean> getCsLoginInfo(HashMap<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(CsService.class).getKeFuLoginInfo(map);
    }
}