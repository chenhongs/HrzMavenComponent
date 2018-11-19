package com.ch.android.poplayer.pop;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/10/27.Best Wishes to You!  []~(~▽~)~* Cheers!


import com.ch.android.poplayer.PopLayerView;

import static com.ch.android.poplayer.PopLayerView.STATE_DIALOG;
import static com.ch.android.poplayer.PopLayerView.STATE_WEBVIEW;

/**
 *  窗口的最小不可分割实体
 */
public class Popi implements Comparable<Popi>{

    private PopType mType;

    //默认不进行 次数记录
    private int maxShowCount=0;

    private int popId;//唯一标示

    //弹窗显示方式 0为点击取消（需要设置显示次数） 1为计时取消(需要设置显示时间)
    private int cancelType=0;

    private int maxShowTimeLength=5;//s

    //弹窗内容视图 可为空
    private PopLayerView content;

    //时间撮形式的开始时间和结束时间
    private long beginDate;
    private long endDate;

    //优先级 数值越小 优先级越高
    private int priority;

    //对应弹窗的点击后路径
    private String routePath;

    public Popi(Builder builder){
        this.priority=builder.mPriority;
        this.popId=builder.mPopId;
        this.content = builder.mPopLayerView;
        if(content.state==STATE_WEBVIEW){
            mType=PopType.WEBVIEW;
        }else if(content.state==STATE_DIALOG){
            mType=PopType.DIALOG;
        }
        this.cancelType=builder.mCancelType;

        if(cancelType==1){
            this.maxShowTimeLength=builder.maxShowTimeLength;
        }

        this.maxShowCount=builder.maxShowCount;
        this.routePath=builder.mRoutePath;
        this.beginDate=builder.mBeginDate;
        this.endDate=builder.mEndDate;
    }


    public int getPopId() {
        return popId;
    }

    public int getCancelType() {
        return cancelType;
    }

    public void setCancelType(int cancelType) {
        this.cancelType = cancelType;
    }

    public int getMaxShowTimeLength() {
        return maxShowTimeLength;
    }

    public void setMaxShowTimeLength(int maxShowTimeLength) {
        this.maxShowTimeLength = maxShowTimeLength;
    }

    public int getMaxShowCount() {
        return maxShowCount;
    }

    public void setMaxShowCount(int maxShowCount) {
        this.maxShowCount = maxShowCount;
    }

    public PopLayerView getContent() {
        return content;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getPriority() {
        return priority;
    }


    public static class Builder{

        private PopLayerView mPopLayerView;
        private PopType mPopType;
        private int maxShowCount;
        private int mPopId;
        private long mBeginDate;
        private long mEndDate;
        private int mPriority;
        private String mRoutePath;
        private int mCancelType;
        private int maxShowTimeLength;

        public void setMaxShowTimeLength(int maxShowTimeLength) {
            this.maxShowTimeLength = maxShowTimeLength;
        }

        public Builder setmPopLayerView(PopLayerView mPopLayerView) {
            this.mPopLayerView = mPopLayerView;
            return this;
        }

        public Builder setmPopType(PopType mPopType) {
            this.mPopType = mPopType;
            return this;
        }

        public Builder setMaxShowCount(int maxShowCount) {
            this.maxShowCount = maxShowCount;
            return this;
        }

        public Builder setmPopId(int mPopId) {
            this.mPopId = mPopId;
            return this;
        }

        public Builder setmBeginDate(long mBeginDate) {
            this.mBeginDate = mBeginDate;
            return this;
        }

        public Builder setmEndDate(long mEndDate) {
            this.mEndDate = mEndDate;
            return this;
        }

        public Builder setmPriority(int mPriority) {
            this.mPriority = mPriority;
            return this;
        }

        public Builder setmRoutePath(String mRoutePath) {
            this.mRoutePath = mRoutePath;
            return this;
        }

        public Builder setmCancelType(int mCancelType) {
            this.mCancelType = mCancelType;
            return this;
        }

        public Popi build(){
            return new Popi(this);
        }

        public void pushToQueue(){
            PopManager.getInstance().pushToQueue(build());
        }

    }


    /**
     * 自然排序即从小到大
     * 返回1的，代表此对象比参数对象大，排在后面，这样就可以控制降序或升序排列
     */
    @Override
    public int compareTo(Popi o) {
        if (this.priority > o.getPriority()) {
            return 1;
        }else if (this.priority < o.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }


}
