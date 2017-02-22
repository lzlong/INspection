package com.li.inspection.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;
import com.li.inspection.entity.RequestDTO;
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.PullUtils;
import com.li.inspection.util.Utils;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by long on 17-2-22.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }
    private EditText register_user, register_pass, register_pass_two;
    private Button register_btn;
    private ImageView back_none;
    private TextView name_none;
    private void initView() {
        back_none = (ImageView) findViewById(R.id.back_none);
        back_none.setOnClickListener(this);
        name_none = (TextView) findViewById(R.id.name_none);
        name_none.setText("注册");
        register_user = (EditText) findViewById(R.id.register_user);
        register_pass = (EditText) findViewById(R.id.register_pass);
        register_pass_two = (EditText) findViewById(R.id.register_pass_two);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == register_btn){
            register();
        } else if (v == back_none){
            finish();
        }
    }

    private void register() {
        String user = register_user.getText().toString();
        String pass = register_pass.getText().toString();
        String pass_t = register_pass_two.getText().toString();
        if (Utils.isBlank(user)){
            Utils.showToast(RegisterActivity.this, "请输入用户名");
            return;
        }
        if (Utils.isBlank(pass)){
            Utils.showToast(RegisterActivity.this, "请输入密码");
            return;
        }
        if (Utils.isBlank(pass_t)){
            Utils.showToast(RegisterActivity.this, "请再次输入密码");
            return;
        }
        if (!pass.equals(pass_t)){
            Utils.showToast(RegisterActivity.this, "两次输入密码不同");
        }
        Utils.showToast(RegisterActivity.this, "正在注册");
        Map<String, Object> json = new HashMap<String,Object>();
        json.put("phone", user);
        json.put("password",pass);
        json.put("userType", "2");
        final RequestDTO dto = new RequestDTO();
        dto.setXtlb("02");
        dto.setJkxlh("456789");
        dto.setJkid("01");
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
                if (jsonObject == null){
                    Utils.showToast(RegisterActivity.this, "注册失败");
                } else if (jsonObject.optInt("code") != 0){
                    Utils.showToast(RegisterActivity.this, jsonObject.optString("desc"));
                } else {
                    Utils.showToast(RegisterActivity.this, "注册成功");
                    finish();
                }
            }
        }
    };
}
