package com.example.note_app.data.repo;

import com.example.note_app.data.mapper.NoteMapper;
import com.example.note_app.data.remote.FirebaseAuthService;
import com.example.note_app.data.remote.FirebaseDatabaseService;
import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class NoteRepositoryImpl implements NoteRepository {

    private final FirebaseDatabaseService db;
    private final FirebaseAuthService firebaseAuthService;
    private final NoteMapper noteMapper = new NoteMapper();

    @Inject
    public NoteRepositoryImpl(FirebaseDatabaseService db, FirebaseAuthService firebaseAuthService) {
        this.db = db;
        this.firebaseAuthService = firebaseAuthService;
    }

    @Override
    public void deleteNoteItem(String noteItemId) {

    }

    @Override
    public void addNoteItem(NoteModel noteModel) {
        db.addUserNote(
                firebaseAuthService.getCurrentUserId(),
                noteMapper.mapNoteModelToDto(noteModel)
        );
    }

    @Override
    public void editNoteItem(String noteItemId) {
        db.deleteUserNote(noteItemId);
    }

    @Override
    public NoteModel getNoteItem(String noteItemId) {
        return null;
    }

    @Override
    public Observable<List<NoteModel>> getAllNoteItems() {
        return db.getAllUserNotes(firebaseAuthService.getCurrentUserId())
                .flatMap(noteDtos -> {
                    List<NoteModel> noteModels = new ArrayList<>();
                    noteModels = noteMapper.mapDtoListToNoteModelList(noteDtos);
                    return Observable.just(noteModels);
                });
    }
}
