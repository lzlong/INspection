package com.li.inspection.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by long on 17-1-9.
 */

public class BaseActivity extends AppCompatActivity {

    public static Bitmap bitmap;
    public static int tag = -1;  // 拍照和签名的标志 -1 未拍照或签名 0 左前45度 1 右前45度 2 车辆识别代码 3 签名
    public static int bitTag = -1; //是否进行了拍照或者签名
    public static String VIN = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
