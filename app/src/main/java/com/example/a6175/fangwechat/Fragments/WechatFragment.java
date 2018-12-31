package com.example.a6175.fangwechat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a6175.fangwechat.Activity.chatMsg;
import com.example.a6175.fangwechat.Activity.detailInformation;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class WechatFragment extends Fragment implements MessageListHandler {
    private String mTitle;
    private User user;
    private DialogsListAdapter dialogsListAdapter ;
    private ImageLoader imageLoader;
    private List<DefaultDialog>dialogList;
    private DialogsList dialogsList;
    private List<Conversation> lists;


    public static WechatFragment getInstance(String mTitle)
    {
        WechatFragment WF = new WechatFragment();
        WF.mTitle = mTitle;
        return WF;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wechatfragment,null);
        user = BmobUser.getCurrentUser(User.class);
        dialogsList =v.findViewById(R.id.dialogsList);
        imageLoader =new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.with(getActivity()).load(url).into(imageView);
            }
        };

        dialogsListAdapter =new DialogsListAdapter(R.layout.item_custom_dialog,imageLoader);
        dialogsList.setAdapter(dialogsListAdapter);
        query();
        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener() {
            @Override
            public void onDialogClick(IDialog dialog) {
                String id = dialog.getId();
                BmobIMUserInfo info = new BmobIMUserInfo(dialog.getId(),dialog.getDialogName(),dialog.getDialogPhoto());
                //开启私聊会话，设置会话保存在本地会话表中
                BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                    @Override
                    public void done(BmobIMConversation bmobIMConversation, BmobException e) {
                        if (e == null){
                            Intent intent = new Intent(getActivity(),chatMsg.class);
                            //intent.putExtra("User_data",friend);
                            intent.putExtra("c",bmobIMConversation);
                            startActivity(intent);
                        }
                    }
                });


            }
        });
        return v;
    }

    public void updateUserInfo(MessageEvent event){
        final BmobIMConversation conversation=event.getConversation();
        final BmobIMUserInfo info =event.getFromUserInfo();
        final BmobIMMessage msg =event.getMessage();
        String username = info.getName();
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

    }

    /**
     * 查询本地会话
     */
    public void query(){
        lists = getConversation();
        dialogList = new ArrayList<>();
        for (Conversation list:lists){
            Author author =new Author(list.getConversation().getConversationId(),list.getConversation().getConversationTitle(),
                    list.getConversation().getConversationIcon(),true);
            List<Author>authorList =new ArrayList<>();
            authorList.add(author);
            Message message =new Message(list.getConversation().getConversationId(),author,list.getLastMessageContent());
            DefaultDialog defaultDialog=new DefaultDialog(list.getcId(),list.getcName(),list.getConversation().getConversationIcon(),authorList,message,list.getUnReadCount());
            dialogList.add(defaultDialog);
        }
        dialogsListAdapter.setItems(dialogList);

    }

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
