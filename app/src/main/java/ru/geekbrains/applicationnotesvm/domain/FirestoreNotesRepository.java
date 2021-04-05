package ru.geekbrains.applicationnotesvm.domain;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
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

//    public void addNewNote(Callback<Note> noteCallback) {
//
//        Note note = new Note("", "Мероприятие", "Провести мероприятие 1.04", new Date(), 2, false, "https://images.pexels.com/photos/1157557/pexels-photo-1157557.jpeg");
//
//        HashMap<String, Object> data = new HashMap<>();
//        data.put(FIELD_NOTE_NAME, note.getNoteName());
//        data.put(FIELD_NOTE_PURPORT, note.getNotePurport());
//        data.put(FIELD_NOTE_IMAGE_URL, note.getImageUrl());
//        data.put(FIELD_NOTE_IMPORTANCE_DEGREE, note.getNoteImportanceDegree());
//        data.put(FIELD_NOTE_TO_ARCHIVE, note.isNoteToArchive());
//        data.put(FIELD_NOTE_CREATION_DATE,note.getNoteCreationDate());
//
//        fireStore.collection(NOTES_COLLECTION)
//                .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                String id = task.getResult().getId();
//
//                note.setId(id);
//
//                noteCallback.onResult(note);
//            }
//        });
//    }

}
