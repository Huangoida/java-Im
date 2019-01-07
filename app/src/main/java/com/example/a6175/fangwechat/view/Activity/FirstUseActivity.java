package com.example.a6175.fangwechat.view.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;

/**
 * 第一次使用该app会进入该Activity
 */

public class FirstUseActivity extends BaseActivity  {


    public static FirstUseActivity instance;
    Button btn_login;
    Button btn_registere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_first_use);
        super.onCreate(savedInstanceState);
        //注册的按钮
        btn_registere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start_Activity(FirstUseActivity.this,User_regiseter.class);
            }
        });
        //登录的按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start_Activity(FirstUseActivity.this,UserLoginActivity.class);
            }
        });
    }

    @Override
    protected void initControl() {
        btn_login =  findViewById(R.id.btn_login);
        btn_registere = findViewById(R.id.btn_registerer);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        instance = this;
    }

    @Override
    protected void setListener() {

    }

}
