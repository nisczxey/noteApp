package com.example.note_app.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.usecases.authUseCases.IsUserLoggedInUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class MainViewModel extends ViewModel {

    private final IsUserLoggedInUseCase isUserLoggedInUseCase;


    @Inject
    public MainViewModel(IsUserLoggedInUseCase isUserLoggedInUseCase) {
        this.isUserLoggedInUseCase = isUserLoggedInUseCase;
    }

    public LiveData<Boolean> checkUserLoggedIn() {
        MutableLiveData<Boolean> _userLoggedIn = new MutableLiveData<>();
        _userLoggedIn.setValue(isUserLoggedInUseCase.execute());
        return _userLoggedIn;
    }

}
