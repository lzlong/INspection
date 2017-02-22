package com.li.inspection.activity;

import android.content.SharedPreferences;
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
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.Utils;

import org.apache.http.HttpResponse;

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
        setting_edit.setText("http://");
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        back_none.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        preferences = getSharedPreferences("Data", 0);
        if (Utils.isNotBlank(preferences.getString("httpurl", ""))){
            setting_edit.setText(preferences.getString("httpurl", ""));
        }

    }
    private String httpUrl;
    @Override
    public void onClick(View v) {
        if (v == back_none){
            finish();
        } else if (v == confirm){
            httpUrl = setting_edit.getText().toString();
            if (Utils.isBlank(httpUrl)){
                Utils.showToast(SettingActivity.this, "请输入浏览器地址");
                return;
            }
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
        } else if (v == cancel){
            finish();
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
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("httpurl", httpUrl);
                    editor.putString("uploadUrl", httpUrl.substring(7, httpUrl.length()));
                    editor.commit();
                    Utils.showToast(SettingActivity.this, "设置成功");
                    finish();
                } else {
                    Utils.showToast(SettingActivity.this, "地址错误，请重新填写");
                    setting_edit.setText("http://");
                }
            }
        }
    };
}
