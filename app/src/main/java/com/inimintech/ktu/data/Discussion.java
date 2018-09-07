package com.inimintech.ktu.data;

import java.io.Serializable;

public class Discussion implements Serializable{

    private String topic;

    private long startTime;

    private long endTime;

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

    @Override
    public String toString() {
        return "Discussion{" +
                "topic='" + topic + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
