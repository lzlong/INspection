package com.li.inspection.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;

import com.li.inspection.constant.Constants;
import com.li.inspection.util.Utils;

import java.io.File;

/**
 * Created by long on 17-1-14.
 */

public class MyApplication extends Application {

    private static String path = "/inspection/image/cache/";
    private static String crash = "/inspection/crash/";
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        //崩溃记录
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        //图片缓存地址
        File file = new File(Environment.getExternalStorageDirectory().toString() + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //崩溃文件地址
        File crashFile = new File(Environment.getExternalStorageDirectory().toString() + crash);
        if (!crashFile.exists()) {
            crashFile.mkdirs();
        }

        preferences = getSharedPreferences("Data", 0);
        if (Utils.isNotBlank(preferences.getString("httpurl", ""))) {
            Constants.HTTP_PATH = preferences.getString("httpurl", "");
        }
        if (Utils.isNotBlank(preferences.getString("uploadUrl", ""))) {
            Constants.UPLOADSERVER = preferences.getString("uploadUrl", "");
        }
//        if (Utils.isNotBlank(preferences.getString("uploadProt", ""))) {
        Constants.UPLOADSERVERPORT = preferences.getInt("uploadProt", 4009);
//        }
        if (Utils.isNotBlank(preferences.getString("bitmapdata", ""))) {
            Constants.BITMAPDATA = preferences.getString("bitmapdata", "");
        }
    }


}

