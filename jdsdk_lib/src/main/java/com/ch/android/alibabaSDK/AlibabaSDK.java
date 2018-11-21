package com.ch.android.alibabaSDK;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: panrongfu
 * @date: 2018/8/27 20:02
 * @describe:
 */

public class AlibabaSDK {

    /**
     * 百川初始化入口
     */
    public static void initSDK(Application application) {
        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                Log.d(getClass().getSimpleName(),"初始化成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Log.d(getClass().getSimpleName(),"初始化失败,错误码="+code+" / 错误消息="+msg);
            }
        });
    }

    /**
     * 回收资源
     */
    public static void destroy() {
        AlibcTradeSDK.destory();
    }

    /**
     * 打开某个商品详情页面
     * @param context
     * @param itemId
     */
    public static void openProductDetailsPage(Activity context, String itemId) {
        //商品详情page
        AlibcBasePage detailPage = new AlibcDetailPage(itemId);

        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams();
        alibcTaokeParams.setPid(AlibabaConstant.TAOKE_PID);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(context, detailPage, showParams, alibcTaokeParams, exParams ,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
    }

    /**
     * 打开店铺详情页面
     */
    public static void openShopDetailsPage(Activity context, String shopId) {
        //实例化店铺打开page
        AlibcBasePage shopPage = new AlibcShopPage(shopId);

        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams();
        alibcTaokeParams.setPid(AlibabaConstant.TAOKE_PID);

        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(context, shopPage, showParams, alibcTaokeParams, exParams ,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });

    }


    /*
     * 打开web页面
     * @param context
     * @param url
     */
    public static void openWebUrl(Activity context, String url) {

        Log.d(AlibabaSDK.class.getSimpleName(),url+":跳转淘宝");

        AlibcBasePage page = new AlibcPage(url);

        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams();
        alibcTaokeParams.setPid(AlibabaConstant.TAOKE_PID);

        //使用百川sdk提供默认的Activity打开detail
        Log.d(AlibabaSDK.class.getSimpleName(),"page:"+page+"showParams:"+showParams+"alibcTaokeParams:"+alibcTaokeParams+"exParams:"+exParams);

        AlibcTrade.show(context, page, showParams, alibcTaokeParams, exParams ,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                        Log.d(getClass().getSimpleName(),tradeResult.resultType.name());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                        Log.d(getClass().getSimpleName(),"错误码："+code+"错误信息："+msg);
                    }
                });
    }
}
