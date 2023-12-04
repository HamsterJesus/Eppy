package com.example.eppy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//create DAO for questionTable
@Dao
public interface questionDAO {

    //queries for questionTable
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(question newQuestion);

    @Query("SELECT * FROM questionTable")
    List<question> getAllQuestions();

    @Delete
    void deleteAllQuestions(List<question> allQuestions);
}
