package com.example.a6175.fangwechat.bean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private Integer sex;
    private String Nickname;
    private BmobFile avater;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BmobFile getAvater() {
        return avater;
    }

    public void setAvater(BmobFile avater) {
        this.avater = avater;
    }



    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
