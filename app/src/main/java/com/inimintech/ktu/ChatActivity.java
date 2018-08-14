package com.inimintech.ktu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.helper.ChatActivityHelper;
import com.inimintech.ktu.services.AuthServices;

import java.util.Date;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getName();
    private Button sendBtn;
    private EditText msg;
    public static ChatAdapter adapter;
    public static RecyclerView rvChats;
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializeActivity();

    }

    @Override
    protected void onStart(){
        super.onStart();
        initializeClickEvents();
        ChatActivityHelper.INSTANCE.startListener();

    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter = null;
        rvChats = null;
    }

    public void initializeActivity(){
        sendBtn = findViewById(R.id.button_chatbox_send);
        msg = findViewById(R.id.edittext_chatbox);
        rvChats = (RecyclerView) findViewById(R.id.reyclerview_list);
        adapter = new ChatAdapter();
        rvChats.setAdapter(adapter);
        rvChats.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeClickEvents() {
        sendMessgeBtnClickEvent();
    }

    private void sendMessgeBtnClickEvent() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(msg.getText())){
                    chat = new Chat(AuthServices.UID,
                            msg.getText().toString(), new Date().getTime());
                   ChatActivityHelper.INSTANCE.saveToDB(chat);
                    msg.setText("");
                }
            }
        });
    }


}
