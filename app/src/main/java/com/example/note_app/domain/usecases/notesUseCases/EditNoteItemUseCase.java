package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.repo.NoteRepository;

public class EditNoteItemUseCase {

    private final NoteRepository noteRepository;

    public EditNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void execute(String noteItemId) {
        noteRepository.editNoteItem(noteItemId);
    }

}
