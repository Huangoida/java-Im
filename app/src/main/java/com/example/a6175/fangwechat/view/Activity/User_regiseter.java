package com.example.a6175.fangwechat.view.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a6175.fangwechat.presenter.RegiseterePresenter;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.view.RegistereView;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 注册的活动
 */
public class User_regiseter extends BaseActivity implements RegistereView {

    private Button btn_register;
    private EditText et_username;
    private EditText et_usertel;
    private EditText et_password;
    private RegiseterePresenter regiseterePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_regiseter);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initControl() {
        btn_register =  findViewById(R.id.btn_register);
        et_password =  findViewById(R.id.et_password);
        et_username =  findViewById(R.id.et_username);
        et_usertel =  findViewById(R.id.et_usertel);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() { }

    @Override
    protected void setListener() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regiseterePresenter = new RegiseterePresenter(User_regiseter.this);
                regiseterePresenter.Regiseter();
            }
        });
    }

    @Override
    public void finishRegister() {
        ActivityUtils.showShortToast(User_regiseter.this,"注册成功");
        finish();
        regiseterePresenter.detachMVPView();
    }

    @Override
    public void showFailederror(String e) {
        ActivityUtils.showShortToast(User_regiseter.this,e);
    }

    @Override
    public String getUserName() {
        return et_username.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public String getMobilePhone() {
        return et_usertel.getText().toString();
    }

    @Override
    public String getNickName() {
        return et_usertel.getText().toString();
    }
}
