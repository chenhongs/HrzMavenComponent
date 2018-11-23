package com.ch.android.cssdk.di.component;

import com.ch.android.cssdk.di.module.CSModule;
import com.ch.android.cssdk.mvp.ui.activity.CSActivity;

import dagger.Component;


@Component(modules = CSModule.class)
public interface CSComponent {
    void inject(CSActivity activity);
}