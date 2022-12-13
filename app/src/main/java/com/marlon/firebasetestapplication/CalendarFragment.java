package com.marlon.firebasetestapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Calendar Variables

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = view.findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
//                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH) + " " + (calSelected.get(Calendar.MONTH) + 1)+ " " + calSelected.get(Calendar.YEAR);


                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String dayNumber = String.valueOf(calSelected.get(Calendar.DAY_OF_MONTH));
                String month = String.valueOf(calSelected.get(Calendar.MONTH) + 1);
                String year = String.valueOf(calSelected.get(Calendar.YEAR));

//                Toast.makeText(getActivity(), selectedDate, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DateActivity.class);

                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("dayNumber",dayNumber);
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                startActivity(intent);
            }

            @Override
            public void onDateUnselected(Date date) {
//                Toast.makeText(getActivity(), "Please select a Date.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}