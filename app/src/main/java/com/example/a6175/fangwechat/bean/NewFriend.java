package com.example.a6175.fangwechat.bean;

import org.litepal.crud.LitePalSupport;

/**本地的好友请求类
 *
 */
//TODO 好友管理 本地数据库存储添加好友请求
public class NewFriend extends LitePalSupport implements java.io.Serializable{
    private Long id;
    private String userId;
    //用户id
    private String uid;
    //留言信息
    private String msg;
    //用户名
    private String name;
    //头像
    private String avatar;
    //状态: 未读、已读、已添加、已拒绝
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
