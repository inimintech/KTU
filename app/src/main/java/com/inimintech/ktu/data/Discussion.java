package com.inimintech.ktu.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Discussion implements Serializable{

    private String topic;

    private long startTime;

    private long endTime;

    private String categoryId;

    private List<String> loggedInUsers;

    public Discussion(){

    }

    public Discussion(String topic, long startTime, long endTime){
        this.topic = topic;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCategoryId() {   return categoryId;   }

    public void setCategoryId(String categoryId) {  this.categoryId = categoryId;  }

    public List<String> getLoggedInUsers() { return loggedInUsers;  }

    public void setLoggedInUsers(List<String> loggedInUsers) { this.loggedInUsers = loggedInUsers;   }

    public String getDiscussionId(){ return this.topic+"_"+String.valueOf(startTime); }

    @Override
    public String toString() {
        return "Discussion{" +
                "topic='" + topic + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", categoryId='" + categoryId + '\'' +
                ", loggedInusers=" + loggedInUsers +
                '}';
    }
}
