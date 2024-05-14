package com.example.note_app.presentation.fragments.auth.signUp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentSignUpBinding;
import com.example.note_app.presentation.fragments.main.MainFragment;


public class SignUpFragment extends Fragment {


    private FragmentSignUpBinding binding;
    private SignUpViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        initClicks();

        vm.getRegistrationResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result) {
                    Toast.makeText(
                            view.getContext(), R.string.register_is_success, Toast.LENGTH_SHORT
                    ).show();
                    getActivity().getSupportFragmentManager().popBackStack(
                            null, FragmentManager.POP_BACK_STACK_INCLUSIVE
                    );
                    FragmentTransaction transaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    MainFragment fragment = new MainFragment();
                    transaction.replace(R.id.fragment_container, fragment).commit();
                } else {
                    Toast.makeText(
                            view.getContext(), R.string.something_went_wrong,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }

    private void initClicks() {
        binding.btnRegister.setOnClickListener(v -> signUp());
        binding.btnBack.setOnClickListener(
                v -> getActivity().getSupportFragmentManager().popBackStack()
        );
    }

    private void signUp() {
        String email = binding.edEmail.getText().toString();
        String password = binding.edPassword.getText().toString();
        String confirmPassword = binding.edConfirmPassword.getText().toString();
        if (checkPasswordLength(password)
                && isEmailValid(email) && isPasswordConfirmed(password, confirmPassword)) {
            vm.registerUser(email, password);
        }
    }

    private boolean isPasswordConfirmed(String password, String confirmPassText) {
        if (password.equals(confirmPassText)) {
            return true;
        } else {
            Toast.makeText(getView().getContext(),
                    getString(R.string.password_is_not_confirmed), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(getView().getContext(),
                    getString(R.string.email_adress_is_not_valid), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkPasswordLength(String password) {
        if (!(password.length() < 6)) {
            return true;
        } else {
            Toast.makeText(getView().getContext(),
                    getString(R.string.minimum_password_length_6_characters),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}