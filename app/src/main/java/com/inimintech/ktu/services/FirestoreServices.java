package com.inimintech.ktu.services;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inimintech.ktu.data.Chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author      Calvin Ruben Raj
 * @Created on  11 Aug 2018
 */



public class FirestoreServices {
    private static Map<String, Object> discussionCategory = null;

    enum Collections{
        Category, Discussions
    }

    enum Documents{
        categories
    }

    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    public static final DocumentReference CurrentUser = ourdb.
            collection("users").document(AuthServices.UID);

    public static final Map<String, Object> CATEGORY = loadCat();

    private static Map<String,Object> loadCat() {
        if (discussionCategory == null)
            getAllCategories();
        return discussionCategory;
    }


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

    public static CollectionReference getTopicCollection(){
        return ourdb.collection("Discussions");
    }

    /*public static final void getAllCategories(){
        ourdb.collection("Category").get("OI4GGIypduTiKsMgGMLZ");
    }
*/

    private static void getAllCategories() {
        ourdb.collection(String.valueOf(Collections.Category))
                .document(String.valueOf(Documents.categories))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        discussionCategory =  document.getData();
                    }

                }
            }
        });
    }
}
