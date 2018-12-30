package com.example.a6175.fangwechat.Activity;

import android.support.v7.app.AppCompatActivity;
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
import com.example.a6175.fangwechat.db.NewFriend;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_information);
        super.onCreate(savedInstanceState);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriend();
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

    private  void AddFriend(){
        final FriendUser friendUser = new FriendUser();
        final User UserNow = BmobUser.getCurrentUser(User.class);
        friendUser.setUser(UserNow);
        friendUser.setFirendUser(user);
        friendUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    friendUser.setFirendUser(UserNow);
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
