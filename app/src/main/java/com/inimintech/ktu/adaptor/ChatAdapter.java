package com.inimintech.ktu.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.helper.ChatActivityHelper;
import com.inimintech.ktu.services.AuthServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/*
 * @author      Bathire Nathan
 * @Created on  11 Aug 2018
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Chat> mChats;

    public ChatAdapter() {
        mChats = new ArrayList<>();
    }

    public ChatAdapter(List<Chat> chats) {
        mChats = chats;
        sort();
    }

    public void addChat(Chat chat) {
        mChats.add(chat);
        sort();
    }

    private void sort(){
        Collections.sort(mChats, new Comparator<Chat>() {
            @Override
            public int compare(Chat chat, Chat t1) {
                if(chat.getSentTime() > t1.getSentTime())
                    return 1;
                return -1;
            }
        });
    }

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    @Override
    public int getItemViewType(int position) {
        Chat message = mChats.get(position);
        if (message.getUserId().equals(AuthServices.UID)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            View contactView = inflater.inflate(R.layout.item_chat, parent, false);
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            View contactView = inflater.inflate(R.layout.item_recieved, parent, false);
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Chat chat = mChats.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.msg;
        textView.setText(chat.getMessage());
        TextView textView1 = viewHolder.msgTime;
        DateFormat time = new SimpleDateFormat("hh:mm a");
        textView1.setText(String.valueOf(time.format(new Date(chat.getSentTime()))));
        if(viewHolder.getItemViewType() == VIEW_TYPE_MESSAGE_RECEIVED){
            final Button likeBtn = viewHolder.likeBtn;
            final AlphaAnimation alhpaAnim = new AlphaAnimation(1F, 0.8F);
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(alhpaAnim);
                    System.out.println(position);
                    if(mChats.get(position).getRatedUsers().contains(AuthServices.UID))
                        likeBtn.setBackgroundResource(R.drawable.like);
                    else
                        likeBtn.setBackgroundResource(R.drawable.liked);
                    ChatActivityHelper.INSTANCE.setLike(mChats.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    public List<Chat> getmChats() {
        return mChats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView msgTime;
        public Button likeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            msg = (TextView) itemView.findViewById(R.id.text_message_body);
            msgTime = (TextView) itemView.findViewById(R.id.text_message_time);
            likeBtn = (Button) itemView.findViewById(R.id.likeMsg);

        }

    }
}
