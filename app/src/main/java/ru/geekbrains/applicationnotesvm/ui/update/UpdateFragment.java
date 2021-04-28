package ru.geekbrains.applicationnotesvm.ui.update;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.applicationnotesvm.R;
import ru.geekbrains.applicationnotesvm.domain.Note;

public class UpdateFragment extends Fragment {
    public static final String TAG = "AddingUpdateFragment";
    private static final String ARG_NOTE = "ARG_NOTE";
    private static final int START_IMPORTANCE_DEGREE = 1;

    public static UpdateFragment newInstance(Note note) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    private UpdateViewModel viewModel;
    private Note note;
    private OnNoteSaved listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSaved) {
            listener = (OnNoteSaved) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = getArguments().getParcelable(ARG_NOTE);
        viewModel = new ViewModelProvider(this, new UpdateViewModelFactory()).get(UpdateViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        Spinner spinnerName = view.findViewById(R.id.adding_name);
        String[] names = getResources().getStringArray(R.array.note_names);
        for (int i = 0; i < names.length; i++) {
            if (note.getNoteName().equals(names[i])) {
                spinnerName.setSelection(i);
            }
        }

        Spinner spinnerImportance = view.findViewById(R.id.adding_importance);
        if (note.getNoteImportanceDegree() == START_IMPORTANCE_DEGREE) {
            spinnerImportance.setSelection(0);
        } else if (note.getNoteImportanceDegree() == START_IMPORTANCE_DEGREE + 1) {
            spinnerImportance.setSelection(1);
        } else if (note.getNoteImportanceDegree() == START_IMPORTANCE_DEGREE + 2) {
            spinnerImportance.setSelection(2);
        }

        Button saveButton = view.findViewById(R.id.saving);
        EditText editTextPurport = view.findViewById(R.id.adding_purport);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveNote(spinnerName.getSelectedItem(), editTextPurport.getText(), spinnerImportance.getSelectedItem(), note);

            }
        });

        editTextPurport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.validateInput(s.toString());
            }
        });
        editTextPurport.setText(note.getNotePurport());

        viewModel.saveEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                saveButton.setEnabled(aBoolean);
            }
        });
        ProgressBar progressBar = view.findViewById(R.id.progress);
        viewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        viewModel.saveSucceed().observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

                if (listener != null) {
                    listener.onNoteSaved();
                }
            }
        });
    }

    public interface OnNoteSaved {
        void onNoteSaved();
    }

}