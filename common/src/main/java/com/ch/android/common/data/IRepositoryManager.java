
package com.ch.android.common.data;

import android.content.Context;

import com.ch.android.common.mvp.iterface.IModel;


/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link IModel} 必要的 Api 做数据处理
 * ================================================
 */
public interface IRepositoryManager {

    <T> T obtainRetrofitService(Class<T> service);

    <T> T obtainCacheService(Class<T> cache);

    <T> T obtainTypeService(Class<T> datasource);

    <T> T obtainDBService(Class<T> db);

    void clearAllCache();

    Context getContext();

}
