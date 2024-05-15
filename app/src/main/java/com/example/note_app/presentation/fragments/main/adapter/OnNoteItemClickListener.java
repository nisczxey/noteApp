package com.example.note_app.presentation.fragments.main.adapter;

import android.view.View;

import com.example.note_app.domain.models.NoteModel;

public interface OnNoteItemClickListener {
	void onNoteItemLongClick(View view, NoteModel note);
	void onNoteItemClick(NoteModel note);
}
