package com.inimintech.ktu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.helper.ChatActivityHelper;
import com.inimintech.ktu.services.LocalSaveServices;

import java.util.List;

public class HistoryChatActivity extends AppCompatActivity {
    private static final String TAG = HistoryChatActivity.class.getName();
    private ChatActivityHelper chatActivityHelper;
    public  RecyclerView rvChats;
    public  ChatAdapter adapter;
    private Discussion d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_chat);
        d = (Discussion) getIntent().getExtras().get("discussion");
        Log.d(TAG,d.toString());
        getSupportActionBar().setTitle(String.valueOf(d.getTopic()));
        initializeActivity();
        setAdapterAndView();

        List<Chat> mChat = LocalSaveServices.INSTANCES.getLocalmChat(d.getDiscussionId(),this);
        if(mChat != null){
            adapter.setmChats(mChat);
            //adapter.notifyDataSetChanged();
           // rvChats.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        adapter = null;
        rvChats = null;
    }

    private void setAdapterAndView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        adapter = new ChatAdapter();
        rvChats.setAdapter(adapter);
        rvChats.setLayoutManager(manager);
    }

    public void initializeActivity(){

        rvChats = (RecyclerView) findViewById(R.id.reyclerview_list);
        rvChats.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_chat_options, menu);
        return true;
    }

}
