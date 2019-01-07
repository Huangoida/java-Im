package com.example.a6175.fangwechat.view.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a6175.fangwechat.view.Activity.chatMsg;
import com.example.a6175.fangwechat.R;
import com.example.a6175.fangwechat.bean.Conversation;
import com.example.a6175.fangwechat.bean.PrivateConversation;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.db.Author;
import com.example.a6175.fangwechat.db.DefaultDialog;
import com.example.a6175.fangwechat.db.Message;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;


//TODO 不会新增会话

public class WechatFragment extends Fragment implements MessageListHandler {
    private String mTitle;
    private User user;
    private DialogsListAdapter dialogsListAdapter ;
    private ImageLoader imageLoader;
    private List<DefaultDialog>dialogList;
    private DialogsList dialogsList;
    private List<Conversation> lists;
    List<BmobIMUserInfo> bmobIMUserInfoList;


    public static WechatFragment getInstance(String mTitle)
    {
        WechatFragment WF = new WechatFragment();
        WF.mTitle = mTitle;
        return WF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wechat,null);
        user = BmobUser.getCurrentUser(User.class);
        dialogsList =v.findViewById(R.id.dialogsList);
        imageLoader =new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                if (url.equals("")){
                    Picasso.with(getActivity()).load(R.drawable.default_avatar).into(imageView);
                }else {
                    Picasso.with(getActivity()).load(url).into(imageView);
                }

            }
        };
        dialogsListAdapter =new DialogsListAdapter(R.layout.item_custom_dialog,imageLoader);
        dialogsList.setAdapter(dialogsListAdapter);
        query();
        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener() {
            @Override
            public void onDialogClick(IDialog dialog) {
                String id = dialog.getId();
                final BmobIMUserInfo info = BmobIM.getInstance().getUserInfo(id);
                //开启私聊会话，设置会话保存在本地会话表中
                BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                    @Override
                    public void done(BmobIMConversation bmobIMConversation, BmobException e) {
                        if (e == null){
                            Intent intent = new Intent(getActivity(),chatMsg.class);
                            intent.putExtra("ID",info.getUserId());
                            intent.putExtra("USerInfo",info);
                            intent.putExtra("c",bmobIMConversation);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        dialogsListAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener() {
            @Override
            public void onDialogLongClick(IDialog dialog) {
                dialogsListAdapter.deleteById(dialog.getId());
            }
        });
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        BmobIM.getInstance().addMessageListHandler(this);
        query();
    }

    @Override
    public void onPause() {
        super.onPause();
        BmobIM.getInstance().removeMessageListHandler(this);
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        Log.d("会话界面收到消息：", String.valueOf(list.size()));
        for (int i=0 ;i<list.size();i++){

            BmobIMConversation conversation=list.get(i).getConversation();
            BmobIMMessage BmobIMmessage =list.get(i).getMessage();
            BmobIMUserInfo bmobIMUserInfo = list.get(i).getFromUserInfo();
            if (BmobIMmessage.getMsgType().equals("txt")){
                query();
            }else if (BmobIMmessage.getMsgType().equals("add")){

            }

        }
    }

    /**
     * 将会话添加到Chat
     */
    public void query(){

        lists = getConversation();
        List<BmobIMUserInfo>userInfos =new ArrayList<>();

        for (int i=0;i<lists.size();i++){
            BmobIMUserInfo userInfo=BmobIM.getInstance().getUserInfo(lists.get(i).getcId());
            if (userInfo != null){
                userInfos.add(userInfo);
            }
        }
        dialogList = new ArrayList<>();
        if (userInfos.isEmpty()){
            return;
        }
        for (int i = 0; i < lists.size(); i++) {
            List<Author>authorList =new ArrayList<>();
            Author author =new Author(userInfos.get(i).getUserId(),userInfos.get(i).getName(), userInfos.get(i).getAvatar(),true);
            authorList.add(author);
            Message message =new Message(lists.get(i).getConversation().getConversationId(),author,lists.get(i).getLastMessageContent());
             DefaultDialog defaultDialog=new DefaultDialog(lists.get(i).getcId(),userInfos.get(i).getName(),userInfos.get(i).getAvatar(),authorList,message,lists.get(i).getUnReadCount());
            dialogList.add(defaultDialog);
        }
        dialogsListAdapter.setItems(dialogList);

    }
    /**
     *获取所有的会话
     * @return
     */

    private List<Conversation> getConversation(){
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation>list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size()>0){
            for (BmobIMConversation item : list){
                switch (item.getConversationType()){
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //添加新朋友会话-获取好友请求表中最新的一条记录
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

}
