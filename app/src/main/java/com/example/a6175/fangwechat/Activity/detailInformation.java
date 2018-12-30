package com.example.a6175.fangwechat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.User;
import com.squareup.picasso.Picasso;

public class detailInformation extends BaseActivity {

    private User user;
    private Toolbar toolbar;
    private TextView User_nickname;
    private ImageView User_avater;
    private TextView sign;
    private Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_information);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initControl() {
        toolbar=  findViewById(R.id.toolbar);
        User_nickname= findViewById(R.id.User_nickname);
        User_avater =  findViewById(R.id.User_avater);
        sign = findViewById(R.id.sign);
        addFriend =  findViewById(R.id.Addfriend);
    }

    @Override
    protected void initView() {
        User_nickname.setText(user.getNickname());
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);
        sign.setText("空");
    }

    @Override
    protected void initData() {
        user=(User)getIntent().getSerializableExtra("User_data");
    }

    @Override
    protected void setListener() {

    }
}
