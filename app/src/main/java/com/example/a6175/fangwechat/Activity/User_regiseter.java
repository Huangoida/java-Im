package com.example.a6175.fangwechat.Activity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.PhotoUtils;
import com.example.a6175.fangwechat.db.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

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
                user.setNickname(et_username.getText().toString());
                user.setMobilePhoneNumber(et_usertel.getText().toString());
                user.setUsername(user.getMobilePhoneNumber());
                user.setPassword(et_password.getText().toString());
                SetDefaultAvater();
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null)
                        {
                            Toast.makeText(User_regiseter.this,"注册成功",Toast.LENGTH_SHORT).show();
                            user.setId("wxid_"+user.getObjectId());
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                            finish();//注册成功后退出该活动
                        }else
                        {
                            Toast.makeText(User_regiseter.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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


    private void SetDefaultAvater(){
        BmobFile bmobFile = new BmobFile("default.png","","http://bmob-cdn-23206.b0.upaiyun.com/2018/12/29/0cad45e4401054b280f5556c05395e17.png");
        user.setAvater(bmobFile);
    }

}
