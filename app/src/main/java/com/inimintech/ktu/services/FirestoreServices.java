package com.inimintech.ktu.services;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inimintech.ktu.data.Chat;

import java.util.List;
import java.util.Map;

/*
 * @author      Calvin Ruben Raj
 * @Created on  11 Aug 2018
 */



public class FirestoreServices {
    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    public static final DocumentReference CurrentUser = ourdb.
            collection("users").document(AuthServices.UID);

    public static final Map<String, String> discussionCategory = getAllCategories();



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

    private static Map<String,String> getAllCategories() {

        return null;
    }
}
