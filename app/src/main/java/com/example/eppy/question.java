package com.example.eppy;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

//create questionTable
@Entity(tableName = "questionTable")
public class question {
    //getters, setters, and declarations for question

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true) //autogenerated pk for question table
    private int qid;

    public String getActualQuestion() {
        return actualQuestion;
    }

    public void setActualQuestion(String actualQuestion) {
        this.actualQuestion = actualQuestion;
    }

    private String actualQuestion; //string question

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String correctAnswer; //correct answer for comparisons in quiz

    public List<String> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    @TypeConverters(Converters.class) //type converter to store list in db
    private List<String> allAnswers; //all multiple choices answers correct and incorrect for specific question
}
