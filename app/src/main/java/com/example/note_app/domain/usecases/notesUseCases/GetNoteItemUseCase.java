package com.example.note_app.domain.usecases.notesUseCases;

import android.util.Log;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

import io.reactivex.rxjava3.core.Single;

public class GetNoteItemUseCase {
    private final NoteRepository noteRepository;

    public GetNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Single<NoteModel> execute(String noteItemId){
       return noteRepository.getNoteItem(noteItemId);
    }

}
