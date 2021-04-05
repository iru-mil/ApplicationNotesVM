package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.applicationnotesvm.domain.FirestoreNotesRepository;
//import ru.geekbrains.applicationnotesvm.domain.MockNotesRepository;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //return (T) new NotesViewModel(MockNotesRepository.INSTANCE);
        return (T) new NotesViewModel(FirestoreNotesRepository.INSTANCE);
    }

}
