package com.example.eppy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
//    private AlarmFragmentListener listener;

    //list of alarms to display
    private List<AlarmItem> alarms;

    //constructor
    public AlarmListItemViewAdapter(@NonNull Context context, int resource, @NonNull List<AlarmItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.alarms = objects;
//        this.listener = listener;
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
        int alarmID = alarm.getUid();

        // Format the time
        String formattedTime = String.format("%02d:%02d", hours, minutes);

        TextView tv_alarmTime = itemView.findViewById(R.id.alarmSetFortxt);
        tv_alarmTime.setText(formattedTime);

        //alarms triggering
        Switch alarmSwitch = itemView.findViewById(R.id.SetAlarmbtn);
        alarmSwitch.setChecked(alarm.isAlarmSet());
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    alarm.setAlarmSet(true);
                    AlarmRepository.getRepository(getContext()).updateAlarm(alarm);

                    CharSequence text = "I have been set";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
//                    Alarm instance = new Alarm();
//                    instance.setAlarm(alarmID);
//                    if (context instanceof AlarmFragmentListener){
//                        listener.setAlarm(alarmID);
//                    }
                } else {
                    alarm.setAlarmSet(false);
                    AlarmRepository.getRepository(getContext()).updateAlarm(alarm);

                    CharSequence text = "I have been unset";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        return itemView;
    }

//    public interface AlarmFragmentListener {
//        void setAlarm(int alarmID);
//    }
}
