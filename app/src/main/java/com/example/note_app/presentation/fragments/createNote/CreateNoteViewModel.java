package com.example.note_app.presentation.fragments.createNote;

import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.usecases.notesUseCases.AddNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.EditNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetNoteItemUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class CreateNoteViewModel extends ViewModel {

	private final AddNoteItemUseCase addNoteItemUseCase;
	private final GetNoteItemUseCase getNoteItemUseCase;
	private final EditNoteItemUseCase editNoteItemUseCase;
	private Disposable disposable;

	@Inject
	public CreateNoteViewModel(
			AddNoteItemUseCase addNoteItemUseCase,
			GetNoteItemUseCase getNoteItemUseCase,
			EditNoteItemUseCase editNoteItemUseCase
	) {
		this.addNoteItemUseCase = addNoteItemUseCase;
		this.getNoteItemUseCase = getNoteItemUseCase;
		this.editNoteItemUseCase = editNoteItemUseCase;
	}

	public void createNote(String title, String text) {
		addNoteItemUseCase.execute(title, text);
	}

	public void changeNote(String noteId, String title, String text) {
		editNoteItemUseCase.execute(noteId,title,text);
	}

	public void getNoteTextAndTitle(String noteId, OnNoteDetailCallback callback){
		disposable = getNoteItemUseCase.execute(noteId).subscribe(noteModel -> {
		callback.onNoteDetailsRetrieved(noteModel.getNoteTitle(), noteModel.getNoteText());
		});
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		if (disposable != null) {
			disposable.dispose();
		}
	}
}
