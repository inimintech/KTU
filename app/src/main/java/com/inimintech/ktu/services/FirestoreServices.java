package com.inimintech.ktu.services;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.inimintech.ktu.data.Chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author      Calvin Ruben Raj
 * @Created on  11 Aug 2018
 */



public class FirestoreServices {

    enum CollectionsEnum{
        users, Category, Discussions
    }

    enum Documents{
        categories
    }

    public static String notificationToken = null;

    public static Map<String, Object> discussionCategory = null ;

    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    public static DocumentReference CurrentUser = getCurrentUser(AuthServices.UID);

    private static DocumentReference getCurrentUser(String uid) {
        if(uid == null )
            return  null;
        return ourdb
                .collection(CollectionsEnum.users.toString())
                .document(uid);
    }

    public static final DocumentReference categoriesRef = ourdb
            .collection(CollectionsEnum.Category.toString())
            .document(Documents.categories.toString());

    public static final CollectionReference topicCollection = ourdb.
            collection(CollectionsEnum.Discussions.toString());

    public static DocumentReference docRefForDiscussion(String documentKey){
        return topicCollection.document(documentKey);
    }

    public static void saveUser(final FirebaseUser user) {

        Map<String, String> dets = new HashMap<>();
        dets.put("uid", user.getUid());
        dets.put("email", user.getEmail());
        dets.put("name", user.getDisplayName());
        dets.put("tokenId", "" );
        ourdb.collection(CollectionsEnum.users.toString())
                .document(user.getUid()).set(dets)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("user", "added succesfully");
                        updateTokenId(user);
                    }
                });
    }

    private static void updateTokenId(final FirebaseUser user){
        /*user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()) {
                    notificationToken = task.getResult().getToken();
                    getCurrentUser(user.getUid()).update("tokenId", notificationToken);
                }
            }
        });*/

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                getCurrentUser(user.getUid()).update("tokenId", newToken);
            }
        });
    }
}
