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
import android.view.View;

import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.helper.ChatActivityHelper;
import com.inimintech.ktu.services.LocalSaveServices;

public class HistoryChatActivity extends AppCompatActivity {
    private static final String TAG = HistoryChatActivity.class.getName();
    private ChatActivityHelper chatActivityHelper;
    public static RecyclerView rvChats;
    public static ChatAdapter adapter;
    private Discussion d;
    private String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_chat);
        key = getIntent().getStringExtra("discussionKey");
        String topic = getIntent().getStringExtra("discussionName");
        d = (Discussion) getIntent().getExtras().get("discussion");
        Log.d(TAG,d.toString());
        getSupportActionBar().setTitle(topic);
        if(TextUtils.isEmpty(key))
            chatActivityHelper = ChatActivityHelper.INSTANCE;
        else
            chatActivityHelper = ChatActivityHelper.getInstance(key);
        initializeActivity();
        setAdapterAndView();
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
        LocalSaveServices localServicOobj = new LocalSaveServices();
        localServicOobj.getLocalmChat(key,this);
        rvChats.setAdapter(adapter);
        rvChats.setLayoutManager(manager);
        chatActivityHelper.startListener();
    }

    public void initializeActivity(){

        rvChats = (RecyclerView) findViewById(R.id.reyclerview_list);
        rvChats.setHasFixedSize(true);

    }

}
