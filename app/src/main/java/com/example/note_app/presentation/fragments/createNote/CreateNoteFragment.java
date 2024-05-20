package com.example.note_app.presentation.fragments.createNote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentCreateNoteBinding;
import com.example.note_app.presentation.utils.Constants;


public class CreateNoteFragment extends Fragment {
	private FragmentCreateNoteBinding binding;
	private CreateNoteViewModel vm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentCreateNoteBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		vm = new ViewModelProvider(requireActivity()).get(CreateNoteViewModel.class);
		if (getArguments() != null) {
			String screenMode = getArguments().getString(Constants.SCREEN_MODE_KEY);
			setScreenMode(screenMode);
		} else {
			throw new RuntimeException("some problems: arguments equal: "
					+ getArguments().toString());
		}
	}

	private void setScreenMode(String screenMode){
		if (screenMode == Constants.ADD_SCREEN_MODE) {
			Toast.makeText(getContext(), screenMode, Toast.LENGTH_SHORT).show();
			initForCreatingMode();
		} else if (screenMode == Constants.EDIT_SCREEN_MODE) {
			String noteID = getArguments().getString(Constants.NOTE_ID_KEY);
			Toast.makeText(getContext(), screenMode, Toast.LENGTH_SHORT).show();
			initForEditingMode(noteID);
		} else {
			throw new RuntimeException(getString(R.string.unknown_screenmode));
		}
	}

	private void initForCreatingMode() {
		binding.saveBtn.setOnClickListener(v -> {
			String title = binding.titleEd.getText().toString();
			String text = binding.contentEditText.getText().toString();
			if (isDataIsNotEmpty(title, text)) {
				vm.createNote(title, text);
				closeCreateFragment();
			} else {
				Toast.makeText(
						getContext(),
						getString(R.string.fields_dont_must_be_empty),
						Toast.LENGTH_SHORT
				).show();
			}
		});
	}

	private void initForEditingMode(String noteId) {
		binding.tvFragmentTitle.setText(R.string.editing_a_note);
		vm.getNoteTextAndTitle(noteId, new OnNoteDetailCallback() {
			@Override
			public void onNoteDetailsRetrieved(String title, String text) {
				binding.titleEd.setText(title);
				binding.contentEditText.setText(text);
			}
		});
		binding.saveBtn.setOnClickListener(v -> {
			String title = binding.titleEd.getText().toString();
			String text = binding.contentEditText.getText().toString();
			if (isDataIsNotEmpty(title, text)) {
				vm.changeNote(noteId, title, text);
				closeCreateFragment();
			} else {
				Toast.makeText(
						getContext(),
						getString(R.string.fields_dont_must_be_empty),
						Toast.LENGTH_SHORT
				).show();
			}
		});
	}


	private boolean isDataIsNotEmpty(String title, String content) {
		return (! title.trim().isEmpty()) && (! content.trim().isEmpty());
	}
	private void closeCreateFragment(){
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.popBackStack();
	}

	public static CreateNoteFragment newInstanceInCreatingMode(
			String screenModeKey, String screenMode
	) {
		CreateNoteFragment fragment = new CreateNoteFragment();
		Bundle args = new Bundle();
		args.putString(screenModeKey, screenMode);
		fragment.setArguments(args);
		return fragment;
	}

	public static CreateNoteFragment newInstanceInEditingMode(
			String screenModeKey, String screenMode, String noteIdKey, String noteId
	) {
		CreateNoteFragment fragment = new CreateNoteFragment();
		Bundle args = new Bundle();
		args.putString(screenModeKey, screenMode);
		args.putString(noteIdKey, noteId);
		fragment.setArguments(args);
		return fragment;
	}

}