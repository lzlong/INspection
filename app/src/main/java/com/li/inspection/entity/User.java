package com.li.inspection.entity;

import java.io.Serializable;

/**
 * Created by long on 17-1-19.
 */

public class User implements Serializable{
    private String id;
    private String name;
    private String phone;
    private String photoUrl;
    private static User user=null;
    private User(){}
    public static synchronized User getInstance() {
        if(user==null)
            user=new User();
        return user;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
