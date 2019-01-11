package com.example.a6175.fangwechat.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.a6175.fangwechat.view.Activity.chatMsg;
import com.example.a6175.fangwechat.R;

import java.util.List;


import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMConversationType;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;


/**
 * 私聊会话
 * Created by Administrator on 2016/5/25.
 */

public class PrivateConversation extends AbstractConversation {



    private BmobIMMessage lastMsg;

    public PrivateConversation(BmobIMConversation conversation){
        this.conversation = conversation;
        cType = BmobIMConversationType.setValue(conversation.getConversationType());
        cId = conversation.getConversationId();
        if (cType == BmobIMConversationType.PRIVATE){
            cName=conversation.getConversationTitle();
            if (TextUtils.isEmpty(cName)) {cName = cId;}
        }else{
            cName="未知会话";
        }
        List<BmobIMMessage> msgs =conversation.getMessages();
        if(msgs!=null && msgs.size()>0){
            lastMsg =msgs.get(0);
        }
    }

    @Override
    public void readAllMessages() {
        conversation.updateLocalCache();
    }

    @Override
    public Object getAvatar() {
        if (cType == BmobIMConversationType.PRIVATE){
            String avatar =  conversation.getConversationIcon();
            //头像为空，使用默认头像
            if (TextUtils.isEmpty(avatar)){
                return R.drawable.default_avatar;
            }else{
                return avatar;
            }
        }else{
            return R.drawable.default_avatar;
        }
    }

    @Override
    public String getLastMessageContent() {
        if(lastMsg!=null){
            String content =lastMsg.getContent();
            if(lastMsg.getMsgType().equals(BmobIMMessageType.TEXT.getType()) || "agree".equals(lastMsg.getMsgType())){
                return content;
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
                return "[图片]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.VOICE.getType())){
                return "[语音]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.LOCATION.getType())){
                return"[位置]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.VIDEO.getType())){
                return "[视频]";
            }else{
                return "[未知]";
            }
        }else{//防止消息错乱
            return "";
        }
    }

    @Override
    public long getLastMessageTime() {
        if(lastMsg!=null) {
            return lastMsg.getCreateTime();
        }else{
            return 0;
        }
    }

    @Override
    public int getUnReadCount() {
        return (int)BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, chatMsg.class);
        intent.putExtra("c",conversation);
        context.startActivity(intent);
    }

    @Override
    public void onLongClick(Context context) {
        BmobIM.getInstance().deleteConversation(conversation);
    }


}