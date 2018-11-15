package com.ch.android.cssdk.di.module;


import dagger.Module;
import dagger.Provides;

import com.ch.android.common.dagger.scope.ActivityScope;
import com.ch.android.cssdk.mvp.contract.CSContract;
import com.ch.android.cssdk.mvp.model.CSModel;


@Module
public class CSModule {
    private CSContract.View view;

    /**
     * 构建CSModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CSModule(CSContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CSContract.View provideCSView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CSContract.Model provideCSModel(CSModel model) {
        return model;
    }
}