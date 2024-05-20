package com.example.note_app.presentation.fragments.main;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentMainBinding;
import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.presentation.fragments.auth.signIn.SignInFragment;
import com.example.note_app.presentation.fragments.createNote.CreateNoteFragment;
import com.example.note_app.presentation.fragments.main.adapter.MainAdapter;
import com.example.note_app.presentation.fragments.main.adapter.OnNoteItemClickListener;
import com.example.note_app.presentation.utils.Constants;

import java.util.ArrayList;

public class MainFragment extends Fragment {

	private FragmentMainBinding binding;
	private MainViewModel viewModel;
	private MainAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentMainBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupRecyclerView();
		viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
		initClicks();
		addMenu();
		viewModel.getNotes().observe(getViewLifecycleOwner(), result -> adapter.submitList(result));
		viewModel.loadNotes();
	}


	private void initClicks() {
		binding.fabAdd.setOnClickListener(v -> {
			CreateNoteFragment fragment = CreateNoteFragment.newInstanceInCreatingMode(
					Constants.SCREEN_MODE_KEY,
					Constants.ADD_SCREEN_MODE
					);
			createCreateNoteFragment(fragment);
		});
		adapter.setOnNoteItemClickListener(new OnNoteItemClickListener() {
			@Override
			public void onNoteItemLongClick(View view, NoteModel note) {
				showContextMenu(view, note);
			}

			@Override
			public void onNoteItemClick(NoteModel note) {
				createNoteAlertDialog(note.getNoteTitle(), note.getNoteText());
			}
		});

	}

	private void showContextMenu(View view, NoteModel note) {
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.inflate(R.menu.context_menu);
		popup.setOnMenuItemClickListener(menuItem -> {
			int itemId = menuItem.getItemId();
			if (itemId == R.id.menu_open) {
				createNoteAlertDialog(note.getNoteTitle(), note.getNoteText());
				return true;
			} else if (itemId == R.id.menu_edit) {
				CreateNoteFragment fragment = CreateNoteFragment.newInstanceInEditingMode(
						Constants.SCREEN_MODE_KEY,
						Constants.EDIT_SCREEN_MODE,
						Constants.NOTE_ID_KEY,
						note.getId()
				);
				createCreateNoteFragment(fragment);
				return true;
			} else if (itemId == R.id.menu_delete) {
				viewModel.deleteNoteById(note.getId());
				return true;
			} else {
				return false;
			}
		});
		popup.show();
	}

	private  void createNoteAlertDialog(String noteName, String noteDescription){
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_note_details, null);
		builder.setView(dialogView);
		TextView noteTitle = dialogView.findViewById(R.id.dialog_note_title);
		TextView noteDesc = dialogView.findViewById(R.id.dialog_note_description);
		noteTitle.setText(noteName);
		noteDesc.setText(noteDescription);
		builder.setPositiveButton(getString(R.string.close),((dialogInterface, i) ->
				dialogInterface.dismiss()));
		AlertDialog dialog = builder.create();
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
	}

	private  void createCreateNoteFragment(CreateNoteFragment fragment){
		FragmentTransaction fragmentTransaction = getActivity().
				getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment)
				.addToBackStack(null).commit();
	}



	private void setupRecyclerView() {
		adapter = new MainAdapter(LayoutInflater.from(requireContext()), new ArrayList<>());
		binding.rvNotes.setAdapter(adapter);
	}

	private void addMenu() {
		binding.toolbar.addMenuProvider(new MenuProvider() {
			@Override
			public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
				menuInflater.inflate(R.menu.toolbar_menu, menu);
			}

			@Override
			public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
				if (menuItem.getItemId() == R.id.menu_item_log_out) {
					viewModel.userLogOut();
					FragmentTransaction fragmentTransaction = getActivity().
							getSupportFragmentManager().beginTransaction();
					SignInFragment fragment = new SignInFragment();
					fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
					return true;
				} else {
					return false;
				}
			}
		}, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
	}

}