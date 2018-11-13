package com.ch.android.resource;

import android.content.Context;


import com.ch.android.resource.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

public class PreloadStrategy {

    private static final String TAG = "PreloadStrategy";

    private Context mContext;
    private InternalResourceManager mInternalResMgr;

    private boolean mHasScheduled;
    private Timer mTimer;
    private TimerTask mTimerTask = null;

    public PreloadStrategy(InternalResourceManager internalResMgr) {
        mContext = internalResMgr.getContext();
        mInternalResMgr = internalResMgr;
        mHasScheduled = false;
    }

    public void onMainActivityCreate() {
        LogUtils.i(TAG, "onMainActivityCreate");
        setupAlarm();
    }

    public void onMainActivityDestroy() {
        LogUtils.i(TAG, "onMainActivityDestroy");
        cancelAlarm();
    }

    private void refresh() {
        // mInternalResMgr.refresh();
    }

    private void setupAlarm() {
        if (!mHasScheduled) {
            mTimer = new Timer(true);
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    refresh();
                }
            };
            mTimer.schedule(mTimerTask, 0, (long)(Configuration.PRELOAD_INTERVAL * 60 * 60 * 1000));

            mHasScheduled = true;
        }
    }

    private void cancelAlarm() {
        if (mHasScheduled) {
            mTimer.cancel();

            mHasScheduled = false;
        }
    }
}
