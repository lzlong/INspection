package com.li.inspection.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private TextView vehicle_photo_tve, vehicle_photo_tvf, vehicle_photo_tvg, vehicle_photo_tvh,
            vehicle_photo_tvi, vehicle_photo_tvj, vehicle_photo_tvk, vehicle_photo_tvl, vehicle_photo_tvm;
    private ImageView vehicle_photo_imge, vehicle_photo_imgf, vehicle_photo_imgg, vehicle_photo_imgh,
            vehicle_photo_imgi, vehicle_photo_imgj, vehicle_photo_imgk, vehicle_photo_imgl, vehicle_photo_imgm;

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

        vehicle_photo_tve = (TextView) findViewById(R.id.vehicle_photo_tve);
        vehicle_photo_imge = (ImageView) findViewById(R.id.vehicle_photo_imge);
        vehicle_photo_tvf = (TextView) findViewById(R.id.vehicle_photo_tvf);
        vehicle_photo_imgf = (ImageView) findViewById(R.id.vehicle_photo_imgf);
        vehicle_photo_tvg = (TextView) findViewById(R.id.vehicle_photo_tvg);
        vehicle_photo_imgg = (ImageView) findViewById(R.id.vehicle_photo_imgg);
        vehicle_photo_tvh = (TextView) findViewById(R.id.vehicle_photo_tvh);
        vehicle_photo_imgh = (ImageView) findViewById(R.id.vehicle_photo_imgh);
        vehicle_photo_tvi = (TextView) findViewById(R.id.vehicle_photo_tvi);
        vehicle_photo_imgi = (ImageView) findViewById(R.id.vehicle_photo_imgi);
        vehicle_photo_tvj = (TextView) findViewById(R.id.vehicle_photo_tvj);
        vehicle_photo_imgj = (ImageView) findViewById(R.id.vehicle_photo_imgj);
        vehicle_photo_tvk = (TextView) findViewById(R.id.vehicle_photo_tvk);
        vehicle_photo_imgk = (ImageView) findViewById(R.id.vehicle_photo_imgk);
        vehicle_photo_tvl = (TextView) findViewById(R.id.vehicle_photo_tvl);
        vehicle_photo_imgl = (ImageView) findViewById(R.id.vehicle_photo_imgl);
        vehicle_photo_tvm = (TextView) findViewById(R.id.vehicle_photo_tvm);
        vehicle_photo_imgm = (ImageView) findViewById(R.id.vehicle_photo_imgm);

        submit_btn = (Button) findViewById(R.id.submit_btn);
        click();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }
    int width;
    int height;

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

        vehicle_photo_tve.setOnClickListener(this);
        vehicle_photo_imge.setOnClickListener(this);
        vehicle_photo_tvf.setOnClickListener(this);
        vehicle_photo_imgf.setOnClickListener(this);
        vehicle_photo_tvg.setOnClickListener(this);
        vehicle_photo_imgg.setOnClickListener(this);
        vehicle_photo_tvh.setOnClickListener(this);
        vehicle_photo_imgh.setOnClickListener(this);
        vehicle_photo_tvi.setOnClickListener(this);
        vehicle_photo_imgi.setOnClickListener(this);
        vehicle_photo_tvj.setOnClickListener(this);
        vehicle_photo_imgj.setOnClickListener(this);
        vehicle_photo_tvk.setOnClickListener(this);
        vehicle_photo_imgk.setOnClickListener(this);
        vehicle_photo_tvl.setOnClickListener(this);
        vehicle_photo_imgl.setOnClickListener(this);
        vehicle_photo_tvm.setOnClickListener(this);
        vehicle_photo_imgm.setOnClickListener(this);

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
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgc){
            setTag(2);
            jump(PhotoActivity_a.class);
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
        } else if (v == vehicle_photo_tve){
            setTag(4);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imge){
            setTag(4);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvf){
            setTag(5);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgf){
            setTag(5);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvg){
            setTag(6);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgg){
            setTag(6);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvh){
            setTag(7);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgh){
            setTag(7);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvi){
            setTag(8);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgi){
            setTag(8);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvj){
            setTag(9);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgj){
            setTag(9);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvk){
            setTag(10);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgk){
            setTag(10);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvl){
            setTag(11);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgl){
            setTag(11);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_tvm){
            setTag(12);
            jump(PhotoActivity_a.class);
        } else if (v == vehicle_photo_imgm){
            setTag(12);
            jump(PhotoActivity_a.class);
        }
    }
    private boolean isSubmit = false;
    private void submitData() {
        InspectionData inspectionData = InspectionData.getInstance();
        if (Utils.isBlank(inspectionData.getLeft_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未拍摄左前45°照片");
            return;
        }
        if (Utils.isBlank(inspectionData.getRight_path())){
            Utils.showToast(VehiclePhotoActivity.this, "还未拍摄右后45°照片");
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
        if (isSubmit) return;
        isSubmit = true;
        Utils.showToast(VehiclePhotoActivity.this, "开始上传数据");
        Map<String, Object> params = new HashMap<String, Object>();
        if (Utils.isBlank(User.getInstance().getId())) {
            Utils.getUserData(VehiclePhotoActivity.this);
        }
        params.put("regId", User.getInstance().getId());//用户id
        params.put("cyy", User.getInstance().getName());
        params.put("syxx", inspectionData.getUse_property());
        params.put("hpzl", inspectionData.getPlate_type());
        params.put("ywlx", inspectionData.getService_type());
        params.put("cllx1", inspectionData.getVehicle_type());
        params.put("csys1", ((Parameter)inspectionData.getJudeList().get(3)).getData());
        params.put("hdzrs1", ((Parameter)inspectionData.getJudeList().get(4)).getData());
        params.put("fdjh", inspectionData.getFaDongJiHao());//车架号
        params.put("clsbdh1", inspectionData.getVIN());//VIN
        params.put("filename", inspectionData.getLeft_path().substring(
                inspectionData.getLeft_path().lastIndexOf("/") + 1,
                inspectionData.getLeft_path().length()));
        params.put("photoinfo", "1");
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
        } else {
            params.put(Constants.upData[Constants.upData.length-1], "1");
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
    private int imgNum = 4;
    private int upLoadNum = 0;
    private List filePaths  = null;

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
                    submit_num.setText("1/"+imgNum);
                    popupWindow=new WPopupWindow(wh, width-(width/4), height/3);
                    popupWindow.setOutTouchCancel(false);
                    wh.setFocusable(false); // 这个很重要
                    wh.setFocusableInTouchMode(false);
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
                    submit_num.setText("2/"+imgNum);
                    upLoad(InspectionData.getInstance().getRight_path(), 2);
                }
            } else if (msg.what == 2){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    submit_pro.setProgress(0);
                    submit_num.setText("3/"+imgNum);
                    upLoad(InspectionData.getInstance().getVin_path(), 3);
                }
            } else if (msg.what == 3){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    submit_pro.setProgress(0);
                    submit_num.setText("4/"+imgNum);
                    upLoad(InspectionData.getInstance().getSign_path(), 4);
                }
            } else if (msg.what == 4){
                int position = (int) msg.obj;
                submit_pro.setProgress(position);
                if (position == 100){
                    if (imgNum > (4+upLoadNum)){
                        submit_pro.setProgress(0);
                        submit_num.setText((4+upLoadNum)+"/"+imgNum);
                        upLoad((String) filePaths.get(upLoadNum), 4);
                        upLoadNum++;
                        return;
                    }
                    popupWindow.dismiss();
                    Utils.showToast(VehiclePhotoActivity.this, "上传完成");
                    InspectionData.getInstance().setNull();
                    Intent intent = new Intent(VehiclePhotoActivity.this, MainActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
                }
            } else if (msg.what == 5){
                JSONObject jsonObject = (JSONObject) msg.obj;
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
                Map<String, String> fileMap = makeName(name, type, fileId);
                if (fileMap != null){
                    if (fileMap.containsKey("name") && Utils.isNotBlank(fileMap.get("name"))) name = fileMap.get("name");
                    if (fileMap.containsKey("type") && Utils.isNotBlank(fileMap.get("type"))) type = fileMap.get("type");
                    if (fileMap.containsKey("fileId") && Utils.isNotBlank(fileMap.get("fileId"))) fileId = fileMap.get("fileId");
                }

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

    private Map makeName(String name, String type, String fileId){
        filePaths = new ArrayList();
        InspectionData inspectionData = InspectionData.getInstance();
        if (Utils.isNotBlank(inspectionData.getImgPath1())){
            name = name + "@" + inspectionData.getImgPath1().substring(inspectionData.getImgPath1().lastIndexOf("/") + 1, inspectionData.getImgPath1().length());
            type = type+"@1";
            fileId = fileId+"@5";
            imgNum++;
            filePaths.add(inspectionData.getImgPath1());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath2())){
            name = name + "@" + inspectionData.getImgPath2().substring(inspectionData.getImgPath2().lastIndexOf("/") + 1, inspectionData.getImgPath2().length());
            type = type+"@1";
            fileId = fileId+"@6";
            imgNum++;
            filePaths.add(inspectionData.getImgPath2());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath3())){
            name = name + "@" + inspectionData.getImgPath3().substring(inspectionData.getImgPath3().lastIndexOf("/") + 1, inspectionData.getImgPath3().length());
            type = type+"@1";
            fileId = fileId+"@7";
            imgNum++;
            filePaths.add(inspectionData.getImgPath3());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath4())){
            name = name + "@" + inspectionData.getImgPath4().substring(inspectionData.getImgPath4().lastIndexOf("/") + 1, inspectionData.getImgPath4().length());
            type = type+"@1";
            fileId = fileId+"@8";
            imgNum++;
            filePaths.add(inspectionData.getImgPath4());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath5())){
            name = name + "@" + inspectionData.getImgPath5().substring(inspectionData.getImgPath5().lastIndexOf("/") + 1, inspectionData.getImgPath5().length());
            type = type+"@1";
            fileId = fileId+"@9";
            imgNum++;
            filePaths.add(inspectionData.getImgPath5());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath6())){
            name = name + "@" + inspectionData.getImgPath6().substring(inspectionData.getImgPath6().lastIndexOf("/") + 1, inspectionData.getImgPath6().length());
            type = type+"@1";
            fileId = fileId+"@10";
            imgNum++;
            filePaths.add(inspectionData.getImgPath6());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath7())){
            name = name + "@" + inspectionData.getImgPath7().substring(inspectionData.getImgPath7().lastIndexOf("/") + 1, inspectionData.getImgPath7().length());
            type = type+"@1";
            fileId = fileId+"@11";
            imgNum++;
            filePaths.add(inspectionData.getImgPath7());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath8())){
            name = name + "@" + inspectionData.getImgPath8().substring(inspectionData.getImgPath8().lastIndexOf("/") + 1, inspectionData.getImgPath8().length());
            type = type+"@1";
            fileId = fileId+"@12";
            imgNum++;
            filePaths.add(inspectionData.getImgPath8());
        }
        if (Utils.isNotBlank(inspectionData.getImgPath9())){
            name = name + "@" + inspectionData.getImgPath9().substring(inspectionData.getImgPath9().lastIndexOf("/") + 1, inspectionData.getImgPath9().length());
            type = type+"@1";
            fileId = fileId+"@13";
            imgNum++;
            filePaths.add(inspectionData.getImgPath9());
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("fileId", fileId);
        return map;
    }

    String id = "";
    private void upLoad(final String path, final int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new FileUpload(id, path, new FileUpload.MyFileListener(){

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
            case 4:
                vehicle_photo_imge.setImageBitmap(bitmap);
                vehicle_photo_imge.setVisibility(View.VISIBLE);
                vehicle_photo_tve.setVisibility(View.INVISIBLE);
                break;
            case 5:
                vehicle_photo_imgf.setImageBitmap(bitmap);
                vehicle_photo_imgf.setVisibility(View.VISIBLE);
                vehicle_photo_tvf.setVisibility(View.INVISIBLE);
                break;
            case 6:
                vehicle_photo_imgg.setImageBitmap(bitmap);
                vehicle_photo_imgg.setVisibility(View.VISIBLE);
                vehicle_photo_tvg.setVisibility(View.INVISIBLE);
                break;
            case 7:
                vehicle_photo_imgh.setImageBitmap(bitmap);
                vehicle_photo_imgh.setVisibility(View.VISIBLE);
                vehicle_photo_tvh.setVisibility(View.INVISIBLE);
                break;
            case 8:
                vehicle_photo_imgi.setImageBitmap(bitmap);
                vehicle_photo_imgi.setVisibility(View.VISIBLE);
                vehicle_photo_tvi.setVisibility(View.INVISIBLE);
                break;
            case 9:
                vehicle_photo_imgj.setImageBitmap(bitmap);
                vehicle_photo_imgj.setVisibility(View.VISIBLE);
                vehicle_photo_tvj.setVisibility(View.INVISIBLE);
                break;
            case 10:
                vehicle_photo_imgk.setImageBitmap(bitmap);
                vehicle_photo_imgk.setVisibility(View.VISIBLE);
                vehicle_photo_tvk.setVisibility(View.INVISIBLE);
                break;
            case 11:
                vehicle_photo_imgl.setImageBitmap(bitmap);
                vehicle_photo_imgl.setVisibility(View.VISIBLE);
                vehicle_photo_tvl.setVisibility(View.INVISIBLE);
                break;
            case 12:
                vehicle_photo_imgm.setImageBitmap(bitmap);
                vehicle_photo_imgm.setVisibility(View.VISIBLE);
                vehicle_photo_tvm.setVisibility(View.INVISIBLE);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (popupWindow != null && popupWindow.isShowing()){
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
    }
}
