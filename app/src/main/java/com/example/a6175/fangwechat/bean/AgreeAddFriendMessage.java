package com.example.a6175.fangwechat.bean;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMExtraMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 同意添加好友请求-仅仅只用于发送同意添加好友的消息
 */

public class AgreeAddFriendMessage extends BmobIMExtraMessage {

    //以下均是从extra里面抽离出来的字段，方便获取
    private String uid;//最初的发送方
    private Long time;
    private String msg;//用于通知栏显示的内容

    @Override
    public String getMsgType() {
        return "agree";
    }

    @Override
    public boolean isTransient() {
        return false;
    }

    /**
     * 发送添加好友的请求
     */
    //TODO 好友管理 发送添加好友请求
    private void sendAddFriendMessage(){
        //TODO 添加要加好友的对象
        BmobIMUserInfo info=new BmobIMUserInfo();
        //创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info,true,null);
        // 根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(),conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方一个留言消息
        //TODO 这里只是举个例子，可以不需要发送者的信息过去
        Map<String,Object>map = new HashMap<>();
        map.put("name",currentUser.getUsername());//发送者的姓名
        map.put("avater", currentUser.getAvater());//发送者的头像
        map.put("uid",currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null){
                    Log.d("addfriend","好友请求发送成功，等待验证");
                }    else{
                    Log.d("addfriend","好友请求发送失败"+e.getMessage());
                }
            }
        });


    }

    /**
     * 发送同意添加好友的消息
     */
    //TODO 好友管理 发送同意添加好友
    private void snedAgreeAddFriendMEssage(final NewFriend add, final SaveListener<Object>listener){
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(),add.getName(),add.getAvatar());
        //创建一个暂态会话入口，发送同意好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info,true,null);
        // 根据会话如空获取消息管理，发送同意好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(),conversationEntrance);
        //AgreeAddFriendMessage 的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        AgreeAddFriendMessage msg = new AgreeAddFriendMessage();
        final User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("我通过了你的好友请求，我们可以开始聊天了!");//这句话直接存储到对方的消息表中
        Map<String, Object> map = new HashMap<>();
        map.put("msg", currentUser.getUsername() + "同意添加你为好友");//显示在通知栏上面的内容
        map.put("uid", add.getUid());//发送者的uid-方便请求添加的发送方找到该条添加好友的请求
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null){//发送成功
                    //修改本地的好友请求记录
                    //TODO 这里有个NewFirend的

                }else {
                    Log.d("addFriend",e.getMessage());
                }
            }
        });
    }

    private void processCustiomMessage(BmobIMMessage msg,BmobIMUserInfo info){
        //消息类型
        String type = msg.getMsgType();

        if (type.equals("add")){ // 接收到的添加好友的请求

        }


    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
