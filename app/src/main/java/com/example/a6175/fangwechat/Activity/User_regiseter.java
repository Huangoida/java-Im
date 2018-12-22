package com.example.a6175.fangwechat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册的活动
 */
public class User_regiseter extends AppCompatActivity {

    Button btn_register;
    EditText et_username;
    EditText et_usertel;
    EditText et_password;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regiseter);
        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(et_username.getText().toString());
                user.setMobilePhoneNumber(et_usertel.getText().toString());
                user.setUsername(user.getMobilePhoneNumber());
                user.setPassword(et_password.getText().toString());

                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null)
                        {
                            Toast.makeText(User_regiseter.this,"注册成功",Toast.LENGTH_SHORT).show();
                            finish();//注册成功后退出该活动
                        }else
                        {
                            Log.d("注册失败","原因:",e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化组件
     */

    private  void init()
    {
        user = new User();
        btn_register = (Button) findViewById(R.id.btn_register);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        et_usertel = (EditText) findViewById(R.id.et_usertel);
    }
}
