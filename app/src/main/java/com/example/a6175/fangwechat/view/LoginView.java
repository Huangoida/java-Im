package com.example.a6175.fangwechat.view;

/**
 * @author a6175
 */
public interface LoginView {

    String getUSerName();
    String getPassword();

    void toMainActivity();
    void showFailederror(String e);
}
