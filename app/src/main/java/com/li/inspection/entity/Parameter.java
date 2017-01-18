package com.li.inspection.entity;

/**
 * Created by long on 17-1-13.
 */

public class Parameter {
    private String parameter;
    private int idqualified;// -1 请判定  0 合格  1 不合格
    private String data;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getIdqualified() {
        return idqualified;
    }

    public void setIdqualified(int idqualified) {
        this.idqualified = idqualified;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
