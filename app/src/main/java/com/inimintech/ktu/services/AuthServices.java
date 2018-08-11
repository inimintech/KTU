package com.inimintech.ktu.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthServices {
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public static String getUid(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
            return user.getUid();
        return null;
    }

}
