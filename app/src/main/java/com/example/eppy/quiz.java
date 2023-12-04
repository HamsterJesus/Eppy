package com.example.eppy;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quiz extends Fragment {
    //private int currentQuestionIndex = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String ARG_PARAM_CurrentId = "currentId";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private int mCurrentId;

    private AlarmItem currentAlarm;

    //next
    //private boolean nextBool = false;

    private int currentQuestionIndex = 0;


    public quiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment quiz.
     */
    // TODO: Rename and change types and number of parameters
    public static quiz newInstance(String param1, String param2, int currentId) {
        quiz fragment = new quiz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM_CurrentId, currentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mCurrentId = getArguments().getInt(ARG_PARAM_CurrentId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Quiz Fragment", "Welcome" + mCurrentId);
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        getQuizQuestions(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void getQuizQuestions(View view) {
        AlarmRepository repo = AlarmRepository.getRepository(getContext());
        currentAlarm = repo.getAlarmById(mCurrentId);
        String url = currentAlarm.getQuizURL();
        //TextView textMan = view.findViewById(R.id.textMan);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textMan.setText("Response is: " + response.substring(0,500));
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            //array of quiz questions
                            JSONArray resultsArray = jsonResponse.getJSONArray("results");

                            //iterate over questions
                            for(int i = 0; i < resultsArray.length(); i++){
                                JSONObject questionObject = resultsArray.getJSONObject(i);

                                //get question name
                                String specificQuestion = questionObject.getString("question");

                                //get correct answer
                                String correctAnswer = questionObject.getString("correct_answer");

                                //array of incorrect answers
                                JSONArray incorrectArray = questionObject.getJSONArray("incorrect_answers");

                                //array for storing all answers
                                List<String> answerArray = new ArrayList<>();
                                for(int j = 0; j<incorrectArray.length(); j++){
                                    answerArray.add(incorrectArray.getString(j));
                                }

                                answerArray.add(correctAnswer);

                                Collections.shuffle(answerArray);

                                question newQuestion = new question();
                                newQuestion.setActualQuestion(specificQuestion);
                                newQuestion.setCorrectAnswer(correctAnswer);
                                newQuestion.setAllAnswers(answerArray);

                                AlarmRepository repo = AlarmRepository.getRepository(getContext());
                                repo.storeQuestion(newQuestion);

                                String tagResponse = "Testing testing 1 2 3";

                                Log.d(tagResponse, "question: " + specificQuestion);
                                Log.d(tagResponse, "answers: " + answerArray);
                                Log.d(tagResponse, "correct only: " + correctAnswer);
                            }

                            displayQuestion(view);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textMan.setText("That didn't work!");
            }
        });

        requestQueue.add(stringRequest);
    }

    //display question on ui
    private void displayQuestion(View view) {

        //get ui components in quiz fragment
        TextView actualQuestion = view.findViewById(R.id.questionTitle);
        RadioGroup multipleChoices = view.findViewById(R.id.multipleChoiceRG);
        Button nextQuestionBtn = view.findViewById(R.id.nextQuestionBtn);
        TextView Correctness = view.findViewById(R.id.displayCorrectness);

        List<question> allQuestions = AlarmRepository.getRepository(getContext()).getAllQuestions();
        question currentQuestion = allQuestions.get(currentQuestionIndex);

        actualQuestion.setText(currentQuestion.getActualQuestion());

        //RADIO GROUP
        List<String> possibleAnswers = currentQuestion.getAllAnswers();

        for(String answer : possibleAnswers){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(answer);
            multipleChoices.addView(radioButton);
        }

        multipleChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //@SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedAnswer = selectedRadioButton.getText().toString();
                    // Print a message with the selected answer
                    Log.d("SelectedAnswer", selectedAnswer + " : " + currentQuestion.getCorrectAnswer());

                    if(selectedAnswer.equals(currentQuestion.getCorrectAnswer().trim())){
                        Correctness.setText("Correct :)");
                    } else {
                        Correctness.setText("Incorrect :(");
                    }

                    Log.d("SelectedAnswer", "Selected Answer: " + selectedAnswer);
                }
            }
        });


        //NEXT BUTTON
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                multipleChoices.removeAllViews();
                Correctness.setText("");

                if(currentQuestionIndex >= allQuestions.size()){
                    MainActivity.getNavController().navigate(R.id.alarm);
                    AlarmRepository.getRepository(getContext()).deleteQuestions(allQuestions);
                    return;
                }

                displayQuestion(view);
            }
        });
    }

}