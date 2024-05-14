package com.example.note_app.data.remote;

import android.util.Log;

import com.example.note_app.data.utils.AuthCompletionListener;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthService {

	private final FirebaseAuth firebaseAuth;


	@Inject
	public FirebaseAuthService(FirebaseAuth firebaseAuth) {
		this.firebaseAuth = firebaseAuth;
	}

	public boolean isUserLoggedIn() {
		return firebaseAuth.getCurrentUser() != null;
	}

	public void userRegister(String email, String password, AuthCompletionListener listener) {

		firebaseAuth.createUserWithEmailAndPassword(email, password)
				.addOnSuccessListener(authResult -> {
					if (authResult != null) {
						listener.onComplete(true);
					} else {
						listener.onError(new RuntimeException(
								"Registration failed: authResult is null"
						));
					}
				}).
				addOnFailureListener(listener::onError);
	}

	public void userLogIn(String email, String password, AuthCompletionListener listener) {
		firebaseAuth.signInWithEmailAndPassword(email, password)
				.addOnSuccessListener(signInResult -> {
            if (signInResult != null){
                listener.onComplete(true);
            } else {
                listener.onError(new RuntimeException(
                        "Log In failed: signInResult is null"
                ));
            }
				});
	}

	public void userLogOut() {
		firebaseAuth.signOut();
	}

	public String getCurrentUserId() {
		if (firebaseAuth.getCurrentUser() != null) {
			String userId = firebaseAuth.getUid();
			if (userId != null) {
				Log.e("ololo", userId);
				return userId;
			}
		}
		return null;
	}

}
