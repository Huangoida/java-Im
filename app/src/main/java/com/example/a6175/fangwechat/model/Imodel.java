package com.example.a6175.fangwechat.model;

import com.example.a6175.fangwechat.Listener.OnLoginListener;
import com.example.a6175.fangwechat.Listener.OnRegisetereListener;

public interface Imodel {
    public void login(String username,String password,OnLoginListener onLoginListener);
    public void regiseter(String NickName,String MobilePhone,String Username,String password,OnRegisetereListener onRegisetereListener);
}
