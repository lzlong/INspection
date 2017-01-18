package com.li.inspection.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.li.inspection.R;
import com.li.inspection.util.LinePathView;

import java.io.IOException;

/**
 * Created by long on 17-1-15.
 */

public class AutographActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autograph);
        initView();
    }

    private LinePathView linepath;
    private Button confirm, cancel;
    private void initView() {
        linepath = (LinePathView) findViewById(R.id.linepath);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == confirm){
            if (linepath.getTouched()){
//                bitmap = linepath.getBitMap();
                bitTag = 0;
                try {
                    linepath.save(Environment.getExternalStorageDirectory().toString() + "/inspection/qm.jpg", true, 20);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        } else if (v == cancel){
            linepath.clear();
        }
    }
}
