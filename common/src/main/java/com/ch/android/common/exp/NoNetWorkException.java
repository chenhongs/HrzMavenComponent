package com.ch.android.common.exp;

/**
 * Created by admin on 2017/3/10.
 */

public class NoNetWorkException extends RuntimeException {
    public NoNetWorkException() {
    }

    public NoNetWorkException(String message) {
        super(message);
    }
}
