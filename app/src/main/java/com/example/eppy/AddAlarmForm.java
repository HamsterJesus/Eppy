package com.example.eppy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAlarmForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAlarmForm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAlarmForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAlarmForm.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAlarmForm newInstance(String param1, String param2) {
        AddAlarmForm fragment = new AddAlarmForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            mParam1 = args.getString(ARG_PARAM1);
            mParam2 = args.getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_alarm_form, container, false);

        //Difficulty spinner
        Spinner spinnerDifficulty = view.findViewById(R.id.difficultySpinner);

        //render items in spinner
        ArrayAdapter<CharSequence>adapterSpin=ArrayAdapter.createFromResource(getActivity(),R.array.Difficulty, android.R.layout.simple_spinner_item);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDifficulty.setAdapter(adapterSpin);

        //Form save button
        Button saveButton = view.findViewById(R.id.saveButton); //find button

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //get user inputted values for each variable
                String alarmName = String.valueOf(((TextView) getView().findViewById(R.id.a_nameInput)).getText());
//                double alarmTime = Double.parseDouble(((TextView) getView().findViewById(R.id.a_timeInput)).getText().toString());
                int noOfQuestions = Integer.valueOf(((TextView) getView().findViewById(R.id.a_NoQuestionsInput)).getText().toString());

                //get values from time picker
                TimePicker timePicker = getView().findViewById(R.id.timePicker);
                int alarmHour = timePicker.getHour();
                int alarmMinute = timePicker.getMinute();

                //get values from spinner
                Object selectedDifficulty = spinnerDifficulty.getSelectedItem();
                String questionDifficulty = selectedDifficulty.toString();

                //construct a url for trivia api using noOfQuestions and questionDifficulty
                String url = "https://opentdb.com/api.php?amount=" + noOfQuestions + "&difficulty=" + questionDifficulty + "&type=multiple";

                //create AlarmItem object to hold this data
                AlarmItem newAlarm = new AlarmItem();
                newAlarm.setAlarmSet(false);
                newAlarm.setAlarmName(alarmName);
                newAlarm.setHour(alarmHour);
                newAlarm.setMinute(alarmMinute);
                newAlarm.setQuizURL(url);

                //save alarm to alarmRepo
                AlarmRepository repo = AlarmRepository.getRepository(getContext());
                repo.storeAlarm(newAlarm);

                //return to the alarm page
                MainActivity.getNavController().navigate(R.id.alarm);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}