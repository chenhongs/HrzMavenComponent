package com.ch.android.resource.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ch.android.resource.Configuration;
import com.ch.android.resource.InternalResourceManager;
import com.ch.android.resource.model.DiskHelper;
import com.ch.android.resource.model.LoadCallback;
import com.ch.android.resource.model.NetHelper;
import com.ch.android.resource.util.LogUtils;
import com.ch.android.resource.util.MD5Utils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitmapResourceHelper {

    private static final String TAG = "BitmapResourceHelper";

    private Context mContext;
    private InternalResourceManager mInternalResMgr;

    private Map<String, String> mBitmapCachePathMap;

    public BitmapResourceHelper(InternalResourceManager internalResMgr) {
        mContext = internalResMgr.getContext();
        mInternalResMgr = internalResMgr;
        mBitmapCachePathMap = new HashMap<>();
    }

    public Bitmap getBitmapForUri(String uri) {
        Bitmap result = null;
        String md5Str = MD5Utils.getMD5String(uri);
        if (mBitmapCachePathMap.containsKey(md5Str)) {
            // 如果在磁盘中，直接读出
            LogUtils.i(TAG, "getBitmapForUri >> DISK uri = " + uri);
            result = DiskHelper.getDefault(mContext).readBitmap(mBitmapCachePathMap.get(md5Str));
        } else {
            // 如果不在磁盘中，从网络中获取
            LogUtils.i(TAG, "getBitmapForUri >> NET uri = " + uri);
            result = NetHelper.getDefault(mContext).loadBitmap(uri);
        }

        return result;
    }

    public void loadBitmapForImageView(String uri, ImageView target) {
        String md5Str = MD5Utils.getMD5String(uri);
        if (mBitmapCachePathMap.containsKey(md5Str)) {
            // 如果在磁盘中，直接读出
            LogUtils.i(TAG, "loadBitmapForImageView >> DISK uri = " + uri);
            Glide.with(target).load(new File(mBitmapCachePathMap.get(md5Str))).into(target);
        } else {
            // 如果不在磁盘中，从网络中获取
            LogUtils.i(TAG, "loadBitmapForImageView >> NET uri = " + uri);
            Glide.with(target).load(uri).into(target);
        }
    }

    public void refreshBitmapCacheForResourcePackage(String pkgResCacheDirPath, List<String> uriList) {
        final File bmpCacheDir = new File(pkgResCacheDirPath + File.separator + Configuration.CACHE_BITMAP_DIR_NAME);
        if (!bmpCacheDir.exists()) {
            LogUtils.d(TAG, "loadBitmapForUri >> bmpCacheDir is not existing.");
            return;
        }

        List<String> bmpFileNameListToDel = new ArrayList<>();
        List<String> bmpFileNameListDontLoad = new ArrayList<>();

        // 删除掉已经不是这个ResourcePackage的图片缓存
        String[] bitmapFileNames = bmpCacheDir.list();
        if (bitmapFileNames != null) {
            for (String bitmapFileName : bitmapFileNames) {
                boolean shouldDel = true;
                for (String uri : uriList) {
                    if (bitmapFileName.equals(MD5Utils.getMD5String(uri))) {
                        shouldDel = false;
                        bmpFileNameListDontLoad.add(bitmapFileName);
                        break;
                    }
                }
                if (shouldDel) {
                    bmpFileNameListToDel.add(bitmapFileName);
                }
            }
        }
        for (String bmpFileName : bmpFileNameListToDel) {
            File file = new File(bmpCacheDir.getAbsolutePath() + File.separator + bmpFileName);
            file.delete();
        }

        // 从网络获取，并且保存到磁盘中
        NetHelper netHelper = NetHelper.getDefault(mContext);
        final DiskHelper diskHelper = DiskHelper.getDefault(mContext);
        for (final String uri : uriList) {
            final String md5Str = MD5Utils.getMD5String(uri);
            if (bmpFileNameListDontLoad.contains(md5Str)) {
                continue;
            }

            netHelper.loadBitmapAsync(uri, new LoadCallback<Bitmap>() {
                @Override
                public void onLoadCompleted(Bitmap result) {
                    diskHelper.saveBitmap(
                            bmpCacheDir.getAbsolutePath() + File.separator + md5Str,
                            result);
                    updateBitmapCachePathMap();
                }

                @Override
                public void onLoadFailed(Throwable error) {

                }
            });
        }

        updateBitmapCachePathMap();
    }

    private void updateBitmapCachePathMap() {
        mBitmapCachePathMap.clear();
        File cacheRootDir = new File(Configuration.getCacheRootDirectoryPath(mContext));
        if (!cacheRootDir.exists()) return;

        String[] resPkgDirNames = cacheRootDir.list();
        if (resPkgDirNames == null || resPkgDirNames.length == 0) return;

        for (String resPkgDirName : resPkgDirNames) {
            String bmpCacheDirPath = cacheRootDir.getAbsolutePath()
                    + File.separator
                    + resPkgDirName
                    + File.separator
                    + Configuration.CACHE_BITMAP_DIR_NAME;
            File bitmapCacheDir = new File(bmpCacheDirPath);
            if (!bitmapCacheDir.exists()) continue;
            File[] bitmapFiles = bitmapCacheDir.listFiles();
            if (bitmapFiles == null || bitmapFiles.length == 0) continue;
            for (File bitmapFile : bitmapFiles) {
                mBitmapCachePathMap.put(bitmapFile.getName(), bitmapFile.getAbsolutePath());
            }
        }
    }
}
