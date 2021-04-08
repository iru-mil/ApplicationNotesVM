package ru.geekbrains.applicationnotesvm.ui.notes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.applicationnotesvm.R;
import ru.geekbrains.applicationnotesvm.domain.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private final List<Note> items = new ArrayList<>();
    private OnNoteClicked noteClicked;
    private OnNoteLongClicked noteLongClicked;
    private final Fragment fragment;

    public void setItems(List<Note> toSet) {
        items.clear();
        items.addAll(toSet);
    }

    public void addItem(Note note) {
        items.add(note);
    }

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

//    public void addItems(List<Note> toAdd) {
//        items.addAll(toAdd);
//    }
//
//    public void clear() {
//        items.clear();
//    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public void setNoteLongClicked(OnNoteLongClicked noteLongClicked) {
        this.noteLongClicked = noteLongClicked;
    }

    public Note getItemAtIndex(int contextMenuItemPosition) {
        return null;
        //return ((NoteAdapterItem)items.get(contextMenuItemPosition)).getNote();
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    interface OnNoteLongClicked {
        void onNoteLongClicked(View itemView, int position, Note note);
    }

    @NonNull
    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(root);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteViewHolder holder, int position) {
        Note item = items.get(position);
        holder.getTitle().setText(item.getNoteName());
        holder.getPurport().setText(item.getNotePurport());
        if (item.getNoteImportanceDegree() == 1) {
            holder.getPurport().setTextColor(Color.RED);
        }
        if (!item.isNoteToArchive()) {
            holder.getStatus().setText("Актуально");
        } else {
            holder.getStatus().setText("Выполнено");
        }
        Glide.with(holder.getImage())
                .load(item.getImageUrl())
                .into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeAtPosition(int position) {
        items.remove(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView status;
        private final TextView purport;
        private final ImageView image;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            title = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.status);
            purport = itemView.findViewById(R.id.purport);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (noteClicked != null) {
                    noteClicked.onNoteClicked(items.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (noteLongClicked != null) {
                    noteLongClicked.onNoteLongClicked(itemView, getAdapterPosition(), items.get(getAdapterPosition()));
                }
                return true;
            });
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPurport() {
            return purport;
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getStatus() {
            return status;
        }
    }
}


