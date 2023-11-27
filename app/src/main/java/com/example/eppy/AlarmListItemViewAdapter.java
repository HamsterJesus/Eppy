package com.example.eppy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        int hours = (int) alarm.getAlarmTime();  // Extract the whole number part as hours
        int minutes = (int) ((alarm.getAlarmTime() - hours) * 60);  // Extract the decimal part as minutes

        // Format the time
        String formattedTime = String.format("%02d:%02d", hours, minutes);

        TextView tv_alarmTime = itemView.findViewById(R.id.alarmSetFortxt);
        tv_alarmTime.setText(formattedTime);

        return itemView;
    }
}
