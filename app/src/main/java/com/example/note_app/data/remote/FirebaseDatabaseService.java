package com.example.note_app.data.remote;


import androidx.annotation.NonNull;

import com.example.note_app.data.dto.NoteDto;
import com.example.note_app.data.utils.Constants;
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
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseDatabaseService {

	private final FirebaseDatabase db;
	private final DatabaseReference dbRef;


	@Inject
	public FirebaseDatabaseService(FirebaseDatabase db) {
		this.db = db;
		this.dbRef = db.getReference(Constants.DB_REFERENCE_PATH);
	}

	public void addUserNote(String userId, NoteDto noteDto) {
		dbRef.child(userId).child(Constants.DB_PATH_NOTES)
				.child(noteDto.getId())
				.setValue(noteDto);
	}

	public void deleteUserNote(String userId, String noteItemId) {
		dbRef.child(userId).child(Constants.DB_PATH_NOTES).child(noteItemId).removeValue();
	}

	public Single<NoteDto> getUserNote(String userId, String noteId) {
		return Single.create(emitter -> {
			DatabaseReference noteRef =
					dbRef.child(userId).child(Constants.DB_PATH_NOTES).child(noteId);
			noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					NoteDto note = snapshot.getValue(NoteDto.class);
					if (note != null) {
						emitter.onSuccess(note);
					} else {
						emitter.onError(new Throwable("note not found"));
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					emitter.onError(error.toException());
				}
			});
		});
	}

	public Observable<List<NoteDto>> getAllUserNotes(String userId) {
		return Observable.create((ObservableEmitter<List<NoteDto>> emitter) -> {
					DatabaseReference notesRef =
							dbRef.child(userId).child(Constants.DB_PATH_NOTES);
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

	public void editUserNoteById() {

	}

	public void createUserFolder(String userId) {
		dbRef.child(userId).setValue(true);
	}

}
