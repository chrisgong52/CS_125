package com.example.cs125revamp.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.FragmentExerciseBinding;
import com.example.cs125revamp.databinding.FragmentSleepBinding;
import com.example.cs125revamp.ui.sleep.EnterSleepFragment;

public class ExerciseFragment extends Fragment {

    private FragmentExerciseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ExerciseViewModel exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnEnterExercise = root.findViewById(R.id.buttonEnterExercise);
        btnEnterExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new EnterExerciseFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });


        final TextView textView = binding.textExercise;
        exerciseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}