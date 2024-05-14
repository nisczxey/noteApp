package com.example.note_app.domain.usecases.authUseCases;

import com.example.note_app.domain.repo.UserAuthRepository;

public class IsUserLoggedInUseCase {
    private final UserAuthRepository repo;

    public IsUserLoggedInUseCase(UserAuthRepository repo) {
        this.repo = repo;
    }

    public boolean execute() {
        return repo.isUserLoggedIn();
    }
}
