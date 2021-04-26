package ru.geekbrains.applicationnotesvm.ui.update;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.geekbrains.applicationnotesvm.domain.Callback;
import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.domain.NotesRepository;

public class UpdateViewModel extends ViewModel {
    private final NotesRepository notesRepository;

    public UpdateViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    private final MutableLiveData<Boolean> progress = new MutableLiveData<>(false);

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    private final MutableLiveData<Boolean> saveEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> saveEnabled() {
        return saveEnabled;
    }

    private final MutableLiveData<Object> saveSucceed = new MutableLiveData<>();

    public LiveData<Object> saveSucceed() {
        return saveSucceed;
    }

    public void validateInput(String newNoteName) {
        saveEnabled.setValue(!newNoteName.isEmpty());
    }

    public void saveNote(Object name, Editable purport, Object importance, Note note) {
        note.setNoteName(name.toString());
        note.setNotePurport(purport.toString());

        progress.setValue(true);
        notesRepository.updateNote(importance, note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                progress.setValue(false);
                saveSucceed.setValue(new Object());
            }
        });
    }

}