package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.geekbrains.applicationnotesvm.domain.Callback;
import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    private final NotesRepository notesRepository;
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Note> newNoteAddedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> removedItemPositionLiveData = new MutableLiveData<>();

    public NotesViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public LiveData<Integer> getRemovedItemPositionLiveData() {
        return removedItemPositionLiveData;
    }

    public void fetchNotes() {
        progressLiveData.setValue(true);
        notesRepository.getNotes(value -> {
            notesLiveData.postValue(value);
            progressLiveData.setValue(false);
        });
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public void deleteAtPosition(int contextMenuItemPosition, Note note) {
        progressLiveData.setValue(true);
        notesRepository.deleteNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                removedItemPositionLiveData.postValue(contextMenuItemPosition);
                progressLiveData.setValue(false);
            }
        });
    }

    public void addNewNote() {
        progressLiveData.setValue(true);
        notesRepository.addNewNote(new Callback<Note>() {
            @Override
            public void onResult(Note value) {
                newNoteAddedLiveData.postValue(value);
                progressLiveData.setValue(false);
            }
        });
    }

    public LiveData<Note> getNewNoteAddedLiveData() {
        return newNoteAddedLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


}
