package com.example.note_app.presentation.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.databinding.ActivityMainBinding;
import com.example.note_app.presentation.fragments.auth.signIn.SignInFragment;
import com.example.note_app.presentation.fragments.main.MainFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mvm = new ViewModelProvider(this).get(MainViewModel.class);

        mvm.checkUserLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedIn) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (loggedIn) {
                    MainFragment fragment = new MainFragment();
                    fragmentTransaction.replace(binding.fragmentContainer.getId(), fragment).commit();
                } else {
                    SignInFragment fragment = new SignInFragment();
                    fragmentTransaction.replace(binding.fragmentContainer.getId(), fragment).commit();
                }
            }
        });

    }


}