package com.inimintech.ktu.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.inimintech.ktu.ChatActivity;
import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.services.AuthServices;

import javax.annotation.Nullable;

public class ChatActivityHelper {

    private static final String TAG = ChatActivity.class.getName();
    private static FirebaseFirestore ourdb = FirebaseFirestore.getInstance();
    public CollectionReference colRef ;

    public static final ChatActivityHelper INSTANCE = new ChatActivityHelper();

    private ChatActivityHelper(){
        this.colRef= ourdb.collection("chats");
    }

    private ChatActivityHelper(String collectionName){
        this.colRef= ourdb.collection(collectionName);
    }

    public static ChatActivityHelper getInstance(String collectionName){
        return new ChatActivityHelper(collectionName);
    }

    public void saveToDB(final Chat chat) {
        colRef.document(chat.getKey()).set(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "DocumentSnapshot added with ID: " + chat.getKey());
                }
            }
        });
    }

    public void startListener() {

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

    private void addToView(Chat chat) {
        ChatAdapter adapter = ChatActivity.adapter;
        if (adapter.getmChats().size() == 0 ||
                !adapter.getmChats().get(adapter.getmChats().size() - 1).equals(chat)) {
            adapter.addChat(chat);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            ChatActivity.rvChats.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }

    public void setLike(Chat chat){
        if(chat.getRatedUsers().contains(AuthServices.UID))
            chat.getRatedUsers().remove(AuthServices.UID);
        else
            chat.getRatedUsers().add(AuthServices.UID);

        colRef.document(chat.getKey())
                .set(chat, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Log.d(TAG, "updated Successfully");
            }
        });
    }

}
