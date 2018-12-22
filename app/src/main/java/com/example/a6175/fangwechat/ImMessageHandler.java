package com.example.a6175.fangwechat;

import com.example.a6175.fangwechat.Activity.MainActivity;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
*  注册消息接收器
*
 * */


public class ImMessageHandler extends BmobIMMessageHandler {

    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        super.onMessageReceive(messageEvent);

        //当接收到服务器发来的消息时，此方法被调用。

    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}
