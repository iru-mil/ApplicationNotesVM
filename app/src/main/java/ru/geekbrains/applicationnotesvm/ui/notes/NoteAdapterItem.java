package ru.geekbrains.applicationnotesvm.ui.notes;

import java.util.Objects;

import ru.geekbrains.applicationnotesvm.domain.Note;

public class NoteAdapterItem implements AdapterItem{
    private Note note;

    public NoteAdapterItem(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    @Override
    public String uniqueTag() {
        return "NoteAdapterItem" + note.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteAdapterItem that = (NoteAdapterItem) o;
        return Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note);
    }
}
