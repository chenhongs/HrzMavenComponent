package com.google.zxing.view;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/19.Best Wishes to You!  []~(~▽~)~* Cheers!

import java.util.Observable;

/**
 * 专门产出数据的工厂 作为builder的一部分传入
 */
public class CaptureFactory implements CaptureBlock.EditTypeFactory{


    /*
    * 构造方法接受参数
     */
    public CaptureFactory() {
    }


    @Override
    public Observable getCaptureCodes() {
        //异步获取数据返回
        return null;
    }

    @Override
    public void setCaptureView(Observable observable) {
        //接受异步获取的数据并更新ui
    }
}
