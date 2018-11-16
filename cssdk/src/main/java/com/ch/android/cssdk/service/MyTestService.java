package com.ch.android.cssdk.service;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/16.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ch.android.common.constant.Consts;
import com.ch.android.common.http.HttpParamUtil;
import com.ch.android.common.util.ArmsUtils;
import com.ch.android.cssdk.bean.KeFuLoginBean;
import com.ch.android.cssdk.mvp.contract.CSContract;
import com.ch.android.cssdk.mvp.model.CSModel;
import com.ch.android.cssdk.mvp.presenter.CSPresenter;
import com.ch.android.cssdk.util.KeFuSdkUtil;
import com.orhanobut.logger.Logger;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class MyTestService extends IntentService implements CSContract.View{

    public MyTestService() {
        super("MyTestService");
        Log.d(getClass().getSimpleName(),"MyTestService");


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(getClass().getSimpleName(),"onHandleIntent");
        RetrofitUrlManager.getInstance().putDomain("kefu_login_info", Consts.PROTOCROL+Consts.TEST_SERVER);
        CSModel csModel=new CSModel(ArmsUtils.obtainAppComponentFromContext(this.getApplicationContext()).repositoryManager());
        CSPresenter csPresenter=new CSPresenter(csModel,this);
        KeFuSdkUtil.getKefuLoginInfo(csPresenter, HttpParamUtil.getCommonSignParamMap(this,null));
    }


    @Override
    public void getCsLoginInfoSuccess(KeFuLoginBean keFuLoginBean) {
        Logger.e(keFuLoginBean.toString());
    }

    @Override
    public void getCsLoginInfoFail(String msg) {
        Logger.e("fail");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String s) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}