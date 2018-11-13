package com.ch.android.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;


import com.ch.android.resource.controller.BitmapResourceHelper;
import com.ch.android.resource.controller.GeneralDataHelper;
import com.ch.android.resource.controller.data_structure.HomeData;
import com.ch.android.resource.controller.data_structure.ResourcePackage;

import java.io.File;

public class InternalResourceManager {

    private static final String TAG = "InternalResourceManager";

    private Context mContext;
    private GeneralDataHelper mGeneralDataHelper;
    private BitmapResourceHelper mBmpResHelper;
    private PreloadStrategy mPreloadStrategy;

    public InternalResourceManager(Context context) {
        mContext = context;
        mBmpResHelper = new BitmapResourceHelper(this);
        mGeneralDataHelper = new GeneralDataHelper(this);
        mPreloadStrategy = new PreloadStrategy(this);
    }

    public HomeData.ItemInfoListBean getDataForModule(int module) {
        return mGeneralDataHelper.getDataForModule(module);
    }

    public Bitmap getBitmapForUri(String uri) {
        return mBmpResHelper.getBitmapForUri(uri);
    }

    public void loadBitmapForImageView(String uri, ImageView target) {
        mBmpResHelper.loadBitmapForImageView(uri, target);
    }

    public Context getContext() {
        return mContext;
    }

    public void refresh() {
        mGeneralDataHelper.refresh();
    }

    public void updateBitmapCache(ResourcePackage resPkg) {
        String pkgResCacheDirPath = Configuration.getCacheRootDirectoryPath(mContext)
                + File.separator
                + resPkg.getType();
        mBmpResHelper.refreshBitmapCacheForResourcePackage(pkgResCacheDirPath, resPkg.getAllBitmapUriList());
    }

    public void onMainActivityCreate() {
        mPreloadStrategy.onMainActivityCreate();
    }

    public void onMainActivityDestroy() {
        mPreloadStrategy.onMainActivityDestroy();
    }
}
