package com.ch.android.poplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.ch.android.poplayer.R;
import com.ch.android.poplayer.pop.PopDismissListener;

import static com.ch.android.poplayer.PopLayerView.STATE_DIALOG;

/**
 * Created by mac on 2018/11/19.
 */

public class PopTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poplayer_test_layout);



    }


    /**
     * 独立的dialog文件
     * @param view
     */
    public void invidualDialog(View view){
        final PopLayerView hrzLayerView = new PopLayerView(this, STATE_DIALOG);
        View views= LayoutInflater.from(this).inflate(R.layout.pop_announce_layout,null);
        hrzLayerView.setDialogView(views);
        hrzLayerView.setDialogOutsideCancel(false);
        hrzLayerView.initLayerView(null);
        hrzLayerView.show();
    }



}
