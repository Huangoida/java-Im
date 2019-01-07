package com.example.a6175.fangwechat.view.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.db.Author;
import com.example.a6175.fangwechat.db.Message;
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



// TODO 发送图片
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
       // EventBus.getDefault().register(this);
        user = BmobUser.getCurrentUser(User.class);
        //friend =(User)getIntent().getSerializableExtra("User_data");
        //创建新的对话实例，obtain方法才是真正创建一个管理消息发送的会话
        conversationEntrance = BmobIMConversation.obtain(BmobIMClient.getInstance(),((BmobIMConversation)getActivity().getIntent().getSerializableExtra("c")));
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


    private void setListener(){
        //设置文本提交的监听器
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                final BmobIMTextMessage msg = new BmobIMTextMessage();
                msg.setContent(input.toString());
                conversationEntrance.sendMessage(msg, Listener);
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
                        BmobIMUserInfo bmobIMUserInfo = new BmobIMUserInfo();
                        if (message.getFromId().equals(user.getObjectId())){
                            bmobIMUserInfo=new BmobIMUserInfo(user.getObjectId(),user.getNickname(),user.getAvater().getFileUrl());
                        }else {
                            bmobIMUserInfo=(BmobIMUserInfo) getActivity().getIntent().getSerializableExtra("USerInfo");
                        }
                        Author author = new Author("wxid_"+message.getFromId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
                        Message messages =new Message(message.getFromId(),author,message.getContent());
                        adapter.addToStart(messages,true);
                    }
                }

            }
        });
    }


    /**
     * 消息发送监听器
     */
    public MessageSendListener Listener = new MessageSendListener() {
        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            if (e == null){
                Author author =new Author(user.getId(),user.getNickname(),user.getAvater().getFileUrl(),true);
                Date date=new Date();
                date.getTime();
                Message message = new Message(user.getId(),author,bmobIMMessage.getContent(),date);
                adapter.addToStart(message,true);
                Toast.makeText(getActivity(), bmobIMMessage.getContent(), Toast.LENGTH_SHORT).show();
            }else {
                ActivityUtils.showShortToast(getActivity(),e.getMessage());
            }
        }
    };


    /**
     * 消息接收：8.2、单个页面的自定义接收器
     * @param list
     */
    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        Log.d("聊天界面收到消息：", String.valueOf(list.size()));
        for (int i=0 ;i<list.size();i++){
            addMessage2Chat(list.get(i));
        }
    }

    private void addMessage2Chat(MessageEvent event){
        BmobIMMessage msg = event.getMessage();
        if (conversationEntrance != null && event != null && conversationEntrance.getConversationId().equals(event.getConversation().getConversationId())//如果是当前会话消息
            && !msg.isTransient()){//而且不为暂态消息
            BmobIMUserInfo bmobIMUserInfo =msg.getBmobIMUserInfo();
            Author author = new Author("wxid_"+msg.getFromId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
            Message messages =new Message(msg.getFromId(),author,msg.getContent());
            adapter.addToStart(messages,true);
        }
    }

    @Override
    public void onResume() {
        //添加页面消息监听器
        BmobIM.getInstance().addMessageListHandler(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        //在销毁当前界面时，注销EventBus
       // EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onPause() {
        BmobIM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }
//
//    /**
//     * 聊天消息接收事件
//     * @param event
//     */
//    @Subscribe
//    public void onEventMainThread(MessageEvent event){
//        BmobIMMessage message=event.getMessage();
//        BmobIMUserInfo bmobIMUserInfo = event.getFromUserInfo();
//        Author author = new Author(bmobIMUserInfo.getUserId(),bmobIMUserInfo.getName(),bmobIMUserInfo.getAvatar(),true);
//        Message messages =new Message(message.getFromId(),author,message.getContent());
//        adapter.addToStart(messages,true);
//    }
//
//    /**
//     * 离线消息接受事件
//     * @param event
//     */
//    @Subscribe
//    public void onEventMainThread(OfflineMessageEvent event ){
//
//    }




}
