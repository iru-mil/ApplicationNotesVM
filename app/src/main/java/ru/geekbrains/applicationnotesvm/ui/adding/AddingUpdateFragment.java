package ru.geekbrains.applicationnotesvm.ui.adding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.applicationnotesvm.R;

public class AddingUpdateFragment extends Fragment {
    public static final String TAG = "AddingUpdateFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //AddingUpdateViewModel addingUpdateViewModel = new ViewModelProvider(this).get(AddingUpdateViewModel.class);
        return inflater.inflate(R.layout.fragment_adding_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}