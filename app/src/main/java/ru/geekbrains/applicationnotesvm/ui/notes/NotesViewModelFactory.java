package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.applicationnotesvm.domain.FirestoreNotesRepository;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(FirestoreNotesRepository.INSTANCE);
    }

}
