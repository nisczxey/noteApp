package com.example.note_app.data.remote;


import androidx.annotation.NonNull;

import com.example.note_app.data.dto.NoteDto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseDatabaseService {

    private  final FirebaseDatabase db;


    @Inject
    public FirebaseDatabaseService(FirebaseDatabase db) {
        this.db = db;
    }

    public void addUserNote(String userId, NoteDto noteDto){
        db.getReference("users").child(userId).child("notes")
                .child(noteDto.getId())
                .setValue(noteDto);
    }

    public void deleteUserNote(String noteItemId){
        ;
    }

    public NoteDto getUserNote(String userId, String noteId){
        return  null;
    }

    public Observable<List<NoteDto>> getAllUserNotes(String userId){
        return Observable.create((ObservableEmitter<List<NoteDto>> emitter) -> {
                    DatabaseReference notesRef = db.getReference("users").child(userId).child("notes");
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<NoteDto> notesList = new ArrayList<>();
                            for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                                NoteDto note = noteSnapshot.getValue(NoteDto.class);
                                notesList.add(note);
                            }
                            emitter.onNext(notesList);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    };
                    notesRef.addValueEventListener(valueEventListener);
                    emitter.setCancellable(() -> notesRef.removeEventListener(valueEventListener));
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  void editUserNoteById(){}

    public  void createUserFolder(String userId){
        DatabaseReference userReference = db.getReference("users").child(userId);
        userReference.setValue(true);
    }

}
