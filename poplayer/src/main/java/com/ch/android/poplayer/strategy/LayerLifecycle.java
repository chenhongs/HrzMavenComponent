package com.ch.android.poplayer.strategy;

import android.content.Context;

/**
 * 生命周期管理
 */
public interface LayerLifecycle {

    void onCreate(Context context);
    void onShow(Context context);
    void onDismiss(Context context);
    void onRecycle(Context context);

}
