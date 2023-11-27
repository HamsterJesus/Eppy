package com.example.eppy;

import java.time.LocalTime;

public class AlarmItem {
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
