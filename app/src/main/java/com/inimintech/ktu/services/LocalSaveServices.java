package com.inimintech.ktu.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.data.Discussion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.inimintech.ktu.ChatActivity.adapter;

public class LocalSaveServices {
    private static final String TAG = "Shared Pref";

    public void saveLocalString(String ID, String Data,Context activity) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //String id =  d.getTopic()+"_"+d.getStartTime();
        Log.d(TAG,"ID: " + ID);
        Log.d(TAG,"Data: " + Data);
        // get storage key
        editor.putString(ID, Data);
        editor.commit();
        Log.d(TAG,"Data has been saved");
    }

    public static String getLocalString(String id,Context activity){
        // do the reverse operation
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        String Data = sharedPreferences.getString(id, "");
        return Data;
    }

    public void saveLocalTopiclist(String id, Discussion diss, Context activity){
        String listID="Topics";
        Map<String, Discussion> TopicList = new HashMap<>();
        Gson gson = new Gson();
        String stringTopicsMap = getLocalString(listID,activity);
        if(stringTopicsMap != null) {
                TopicList = gson.fromJson(stringTopicsMap, new TypeToken<Map<String, Discussion>>() {
            }.getType());
        }
        TopicList.put(id, diss);
        stringTopicsMap = gson.toJson(TopicList);
        saveLocalString(listID,stringTopicsMap,activity);
    }

    public static Map<String, Discussion> getLocalTopicList(Context activity){
        String listID="Topics";
        String stringTopicsMap = getLocalString(listID,activity);
        Gson gson = new Gson();
        Map<String, Discussion> TopicList = new HashMap<>();
        TopicList = gson.fromJson(stringTopicsMap, new TypeToken<Map<String, Discussion>>() {
        }.getType());
        return TopicList;
    }

    public void saveLocalmChat(String id, Context activity){
        Gson gson = new Gson();
        String mchatJson = gson.toJson(adapter.getmChats());
        saveLocalString(id,mchatJson,activity);
    }

    public List<Chat> getLocalmChat(String id,Context activity){
        Gson gson = new Gson();
        // do the reverse operation
        String mchatJson = getLocalString(id,activity);
        List<Chat> chatList = gson.fromJson(mchatJson,new TypeToken<ArrayList<Chat>>(){}.getType());
        Log.d(TAG,"List chat: "+chatList.toString());
        return chatList;
    }
}
