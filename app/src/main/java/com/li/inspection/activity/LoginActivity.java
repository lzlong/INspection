package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.li.inspection.R;
import com.li.inspection.util.Utils;

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
    private void initView() {
        login_user = (EditText) findViewById(R.id.login_user);
        login_pass = (EditText) findViewById(R.id.login_pass);
        login_btn = (Button) findViewById(R.id.login_btn);
        setting_btn = (Button) findViewById(R.id.setting_btn);
        login_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (v == setting_btn){
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    }
}
