package com.ch.android.resource.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.ch.android.resource.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetHelper {

    private static final String TAG = "NetHelper";

    private static NetHelper sDefault;

    private Context mContext;
    private OkHttpClient mOkHttpClient;

    private NetHelper(Context context) {
        mContext = context;
        mOkHttpClient = new OkHttpClient();
    }

    public static NetHelper getDefault(Context context) {
        if (sDefault == null) {
            synchronized (NetHelper.class) {
                if (sDefault == null) {
                    sDefault = new NetHelper(context);
                }
            }
        }
        return sDefault;
    }

    public void loadJsonString(final String uri, final LoadCallback<String> callback) {
        Request request = new Request.Builder().url(uri).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String bodyStr = body.string();
                    LogUtil.i(TAG, "onResponse >> bodyStr = " + bodyStr);
                    callback.onLoadCompleted(bodyStr);
                } else {
                    callback.onLoadFailed(new NullPointerException("Response body is null."));
                }
            }
        });
    }

    public Bitmap loadBitmap(String uri) {
        Bitmap result = null;

        Request request = new Request.Builder().url(uri).build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                InputStream is = body.byteStream();
                result = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void loadBitmapAsync(final String uri,
                                final LoadCallback<Bitmap> callback) {
        Request request = new Request.Builder().url(uri).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    InputStream is = body.byteStream();
                    callback.onLoadCompleted(BitmapFactory.decodeStream(is));
                } else {
                    callback.onLoadFailed(new NullPointerException("Response body is null."));
                }
            }
        });
    }
}
