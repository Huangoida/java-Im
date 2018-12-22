package com.example.a6175.fangwechat.db;


import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private Boolean sex;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
