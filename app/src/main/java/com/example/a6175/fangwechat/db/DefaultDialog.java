package com.example.a6175.fangwechat.db;

import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.List;

public class DefaultDialog implements IDialog<Message> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private List<Author> users;
    private Message lastMessage;

    private int unreadCount;

    public DefaultDialog(String id, String name, String photo,
                         List<Author> users, Message lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<Author> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
