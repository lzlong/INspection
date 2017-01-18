package com.li.inspection.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by long on 17-1-18.
 */

public class RequestDTO implements Serializable {
    /**
     * 接口提供者的业务类别代码， 01：交警接口
     */
    private String xtlb;
    /**
     * 由外挂系统请求服务平台授权生成下发，现在暂时为：123456
     */
    private String jkxlh;
    /**
     * 接口定义
     */
    private String jkid;
    /**
     * 请求的json格式字符串
     */
    private Map<String, Object> json = new HashMap<String, Object>();

    public String getXtlb() {
        return xtlb;
    }

    public void setXtlb(String xtlb) {
        this.xtlb = xtlb;
    }

    public String getJkxlh() {
        return jkxlh;
    }

    public void setJkxlh(String jkxlh) {
        this.jkxlh = jkxlh;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public Map<String, Object> getJson() {
        return json;
    }

    public void setJson(Map<String, Object> json) {
        this.json = json;
    }
}
