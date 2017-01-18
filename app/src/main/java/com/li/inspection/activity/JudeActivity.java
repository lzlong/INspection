package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.adapter.JudeAdapter;
import com.li.inspection.application.SysApplication;
import com.li.inspection.constant.Constants;
import com.li.inspection.entity.Parameter;
import com.li.inspection.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by long on 17-1-10.
 */

public class JudeActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge);
        SysApplication.getInstance().addActivity(this);
        initView();
    }

    private ImageView back_none;
    private TextView name_none;
    private ListView judge_lv;
    private Button next_btn;
    private void initView() {
        back_none = (ImageView) findViewById(R.id.back_none);
        back_none.setOnClickListener(this);
        name_none = (TextView) findViewById(R.id.name_none);
        name_none.setText("车辆查验判定");
        judge_lv = (ListView) findViewById(R.id.judge_lv);
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);
        getData();
        JudeAdapter adapter = new JudeAdapter(JudeActivity.this, list);
        judge_lv.setAdapter(adapter);
    }

    List<Parameter> list = new ArrayList<>();
    private void getData() {
        for (String name: Constants.judea) {
            Parameter parameter = new Parameter();
            parameter.setParameter(name);
            parameter.setIdqualified(-1);
            list.add(parameter);
        }
        for (String name: Constants.judeb) {
            Parameter parameter = new Parameter();
            parameter.setParameter(name);
            parameter.setIdqualified(-1);
            list.add(parameter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == next_btn){
            for (int i = 1; i < Constants.judea.length; i++){
                if (list.get(i).getIdqualified() == -1){
                    Utils.showToast(JudeActivity.this, "还未对"+list.get(i).getParameter().substring(0, list.get(i).getParameter().length()-1)+"进行判定");
                    return;
                }
            }
            if (Utils.isBlank(list.get(3).getData())){
                Utils.showToast(JudeActivity.this, "请选择车辆颜色");
                return;
            }
            if (Utils.isBlank(list.get(4).getData())){
                Utils.showToast(JudeActivity.this, "请选择车辆和载人数");
                return;
            }
            Intent intent = new Intent(JudeActivity.this, VehiclePhotoActivity.class);
            startActivity(intent);
        } else if (v == back_none){
            finish();
        }
    }
}
