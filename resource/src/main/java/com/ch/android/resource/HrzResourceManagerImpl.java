package com.ch.android.resource;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ch.android.resource.controller.data_structure.HomeData;


public class HrzResourceManagerImpl implements IHrzResourceManager, LifecycleObserver {

    private static final String TAG = "HrzResourceManagerImpl";

    private static IHrzResourceManager sDefault;

    private Context mContext;
    private InternalResourceManager mInternalResMgr;

    private HrzResourceManagerImpl(Context context) {
        mContext = context.getApplicationContext();
        mInternalResMgr = new InternalResourceManager(mContext);
    }

    public static IHrzResourceManager getDefault(Context context) {
        if (sDefault == null) {
            synchronized (HrzResourceManagerImpl.class) {
                if (sDefault == null) {
                    sDefault = new HrzResourceManagerImpl(context);
                }
            }
        }
        return sDefault;
    }

    @Override
    public HomeData.ItemInfoListBean getDataForModule(int module) {
        return mInternalResMgr.getDataForModule(module);
    }

    @Override
    public Bitmap getBitmapForUri(String uri) {
        return mInternalResMgr.getBitmapForUri(uri);
    }

    @Override
    public void loadBitmapForImageView(String uri, ImageView target) {
        mInternalResMgr.loadBitmapForImageView(uri, target);
    }

    @Override
    public void refreshResource() {
        mInternalResMgr.refresh();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        mInternalResMgr.onMainActivityCreate();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mInternalResMgr.onMainActivityDestroy();
    }
}
