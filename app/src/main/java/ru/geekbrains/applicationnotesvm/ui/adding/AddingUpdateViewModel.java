package ru.geekbrains.applicationnotesvm.ui.adding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddingUpdateViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddingUpdateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Пока не реализовано");
    }

    public LiveData<String> getText() {
        return mText;
    }

}