package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.application.SysApplication;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;

/**
 * Created by long on 17-1-10.
 */

public class VehiclePhotoActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehiclephoto);
        SysApplication.getInstance().addActivity(this);
        initCiew();
    }
    private ImageView back_none;
    private TextView name_none;
    private TextView vehicle_photo_tva, vehicle_photo_tvb, vehicle_photo_tvc, vehicle_photo_tvd;
    private ImageView vehicle_photo_imga, vehicle_photo_imgb, vehicle_photo_imgc, vehicle_photo_imgd;
    private Button submit_btn;
    private void initCiew() {
        back_none = (ImageView) findViewById(R.id.back_none);
        name_none = (TextView) findViewById(R.id.name_none);
        name_none.setText("车辆查验拍照");
        vehicle_photo_tva = (TextView) findViewById(R.id.vehicle_photo_tva);
        vehicle_photo_imga = (ImageView) findViewById(R.id.vehicle_photo_imga);
        vehicle_photo_tvb = (TextView) findViewById(R.id.vehicle_photo_tvb);
        vehicle_photo_imgb = (ImageView) findViewById(R.id.vehicle_photo_imgb);
        vehicle_photo_tvc = (TextView) findViewById(R.id.vehicle_photo_tvc);
        vehicle_photo_imgc = (ImageView) findViewById(R.id.vehicle_photo_imgc);
        vehicle_photo_tvd = (TextView) findViewById(R.id.vehicle_photo_tvd);
        vehicle_photo_imgd = (ImageView) findViewById(R.id.vehicle_photo_imgd);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        click();
    }

    private void click() {
        back_none.setOnClickListener(this);
        vehicle_photo_tva.setOnClickListener(this);
        vehicle_photo_imga.setOnClickListener(this);
        vehicle_photo_tvb.setOnClickListener(this);
        vehicle_photo_imgb.setOnClickListener(this);
        vehicle_photo_tvc.setOnClickListener(this);
        vehicle_photo_imgc.setOnClickListener(this);
        vehicle_photo_tvd.setOnClickListener(this);
        vehicle_photo_imgd.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == vehicle_photo_tva){
            setTag(0);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imga){
            setTag(0);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvb){
            setTag(1);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgb){
            setTag(1);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvc){
            setTag(2);
            jump(PhotoActivity_b.class);
        } else if (v == vehicle_photo_imgc){
            setTag(2);
            jump(PhotoActivity_b.class);
        } else if (v == vehicle_photo_tvd){
            setTag(3);
            jump(AutographActivity.class);
        } else if (v == vehicle_photo_imgd){
            setTag(3);
            jump(AutographActivity.class);
        } else if (v == submit_btn){
            setTag(-1);
            submitData();
//            Intent intent = new Intent(VehiclePhotoActivity.this, MainActivity.class);
//            startActivity(intent);
//            SysApplication.getInstance().exit();
        } else if (v == back_none){
            finish();
        }
    }

    private void submitData() {
        View wh= LayoutInflater.from(this).inflate(R.layout.submitpop,null);
        WPopupWindow popupWindow=new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView(VehiclePhotoActivity.this), Gravity.CENTER, 0, 0);
    }

    public void setTag(int tag){
        this.tag = tag;
        this.bitTag = -1;
    }

    public void jump(Class activityClass){
        Intent intent = new Intent(VehiclePhotoActivity.this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tag != 3 && (bitmap == null || bitTag == -1)){return;}
        switch (tag){
            case 0:
                vehicle_photo_imga.setImageBitmap(bitmap);
                vehicle_photo_imga.setVisibility(View.VISIBLE);
                vehicle_photo_tva.setVisibility(View.INVISIBLE);
                break;
            case 1:
                vehicle_photo_imgb.setImageBitmap(bitmap);
                vehicle_photo_imgb.setVisibility(View.VISIBLE);
                vehicle_photo_tvb.setVisibility(View.INVISIBLE);
                break;
            case 2:
                vehicle_photo_imgc.setImageBitmap(bitmap);
                vehicle_photo_imgc.setVisibility(View.VISIBLE);
                vehicle_photo_tvc.setVisibility(View.INVISIBLE);
                break;
            case 3:
                bitmap = Utils.getDiskBitmap(Environment.getExternalStorageDirectory().toString() + "/inspection/qm.jpg");
                vehicle_photo_imgd.setImageBitmap(bitmap);
                vehicle_photo_imgd.setVisibility(View.VISIBLE);
                vehicle_photo_tvd.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
