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

    private MessageInput messageInput;
    private User friend;
    private User user;
    private BmobIMConversation conversationEntrance;
    private MessagesListAdapter<Message> adapter;
    private ImageLoader imageLoader;
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat_msg);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initControl() {
        messagesList = findViewById(R.id.messagesList);
        messageInput = findViewById(R.id.input);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = BmobUser.getCurrentUser(User.class);
        //friend =(User)getIntent().getSerializableExtra("User_data");
        //创建新的对话实例，obtain方法才是真正创建一个管理消息发送的会话
        conversationEntrance = BmobIMConversation.obtain(BmobIMClient.getInstance(),((BmobIMConversation)getIntent().getSerializableExtra("c")));

        imageLoader =new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.with(chatMsg.this).load(url).into(imageView);
            }
        };

        adapter = new MessagesListAdapter<>(user.getId(),imageLoader);
        messagesList.setAdapter(adapter);
        queryMseeage(null);

    }

    @Override
    protected void setListener() {

        //设置文本提交的监听器
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                final BmobIMTextMessage msg = new BmobIMTextMessage();
                msg.setContent(input.toString());
                conversationEntrance.sendMessage(msg, new MessageSendListener() {
                    @Override
                    public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                        Author author =new Author(user.getId(),user.getNickname(),user.getAvater().getFileUrl(),true);
                        Date date=new Date();
                        date.getTime();
                        Message message = new Message(user.getId(),author,bmobIMMessage.getContent(),date);
                        adapter.addToStart(message,true);
                    }
                });

                return true;
            }
        });
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
        for (int i =0 ;i<list.size();i++){
            BmobIMMessage message=list.get(i).getMessage();
            BmobIMUserInfo bmobIMUserInfo =message.getBmobIMUserInfo();
            Author author = new Author(bmobIMUserInfo.getUserId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
            Message messages =new Message(message.getFromId(),author,message.getContent());
            adapter.addToStart(messages,true);
        }
    }

    private void queryMseeage(BmobIMMessage message){
        conversationEntrance.queryMessages(message, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e==null){
                    for(BmobIMMessage message : list) {
                        BmobIMUserInfo bmobIMUserInfo =message.getBmobIMUserInfo();
                        Author author = new Author(bmobIMUserInfo.getUserId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
                        Message messages =new Message(message.getFromId(),author,message.getContent());
                        adapter.addToStart(messages,true);
                    }
                }

            }
        });

    }
}
