package com.li.inspection.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by long on 17-1-10.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_a);
        initView();
    }
    private ImageView back_none;
    private TextView name_none;
    private LinearLayout layout_a, layout_b, layout_c;
    private TextView vehicle_input_tva, vehicle_input_tvb, vehicle_input_tvc;
    private ImageView vehicle_input_imga, vehicle_input_imgb, vehicle_input_imgc;
    private SharedPreferences preferences;
    private void initView() {
        back_none = (ImageView) findViewById(R.id.back_none);
        name_none = (TextView) findViewById(R.id.name_none);
        name_none.setText("设置");
        layout_a = (LinearLayout) findViewById(R.id.layout_a);
        vehicle_input_tva = (TextView) findViewById(R.id.vehicle_input_tva);
        vehicle_input_tva.setText(Constants.HTTP_PATH);
        vehicle_input_imga = (ImageView) findViewById(R.id.vehicle_input_imga);
        layout_b = (LinearLayout) findViewById(R.id.layout_b);
        vehicle_input_tvb = (TextView) findViewById(R.id.vehicle_input_tvb);
        vehicle_input_tvb.setText(Constants.UPLOADSERVERPORT+"");
        vehicle_input_imgb = (ImageView) findViewById(R.id.vehicle_input_imgb);
        layout_c = (LinearLayout) findViewById(R.id.layout_c);
        vehicle_input_tvc = (TextView) findViewById(R.id.vehicle_input_tvc);
        vehicle_input_tvc.setText(Constants.BITMAPDATA);
        vehicle_input_imgc = (ImageView) findViewById(R.id.vehicle_input_imgc);



        back_none.setOnClickListener(this);
        layout_a.setOnClickListener(this);
        layout_b.setOnClickListener(this);
        layout_c.setOnClickListener(this);

        preferences = getSharedPreferences("Data", 0);
//        if (Utils.isNotBlank(preferences.getString("httpurl", ""))){
//            vehicle_input_tva.setText(preferences.getString("httpurl", ""));
//        }
//        if (Utils.isNotBlank(preferences.getString("uploadProt", ""))){
//            vehicle_input_tvb.setText(preferences.getString("uploadProt", ""));
//        }

    }
    private String httpUrl;
    private String upload;
    @Override
    public void onClick(View v) {
        if (v == back_none){
            finish();
        } else if (v == layout_a) {
            tag = 1;
            showPop("服务器地址设置", Constants.HTTP_PATH);
        } else if (v == layout_b) {
            tag = 2;
            showPop("文件上传端口设置", Constants.UPLOADSERVERPORT+"");
        } else if (v == layout_c) {
            tag = 3;
            showPop("图片水印文字设置", Constants.BITMAPDATA);
        }
    }
    WPopupWindow popupWindow;
    public void showPop(String name, String data){
        View wh= LayoutInflater.from(this).inflate(R.layout.setting,null);
        ((TextView)wh.findViewById(R.id.name_none)).setText(name);
        final EditText setting_edit = (EditText) wh.findViewById(R.id.setting_edit);
        setting_edit.setText(data);
        popupWindow=new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView(SettingActivity.this), Gravity.CENTER, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(setting_edit);
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        wh.findViewById(R.id.back_none).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    private int tag = -1;
    private void checkData(EditText setting_edit) {
        switch (tag){
            case -1:
                break;
            case 1:
                httpUrl = setting_edit.getText().toString();
                if (Utils.isBlank(httpUrl)){
                    Utils.showToast(SettingActivity.this, "请输入浏览器地址");
                    return;
                }
                if (Utils.isNotBlank(httpUrl)){
                    if (!httpUrl.startsWith("http://")){
                        httpUrl += "http://";
                    }
                    Utils.showToast(SettingActivity.this, "正在设置");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpHelper httpHelper = new HttpHelper();
                            httpHelper.connect();
                            HttpResponse response = httpHelper.doGet(httpUrl + Constants.WEBSERVCIE_PATH);
                            handler.sendMessage(handler.obtainMessage(0, response));
                        }
                    }).start();
                }
                break;
            case 2:
                upload = setting_edit.getText().toString();
                if (Utils.isNotBlank(upload)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Socket socket = null;
                            int prot = Integer.parseInt(upload);
                            try {
                                socket = new Socket(Constants.UPLOADSERVER, prot);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (socket != null){
                                Message msg = handler.obtainMessage();
                                msg.what = 1;
                                msg.arg1 = prot;
                                handler.sendMessage(msg);
                            } else {
                                handler.sendMessage(handler.obtainMessage(-1));
                            }
                        }
                    }).start();
                } else {
                    Utils.showToast(SettingActivity.this, "请输入文件上传端口号");
                }
                break;
            case 3:
                if (Utils.isNotBlank(setting_edit.getText())){
                    Constants.BITMAPDATA = setting_edit.getText().toString();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("bitmapdata", Constants.BITMAPDATA);
                    editor.commit();
                    vehicle_input_tvc.setText(Constants.BITMAPDATA);
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }
                } else {
                    Utils.showToast(SettingActivity.this, "请输入需要显示的文字");
                }
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                HttpResponse response = (HttpResponse) msg.obj;
                if (response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200){
                    Constants.HTTP_PATH = httpUrl;
                    String uploadUrl = httpUrl.substring(7, httpUrl.length());
                    if (uploadUrl.contains(":")){
                        uploadUrl = uploadUrl.substring(0, uploadUrl.lastIndexOf(":"));
                    }
                    Constants.UPLOADSERVER = uploadUrl;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("httpurl", Constants.HTTP_PATH);
                    editor.putString("uploadUrl", uploadUrl);
                    editor.commit();
                    Utils.showToast(SettingActivity.this, "设置服务器地址成功");
                    vehicle_input_tva.setText(Constants.HTTP_PATH);
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }
                } else {
                    Utils.showToast(SettingActivity.this, "地址错误，请重新填写");
                }
            } else if (msg.what == 1){
                int prot = msg.arg1;
                Constants.UPLOADSERVERPORT = prot;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("uploadProt", prot);
                editor.commit();
                vehicle_input_tvb.setText(Constants.UPLOADSERVERPORT+"");
                Utils.showToast(SettingActivity.this, "设置端口号成功");
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            } else if (msg.what == -1){
                Utils.showToast(SettingActivity.this, "设置端口号失败");
            }
        }
    };
}
