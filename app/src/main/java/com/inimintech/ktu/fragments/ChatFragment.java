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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.inimintech.ktu.ChatActivity;
import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.services.FirestoreServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.inimintech.ktu.services.FirestoreServices.discussionCategory;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;

    private static final String TAG = ChatFragment.class.getName();
    private LinearLayout topicsList, categories;
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
        categories = root.findViewById(R.id.cartegory);

        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        swipeLayout.setRefreshing(true);

        loadCategories();
        loadTopics();

        return root;

    }

    private void loadCategories() {
        if(discussionCategory == null) {
            FirestoreServices.categoriesRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        discussionCategory = task.getResult().getData();
                        addViewForCategories();
                        Log.d("Loading Categories", discussionCategory.toString());
                    }
                }
            });
        }else{
            /*for(String key : discussionCategory.keySet()){
                Log.d("keyyy", key);
            }*/
            addViewForCategories();
        }
    }

    private void addViewForCategories() {
        for(String key : discussionCategory.keySet()){
            categories.addView(getCategeoriesView(key,
                    discussionCategory.get(key).toString()));
        }
    }



    private View getCategeoriesView(String key, String value){
        LayoutInflater li = LayoutInflater.from(getContext());
        View inflate = li.inflate(R.layout.item_categories, categories, false );
        inflate.setTag(key);
        TextView tv = (TextView) inflate.findViewById(R.id.cartegory_names);
        tv.setText(value);
        return inflate;
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
        TextView endTime = (TextView) inflate.findViewById(R.id.endTime);

        Date sDate =new Date(d.getStartTime());
        Date eDate =new Date(d.getEndTime());
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String sTime = "Start Time :"+ sdf.format(sDate);
        String eTime = "End Time :"+sdf.format(eDate);

        topicName.setText(d.getTopic());
        startTime.setText(String.valueOf(sTime));
        endTime.setText(String.valueOf(eTime));

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
