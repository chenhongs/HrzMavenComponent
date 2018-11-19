package com.ch.android.poplayer.pop;

import android.content.Context;
import android.os.Message;
import android.util.Log;


import com.ch.android.poplayer.PopLayerView;
import com.ch.android.poplayer.util.SPUtils;

import java.util.PriorityQueue;
import java.util.Queue;


public class PopManager {

    private final String TAG=getClass().getSimpleName();

    //每 添加完一个元素都会进行堆排序 调整
    private static PriorityQueue<Popi> queue=new PriorityQueue();

    private static PopManager mInstance;

    private Popi mPopi;

    private  android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPopi.getContent().dismiss();
        }
    };



    private PopManager(){

    }

    public static PopManager getInstance(){
        if(mInstance==null){
            synchronized (PopManager.class){
                if(mInstance==null){
                    mInstance=new PopManager();
                }
            }
        }
        return mInstance;
    }


    public boolean canShow(){
        return queue.size()>0;
    }


    public void pushToQueue(Popi popi){
        PopLayerView hrzLayerView=popi.getContent();
        hrzLayerView.setListener(new PopDismissListener() {
            @Override
            public void onPopDimiss() {
                Log.e(TAG,"onPopDimiss");
                removeTopPopi();
                showNextPopi();
            }
        });

        if(!isAlreadyInQueue(popi,queue)){
            queue.add(popi);
        }

        Log.e(TAG,"QueueSize:"+queue.size());

    }

    private boolean isAlreadyInQueue(Popi popi,Queue<Popi> queue){
        for(Popi item:queue){
            if(item.getPopId()==popi.getPopId()){
                return true;
            }
        }
        return false;
    }

    /**
     * 只有当前队列中 弹窗为1 才能进行
     */
    public void  showNextPopi(){
        if(!canShow()){
            Log.e(TAG,"队列为空");
            return;
        }

        mPopi=queue.element();
        if(mPopi==null){
            Log.e(TAG,"队列为空");
        }else {

            //不在限定时间
            if(isPopDateOut(mPopi)){
                queue.remove(mPopi);//移出队中
                return;
            }


            String key="PopiItem"+mPopi.getPopId();
            Log.e(TAG,key);
            Context context=mPopi.getContent().getContext().getApplicationContext();
            //显示
            if(mPopi.getMaxShowCount()>0){
                Log.e(TAG,"sp:"+SPUtils.getInstance(context).getInt(key));
                if(SPUtils.getInstance(context).getInt(key)>=mPopi.getMaxShowCount()){
                    return;
                }
            }

            mPopi.getContent().show();
            if(mPopi.getCancelType()==1){
                Log.e(TAG,"延迟取消");
                delayDimiss(mPopi.getMaxShowTimeLength(),mPopi);
            }

            //记录 当前弹窗显示的次数
            if(mPopi.getMaxShowCount()>0){
                int existTime=SPUtils.getInstance(context).getInt(key)+1;
                Log.e(TAG,"已经显示了"+existTime+"次");
                SPUtils.getInstance(context).put(key,existTime);
            }

        }
    }


    public void clear(){
        queue.clear();
        if(mPopi!=null){
            mPopi.getContent().dismiss();
        }
    }

    public void removeTopPopi(){
        queue.poll();
    }


    private void delayDimiss(long second, final Popi popi){
         final long delayTime=second*1000;
         new Thread(new Runnable() {
             @Override
             public void run() {
                 long startTime=System.currentTimeMillis();
                 while ((System.currentTimeMillis()-startTime)<delayTime){

                 }
                 handler.sendEmptyMessage(0);
             }
         }).start();
    }


    private boolean isPopDateOut(Popi popi){
        if(System.currentTimeMillis()>popi.getBeginDate()&&System.currentTimeMillis()<popi.getEndDate()){
            return false;
        }
        return true;
    }







}
