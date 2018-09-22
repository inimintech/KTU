package com.inimintech.ktu.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inimintech.ktu.ChatActivity;
import com.inimintech.ktu.HistoryChatActivity;
import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.services.LocalSaveServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class SavedDiscussionFragment extends Fragment {
    private LinearLayout historyTopicsList;
    private static final String TAG = SavedDiscussionFragment.class.getName();
    private Map<String, Discussion> discussions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saved_discussion, container, false);
        historyTopicsList = rootView.findViewById(R.id.historyTopicsList);
        loadhistoryTopics();

        return rootView;
    }
    private void loadhistoryTopics(){
        historyTopicsList.removeAllViews();
        discussions = LocalSaveServices.INSTANCES.getLocalTopicList(getContext());
        if(discussions != null){
            for (Map.Entry<String,Discussion> entry : discussions.entrySet()) {
                historyTopicsList.addView(getView(historyTopicsList, entry.getValue(), entry.getKey()));
            }
        }
    }

    public View getView(ViewGroup parent, Discussion d, String tag){
        LayoutInflater li = LayoutInflater.from(getContext());
        View inflate = li.inflate(R.layout.item_topics, parent, false);
        inflate.setTag(tag);

        TextView topicName = (TextView) inflate.findViewById(R.id.textViewName);
        TextView startTime = (TextView) inflate.findViewById(R.id.textViewVersion);
        TextView endTime = (TextView) inflate.findViewById(R.id.endTime);

        Date sDate = new Date(d.getStartTime());
        Date eDate = new Date(d.getEndTime());
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String sTime = "Start Time :"+ sdf.format(sDate);
        String eTime = "End Time :"+sdf.format(eDate);

        topicName.setText(d.getTopic());
        startTime.setText(String.valueOf(sTime));
        endTime.setText(String.valueOf(eTime));

        inflate.setOnClickListener(clickListerner());
        return inflate;
    }


    public View.OnClickListener clickListerner(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,view.getTag().toString());
                System.out.println(view.getTag().toString());
                Discussion d = discussions.get(view.getTag().toString());
                Intent i = new Intent(getActivity(), HistoryChatActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("discussion", d);
                i.putExtras(b);
                startActivity(i);
            }
        };
    }


}
