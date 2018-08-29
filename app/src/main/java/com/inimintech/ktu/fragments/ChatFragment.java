package com.inimintech.ktu.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.inimintech.ktu.ChatActivity;
import com.inimintech.ktu.R;
import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;

import java.util.List;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatFragment extends Fragment {

    private LinearLayout topicsList;


    public ChatFragment() {
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_chat, container, false);

        topicsList = root.findViewById(R.id.topicsList);

        topicsList.addView(createViews(topicsList));
        topicsList.addView(createViews(topicsList));
        topicsList.addView(createViews(topicsList));
        topicsList.addView(createViews(topicsList));


        return root;

    }

    public View createViews(ViewGroup parent){
        LayoutInflater li = LayoutInflater.from(getContext());
        View inflate = li.inflate(R.layout.item_recieved, parent, false);
        return inflate;
    }

    public void showHideOnGoingData(){

    }


}
