package com.example.cs125revamp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.FragmentUpdateGoalsBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateGoalsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentUpdateGoalsBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateGoals.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateGoalsFragment newInstance(String param1, String param2) {
        UpdateGoalsFragment fragment = new UpdateGoalsFragment();
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
        binding = FragmentUpdateGoalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);

        int user_id = preferences.getInt("user_id", -1);
        updateWeightsShown(root, user_id);

        //BACK BUTTON
        Button btnUpdateGoalsBack = root.findViewById(R.id.backButtonEnterGoals);
        btnUpdateGoalsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new SettingsFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        EditText curr_weight_box=(EditText)root.findViewById(R.id.currentWeightForm);
        EditText targ_weight_box=(EditText)root.findViewById(R.id.targetWeightForm);

        //SUBMIT BUTTON
        Button btnUpdateGoalsSubmit = root.findViewById(R.id.submitInfoChangeBtn);
        TextView confirmationMessage= (TextView)root.findViewById(R.id.confirmationMessage);
        btnUpdateGoalsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Python.isStarted())
                    Python.start(new AndroidPlatform(getContext()));
                String curr_weight_input = curr_weight_box.getText().toString();
                String targ_weight_input = targ_weight_box.getText().toString();
                if (curr_weight_input.length() ==0) {
                    curr_weight_input = "-1";
                }
                if (targ_weight_input.length() ==0) {
                    targ_weight_input = "-1";
                }
                int new_curr_weight = Integer.valueOf(curr_weight_input);
                int new_targ_weight = Integer.valueOf(targ_weight_input);
                String command1 = "";
                String command2 = "";

                if (new_curr_weight != -1) {
                    command1 = "update " +user_id
                            + " stats weight " + new_curr_weight;
                }
                if (new_targ_weight != -1) {
                    command2 = "update " + user_id
                            + " goals weight " + new_targ_weight;
                }

                Python py = Python.getInstance();
                PyObject pyf = py.getModule("main");

                if (command1.length() != 0) {
                    pyf.callAttr("accept_one_command", command1);
                }
                if (command2.length() != 0) {
                    pyf.callAttr("accept_one_command", command2);
                }
                confirmationMessage.setText("Successfully updated weights");

                updateWeightsShown(root, user_id);
            }
        });
        return root;
    }

    private void updateWeightsShown(View root, int user_id) {
        TextView currWeightText= (TextView)root.findViewById(R.id.currWeightText);
        TextView targWeightText= (TextView)root.findViewById(R.id.targetWeightText);
        String command1 = "find " + user_id;
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));

        Python py = Python.getInstance();
        PyObject pyf = py.getModule("main");
        Map<PyObject, PyObject> result = pyf.callAttr("accept_one_command", command1).asMap();

        int targWeight = result.get("goals").asMap().get("weight").toInt();
        int currWeight = result.get("stats").asMap().get("weight").toInt();

        currWeightText.setText("Current Weight: " + currWeight);
        targWeightText.setText("Target Weight: " + targWeight);
    }
}