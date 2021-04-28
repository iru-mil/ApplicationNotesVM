package ru.geekbrains.applicationnotesvm.ui.notes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.applicationnotesvm.R;
import ru.geekbrains.applicationnotesvm.domain.Note;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_NOTE = 0;
    public static final int ITEM_HEADER = 1;
    private final List<AdapterItem> items = new ArrayList<>();
    private OnNoteClicked noteClicked;
    private OnNoteLongClicked noteLongClicked;
    private final Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setItems(List<AdapterItem> toSet) {
        DiffUtil.Callback callback = new DiffUtilCallBack(items, toSet);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        items.clear();
        items.addAll(toSet);
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOTE) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            return new NoteViewHolder(root);
        }

        if (viewType == ITEM_HEADER) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(root);
        }

        throw new IllegalStateException("It can't be happening");
    }

    @Override
    public int getItemViewType(int position) {
        AdapterItem item = items.get(position);

        if (item instanceof HeaderAdapterItem) {
            return ITEM_HEADER;
        }
        if (item instanceof NoteAdapterItem) {
            return ITEM_NOTE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoteViewHolder) {
            NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
            Note item = ((NoteAdapterItem) items.get(position)).getNote();
            noteViewHolder.getTitle().setText(item.getNoteName());
            noteViewHolder.getPurport().setText(item.getNotePurport());
            if (item.getNoteImportanceDegree() == 1) {
                noteViewHolder.getPurport().setTextColor(Color.RED);
            }
            if (!item.isNoteToArchive()) {
                noteViewHolder.getStatus().setText("Актуально");
            } else {
                noteViewHolder.getStatus().setText("Выполнено");
            }
            Glide.with(noteViewHolder.getImage())
                    .load(item.getImageUrl())
                    .into(noteViewHolder.getImage());
        }
        if (holder instanceof HeaderViewHolder) {
            String header = ((HeaderAdapterItem) items.get(position)).getHeader();
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.getHeader().setText(header);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public void setNoteLongClicked(OnNoteLongClicked noteLongClicked) {
        this.noteLongClicked = noteLongClicked;
    }

    public Note getItemAtIndex(int contextMenuItemPosition) {
        return ((NoteAdapterItem) items.get(contextMenuItemPosition)).getNote();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public interface OnNoteLongClicked {
        void onNoteLongClicked(View itemView, int position, Note note);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }

        public TextView getHeader() {
            return header;
        }
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
                    noteClicked.onNoteClicked(((NoteAdapterItem) items.get(getAdapterPosition())).getNote());
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (noteLongClicked != null) {
                    noteLongClicked.onNoteLongClicked(itemView, getAdapterPosition(), ((NoteAdapterItem) items.get(getAdapterPosition())).getNote());
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


