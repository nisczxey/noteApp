package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

public class GetNoteItemUseCase {
    private final NoteRepository noteRepository;

    public GetNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteModel execute(String noteItemId){
       return noteRepository.getNoteItem(noteItemId);
    }

}
