package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.geekbrains.applicationnotesvm.domain.MockNotesRepository;
import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    private final NotesRepository notesRepository = MockNotesRepository.INSTANCE;
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> removedItemPositionLiveData = new MutableLiveData<>();

    public LiveData<Integer> getRemovedItemPositionLiveData() {
        return removedItemPositionLiveData;
    }

    public void fetchNotes() {
        notesLiveData.setValue(notesRepository.getNotes());
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public void deleteAtPosition(int contextMenuItemPosition) {
        removedItemPositionLiveData.setValue(contextMenuItemPosition);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


}
