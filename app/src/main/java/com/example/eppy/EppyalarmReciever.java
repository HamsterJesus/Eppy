package com.example.eppy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//receiver for "soundTheAlarm()" function in main activity
//doesn't work (this class is never entered despite attempts to)
public class EppyalarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //just logs for testing rn
        try{
            Log.d("EppyalarmReceiver", "Received alarm broadcast!");
            Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.d("EppyalarmReceiver", "Error in Received alarm broadcast!");
        }

        //this was gonna be for sounding the alarm
    }
}
