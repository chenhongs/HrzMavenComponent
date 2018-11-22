package com.ch.android.mavenmodule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.android.common.dagger.component.AppComponent;
import com.ch.android.common.dagger.scope.ActivityScope;
import com.ch.android.common.util.ArmsUtils;
import com.ch.android.common.widget.tablayout.rxtools.RxCaptcha;
import com.ch.android.cssdk.recorder.MediaManager;
import com.ch.android.poplayer.PopLayerView;

import static com.ch.android.common.widget.tablayout.rxtools.RxCaptcha.TYPE.CHARS;
import static com.ch.android.poplayer.PopLayerView.STATE_DIALOG;


/**
 * Created by mac on 2018/11/10.
 */

@ActivityScope
public class MainActivity extends com.ch.android.common.base.BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle bundle) {
        return R.layout.none_layout;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        Log.d(getClass().getSimpleName(),"initData");


    }

    private PopLayerView dialog;


    public void test(View v){
        showVertifyCodeDialog();
    }


    /**
     * 独立的dialog文件
     * @param view
     */
    public void invidualDialog(View view){
        final PopLayerView hrzLayerView = new PopLayerView(this, STATE_DIALOG);
        View views= LayoutInflater.from(this).inflate(null,null);
        hrzLayerView.setDialogView(views);
        hrzLayerView.setDialogOutsideCancel(false);
        hrzLayerView.initLayerView(null);
        hrzLayerView.show();
    }




    /**
     * 显示是否删除历史记录dialog
     * @param msg
     */
    private void showDialog(String msg){
        PopLayerView dialog = new PopLayerView(this, STATE_DIALOG);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_normal, null);
        TextView contentTv = view.findViewById(R.id.tv_message);
        TextView cancelTv = view.findViewById(R.id.tv_right_action);
        TextView confirmTv = view.findViewById(R.id.tv_left_action);
        contentTv.setText(msg);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) contentTv.getLayoutParams();
        params.gravity = Gravity.CENTER;
        contentTv.setTextColor(Color.parseColor("#B2B2B2"));
        cancelTv.setText("取消");
        confirmTv.setText("确定");
        confirmTv.setOnClickListener(v -> {
//            isClearHistory=true;
//            mPresenter.deleteAllSearchHistory();
            dialog.dismiss();
        });
        cancelTv.setOnClickListener(v ->{
            dialog.dismiss();
        });
        dialog.setDialogView(view);
        dialog.setDialogOutsideCancel(false);
        dialog.initLayerView(null);
        dialog.show();
    }





    /**
     *  弹出清除历史记录的对话框
     */
    private void showClearHistoryDialog() {
        PopLayerView dialog = new PopLayerView(this, STATE_DIALOG);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_normal, null);
        TextView contentTv = view.findViewById(R.id.tv_message);
        TextView cancelTv = view.findViewById(R.id.tv_right_action);
        TextView confirmTv = view.findViewById(R.id.tv_left_action);
        contentTv.setText(R.string.kefu_delete_dialog_content);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) contentTv.getLayoutParams();
        params.gravity = Gravity.CENTER;
        contentTv.setTextColor(Color.parseColor("#B2B2B2"));
        confirmTv.setText(R.string.kefu_delete_dialog_okay_text);
        cancelTv.setOnClickListener(v -> dialog.dismiss());
        confirmTv.setOnClickListener(v ->{
            MediaManager.release();
//            ChatClient.getInstance().chatManager().clearConversation(toChatUsername);
//            messageList.refresh();
            dialog.dismiss();
        });
        dialog.setDialogView(view);
        dialog.initLayerView(null);
        dialog.show();
    }


    private String code;
    /*
     * 显示验证码弹窗
     */
    private void showVertifyCodeDialog() {
              dialog = new PopLayerView(this, STATE_DIALOG);
            dialog.setDialogView(initVertifyCodeView());
            dialog.initLayerView(null);
            dialog.show();
    }

    private View initVertifyCodeView(){
        View view = View.inflate(this, R.layout.mine_module_vertify_dialog_view, null);
        view.findViewById(R.id.tv_cancle).setOnClickListener(v -> dialog.dismiss());
        EditText et_vertify_code=view.findViewById(R.id.et_vertify_code);
        code=getCaptureCode(view.findViewById(R.id.view_capture));
        view.findViewById(R.id.tv_change).setOnClickListener(v -> {
            code=getCaptureCode(view.findViewById(R.id.view_capture));
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(v -> {
            if(TextUtils.equals(et_vertify_code.getText().toString().toLowerCase(),code)){
                dialog.dismiss();
                et_vertify_code.setText("");
                ArmsUtils.makeText(this,"验证成功");
            }else {
                et_vertify_code.setText("");
                ArmsUtils.makeText(this,"验证失败,请重试");
            }
        });
        return view;
    }


    /**
     * 生成二维码
     * @param view
     * @return
     */
    private String getCaptureCode(ImageView view) {
        RxCaptcha.build()
                .backColor(0xB2B2B2)
                .codeLength(4)
                .fontSize(45)
                .lineNumber(0)
                .size(200, 100)
                .type(CHARS).into(view);
        return RxCaptcha.build().getCode();
    }
    
    
    


}
