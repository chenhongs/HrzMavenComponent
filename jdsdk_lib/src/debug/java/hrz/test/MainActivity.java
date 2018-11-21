package hrz.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ch.android.alibabaSDK.AlibabaSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_test_layout);


        //测试JD



        //测试唯品会





    }

    public void testTbRoute(View view){
         //测试阿里
        String goodsUrl="https://item.taobao.com/item.htm?spm=a1z10.1-c-s.w5003-18776842796.2.7dee96f95PenTf&id=574499704385&scene=taobao_shop";
//       AlibabaSDK.openWebUrl(this, goodsUrl);

       AlibabaSDK.openShopDetailsPage(this,"60552065");
    }



}
