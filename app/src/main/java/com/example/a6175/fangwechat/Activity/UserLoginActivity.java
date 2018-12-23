package com.example.a6175.fangwechat.Activity;

import android.content.Intent;
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

public class UserLoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_Login_password;
    EditText et_Login_usertel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);

        init();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsername(et_Login_usertel.getText().toString());
                user.setPassword(et_Login_password.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null)
                        {
                            Toast.makeText(UserLoginActivity.this,user.getUsername()+"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            FirstuseActivity.instance.finish();

                        }else
                        {
                            Log.e("登录失败","原因",e);
                        }
                    }
                });
            }
        });

    }

    //初始化控件和对象
    private void init()
    {
        user = new User();
        btn_login=findViewById(R.id.btn_login);
        et_Login_password=findViewById(R.id.et_Login_password);
        et_Login_usertel = findViewById(R.id.et_Login_usertel);
    }
}
