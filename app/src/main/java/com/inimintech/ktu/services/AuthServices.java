package com.inimintech.ktu.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * @author      Bathire Nathan
 * @Created on  12 Aug 2018
 */
public class AuthServices {

    public static final AuthServices INSTANCE = new AuthServices();
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final String UID = INSTANCE.getUid();

    private AuthServices(){
    }

    private String getUid(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
            return user.getUid();
        return null;
    }

}
