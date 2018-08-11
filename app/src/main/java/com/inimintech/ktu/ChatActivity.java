package com.inimintech.ktu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.services.AuthServices;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getName();
    private Button sendBtn;
    private EditText msg;
    private ChatAdapter adapter;
    private  RecyclerView rvChats;
    private Chat chat;

    private static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();
    private CollectionReference colRef = ourdb.collection("chats");

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

        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Chat chat = dc.getDocument().toObject(Chat.class);
                            addToView(chat);
                            Log.d(TAG, "New city: " + dc.getDocument().getData());
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified city: " + dc.getDocument().getData());
                            break;
                        case REMOVED:
                            Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                            break;
                    }
                }
            }
        });
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
                   saveToDB();
                    msg.setText("");
                }
            }
        });
    }

    private void saveToDB() {
        colRef.add(chat)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        });
    }

    private void addToView(Chat chat) {
        adapter.addChat(chat);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        rvChats.smoothScrollToPosition(adapter.getItemCount()-1);
    }


}
