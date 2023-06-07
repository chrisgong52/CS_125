package com.example.cs125revamp.ui.recommendations;

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
import com.example.cs125revamp.databinding.FragmentRecommendationsBinding;

public class RecommendationsFragment extends Fragment {

    private FragmentRecommendationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SleepViewModel sleepViewModel =
                new ViewModelProvider(this).get(SleepViewModel.class);

        binding = FragmentRecommendationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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

        final TextView textView = binding.textSleep;
        //sleepViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}