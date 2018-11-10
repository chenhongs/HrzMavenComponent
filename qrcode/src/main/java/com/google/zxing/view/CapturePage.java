package com.google.zxing.view;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/19.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.zxing.interf.OnTestClickListener;
import com.google.zxing.receiver.ICaptureReceiver;
import com.hrz.qrcode.R;


/**
 * 就是个容器
 */
public class CapturePage extends LinearLayout{


    private CaptureBlock captureView;
    private OnTestClickListener clickListener;

    public OnTestClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnTestClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CapturePage(Context context, ICaptureReceiver receiver) {
        super(context);
        captureView=new CaptureBlock.Builder(context).setMaxLength(100).setCaptureReceiver(receiver).build();
        captureView.findViewById(R.id.zxing_status_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.test();
                }
            }
        });
        addView(captureView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }


}
