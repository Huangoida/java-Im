package com.example.a6175.fangwechat.db;

import android.speech.tts.Voice;
import android.support.annotation.Nullable;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class Message implements IMessage,MessageContentType.Image,MessageContentType {

    private String Id;
    private String Text;
    private Author author;
    private Date createdAt;
    private Image image;
    private Voice voice;


    public Message(String id, Author user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, Author user, String text, Date createdAt) {
        this.Id = id;
        this.Text = text;
        this.author = user;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public String getText() {
        return Text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public Author getUser() {
        return this.author;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.Text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }
}