package com.megthinksolutions.apps.hived.ui.extendwork;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExtendWorkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExtendWorkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}