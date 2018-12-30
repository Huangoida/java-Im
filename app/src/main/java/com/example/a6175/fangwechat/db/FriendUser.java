package com.example.a6175.fangwechat.db;

import cn.bmob.v3.BmobObject;


/**
 * 好友数据
 */
public class FriendUser extends BmobObject {
    private User user;
    private User friendUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }
}
