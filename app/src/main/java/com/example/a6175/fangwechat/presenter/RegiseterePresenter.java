package com.example.a6175.fangwechat.presenter;

import com.example.a6175.fangwechat.Listener.OnRegisetereListener;
import com.example.a6175.fangwechat.model.UserModel;
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
        String nickname = RegisetereView.getNickName();
        String phonename = RegisetereView.getMobilePhone();
        String username =RegisetereView.getUserName();
        String password = RegisetereView.getPassword();
        userModel.regiseter(nickname, phonename, username, password, new OnRegisetereListener() {
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
