package com.example.note_app.data.utils;

import com.example.note_app.data.dto.NoteDto;

import java.util.List;

public interface NoteDataListener {
    void onNoteDataLoaded(List<NoteDto> notesList);
    void onDataCancelled(Exception databaseError);
}
