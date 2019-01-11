package com.example.a6175.fangwechat.view.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a6175.fangwechat.presenter.RegiseterePresenter;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.view.RegistereView;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

/**
 * 注册的活动
 */
public class User_regiseter extends BaseActivity implements RegistereView {

    private Button btnRegister;
    private EditText etUsername;
    private EditText etUsertel;
    private EditText etPassword;
    private RegiseterePresenter regiseterePresenter;
    private CircleProgressDialog circleProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_regiseter);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initControl() {
        btnRegister =  findViewById(R.id.btn_register);
        etPassword =  findViewById(R.id.et_password);
        etUsername =  findViewById(R.id.et_username);
        etUsertel =  findViewById(R.id.et_usertel);
    }

    @Override
    protected void initView() {
        circleProgressDialog =new CircleProgressDialog(this);
        circleProgressDialog.setDialogSize(15);
        circleProgressDialog.setProgressColor(Color.parseColor("#FFFFFF"));
        circleProgressDialog.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void initData() { }

    @Override
    protected void setListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressDialog.showDialog();
                regiseterePresenter = new RegiseterePresenter(User_regiseter.this);
                regiseterePresenter.Regiseter();
            }
        });
    }

    @Override
    public void finishRegister() {
        circleProgressDialog.dismiss();
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
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getMobilePhone() {
        return etUsertel.getText().toString();
    }

    @Override
    public String getNickName() {
        return etUsertel.getText().toString();
    }
}
