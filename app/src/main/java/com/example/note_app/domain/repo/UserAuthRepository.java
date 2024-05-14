package com.example.note_app.domain.repo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface UserAuthRepository {

    Observable<Boolean> registerUser(String email, String password);
    Observable<Boolean> logInUser(String email, String password);

    boolean isUserLoggedIn();

    void userLogOut();

}
