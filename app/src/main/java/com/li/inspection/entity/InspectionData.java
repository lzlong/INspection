package com.li.inspection.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by long on 17-1-19.
 */

public class InspectionData implements Serializable {
    private String use_property;
    private String plate_type;
    private String service_type;
    private String vehicle_type;
    private String VIN;
    private String faDongJiHao;
    private List judeList;
    private String left_path;
    private String right_path;
    private String vin_path;
    private String sign_path;
    private String imgPath1; //铭牌
    private String imgPath2; //ABS连线
    private String imgPath3;//ABS阀
    private String imgPath4;//轮胎带花
    private String imgPath5;//行驶记录仪
    private String imgPath6;//驾驶室左侧
    private String imgPath7;//驾驶室右侧
    private String imgPath8;//驾驶室后排室
    private String imgPath9;//合格证

    private static InspectionData inspectionData=null;
    private InspectionData(){}
    public static synchronized InspectionData getInstance() {
        if(inspectionData==null)
            inspectionData=new InspectionData();
        return inspectionData;
    }
    public  void setNull(){
        inspectionData=null;
    }
    public String getSign_path() {
        return sign_path;
    }

    public void setSign_path(String sign_path) {
        this.sign_path = sign_path;
    }

    public String getVin_path() {
        return vin_path;
    }

    public void setVin_path(String vin_path) {
        this.vin_path = vin_path;
    }

    public String getRight_path() {
        return right_path;
    }

    public void setRight_path(String right_path) {
        this.right_path = right_path;
    }

    public String getLeft_path() {
        return left_path;
    }

    public void setLeft_path(String left_path) {
        this.left_path = left_path;
    }

    public List getJudeList() {
        return judeList;
    }

    public void setJudeList(List judeList) {
        this.judeList = judeList;
    }

    public String getFaDongJiHao() {
        return faDongJiHao;
    }

    public void setFaDongJiHao(String faDongJiHao) {
        this.faDongJiHao = faDongJiHao;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getPlate_type() {
        return plate_type;
    }

    public void setPlate_type(String plate_type) {
        this.plate_type = plate_type;
    }

    public String getUse_property() {
        return use_property;
    }

    public void setUse_property(String use_property) {
        this.use_property = use_property;
    }


    public String getImgPath1() {
        return imgPath1;
    }

    public void setImgPath1(String imgPath1) {
        this.imgPath1 = imgPath1;
    }

    public String getImgPath2() {
        return imgPath2;
    }

    public void setImgPath2(String imgPath2) {
        this.imgPath2 = imgPath2;
    }

    public String getImgPath3() {
        return imgPath3;
    }

    public void setImgPath3(String imgPath3) {
        this.imgPath3 = imgPath3;
    }

    public String getImgPath4() {
        return imgPath4;
    }

    public void setImgPath4(String imgPath4) {
        this.imgPath4 = imgPath4;
    }

    public String getImgPath5() {
        return imgPath5;
    }

    public void setImgPath5(String imgPath5) {
        this.imgPath5 = imgPath5;
    }

    public String getImgPath6() {
        return imgPath6;
    }

    public void setImgPath6(String imgPath6) {
        this.imgPath6 = imgPath6;
    }

    public String getImgPath7() {
        return imgPath7;
    }

    public void setImgPath7(String imgPath7) {
        this.imgPath7 = imgPath7;
    }

    public String getImgPath8() {
        return imgPath8;
    }

    public void setImgPath8(String imgPath8) {
        this.imgPath8 = imgPath8;
    }

    public String getImgPath9() {
        return imgPath9;
    }

    public void setImgPath9(String imgPath9) {
        this.imgPath9 = imgPath9;
    }

}
