package com.li.inspection.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.application.SysApplication;
import com.li.inspection.constant.Constants;
import com.li.inspection.entity.InspectionData;
import com.li.inspection.entity.RequestDTO;
import com.li.inspection.entity.User;
import com.li.inspection.util.AppUtils;
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.PermissionUtils;
import com.li.inspection.util.PullUtils;
import com.li.inspection.util.SmartDownloadProgressListener;
import com.li.inspection.util.SmartFileDownloader;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;
import com.li.inspection.util.WheelView;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);
        initView();
    }

    private ImageView user_img;
    private TextView user_name;
    private ImageView vehicle_input_imga, vehicle_input_imgb, vehicle_input_imgc, vehicle_input_imgd;
    private TextView vehicle_input_tva, vehicle_input_tvb, vehicle_input_tvc, vehicle_input_tvd;
    private EditText vehicle_input_tve, vehicle_input_tvf;
    private Button next_btn;

    private void initView() {
        user_img = (ImageView) findViewById(R.id.user_img);
        user_name = (TextView) findViewById(R.id.user_name);
        user_img.setOnClickListener(this);
        user_name.setOnClickListener(this);
        if (Utils.isBlank(User.getInstance().getName())) {
            Utils.getUserData(MainActivity.this);
        }
        if (Utils.isNotBlank(User.getInstance().getName())){
            user_name.setText(User.getInstance().getName());
        } else {
            user_name.setText("暂无姓名");
        }
        vehicle_input_imga = (ImageView) findViewById(R.id.vehicle_input_imga);
        vehicle_input_imgb = (ImageView) findViewById(R.id.vehicle_input_imgb);
        vehicle_input_imgc = (ImageView) findViewById(R.id.vehicle_input_imgc);
        vehicle_input_imgd = (ImageView) findViewById(R.id.vehicle_input_imgd);
        vehicle_input_tva = (TextView) findViewById(R.id.vehicle_input_tva);
        vehicle_input_tvb = (TextView) findViewById(R.id.vehicle_input_tvb);
        vehicle_input_tvc = (TextView) findViewById(R.id.vehicle_input_tvc);
        vehicle_input_tvd = (TextView) findViewById(R.id.vehicle_input_tvd);
        vehicle_input_tve = (EditText) findViewById(R.id.vehicle_input_tve);
        vehicle_input_tvf = (EditText) findViewById(R.id.vehicle_input_tvf);
        next_btn = (Button) findViewById(R.id.next_btn);
        click();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gps定位
        if (isOpen(this)) {
            getGPSConfi();
        } else {
            openGPS(this);
        }
        checkApp();
    }

    private LocationManager locationManager;

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
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    getGPSConfi();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    getGPSConfi();
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * GPS功能已经打开-->根据GPS去获取经纬度
     */
    public void getGPSConfi() {
        Location location;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
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
    String dir = Environment.getExternalStorageDirectory().toString() +
            File.separator +"inspection/down/apk" + File.separator;//文件保存目录
    String path = "";
    int versionCode;
    private void checkApp() {
        versionCode = AppUtils.getAppVersionCode(MainActivity.this);
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("versionCode", versionCode); // 当前版本号
        json.put("platForm", 1);// 平台类型：Android或IOS，1：Android，2：IOS
        json.put("clientType", "2");// 客户端类别，1：警员客户端，2：驾驶人客户端
        final RequestDTO dto = new RequestDTO();
        dto.setXtlb("02");
        dto.setJkxlh("456789");
        dto.setJkid("32");
        dto.setJson(json);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = PullUtils.buildXML(dto);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.connect();
                HttpResponse response = httpHelper.doPost(Constants.HTTP_PATH + Constants.WEBSERVCIE_PATH, data);
                JSONObject jsonObject = Utils.parseResponse(response);
                handler.sendMessage(handler.obtainMessage(0, jsonObject));
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                JSONObject jsonObject = (JSONObject) msg.obj;
                if (jsonObject != null && jsonObject.optInt("code") == 0){
                    int code = jsonObject.optInt("versionCode");
                    if (code > versionCode){
                        path = jsonObject.optString("fileUrl");
                        showDownload();
                    }
                }
            } else if (msg.what == 1){
                int size = msg.getData().getInt("size");
                pro.setProgress(size);
                float result = (float)pro.getProgress()/ (float)pro.getMax();
                int p = (int)(result*100);
                if(pro.getProgress()==pro.getMax()) {
                    Utils.showToast(MainActivity.this, "下载成功, 正在准备安装");
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    AppUtils.installApp(MainActivity.this, dir + path.substring(path.lastIndexOf('/') + 1));
                }
            } else if (msg.what == -1){
                wh.findViewById(R.id.down_l).setVisibility(View.VISIBLE);
                Utils.showToast(MainActivity.this, msg.getData().getString("error"));
            } else if (msg.what == 2){
                JSONObject jsonObject = (JSONObject) msg.obj;
                if (jsonObject != null && jsonObject.optInt("code") == 0){
                    Utils.showToast(MainActivity.this, "设置成功");
                    User.getInstance().setName(jsonObject.optString("name"));
                    user_name.setText(User.getInstance().getName());
                    namePop.dismiss();
                }

            }
        }
    };
    private ProgressBar pro;
    WPopupWindow popupWindow;
    View wh;
    private void showDownload() {
        wh = LayoutInflater.from(this).inflate(R.layout.downpop,null);
        pro = (ProgressBar) wh.findViewById(R.id.down_pro);
        popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView(MainActivity.this), Gravity.BOTTOM, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File file = new File(dir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    ((TextView)wh.findViewById(R.id.down_t)).setText("正在下载");
                    wh.findViewById(R.id.down_l).setVisibility(View.GONE);
                    download(wh, pro, path, file);
                }else{
                    Utils.showToast(MainActivity.this, "SDCard不存在或者写保护");
                }
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });
    }

    private void download(final View wh, final ProgressBar pro, final String path, final File dir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SmartFileDownloader loader = new SmartFileDownloader(MainActivity.this, path, dir, 3);
                    int length = loader.getFileSize();//获取文件的长度
                    pro.setMax(length);
                    loader.download(new SmartDownloadProgressListener() {
                        @Override
                        public void onDownloadSize(int size) {//可以实时得到文件下载的长度
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            handler.sendMessage(msg);
                        }
                    });
                } catch (Exception e) {
                    Message msg = new Message();//信息提示
                    msg.what = -1;
                    msg.getData().putString("error", "下载失败, 点击确定重新下载");//如果下载错误，显示提示失败！
                    handler.sendMessage(msg);
                }
            }
        }).start();//开始
    }
    private void click() {
        vehicle_input_imga.setOnClickListener(this);
        vehicle_input_imgb.setOnClickListener(this);
        vehicle_input_imgc.setOnClickListener(this);
        vehicle_input_imgd.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == vehicle_input_imga){
            showPop(vehicle_input_tva, Constants.USE_PROPERTY);
        } else if (v == vehicle_input_imgb){
            showPop(vehicle_input_tvb, Constants.PLATE_TYPE);
        } else if (v == vehicle_input_imgc){
            showPop(vehicle_input_tvc, Constants.SERVICE_TYPE);
        } else if (v == vehicle_input_imgd){
            showPop(vehicle_input_tvd, Constants.VEHICLE_TYPE);
        } else if (v == next_btn){
            checkData();
        } else if (v == user_img || v == user_name){
            setName();
        }
    }
    WPopupWindow namePop;
    private void setName() {
        View wh= LayoutInflater.from(this).inflate(R.layout.setting,null);
        ((TextView)wh.findViewById(R.id.name_none)).setText("姓名设置");
        final EditText setting_edit = (EditText) wh.findViewById(R.id.setting_edit);
        setting_edit.setHint("请输入姓名");
        namePop=new WPopupWindow(wh);
        namePop.showAtLocation(Utils.getContentView(MainActivity.this), Gravity.CENTER, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = setting_edit.getText().toString();
                if (Utils.isBlank(name)){
                    Utils.showToast(MainActivity.this, "请输入姓名");
                    return;
                }
                Utils.showToast(MainActivity.this, "正在设置");
                saveName(name);
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namePop.dismiss();
            }
        });
        wh.findViewById(R.id.back_none).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namePop.dismiss();
            }
        });
    }
    private void saveName(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (Utils.isBlank(User.getInstance().getId())) {
            Utils.getUserData(MainActivity.this);
        }
        params.put("id", User.getInstance().getId());
        params.put("name", name);
//        params.put("gender", sex);
//        params.put("photoUrl", photoUrl);
//        params.put("drivingLicence", license_number);
//        params.put("archivesNo", fileno);
//        params.put("drivingLicenceUrl", license_img);
        final RequestDTO dto = new RequestDTO();
        dto.setXtlb("02");
        dto.setJkxlh("456789");
        dto.setJkid("03");
        dto.setJson(params);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = PullUtils.buildXML(dto);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.connect();
                HttpResponse response = httpHelper.doPost(Constants.HTTP_PATH + Constants.WEBSERVCIE_PATH, data);
                JSONObject jsonObject = Utils.parseResponse(response);
                handler.sendMessage(handler.obtainMessage(2, jsonObject));
            }
        }).start();
    }
    private void checkData() {
        if (Utils.isBlank(User.getInstance().getName())){
            Utils.showToast(MainActivity.this, "还未设置姓名，请点击左上角设置姓名。");
            return;
        }
        String use_property = vehicle_input_tva.getText().toString();
        String plate_type = vehicle_input_tvb.getText().toString();
        String service_type = vehicle_input_tvc.getText().toString();
        String vehicle_type = vehicle_input_tvd.getText().toString();
        VIN = vehicle_input_tve.getText().toString();
        String engine = vehicle_input_tvf.getText().toString();
        if (Utils.isBlank(use_property)){
            Utils.showToast(MainActivity.this, "请选择使用性质");
            return;
        }
        if (Utils.isBlank(plate_type)){
            Utils.showToast(MainActivity.this, "请选择号牌种类");
            return;
        }
        if (Utils.isBlank(service_type)){
            Utils.showToast(MainActivity.this, "请选择业务类型");
            return;
        }
        if (Utils.isBlank(vehicle_type)){
            Utils.showToast(MainActivity.this, "请选择车辆类型");
            return;
        }
        if (Utils.isBlank(VIN)){
            Utils.showToast(MainActivity.this, "请填写车架号");
            return;
        }
//        if (Utils.isBlank(engine)){
//            Utils.showToast(MainActivity.this, "请填写发动机号");
//            return;
//        }
        InspectionData inspectionData = InspectionData.getInstance();
        inspectionData.setUse_property(use_property.substring(use_property.lastIndexOf(" ")+1, use_property.length()));
        inspectionData.setPlate_type(plate_type.substring(plate_type.lastIndexOf(" ")+1, plate_type.length()));
        inspectionData.setService_type(service_type.substring(service_type.lastIndexOf(" ")+1, service_type.length()));
        inspectionData.setVehicle_type(vehicle_type.substring(vehicle_type.lastIndexOf(" ")+1, vehicle_type.length()));
        inspectionData.setVIN(VIN);
        inspectionData.setFaDongJiHao(engine);
        Intent intent = new Intent(MainActivity.this, JudeActivity.class);
        startActivity(intent);
    }

    public void showPop(final TextView textView, String[] data){
        View wh= LayoutInflater.from(this).inflate(R.layout.common_window_wheel,null);
        final WheelView picker= (WheelView) wh.findViewById(R.id.wheel);
        for (String name : data){
            picker.addData(name);
        }
        picker.setCenterItem(0);
        final WPopupWindow popupWindow=new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView(MainActivity.this), Gravity.BOTTOM, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText((String)picker.getCenterItem());
                popupWindow.dismiss();
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}