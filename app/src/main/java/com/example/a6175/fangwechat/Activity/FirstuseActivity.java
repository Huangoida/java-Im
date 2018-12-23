package com.example.a6175.fangwechat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a6175.fangwechat.R;

/**
 * 第一次使用该app会进入该Activity
 */

public class FirstuseActivity extends AppCompatActivity {

    public static FirstuseActivity instance;
    Button btn_login;
    Button btn_registere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);

        init();


        //注册的按钮
        btn_registere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstuseActivity.this,User_regiseter.class);
                startActivity(intent);
            }
        });
        //登录的按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstuseActivity.this,UserLoginActivity.class );
                startActivity(intent);
            }
        });
    }

    //初始化控件和对象
    private  void init()
    {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registere = (Button) findViewById(R.id.btn_registerer);
        instance = this;
    }
}
