package com.ch.android.common.dagger.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ch.android.common.base.interal.lifecycle.ActivityLifecycle;
import com.ch.android.common.base.interal.lifecycle.ActivityLifecycleForRxLifecycle;
import com.ch.android.common.base.interal.lifecycle.FragmentLifecycle;
import com.ch.android.common.data.IRepositoryManager;
import com.ch.android.common.data.RepositoryManager;
import com.ch.android.common.data.cache.Cache;
import com.ch.android.common.data.cache.CacheType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class ApplicationModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null)
            configuration.configGson(application, builder);
        return builder.create();
    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);
    //这个就是告诉IRepositoryManager，这个IRepositoryManager是用的RepositoryManager类实现的

    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    //通过接口 找到 具体实现类 即(ActivityLifecycle)
    @Binds
    @Named("ActivityLifecycle")//name进行区分 之后都必须用name区分
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    @Named("ActivityLifecycleForRxLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycleForRxLifecycle(ActivityLifecycleForRxLifecycle activityLifecycleForRxLifecycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles(){
        return new ArrayList<>();
    }


}
