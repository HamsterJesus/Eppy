package com.example.eppy;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context = this;

    //create nav controller
    private static NavController navController;
    private AlarmManager alarmManager;
//    private BroadcastReceiver alarmReceiver;
    private PendingIntent pendingIntent;

    EppyalarmReciever alarmReceiver = new EppyalarmReciever();

    public static NavController getNavController() {
        return navController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "I work");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NAVBAR CODE

        //define nav fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);

        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        NavigationUI.setupWithNavController(bottomNav, navController);

        //when buttons clicked on nav bar
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.homeMenuItem){
                navController.navigate(R.id.home2);
            } else if(itemId == R.id.alarmMenuItem){
                navController.navigate(R.id.alarm);
            } else if (itemId == R.id.settingsMenuItem) {
                navController.navigate(R.id.settings);
            }


            return true;
        });

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        alarmReceiver = new BroadcastReceiver(){
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(context, "BEEP BEEP BEEP!", Toast.LENGTH_SHORT).show();
//            }
//        };

//        registerReceiver(alarmReceiver, new IntentFilter("com.example.eppy.ALARM"), null, null,Context.RECEIVER_NOT_EXPORTED | 0);

        soundTheAlarm();
    }

    private void soundTheAlarm() {
        //alarm
        List<AlarmItem> alarms = AlarmRepository.getRepository(context).getAllAlarms();

        for(int i = 0; i < alarms.size(); i++){
            if(alarms.get(i).isAlarmSet() == true){
                //create calender obj
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarms.get(i).getHour());
                calendar.set(Calendar.MINUTE, alarms.get(i).getMinute());
                calendar.set(Calendar.SECOND,0);

                registerReceiver(alarmReceiver, new IntentFilter("com.example.eppy.ALARM"), null, null,Context.RECEIVER_NOT_EXPORTED | 0);
                //create intent to trigger when alarm go off
                Intent alarmIntent = new Intent("com.example.eppy.ALARM");
                pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE);
//                registerReceiver(alarmReceiver, alarmIntent);

                //set alarm
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(this, "Alarm set for " + alarms.get(i).getHour() + ":" + alarms.get(i).getMinute(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(alarmReceiver);
    }
}