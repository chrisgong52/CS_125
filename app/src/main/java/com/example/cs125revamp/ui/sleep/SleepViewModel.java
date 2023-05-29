package com.example.cs125revamp.ui.sleep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SleepViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SleepViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Sleepy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}