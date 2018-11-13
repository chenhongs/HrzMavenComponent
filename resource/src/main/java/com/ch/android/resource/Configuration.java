package com.ch.android.resource;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class Configuration {

    public static final float PRELOAD_INTERVAL = 4.0f;// unit: hour

    public static final String GENERAL_DATA_URI = "";//TODO
    public static final String BUILD_IN_GENERAL_DATA_FILE_NAME = "build_in_general_data.json";

    /**
     * 缓存根目录。
     */
    public static final String CACHE_ROOT_DIR_NAME = "ResourceCache";

    /**
     * Bitmap缓存目录。
     */
    public static final String CACHE_BITMAP_DIR_NAME = "BitmapCache";

    public static final String getCacheRootDirectoryPath(Context context) {
        return getCachePath(context) + File.separator + CACHE_ROOT_DIR_NAME;
    }

    private static final String getCachePath(Context context) {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
//        return context.getCacheDir().getAbsolutePath();
    }

    public static class Constant {
        public static final int TYPE_TOP_BANNER = 1;
        public static final int TYPE_ICON_LIST = 2;
        public static final int TYPE_NEW_USER = 3;
        public static final int TYPE_JD_BULLETIN = 4;
        public static final int TYPE_SHOW_EVENT_3 = 7;
        public static final int TYPE_FIND_GOOD_SHOP = 15;
        public static final int TYPE_RECOMMENDED_WARE = 18;
        public static final int TYPE_WATERFALL_GOODS = 101;
        public static final int TYPE_WATERFALL_ACTIVITY = 102;
    }
}
