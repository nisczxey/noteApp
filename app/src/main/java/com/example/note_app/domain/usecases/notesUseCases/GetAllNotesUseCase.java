package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetAllNotesUseCase {

    private final NoteRepository noteRepository;

    public GetAllNotesUseCase(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Observable<List<NoteModel>> execute() {
        return noteRepository.getAllNoteItems()
                .subscribeOn(Schedulers.io());
    }
}
