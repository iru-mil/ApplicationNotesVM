package ru.geekbrains.applicationnotesvm.ui.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ru.geekbrains.applicationnotesvm.R;

public class NotesFragment extends Fragment {
    public static final String TAG = "NotesFragment";
    private NotesViewModel notesViewModel;
    private NotesAdapter adapter;
    private int contextMenuItemPosition;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.fetchNotes();
        adapter = new NotesAdapter(this);
        adapter.setNoteClicked(note -> Toast.makeText(requireContext(), note.getNoteName(), Toast.LENGTH_SHORT).show());
        adapter.setNoteLongClicked((itemView, position, note) -> {
            contextMenuItemPosition = position;
            itemView.showContextMenu();
        });
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
        TextView filter = view.findViewById(R.id.filter);

        filter.setOnClickListener(v -> {
            MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker().build();
            picker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> Toast.makeText(requireContext(), "выбрана дата: " + simpleDateFormat.format(new Date(selection)), Toast.LENGTH_LONG).show());
            picker.show(getChildFragmentManager(), "MaterialDatePicker");
        });

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setAdapter(adapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setRemoveDuration(2000);
        notesList.setItemAnimator(animator);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        notesList.addItemDecoration(itemDecoration);

        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), notes -> {
                    adapter.clear();
                    adapter.addItems(notes);
                    adapter.notifyDataSetChanged();
                });

        notesViewModel.getRemovedItemPositionLiveData()
                .observe(getViewLifecycleOwner(), position -> {
                    adapter.removeAtPosition(position);
                    adapter.notifyItemRemoved(position);
                });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            notesViewModel.deleteAtPosition(contextMenuItemPosition);
            return true;
        }
        if (item.getItemId() == R.id.action_update) {
            Toast.makeText(requireContext(), "изменена заметка " + contextMenuItemPosition, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_to_archive) {
            Toast.makeText(requireContext(), "отправлена в архив заметка " + contextMenuItemPosition, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

}