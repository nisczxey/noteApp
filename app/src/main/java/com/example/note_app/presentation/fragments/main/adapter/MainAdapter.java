package com.example.note_app.presentation.fragments.main.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.note_app.R;
import com.example.note_app.domain.models.NoteModel;

import java.util.List;

public class MainAdapter extends ListAdapter<NoteModel, NoteItemViewHolder> {

    private final LayoutInflater inflater;

    public MainAdapter(@NonNull LayoutInflater inflater, List<NoteModel> notes) {
        super(new NoteItemDiffCallback());
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_note, parent, false);
        return new NoteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        NoteModel note = getItem(position);
        holder.title.setText(note.getNoteTitle());
        holder.description.setText(note.getNoteText());
    }

}
