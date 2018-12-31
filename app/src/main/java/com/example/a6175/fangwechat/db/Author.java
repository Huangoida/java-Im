package com.example.a6175.fangwechat.db;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {
    private String Id;
    private String Name;
    private String Avater;
    private boolean online;

    public Author(String id, String name, String avater,boolean online) {
        this.Id = id;
        this.Name = name;
        this.Avater = avater;
        this.online = online;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getAvatar() {
        return Avater;
    }

    public boolean isOnline() {
        return online;
    }
}
