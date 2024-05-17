package com.example.note_app.presentation.fragments.main.adapter;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.note_app.R;
import com.example.note_app.domain.models.NoteModel;

import java.util.List;

public class MainAdapter extends ListAdapter<NoteModel, NoteItemViewHolder> {

	private final LayoutInflater inflater;
	private OnNoteItemClickListener itemClickListener;

	public MainAdapter(@NonNull LayoutInflater inflater, List<NoteModel> notes) {
		super(new NoteItemDiffCallback());
		this.inflater = inflater;
	}


	@NonNull
	@Override
	public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_item_note, parent, false);
		NoteItemViewHolder viewHolder = new NoteItemViewHolder(view);
		initClicks(view, viewHolder);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
		NoteModel note = getItem(position);
		holder.title.setText(note.getNoteTitle());
		holder.description.setText(note.getNoteText());
	}

	public void setOnNoteItemClickListener(OnNoteItemClickListener listener) {
		this.itemClickListener = listener;
	}

	private void initClicks(View view, NoteItemViewHolder viewHolder) {
		view.setOnTouchListener(
				new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (itemClickListener != null && view.isPressed()) {
								itemClickListener.onNoteItemLongClick(
										view,
										getItem(viewHolder.getAdapterPosition())
								);
							}
						}
					}, 300);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					view.cancelLongPress();
				}
				return false;
			}
		});
		view.setOnClickListener(
				new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (itemClickListener != null) {
					itemClickListener.onNoteItemClick(
							getItem(viewHolder.getAdapterPosition())
					);
				}
			}
		});

	}

}
