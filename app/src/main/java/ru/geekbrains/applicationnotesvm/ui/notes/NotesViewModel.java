package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.geekbrains.applicationnotesvm.domain.Callback;
import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    private final NotesRepository notesRepository;
    private final MutableLiveData<ArrayList<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Note> newNoteAddedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> removedItemPositionLiveData = new MutableLiveData<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public NotesViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public LiveData<Integer> getRemovedItemPositionLiveData() {
        return removedItemPositionLiveData;
    }

    public void fetchNotes() {
        progressLiveData.setValue(true);
        notesRepository.getNotes(value -> {
            notesLiveData.postValue(new ArrayList<>(value));
            progressLiveData.setValue(false);
        });
    }

    public LiveData<List<AdapterItem>> getNotesLiveData() {
        return Transformations.map(notesLiveData, new Function<ArrayList<Note>, List<AdapterItem>>() {
            @Override
            public List<AdapterItem> apply(ArrayList<Note> input) {
                ArrayList<AdapterItem> result = new ArrayList<>();
                Collections.sort(input, new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o1.getNoteCreationDate().compareTo(o2.getNoteCreationDate());
                    }
                });
                Date currentDate = null;
                for (Note note : input) {
                    Date noteDate = note.getNoteCreationDate();
                    if (!noteDate.equals(currentDate)) {
                        currentDate = noteDate;
                        result.add(new HeaderAdapterItem(simpleDateFormat.format(currentDate)));
                    }
                    result.add(new NoteAdapterItem(note));
                }
                return result;
            }
        });
    }

    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public void deleteAtPosition(Note note) {
        progressLiveData.setValue(true);
        notesRepository.deleteNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                progressLiveData.setValue(false);
                ArrayList<Note> currentNotes = notesLiveData.getValue();
                currentNotes.remove(note);
                notesLiveData.postValue(currentNotes);
            }
        });
    }

    public void addNewNote() {
        progressLiveData.setValue(true);
        notesRepository.addNewNote(new Callback<Note>() {
            @Override
            public void onResult(Note value) {
                progressLiveData.setValue(false);
                ArrayList<Note> currentNotes = notesLiveData.getValue();
                currentNotes.add(value);
                notesLiveData.postValue(currentNotes);
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
