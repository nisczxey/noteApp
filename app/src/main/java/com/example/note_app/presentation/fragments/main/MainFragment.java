package com.example.note_app.presentation.fragments.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentMainBinding;
import com.example.note_app.presentation.fragments.auth.signIn.SignInFragment;
import com.example.note_app.presentation.fragments.createNote.CreateNoteFragment;
import com.example.note_app.presentation.fragments.main.adapter.MainAdapter;

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
        viewModel.getNotes().observe(getViewLifecycleOwner(), result -> {
            adapter.submitList(result);
        });
        viewModel.loadNotes();
    }



    private void initClicks() {
        binding.fabAdd.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().
                    getSupportFragmentManager().beginTransaction();
            CreateNoteFragment fragment = new CreateNoteFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment)
                    .addToBackStack(null).commit();
        });

    }


    private void setupRecyclerView() {
        adapter = new MainAdapter(LayoutInflater.from(requireContext()),new ArrayList<>());
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
                if(menuItem.getItemId() == R.id.menu_item_log_out){
                    viewModel.userLogOut();
                    FragmentTransaction fragmentTransaction = getActivity().
                            getSupportFragmentManager().beginTransaction();
                    SignInFragment fragment = new SignInFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                            .commit();
                    return true;
                }
                else {
                    return false;
                }
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

}