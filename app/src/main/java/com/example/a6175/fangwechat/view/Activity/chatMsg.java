package com.example.a6175.fangwechat.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.a6175.fangwechat.view.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;


import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;



public class chatMsg extends BaseActivity implements MessageListHandler {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent =getIntent();
        String objectId= intent.getStringExtra("ID");
        queryUser(objectId);
    }

    @Override
    protected void initControl() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {
    }

    @Override
    public void onResume() {
        super.onResume();
        BmobIM.getInstance().addMessageListHandler(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BmobIM.getInstance().removeMessageListHandler(this);
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {

    }

    /**
     * 查询聊天用户，渲染view
     */
    private void queryUser(String objectID){
        BmobQuery<User>userBmobQuery =new BmobQuery<>();
        userBmobQuery.getObject(objectID, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                toolbar.setTitle(user.getNickname());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

            }
        });

    }
}
