package com.example.eppy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class EppyalarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Log.d("EppyalarmReceiver", "Received alarm broadcast!");
            Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.d("EppyalarmReceiver", "Error in Received alarm broadcast!");
        }

    }
}
