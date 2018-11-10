package com.google.zxing.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.util.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.receiver.ICaptureReceiver;
import com.google.zxing.self.callback.OnScannerCompletionListener;
import com.google.zxing.self.consts.CaptureConsts;
import com.google.zxing.self.core.ScannerOptions;
import com.google.zxing.self.decode.QRDecode;
import com.google.zxing.self.manager.LightSensorManager;
import com.google.zxing.self.util.ImageUtils;
import com.google.zxing.self.util.StatusBarUtil;
import com.google.zxing.self.view.ScannerView;
import com.hrz.qrcode.R;

import java.lang.ref.WeakReference;

import static com.google.zxing.self.consts.CaptureConsts.CAPTURE_ACTION;
import static com.google.zxing.self.consts.CaptureConsts.CAPTURE_RESULT;


/**
 *
 */
public class MyCapturesActivity extends Activity implements OnScannerCompletionListener,
        View.OnClickListener,SensorEventListener,LightSensorManager.LightStateCallback {

    public static final String TAG=MyCapturesActivity.class.getSimpleName();

    private ScannerView mScannerView;
    private ImageView tv_back;
    private TextView tv_album;
    private TextView tv_light;
    private TextView tv_mycode;
    private LinearLayout ll_click_light;
    private ImageView iv_light;

    private boolean isTourch = false;

    private SensorManager sensorManager;
    private IntentFilter intentFilter;


    private LocalBroadcastManager localBroadcastManager;

    private static WeakReference<ICaptureReceiver> receiver;

    public static void routeToCaptureActivity(Activity context,ICaptureReceiver captureReceiver){
        Intent intent=new Intent(context, MyCapturesActivity.class);
        receiver=new WeakReference<>(captureReceiver);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarTransparent(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqrcode);
        initEventPoster();
        initSensor();
        initPreView();
        initScanOption();
    }

    private void initEventPoster() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(CAPTURE_ACTION);
        //注册本地接收器
        localBroadcastManager.registerReceiver(receiver.get(),intentFilter);
    }

    private void initSensor(){
        sensorManager= (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
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
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.onResume();//这个时候就显示surfaceview并聚焦了
        LightSensorManager.getInstance(this).start(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScannerView.onResume();
        LightSensorManager.getInstance(this).start(this);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
        mScannerView.onPause();
        LightSensorManager.getInstance(this).stop();
    }


    /**
     * 扫码完成回调
     * @param result
     * @param parsedResult 抽象类，结果转换成目标类型
     * @param bitmap
     */
    @Override
    public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
        sendScanResult(result.toString());
    }


    private void sendScanResult(String result){
        Log.w(TAG,"扫码结果:"+result);
        sendLocalBrodCast(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver.get());
        receiver.clear();
    }


    /**
     *   发送本地广播
     * @param result
     */
    private void sendLocalBrodCast(String result){
        Intent intent = new Intent(CAPTURE_ACTION);
        intent.putExtra(CAPTURE_RESULT,result);
        localBroadcastManager.sendBroadcast(intent);
    }


//    private void switchQrCode(String qrCode) {
//
//
//        CaptureEvent captureEvent=new CaptureEvent();
//        //1.商家入驻二维码 扫描结果格式   http://hdyl.lexj.com/abutment?pid=5541890
//        if(qrCode.contains("abutment")){
//            Intent intent=new Intent();
//            intent.putExtra("pid",Uri.parse(qrCode).getQueryParameter("pid"));
//            intent.putExtra("type",ParsedQrCodeType.MERCHANT_IN);
//            captureEvent.setIntent(intent);
//            Log.e(TAG,"商家入驻二维码");
//            //2.激活商家二维码 https://qr.hdyl.net.cn?sid=119247550656236&ver=1.0&from=hrz
//        }else if(qrCode.contains("qr.hdyl.net.cn")){
//            Intent intent=new Intent();
//            intent.putExtra("qrcodeContent",qrCode);
//            intent.putExtra("type",ParsedQrCodeType.ACTIVE_MERCHANT_QRCODE);
//            captureEvent.setIntent(intent);
//            Log.e(TAG,"激活商家二维码");
//        }else{
//            showDialog("识别不到/二维码无效","重新识别");
//            return;
//        }
//        //EventBus.getDefault().post(captureEvent);
//        finish();
//    }


    @Override
    public void lightWeak() {
        ll_click_light.setVisibility(View.VISIBLE);
    }

    @Override
    public void lightStrong() {
        ll_click_light.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_scan_back) {
            finish();
        } else if (id == R.id.tv_scan_alblum) {
            gallery();
        } else if (id == R.id.ll_click_light) {
           controllFlash();
        } else if (id == R.id.zxing_myqrcode) {

        }
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

    /**
     *
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, CaptureConsts.PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CaptureConsts.PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径 解析图片  得到的是图片地址
                Uri uri = data.getData();
                QRDecode.decodeQR(ImageUtils.getRealPathFromUri(MyCapturesActivity.this, uri), this);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
