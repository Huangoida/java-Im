package com.example.a6175.fangwechat.Utils;

import cn.bmob.newim.bean.BmobIMExtraMessage;

/**
 * 添加好友请求-自定义消息类型
 */
//TODO 好友管理 自定义添加好友的消息类型
public class AddFriendMessage extends BmobIMExtraMessage {
    public AddFriendMessage(){}

    @Override
    public String getMsgType() {
        //自定义一个‘add’的消息类型
        return "add";
    }

    @Override
    public boolean isTransient() {
        //设置为true，表明为暂态消息，这条消息不会保存在本地db中，SDK是负责发送出去
        return true;
    }
}
