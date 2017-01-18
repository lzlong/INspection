package com.li.inspection.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;
import com.li.inspection.util.Utils;

/**
 * Created by long on 17-1-10.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initView();
    }
    private ImageView back_none;
    private TextView name_none;
    private EditText setting_edit;
    private Button confirm, cancel;
    private SharedPreferences preferences;
    private void initView() {
        back_none = (ImageView) findViewById(R.id.back_none);
        name_none = (TextView) findViewById(R.id.name_none);
        name_none.setText("服务器地址设置");
        setting_edit = (EditText) findViewById(R.id.setting_edit);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        back_none.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        preferences = getSharedPreferences("data", 0);
        setting_edit.setText(preferences.getString("httpurl", ""));

    }

    @Override
    public void onClick(View v) {
        if (v == back_none){
            finish();
        } else if (v == confirm){
            String httpUrl = setting_edit.getText().toString();
            if (Utils.isBlank(httpUrl)){
                return;
            }
            Constants.httpUrl = httpUrl;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("httpurl", httpUrl);
            editor.commit();
            finish();
        } else if (v == cancel){
            finish();
        }
    }
}
