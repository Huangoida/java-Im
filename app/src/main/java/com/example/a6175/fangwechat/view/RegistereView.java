package com.example.a6175.fangwechat.view;

public interface RegistereView {
    void finishRegister();
    void showFailederror(String e);

    String getUserName();
    String getPassword();
    String getMobilePhone();
    String getNickName();
}
