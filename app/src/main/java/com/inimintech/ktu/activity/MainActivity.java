package com.inimintech.ktu.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.inimintech.ktu.R;
import com.inimintech.ktu.fragments.ChatFragment;

/*
* @author      Bathire Nathan
* @Created on  11 Aug 2018
* */

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.container_main, ChatFragment.newInstance());
                    break;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.container_main, ChatFragment.newInstance());
                    break;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.container_main, ChatFragment.newInstance());
                    break;
            }
            transaction.commit();
            return true;

            //Used to select an item programmatically
            //bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setDefaultTransaction(navigation, 1);
    }

    private void setDefaultTransaction(BottomNavigationView navigation, int menuIndex) {
        navigation.getMenu().getItem(menuIndex).setChecked(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main, ChatFragment.newInstance());
        transaction.commit();
    }

}
