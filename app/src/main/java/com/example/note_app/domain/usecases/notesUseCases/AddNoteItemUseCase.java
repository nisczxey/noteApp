package com.example.note_app.domain.usecases.notesUseCases;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.repo.NoteRepository;

import java.util.UUID;

public class AddNoteItemUseCase {

	private final NoteRepository noteRepository;

	public AddNoteItemUseCase(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public void execute(String title, String text) {
		noteRepository.addNoteItem(
                createNoteModel(title,text)
        );
	}

	private NoteModel createNoteModel(String title, String text) {
		String generatedID = generateUID();
		return new NoteModel(generatedID, title, text);
	}

	private String generateUID() {
		return UUID.randomUUID().toString();
	}

}
