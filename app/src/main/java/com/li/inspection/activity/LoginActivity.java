package com.li.inspection.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.li.inspection.R;
import com.li.inspection.entity.RequestDTO;
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.PullUtils;
import com.li.inspection.util.Utils;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by long on 17-1-12.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    private EditText login_user, login_pass;
    private Button login_btn, setting_btn;
    private SharedPreferences preferences;
    private void initView() {
        login_user = (EditText) findViewById(R.id.login_user);
        login_pass = (EditText) findViewById(R.id.login_pass);
        login_btn = (Button) findViewById(R.id.login_btn);
        setting_btn = (Button) findViewById(R.id.setting_btn);
        login_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        preferences = getSharedPreferences("userData", 0);
    }

    @Override
    public void onClick(View v) {
        if (v == login_btn){
            String user = login_user.getText().toString();
            String pass = login_pass.getText().toString();
            if (Utils.isBlank(user)){
                Utils.showToast(LoginActivity.this, "请输入用户名");
                return;
            }
            if (Utils.isBlank(pass)){
                Utils.showToast(LoginActivity.this, "请输入密码");
                return;
            }
            login(user, pass);
        } else if (v == setting_btn){
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    }

    private void login(String user, String pass) {
        Map<String, Object> json = new HashMap<String,Object>();
        json.put("phone", user);
        json.put("password",pass);
        json.put("userType", "2");
        final RequestDTO dto = new RequestDTO();
        dto.setXtlb("02");
        dto.setJkxlh("456789");
        dto.setJkid("02");
        dto.setJson(json);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = PullUtils.buildXML(dto);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.connect();
                HttpResponse response = httpHelper.doPost("http://sdkj.kmdns.net:4008/IntelligentTraffic/services/TpiWebService?wsdl", data);
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
                if (jsonObject == null || jsonObject.optInt("code") != 0){
                    Utils.showToast(LoginActivity.this, jsonObject.optString("desc"));
                } else {
                    Utils.parseUser(jsonObject, preferences);
                    Utils.showToast(LoginActivity.this, jsonObject.optString("desc"));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };
}
