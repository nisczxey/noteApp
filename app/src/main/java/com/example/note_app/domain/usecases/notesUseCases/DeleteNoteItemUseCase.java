package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.repo.NoteRepository;

public class DeleteNoteItemUseCase {

    private final   NoteRepository noteRepository;

    public DeleteNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public  void execute(String noteItemId){
        noteRepository.deleteNoteItem(noteItemId);
    }

}
