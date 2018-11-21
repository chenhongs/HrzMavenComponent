package com.ch.android.alibabaSDK;

import android.util.Log;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;


public class HrzTradeCallback implements AlibcTradeCallback {

    @Override
    public void onTradeSuccess(AlibcTradeResult tradeResult) {
        //当addCartPage加购成功和其他page支付成功的时候会回调

        if(tradeResult.resultType.equals(AlibcResultType.TYPECART)){
            //加购成功
            Log.d(getClass().getSimpleName(),"加购成功");
        }else if (tradeResult.resultType.equals(AlibcResultType.TYPEPAY)){
            //支付成功
            Log.d(getClass().getSimpleName(),"支付成功,成功订单号为"+tradeResult.payResult.paySuccessOrders);
        }
    }

    @Override
    public void onFailure(int errCode, String errMsg) {
        Log.d(getClass().getSimpleName(),"电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg);
    }
}
