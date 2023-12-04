package com.example.eppy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmListItemViewAdapter extends ArrayAdapter<AlarmItem> {

    //array adapter context
    private Context context;


    //list of alarms to display
    private List<AlarmItem> alarms;

    //constructor
    public AlarmListItemViewAdapter(@NonNull Context context, int resource, @NonNull List<AlarmItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.alarms = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;

        if(itemView == null){
            itemView = LayoutInflater.from(this.context).inflate(R.layout.alarm_list_item, parent, false);
        }

        //get alarm to display
        AlarmItem alarm = this.alarms.get(position);

        //update itemView to show alarms name
        TextView tv_AlarmName = itemView.findViewById(R.id.alarmNametxt);
        tv_AlarmName.setText(alarm.getAlarmName());

        //Time
        int hours = alarm.getHour();  // Extract the whole number part as hours
        int minutes = alarm.getMinute();  // Extract the decimal part as minutes

        // Format the time
        String formattedTime = String.format("%02d:%02d", hours, minutes);

        TextView tv_alarmTime = itemView.findViewById(R.id.alarmSetFortxt);
        tv_alarmTime.setText(formattedTime); //display time in listview

        //alarms triggering
        Switch alarmSwitch = itemView.findViewById(R.id.SetAlarmbtn);
        alarmSwitch.setChecked(alarm.isAlarmSet()); //change checked state based on if alarm is set
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //if alarm checked = true
                if(isChecked){
                    //update alarm in repo so that set is true
                    alarm.setAlarmSet(true);
                    AlarmRepository.getRepository(getContext()).updateAlarm(alarm);

                    //display toast message on ui saying "I have been set"
                    CharSequence text = "I have been set";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else { //if alarm checked = false
                    //update alarm in repo so that set is false
                    alarm.setAlarmSet(false);
                    AlarmRepository.getRepository(getContext()).updateAlarm(alarm);

                    //display toast message on ui saying "I have been unset"
                    CharSequence text = "I have been unset";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        //quiz button
        Button triviaButton = itemView.findViewById(R.id.Triviabtn);

        triviaButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); //create bundle
                bundle.putInt(quiz.ARG_PARAM_CurrentId, alarm.getUid()); //put id of selected list item in bundle

                //navigate to quiz fragment with bundle
                MainActivity.getNavController().navigate(R.id.quiz, bundle);
            }
        });

        //delete button
        Button deleteButton = itemView.findViewById(R.id.DeleteAlarmbtn);

        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("DeleteAlarm", "Deleting alarm: " + alarm.getUid()); //testing

                //delete selected alarm from the alarmTable
                AlarmRepository.getRepository(getContext()).delete(alarm);
                alarms.remove(position); //remove selected alarm from alarms list so it isn't seen in listview
                notifyDataSetChanged(); //lest listview know it's been edited
            }
        });


        return itemView;
    }
}
