package com.example.cs125revamp.ui.sleep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs125revamp.databinding.FragmentCalendarBinding;
import com.example.cs125revamp.databinding.FragmentSleepBinding;

public class SleepFragment extends Fragment {

    private FragmentSleepBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SleepViewModel sleepViewModel =
                new ViewModelProvider(this).get(SleepViewModel.class);

        binding = FragmentSleepBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSleep;
        sleepViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}