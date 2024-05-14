package com.example.note_app.presentation.fragments.createNote;

import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.usecases.notesUseCases.AddNoteItemUseCase;

import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateNoteViewModel extends ViewModel {

    private  final AddNoteItemUseCase addNoteItemUseCase;

    @Inject
    public CreateNoteViewModel(AddNoteItemUseCase addNoteItemUseCase) {
        this.addNoteItemUseCase = addNoteItemUseCase;
    }

    public  void createNote(String title, String text){
        String generatedId = generateId();
        NoteModel note = new NoteModel(generatedId, title, text);
        saveNote(note);
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    private void saveNote(NoteModel note){
        addNoteItemUseCase.execute(note);
    }

}
