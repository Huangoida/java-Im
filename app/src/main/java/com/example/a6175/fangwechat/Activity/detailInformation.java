package com.example.a6175.fangwechat.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.db.FriendUser;
import com.example.a6175.fangwechat.db.User;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class detailInformation extends BaseActivity {

    private User user;
    private Toolbar toolbar;
    private TextView User_nickname;
    private ImageView User_avater;
    private TextView sign;
    private Button addFriend;
    private int status_code;//判断是否是好友

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_information);
        super.onCreate(savedInstanceState);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_code==0){
                    AddFriend();
                }

            }
        });

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
        if (status_code==0){
            addFriend.setText("添加到通讯录");
        }else {
            addFriend.setText("发消息");
        }
        User_nickname.setText(user.getNickname());
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(User_avater);
        sign.setText("空");
    }

    @Override
    protected void initData() {
        user=(User)getIntent().getSerializableExtra("User_data");
        status_code = getIntent().getIntExtra("CODE",0);
    }

    @Override
    protected void setListener() {

    }

    private  void AddFriend(){
        final FriendUser friendUser = new FriendUser();
        final User UserNow = BmobUser.getCurrentUser(User.class);
        friendUser.setUser(UserNow);
        friendUser.setFriendUser(user);
        friendUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    friendUser.setFriendUser(UserNow);
                    friendUser.setUser(user);
                    friendUser.save(new SaveListener<String>() {//双向存储
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                ActivityUtils.showShortToast(detailInformation.this,"添加成功");
                            }else{
                                ActivityUtils.showShortToast(detailInformation.this,e.getMessage());
                            }
                        }
                    });
                }   else {
                    ActivityUtils.showShortToast(detailInformation.this,e.getMessage());
                }
            }
        });
    }
}
