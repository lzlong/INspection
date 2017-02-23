package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.application.SysApplication;
import com.li.inspection.constant.Constants;
import com.li.inspection.entity.InspectionData;
import com.li.inspection.entity.Parameter;
import com.li.inspection.entity.RequestDTO;
import com.li.inspection.entity.User;
import com.li.inspection.util.FileUpload;
import com.li.inspection.util.HttpHelper;
import com.li.inspection.util.PermissionUtils;
import com.li.inspection.util.PullUtils;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        } else if (v == back_none){
            finish();
        }
    }
    private void submitData() {
        InspectionData inspectionData = InspectionData.getInstance();
        if (Utils.isBlank(inspectionData.getLeft_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未拍摄左前45°照片");
            return;
        }
        if (Utils.isBlank(inspectionData.getRight_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未拍摄右前45°照片");
            return;
        }
        if (Utils.isBlank(inspectionData.getVin_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未拍摄VIN");
            return;
        }
        if (Utils.isBlank(inspectionData.getSign_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未签名");
            return;
        }
//
        Map<String, Object> params = new HashMap<String, Object>();
        if (Utils.isBlank(User.getInstance().getId())) {
            Utils.getUserData(VehiclePhotoActivity.this);
        }
        params.put("regid", User.getInstance().getId());//用户id
        params.put("cyy", User.getInstance().getName());
        params.put("syxx", inspectionData.getUse_property());
        params.put("hpzl", inspectionData.getPlate_type());
        params.put("ywlx", inspectionData.getService_type());
        params.put("cllx1", inspectionData.getVehicle_type());
        params.put("csys1", ((Parameter)inspectionData.getJudeList().get(3)).getData());
        params.put("hdzrs1", ((Parameter)inspectionData.getJudeList().get(4)).getData());
        params.put("fdjh", inspectionData.getFaDongJiHao());//车架号
        params.put("clsbdh1", inspectionData.getVIN());//VIN
        boolean zt = false;
        for (int i=0; i< Constants.upData.length; i++){
            Parameter parameter = (Parameter) inspectionData.getJudeList().get(i);
            if (parameter.getIdqualified() == 0){
                params.put(Constants.upData[i], "1");
            } else if (parameter.getIdqualified() == 1){
                zt = true;
                params.put(Constants.upData[i], "2");
            } //else {
//                params.put(Constants.upData[i], "");
//            }
        }
        if (zt){
            params.put(Constants.upData[Constants.upData.length-1], "2");
        }
        final RequestDTO dto = new RequestDTO();
        dto.setXtlb("02");
        dto.setJkxlh("456789");
        dto.setJkid("07");
        dto.setJson(params);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = PullUtils.buildXML(dto);
                HttpHelper httpHelper = new HttpHelper();
                httpHelper.connect();
                HttpResponse response = httpHelper.doPost(Constants.HTTP_PATH + Constants.WEBSERVCIE_PATH, data);
                JSONObject jsonObject = Utils.parseResponse(response);
                handler.sendMessage(handler.obtainMessage(5, jsonObject));
            }
        }).start();
    }
    ProgressBar submit_pro;
    TextView submit_num;
    WPopupWindow popupWindow;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                JSONObject jsonObject = (JSONObject) msg.obj;
                if (jsonObject != null && jsonObject.optInt("0") == 0){
                    View wh= LayoutInflater.from(VehiclePhotoActivity.this).inflate(R.layout.submitpop,null);
                    submit_pro = (ProgressBar) wh.findViewById(R.id.submit_pro);
                    submit_num = (TextView) wh.findViewById(R.id.submit_num);
                    popupWindow=new WPopupWindow(wh, 500, 200);
                    popupWindow.setOutTouchCancel(false);
                    popupWindow.showAtLocation(Utils.getContentView(VehiclePhotoActivity.this), Gravity.CENTER, 0, 0);
                    upLoad(InspectionData.getInstance().getLeft_path(), 1);
                } else {
                    Utils.showToast(VehiclePhotoActivity.this, "网络异常");
                }
            } else if (msg.what == 1){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    submit_pro.setProgress(0);
                    submit_num.setText("2/4");
                    upLoad(InspectionData.getInstance().getRight_path(), 2);
                }
            } else if (msg.what == 2){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    submit_pro.setProgress(0);
                    submit_num.setText("3/4");
                    upLoad(InspectionData.getInstance().getVin_path(), 3);
                }
            } else if (msg.what == 3){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    submit_pro.setProgress(0);
                    submit_num.setText("4/4");
                    upLoad(InspectionData.getInstance().getSign_path(), 4);
                }
            } else if (msg.what == 4){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    popupWindow.dismiss();
                    Utils.showToast(VehiclePhotoActivity.this, "上传完成");
                    Intent intent = new Intent(VehiclePhotoActivity.this, MainActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
                }
            } else if (msg.what == 5){
                JSONObject jsonObject = (JSONObject) msg.obj;
                String id = "";
                if (jsonObject != null && jsonObject.optInt("0") == 0){
                    id = jsonObject.optString("id");
                } else {
                    Utils.showToast(VehiclePhotoActivity.this, "网络异常");
                }
                InspectionData inspectionData = InspectionData.getInstance();
                String name = inspectionData.getLeft_path().substring(inspectionData.getLeft_path().lastIndexOf("/") + 1, inspectionData.getLeft_path().length()) + "@"
                        + inspectionData.getRight_path().substring(inspectionData.getRight_path().lastIndexOf("/") + 1, inspectionData.getRight_path().length()) + "@"
                        + inspectionData.getVin_path().substring(inspectionData.getVin_path().lastIndexOf("/") + 1, inspectionData.getVin_path().length()) + "@"
                        + inspectionData.getSign_path().substring(inspectionData.getSign_path().lastIndexOf("/") + 1, inspectionData.getSign_path().length());
                String type = "1@1@1@1";
                String fileId = "1@2@3@4";

                Map<String, Object> params = new HashMap<String, Object>();
                if (Utils.isBlank(User.getInstance().getId())) {
                    Utils.getUserData(VehiclePhotoActivity.this);
                }
                params.put("regId", User.getInstance().getId());//用户id
                params.put("ownerId", id);//警情id
                params.put("fileName",name);
                params.put("fileType", type);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", fileId);
                params.put("photoInfo", param);
                final RequestDTO dto = new RequestDTO();
                dto.setXtlb("02");
                dto.setJkxlh("456789");
                dto.setJkid("08");
                dto.setJson(params);
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
        }
    };

    private void upLoad(final String path, final int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new FileUpload(path, new FileUpload.MyFileListener(){

                    @Override
                    public void transferred(int position) {
                        handler.sendMessage(handler.obtainMessage(what, position));
                    }
                }).transStart();
            }
        }).start();
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

    @Override
    protected void onStart() {
        super.onStart();
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    break;
                default:
                    break;
            }
        }
    };
}
