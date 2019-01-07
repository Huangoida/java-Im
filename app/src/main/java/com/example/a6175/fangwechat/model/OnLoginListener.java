package com.example.a6175.fangwechat.model;

import com.example.a6175.fangwechat.bean.User;

public interface OnLoginListener {
    void loginSuccess();
    void loginFailed(String e);
}
