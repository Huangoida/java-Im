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

public class First_use extends AppCompatActivity {

    public static  First_use  instance;
    Button btn_login;
    Button btn_registere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);

        init();

        btn_registere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First_use.this,User_regiseter.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First_use.this,User_Login.class );
                startActivity(intent);
            }
        });
    }

    private  void init()
    {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registere = (Button) findViewById(R.id.btn_registerer);
        instance = this;
    }
}
