package com.example.a6175.fangwechat.presenter;

import com.example.a6175.fangwechat.model.UserModel;
import com.example.a6175.fangwechat.model.OnLoginListener;
import com.example.a6175.fangwechat.view.LoginView;

public class LoginPresenter implements UserPresenter {
    LoginView loginView;
    UserModel userModel;

    /**
     * 绑定V层和M层
     * @param loginView
     */
    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
        this.userModel = new UserModel();
    }

    public void Login(){
        userModel.login(loginView.getUSerName(), loginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess() {
                loginView.toMainActivity();
            }

            @Override
            public void loginFailed(String e) {
                loginView.showFailederror(e);
            }
        });
    }
    /**
     * 解绑View层，防止内存泄漏
     */
    public void detachMVPView(){
        loginView = null;
    }



}
