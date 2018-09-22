package com.inimintech.ktu.data;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */
public class Chat {

    private String userKey;
    private String userId;
    private String message;
    private Long sentTime;
    private List<String> ratedUsers;

    public Chat(String userKey, String userId, String message, Long sentTime) {
        this.userKey = userKey;
        this.userId = userId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public Chat() {
    }

    public String getUserKey() { return userKey; }

    public void setUserKey(String userKey) { this.userKey = userKey; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSentTime() {
        return sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
    }

    public String getKey(){
        if(TextUtils.isEmpty(userId) || sentTime == null)
            return null;
        return userId+"_"+String.valueOf(sentTime);
    }

    public String getMsgKey(){
        if(TextUtils.isEmpty(userId) || message == null)
            return null;
        return userId+"_"+message;
    }

    public List<String> getRatedUsers() {
        if(ratedUsers == null)
            this.ratedUsers = new ArrayList<>();
        return ratedUsers;
    }

    public void setRatedUsers(List<String> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }

    @Override
    public boolean equals(Object obj) {
        Chat ch = (Chat) obj;
        if(ch.getMsgKey().equals(this.getMsgKey()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", sentTime=" + sentTime +
                ", ratedUsers=" + ratedUsers +
                '}';
    }
}
