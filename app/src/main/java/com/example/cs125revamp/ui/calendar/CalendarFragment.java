package com.example.cs125revamp.ui.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.ActivityRegisterBinding;
import com.example.cs125revamp.databinding.FragmentCalendarBinding;
import com.example.cs125revamp.databinding.FragmentSettingsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = preferences.edit();
        int user_id = preferences.getInt("user_id", -1);
        TextView selectedDateActivity= root.findViewById(R.id.chosenDateInfo);

        String command1 = "find " + user_id;
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));
        Python py = Python.getInstance();
        PyObject pyf = py.getModule("main");
        Map<PyObject, PyObject> result = pyf.callAttr("accept_one_command", command1).asMap();

        CalendarView calendarView = root.findViewById(R.id.appCalendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                int sleep = 0;
                int exercise = 0;
                //prev
                System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                System.out.println(dayOfMonth);
                if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < dayOfMonth) {
                    selectedDateActivity.setText("On " + month + "/" + dayOfMonth + "," + year +
                            ", you didn't track any sleep or exercise.");
                }
                else if (Math.abs(Calendar.DAY_OF_MONTH - dayOfMonth) >= 7) {
                    try {
                        sleep = result.get("calendar").asMap().get("prev_sleep").asMap()
                                .get(getDayOfWeek(year, month, dayOfMonth)).toInt();
                        exercise = result.get("calendar").asMap().get("prev_exercise").asMap()
                                .get(getDayOfWeek(year, month, dayOfMonth)).toInt();
                        if (sleep == -1) {
                            sleep = 0;
                        }
                        if (exercise == -1) {
                            exercise = 0;
                        }
                    } catch (ParseException e) {
                        ;
                    }
                    if (sleep == 0 && exercise == 0) {
                        selectedDateActivity.setText("On " + month + "/" + dayOfMonth + "," + year +
                                ", you didn't track any sleep or exercise.");
                    }
                    else {
                        selectedDateActivity.setText("On " + month + "/" + dayOfMonth + "," + year +
                                ", you exercised " + exercise + " minutes and slept " + sleep + " minutes");
                    }
                }
                //curr
                else {
                    try {
                        sleep = result.get("calendar").asMap().get("sleep").asMap()
                                .get(getDayOfWeek(year, month, dayOfMonth)).toInt();
                        exercise = result.get("calendar").asMap().get("exercise").asMap()
                                .get(getDayOfWeek(year, month, dayOfMonth)).toInt();
                        if (sleep == -1) {
                            sleep = 0;
                        }
                        if (exercise == -1) {
                            exercise = 0;
                        }
                    } catch (ParseException e) {
                        ;
                    }
                    if (sleep == 0 && exercise == 0) {
                        selectedDateActivity.setText("On " + month + "/" + dayOfMonth + "," + year +
                                ", you didn't track any sleep or exercise.");
                    }
                    else {
                        selectedDateActivity.setText("On " + month + "/" + dayOfMonth + "," + year +
                                ", you exercised " + exercise + " minutes and slept " + sleep + " minutes");
                    }
                }
            }
        });



        return root;
    }

    private String getDayOfWeek(int year, int month, int dayOfMonth) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat("dd/M/yyyy")
                .parse(dayOfMonth + "/" + month + "/" +year));

        int selectedDay = c.get(Calendar.DAY_OF_WEEK);
        String result = "";
        switch (selectedDay) {
            case Calendar.SUNDAY:
                result = "Sun";
                break;
            case Calendar.MONDAY:
                result = "Mon";
                break;
            case Calendar.TUESDAY:
                result = "Tues";
                break;
            case Calendar.WEDNESDAY:
                result = "Wed";
                break;
            case Calendar.THURSDAY:
                result = "Thurs";
                break;
            case Calendar.FRIDAY:
                result = "Fri";
                break;
            case Calendar.SATURDAY:
                result = "Sat";
                break;
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}