package com.example.a6175.fangwechat.model;

public interface Imodel {
    public void login(String username,String password,OnLoginListener onLoginListener);
    public void regiseter(String NickName,String MobilePhone,String Username,String password,OnRegisetereListener onRegisetereListener);
}
