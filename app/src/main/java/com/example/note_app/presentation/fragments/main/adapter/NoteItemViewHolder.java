package com.example.note_app.presentation.fragments.main.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_app.R;

public class NoteItemViewHolder extends RecyclerView.ViewHolder {
    final TextView title, description;

    public NoteItemViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_item_title);
        description = itemView.findViewById(R.id.tv_item_desc);
    }

}
