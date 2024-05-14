package com.example.note_app.data.repo;

import com.example.note_app.data.remote.FirebaseAuthService;
import com.example.note_app.data.remote.FirebaseDatabaseService;
import com.example.note_app.data.utils.AuthCompletionListener;
import com.example.note_app.domain.repo.UserAuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class UserAuthRepositoryImpl implements UserAuthRepository {

	private final FirebaseAuthService firebaseAuthService;
	private final FirebaseDatabaseService firebaseDatabaseService;

	@Inject
	public UserAuthRepositoryImpl(FirebaseAuthService firebaseAuthService, FirebaseDatabaseService firebaseDatabaseService) {
		this.firebaseAuthService = firebaseAuthService;
		this.firebaseDatabaseService = firebaseDatabaseService;
	}


	@Override
	public boolean isUserLoggedIn() {
		return firebaseAuthService.isUserLoggedIn();
	}

	@Override
	public void userLogOut() {
		firebaseAuthService.userLogOut();
	}


	@Override
	public Observable<Boolean> registerUser(String email, String password) {
		return Observable.create(emitter -> {
			firebaseAuthService.userRegister(email, password, new AuthCompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (success) {
						if (firebaseAuthService.getCurrentUserId() != null) {
							firebaseDatabaseService.createUserFolder(firebaseAuthService.getCurrentUserId());
							emitter.onNext(true);
							emitter.onComplete();
						} else {
							emitter.onNext(false);
							emitter.onComplete();
						}
					} else {
						emitter.onNext(false);
						emitter.onComplete();
					}
				}

				@Override
				public void onError(Exception e) {
					emitter.onError(e);
				}
			});
		});

	}

	@Override
	public Observable<Boolean> logInUser(String email, String password) {
		return Observable.create(emitter -> {
			firebaseAuthService.userLogIn(email, password, new AuthCompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (success) {
						emitter.onNext(true);
					} else {
						emitter.onNext(false);
					}
					emitter.onComplete();
				}

				@Override
				public void onError(Exception e) {
					emitter.onError(e);
				}
			});
		});
	}

}
