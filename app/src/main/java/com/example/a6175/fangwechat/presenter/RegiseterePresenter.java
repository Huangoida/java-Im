package com.example.a6175.fangwechat.presenter;

import com.example.a6175.fangwechat.model.OnRegisetereListener;
import com.example.a6175.fangwechat.model.UserModel;
import com.example.a6175.fangwechat.view.LoginView;
import com.example.a6175.fangwechat.view.RegistereView;

public class RegiseterePresenter implements UserPresenter {
    RegistereView RegisetereView;
    UserModel userModel;

    /**
     * 绑定V层和M层
     * @param loginView
     */
    public RegiseterePresenter(RegistereView loginView){
        this.RegisetereView = loginView;
        this.userModel = new UserModel();
    }

    public void Regiseter(){
        String Nickname = RegisetereView.getNickName();
        String PhoneName = RegisetereView.getMobilePhone();
        String UserName =RegisetereView.getUserName();
        String PassWord = RegisetereView.getPassword();
        userModel.regiseter(Nickname, PhoneName, UserName, PassWord, new OnRegisetereListener() {
            @Override
            public void RegisetereSuccess() {
                RegisetereView.finishRegister();
            }

            @Override
            public void RegisetereFailed(String e) {
                RegisetereView.showFailederror(e);
            }
        });
    }
    /**
     * 解绑View层，防止内存泄漏
     */
    public void detachMVPView(){
        RegisetereView = null;
    }
}
