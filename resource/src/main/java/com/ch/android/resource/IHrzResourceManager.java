package com.ch.android.resource;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ch.android.resource.controller.data_structure.HomeData;


public interface IHrzResourceManager {

    // TODO 重命名
    int MODULE_A = 0;
    int MODULE_B = 1;
    int MODULE_C = 2;
    int MODULE_D = 3;

    HomeData.ItemInfoListBean getDataForModule(int module);
    Bitmap getBitmapForUri(String uri);
    void loadBitmapForImageView(String uri, ImageView target);
    void refreshResource();
}
