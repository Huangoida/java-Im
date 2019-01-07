package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.presenter.LoginPresenter;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.view.LoginView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UserLoginActivity extends BaseActivity implements LoginView {

    private Button btn_login;
    private EditText et_Login_password;
    private EditText et_Login_usertel;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user__login);
        super.onCreate(savedInstanceState);





    }

    @Override
    protected void initControl() {
        btn_login=findViewById(R.id.btn_login);
        et_Login_password=findViewById(R.id.et_Login_password);
        et_Login_usertel = findViewById(R.id.et_Login_usertel);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter = new LoginPresenter(UserLoginActivity.this);
                loginPresenter.Login();

            }
        });
    }


    @Override
    public String getUSerName() {
        return et_Login_usertel.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_Login_password.getText().toString();
    }

    @Override
    public void toMainActivity() {
        Toast.makeText(UserLoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        FirstUseActivity.instance.finish();
        loginPresenter.detachMVPView();
    }

    @Override
    public void showFailederror(String e) {
        ActivityUtils.showShortToast(UserLoginActivity.this,e);
    }
}
