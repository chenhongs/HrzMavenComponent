package com.ch.android.cssdk.mvp.contract;


import com.ch.android.common.mvp.iterface.IModel;
import com.ch.android.common.mvp.iterface.IView;
import com.ch.android.cssdk.bean.KeFuLoginBean;

import java.util.HashMap;

import io.reactivex.Observable;

public interface CSContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getCsLoginInfoSuccess(KeFuLoginBean keFuLoginBean);
        void getCsLoginInfoFail(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<KeFuLoginBean> getCsLoginInfo(HashMap<String,String> map);
    }
}
