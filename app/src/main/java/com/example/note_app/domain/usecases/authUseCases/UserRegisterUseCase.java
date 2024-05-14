package com.example.note_app.domain.usecases.authUseCases;

import com.example.note_app.domain.repo.UserAuthRepository;

import io.reactivex.rxjava3.core.Observable;

public class UserRegisterUseCase {

    private  final UserAuthRepository repo;

    public UserRegisterUseCase(UserAuthRepository repo) {
        this.repo = repo;
    }

    public Observable<Boolean> execute(String email, String password){
        return repo.registerUser(email,password);
    }

}
