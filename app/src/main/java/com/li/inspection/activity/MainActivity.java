package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.application.SysApplication;
import com.li.inspection.constant.Constants;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;
import com.li.inspection.util.WheelView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

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
            check();
        }
    }

    private void check() {
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
        if (Utils.isBlank(engine)){
            Utils.showToast(MainActivity.this, "请填写发动机号");
            return;
        }
        Intent intent = new Intent(MainActivity.this, JudeActivity.class);
        startActivity(intent);
    }

    public void showPop(final TextView textView, String[] data){
        View wh= LayoutInflater.from(this).inflate(R.layout.common_window_wheel,null);
        final WheelView picker= (WheelView) wh.findViewById(R.id.wheel);
        for (String name : data){
            picker.addData(name);
        }
        picker.setCenterItem(data.length/3);
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