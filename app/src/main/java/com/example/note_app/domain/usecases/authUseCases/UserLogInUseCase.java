package com.example.note_app.domain.usecases.authUseCases;

import com.example.note_app.domain.repo.UserAuthRepository;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class UserLogInUseCase {

    private final UserAuthRepository repo;

    public UserLogInUseCase(UserAuthRepository repo) {
        this.repo = repo;
    }

    public Observable<Boolean> execute(String email, String password){
       return repo.logInUser(email, password);
    }
}
