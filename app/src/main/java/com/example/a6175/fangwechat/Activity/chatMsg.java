package com.example.a6175.fangwechat.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.a6175.fangwechat.BaseActivity;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.db.Author;
import com.example.a6175.fangwechat.db.Message;
import com.example.a6175.fangwechat.bean.User;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


import java.util.Date;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;


public class chatMsg extends BaseActivity implements MessageListHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
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

    private void queryMseeage(BmobIMMessage message){


    }
}
