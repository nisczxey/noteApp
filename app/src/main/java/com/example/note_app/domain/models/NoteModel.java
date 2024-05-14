package com.example.note_app.domain.models;

public class NoteModel {

    private String id;
    private String noteTitle;
    private String noteText;

    public NoteModel() {}


    public NoteModel(String id, String noteTitle, String noteText) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }




}
