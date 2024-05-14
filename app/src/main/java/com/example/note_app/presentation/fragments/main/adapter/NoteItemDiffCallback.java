package com.example.note_app.presentation.fragments.main.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.note_app.domain.models.NoteModel;

public class NoteItemDiffCallback extends DiffUtil.ItemCallback<NoteModel>{

    @Override
    public boolean areItemsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {
        return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                oldItem.getNoteText().equals(newItem.getNoteText());
    }
}
