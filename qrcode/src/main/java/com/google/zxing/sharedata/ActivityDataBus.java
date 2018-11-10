package com.google.zxing.sharedata;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/20.Best Wishes to You!  []~(~▽~)~* Cheers!

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

public class ActivityDataBus {

    public static <T extends ActivityShareData> T getData(Context context, Class<T> tClass) {
        return getData(checkContext(context),tClass);
    }

    public static <T extends ActivityShareData> T getData(Activity context, Class<T> tClass) {
        return getData(checkContext(context),tClass);
    }

    public static <T extends ActivityShareData> T getData(FragmentActivity context, Class<T> tClass) {
        return ViewModelProviders.of(context).get(tClass);
    }

    private static FragmentActivity checkContext(Context context) {
        if(context instanceof FragmentActivity) return (FragmentActivity) context;
        throw new IllegalContextException();
    }

    public static class ActivityShareData extends ViewModel {}

    public static class IllegalContextException extends RuntimeException {
        public IllegalContextException() {
            super("ActivityDataBus 需要FragmentActivity作为上下文！");
        }
    }

}

