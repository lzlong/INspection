package com.li.inspection.application;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.li.inspection.constant.Constants;

import java.io.File;

/**
 * Created by long on 17-1-14.
 */

@SuppressWarnings("ResourceType")
public class MyApplication extends Application {

    private LocationManager locationManager;
    private static String path = "/inspection/image/cache/";
    private static String crash = "/inspection/crash/";
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
        //gps定位
        if (isOpen(this)){
//            openGPS(this);
            getGPSConfi();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();
    }

    /**
     * 判断手机GPS是否开启
     * @return
     */
    public boolean isOpen(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位,定位级别到街
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或者移动网络确定位置
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }
        return false;
    }
    /**
     * 开启手机GPS
     */
    public void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvide");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));

        try {
            //使用PendingIntent发送广播告诉手机去开启GPS功能
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * GPS功能已经打开-->根据GPS去获取经纬度
     */
    public void getGPSConfi() {
        Location location;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Constants.Gps = latitude + "," + longitude;
//            Log.d(Constants.TAG, "经纬度:" + latitude + "--" + longitude);
        } else {
//            getGPSConfi();
            Log.d(Constants.TAG, "未获取到经纬度数据");
        }
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Constants.Gps = latitude + "," + longitude;
//                Log.d(Constants.TAG, "经纬度:" + latitude + "--" + longitude);
            } else {
//                getGPSConfi();
                Log.d(Constants.TAG, "未获取到经纬度数据");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}

