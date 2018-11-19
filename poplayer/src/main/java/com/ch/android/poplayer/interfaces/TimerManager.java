package com.ch.android.poplayer.interfaces;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/4.Best Wishes to You!  []~(~▽~)~* Cheers!

/**
 * 对下发活动的时间进行管理
 */
public interface TimerManager {

    //显示时间超出
    void onTimeOut();
    //显示次数超出
    void onCountOut();
    //显示的时间范围外
    void DateRangeOut();


}
