package com.example.note_app.presentation.fragments.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.usecases.authUseCases.UserLogOutUseCase;
import com.example.note_app.domain.usecases.notesUseCases.DeleteNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetAllNotesUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class MainViewModel extends ViewModel {
	private final GetAllNotesUseCase getAllNotesUseCase;
	private final UserLogOutUseCase userLogOutUseCase;
	private final DeleteNoteItemUseCase deleteNoteItemUseCase;

	private MutableLiveData<List<NoteModel>> notesLiveData;
	private Disposable disposable;

	@Inject
	public MainViewModel(
			GetAllNotesUseCase getAllNotesUseCase,
			UserLogOutUseCase userLogOutUseCase,
			DeleteNoteItemUseCase deleteNoteItemUseCase
	) {
		this.getAllNotesUseCase = getAllNotesUseCase;
		this.userLogOutUseCase = userLogOutUseCase;
		this.deleteNoteItemUseCase = deleteNoteItemUseCase;
		this.notesLiveData = new MutableLiveData<>();
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

	public void userLogOut() {
		userLogOutUseCase.execute();
	}


	@Override
	protected void onCleared() {
		super.onCleared();
		if (disposable != null && ! disposable.isDisposed()) {
			disposable.dispose();
		}
	}
}
