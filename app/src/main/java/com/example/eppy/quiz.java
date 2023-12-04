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


    private int mCurrentId; //id of AlarmItem instance

    private AlarmItem currentAlarm; //create alarm item


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
        Log.d("Quiz Fragment", "Welcome" + mCurrentId); //test
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        getQuizQuestions(view); //call get quiz question

        // Inflate the layout for this fragment
        return view;
    }

    //method for parsing json api call and calling the display method
    private void getQuizQuestions(View view) {
        //make a call to the AlarmRepository
        AlarmRepository repo = AlarmRepository.getRepository(getContext());
        currentAlarm = repo.getAlarmById(mCurrentId); //get alarm by id passed into fragment via bundle from AlarmListItemViewAdapter
        String url = currentAlarm.getQuizURL(); //set url to value of url attribute from currentAlarm

        //create requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //make request to api
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response); //jsonObject containing response from Open Trivia DB

                            //array of quiz questions
                            JSONArray resultsArray = jsonResponse.getJSONArray("results");

                            //iterate over every received question
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

                                for(int j = 0; j<incorrectArray.length(); j++){ //loop through incorrectArray adding all the incorrect answers
                                    answerArray.add(incorrectArray.getString(j));
                                }

                                answerArray.add(correctAnswer); //add the correct answer to the array as well

                                Collections.shuffle(answerArray); //randomise the order of the array to make the quiz more challenging

                                //create a question object and add values to each attribute
                                question newQuestion = new question();
                                newQuestion.setActualQuestion(specificQuestion);
                                newQuestion.setCorrectAnswer(correctAnswer);
                                newQuestion.setAllAnswers(answerArray);

                                //store the question object in the quetionTable database
                                AlarmRepository repo = AlarmRepository.getRepository(getContext());
                                repo.storeQuestion(newQuestion);

                                //testing data is as it should be
                                String tagResponse = "Testing testing 1 2 3";

                                Log.d(tagResponse, "question: " + specificQuestion);
                                Log.d(tagResponse, "answers: " + answerArray);
                                Log.d(tagResponse, "correct only: " + correctAnswer);
                            }

                            displayQuestion(view); //call display questions

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

    //display questions one at a time on the ui
    private void displayQuestion(View view) {

        //get ui components in quiz fragment
        TextView actualQuestion = view.findViewById(R.id.questionTitle);
        RadioGroup multipleChoices = view.findViewById(R.id.multipleChoiceRG);
        Button nextQuestionBtn = view.findViewById(R.id.nextQuestionBtn);
        TextView Correctness = view.findViewById(R.id.displayCorrectness);

        //get questions from the questionTable
        List<question> allQuestions = AlarmRepository.getRepository(getContext()).getAllQuestions();
        question currentQuestion = allQuestions.get(currentQuestionIndex);

        actualQuestion.setText(currentQuestion.getActualQuestion());

        //RADIO GROUP
        List<String> possibleAnswers = currentQuestion.getAllAnswers(); //list of answers to a question

        //iterate over each answer
        for(String answer : possibleAnswers){
            //add the answers value to a radioButton
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(answer);

            //add radio button to radio group
            multipleChoices.addView(radioButton);
        }

        //Radio group item selected
        multipleChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId); //find RadioButton
                if (selectedRadioButton != null) { //only do if not null
                    //get value of selectedAnswer
                    String selectedAnswer = selectedRadioButton.getText().toString();

                    // testing
                    Log.d("SelectedAnswer", selectedAnswer + " : " + currentQuestion.getCorrectAnswer());

                    if(selectedAnswer.equals(currentQuestion.getCorrectAnswer().trim())){ //compare selectedAnswer and correctAnswer
                        Correctness.setText("Correct :)"); //if the same display this to user
                    } else {
                        Correctness.setText("Incorrect :("); //if different displat this to user
                    }

                    //testing
                    Log.d("SelectedAnswer", "Selected Answer: " + selectedAnswer);
                }
            }
        });


        //NEXT BUTTON
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentQuestionIndex++; //increment question index by 1
                multipleChoices.removeAllViews(); //remove current RadioButtons from RadioGroup for new answers
                Correctness.setText(""); //reset correctness text to be blank

                if(currentQuestionIndex >= allQuestions.size()){ //if out of questions
                    MainActivity.getNavController().navigate(R.id.alarm); //navigate back to alarm fragment
                    AlarmRepository.getRepository(getContext()).deleteQuestions(allQuestions); //delete all the questions so next time the user will get new questions
                    return; //break out of method
                }

                displayQuestion(view); //recursive call but with the currentQuestionIndex increased to access a different question
            }
        });
    }

}