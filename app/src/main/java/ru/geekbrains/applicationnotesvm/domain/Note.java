package ru.geekbrains.applicationnotesvm.domain;

import java.util.Date;

public class Note {
    private String id;
    private String noteName;
    private String notePurport;
    private Date noteCreationDate;
    private int noteImportanceDegree;
    private boolean noteToArchive;
    private String noteImageUrl;

    public Note(String id, String noteName, String notePurport, Date noteCreationDate, int noteImportanceDegree, boolean noteToArchive, String noteImageUrl) {
        this.id = id;
        this.noteName = noteName;
        this.notePurport = notePurport;
        this.noteCreationDate = noteCreationDate;
        this.noteImportanceDegree = noteImportanceDegree;
        this.noteToArchive = noteToArchive;
        this.noteImageUrl = noteImageUrl;
    }

    public String getId() {
        return id;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNotePurport() {
        return notePurport;
    }

    public Date getNoteCreationDate() {
        return noteCreationDate;
    }

    public int getNoteImportanceDegree() {
        return noteImportanceDegree;
    }

    public boolean isNoteToArchive() {
        return noteToArchive;
    }

    public String getImageUrl() {
        return noteImageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNotePurport(String notePurport) {
        this.notePurport = notePurport;
    }

    public void setNoteCreationDate(Date noteCreationDate) {
        this.noteCreationDate = noteCreationDate;
    }

    public void setNoteImportanceDegree(int noteImportanceDegree) {
        this.noteImportanceDegree = noteImportanceDegree;
    }

    public void setNoteToArchive(boolean noteToArchive) {
        this.noteToArchive = noteToArchive;
    }

    public void setImageUrl(String imageUrl) {
        this.noteImageUrl = imageUrl;
    }
}
