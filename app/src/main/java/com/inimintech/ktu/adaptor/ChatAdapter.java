package com.inimintech.ktu.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Chat;
import com.inimintech.ktu.services.AuthServices;

import java.util.ArrayList;
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
    }

    public void addChat(Chat chat){
        mChats.add(chat);
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Chat chat = mChats.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.msg;
        textView.setText(chat.getMessage());
        TextView textView1 = viewHolder.msgTime;
        textView1.setText(String.valueOf(new Date(chat.getSentTime())));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView msgTime;

        public ViewHolder(View itemView) {
            super(itemView);

            msg = (TextView) itemView.findViewById(R.id.text_message_body);
            msgTime = (TextView) itemView.findViewById(R.id.text_message_time);
        }
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView msgTime;

        public RecViewHolder(View itemView) {
            super(itemView);

            msg = (TextView) itemView.findViewById(R.id.text_message_body);
            msgTime = (TextView) itemView.findViewById(R.id.text_message_time);
        }
    }
}
