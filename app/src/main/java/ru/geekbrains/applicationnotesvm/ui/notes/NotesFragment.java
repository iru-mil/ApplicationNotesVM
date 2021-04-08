package ru.geekbrains.applicationnotesvm.ui.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.ui.dialog.AlertDialogFragment;


public class NotesFragment extends Fragment {
    public static final String TAG = "NotesFragment";
    private static final int REQUEST_CODE = 1;
    private NotesViewModel notesViewModel;
    private NotesAdapter adapter;
    private int contextMenuItemPosition;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public void openAlertDialog() {
        DialogFragment fragment = new AlertDialogFragment();
        fragment.setTargetFragment(this, REQUEST_CODE);
        fragment.show(getFragmentManager(), fragment.getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int value = data.getIntExtra(AlertDialogFragment.TAG, -1);
                if (value == 1) {
                    Toast.makeText(requireContext(), "Удалена заметка " + contextMenuItemPosition, Toast.LENGTH_LONG).show();
                } else if (value == 0) {
                    Toast.makeText(requireContext(), "Удаление отменено", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory()).get(NotesViewModel.class);

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
        ProgressBar progressBar = view.findViewById(R.id.progress);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add_new) {
                    notesViewModel.addNewNote();
                } else {
                    Toast.makeText(requireContext(), "Заметки отсортированы", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

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
                    adapter.setItems(notes);
                    adapter.notifyDataSetChanged();
                });

        notesViewModel.getProgressLiveData()
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isVisible) {
                        if (isVisible) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        notesViewModel.getNewNoteAddedLiveData()
                .observe(getViewLifecycleOwner(), new Observer<Note>() {
                    @Override
                    public void onChanged(Note note) {
                        adapter.addItem(note);
                        adapter.notifyItemInserted(adapter.getItemCount() - 1);
                        notesList.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
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
            //int itemIndex = contextMenuItemPosition;
            openAlertDialog();
            //Toast.makeText(requireContext(), "удалена заметка " + contextMenuItemPosition, Toast.LENGTH_SHORT).show();
            //notesViewModel.deleteAtPosition(contextMenuItemPosition, adapter.getItemAtIndex(contextMenuItemPosition));
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