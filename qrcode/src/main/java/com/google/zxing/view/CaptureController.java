package com.google.zxing.view;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/19.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.content.Context;

/**
 * 用来管理这个组件的操作
 */
public class CaptureController {

    private Context mContext;
    private CaptureBlock mCaptureBlock;
    private CaptureBlock.EditTypeFactory mEditTypeFactory;

    /**
     *
     * @param context
     * @param block  拿block里的view
     * @param editTypeFactory 用这个创建数据源
     */
    public CaptureController(Context context, CaptureBlock block, CaptureBlock.EditTypeFactory editTypeFactory){
        this.mCaptureBlock=block;
        this.mContext=context;
        this.mEditTypeFactory=editTypeFactory;
    }



    public void startScan(){

    }





}
