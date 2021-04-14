package ru.geekbrains.applicationnotesvm.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();
    private static final String NOTES_COLLECTION = "notes";
    public static final String FIELD_NOTE_NAME = "noteName";
    public static final String FIELD_NOTE_PURPORT = "notePurport";
    public static final String FIELD_NOTE_IMAGE_URL = "noteImageUrl";
    public static final String FIELD_NOTE_CREATION_DATE = "noteCreationDate";
    public static final String FIELD_NOTE_IMPORTANCE_DEGREE = "noteImportanceDegree";
    public static final String FIELD_NOTE_TO_ARCHIVE = "noteToArchive";
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        fireStore.collection(NOTES_COLLECTION).get()
                .addOnCompleteListener(task -> {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    ArrayList<Note> result = new ArrayList<>();

                    for (DocumentSnapshot doc : documents) {
                        String noteName = doc.getString(FIELD_NOTE_NAME);
                        String notePurport = doc.getString(FIELD_NOTE_PURPORT);
                        String noteImageUrl = doc.getString(FIELD_NOTE_IMAGE_URL);
                        Date noteCreationDate = doc.getDate(FIELD_NOTE_CREATION_DATE);
                        double noteImportanceDegree = doc.getDouble(FIELD_NOTE_IMPORTANCE_DEGREE);
                        Boolean noteToArchive = doc.getBoolean(FIELD_NOTE_TO_ARCHIVE);
                        Note note = new Note(doc.getId(), noteName, notePurport, noteCreationDate, noteImportanceDegree, noteToArchive, noteImageUrl);
                        result.add(note);
                    }
                    callback.onResult(result);
                });
    }

    @Override
    public void addNewNote(Callback<Note> noteCallback) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Note note = new Note("", "Покупка", "Купить ноутбук для работы", calendar.getTime(), 1, false, "https://images.pexels.com/photos/259200/pexels-photo-259200.jpeg");

        HashMap<String, Object> notes = new HashMap<>();
        notes.put(FIELD_NOTE_NAME, note.getNoteName());
        notes.put(FIELD_NOTE_PURPORT, note.getNotePurport());
        notes.put(FIELD_NOTE_IMAGE_URL, note.getImageUrl());
        notes.put(FIELD_NOTE_IMPORTANCE_DEGREE, note.getNoteImportanceDegree());
        notes.put(FIELD_NOTE_TO_ARCHIVE, note.isNoteToArchive());
        notes.put(FIELD_NOTE_CREATION_DATE, note.getNoteCreationDate());

        fireStore.collection(NOTES_COLLECTION)
                .add(notes).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                String id = task.getResult().getId();
                note.setId(id);
                noteCallback.onResult(note);
            }
        });
    }

    @Override
    public void deleteNote(Note note, Callback<Object> objectCallback) {
        fireStore.collection(NOTES_COLLECTION)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objectCallback.onResult(new Object());
                    }
                });
    }

}
