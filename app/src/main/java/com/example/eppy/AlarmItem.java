package com.example.eppy;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

@Entity(tableName = "alarmTable")
public class AlarmItem {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    private String alarmName;

    public double getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(double alarmTime) {
        this.alarmTime = alarmTime;
    }

    private double alarmTime;
}
