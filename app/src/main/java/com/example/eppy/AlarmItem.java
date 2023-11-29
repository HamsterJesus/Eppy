package com.example.eppy;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

@Entity(tableName = "alarmTable")
public class AlarmItem {
    public void setAlarmSet(boolean set) {
        alarmSet = set;
    }

    public boolean isAlarmSet() {
        return alarmSet;
    }

    private boolean alarmSet;

    public String getQuizURL() {
        return quizURL;
    }

    public void setQuizURL(String quizURL) {
        this.quizURL = quizURL;
    }

    private String quizURL;

    public AlarmItem() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    //    public double getAlarmTime() {
//        return alarmTime;
//    }
//
//    public void setAlarmTime(double alarmTime) {
//        this.alarmTime = alarmTime;
//    }
//
//    private double alarmTime;
    private int hour;

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    private int minute;
}
