package ru.geekbrains.applicationnotesvm.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockNotesRepository implements NotesRepository {
    public static final NotesRepository INSTANCE = new MockNotesRepository();

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note("id1", "Звонок", "Позвонить терапевту", new Date(), 1, true, "https://images.pexels.com/photos/3783559/pexels-photo-3783559.jpeg"));
        notes.add(new Note("id2", "Мероприятие", "Провести мероприятие 1.04", new Date(), 2, false, "https://images.pexels.com/photos/1157557/pexels-photo-1157557.jpeg?auto=compress&amp;cs=tinysrgb&amp;h=750&amp;w=1260"));
        notes.add(new Note("id3", "Встреча", "Втретиться с друзьями", new Date(), 2, false, "https://images.pexels.com/photos/5698093/pexels-photo-5698093.jpeg"));
        notes.add(new Note("id4", "Звонок", "Позвонить в начальнику", new Date(), 1, true, "https://images.pexels.com/photos/3783559/pexels-photo-3783559.jpeg"));
        notes.add(new Note("id5", "Покупка", "Купить планшет", new Date(), 2, false, "https://images.pexels.com/photos/259200/pexels-photo-259200.jpeg"));
        return notes;
    }
}
