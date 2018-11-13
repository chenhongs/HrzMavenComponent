package com.ch.android.resource.model;

import android.content.Context;

public class DbHelper {

    private static final String TAG = "DbHelper";

    private static DbHelper sDefault;

    private Context mContext;

    private DbHelper(Context context) {
        mContext = context;
    }

    public static DbHelper getDefault(Context context) {
        if (sDefault == null) {
            synchronized (DbHelper.class) {
                if (sDefault == null) {
                    sDefault = new DbHelper(context);
                }
            }
        }
        return sDefault;
    }
}
