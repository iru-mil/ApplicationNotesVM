package ru.geekbrains.applicationnotesvm.domain;

import java.util.List;

public interface NotesRepository {
    List<Note> getNotes();
}
