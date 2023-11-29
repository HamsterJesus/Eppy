package com.example.eppy;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AlarmItem.class}, version=3, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {

    //get DOA
    public abstract AlarmDAO alarmDAO();
    private static AlarmDatabase INSTANCE;

    public static AlarmDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AlarmDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AlarmDatabase.class, "alarm_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
