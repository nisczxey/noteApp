package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

public class AddNoteItemUseCase {

    private final NoteRepository noteRepository;

    public AddNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public  void execute(NoteModel noteModel){
        noteRepository.addNoteItem(noteModel);
    }

}
