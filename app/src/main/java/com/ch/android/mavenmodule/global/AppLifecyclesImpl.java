
package com.ch.android.mavenmodule.global;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.android.common.base.delegate.AppLifecycles;
import com.ch.android.common.util.ArmsUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;



/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法  初始化
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {
    // debug 模式的数据接收地址 （测试，测试项目）
    final static String SA_SERVER_URL_DEBUG = "http://120.79.200.180:8106/sa?project=default";
    // release 模式的数据接收地址（发版，正式项目）
    final static String SA_SERVER_URL_RELEASE = "http://120.79.200.180:8106/sa?project=production";
    // SDK Debug 模式选项
    // SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
    // SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
    // SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
    // TODO 注意！请不要在正式发布的 App 中使用 DEBUG_AND_TRACK /DEBUG_ONLY 模式！ 请使用 DEBUG_OFF 模式！！！

    // debug 时，初始化 SDK 使用测试项目数据接收 URL 、使用 DEBUG_AND_TRACK 模式；release 时，初始化 SDK 使用正式项目数据接收 URL 、使用 DEBUG_OFF 模式。
    private boolean isDebugMode = false;

    @Override
    public void attachBaseContext(@NonNull Context base) {
          MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        Log.e("xxx","oncreate-applifecycle");
        //打点初始化
        initSensorsDataSDK(application);
//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this prcess.
//            return;
//        }
//        JdSdkRouter.init(application,"");
//        AlibabaSDK.initSDK(application);
        if (true) {
            //日志Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());//将测试日志加入到日志树中
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
            Logger.addLogAdapter(new AndroidLogAdapter());
//            ButterKnife.setDebug(true);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        //路由初始化
        ARouter.init(application);
        //webkit初始化
//        if (!SonicEngine.isGetInstanceAllowed()) {
//            SonicEngine.createInstance(new SonicRuntimeImpl(application), new SonicConfig.Builder().build());
//        }
        //LeakCanary 内存泄露检查
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
     //   ArmsUtils.obtainAppComponentFromContext(application).extras().put(IntelligentCache.KEY_KEEP + RefWatcher.class.getName(), USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        //扩展 AppManager 的远程遥控功能
        ArmsUtils.obtainAppComponentFromContext(application).appManager().setHandleListener((appManager, message) -> {
            switch (message.what) {
                //case 0:
                //do something ...
                //   break;
            }
        });
    }
    /**
     * 初始化 SDK 、设置公共属性、开启自动采集
     */
    private void initSensorsDataSDK(Context context) {
        try {
            // 初始化 SDK
            SensorsDataAPI.sharedInstance(
                    context,                                                                                  // 传入 Context
                    isDebugMode ? SA_SERVER_URL_DEBUG : SA_SERVER_URL_RELEASE,       // 数据接收的 URL
                    isDebugMode ? SensorsDataAPI.DebugMode.DEBUG_AND_TRACK : SensorsDataAPI.DebugMode.DEBUG_OFF); // Debug 模式选项

            // 初始化SDK后，获取应用名称设置为公共属性
            JSONObject properties = new JSONObject();
            properties.put("app_name", getAppName(context));
            SensorsDataAPI.sharedInstance().registerSuperProperties(properties);

            // 打开自动采集, 并指定追踪哪些 AutoTrack 事件
            List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
            // $AppStart
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
            // $AppEnd
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
            // $AppViewScreen
            //eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
            // $AppClick
            //eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
            SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    /**
     * @param context App 的 Context
     *                获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
