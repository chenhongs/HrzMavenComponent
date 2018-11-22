package com.ch.android.mavenmodule;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/11/17.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.content.Context;
import android.util.Log;

import com.hrz.push.HrzPushIntentService;
import com.igexin.sdk.message.GTTransmitMessage;

import java.io.UnsupportedEncodingException;

public class DemoIntentService extends HrzPushIntentService {

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        try{
             String s=new String(gtTransmitMessage.getPayload(),"utf-8");
             Log.e("xxx","收到消息:"+s);
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
         }
    }
}