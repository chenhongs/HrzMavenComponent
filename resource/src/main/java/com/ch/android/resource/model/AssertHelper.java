package com.ch.android.resource.model;

import android.content.Context;


import com.ch.android.resource.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssertHelper {

    private static final String TAG = "AssertHelper";

    private static AssertHelper sDefault;

    private Context mContext;

    private AssertHelper(Context context) {
        mContext = context;
    }

    public static AssertHelper getDefault(Context context) {
        if (sDefault == null) {
            synchronized (AssertHelper.class) {
                if (sDefault == null) {
                    sDefault = new AssertHelper(context);
                }
            }
        }
        return sDefault;
    }

    public String parseJson(String assertName) {
        BufferedReader br = null;
        try {
            InputStream is = mContext.getAssets().open(assertName);
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, "parseJson(" + assertName + ") occurs an error.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
