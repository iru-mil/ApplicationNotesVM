package ru.geekbrains.applicationnotesvm.ui.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.applicationnotesvm.R;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesViewModel notesViewModel;
    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.fetchNotes();
        adapter = new NotesAdapter();
        adapter.setNoteClicked(note -> Toast.makeText(requireContext(), note.getNoteName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setAdapter(adapter);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        notesList.addItemDecoration(itemDecoration);

        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), notes -> {
                    adapter.clear();
                    adapter.addItems(notes);
                    adapter.notifyDataSetChanged();
                });
    }
}