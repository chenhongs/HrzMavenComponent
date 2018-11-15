package com.ch.android.cssdk.service;

import com.ch.android.common.event.KeFuLoginEvent;
import com.ch.android.cssdk.bean.KeFuLoginBean;

import java.util.HashMap;
import java.util.Observable;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by mac on 2018/11/15.
 */

public interface CsService {
    @GET("/hrz/api/account/custom/service/login")
    @Headers({"Domain-Name: kefu_login_info"})
    io.reactivex.Observable<KeFuLoginBean> getKeFuLoginInfo(@QueryMap HashMap<String,String> map);
}
