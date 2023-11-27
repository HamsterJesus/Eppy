package com.example.eppy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alarm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alarm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Alarm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alarm.
     */
    // TODO: Rename and change types and number of parameters
    public static Alarm newInstance(String param1, String param2) {
        Alarm fragment = new Alarm();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        //redirect user to add new button form page
        Button addNewRedirect = view.findViewById(R.id.newAlarmBtn);

        addNewRedirect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MainActivity.getNavController().navigate(R.id.addAlarmForm);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //testing
        AlarmItem boo = new AlarmItem(), boo2 = new AlarmItem();
        boo.setAlarmName("Dingus");
        boo.setAlarmTime(8.5);
        boo2.setAlarmName("PringleTube");
        boo2.setAlarmTime(21);
        //list tasks
        List<AlarmItem> alarms = new ArrayList<>();
        alarms.add(boo);
        alarms.add(boo2);

        //get list view to display alarms
        ListView lv_alarms = view.findViewById(R.id.alarm_lv);

        //use the alarms array adapter in list view
        AlarmListItemViewAdapter adapter = new AlarmListItemViewAdapter(getContext(), R.layout.alarm_list_item, alarms);

        lv_alarms.setAdapter(adapter);

    }
}