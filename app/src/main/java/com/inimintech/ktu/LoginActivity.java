package com.inimintech.ktu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inimintech.ktu.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && !TextUtils.isEmpty(user.getUid())) {
            System.out.println(user.getUid());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("uid", user.getUid());
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMobile = findViewById(R.id.editTextMobile);
        mAuth = FirebaseAuth.getInstance();

        editTextMobile.setText(getMyPhoneNO());

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, VerifyPhnoActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });

    }

    private String getMyPhoneNO() {
        try {
            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            return tMgr.getLine1Number();
        }catch (Exception e){
            return "";
        }
    }


}
