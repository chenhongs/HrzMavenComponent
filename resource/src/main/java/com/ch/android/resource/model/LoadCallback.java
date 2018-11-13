package com.ch.android.resource.model;

public interface LoadCallback<T> {

    void onLoadCompleted(T result);
    void onLoadFailed(Throwable error);
}
