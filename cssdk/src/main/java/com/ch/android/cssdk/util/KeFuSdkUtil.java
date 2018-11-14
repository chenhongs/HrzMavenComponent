package com.ch.android.cssdk.util;

import android.app.Application;

import com.ch.android.cssdk.UIProvider;
import com.hyphenate.chat.ChatClient;



/**
 * @author: panrongfu
 * @date: 2018/7/25 9:50
 * @describe:
 */

public class KeFuSdkUtil {

    /**
     * 初始化环信客服SDK
     * @param application
     */
    public static void initKefuSDK(Application application){


        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("xinrenchuangyi#liangchenbufu");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("27539");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(application, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(application);
        //后面可以设置其他属性
//        UIProvider.getInstance().setUserProfileProvider((context, message, userAvatarView, userNickView) -> {
//
//            userAvatarView.setBackgroundResource(R.drawable.iv_user_default_icon);
//            userNickView.setText("蜡笔小新");
//        });
    }
}


