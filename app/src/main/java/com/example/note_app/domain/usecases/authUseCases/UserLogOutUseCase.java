package com.example.note_app.domain.usecases.authUseCases;

import com.example.note_app.domain.repo.UserAuthRepository;

public class UserLogOutUseCase {

    private final UserAuthRepository repo;

    public UserLogOutUseCase(UserAuthRepository repo) {
        this.repo = repo;
    }

    public void execute(){
        repo.userLogOut();
    }

}
