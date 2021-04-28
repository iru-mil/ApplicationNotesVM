package ru.geekbrains.applicationnotesvm.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void addNewNote(Callback<Note> noteCallback);

    void deleteNote(Note note, Callback<Object> objectCallback);

    void updateNote(Object importance, Note note, Callback<Object> objectCallback);
}
