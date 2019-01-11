package com.example.a6175.fangwechat.model;

import android.util.Log;

import com.example.a6175.fangwechat.Listener.BaseListener;
import com.example.a6175.fangwechat.Utils.ActivityUtils;
import com.example.a6175.fangwechat.bean.AddFriendMessage;
import com.example.a6175.fangwechat.bean.FriendUser;
import com.example.a6175.fangwechat.bean.NewFriend;
import com.example.a6175.fangwechat.bean.User;
import com.example.a6175.fangwechat.view.Activity.addFriendList;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMExtraMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static com.example.a6175.fangwechat.Config.STATUS_VERIFY_NONE;

/**
 * 同意添加好友请求-仅仅只用于发送同意添加好友的消息
 */

public class AgreeAddFriendMessage extends BmobIMExtraMessage {
    //最初的发送方
    //以下均是从extra里面抽离出来的字段，方便获取
    private String uid;
    private Long time;
    //用于通知栏显示的内容
    private String msg;
    private static BaseListener listener;

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
    public static void sendAddFriendMessage(User user){
        //TODO 添加要加好友的对象
        BmobIMUserInfo info=new BmobIMUserInfo(user.getObjectId(),user.getNickname(),user.getAvater().getFileUrl());
        //创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info,true,null);
        // 根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(),conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方一个留言消息
        Map<String,Object>map = new HashMap<>();
        map.put("name",currentUser.getNickname());//发送者的姓名
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
    public static void snedAgreeAddFriendMEssage(final NewFriend add,final BaseListener listener){
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(),add.getName(),add.getAvatar());
        //创建一个暂态会话入口，发送同意好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info,true,null);
        // 根据会话如空获取消息管理，发送同意好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(),conversationEntrance);
        //AgreeAddFriendMessage 的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        AgreeAddFriendMessage msg = new AgreeAddFriendMessage();
        final User currentUser = BmobUser.getCurrentUser(User.class);
        //这句话直接存储到对方的消息表中
        msg.setContent("我通过了你的好友请求，我们可以开始聊天了!");
        Map<String, Object> map = new HashMap<>();
        //显示在通知栏上面的内容
        map.put("msg", currentUser.getUsername() + "同意添加你为好友");
        //发送者的uid-方便请求添加的发送方找到该条添加好友的请求
        map.put("uid", add.getUid());
        msg.setExtraMap(map);
        LitePal.delete(NewFriend.class,add.getId());
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                //发送成功
                if (e == null){
                    BmobQuery<User>userBmobQuery=new BmobQuery<>();
                    userBmobQuery.getObject(add.getUid(), new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            addfriend(user,listener);
                        }
                    });
                }else {
                    Log.d("addFriend",e.getMessage());
                    ActivityUtils.showShortToast(addFriendList.getContext(),e.getMessage());
                    listener.Failure(e.getMessage());
                }
            }
        });
    }

    private static void addfriend(final User user,final BaseListener listener){
        final FriendUser friendUser = new FriendUser();
        final User UserNow = BmobUser.getCurrentUser(User.class);
        friendUser.setUser(UserNow);
        friendUser.setFriendUser(user);
        friendUser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    friendUser.setFriendUser(UserNow);
                    friendUser.setUser(user);
                    friendUser.save(new SaveListener<String>() {//双向存储
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                listener.Success();
                            }else{
                                listener.Failure(e.getMessage());
                            }
                        }
                    });
                }else {
                    listener.Failure(e.getMessage());
                }
            }
        });
    }

    public static void processCustiomMessage(BmobIMMessage msg,BmobIMUserInfo info){
        //消息类型
        try {
            String extra = msg.getExtra();
            JSONObject json =new JSONObject(extra);
            String uid = json.getString("uid");
            NewFriend newFriend = new NewFriend();
            newFriend.setUserId(msg.getToId());
            newFriend.setUid(json.getString("uid"));
            newFriend.setName(json.getString("name"));
            newFriend.setAvatar(info.getAvatar());
            newFriend.setStatus(STATUS_VERIFY_NONE);
            newFriend.setMsg(msg.getContent());
            newFriend.save();
        } catch (JSONException e) {
            Logger.e(e.getMessage());
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
