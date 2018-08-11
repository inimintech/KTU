package com.inimintech.ktu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.services.AuthServices;

import java.util.Date;
import java.util.List;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatActivity extends AppCompatActivity {
    private Button sendBtn;
    private EditText msg;
    private ChatAdapter adapter;
    private  RecyclerView rvChats;
    private Chat chat;

    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializeActivity();
        initializeClickEvents();
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
                    chat = new Chat(AuthServices.getUid(),
                            msg.getText().toString(), new Date().getTime());

                    ourdb.collection("Chats").document(chat.getKey())
                            .set(chat)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.getId());
                                    adapter.addChat(chat);
                                    adapter.notifyItemInserted(adapter.getItemCount()-1);
                                    rvChats.smoothScrollToPosition(adapter.getItemCount()-1);
                                }
                            });



                    msg.setText("");
                }
            }
        });
    }



}
