package com.google.zxing.view;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/19.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.util.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.receiver.ICaptureReceiver;
import com.google.zxing.self.callback.OnScannerCompletionListener;
import com.google.zxing.self.core.ScannerOptions;
import com.google.zxing.self.manager.LightSensorManager;
import com.google.zxing.self.view.ScannerView;
import com.hrz.qrcode.R;

import java.lang.ref.WeakReference;
import java.util.Observable;

import static android.content.Context.SENSOR_SERVICE;
import static com.google.zxing.self.consts.CaptureConsts.CAPTURE_ACTION;
import static com.google.zxing.self.consts.CaptureConsts.CAPTURE_RESULT;

/**
 *  block用来通信 生命周期的观察者
 */
public class CaptureBlock extends FrameLayout implements LifecycleObserver,OnScannerCompletionListener,
        View.OnClickListener,SensorEventListener,LightSensorManager.LightStateCallback {

    public static final String TAG=CaptureBlock.class.getSimpleName();
    private CaptureController captureController;

    private boolean isTourch = false;

    private int maxCodeLen;
    private Context mContext;

    private ScannerView mScannerView;
    private ImageView tv_back;
    private TextView tv_album;
    private TextView tv_light;
    private TextView tv_mycode;
    private LinearLayout ll_click_light;
    private ImageView iv_light;
    private WeakReference<ICaptureReceiver> receiver;
    private SensorManager sensorManager;



    public CaptureBlock(Context context,int maxCodeLength,ICaptureReceiver iCaptureReceiver) {
        super(context);
        this.mContext=context;
        this.maxCodeLen=maxCodeLength;
        this.receiver=new WeakReference<ICaptureReceiver>(iCaptureReceiver);
        init();
    }


    public int getMaxCodeLen() {
        return maxCodeLen;
    }

    public Context getmContext() {
        return mContext;
    }



    private void init() {

        captureController=new CaptureController(mContext,this,null);

        ((LifecycleRegistryOwner)getmContext()).getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.activity_myqrcode,null);
        addView(view,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initEventPoster();
        initSensor();
        initPreView();
        initScanOption();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        mScannerView.onResume();
        LightSensorManager.getInstance(this).start(mContext);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver.get());
        receiver.clear();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        mScannerView.onResume();
        LightSensorManager.getInstance(this).start(mContext);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        sensorManager.unregisterListener(this);
        mScannerView.onPause();
        LightSensorManager.getInstance(this).stop();
    }


    private void initEventPoster() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CAPTURE_ACTION);
        //注册本地接收器
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver.get(),intentFilter);
    }

    private void initSensor(){
        sensorManager= (SensorManager) mContext.getApplicationContext().getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this,sensor,Sensor.TYPE_GRAVITY);
    }

    private void initPreView() {
        mScannerView = findViewById(R.id.scanner_view);
        tv_back = findViewById(R.id.tv_scan_back);
        tv_album = findViewById(R.id.tv_scan_alblum);
        tv_light = findViewById(R.id.touch_light_view);
        iv_light = findViewById(R.id.iv_light);
        tv_mycode = findViewById(R.id.zxing_myqrcode);
        ll_click_light = findViewById(R.id.ll_click_light);
        mScannerView.setOnScannerCompletionListener(this);
        tv_back.setOnClickListener(this);
        tv_album.setOnClickListener(this);
        tv_mycode.setOnClickListener(this);
        ll_click_light.setOnClickListener(this);
    }


    private void initScanOption() {
        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder.setTipTextColor(R.color.transparent);
        builder.setTipTextSize(0);
        builder.setFrameCornerColor(getResources().getColor(R.color.main_color));
        builder.setLaserLineColor(getResources().getColor(R.color.main_color));
        mScannerView.setScannerOptions(builder.build());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_scan_back) {
            ((Activity)(mContext)).finish();
        } else if (id == R.id.tv_scan_alblum) {
//            gallery();
        } else if (id == R.id.ll_click_light) {
            controllFlash();
        } else if (id == R.id.zxing_myqrcode) {

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        Log.w(TAG,"扫码结果:"+rawResult.getText());
        sendLocalBrodCast(rawResult.getText());
    }


    /**
     *   发送本地广播
     * @param result
     */
    private void sendLocalBrodCast(String result){
        Intent intent = new Intent(CAPTURE_ACTION);
        intent.putExtra(CAPTURE_RESULT,result);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }


    @Override
    public void lightWeak() {
        ll_click_light.setVisibility(View.VISIBLE);
    }

    @Override
    public void lightStrong() {
        ll_click_light.setVisibility(View.GONE);
    }

    private void controllFlash(){
        if (isTourch) {
            mScannerView.toggleLight(false);
            iv_light.setSelected(false);
            tv_light.setText("轻触照亮");
            isTourch = false;
        } else {
            iv_light.setSelected(true);
            tv_light.setText("轻触熄灭");
            mScannerView.toggleLight(true);
            isTourch = true;
        }
    }


    public  interface EditTypeFactory{
        Observable getCaptureCodes();
        public void setCaptureView(Observable observable);
    }


    public static class Builder{

        private Context mContext;
        private int mMaxCodeLength;
        private ICaptureReceiver iCaptureReceiver;

        public Builder(Context context){
            this.mContext=context;
        }

        public Builder setMaxLength(int length){
            this.mMaxCodeLength=length;
            return this;
        }

        public Builder setCaptureReceiver(ICaptureReceiver receiver){
            this.iCaptureReceiver=receiver;
            return this;
        }


        public CaptureBlock build(){
            return new CaptureBlock(mContext,mMaxCodeLength,iCaptureReceiver);
        }

    }





    
    
}
