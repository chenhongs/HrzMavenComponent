package com.ch.android.cssdk.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ch.android.common.base.BaseActivity;
import com.ch.android.common.dagger.component.AppComponent;
import com.ch.android.common.util.ArmsUtils;
import com.ch.android.cssdk.bean.KeFuLoginBean;
import com.ch.android.cssdk.di.component.DaggerCSComponent;
import com.ch.android.cssdk.di.module.CSModule;
import com.ch.android.cssdk.mvp.contract.CSContract;
import com.ch.android.cssdk.mvp.presenter.CSPresenter;

import com.ch.android.cssdk.R;

import static com.ch.android.common.util.Preconditions.checkNotNull;


public class CSActivity extends BaseActivity<CSPresenter> implements CSContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCSComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .cSModule(new CSModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cs; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void getCsLoginInfoSuccess(KeFuLoginBean keFuLoginBean) {

    }

    @Override
    public void getCsLoginInfoFail(String msg) {

    }
}
