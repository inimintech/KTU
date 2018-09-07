package com.inimintech.ktu.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.inimintech.ktu.ChatActivity;
import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.services.FirestoreServices;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;

    private static final String TAG = ChatFragment.class.getName();
    private LinearLayout topicsList;
    private Map<String, Discussion> discussions;


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

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_chat, container, false);
        topicsList = root.findViewById(R.id.topicsList);

        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        swipeLayout.setRefreshing(true);

        loadTopics();

        return root;

    }

    private void loadTopics(){
        topicsList.removeAllViews();
        long val = new Date().getTime();
        Log.d(TAG, String.valueOf(val));
        discussions = new HashMap<>();
        FirestoreServices.ourdb.collection("Discussions")
                .whereGreaterThan("endTime", new Date().getTime())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Discussion d = document.toObject(Discussion.class);
                        discussions.put(document.getId(), d);
                        topicsList.addView(getView(topicsList, d, document.getId()));
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    swipeLayout.setRefreshing(false);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }

    public View getView(ViewGroup parent, Discussion d, String tag){
        LayoutInflater li = LayoutInflater.from(getContext());
        View inflate = li.inflate(R.layout.item_topics, parent, false);
        inflate.setTag(tag);

        TextView topicName = (TextView) inflate.findViewById(R.id.textViewName);
        TextView startTime = (TextView) inflate.findViewById(R.id.textViewVersion);
        topicName.setText(d.getTopic());
        startTime.setText(String.valueOf(new Date(d.getEndTime())));

        inflate.setOnClickListener(clickListerner());
        return inflate;
    }


    public View.OnClickListener clickListerner(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,view.getTag().toString());
                System.out.println(view.getTag().toString());
                Discussion d = discussions.get(view.getTag().toString());
                Intent i = new Intent(getActivity(), ChatActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("discussion", d);
                i.putExtra("discussionKey", view.getTag().toString());
                i.putExtra("discussionName", d.getTopic());
                i.putExtras(b);
                startActivity(i);
            }
        };
    }


    @Override
    public void onRefresh() {

        loadTopics();
    }
}
