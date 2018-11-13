package com.ch.android.resource.util;

import android.util.Log;

public class LogUtils {

    private static final String TAG_PREFIX = "ResourceManager|";
    private static final boolean DEBUG = true;

    public static void i(String tag, String message) {
        if (DEBUG)
            Log.i(tag, addPrefixForMsg(message));
    }

    public static void e(String tag, String message) {
        if (DEBUG)
            Log.e(tag, addPrefixForMsg(message));
    }

    public static void e(String tag, String message, Throwable throwble) {
        if (DEBUG)
            Log.e(tag, addPrefixForMsg(message), throwble);
    }

    public static void w(String tag, String message) {
        if (DEBUG)
            Log.w(tag, addPrefixForMsg(message));
    }

    public static void w(String tag, String message, Throwable throwble) {
        if (DEBUG)
            Log.w(tag, addPrefixForMsg(message), throwble);
    }

    public static void d(String tag, String message) {
        if (DEBUG)
            Log.d(tag, addPrefixForMsg(message));
    }

    public static void v(String tag, String message) {
        if (DEBUG)
            Log.v(tag, addPrefixForMsg(message));
    }

    public static void e(Throwable e) {
        if (DEBUG)
            e.printStackTrace();
    }

    public static void p(Object e) {
        if (DEBUG)
            System.out.println(e.toString());
    }

    private static String addPrefixForMsg(String msg) {
        return TAG_PREFIX + msg;
    }
}
