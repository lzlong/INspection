package com.li.inspection.entity;

import java.io.Serializable;
import java.util.List;

import static com.li.inspection.R.mipmap.user;

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
    private static InspectionData inspectionData=null;
    private InspectionData(){}
    public static synchronized InspectionData getInstance() {
        if(inspectionData==null)
            inspectionData=new InspectionData();
        return inspectionData;
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
}
