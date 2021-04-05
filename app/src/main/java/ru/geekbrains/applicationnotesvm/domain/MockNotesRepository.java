package ru.geekbrains.applicationnotesvm.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MockNotesRepository implements NotesRepository {
    //public static final NotesRepository INSTANCE = new MockNotesRepository();
    private final Executor executor = Executors.newCachedThreadPool();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Note> notes = new ArrayList();
            notes.add(new Note("id1", "Звонок", "Позвонить терапевту", new Date(), 1, true, "https://images.pexels.com/photos/3783559/pexels-photo-3783559.jpeg"));
            notes.add(new Note("id2", "Мероприятие", "Провести мероприятие 1.04", new Date(), 2, false, "https://images.pexels.com/photos/1157557/pexels-photo-1157557.jpeg"));
            notes.add(new Note("id3", "Встреча", "Втретиться с друзьями", new Date(), 2, false, "https://images.pexels.com/photos/5698093/pexels-photo-5698093.jpeg"));
            notes.add(new Note("id4", "Звонок", "Позвонить в начальнику", new Date(), 1, true, "https://images.pexels.com/photos/3783559/pexels-photo-3783559.jpeg"));
            notes.add(new Note("id5", "Покупка", "Купить планшет", new Date(), 2, false, "https://images.pexels.com/photos/259200/pexels-photo-259200.jpeg"));

            mainThreadHandler.post(() -> callback.onResult(notes));
        });
    }
}
