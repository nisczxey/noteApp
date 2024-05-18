package com.example.note_app.presentation.fragments.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.usecases.authUseCases.UserLogOutUseCase;
import com.example.note_app.domain.usecases.notesUseCases.DeleteNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetAllNotesUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetNoteItemUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class MainViewModel extends ViewModel {
	private final GetAllNotesUseCase getAllNotesUseCase;
	private final UserLogOutUseCase userLogOutUseCase;
	private final DeleteNoteItemUseCase deleteNoteItemUseCase;
	private  final GetNoteItemUseCase getNoteItemUseCase;

	private MutableLiveData<List<NoteModel>> notesLiveData;
	private  MutableLiveData<NoteModel> noteLD;
	private Disposable disposable;

	@Inject
	public MainViewModel(
			GetAllNotesUseCase getAllNotesUseCase,
			UserLogOutUseCase userLogOutUseCase,
			DeleteNoteItemUseCase deleteNoteItemUseCase,
			GetNoteItemUseCase getNoteItemUseCase
	) {
		this.getAllNotesUseCase = getAllNotesUseCase;
		this.userLogOutUseCase = userLogOutUseCase;
		this.deleteNoteItemUseCase = deleteNoteItemUseCase;
		this.getNoteItemUseCase = getNoteItemUseCase;
		this.notesLiveData = new MutableLiveData<>();
		this.noteLD = new MutableLiveData<>();
	}


	public LiveData<List<NoteModel>> getNotes() {
		return notesLiveData;
	}

	public void loadNotes() {
		disposable = getAllNotesUseCase.execute()
				.subscribe(
						notes -> notesLiveData.setValue(notes),
						throwable -> {
						}
				);
	}

	public void deleteNoteById(String noteId){
		deleteNoteItemUseCase.execute(noteId);
	}

	public void userLogOut() {
		userLogOutUseCase.execute();
	}

	public LiveData<NoteModel> getNote(String noteId){
		disposable =
				getNoteItemUseCase.execute(noteId).subscribe(
						noteModel -> noteLD.setValue(noteModel)
				);
		return noteLD;
	}


	@Override
	protected void onCleared() {
		super.onCleared();
		if (disposable != null && ! disposable.isDisposed()) {
			disposable.dispose();
		}
	}
}
