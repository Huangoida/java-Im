package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.presenter.LoginPresenter;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.view.LoginView;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

public class UserLoginActivity extends BaseActivity implements LoginView {

    private Button btnLogin;
    private EditText etLoginPassword;
    private EditText etLoginUsertel;
    private LoginPresenter loginPresenter;
    private CircleProgressDialog circleProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user__login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initControl() {
        btnLogin =findViewById(R.id.btn_login);
        etLoginPassword =findViewById(R.id.et_Login_password);
        etLoginUsertel = findViewById(R.id.et_Login_usertel);
    }

    @Override
    protected void initView() {
        circleProgressDialog = new CircleProgressDialog(this);
        circleProgressDialog.setDialogSize(15);
        circleProgressDialog.setProgressColor(Color.parseColor("#FFFFFF"));
        circleProgressDialog.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressDialog.showDialog();//显示对话框
                loginPresenter = new LoginPresenter(UserLoginActivity.this);
                loginPresenter.Login();

            }
        });
    }


    @Override
    public String getUSerName() {
        return etLoginUsertel.getText().toString();
    }

    @Override
    public String getPassword() {
        return etLoginPassword.getText().toString();
    }

    @Override
    public void toMainActivity() {
        Toast.makeText(UserLoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
        startActivity(intent);
        circleProgressDialog.dismiss();
        finish();
        FirstUseActivity.instance.finish();
        loginPresenter.detachMVPView();

    }

    @Override
    public void showFailederror(String e) {
        ActivityUtils.showShortToast(UserLoginActivity.this,e);
    }
}
