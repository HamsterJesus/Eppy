package com.example.eppy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(AlarmItem alarmItem);

    @Query("SELECT * FROM alarmTable")
    List<AlarmItem> getAllAlarms();

    @Query("SELECT * FROM alarmTable WHERE uid=:id")
    AlarmItem getAlarmById(int id);

    @Update
    void update(AlarmItem alarm);

    @Delete
    public void delete(AlarmItem alarm);
}
