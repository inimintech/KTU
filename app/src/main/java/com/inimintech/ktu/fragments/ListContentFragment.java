package com.inimintech.ktu.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.inimintech.ktu.R;
import com.inimintech.ktu.data.Discussion;
import com.inimintech.ktu.services.FirestoreServices;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ListContentFragment extends Fragment{
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private EditText topic, startTime, endTime;
    private Button genBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_content, container, false);
        initialize(rootView);

        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date start = parseDateTime(String.valueOf(startTime.getText()));
                Date end = parseDateTime(String.valueOf(endTime.getText()));
                String stopic = String.valueOf(topic.getText());
                Discussion d = new Discussion(stopic, start.getTime(), end.getTime());
                Log.d(ListContentFragment.class.getName(), d.toString());
                if(start != null && end != null & !TextUtils.isEmpty(stopic)){
                    FirestoreServices.getTopicCollection().document(stopic)
                            .set(d).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "saved Succesfully", Toast.LENGTH_SHORT).show();
                                topic.setText("");
                            }
                        }
                    });

                }
            }
        });


        return rootView;
    }

    private Date parseDateTime(String time) {
        try {
            String sDate1=String.valueOf(time);
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(sDate1);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    private void initialize(View rootView) {
        topic =(EditText) rootView.findViewById(R.id.topicName);
        startTime =(EditText) rootView.findViewById(R.id.startTime);
        endTime =(EditText) rootView.findViewById(R.id.end_time);
        genBtn =(Button) rootView.findViewById(R.id.genTopic);
        onDateSet();
    }

    private void onDateSet() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        startTime.setText(mDay+"/"+mMonth+"/"+mYear+" "+mHour+":"+mMinute);
        endTime.setText(mDay+"/"+mMonth+"/"+mYear+" "+mHour+":"+mMinute);
    }


}
