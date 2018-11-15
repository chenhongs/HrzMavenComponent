package com.ch.android.cssdk.mvp.presenter;

import android.app.Application;


import com.ch.android.common.base.interal.AppManager;
import com.ch.android.common.base.mvp.BasePresenter;
import com.ch.android.common.dagger.scope.ActivityScope;
import com.ch.android.common.event.KeFuInitEvent;
import com.ch.android.common.user.LoginDataManager;
import com.ch.android.cssdk.bean.KeFuLoginBean;
import com.ch.android.cssdk.mvp.contract.CSContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class CSPresenter extends BasePresenter<CSContract.Model, CSContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    AppManager mAppManager;

    @Inject
    public CSPresenter(CSContract.Model model, CSContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }


    public void requestCsLoginInfo(HashMap<String, String> map){

        mModel.getCsLoginInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<KeFuLoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(KeFuLoginBean keFuLoginBean) {
                       if(mRootView!=null&&keFuLoginBean!=null&&keFuLoginBean.getData()!=null){
                           mRootView.getCsLoginInfoSuccess(keFuLoginBean);
                       }else {
                           mRootView.getCsLoginInfoFail("");
                       }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getCsLoginInfoFail("");
                    }
                });

    }
}
