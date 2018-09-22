package com.inimintech.ktu.services;


import android.support.annotation.NonNull;
import android.util.Log;

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

    enum CollectionsEnum{
        users, Category, Discussions
    }

    enum Documents{
        categories
    }

    public static Map<String, Object> discussionCategory = null ;

    public static final FirebaseFirestore ourdb = FirebaseFirestore.getInstance();

    public static final DocumentReference CurrentUser = ourdb
            .collection(CollectionsEnum.users.toString())
            .document(AuthServices.UID);

    public static final DocumentReference categoriesRef = ourdb
            .collection(CollectionsEnum.Category.toString())
            .document(Documents.categories.toString());

    public static final CollectionReference topicCollection = ourdb.
            collection(CollectionsEnum.Discussions.toString());

    public static DocumentReference docRefForDiscussion(String documentKey){
        return topicCollection.document(documentKey);
    }
}
