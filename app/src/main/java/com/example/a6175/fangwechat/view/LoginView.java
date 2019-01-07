package com.example.a6175.fangwechat.view;

public interface LoginView {

    String getUSerName();
    String getPassword();

    void toMainActivity();
    void showFailederror(String e);
}
