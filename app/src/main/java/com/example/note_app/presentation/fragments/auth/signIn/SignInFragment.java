package com.example.note_app.presentation.fragments.auth.signIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.note_app.R;
import com.example.note_app.databinding.FragmentSignInBinding;
import com.example.note_app.presentation.fragments.auth.signUp.SignUpFragment;
import com.example.note_app.presentation.fragments.main.MainFragment;


public class SignInFragment extends Fragment {

	private SignInViewModel viewModel;
	private FragmentSignInBinding binding;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentSignInBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewModel = new ViewModelProvider(requireActivity()).get(SignInViewModel.class);
		initClick();
	}

	private void initClick() {
		binding.btnLogIn.setOnClickListener(view -> {
			logInUser();
			getUserLogInResult();
		});

		binding.btnGoToRegister.setOnClickListener(view -> {
			FragmentTransaction fragmentTransaction =
					getActivity().getSupportFragmentManager().beginTransaction();
			SignUpFragment fragment = new SignUpFragment();
			fragmentTransaction.replace(
					R.id.fragment_container, fragment
			).addToBackStack(null).commit();
		});

	}

	private void logInUser() {
		String email = binding.edEmail.getText().toString();
		String password = binding.edPassword.getText().toString();
		if (! isFieldsEmpty(email, password)) {
			viewModel.userLogIn(email, password);
		} else {
			Toast.makeText(
					getContext(), R.string.fields_dont_must_be_empty, Toast.LENGTH_SHORT
			).show();
		}
	}

	private boolean isFieldsEmpty(String email, String password) {
		if (email.isEmpty() && password.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private void getUserLogInResult() {
		viewModel.logIn().observe(getViewLifecycleOwner(), result -> {
			if (result) {
				if (viewModel.isUserLoggedIn()) {
					FragmentTransaction fragmentTransaction =
							getActivity().getSupportFragmentManager().beginTransaction();
					MainFragment fragment = new MainFragment();
					fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
				}
			} else {
				Toast.makeText(
						requireContext(), R.string.unknown_user, Toast.LENGTH_SHORT
				).show();
			}
		});
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}