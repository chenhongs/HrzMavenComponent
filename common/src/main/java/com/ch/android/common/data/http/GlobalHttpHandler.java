package com.ch.android.common.data.http;



import com.ch.android.common.dagger.module.GlobalConfigModule;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 处理 Http 请求和响应结果的处理类
 * 使用 {@link GlobalConfigModule.Builder#globalHttpHandler(GlobalHttpHandler)} 方法配置
 * ================================================
 */
public interface GlobalHttpHandler {


    //请求完成
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    //请求前
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    //空实现
    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            //不管是否处理,都必须将response返回出去
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            //不管是否处理,都必须将request返回出去
            return request;
        }
    };

}
