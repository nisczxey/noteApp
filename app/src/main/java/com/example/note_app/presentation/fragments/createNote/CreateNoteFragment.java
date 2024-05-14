package com.example.note_app.presentation.fragments.createNote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentCreateNoteBinding;


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
        initClicks();
    }

    private void initClicks() {
        binding.saveBtn.setOnClickListener(view -> {
            String noteTitle = binding.titleEd.getText().toString();
            String noteContent = binding.contentEditText.getText().toString();
            saveNote(noteTitle, noteContent);
        });
    }

    private void saveNote(String noteTitle, String noteContent){
        if (isDataIsEmpty(noteTitle, noteContent)) {
            vm.createNote(noteTitle, noteContent);
            Toast.makeText(getView().getContext(),
                    getString(R.string.savingIsSuccess),
                    Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = requireActivity()
                    .getSupportFragmentManager();
            fragmentManager.popBackStack();
        } else {
            Toast.makeText(getView().getContext(),
                    getString(R.string.someDataIsEmpty),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDataIsEmpty(String title, String content) {
        return (!title.trim().isEmpty()) && (!content.trim().isEmpty());
    }
}