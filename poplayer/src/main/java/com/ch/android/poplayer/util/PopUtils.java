package com.ch.android.poplayer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 2018/10/29.
 */

public class PopUtils {

    /**
     * 获取view的bitmap
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        }
        else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    /**
     * JSBRIDGE的文本 用来注入
     * @param context
     * @return
     */
    public static String getJsCode(Context context,String fileName) {

        String str = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int lenght = 0;
            lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            str = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }




















}
