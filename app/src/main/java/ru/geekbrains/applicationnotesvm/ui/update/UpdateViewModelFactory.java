package ru.geekbrains.applicationnotesvm.ui.update;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.applicationnotesvm.domain.FirestoreNotesRepository;

public class UpdateViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UpdateViewModel(FirestoreNotesRepository.INSTANCE);
    }
}
