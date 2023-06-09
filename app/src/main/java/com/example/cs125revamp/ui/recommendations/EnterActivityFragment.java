package com.example.cs125revamp.ui.recommendations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.FragmentEnterActivityBinding;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnterActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterActivityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentEnterActivityBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnterActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterSleepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterActivityFragment newInstance(String param1, String param2) {
        EnterActivityFragment fragment = new EnterActivityFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentEnterActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        int user_id = preferences.getInt("user_id", -1);

        EditText sleepInputForm = root.findViewById(R.id.sleepInput);
        EditText exerciseInputForm = root.findViewById(R.id.exerciseInput);

        //SUBMIT BUTTON
        Button btnTrackActivitySubmit = root.findViewById(R.id.recordButton);
        TextView confirmationMessage= root.findViewById(R.id.confirmationMessage);
        btnTrackActivitySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Python.isStarted())
                    Python.start(new AndroidPlatform(getContext()));
                String today = getDayOfWeek();

                int sleep_input = Integer.valueOf(sleepInputForm.getText().toString());
                int exercise_input = Integer.valueOf(exerciseInputForm.getText().toString());
                String command1 = "update " +user_id
                        + " calendar sleep " + today + " " + sleep_input;
                String command2 = "update " + user_id
                        + " calendar exercise " + today + " " + exercise_input;
                System.out.println(command1);
                System.out.println(command2);

                Python py = Python.getInstance();
                PyObject pyf = py.getModule("main");
                pyf.callAttr("accept_one_command", command1);
                pyf.callAttr("accept_one_command", command2);
                confirmationMessage.setText("Successfully tracked your sleep and exercise!");
            }
        });




        //BACK BUTTON
        Button btnBackTrackActivity = root.findViewById(R.id.backButtonEnterInfo);
        btnBackTrackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new RecommendationsFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }

    private String getDayOfWeek() {
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String result = "";
        switch (today) {
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
}