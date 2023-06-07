package com.example.cs125revamp.ui.recommendations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SleepViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SleepViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}