package com.example.note_app.domain.repo;

import com.example.note_app.domain.models.NoteModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface NoteRepository {

    void deleteNoteItem(String noteItemId);

    void addNoteItem(NoteModel noteModel);

    void editNoteItem(NoteModel noteModel);

    Single<NoteModel> getNoteItem(String noteItemId);

    Observable<List<NoteModel>> getAllNoteItems();

}
