package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6175.fangwechat.bean.AddFriendMessage;
import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.FriendUser;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
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
    private BmobIMConversation conversationEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_information);
        super.onCreate(savedInstanceState);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_code==0){
                    sendAddFriendMessage();
                    //AddFriend();
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

    /**
     * 发送添加好友的请求
     */
    private void sendAddFriendMessage() {
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(),user.getNickname(),user.getAvater().getFileUrl());
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名
        map.put("avatar", currentUser.getAvater().getFileUrl());//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    ActivityUtils.showShortToast(detailInformation.this,"好友请求发送成功，等待验证");
                } else {//发送失败
                    ActivityUtils.showShortToast(detailInformation.this,"发送失败:" + e.getMessage());
                }
            }
        });
    }
}
