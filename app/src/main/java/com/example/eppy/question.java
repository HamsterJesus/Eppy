package com.example.eppy;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "questionTable")
public class question {

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int qid;

    public String getActualQuestion() {
        return actualQuestion;
    }

    public void setActualQuestion(String actualQuestion) {
        this.actualQuestion = actualQuestion;
    }

    private String actualQuestion;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String correctAnswer;

    public List<String> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    @TypeConverters(Converters.class)
    private List<String> allAnswers;
}
