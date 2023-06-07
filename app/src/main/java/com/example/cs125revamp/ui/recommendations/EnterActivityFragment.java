package com.example.cs125revamp.ui.recommendations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cs125revamp.R;
import com.example.cs125revamp.databinding.FragmentEnterActivityBinding;

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

        Button btnBackSleep = root.findViewById(R.id.backButtonEnterInfo);
        btnBackSleep.setOnClickListener(new View.OnClickListener() {
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
}