package com.ch.android.cssdk.di.component;

import dagger.Component;


import com.ch.android.common.dagger.component.AppComponent;
import com.ch.android.common.dagger.scope.ActivityScope;
import com.ch.android.cssdk.di.module.CSModule;


import com.ch.android.cssdk.mvp.ui.activity.CSActivity;

@ActivityScope
@Component(modules = CSModule.class, dependencies = AppComponent.class)
public interface CSComponent {
    void inject(CSActivity activity);
}