package com.ch.android.common.configuration;

import android.content.Context;

import com.ch.android.common.dagger.module.ApplicationModule;
import com.google.gson.GsonBuilder;



/**
 * @创建者 CSDN_LQR
 * @描述 Gson配置
 */
public class MyGsonConfiguration implements ApplicationModule.GsonConfiguration {
    @Override
    public void configGson(Context context, GsonBuilder builder) {
        builder
        .serializeNulls()//支持序列化null的参数
        .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
    }
}
