package ru.geekbrains.applicationnotesvm.domain;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class Note implements Parcelable {
    private String id;
    private String noteName;
    private String notePurport;
    private Date noteCreationDate;
    private double noteImportanceDegree;
    private boolean noteToArchive;
    private String noteImageUrl;

    public Note(String id, String noteName, String notePurport, Date noteCreationDate, double noteImportanceDegree, boolean noteToArchive, String noteImageUrl) {
        this.id = id;
        this.noteName = noteName;
        this.notePurport = notePurport;
        this.noteCreationDate = noteCreationDate;
        this.noteImportanceDegree = noteImportanceDegree;
        this.noteToArchive = noteToArchive;
        this.noteImageUrl = noteImageUrl;
    }

    protected Note(Parcel in) {
        id = in.readString();
        noteName = in.readString();
        notePurport = in.readString();
        noteImportanceDegree = in.readDouble();
        noteToArchive = in.readByte() != 0;
        noteImageUrl = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    public double getNoteImportanceDegree() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(noteName);
        dest.writeString(notePurport);
        dest.writeString(noteImageUrl);
        dest.writeDouble(noteImportanceDegree);
        dest.writeBoolean(noteToArchive);
    }
}
