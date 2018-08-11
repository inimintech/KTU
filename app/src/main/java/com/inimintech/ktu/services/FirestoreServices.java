package com.inimintech.ktu.services;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inimintech.ktu.data.Chat;

/*
 * @author      Calvin Ruben Raj
 * @Created on  11 Aug 2018
 */



public class FirestoreServices {
    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    public static void insertData(String collection, Chat chat) {
        ourdb.collection(collection)
                .add(chat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                });
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        //Log.w(TAG, "Error adding document", e);
//                    }
//                });
    }

}
