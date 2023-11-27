package com.example.eppy;

import android.content.Context;
import android.provider.CalendarContract;

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
}
