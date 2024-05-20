package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

public class EditNoteItemUseCase {

    private final NoteRepository noteRepository;

    public EditNoteItemUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void execute(String noteItemId, String title, String text) {
        noteRepository.editNoteItem(
                recreateNote(noteItemId, title,text)
        );
    }

    private NoteModel recreateNote(String id, String title, String text){
         return new NoteModel(id, title, text);
    }

}
