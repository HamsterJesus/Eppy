package com.example.eppy;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//database for AlarmItem and question
@Database(entities = {AlarmItem.class, question.class}, version=5, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {

    //get DAO of both entities
    public abstract AlarmDAO alarmDAO();
    public abstract questionDAO questionDAO();
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
