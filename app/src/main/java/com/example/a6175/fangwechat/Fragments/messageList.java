package com.example.a6175.fangwechat.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.db.Author;
import com.example.a6175.fangwechat.db.Message;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class messageList extends Fragment implements MessageListHandler {

    private MessageInput messageInput;
    private User friend;
    private User user;
    private BmobIMConversation conversationEntrance;
    private MessagesListAdapter<Message> adapter;
    private ImageLoader imageLoader;
    private MessagesList messagesList;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messagelist,container,false);
        initControl();
        initData();
        initView();

        setListener();
        queryMseeage(null);
        return view;
    }

    private void initControl(){
        messagesList = view.findViewById(R.id.messagesList);
        messageInput = view.findViewById(R.id.input);
    }

    private void initData(){
        //注册EventBus
        EventBus.getDefault().register(this);
        user = BmobUser.getCurrentUser(User.class);
        //friend =(User)getIntent().getSerializableExtra("User_data");
        //创建新的对话实例，obtain方法才是真正创建一个管理消息发送的会话
        conversationEntrance = BmobIMConversation.obtain(BmobIMClient.getInstance()
                ,((BmobIMConversation)getActivity().getIntent().getSerializableExtra("c")));
        imageLoader =new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.with(getActivity()).load(url).into(imageView);
            }
        };
    }

    private void initView(){
        adapter = new MessagesListAdapter<>(user.getId(),imageLoader);
        messagesList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在销毁当前界面时，注销EventBus
        EventBus.getDefault().unregister(this);
    }



    private void setListener(){
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


    private void queryMseeage(BmobIMMessage message){
        conversationEntrance.queryMessages(message, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e==null){
                    for(BmobIMMessage message : list) {
                        BmobIMUserInfo bmobIMUserInfo =message.getBmobIMUserInfo();
                        Author author = new Author("wxid_"+message.getFromId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
                        Message messages =new Message(message.getFromId(),author,message.getContent());
                        adapter.addToStart(messages,true);
                    }
                }

            }
        });
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

    /**
     * 聊天消息接收事件
     * @param event
     */
    public void onEventMainThread(MessageEvent event){
        BmobIMMessage message=event.getMessage();
        BmobIMUserInfo bmobIMUserInfo = event.getFromUserInfo();
        Author author = new Author(bmobIMUserInfo.getUserId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
        Message messages =new Message(message.getFromId(),author,message.getContent());
        adapter.addToStart(messages,true);
    }

    /**
     * 离线消息接受事件
     * @param event
     */
    public void onEventMainThread(OfflineMessageEvent event ){

    }


}
