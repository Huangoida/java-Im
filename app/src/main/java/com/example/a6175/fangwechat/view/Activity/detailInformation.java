package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6175.fangwechat.model.AgreeAddFriendMessage;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.exception.BmobException;

public class detailInformation extends BaseActivity {

    private User user;
    private Toolbar toolbar;
    private TextView userNickname;
    private ImageView userAvater;
    private TextView sign;
    private Button addFriend;
    private int statusCode;//判断是否是好友
    private BmobIMConversation conversationEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_information);
        super.onCreate(savedInstanceState);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusCode ==0){
                   AgreeAddFriendMessage.sendAddFriendMessage(user);
                }else {
                    BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickname(),user.getAvater().getFileUrl());
                    //开启私聊会话，设置会话保存在本地会话表中
                    conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                        @Override
                        public void done(BmobIMConversation bmobIMConversation, BmobException e) {
                            if (e == null){
                                final BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickname(),user.getAvater().getFileUrl());
                                Intent intent = new Intent(detailInformation.this,chatMsg.class);
                                intent.putExtra("USerInfo",info);
                                String objectid =info.getUserId();
                                intent.putExtra("ID",objectid);
                                intent.putExtra("c",bmobIMConversation);
                                startActivity(intent);
                                finish(detailInformation.this);
                            }
                        }
                    });

                }

            }
        });
    }

    @Override
    protected void initControl() {
        toolbar=  findViewById(R.id.toolbar);
        userNickname = findViewById(R.id.User_nickname);
        userAvater =  findViewById(R.id.User_avater);
        sign = findViewById(R.id.sign);
        addFriend =  findViewById(R.id.Addfriend);
    }

    @Override
    protected void initView() {
        if (statusCode ==0){
            addFriend.setText("添加到通讯录");
        }else {
            addFriend.setText("发消息");
        }
        userNickname.setText(user.getNickname());
        Picasso.with(this).load(user.getAvater().getFileUrl()).into(userAvater);
        sign.setText("空");
    }

    @Override
    protected void initData() {
        user=(User)getIntent().getSerializableExtra("User_data");
        statusCode = getIntent().getIntExtra("CODE",0);
    }

    @Override
    protected void setListener() {

    }




}
