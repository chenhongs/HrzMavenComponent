package com.ch.android.mavenmodule;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ch.android.common.base.BaseApplication;
import com.ch.android.common.constant.Consts;
import com.ch.android.common.data.IRepositoryManager;
import com.ch.android.common.entity.bean.Const;
import com.ch.android.common.http.HttpParamName;
import com.ch.android.common.http.HttpParamUtil;
import com.ch.android.common.util.ArmsUtils;
import com.ch.android.cssdk.bean.KeFuLoginBean;
import com.ch.android.cssdk.mvp.contract.CSContract;
import com.ch.android.cssdk.mvp.model.CSModel;
import com.ch.android.cssdk.mvp.presenter.CSPresenter;
import com.ch.android.cssdk.util.KeFuSdkUtil;
import com.orhanobut.logger.Logger;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * Created by mac on 2018/11/15.
 */

public class MyTestService extends IntentService implements CSContract.View{


    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RetrofitUrlManager.getInstance().putDomain("kefu_login_info", Consts.PROTOCROL+Consts.TEST_SERVER);
        CSModel csModel=new CSModel(ArmsUtils.obtainAppComponentFromContext(this).repositoryManager());
        CSPresenter csPresenter=new CSPresenter(csModel,this);
        KeFuSdkUtil.getKefuLoginInfo(csPresenter,HttpParamUtil.getCommonSignParamMap(this,null));
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
