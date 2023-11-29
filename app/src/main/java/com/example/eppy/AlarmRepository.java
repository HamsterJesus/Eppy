package com.example.eppy;

import android.content.Context;
import android.provider.CalendarContract;

import java.util.List;

public class AlarmRepository {

    private AlarmDAO r_alarmDAO;

    private static AlarmRepository INSTANCE;
    private Context context;

    private AlarmRepository(Context context) {
        super();
        this.context = context;

        r_alarmDAO = AlarmDatabase.getDatabase(context).alarmDAO();
    }

    public static AlarmRepository getRepository(Context context){
        if(INSTANCE == null){
            synchronized (AlarmRepository.class){
                if(INSTANCE == null)
                    INSTANCE = new AlarmRepository(context);
            }
        }
        return INSTANCE;
    }

    public void storeAlarm(AlarmItem newAlarm){
        this.r_alarmDAO.insert(newAlarm);
    }

    public List<AlarmItem> getAllAlarms() {
        return r_alarmDAO.getAllAlarms();
    }

    public void updateAlarm(AlarmItem alarm) {
        r_alarmDAO.update(alarm);
    }

    public AlarmItem getAlarmById(int id){
        return r_alarmDAO.getAlarmById(id);
    }
}
