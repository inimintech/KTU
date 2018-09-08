package com.inimintech.ktu;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inimintech.ktu.activity.Main2Activity;
import com.inimintech.ktu.adaptor.ChatAdapter;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.helper.ChatActivityHelper;
import com.inimintech.ktu.services.AuthServices;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getName();
    private Button sendBtn;
    private EditText msg;
    private TextView timer;
    public static ChatAdapter adapter;
    public static RecyclerView rvChats;
    private Chat chat;
    private ChatActivityHelper chatActivityHelper;
    private Discussion d;

    private CountDownTimer cTimer = null;
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            Toast.makeText(getApplicationContext(), "Discussion time is over", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String key = getIntent().getStringExtra("discussionKey");
        String topic = getIntent().getStringExtra("discussionName");
        d = (Discussion) getIntent().getExtras().get("discussion");
        Log.d(TAG,d.toString());
        getSupportActionBar().setTitle(topic);
        if(TextUtils.isEmpty(key))
            chatActivityHelper = ChatActivityHelper.INSTANCE;
        else
            chatActivityHelper = ChatActivityHelper.getInstance(key);
        initializeActivity();
    }

    @Override
    protected void onStart(){
        super.onStart();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        adapter = new ChatAdapter();
        rvChats.setAdapter(adapter);
        rvChats.setLayoutManager(manager);

        initializeClickEvents();
        chatActivityHelper.reset();
        chatActivityHelper.startListener();
        long currentTime = new Date().getTime();
        startScheduler(currentTime);
        startTimer(d.getEndTime() - currentTime);
    }

    private void startScheduler(long currentTime) {
        if(d.getEndTime() > currentTime) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    timerMethod();
                }
            }, new Date(d.getEndTime()));
        }else{
            Intent i = new Intent(this, Main2Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    private void timerMethod() {  this.runOnUiThread(Timer_Tick);   }

    @Override
    protected void onResume() {  super.onResume();   }

    @Override
    protected void onStop(){   super.onStop();   }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        adapter = null;
        rvChats = null;
    }

    public void initializeActivity(){

        sendBtn = findViewById(R.id.button_chatbox_send);
        msg = findViewById(R.id.edittext_chatbox);
        timer = findViewById(R.id.timer);
        rvChats = (RecyclerView) findViewById(R.id.reyclerview_list);

    }

    private void initializeClickEvents() {   sendMessgeBtnClickEvent();   }

    private void sendMessgeBtnClickEvent() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(msg.getText())){
                    chat = new Chat(AuthServices.UID,
                            msg.getText().toString(), new Date().getTime());
                    chatActivityHelper.saveToDB(chat);
                    msg.setText("");
                    sendBtn.setClickable(false);

                    new Handler().postDelayed(mRunnable, 5000);
                }
            }
        });
    }


    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            sendBtn.setClickable(true);
        }
    };

    void startTimer(long diff) {
        if(cTimer!=null)
            cTimer.cancel();

        cTimer = new CountDownTimer(diff, 1000) {
            public void onTick(long millis) {
                long min = (millis / 1000)  / 60;
                long sec = (millis / 1000) % 60;
                String minutes = min < 10 ? "0"+min : ""+min;
                String seconds= sec < 10 ? "0"+sec : ""+sec;
                String rem = minutes+":"+seconds;
                timer.setText(rem);
            }
            public void onFinish() {
            }
        };
        cTimer.start();
    }

}
