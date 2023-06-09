package com.example.cs125revamp.ui.recommendations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.FragmentRecommendationsBinding;

import java.util.Map;

public class RecommendationsFragment extends Fragment {

    private FragmentRecommendationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecommendationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        int user_id = preferences.getInt("user_id", -1);

        TextView exerciseText = root.findViewById(R.id.exerciseRecommendations);
        TextView sleepText = root.findViewById(R.id.sleepRecommendations);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));
        Python py = Python.getInstance();
        PyObject pyf = py.getModule("recommender");
        Map<PyObject, PyObject> result = pyf.callAttr("get_all_recommendations", user_id).asMap();

        exerciseText.setText(result.get("exercise").toString());
        sleepText.setText(result.get("sleep").toString());
        exerciseText.setMovementMethod(new ScrollingMovementMethod());
        sleepText.setMovementMethod(new ScrollingMovementMethod());

        //ENTER ACTIVITY BUTTON
        Button btnEnterSleep = root.findViewById(R.id.buttonEnterSleep);
        btnEnterSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new EnterActivityFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}