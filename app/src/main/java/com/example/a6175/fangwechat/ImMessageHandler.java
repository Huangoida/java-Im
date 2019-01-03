package com.example.a6175.fangwechat;

import android.content.Context;
import android.util.Log;

import com.example.a6175.fangwechat.bean.User;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.v3.BmobUser;

/**
*  注册消息接收器
*
 * */


public class ImMessageHandler extends BmobIMMessageHandler {

    private Context context;
    private User user;

    public ImMessageHandler(Context context){
        this.context = context;
    }

    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        excuteMessage(messageEvent);

        //当接收到服务器发来的消息时，此方法被调用。

    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        Map<String,List<MessageEvent>> map = offlineMessageEvent.getEventMap();
        Log.d("receive", map.size()+"个用户");
        for (Map.Entry<String, List<MessageEvent>> entry :map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            int size =list.size();
            for (int i = 0; i < size; i++) {
                excuteMessage(list.get(i));
            }

        }
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }

    private void excuteMessage(final  MessageEvent event){
        user =BmobUser.getCurrentUser(User.class);
        BmobIMMessage msg =event.getMessage();
        EventBus.getDefault().post(event);
    }
}
