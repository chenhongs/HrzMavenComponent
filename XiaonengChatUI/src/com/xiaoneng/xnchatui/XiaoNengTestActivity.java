package com.xiaoneng.xnchatui;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import cn.xiaoneng.api.Ntalker;
import cn.xiaoneng.api.inf.outer.OnBackBtnClickListener;
import cn.xiaoneng.api.inf.outer.OnCloseBtnClickListener;
import cn.xiaoneng.api.inf.outer.OnReceiveNMsgListener;
import cn.xiaoneng.api.inf.outer.OnUnreadMsgListener;
import cn.xiaoneng.conversation.restful.templateid.TemplateIdInfo;
import cn.xiaoneng.manager.bean.ChatParamsBody;
import cn.xiaoneng.manager.inf.outer.NtalkerCoreCallback;
import cn.xiaoneng.manager.inf.outer.NtalkerGetTemplateIdsCallbak;
import cn.xiaoneng.utils.common.ToastUtils;

/**
 * @author: panrongfu
 * @date: 2018/11/21 14:45
 * @describe:
 */

public class XiaoNengTestActivity extends Activity implements
        OnUnreadMsgListener, NtalkerGetTemplateIdsCallbak, OnReceiveNMsgListener{

    public static String siteid = "kf_20083";
    public static String settingid1 = "kf_20083_template_3";
    public static String settingid2 = "kf_20083_template_3";
   // Ringtone ringtonenotification;

    public static final String SETTINGID1_KEY = "kf_20083_template_3";
    public static final String SETTINGID2_KEY = "kf_20083_template_3";
    public static final String SITE_ID_KEY = "kf_20083";
    public static final int RESULT_CODE_SETTING = 101;
    public static final int REQUEST_CODE_SETTING = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_neng_test);
        Ntalker.getInstance().enableDebug(true);//是否开启debug模式
        Ntalker.getInstance().initSDK(this, siteid,"https://init.ntalker.com");
        setNtalkerListener();
       // setNotify();
    }

    private void setNtalkerListener() {
        Ntalker.getInstance().enableDebug(true);
        Ntalker.getInstance().getTemplateIdInfo(this);
        Ntalker.getInstance().setOnReceiveNMsgListener(this);
        //返回键的扩展功能接口
        Ntalker.getInstance().setOnBackBtnListener(new OnBackBtnClickListener() {

            @Override
            public void OnBackBtnClick() {

            }
        });

        Ntalker.getInstance().setOnCloseBtnListener(new OnCloseBtnClickListener() {
            @Override
            public void OnCloseBtnClick() {
            }
        });
        mHistoryTime = System.currentTimeMillis();
    }

//    private void setNotify() {
//        Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.msgnotifyvoice);
//        ringtonenotification = RingtoneManager.getRingtone(this, notification);
//    }

    public void clickKeFu(View view){
        Ntalker.getInstance().login("123456", "xiaoneng", new NtalkerCoreCallback() {

            @Override
            public void successed() {
                Log.e(">>>>>>>>>>","login success ");
                ChatParamsBody chatparams = new ChatParamsBody();
                chatparams.goodsId = "ntalker_test";
                chatparams.settingId = settingid1;
                Ntalker.getInstance().startChat(XiaoNengTestActivity.this, chatparams);

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        et_userid.setEnabled(false);
//                        ToastUtils.getInstance().showToast(getApplicationContext(), "登录成功" );
//                        ChatParamsBody chatparams = new ChatParamsBody();
//                        chatparams.goodsId = "ntalker_test";
//                        chatparams.settingId = settingid1;
//                        Ntalker.getInstance().startChat(XiaoNengTestActivity.this, chatparams);
//                    }
//                });
            }

            @Override
            public void failed(final int errorcode) {
                Log.e(">>>>>>>>>>","login failed"+ errorcode);
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        ToastUtils.getInstance().showToast(getApplicationContext(), "登录失败" + errorcode);
//                    }
//                });

            }
        });
    }

    @Override
    public void onGetTemplateIdInfos(final List<TemplateIdInfo> templateidinfo) {
    }

    @Override
    public void onUnReadMsg(final String settingid, final String kefuname, final String msgcontent, long time, final int messagecount) {

    }

    @Override
    protected void onDestroy() {
        Ntalker.getInstance().getTemplateIdInfo(null);
        Ntalker.getInstance().removeBackBtnClickListener();
        Ntalker.getInstance().removeCloseBtnClickListener();
        Ntalker.getInstance().removeCustomGoodsUICallback();
        super.onDestroy();
    }

    @Override
    public void onReceiveNMsg(String msg) {
        try {
            Log.e(">>>>>>","onReceiveNMsg"+msg);
            JSONObject j1 = new JSONObject(msg);
            long time = j1.optLong("msgTime");
            if (time > mHistoryTime) {
                String s1 = j1.optString("msgContent");
                JSONObject j2 = new JSONObject(s1);
                String s2 = j2.optString("message");
                if(s2.contains("blocks")){
                    JSONObject j3 = new JSONObject(s2);
                    JSONArray j4 = j3.optJSONArray("blocks");
                    String s = j4.getJSONObject(0).optString("text");
                }
//                TtsProxy.kernal().speak(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long mHistoryTime;
}
